package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.Fragment
import com.coolkosta.simbirsofttestapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable

class LoginScreenFragment : Fragment() {

    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.auth_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        val emailEditText = view.findViewById<EditText>(R.id.email_edit_text)
        val isEmailValid = emailEditText.textChanges()
            .map { t -> (t.length >= 6) }
            .distinctUntilChanged()
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.password_input_edit_text)
        val isPasswordValid = passwordEditText.textChanges()
            .map { t -> (t.length >= 6) }
            .distinctUntilChanged()
        val button = view.findViewById<Button>(R.id.enter_btn)
        val isSingUpPossible = Observable
            .combineLatest(isEmailValid, isPasswordValid) { e, p ->
                e && p
            }
            .distinctUntilChanged()

        isSingUpPossible
            .subscribe { b ->
                button.isEnabled = b
                button.isClickable = b
            }
            .isDisposed

        button.clicks()
            .subscribe { loginButtonAction() }
            .isDisposed
    }

    private fun loginButtonAction() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HelpFragment.newInstance())
            .commit()
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar?.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance() = LoginScreenFragment()
    }
}