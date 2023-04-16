import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_application_1/comment.dart';
import 'package:http/src/response.dart';

import 'UserInfoScreen.dart';
import 'main.dart';
import 'myDrawer.dart';
import 'my_function.dart';

class commentList extends StatefulWidget {
  const commentList({super.key, required this.comment});
  final Comment comment;

  // void _startTimer() {
  //   // Start a timer for 5 seconds
  //   _timer = Timer(Duration(seconds: 5), () {
  //     _isButtonPressed = false;
  //   });
  // }

  @override
  State<commentList> createState() => _commentListState(); // create the state
}

class _commentListState extends State<commentList> {
  void _onButtonPressed(int mid) {
    //update_Likes_c(mid);

    print('You like this comment');
  }

  void _onButtonPressed2(int mid) {
    bool _isButtonPressed = false;
    _isButtonPressed = false;
    //update_unLikes_c(mid);
    print('You dislike this comment ');
  }

  Widget build(BuildContext context) {
    final List<String> comment =
        ModalRoute.of(context)!.settings.arguments as List<String>;
    return Scaffold(
      appBar: AppBar(
        title: Text('Comment List'),
      ),
      body: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Tooltip(
              message: comment[0],
              child: InkWell(
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => UserInfoScreen()),
                  );
                },
                child: ListTile(
                  title: Text(
                    "id = ${comment[0]}\n comment1 = ${comment[1]}\n Likes = ${comment[3]}\n create = ${comment[4]}\n",
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
                  _onButtonPressed(int.parse(comment[0]));
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
                  _onButtonPressed2(int.parse(comment[0]));
                },
                label: const Text('downvote'),
                //child: Text('Down Vote'),
                //child: Image.asset('images/downvote.png'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
