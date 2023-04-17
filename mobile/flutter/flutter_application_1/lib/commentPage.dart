import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_application_1/comment.dart';
import 'package:http/src/response.dart';

import 'main.dart';
import 'myDrawer.dart';
import 'my_function.dart';

class commentPage extends StatefulWidget {
  const commentPage({required this.mid, Key? key}) : super(key: key);
  final String mid;

  //const commentPage(String message, {super.key, required this.comment});

  //final Comment comment;

  @override
  State<commentPage> createState() => _commentPageState(); // create the state
}

class _commentPageState extends State<commentPage> {
  TextEditingController CommentTextControl = TextEditingController();

  void dispose() {
    CommentTextControl.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    // call the scaffold
    return Scaffold(
      appBar: AppBar(
        title: const Text("Comment"), // the bar with the words
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              decoration: const InputDecoration(
                labelText: 'Enter your Comment here:',
              ),
              controller: CommentTextControl,
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
                String input = CommentTextControl.text;
                add_comment(input, widget.mid);
                Navigator.pushNamed(context, '/');
              },
              child: const Text('Add'),
            ),
          ],
        ),
      ),
      // drawer: const myDrawer(
      //   selectedPage: 'Comment',
      // ),
      // body: Column(
      //   children: [
      //     Align(
      //       // Creates an alignment widget.
      //       alignment:
      //           Alignment.topCenter, // The center point along the top edge.
      //       child: Card(
      //         // the card for likes
      //         child: ListTile(

      //             //title: Text(), // the title is Content
      //             //subtitle: Text('Like:${widget.post.mLikes}'), // how many likes
      //             ),
      //       ),
      //     ),
      //   ],
      // ),
    );
  }
}

void add_comment(String input, String mId) {
  addComment(input, mId);
}
