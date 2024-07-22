package com.coolkosta.simbirsofttestapp.presentation.screen.loginFragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.presentation.ui.theme.Black54
import com.coolkosta.simbirsofttestapp.presentation.ui.theme.Leaf
import com.coolkosta.simbirsofttestapp.presentation.ui.theme.SimbirSoftTestAppTheme

@Composable
fun LoginFragmentDisplay(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        SocialMediaAuth()
        AppAuthorization(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = com.coolkosta.core.R.dimen.spacing_l),
                    end = dimensionResource(id = com.coolkosta.core.R.dimen.spacing_l),
                    top = dimensionResource(id = com.coolkosta.core.R.dimen.margin_xs)
                ),
            onClick = onClick
        )
    }
}

@Composable
fun SocialMediaAuth(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = com.coolkosta.core.R.dimen.margin_xs))
    ) {
        Text(text = stringResource(id = R.string.auth_social_media_text))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = com.coolkosta.core.R.dimen.margin_xs)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(R.drawable.ic_auth_vk),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.ic_auth_facebook),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.ic_auth_ok),
                contentDescription = null
            )
        }
    }
}

@Composable
fun AppAuthorization(
    modifier: Modifier = Modifier,
    loginFragmentViewModel: LoginFragmentViewModel = viewModel(),
    onClick: () -> Unit
) {
    val loginState by loginFragmentViewModel.loginState.collectAsState()
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var isEnabled by rememberSaveable { mutableStateOf(false) }
    isEnabled = loginState.currentEmail.length >= 6 && loginState.currentPassword.length >= 6

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = stringResource(id = R.string.auth_app_text))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = com.coolkosta.core.R.dimen.spacing_xxl_2)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(KeyboardActions.Default.onNext),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Black54,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            value = loginState.currentEmail,
            onValueChange = { loginFragmentViewModel.sendEvent(LoginEvent.EmailTextChanged(it)) },
            label = { Text(text = stringResource(id = R.string.e_mail)) }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = com.coolkosta.core.R.dimen.spacing_xxl_2)),
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(KeyboardActions.Default.onDone),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Black54,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            value = loginState.currentPassword,
            onValueChange = { loginFragmentViewModel.sendEvent(LoginEvent.PasswordTextChanged(it)) },
            label = {
                Text(
                    text = stringResource(id = R.string.password),
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) painterResource(id = R.drawable.ic_close) else painterResource(
                            id = R.drawable.ic_open
                        )
                    Icon(painter = visibilityIcon, contentDescription = null)
                }
            }
        )
        Button(
            enabled = isEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = com.coolkosta.core.R.dimen.spacing_xxl_2)),
            onClick = onClick,
            colors = ButtonDefaults
                .buttonColors(containerColor = Leaf),
            shape = RoundedCornerShape(dimensionResource(id = com.coolkosta.core.R.dimen.corner_radius))
        ) {
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = stringResource(id = R.string.enter_text_btn)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = com.coolkosta.core.R.dimen.spacing_l)),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                text = stringResource(id = R.string.forgot_pass_text),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(id = R.string.registration_label_tv),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginFragmentDisplayPreview() {
    SimbirSoftTestAppTheme {
        LoginFragmentDisplay(onClick = {})
    }
}
