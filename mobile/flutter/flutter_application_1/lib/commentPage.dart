import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_application_1/comment.dart';
import 'package:http/src/response.dart';

import 'main.dart';
import 'myDrawer.dart';
import 'my_function.dart';

class commentPage extends StatelessWidget {
  //const commentPage({super.key, required this.post});

  //final Post post;
  TextEditingController CommentTextControl = TextEditingController();

  void dispose() {
    CommentTextControl.dispose();
  }
//   @override
//   State<comment> createState() => _commentState(); // create the state
// }

// class _commentState extends State<comment> {
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
                add_comment(input);
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

void add_comment(String input) {
  addComment(input);
}
