package com.example.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.SearchUsersResponse
import com.example.githubuserapp.model.UserDetailItem
import com.example.githubuserapp.model.UsersItem
import com.example.githubuserapp.network.GithubAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel : ViewModel() {
    private var githubAPI = GithubAPI()
    private var listUsers = MutableLiveData<ArrayList<UsersItem>>()
    private var userDetailItem = UserDetailItem()

    fun setUsers(random: Int) {
        githubAPI.githubRepository.getUsersAll(random)
            .enqueue(object : Callback<ArrayList<UsersItem>> {

                override fun onResponse(
                    call: Call<ArrayList<UsersItem>>,
                    response: Response<ArrayList<UsersItem>>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val usersItem: ArrayList<UsersItem> = usersResponse
                            listUsers.postValue(usersItem)
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UsersItem>>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    fun setUsers(keyword: String) {
        githubAPI.githubRepository.getUsersSearch(keyword)
            .enqueue(object : Callback<SearchUsersResponse> {

                override fun onResponse(
                    call: Call<SearchUsersResponse>,
                    response: Response<SearchUsersResponse>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val usersItem: ArrayList<UsersItem> = usersResponse.items
                            listUsers.postValue(usersItem)
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    fun setUserDetail(username: String) {
        githubAPI.githubRepository.getUserDetail(username)
            .enqueue(object : Callback<UserDetailItem> {
                override fun onResponse(
                    call: Call<UserDetailItem>,
                    response: Response<UserDetailItem>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            userDetailItem = usersResponse
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<UserDetailItem>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    fun setUserFollower(username: String) {
        githubAPI.githubRepository.getUserFollower(username)
            .enqueue(object : Callback<ArrayList<UsersItem>> {
                override fun onResponse(
                    call: Call<ArrayList<UsersItem>>,
                    response: Response<ArrayList<UsersItem>>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val usersItem: ArrayList<UsersItem> = usersResponse
                            listUsers.postValue(usersItem)
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UsersItem>>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    fun setUserFollowing(username: String) {
        githubAPI.githubRepository.getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<UsersItem>> {
                override fun onResponse(
                    call: Call<ArrayList<UsersItem>>,
                    response: Response<ArrayList<UsersItem>>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val usersItem: ArrayList<UsersItem> = usersResponse
                            listUsers.postValue(usersItem)
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UsersItem>>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    fun getUsers(): LiveData<ArrayList<UsersItem>> {
        return listUsers
    }

    fun getUserDetail(): UserDetailItem {
        return userDetailItem
    }
}
