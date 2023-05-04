// ignore_for_file: prefer_const_constructors, non_constant_identifier_names

import 'package:flutter/material.dart';
import 'package:flutter_application_1/my_function.dart';
import 'package:provider/provider.dart';
import 'UserData.dart';

class edit_comment extends StatelessWidget {
  TextEditingController MessageTextControl = TextEditingController();

  void dispose() {
    MessageTextControl.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserData>(context);
    final List<String> idparams =
                ModalRoute.of(context)!.settings.arguments as List<String>;
    return Scaffold(
      appBar: AppBar(
        title: Text('Please enter your new message'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
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
                String secondInput = MessageTextControl.text;
                //updateComment(, secondInput, user.sid);
                Navigator.pushNamed(context, '/');
              },
              child: Text('Add'),
            ),
          ],
        ),
      ),
    );
  }
}
