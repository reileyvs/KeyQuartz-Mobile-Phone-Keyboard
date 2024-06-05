package com.example.test_keyboard_ui_compose

import androidx.compose.foundation.Image
import androidx.compose.ui.res.colorResource
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Preview
@Composable
fun KeyboardScreen() {
    val colorBackground = colorResource(id = R.color.colorBackground)
    val colorBackgroundPressed = colorResource(id = R.color.colorBackgroundPressed)

    //var isShiftLock = false
    var isShifted by remember { mutableStateOf(false) }
    val keysMatrix = arrayOf(
        arrayOf('s', 't', 'o'),
        arrayOf('n', 'a'),
        arrayOf('r', 'e', 'i')
    )
    val swipeKeysUnshifted =
            arrayOf(
                //First [0,1]
                arrayOf('l', 'y', 'p', 'd'),
                arrayOf('L', 'Y', 'P', 'D'),
                //Second [2,3]
                arrayOf('.', '.', '.', '.'),
                arrayOf('.', '.', '.', '.'),
                //Third [4,5]
                arrayOf('c', 'g', 'w', 'h'),
                arrayOf('C', 'G', 'W', 'H'),
                //Fourth [6,7]
                arrayOf('b', 'u', 'z', 'm'),
                arrayOf('B', 'U', 'Z', 'M'),
                //Fifth [8,9]
                arrayOf('q', 'f', 'k', 'v'),
                arrayOf('Q', 'F', 'K', 'V'),
                //Sixth [10,11]
                arrayOf('1', 'x', '3', '2'),
                arrayOf('\'', 'X', '!', '"'),
                //Seventh [12,13]
                arrayOf('4', 'j', '6', '5'),
                arrayOf('?', 'J', '(', '-'),
                //Eighth [14,15]
                arrayOf('7', '0', '9', '8'),
                arrayOf(')', ':', ',', '.')
            )
    val displayArray by remember { mutableStateOf(swipeKeysUnshifted) }

    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val ctx = LocalContext.current
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var occurredRight by remember { mutableStateOf(false) }
    var occurredLeft by remember { mutableStateOf(false) }
    var occurredUp by remember { mutableStateOf(false) }
    var occurredDown by remember { mutableStateOf(false) }
    var absoluteLeft by remember { mutableStateOf(0f) }
    val slideDist = 65f
    Column(
        modifier = Modifier
            .clickable(interactionSource = interactionSource, indication = null) {
                (ctx as IMEService).currentInputConnection.commitText(
                    " ",
                    1
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(onDragEnd = {
                    offsetX = 0f
                    offsetY = 0f
                    absoluteLeft = 0f
                    occurredLeft = false
                    occurredRight = false
                    occurredUp = false
                    occurredDown = false
                }) { _, dragAmount ->
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                    absoluteLeft += dragAmount.x
                    if (offsetX > slideDist && !occurredRight) {
                    //Right and left swipes
                        (ctx as IMEService).currentInputConnection.commitText(
                            "end",
                            3
                        )
                        occurredRight = true
                        occurredLeft = false
                        occurredUp = false
                        occurredDown = false
                        offsetX = 0f
                        offsetY = 0f
                    } else if (offsetX < slideDist * -1) {
                            (ctx as IMEService).currentInputConnection.deleteSurroundingText(
                                if (absoluteLeft < slideDist * -3) {3}
                                else {1},
                                0
                            )
                        occurredRight = false
                        occurredLeft = true
                        occurredUp = false
                        occurredDown = false
                        offsetX = 0f
                        offsetY = 0f
                    }
                    //Up and down swipes
                    if (offsetY < slideDist * -1 && !occurredUp) {
                        isShifted = true
                        /*displayArray = displayArray.map { row ->
                            row.map { char ->
                                charMap[char] ?: char
                            }.toTypedArray()
                        }.toTypedArray()*/
                        occurredRight = false
                        occurredLeft = false
                        occurredUp = true
                        occurredDown = false
                        offsetX = 0f
                        offsetY = 0f
                    } else if (offsetY > slideDist && !occurredDown) {
                        (ctx as IMEService).currentInputConnection.commitText(
                            "\n",
                            1
                        )
                        occurredRight = false
                        occurredLeft = false
                        occurredUp = false
                        occurredDown = true
                        offsetX = 0f
                        offsetY = 0f
                    }
                }
            }
            .background(
                if (pressed.value) {
                    colorBackgroundPressed
                } else {
                    colorBackground
                }
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        keysMatrix.forEach { row ->
            var off = 20.dp
            if(keysMatrix.last().contentEquals(row)) {
                off *= -1
            }
            val modifier = Modifier
            val dist = 40.dp
            if(row.size == 3) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .offset(y = off),
                ) {
                    row.forEach { key ->
                        key(isShifted) {
                            KeyboardKey(
                                keyboardKey = key,
                                modifier = when (key) {
                                    row.first() -> {
                                        Modifier.padding(end = dist)
                                    }

                                    row.last() -> {
                                        Modifier.padding(start = dist)
                                    }

                                    else -> {
                                        Modifier
                                    }
                                },
                                swipekeys = when (row) {
                                    keysMatrix.first() ->
                                        when (key) {
                                            row[0] -> when (isShifted) {
                                                false -> displayArray[0]
                                                else -> displayArray[1]
                                            }

                                            row[1] -> when (isShifted) {
                                                false -> displayArray[2]
                                                else -> displayArray[3]
                                            }

                                            else -> when (isShifted) {
                                                false -> displayArray[4]
                                                else -> displayArray[5]
                                            }
                                        }

                                    else ->
                                        when (key) {
                                            row[0] -> when (isShifted) {
                                                false -> displayArray[10]
                                                else -> displayArray[11]
                                            }

                                            row[1] -> when (isShifted) {
                                                false -> displayArray[12]
                                                else -> displayArray[13]
                                            }

                                            else -> when (isShifted) {
                                                false -> displayArray[14]
                                                else -> displayArray[15]
                                            }
                                        }
                                },
                                isShifted = isShifted
                            )
                            { newShifted ->
                                isShifted = newShifted
                            }
                        }
                    }
                }
            //Middle Row
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                ) {
                    key(isShifted) {
                        KeyboardKey(
                            keyboardKey = row[0],
                            modifier = modifier.padding(end = dist / 2),
                            swipekeys = if (!isShifted) displayArray[6] else displayArray[7],
                            isShifted = isShifted
                        )
                        { newShifted ->
                            isShifted = newShifted
                        }
                        KeyboardKey(
                            keyboardKey = row[1],
                            modifier = modifier.padding(start = dist / 2),
                            swipekeys = if (!isShifted) displayArray[8] else displayArray[9],
                            isShifted = isShifted
                        )
                        { newShifted ->
                            isShifted = newShifted
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun KeyboardKey(
    keyboardKey: Char,
    modifier: Modifier,
    swipekeys: Array<Char>,
    isShifted: Boolean,
    changeShifted: (Boolean) -> Unit,
) {
    val colorShapes = colorResource(id = R.color.colorShapes)
    val colorCircle = colorResource(id = R.color.colorCircle)
    val colorCirclePressed = colorResource(id = R.color.colorCirclePressed)
    val colorMainText = colorResource(id = R.color.colorMainText)

    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val ctx = LocalContext.current
    val imgSize = 75.dp
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var occurredRight by remember { mutableStateOf(false) }
    var occurredLeft by remember { mutableStateOf(false) }
    var occurredUp by remember { mutableStateOf(false) }
    var occurredDown by remember { mutableStateOf(false) }
    val slideDist = 90f
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.pointerInput(Unit) {
            detectDragGestures(onDragEnd = {
                offsetX = 0f
                offsetY = 0f
                occurredLeft = false
                occurredRight = false
                occurredUp = false
                occurredDown = false
                changeShifted(false)
            }) { _, dragAmount ->
                offsetX += dragAmount.x
                offsetY += dragAmount.y
                if (offsetX > slideDist && !occurredRight) {
                    (ctx as IMEService).currentInputConnection.commitText(
                        swipekeys[2].toString(),
                        swipekeys[2].toString().length
                    )
                    occurredRight = true
                    occurredLeft = false
                    occurredUp = false
                    occurredDown = false
                    offsetX = 0f
                    offsetY = 0f
                    changeShifted(false)
                } else if (offsetX < slideDist * -1 && !occurredLeft) {
                    (ctx as IMEService).currentInputConnection.commitText(
                        swipekeys[0].toString(),
                        swipekeys[0].toString().length
                    )
                    occurredRight = false
                    occurredLeft = true
                    occurredUp = false
                    occurredDown = false
                    offsetX = 0f
                    offsetY = 0f
                    changeShifted(false)
                }
                if (offsetY < slideDist * -1 && !occurredUp) {
                    (ctx as IMEService).currentInputConnection.commitText(
                        swipekeys[1].toString(),
                        swipekeys[1].toString().length
                    )
                    occurredRight = false
                    occurredLeft = false
                    occurredUp = true
                    occurredDown = false
                    offsetX = 0f
                    offsetY = 0f
                    changeShifted(false)
                } else if (offsetY > slideDist && !occurredDown) {
                    (ctx as IMEService).currentInputConnection.commitText(
                        swipekeys[3].toString(),
                        swipekeys[3].toString().length
                    )
                    occurredRight = false
                    occurredLeft = false
                    occurredUp = false
                    occurredDown = true
                    offsetX = 0f
                    offsetY = 0f
                    changeShifted(false)
                }
            }
        }
    ) {
        Image(
            modifier = modifier
                .size(imgSize)
                .rotate(45f)
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable(interactionSource = interactionSource, indication = null) {
                    (ctx as IMEService).currentInputConnection.commitText(
                        keyboardKey.toString(),
                        keyboardKey.toString().length
                    )
                    //changeShifted(false)
                },
            painter = painterResource(id = R.drawable.white_square),
            contentDescription = "White Square",
            colorFilter = ColorFilter.tint(colorShapes),

        )
        SwipeLetters(
            modifier = modifier,
            keys = swipekeys,
            dist = imgSize,
            isShifted = isShifted
        )
        val size = imgSize - 18.dp
        Box(
            modifier = modifier
                .background(
                    if (pressed.value) {
                        colorCirclePressed
                    } else {
                        colorCircle
                    }, shape = CircleShape
                )
                .size(
                    if (pressed.value) {
                        size - 5.dp
                    } else {
                        size
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text =
                when (isShifted) {
                    keyboardKey.isLetter() -> keyboardKey.uppercase()
                    else -> keyboardKey.toString()
                },
                Modifier
                    .clickable(interactionSource = interactionSource, indication = null) {
                        (ctx as IMEService).currentInputConnection.commitText(
                            when (isShifted) {
                                keyboardKey.isLetter() -> keyboardKey.uppercase()
                                else -> keyboardKey.toString()
                            },
                            keyboardKey.toString().length

                        )
                        changeShifted(false)
                    }
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    ),
                fontSize = 4.em,
                textAlign = TextAlign.Center,
                color = colorMainText,
                //fontFamily = FontFamily.Monospace,

                )
        }
    }
}

@Composable
fun SwipeLetters(
    modifier: Modifier,
    keys: Array<Char>,
    dist: Dp,
    isShifted: Boolean,
    ) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(dist)
    ) {
        val colorSwipeText = colorResource(id = R.color.colorSwipeText)

        val lowerCaseOffset = if(!isShifted) { 1.dp } else { 0.dp }
        val off = (dist+1.dp)/2
        val negative = off * -1
        //Left
        Text(
            keys[0].toString(),
            modifier = Modifier.offset(x = negative, y = lowerCaseOffset),
            color = colorSwipeText,
            //fontFamily = FontFamily.Monospace
        )
        //Right
        Text(
            keys[2].toString(),
            modifier = Modifier.offset(x = off),
            color = colorSwipeText,
            //fontFamily = FontFamily.Monospace
        )
        //Up
        Text(
            keys[1].toString(),
            modifier = Modifier.offset(y = negative-0.5.dp),
            color = colorSwipeText,
            //fontFamily = FontFamily.Monospace
        )
        //Down
        Text(
            keys[3].toString(),
            modifier = Modifier.offset(y = off-0.5.dp),
            color = colorSwipeText,
            //fontFamily = FontFamily.Monospace
        )
    }
}

@Preview
@Composable
fun PreviewSwipeLetters() {
    val keys = arrayOf("A", "B", "C", "D")
    val dist = 90.dp
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(dist)
    ) {
        val off = (dist-12.dp)/2
        val negative = off * -1
        //Left
        Text(
            keys[0],
            modifier = Modifier.offset(x = negative),
            color = Color.Magenta
        )
        //Right
        Text(
            keys[2],
            modifier = Modifier.offset(x = off),
            color = Color.Magenta
        )
        //Up
        Text(
            keys[1],
            modifier = Modifier.offset(y = negative + 2.dp),
            color = Color.Magenta
        )
        //Down
        Text(
            keys[3],
            modifier = Modifier.offset(y = off - 2.dp),
            color = Color.Magenta
        )
    }
}