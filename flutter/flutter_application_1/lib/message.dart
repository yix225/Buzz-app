// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables

import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_application_1/UserInfoScreen.dart';
import 'package:flutter_application_1/my_function.dart';

import 'commentPage.dart';

class message extends StatelessWidget {
  @override
  late Timer _timer;
  bool _isButtonPressed = false;

  // void _startTimer() {
  //   // Start a timer for 5 seconds
  //   _timer = Timer(Duration(seconds: 5), () {
  //     _isButtonPressed = false;
  //   });
  // }

  void _onButtonPressed(int mid) {
    /* if (_isButtonPressed) {
      _isButtonPressed = false;
      update_unLikes(mid);
      print('You unlike this message');
      _timer = Timer(Duration(seconds: 0), () {});
    } else { */
    // Perform the first action if the button is pressed for the first time or after 5 seconds
    update_Likes(mid);
    // _isButtonPressed = true;
    print('You like this message');
    // _startTimer();
    //}
  }

  void _onButtonPressed2(int mid) {
    _isButtonPressed = false;
    update_unLikes(mid);
    print('You unlike this message');
  }

  Widget build(BuildContext context) {
    final List<String> message =
        ModalRoute.of(context)!.settings.arguments as List<String>;
    return Scaffold(
      appBar: AppBar(
        title: Text('Message Info'),
      ),
      body: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Tooltip(
              message: message[0],
              child: InkWell(
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => UserInfoScreen()),
                  );
                },
                child: ListTile(
                  title: Text(
                    "id = ${message[0]}\nSubject = ${message[1]}\nMessage = ${message[2]}\nLikes = ${message[3]}\nCreated = ${message[4]}\n",
                    style: TextStyle(fontSize: 18),
                  ),
                ),
              ),
            ),
            Center(
              child: ElevatedButton.icon(
                icon: SizedBox(
                  width: 20,
                  height: 20,
                  child: Image.asset('images/upvote.png'),
                ),
                onPressed: () {
                  _onButtonPressed(int.parse(message[0]));
                },
                label: const Text('upvote'),
                //child: Text('Up vote'),

                //child: Image.asset('images/upvote.png'),
              ),
            ),
            Center(
              child: ElevatedButton.icon(
                icon: SizedBox(
                  width: 20,
                  height: 20,
                  child: Image.asset('images/downvote.png'),
                ),
                onPressed: () {
                  _onButtonPressed2(int.parse(message[0]));
                },
                label: const Text('downvote'),
                //child: Text('Down Vote'),
                //child: Image.asset('images/downvote.png'),
              ),
            ),
            Center(
              child: ElevatedButton(
                onPressed: () {
                  Navigator.of(context).pushReplacement(
                      // go to the profile page
                      MaterialPageRoute(builder: (context) => commentPage()));
                  //_onButtonPressed(int.parse(message[0]));
                },
                child: Text('add the comment'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
