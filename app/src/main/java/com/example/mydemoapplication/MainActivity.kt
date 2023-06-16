package com.example.mydemoapplication

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mydemoapplication.ui.theme.MyDemoApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyDemoApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
//                    ArtistCard()
                    FormRegistrasi()
                }
            }
        }
    }
}

// Training Component
@Composable
fun FormRegistrasi() {
    //add dependency to gradle implementation 'androidx.navigation:navigation-compose:2.5.3'
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("details") { DetailsScreen(navController) }
        composable("callapi") { CallingApiScreen(navController) }
        composable("datastore") { DataStoreScreen(navController) }
        composable(
            "details/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStack ->
            DetailsWithArgumentScreen(
                navController,
                name = backStack.arguments?.getString("name") ?: ""
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
//        TextFieldComponent()
//        DropdownComponent()
//        CheckListComponent()
//        RadioComponent()
//        SwitchComponent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent() {
    var name by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var phone by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Text(text = "Hallo ${name.text}")

    TextField(
        value = name,
        label = { Text(text = "Nama") },
        placeholder = { Text(text = "Masukkan Nama") },
        onValueChange = { name = it })

    TextField(
        value = phone,
        label = { Text(text = "No HP") },
        placeholder = { Text(text = "Masukkan Nomor Handphone") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        onValueChange = { phone = it })

    TextField(
        value = email,
        label = { Text(text = "Email") },
        placeholder = { Text(text = "Masukkan Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        onValueChange = { email = it })
}

@Composable
fun RadioComponent() {
    val payment = listOf("Cash", "COD", "Credit Card")
    var selectedItem by remember {
        mutableStateOf(payment[0])
    }

    Column(modifier = Modifier.selectableGroup()) {
        payment.forEach { label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (selectedItem == label),
                        onClick = { selectedItem = label }, role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = (selectedItem == label), onClick = { selectedItem = label })
                Text(text = label)
            }
        }
    }
    var contextToast = LocalContext.current.applicationContext
    Button(onClick = { Toast.makeText(contextToast, "$selectedItem", Toast.LENGTH_SHORT).show() }) {
        Text(text = "Click Me to see what you choose")
    }
}

@Composable
fun CheckListComponent() {
    val fruits = listOf("Apple", "Mango", "Banana")
    Column {
        fruits.forEach { fruitName ->
            var checked by remember {
                mutableStateOf(true)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = checked, onCheckedChange = { checked = it })
                Text(text = fruitName, modifier = Modifier.padding(start = 2.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownComponent() {
    val optionsStatus = listOf("Food", "Bill Payment", "Recharges", "Outing", "Other")
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedStatusOption by remember {
        mutableStateOf(optionsStatus[0])
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(value = selectedStatusOption, onValueChange = {

            }, modifier = Modifier.menuAnchor(), readOnly = true, label = {
                Text("Category")
            }, trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                optionsStatus.forEach { selectedOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectedOption) },
                        onClick = {
                            selectedStatusOption = selectedOption
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
fun SwitchComponent() {
    var switchOn by remember {
        mutableStateOf(false)
    }
    Switch(
        checked = switchOn,
        onCheckedChange = { switchOn_ ->
            switchOn = switchOn_
        }
    )
    if (switchOn)
        Text(text = "On")
    else
        Text(text = "Off")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var name by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Home Screen", style = TextStyle(color = Color.White)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Button(onClick = { navController.navigate("details") }) {
                Text(text = "Click Here to detail")
            }
            Spacer(modifier = Modifier.size(8.dp))
            TextField(
                value = name,
                label = { Text(text = "Nama") },
                placeholder = { Text(text = "Masukkan Nama") },
                onValueChange = { name = it })
            Button(onClick = { navController.navigate("details/${name.text}") }) {
                Text(text = "Click Here to send name")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = { navController.navigate("callapi") }) {
                Text(text = "Click Here to call API")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = { navController.navigate("datastore") }) {
                Text(text = "Click Here to data store")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Detail Screen", style = TextStyle(color = Color.White)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsWithArgumentScreen(navController: NavHostController, name: String) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Detail Argument Screen",
                    style = TextStyle(color = Color.White)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Text(text = "Your name is $name")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataStoreScreen(navController: NavHostController) {
    val tokenValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val userStore = UserStore(LocalContext.current)
    var userToken = userStore.getAccessToken.collectAsState(initial = "")

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Data Store Screen",
                    style = TextStyle(color = Color.White)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Text(text = "user_token: ${userToken.value}")
            TextField(value = tokenValue.value, onValueChange = { tokenValue.value = it })
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    userStore.saveToken(tokenValue.value.text)
                }
            }) {
                Text(text = "Update User Token")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallingApiScreen(navController: NavHostController) {
    val myViewModel = MyViewModel()
    val postItems by myViewModel.data.observeAsState()
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Call API Screen",
                    style = TextStyle(color = Color.White)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Button(onClick = { hitAPI(myViewModel) }) {
                Text(text = "Click to HIT API")
            }
            Spacer(modifier = Modifier.size(8.dp))
            if (postItems?.isNotEmpty() == true) {
                postItems?.forEach {
                    Text(text = "${it.title}")
                    Divider()
                }
            }
        }
    }
}

fun hitAPI(myViewModel: MyViewModel) {
    myViewModel.fetchDataPost()
}

@Composable
fun ArtistCard() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize()
        ) {
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage(
                    image = drawableToBitmap(
                        context = context,
                        drawableResId = R.drawable.alfred_sisley_photo_full
                    )
                )
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Alfred Sisley", style = MaterialTheme.typography.titleMedium)
                    Text(text = "3 minutes ago", style = MaterialTheme.typography.bodySmall)
                }
            }
            Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.alfred_sisley_painting),
                    contentDescription = "Image"
                )
            }

        }
    }
}

@Composable
fun drawableToBitmap(context: Context, drawableResId: Int): ImageBitmap {
    val drawable = ContextCompat.getDrawable(context, drawableResId)
    return drawable?.toBitmap()?.asImageBitmap()!!
}

@Composable
fun CircularImage(image: ImageBitmap) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
    ) {
        Image(
            bitmap = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

// End Training Component
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyDemoApplicationTheme {
        ArtistCard()
    }
}

@Preview(showBackground = true)
@Composable
fun Greeting2Preview() {
    MyDemoApplicationTheme {
        FormRegistrasi()
    }
}
