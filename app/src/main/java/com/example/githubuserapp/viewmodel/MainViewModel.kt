package com.example.githubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.SearchUsersResponse
import com.example.githubuserapp.model.UserItem
import com.example.githubuserapp.network.MainAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private var mainAPI = MainAPI()
    private var listUsers = MutableLiveData<ArrayList<UserItem>>()
    private var userItem = UserItem()
    private var errorMessage = ""

    fun setUsers(random: Int) {
        errorMessage = ""

        mainAPI.mainRepository.getUsersAll(random)
            .enqueue(object : Callback<ArrayList<UserItem>> {

                override fun onResponse(
                    call: Call<ArrayList<UserItem>>,
                    response: Response<ArrayList<UserItem>>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val userItem: ArrayList<UserItem> = usersResponse
                            listUsers.postValue(userItem)
                            errorMessage = "success"
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                    errorMessage = t.message.toString()
                }
            })
    }

    fun setUsers(keyword: String) {
        errorMessage = ""

        mainAPI.mainRepository.getUsersSearch(keyword)
            .enqueue(object : Callback<SearchUsersResponse> {

                override fun onResponse(
                    call: Call<SearchUsersResponse>,
                    response: Response<SearchUsersResponse>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val userItem: ArrayList<UserItem> = usersResponse.items
                            listUsers.postValue(userItem)
                            errorMessage = "success"
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }

                override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {
                    errorMessage = t.message.toString()
                }
            })
    }

    fun setUserDetail(username: String) {
        errorMessage = ""

        mainAPI.mainRepository.getUserDetail(username)
            .enqueue(object : Callback<UserItem> {
                override fun onResponse(
                    call: Call<UserItem>,
                    response: Response<UserItem>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            userItem = usersResponse
                            errorMessage = "success"
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }

                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                    errorMessage = t.message.toString()
                }
            })
    }

    fun setUserFollower(username: String) {
        errorMessage = ""

        mainAPI.mainRepository.getUserFollower(username)
            .enqueue(object : Callback<ArrayList<UserItem>> {
                override fun onResponse(
                    call: Call<ArrayList<UserItem>>,
                    response: Response<ArrayList<UserItem>>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val userItem: ArrayList<UserItem> = usersResponse
                            listUsers.postValue(userItem)
                            errorMessage = "success"
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                    errorMessage = t.message.toString()
                }
            })
    }

    fun setUserFollowing(username: String) {
        errorMessage = ""

        mainAPI.mainRepository.getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<UserItem>> {
                override fun onResponse(
                    call: Call<ArrayList<UserItem>>,
                    response: Response<ArrayList<UserItem>>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val userItem: ArrayList<UserItem> = usersResponse
                            listUsers.postValue(userItem)
                            errorMessage = "success"
                        }
                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                    errorMessage = t.message.toString()
                }
            })
    }

    fun getUsers(): LiveData<ArrayList<UserItem>> {
        return listUsers
    }

    fun getUserDetail(): UserItem {
        return userItem
    }

    fun getErrorMessage(): String {
        return errorMessage
    }
}
