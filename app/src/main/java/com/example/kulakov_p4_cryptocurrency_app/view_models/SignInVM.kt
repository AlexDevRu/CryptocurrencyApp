package com.example.kulakov_p4_cryptocurrency_app.view_models

import androidx.databinding.ObservableField
import com.example.domain.use_cases.preferences.GetSignInStatusUseCase
import com.example.domain.use_cases.preferences.SaveSignInStatusUseCase
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInVM @Inject constructor(
    private val getSignInStatusUseCase: GetSignInStatusUseCase,
    private val saveSignInStatusUseCase: SaveSignInStatusUseCase
): BaseVM() {

    val error = ObservableField<String>()

    fun userIsSigned() = getSignInStatusUseCase.invoke()

    fun saveSignInStatus(status: Boolean) {
        saveSignInStatusUseCase.invoke(status)
    }
}