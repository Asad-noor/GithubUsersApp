package com.worldvisionsoft.githubusersapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.verapax.stops.route.data.remote.Status
import com.worldvisionsoft.githubusersapp.R
import com.worldvisionsoft.githubusersapp.data.model.User
import com.worldvisionsoft.githubusersapp.ui.MainActivity
import com.worldvisionsoft.githubusersapp.ui.home.UsersListFragmentViewModel
import kotlinx.android.synthetic.main.fragment_user_details.*

class UserDetailsFragment: Fragment() {

    private val userDetailsFragmentViewModel by activityViewModels<UserDetailsFragmentViewModel>()
    private var userId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUserDetailsObserver()
        getUsername()?.let {
            userDetailsFragmentViewModel.callUserDetails(it)
        }

        acibClearNote.setOnClickListener {
            etNote.setText("")
        }

        acibSaveNote.setOnClickListener {
            userId?.let { it1 ->
                userDetailsFragmentViewModel.saveNote(etNote.text.toString(),
                    it1
                )
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        acibBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }

    private fun setUserDetailsObserver() {
        userDetailsFragmentViewModel.githubUserDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it.status) {
                    Status.LOADING -> {
                        clProgressbar.visibility = View.VISIBLE
                    }

                    Status.SUCCESS -> {
                        clProgressbar.visibility = View.GONE

                        it.data?.let { it1 -> setData(it1) }
                    }

                    Status.ERROR -> {
                        clProgressbar.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setData(user: User) {
        userId = user.id
        tvFollowers.text = getString(R.string.followers_1_s, ""+user.followers)
        tvFollowing.text = getString(R.string.following_1_s, ""+user.following)

        tvName.text = getString(R.string.name_1_s, user.name)
        tvEmail.text = getString(R.string.email_1_s, user.email)
        tvLocation.text = getString(R.string.location_1_s, user.location)
        tvCompany.text = getString(R.string.company_1_s, user.company)
        tvBlog.text = getString(R.string.blog_1_s, user.blog)
        tvPubRepoTotal.text = getString(R.string.total_repositories_1_s, ""+user.publicRepos)
        tvPubGistTotal.text = getString(R.string.total_gists_1_s, ""+user.publicGists)

        Glide.with(requireContext())
            .load(user.avatarUrl)
            .into(acivPropic)

        if (user.note != null) {
            etNote.setText(user.note)
        }
    }

    private fun getUsername(): String? {
        return arguments?.let { UserDetailsFragmentArgs.fromBundle(it).userName }
    }
}