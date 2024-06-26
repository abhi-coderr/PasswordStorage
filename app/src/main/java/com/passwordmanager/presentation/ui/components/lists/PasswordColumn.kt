package com.passwordmanager.presentation.ui.components.lists

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.passwordmanager.R
import com.passwordmanager.core.utils.AppUtils
import com.passwordmanager.core.utils.AppUtils.decrypt
import com.passwordmanager.domain.model.Password
import com.passwordmanager.presentation.ui.theme.BorderColor
import com.passwordmanager.presentation.ui.theme.PasswordColorHidden
import com.passwordmanager.presentation.ui.theme.PurpleGrey40


/**
 * Description: This is the composable custom function on lazy column
 * @author Abhishek Oza
 * @since 24th jan 24
 * @param list
 * @param modifier
 */
@Composable
fun PasswordColumn(
    list: List<Password>,
    modifier: Modifier = Modifier,
    onClick: (Password) -> Unit
) {

    /**
     * Set lazy column on the password list
     */
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(items = list, itemContent = { value ->
            PasswordListItem(password = value) { onClick.invoke(value) }
        })
    }
}

/**
 * Description : This is the composable function which is provide the view of item in lazy column of password
 * @param  password
 * @author Abhishek Oza
 * @since 24th jun 24, 17:30
 */
@Composable
fun PasswordListItem(password: Password, onClick: () -> Unit) {

    /**
     * This is the card for curved layout.
     */
    Card(
        modifier = Modifier
            .padding(vertical = 18.dp)
            .fillMaxWidth()
            .clickable { onClick.invoke() },
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(42.dp)
    ) {
        Row(
            modifier =
            Modifier
                .padding(horizontal = 20.dp, vertical = 18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = password.account, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "************",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 5.dp),
                color = PasswordColorHidden
            )
            Image(
                painter = painterResource(id = R.drawable.ic_forword),
                contentDescription = "forward icon"
            )
        }
    }


}