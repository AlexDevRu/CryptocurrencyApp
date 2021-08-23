package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.use_cases.preferences.SaveSignInStatusUseCase
import com.example.kulakov_p4_cryptocurrency_app.events.SingleLiveEvent
import com.example.kulakov_p4_cryptocurrency_app.utils.SearchDestination
import com.example.kulakov_p4_cryptocurrency_app.utils.SearchableResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.databinding.library.baseAdapters.BR
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val saveSignInStatusUseCase: SaveSignInStatusUseCase,
    private val client: GoogleSignInClient
): ViewModel() {

    val searchableLiveData = SingleLiveEvent<SearchableResult>()
    val openNavigationView = SingleLiveEvent<Boolean>()

    val currentUser = MutableLiveData<UserVM>()

    fun onNewSearchResultReceived(query: String, destination: SearchDestination) {
        searchableLiveData.value = SearchableResult(query, destination)
    }

    fun openDrawer() {
        openNavigationView.value = true
    }

    fun setCurrentUser() {
        val user = Firebase.auth.currentUser
        Log.d("asd", "user ${user} ${user?.displayName}")
        currentUser.value = UserVM(
            user?.photoUrl?.toString(),
            user?.displayName
        )
    }

    fun signOut() {
        saveSignInStatusUseCase.invoke(false)
        Firebase.auth.signOut()
        client.signOut()
    }

    class UserVM(_profileImageUrl: String? = null, _profileName: String? = null): BaseObservable() {
        @Bindable
        var profileImageUrl: String? = null
            set(value) {
                field = value
                notifyPropertyChanged(BR.profileImageUrl)
            }

        @Bindable
        var profileName: String? = null
            set(value) {
                field = value
                notifyPropertyChanged(BR.profileName)
            }

        init {
            profileImageUrl = _profileImageUrl
            profileName = _profileName
        }
    }
}