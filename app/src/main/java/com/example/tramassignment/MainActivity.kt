package com.example.tramassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.tramassignment.ui.theme.TramAssignmentTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "database-User").build()
//        val userDao = db.userDao()
        val userDao = UserDatabase.getDatabase(applicationContext).userDao()

        setContent {

            TramAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserApp(userDao)
//                    val allUsers by userDao.getAllUsers().observeAsState(initial = listOf())
//                    UserForm(onSubmit = { firstName, lastName, email, phoneNumber ->
//                        val user = User(firstName = firstName, lastName = lastName, eMail = email, phoneNumber = phoneNumber)
//                        lifecycleScope.launch{
//                            userDao.insertUser(user)
//                        }
//                    })
//                    UserList(users = allUsers)
//                    Greeting(name = "Andrew")
                }
            }
        }
    }
}

//@Composable
//fun UserList(users: List<User>){
//    LazyColumn {
//        items(users) {user ->
//            Text("Name: ${user.firstName} ${user.lastName}, Email: ${user.eMail}, Phone: ${user.phoneNumber}")
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UserForm(onSubmit: (String, String, String, String) -> Unit){
//    var firstName by remember { mutableStateOf("") }
//    var lastName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var phoneNumber by remember { mutableStateOf("") }
//
//    Column {
//        OutlinedTextField(value = firstName,
//            onValueChange = {firstName = it},
//            label = { Text("First Name") },
//            modifier = Modifier
//                .background(Color.White),
//            shape = RoundedCornerShape(8.dp))
//
//        OutlinedTextField(value = lastName,
//            onValueChange = {lastName = it},
//            label = { Text("Last Name") })
//
//        OutlinedTextField(value = email,
//            onValueChange = {email = it},
//            label = { Text("Email") })
//
//        OutlinedTextField(value = phoneNumber,
//            onValueChange = {phoneNumber = it},
//            label = { Text("Phone Number") })
//
//        Button(onClick = {
//            onSubmit(firstName, lastName, email, phoneNumber)
//            // Reset the form after submission
//            firstName = ""
//            lastName = ""
//            email = ""
//            phoneNumber = ""
//        }) {
//            Text("Add User")
//        }
//
//    }
//}
//
//@Preview
//@Composable
//fun UserFormPreview(){
//    TramAssignmentTheme {
//        UserForm(onSubmit = {
//            _, _, _, _ ->
//        })
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputForm(userDao: UserDao){
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Step 2
    val coroutineScope = rememberCoroutineScope()

    fun validateAndAddUser() {
        coroutineScope.launch {
            when {
                firstName.isBlank() -> errorMessage = "First Name cannot be empty"
                lastName.isBlank() -> errorMessage = "Last Name cannot be empty"
                email.isBlank() -> errorMessage = "Email cannot be empty"
                !email.contains("@") -> errorMessage = "Email must contain @"
                phoneNumber.isBlank() -> errorMessage = "Phone Number cannot be empty"
                phoneNumber.any { !it.isDigit() } -> errorMessage = "Phone Number must be numeric"
                else -> {
                    errorMessage = ""
                    val user = User(firstName = firstName, lastName = lastName, eMail = email, phoneNumber = phoneNumber)
                    userDao.insertUser(user)
                    firstName = ""
                    lastName = ""
                    email = ""
                    phoneNumber = ""
                }
            }
        }
//        when {
//            firstName.isBlank() -> errorMessage = "First Name cannot be empty"
//            lastName.isBlank() -> errorMessage = "Last Name cannot be empty"
//            email.isBlank() -> errorMessage = "Email cannot be empty"
//            phoneNumber.isBlank() -> errorMessage = "Phone Number cannot be empty"
//            else -> {
//                errorMessage = ""
//                val user = User(firstName = firstName, lastName = lastName, eMail = email, phoneNumber = phoneNumber)
//                userDao.insertUser(user)
//                firstName = ""
//                lastName = ""
//                email = ""
//                phoneNumber = ""
//            }
//        }
    }

    Column {
        Text(
            text = "Create A User Profile",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(vertical = 8.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp
            ),
            textAlign = TextAlign.Center
        )

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)) {
            Text(text = "First Name", modifier = Modifier
                .padding(start = 4.dp)
                .weight(0.5f))
            OutlinedTextField(value = firstName, onValueChange = {firstName = it}, modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(2f)
                .background(Color.White), maxLines = 1,
                singleLine = true)
        }

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)) {
            Text(text = "Last Name", modifier = Modifier
                .padding(start = 4.dp)
                .weight(0.5f))
            OutlinedTextField(value = lastName, onValueChange = {lastName = it}, modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(2f)
                .background(Color.White), maxLines = 1,
                singleLine = true)
        }

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)) {
            Text(text = "Email", modifier = Modifier
                .padding(start = 4.dp)
                .weight(0.5f))
            OutlinedTextField(value = email, onValueChange = {email = it}, modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(2f)
                .background(Color.White), maxLines = 1,
                singleLine = true)
        }

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)) {
            Text(text = "Phone Number", modifier = Modifier
                .padding(start = 4.dp)
                .weight(0.5f))
            OutlinedTextField(value = phoneNumber, onValueChange = {phoneNumber = it}, modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(2f)
                .background(Color.White), maxLines = 1,
                singleLine = true)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red)
            }else {
                Spacer(modifier = Modifier.weight(1f))
            }
            Button(
                onClick = { validateAndAddUser() },
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = Color.white,
//                    contentColor = Color.YourContentColor
//                ),
//                modifier = Modifier.border(
//                    width = 1.dp,
//                    color = Color.YourBorderColor,
//                    shape = RoundedCornerShape(4.dp)
//                )
            ) {
                Text("Add User")
            }
        }

    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CustomOutlinedTextField() {
//    var text by remember { mutableStateOf("") }
//    val isFieldActivated = remember { mutableStateOf(false) }
//
//    OutlinedTextField(
//        value = text,
//        onValueChange = {
//            text = it
//            if (it.isNotEmpty()) {
//                isFieldActivated.value = true
//            }
//        },
//        label = {
//            if (!isFieldActivated.value && text.isEmpty()) {
//                // 当输入框未被激活且为空时，显示在输入框外的标签
//                Text("First Name", Modifier.padding(top = 8.dp, start = 16.dp))
//            } else {
//                // 默认行为，当输入框激活或有内容时，显示在输入框内的标签
//                Text("First Name")
//            }
//        }
//    )
//}


@Composable
fun UserList(userDao: UserDao) {
    val users = userDao.getAllUsers().observeAsState(listOf())
    LazyColumn {
        items(users.value) { user ->
            UserItem(user, userDao)
        }
    }
}

@Composable
fun UserItem(user: User, userDao: UserDao) {
    var isEditing by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    // Step 3
    val coroutineScope = rememberCoroutineScope()
    if (isEditing) {
        UserEditForm(user, userDao) { isEditing = false }
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Name: ${user.firstName} ${user.lastName}, Email: ${user.eMail}, Phone: ${user.phoneNumber}",
                modifier = Modifier.weight(1f))

            IconButton(onClick = { isEditing = true }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Blue)
            }

            IconButton(onClick = {
                showDialog = true
//                userDao.deleteUser(user)
                // Step 3
//                coroutineScope.launch {
//                    userDao.deleteUser(user)
//                }

            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(text = "Confirm Delete")
                },
                text = {
                    Text("Are you sure you want to delete?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            coroutineScope.launch {
                                userDao.deleteUser(user)
                            }
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditForm(user: User, userDao: UserDao, onDone: () -> Unit) {
    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var email by remember { mutableStateOf(user.eMail) }
    var phoneNumber by remember { mutableStateOf(user.phoneNumber) }
    var errorMessage by remember { mutableStateOf("") }

    // Step 3
    val coroutineScope = rememberCoroutineScope()

    fun validateForm(): Boolean {
        return when {
            firstName.isBlank() -> { errorMessage = "First Name cannot be empty"; false }
            lastName.isBlank() -> { errorMessage = "Last Name cannot be empty"; false }
            email.isBlank() -> { errorMessage = "Email cannot be empty"; false }
            !email.contains("@") -> { errorMessage = "Email must contain @"; false }
            phoneNumber.any { !it.isDigit() } -> { errorMessage = "Phone Number must be numeric"; false }
            else -> true
        }
    }

    Column {
        TextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name")},
            maxLines = 1, singleLine = true)

        TextField(value = lastName, onValueChange = {lastName = it}, label = {Text("Last Name")},
            maxLines = 1, singleLine = true)

        TextField(value = email, onValueChange = {email = it}, label = {Text("Email")},
            maxLines = 1, singleLine = true)

        TextField(value = phoneNumber, onValueChange = {phoneNumber = it}, label = {Text("PhoneNumber")},
            maxLines = 1, singleLine = true)

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red)
        }

        Button(onClick = {
//            coroutineScope.launch {
//                userDao.updateUser(user.copy(firstName = firstName, lastName = lastName, eMail = email, phoneNumber = phoneNumber))
//                onDone()
//            }

            if(validateForm()){
                coroutineScope.launch {
                    userDao.updateUser(user.copy(firstName = firstName, lastName = lastName, eMail = email, phoneNumber = phoneNumber))
                    onDone()
                }
            }

//            userDao.updateUser(user.copy(firstName = firstName, lastName = lastName, eMail = email, phoneNumber = phoneNumber))
//            onDone()
        }) {
            Text("Save Changes")
        }
    }
}

@Composable
fun UserApp(userDao: UserDao) {
    Column {
        UserInputForm(userDao)
        UserList(userDao)
    }
}

@Preview
@Composable
fun PreviewUserApp() {
    val context = LocalContext.current
    val userDao = UserDatabase.getDatabase(context).userDao()
    UserApp(userDao)
}