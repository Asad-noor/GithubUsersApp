package com.worldvisionsoft.githubusersapp.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class User(

	@SerializedName("id")
	val id: Int,

	@SerializedName("gists_url")
	val gistsUrl: String? = null,

	@SerializedName("repos_url")
	val reposUrl: String? = null,

	@SerializedName("following_url")
	val followingUrl: String? = null,

	@SerializedName("twitter_username")
	var twitterUsername: String? = null,

	@SerializedName("bio")
	var bio: String? = null,

	@SerializedName("created_at")
	var createdAt: String? = null,

	@SerializedName("login")
	val login: String? = null,

	@SerializedName("type")
	val type: String? = null,

	@SerializedName("blog")
	var blog: String? = null,

	@SerializedName("subscriptions_url")
	val subscriptionsUrl: String? = null,

	@SerializedName("updated_at")
	var updatedAt: String? = null,

	@SerializedName("site_admin")
	val siteAdmin: Boolean? = null,

	@SerializedName("company")
	var company: String? = null,

	@SerializedName("public_repos")
	var publicRepos: Int? = null,

	@SerializedName("gravatar_id")
	val gravatarId: String? = null,

	@SerializedName("email")
	var email: String? = null,

	@SerializedName("organizations_url")
	val organizationsUrl: String? = null,

	@SerializedName("hireable")
	var hireable: String? = null,

	@SerializedName("starred_url")
	val starredUrl: String? = null,

	@SerializedName("followers_url")
	val followersUrl: String? = null,

	@SerializedName("public_gists")
	var publicGists: Int? = null,

	@SerializedName("url")
	val url: String? = null,

	@SerializedName("received_events_url")
	val receivedEventsUrl: String? = null,

	@SerializedName("followers")
	var followers: Int? = null,

	@SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@SerializedName("events_url")
	val eventsUrl: String? = null,

	@SerializedName("html_url")
	val htmlUrl: String? = null,

	@SerializedName("following")
	var following: Int? = null,

	@SerializedName("name")
    var name: String? = null,

	@SerializedName("location")
	var location: String? = null,

	@SerializedName("node_id")
	val nodeId: String? = null,

	@SerializedName("note")
	var note: String? = null
)
