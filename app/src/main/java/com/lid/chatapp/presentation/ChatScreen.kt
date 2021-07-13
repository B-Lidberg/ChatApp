package com.lid.chatapp


//@Composable
//fun ChatScreen(vm: ChatViewModel = viewModel(), signOut: () -> Unit) {
//
//    val currentMessage by vm.messageText.observeAsState("")
//    val allMessages by vm.allMessages.collectAsState()
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        topBar = {
//            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
//                TopAppBar(
//                    backgroundColor = Color.White,
//                    elevation = 1.dp,
//                    title = {
//                        Text("Chat App")
//                    },
//                    navigationIcon = {
//                        IconButton(onClick = { /*TODO*/ }) {
//                            Icon(
//                                imageVector = Icons.Rounded.ArrowBack,
//                                contentDescription = null
//                            )
//                        }
//                    },
//                    actions = {
//                        IconButton(
//                            onClick = {
//                            Firebase.auth.signOut()
//                            signOut()
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Rounded.ExitToApp,
//                                contentDescription = null
//                            )
//                        }
//                    }
//                )
//            }
//        },
//        floatingActionButton = {
//            PostMessageButton(ChatMessage(content = currentMessage)) {
//                vm.clearCurrentMessage()
//            }
//        }
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Bottom
//        ) {
//            LazyColumn() {
//                items(items = allMessages) { message ->
//                    Text(message.content)
//                }
//            }
//            OutlinedTextField(
//                value = currentMessage,
//                onValueChange = { vm.onMessageTextChange(it) },
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun PostMessageButton(message: ChatMessage, sendMessage: (ChatMessage) -> Unit) {
//    FloatingActionButton(
//        onClick = { sendMessage(message) },
//    ) {
//        Text("Send")
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ChatScreenPreview() {
//    ChatScreen() {
//
//    }
//}