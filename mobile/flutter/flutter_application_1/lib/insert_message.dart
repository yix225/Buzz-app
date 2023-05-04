// ignore_for_file: prefer_const_constructors, non_constant_identifier_names

import 'package:flutter/material.dart';
import 'package:flutter_application_1/my_function.dart';
import 'package:provider/provider.dart';
import 'user_data.dart';

class InsertMessage extends StatelessWidget {
  TextEditingController subjectTextControl = TextEditingController();
  TextEditingController messageTextControl = TextEditingController();

  void dispose() {
    subjectTextControl.dispose();
    messageTextControl.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserData>(context);
    return Scaffold(
      appBar: AppBar(
        title: Text('Please enter your subject and message'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              decoration: InputDecoration(
                labelText: 'Enter your Subject here:',
              ),
              controller: subjectTextControl,
            ),
            SizedBox(height: 16),
            TextField(
              decoration: InputDecoration(
                labelText: 'Enter your Message here:',
              ),
              controller: messageTextControl,
            ),
            SizedBox(height: 32),
            ElevatedButton(
              onPressed: () {
                String firstInput = subjectTextControl.text;
                String secondInput = messageTextControl.text;
                addMessage(firstInput, secondInput, user.sid);
                Navigator.pushNamed(context, '/home');
              },
              child: Text('Add'),
            ),
          ],
        ),
      ),
    );
  }
}
