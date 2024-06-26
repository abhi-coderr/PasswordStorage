package com.passwordmanager.presentation.ui.components.common_component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.passwordmanager.presentation.ui.theme.BlackColorForTV
import com.passwordmanager.presentation.ui.theme.BorderColor
import com.passwordmanager.presentation.ui.theme.ButtonBlack
import com.passwordmanager.presentation.ui.theme.HintColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    sheetState: SheetState,
    content: @Composable () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize(),
        shape = MaterialTheme.shapes.large,
        onDismissRequest = onDismiss
    ) { content() }
}

@Composable
fun CustomTextField(
    initialText: String,
    onTextChange: (String) -> Unit,
    textSize: TextUnit = 16.sp,
    textColor: Color = Black,
    borderColor: Color = BorderColor,
    hint: String = String(),
    hintColor: Color = HintColor,
    imeAction: ImeAction = ImeAction.Done,
    modifier: Modifier = Modifier
) {

    val textState = remember {
        mutableStateOf(initialText)
    }

    BasicTextField(
        value = textState.value,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        onValueChange = {
            textState.value = it
            onTextChange(it)
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, shape = RoundedCornerShape(5.dp))
            .background(White, shape = RoundedCornerShape(5.dp))
            .padding(10.dp),
        textStyle = TextStyle(fontSize = textSize, color = textColor),
        decorationBox = { innerTextField ->
            if (textState.value.isEmpty()) {
                Text(
                    text = hint,
                    style = TextStyle(color = hintColor)
                )
            }
            innerTextField()
        }

    )

}

@Composable
fun CustomCapsuleButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier,
    color: Color = ButtonBlack
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = White
        ),
        modifier = modifier
            .padding(8.dp)
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TransparentTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var internalText by remember { mutableStateOf(text) }
    BasicTextField(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth(),
        value = internalText,
        onValueChange = {
            internalText = it
            onTextChange(it)
        }, textStyle = TextStyle(
            color = BlackColorForTV, fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
        )
    )
}

@Composable
fun TransparentTextFieldPassword(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    passwordVisibilityIcon: Painter
) {
    var internalText by remember { mutableStateOf(text) }

    var passwordVisible by remember { mutableStateOf(false) }
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        BasicTextField(
            modifier = modifier
                .background(Color.Transparent)
                .fillMaxWidth(0.9f),
            value = internalText,
            onValueChange = {
                internalText = it
                onTextChange(it)
            }, textStyle = TextStyle(
                color = BlackColorForTV, fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
            ),
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }

        )

        IconButton(
            onClick = { passwordVisible = !passwordVisible }
        ) {
            Icon(
                painter = passwordVisibilityIcon,
                contentDescription = if (passwordVisible) "Hide password" else "Show password"
            )
        }
    }

}
