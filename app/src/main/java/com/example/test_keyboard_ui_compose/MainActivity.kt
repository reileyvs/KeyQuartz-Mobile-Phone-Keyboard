package com.example.test_keyboard_ui_compose

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_keyboard_ui_compose.ui.theme.Test_Keyboard_UI_ComposeTheme
import splitties.systemservices.inputMethodManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test_Keyboard_UI_ComposeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column {
                        Options()
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            isSystemInDarkTheme()
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Options() {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        val ctx = LocalContext.current
        Text(text = "Compose Keyboard")
        val (text, setValue) = remember { mutableStateOf(TextFieldValue("Try here")) }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            ctx.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }) {
            Text(text = "Enable IME")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            inputMethodManager.showInputMethodPicker()
        }) {
            Text(text = "Select IME")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

/*
data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.octagon_removebg_preview),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                //Image size
                .size(40.dp)
                //Image shape
                .clip(CircleShape)
                //Color
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        //Horizontal space between img and col
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        //surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = "This",
        )
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )

            //Vertical space between lines
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 2.dp,
                //Gradual color change
                color = surfaceColor,
                //animateContentSize changes size
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) {message ->
            MessageCard(message)
        }
    }
}

//@Preview
@Composable
fun PreviewConversation() {
    Test_Keyboard_UI_ComposeTheme {
        Conversation(SampleData.conversationSample)
    }
}

//@Preview(name = "Light Mode")
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Dark Mode",
//)
@Composable
fun PreviewMessageCard() {
   Test_Keyboard_UI_ComposeTheme {
       Surface {
           MessageCard(
               msg = Message("Lexi", "Hey look at this!")
           )
       }
   }
}

*/
/**
 * SampleData for Jetpack Compose Tutorial
 *//*

object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Lexi",
            "Test...Test...Test..."
        ),
        Message(
            "Lexi",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim()
        ),
        Message(
            "Lexi",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim()
        ),
        Message(
            "Lexi",
            "Searching for alternatives to XML layouts..."
        ),
        Message(
            "Lexi",
            """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim()
        ),
        Message(
            "Lexi",
            "It's available from API 21+ :)"
        ),
        Message(
            "Lexi",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Lexi",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Lexi",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Lexi",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Lexi",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Lexi",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Lexi",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}

@Composable
fun KeyLayout() {
    Box {
        Column {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                OctagonImage()
                OctagonImage()
                OctagonImage()
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                OctagonImage()
                OctagonImage()
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                OctagonImage()
                OctagonImage()
                OctagonImage()
            }
        }
    }
}

@Composable
fun OctagonImage() {
        Image(
            painter = painterResource(id = R.drawable.octagon_removebg_preview),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                //Image size
                .size(40.dp)
                .padding(horizontal = 6.dp)
                //Image shape
//                .clip(CircleShape)
//                //Color
//                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
}

@Preview
@Composable
fun PreviewKeyLayout() {
    KeyLayout()
}*/
