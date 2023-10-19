package com.creamydark.avz.presentation.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.presentation.ui.navgraphs.Screen
import com.creamydark.avz.presentation.viewmodels.RegisterViewModel
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterPart2Screen(
    navHostController: NavHostController,
    viewModel: RegisterViewModel
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val usecaseStateDialog = rememberUseCaseState(
        visible = false,
        true,
        onCloseRequest = {

        }
    )

    val context = LocalContext.current

    val localDate = viewModel._localDate.collectAsState()

    val userType = viewModel._userType.collectAsState()

    val formattedDate = localDate.value.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))

    val registerResult = viewModel._registerResult.collectAsState().value
    val errorTExt = viewModel._errorText.collectAsState().value


    if(errorTExt.isNotEmpty()){
        Toast.makeText(context, errorTExt,Toast.LENGTH_SHORT).show()
    }

    if (registerResult){
        Toast.makeText(context, "Register Successful",Toast.LENGTH_SHORT).show()
        navHostController.navigate(Screen.HomeGraph.route){
            launchSingleTop = true
        }
    }

    DialogDate(useCaseState = usecaseStateDialog, localDate = localDate.value, onSelected = {
        viewModel.editLocalDateForBday(it)
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Register", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            readOnly = true,
            value = formattedDate,
            onValueChange = { },
            label = { Text("Birthday") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    usecaseStateDialog.show()
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        UserTypeSelection(isStudent = userType.value, onUserTypeSelected = {
            viewModel.editUserType(it)
        })
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.registerUserExtraData()

//                Log.d("RegisterPart2Screen", "RegisterPart2Screen:onClick ")
                keyboardController?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Register")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDate(useCaseState: UseCaseState,localDate: LocalDate,onSelected:(LocalDate)->Unit) {
//    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now().minusDays(3)) }
    CalendarDialog(
        state = useCaseState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date(selectedDate = localDate){ newDate ->
            onSelected(newDate)
        }
    )
}
@Composable
fun UserTypeSelection(
    isStudent: Boolean,
    onUserTypeSelected: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Select User Type:",
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Checkbox(
                checked = isStudent,
                onCheckedChange = {
                    onUserTypeSelected(it)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            Text(
                text = "Student",
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = !isStudent,
                onCheckedChange = {
                    onUserTypeSelected(!it)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            Text(
                text = "Teacher",
                modifier = Modifier.weight(1f)
            )
        }
    }
}