package com.passwordmanager.presentation.ui.screens.activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.passwordmanager.R
import com.passwordmanager.domain.model.Password
import com.passwordmanager.presentation.ui.components.common_component.CustomBottomSheet
import com.passwordmanager.presentation.ui.components.common_component.CustomCapsuleButton
import com.passwordmanager.presentation.ui.components.common_component.CustomTextField
import com.passwordmanager.presentation.ui.components.common_component.TransparentTextField
import com.passwordmanager.presentation.ui.components.common_component.TransparentTextFieldPassword
import com.passwordmanager.presentation.ui.components.lists.PasswordColumn
import com.passwordmanager.presentation.ui.screens.viewmodels.MainViewModel
import com.passwordmanager.presentation.ui.theme.BackScreen
import com.passwordmanager.presentation.ui.theme.ButtonRed
import com.passwordmanager.presentation.ui.theme.FabColor
import com.passwordmanager.presentation.ui.theme.HintColor
import com.passwordmanager.presentation.ui.theme.PasswordManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = BackScreen),
                ) { MainLayout(this, viewModel = mainViewModel, lifecycleScope) }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    context: Context,
    viewModel: MainViewModel,
    lifecycleCoroutineScope: LifecycleCoroutineScope
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val sheetState = rememberModalBottomSheetState()

    val editSheetState = rememberModalBottomSheetState()

    val isSheetOpen = rememberSaveable { mutableStateOf(false) }

    val isEditSheetOpen = rememberSaveable { mutableStateOf(false) }

    val accountTF = remember { mutableStateOf(String()) }
    val emailTF = remember { mutableStateOf(String()) }
    val passwordTF = remember { mutableStateOf(String()) }

    val editAccountTF = remember { mutableStateOf(viewModel.userPassword.value?.account) }
    val editEmailTF = remember { mutableStateOf(viewModel.userPassword.value?.email) }
    val userId = remember { mutableStateOf(viewModel.userPassword.value?.id) }
    val password = remember { mutableStateOf<Password?>(null) }
    val editPasswordTF = remember { mutableStateOf(viewModel.userPassword.value?.password) }

    val scope = rememberCoroutineScope()

    password.value =
        password.value?.copy(
            email = viewModel.userPassword.collectAsState().value?.email.toString(),
            password = viewModel.userPassword.collectAsState().value?.password.toString(),
            account = viewModel.userPassword.collectAsState().value?.account.toString(),
            id = viewModel.userPassword.collectAsState().value?.id ?: 0
        )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Surface(shadowElevation = 1.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Password Storage",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        floatingActionButton = {
            Box(modifier = Modifier.fillMaxSize()) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),
                    containerColor = FabColor,
                    onClick = {
                        scope.launch {
                            isSheetOpen.value = true
                        }
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null
                    )
                }
            }
        }
    ) { value ->

        if (isSheetOpen.value) {
            CustomBottomSheet(
                sheetState = sheetState,
                onDismiss = {
                    scope.launch {
                        isSheetOpen.value = false
                    }
                },
                content = {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        CustomTextField(
                            initialText = accountTF.value,
                            onTextChange = { inputText ->
                                accountTF.value = inputText
                            },
                            hint = "Account Name",
                            modifier = Modifier
                                .padding(top = 36.dp),
                            imeAction = ImeAction.Next
                        )

                        CustomTextField(
                            initialText = emailTF.value,
                            onTextChange = { inputText ->
                                emailTF.value = inputText
                            },
                            hint = "Username/ Email",
                            modifier = Modifier
                                .padding(top = 22.dp),
                            imeAction = ImeAction.Next

                        )

                        CustomTextField(
                            initialText = passwordTF.value,
                            onTextChange = { inputText ->
                                passwordTF.value = inputText
                            },
                            hint = "Password",
                            modifier = Modifier
                                .padding(top = 22.dp)
                        )

                        CustomCapsuleButton(
                            onClick = {
                                lifecycleCoroutineScope.launch {

                                    if (accountTF.value.isEmpty() && emailTF.value.isEmpty() && passwordTF.value.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            "Kindly fill all fields.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        viewModel.addPassword(
                                            Password(
                                                account = accountTF.value,
                                                email = emailTF.value,
                                                password = passwordTF.value
                                            )
                                        )
                                        scope.launch {
                                            delay(1000)
                                            isSheetOpen.value = false
                                            sheetState.hide()

                                            accountTF.value = String()
                                            emailTF.value = String()
                                            passwordTF.value = String()
                                        }
                                    }
                                }
                            },
                            text = "Add New Account",
                            modifier = Modifier
                                .padding(top = 28.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            )
        }

        if (isEditSheetOpen.value) {
            editAccountTF.value = viewModel.userPassword.collectAsState().value?.account ?: String()
            editEmailTF.value = viewModel.userPassword.collectAsState().value?.email ?: String()
            editPasswordTF.value = viewModel.userPassword.collectAsState().value?.password ?: String()
            CustomBottomSheet(
                sheetState = editSheetState,
                onDismiss = {
                    scope.launch {
                        isEditSheetOpen.value = false
                        editEmailTF.value = String()
                        editAccountTF.value = String()
                        editPasswordTF.value = String()
                    }
                },
                content = {

                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                        Text(
                            text = "Account Details",
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp,
                            color = Color.Blue, modifier = Modifier.padding(top = 17.dp)
                        )
                        Text(
                            text = "Account Type",
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = HintColor, modifier = Modifier.padding(top = 24.dp)
                        )
                        TransparentTextField(
                            text = editAccountTF.value.orEmpty(),
                            onTextChange = {
                                editAccountTF.value = it
                                password.value = password.value?.copy(account = it)
                            },
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = "Username/ Email",
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = HintColor, modifier = Modifier.padding(top = 24.dp)
                        )
                        TransparentTextField(
                            text = editEmailTF.value.orEmpty(),
                            onTextChange = {
                                editEmailTF.value = it
                                password.value = password.value?.copy(email = it)
                            },
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = "Password",
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = HintColor, modifier = Modifier.padding(top = 24.dp)
                        )
                        TransparentTextFieldPassword(
                            text = editPasswordTF.value.orEmpty(),
                            onTextChange = {
                                editPasswordTF.value = it
                                password.value = password.value?.copy(password = it)
                            },
                            modifier = Modifier.padding(top = 2.dp),
                            passwordVisibilityIcon = painterResource(id = R.drawable.ic_hide_eye)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 28.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomCapsuleButton(
                                onClick = {
                                    lifecycleCoroutineScope.launch {
                                        viewModel.apply {

                                            editPassword(
                                                Password(
                                                    id = userPassword.value?.id ?: 0,
                                                    account = editAccountTF.value.orEmpty(),
                                                    email = editEmailTF.value.orEmpty(),
                                                    password = editPasswordTF.value.orEmpty()
                                                )
                                            ).also { isEditSheetOpen.value = false }
                                        }
                                    }
                                },
                                text = "Edit",
                                modifier = Modifier.weight(1f)
                            )
                            CustomCapsuleButton(
                                onClick = {
                                    lifecycleCoroutineScope.launch {
                                        lifecycleCoroutineScope.launch {
                                            viewModel.apply {
                                                deletePassword(
                                                    Password(
                                                        id = userPassword.value?.id ?: 0,
                                                        account = editAccountTF.value.orEmpty(),
                                                        email = editEmailTF.value.orEmpty(),
                                                        password = editPasswordTF.value.orEmpty()
                                                    )
                                                ).also { isEditSheetOpen.value = false }
                                            }
                                        }
                                    }
                                },
                                text = "Delete",
                                modifier = Modifier.weight(1f), color = ButtonRed
                            )
                        }

                    }
                }
            )
        }

        PasswordColumn(
            list = viewModel.collectData.collectAsState().value,
            Modifier
                .fillMaxSize()
                .padding(value)
                .background(color = BackScreen)
        ) {
            lifecycleCoroutineScope.launch {
                viewModel.getPassword(it.id) { isEditSheetOpen.value = true }
            }
        }
    }

}



