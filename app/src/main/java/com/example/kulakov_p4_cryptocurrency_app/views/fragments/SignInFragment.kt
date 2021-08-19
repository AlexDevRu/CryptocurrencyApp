package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentSignInBinding
import com.example.kulakov_p4_cryptocurrency_app.view_models.SignInVM
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private lateinit var auth: FirebaseAuth

    private lateinit var signInResultLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.userIsSigned()) {
            mainActivityVM.setCurrentUser()
            showMain()
        }

        binding.viewModel = viewModel

        binding.handler = object : Handler {
            override fun onSignIn() {
                val signInIntent = client.signInIntent
                signInResultLauncher.launch(signInIntent)
            }
        }

        auth = Firebase.auth

        signInResultLauncher = registerForActivityResult(
            StartActivityForResult(),
            this::handleSignInResult
        )
    }

    private fun handleSignInResult(result: ActivityResult) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w("asd", "signInResult:failed code=" + e.statusCode)
            viewModel.error.set(e.message)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("asd", "signInWithCredential:success")
                    viewModel.saveSignInStatus(true)
                    showMain()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("asd", "signInWithCredential:failure", task.exception)
                    viewModel.error.set(task.exception?.message)
                }
            }
    }

    private fun showMain() {
        val action = SignInFragmentDirections.actionSignInFragmentToMainFragment()
        findNavController().navigate(action)
    }

    interface Handler {
        fun onSignIn()
    }
}