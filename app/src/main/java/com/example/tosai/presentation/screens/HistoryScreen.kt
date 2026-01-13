package com.example.tosai.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import com.example.tosai.R
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

class HistoryScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val composition = rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.loadinganimation2)
        )
        val navigator = LocalNavigator.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val searchedItem = remember {
            mutableStateOf("")
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0A2463)),
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {navigator?.popUntilRoot()}
//                        ) {
//                            Icon(Icons.Default.ArrowBack,null)
//                        }
//
//                                     },
                    title = { Text("Scan History",
                        modifier = Modifier.padding(start = 20.dp),fontWeight = FontWeight.Bold) }
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 5.dp)
            ) {
                //search bar from here
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = searchedItem.value,
                        onValueChange = {
                            searchedItem.value = it
                         //   viewModel.searchQuery.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color(0xFF0A2463),
                            focusedContainerColor =Color.White   ,
                            unfocusedContainerColor = Color.White,



                            ),
                        leadingIcon = {Icon(Icons.Default.Search,
                            contentDescription = "search-icon", tint = Color.Gray
                        )},
                        placeholder = {Text("Search scan results...", color = Color.DarkGray)},
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            //trigger search action from keyboard
                            onSearch = {
                                // searchedItem.value = ""
                                keyboardController?.hide()
                            }
                        )



                    )
                }
                Spacer(Modifier.height(20.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                   LottieAnimation(
                       composition = composition.value,
                       iterations = LottieConstants.IterateForever,
                       modifier = Modifier.height(250.dp)
                   )
                 //   Spacer(Modifier.height(10.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,

                                )
                            ){
                                append("No scan results here yet!\n")
                            }


                            withStyle(
                                style = SpanStyle(
                                    fontSize = 18.sp,
                                    color = Color(0xFF0A2463),
                                    fontWeight = FontWeight.Bold
                                )
                            ){
                                append("Click  on scan to analyze a new document.")
                            }
                        }, textAlign = TextAlign.Center
                    )
                }
//                LazyColumn (
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ){
//
//                }

            }
        }
    }
}


