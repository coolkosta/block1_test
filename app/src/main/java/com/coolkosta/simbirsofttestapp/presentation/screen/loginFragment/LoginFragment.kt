@file:OptIn(ExperimentalMaterial3Api::class)

package com.coolkosta.simbirsofttestapp.presentation.screen.loginFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.coolkosta.help.presentation.screen.HelpFragment
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.core.presentation.ui.theme.SimbirSoftTestAppTheme
import com.coolkosta.core.presentation.ui.theme.TurtleGreen
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SimbirSoftTestAppTheme {
                    LoginScreenFragmentComposable(navigateBack = { requireActivity().finish() },
                        onClick = { loginButtonAction() }
                    )
                }
            }
        }
    }

    private fun loginButtonAction() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HelpFragment.newInstance())
            .commit()
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar?.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}

@Composable
fun LoginScreenFragmentComposable(navigateBack: () -> Unit, onClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LoginScreenTopAppBar(
                navigateBack = navigateBack
            )
        }
    ) {
        LoginFragmentDisplay(
            modifier = Modifier.padding(it),
            onClick = onClick
        )
    }
}

@Composable
fun LoginScreenTopAppBar(navigateBack: () -> Unit, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TurtleGreen
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(id = R.string.authorization_label)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    SimbirSoftTestAppTheme {
        LoginScreenFragmentComposable(navigateBack = {}, onClick = {})
    }
}