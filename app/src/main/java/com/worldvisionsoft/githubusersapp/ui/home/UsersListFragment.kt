package com.worldvisionsoft.githubusersapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.verapax.stops.route.data.remote.Status
import com.worldvisionsoft.githubusersapp.R
import com.worldvisionsoft.githubusersapp.data.model.User
import com.worldvisionsoft.githubusersapp.ui.custom.NetworkConnectionLiveData
import kotlinx.android.synthetic.main.fragment_users_list.*


class UsersListFragment: Fragment() {

    private lateinit var usersListAdapter: UsersListAdapter
    private lateinit var usersList: MutableList<User>
    private val usersListFragmentViewModel by activityViewModels<UsersListFragmentViewModel>()

    private var isLoading = false
    private var fromIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setUsersListObserver()
        usersListFragmentViewModel.callUsersList(fromIndex)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        NetworkConnectionLiveData(context ?: return)
            .observe(viewLifecycleOwner, Observer { isConnected ->
                if (!isConnected) {
                    // Internet Not Available
                    tvNoInternet.visibility = View.VISIBLE
                    tvNoInternet.text = "No Internet connection"
                    return@Observer
                }
                // Internet Available
                tvNoInternet.text = "Connected"
                Handler().postDelayed(
                    Runnable {
                        tvNoInternet.visibility = View.GONE
                    }, 1500)
            })
    }

    private fun init() {
        usersList = mutableListOf()
        usersListAdapter = UsersListAdapter(usersList, { user ->

        })

        rvRepoList.layoutManager = LinearLayoutManager(requireContext())
        rvRepoList.adapter = usersListAdapter

        initScrollListener()
    }

    private fun setUsersListObserver() {
        usersListFragmentViewModel.githubUsersList.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                        Log.d("tttt", "ui loading")
                    }

                    Status.SUCCESS -> {
                        Log.d("tttt", "ui success")
                        it.data?.let { it1 -> usersList.addAll(it1) }
                        usersListAdapter.notifyDataSetChanged()

                        isLoading = false
                    }

                    Status.ERROR -> {
                        Log.d("tttt", "ui error >"+it.message)
                        tvNoInternet.visibility = View.VISIBLE
                        tvNoInternet.text = it.message
                    }
                }
            }
        })
    }

    private fun initScrollListener() {
        rvRepoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == usersList.size - 1) {
                        //bottom of list!
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        fromIndex += 10
        usersListFragmentViewModel.callUsersList(fromIndex)
    }
}