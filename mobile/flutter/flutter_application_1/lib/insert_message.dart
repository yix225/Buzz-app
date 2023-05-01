// ignore_for_file: prefer_const_constructors, non_constant_identifier_names

import 'package:flutter/material.dart';
import 'package:flutter_application_1/my_function.dart';
import 'package:provider/provider.dart';
import 'UserData.dart';

class insert_message extends StatelessWidget {
  TextEditingController SubjectTextControl = TextEditingController();
  TextEditingController MessageTextControl = TextEditingController();

  void dispose() {
    SubjectTextControl.dispose();
    MessageTextControl.dispose();
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
              controller: SubjectTextControl,
            ),
            SizedBox(height: 16),
            TextField(
              decoration: InputDecoration(
                labelText: 'Enter your Message here:',
              ),
              controller: MessageTextControl,
            ),
            SizedBox(height: 32),
            ElevatedButton(
              onPressed: () {
                String firstInput = SubjectTextControl.text;
                String secondInput = MessageTextControl.text;
                add_Message(firstInput, secondInput, user.sid);
                Navigator.pushNamed(context, '/');
              },
              child: Text('Add'),
            ),
          ],
        ),
      ),
    );
  }

  void add_Message(String firstInput, String secondInput, String sessId) {
    addMessage(firstInput, secondInput, sessId);
  }
}
