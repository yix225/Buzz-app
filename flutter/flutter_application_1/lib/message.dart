// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables

import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_application_1/my_function.dart';

class message extends StatelessWidget {
  @override
  late Timer _timer;
  bool _isButtonPressed = false;

  void _startTimer() {
    // Start a timer for 5 seconds
    _timer = Timer(Duration(seconds: 5), () {
      _isButtonPressed = false;
    });
  }

  void _onButtonPressed(int mid) {
    if (_isButtonPressed) {
      _isButtonPressed = false;
      update_unLikes(mid);
      print('You unlike this message');
      _timer = Timer(Duration(seconds: 0), () {});
    } else {
      // Perform the first action if the button is pressed for the first time or after 5 seconds
      update_Likes(mid);
      _isButtonPressed = true;
      print('You like this message');
      _startTimer();
    }
  }

  Widget build(BuildContext context) {
    final List<String> message = ModalRoute.of(context)!.settings.arguments as List<String>;
    return Scaffold(
      appBar: AppBar(
        title: Text('Message Info'),
      ),
      body: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            ListTile(
              title: Text(
                "id = ${message[0]}\nSubject = ${message[1]}\nMessage = ${message[2]}\nLikes = ${message[3]}\nCreated = ${message[4]}\n",
                style: TextStyle(fontSize: 18),
              ),
            ),
            Center(
              child: ElevatedButton(
                onPressed: () {
                  _onButtonPressed(int.parse(message[0]));
                },
                child: Text('Like this message'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}