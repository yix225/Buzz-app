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
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Padding(
                  padding: EdgeInsetsDirectional.all(6),
                  child: Container(
                    width: 130.0,
                    height: 130.0,
                    decoration: BoxDecoration(
                      border: Border.all(color: Colors.deepPurple, width: 6)
                    ),
                    child: TextButton(
                      child: Image.asset('images/link.png'),
                      onPressed: () {
                      }
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsetsDirectional.all(6),
                  child: Container(
                    width: 130.0,
                    height: 130.0,
                    decoration: BoxDecoration(
                      border: Border.all(color: Colors.deepPurple, width: 6)
                    ),
                    child: TextButton(
                      child: Image.asset('images/attach.png'),
                      onPressed: () {
                      }
                    ),
                  ),
                ),
              ]
            ),
          ],
        ),
      ),
    );
  }
}

class InsertLinkIdea extends StatelessWidget {
  TextEditingController linkTextControl = TextEditingController();
  TextEditingController descTextControl = TextEditingController();

  void dispose() {
    linkTextControl.dispose();
    descTextControl.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserData>(context);
    return Scaffold(
      appBar: AppBar(
        title: Text('Please enter your Link'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              decoration: InputDecoration(
                labelText: 'Enter your Link here:',
              ),
              controller: linkTextControl,
            ),
            TextField(
              decoration: InputDecoration(
                labelText: 'Enter your description here:',
              ),
              controller: linkTextControl,
            ),
            SizedBox(height: 32),
            ElevatedButton(
              onPressed: () {
                String firstInput = linkTextControl.text;
                String secondInput = descTextControl.text;
                addFileIdea(firstInput, secondInput, user.sid);
                Navigator.pushNamed(context, '/mymessage');
              },
              child: Text('Add'),
            ),
          ],
        ),
      ),
    );
  }
}
