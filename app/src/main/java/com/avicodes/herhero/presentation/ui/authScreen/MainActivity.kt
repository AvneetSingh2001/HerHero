package com.avicodes.herhero.presentation.ui.authScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avicodes.herhero.R
import com.avicodes.herhero.databinding.ActivityMainBinding
import com.avicodes.herhero.data.models.Users
import com.avicodes.herhero.data.utils.Response
import com.avicodes.herhero.presentation.ui.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import io.grpc.internal.SharedResourceHolder.Resource
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 123
    private val TAG: String = "Message"

    lateinit var googleSignInClient: GoogleSignInClient


    @Inject
    lateinit var auth: FirebaseAuth


    @Inject
    lateinit var factory: MainActivityViewModelFactory

    lateinit var viewModel: MainActivityViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this, factory)[MainActivityViewModel::class.java]

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.revokeAccess()

        binding.signInButton.setOnClickListener {
            signIn()
        }

    }


    private fun signIn() {
        binding.mainCons.visibility = View.INVISIBLE
        binding.progCons.visibility = View.VISIBLE
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.e(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.e(TAG, "Google sign in failed", e)
                binding.mainCons.visibility = View.VISIBLE
                binding.progCons.visibility = View.INVISIBLE
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    updateUI(firebaseUser)
                } else {
                    Toast.makeText(this, "SignIn Failed... Try Again!", Toast.LENGTH_SHORT).show()
                }
            }
    }


    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null) {
            updateUI(auth.currentUser)
        }
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        binding.mainCons.visibility = View.VISIBLE
        binding.progCons.visibility = View.INVISIBLE
        if (firebaseUser != null) {

            viewModel.checkUser(firebaseUser.uid)

            viewModel.checkUserData.observe(this, Observer { response ->
                when(response) {
                    is Response.NotInitialized -> {
                        binding.mainCons.visibility = View.VISIBLE
                        binding.progCons.visibility = View.INVISIBLE
                        Log.e("MYTAG", "not initialised")
                    }

                    is Response.Loading -> {
                        binding.mainCons.visibility = View.INVISIBLE
                        binding.progCons.visibility = View.VISIBLE
                        Log.e("MYTAG", "loading")

                    }

                    is Response.Success -> {
                        Log.e("MYTAG", "Suceess")
                        if(response.message == "Exists") {
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val intent = Intent(this, DetialsActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                    is Response.Error -> {
                        Log.e("MYTAG", response.toString())
                    }
                }

            })

        }
    }
}



