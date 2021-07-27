package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.lifecycle.ViewModelProvider
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentSignInBinding
import com.example.kulakov_p4_cryptocurrency_app.navigator.Navigator
import com.example.kulakov_p4_cryptocurrency_app.view_models.SignInVM
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignInFragment: BaseFragment<FragmentSignInBinding>
    (R.layout.fragment_sign_in) {

    private val viewModel: SignInVM by lazy {
        ViewModelProvider(this).get(SignInVM::class.java)
    }

    @Inject
    lateinit var client: GoogleSignInClient

    private lateinit var signInResultLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.handler = object : Handler {
            override fun onSignIn() {
                val signInIntent = client.signInIntent
                signInResultLauncher.launch(signInIntent)
            }
        }

        signInResultLauncher = registerForActivityResult(
            StartActivityForResult(),
            this::handleSignInResult
        )
    }

    private fun handleSignInResult(result: ActivityResult) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            Navigator.getInstance().signInFragmentNavigator.showMain()
        } catch (e: ApiException) {
            Log.w("asd", "signInResult:failed code=" + e.statusCode)
            viewModel.error.set(e.localizedMessage)
        }
    }

    interface Handler {
        fun onSignIn()
    }
}