package cs491.project.NJFTRUS

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.stripe.android.PaymentConfiguration
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.stripe.android.*
import com.stripe.android.model.PaymentMethod
import com.stripe.android.view.BillingAddressFields


var RC_SIGN_IN = 1
class StripePayment : AppCompatActivity() {
    private var currentUser: FirebaseUser? = null
    private lateinit var paymentSession: PaymentSession
    private lateinit var selectedPaymentMethod: PaymentMethod
    private val stripe: Stripe by lazy { Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stripe)


        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            // login to firebase
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build())

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
        }

        showUI();
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                currentUser = FirebaseAuth.getInstance().currentUser

                Log.d("Login", "User ${currentUser?.displayName} has signed in.")
                Toast.makeText(applicationContext, "Welcome ${currentUser?.displayName}", Toast.LENGTH_SHORT).show()
                showUI()
            } else {
                Log.d("Login", "Signing in failed!")
                Toast.makeText(applicationContext, response?.error?.message?:"Sign in failed", Toast.LENGTH_LONG).show()
            }
        } else {
            //paymentSession.handlePaymentData(requestCode, resultCode, data ?: Intent())
        }
    }

    @SuppressLint("WrongViewCast")
    public fun showUI() {
        currentUser?.let {
            var loginButton = findViewById<Button>(R.id.loginButton)
            loginButton.visibility = View.INVISIBLE

            var greeting = findViewById<TextView>(R.id.greeting)
            greeting.visibility = View.VISIBLE
            var checkoutSummary = findViewById<TextView>(R.id.checkoutSummary)
            checkoutSummary.visibility = View.VISIBLE
            var payButton = findViewById<Button>(R.id.payButton)
            payButton.visibility = View.VISIBLE
            var paymentmethod = findViewById<TextView>(R.id.paymentmethod)
            paymentmethod.visibility = View.VISIBLE


            greeting.text = "Hello ${it.displayName}"

            //setupPaymentSession()
        }?: run {
            // User does not login
            var loginButton = findViewById<Button>(R.id.loginButton)
            loginButton.visibility = View.VISIBLE
            var greeting = findViewById<TextView>(R.id.greeting)
            greeting.visibility = View.INVISIBLE
            var checkoutSummary = findViewById<TextView>(R.id.checkoutSummary)
            checkoutSummary.visibility = View.INVISIBLE
            var paymentmethod = findViewById<TextView>(R.id.paymentmethod)
            paymentmethod.visibility = View.INVISIBLE
            var payButton = findViewById<Button>(R.id.payButton)
            payButton.visibility = View.INVISIBLE


        }
    }

    private fun setupPaymentSession () {
        // Setup Customer Session
        CustomerSession.initCustomerSession(this, FirebaseEphemeralKeyProvider())
        // Setup a payment session
        paymentSession = PaymentSession(this, PaymentSessionConfig.Builder()
            .setShippingInfoRequired(false)
            .setShippingMethodsRequired(false)
            .setBillingAddressFields(BillingAddressFields.None)
            .setShouldShowGooglePay(true)
            .build())

        paymentSession.init(
            object: PaymentSession.PaymentSessionListener {
                override fun onPaymentSessionDataChanged(data: PaymentSessionData) {
                    Log.d("PaymentSession", "PaymentSession has changed: $data")
                    Log.d("PaymentSession", "${data.isPaymentReadyToCharge} <> ${data.paymentMethod}")

                    if (data.isPaymentReadyToCharge) {
                        Log.d("PaymentSession", "Ready to charge");
                        var payButton = findViewById<Button>(R.id.payButton)
                        payButton.isEnabled = true

                        data.paymentMethod?.let {
                            Log.d("PaymentSession", "PaymentMethod $it selected")
                            var paymentmethod = findViewById<TextView>(R.id.paymentmethod)
                            paymentmethod.text = "${it.card?.brand} card ends with ${it.card?.last4}"
                            selectedPaymentMethod = it
                        }
                    }
                }

                override fun onCommunicatingStateChanged(isCommunicating: Boolean) {
                    Log.d("PaymentSession",  "isCommunicating $isCommunicating")
                }

                override fun onError(errorCode: Int, errorMessage: String) {
                    Log.e("PaymentSession",  "onError: $errorCode, $errorMessage")
                }
            }
        )

    }
}