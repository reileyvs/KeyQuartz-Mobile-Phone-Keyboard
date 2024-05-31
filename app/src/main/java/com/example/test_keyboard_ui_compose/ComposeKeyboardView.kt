package com.example.test_keyboard_ui_compose

import androidx.compose.runtime.Composable
import android.content.Context
import androidx.compose.ui.platform.AbstractComposeView

class ComposeKeyboardView(context: Context) : AbstractComposeView(context) {
    
    @Composable
    override fun Content() {
        KeyboardScreen()
    }
}