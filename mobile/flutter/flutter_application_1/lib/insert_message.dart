// ignore_for_file: prefer_const_constructors, non_constant_identifier_names

import 'package:flutter/material.dart';
import 'package:flutter_application_1/my_function.dart';
import 'package:image_picker/image_picker.dart';
import 'package:provider/provider.dart';
import 'package:file_picker/file_picker.dart';
import 'user_data.dart';
import 'dart:convert';

class InsertMessage extends StatefulWidget {

  @override
  _InsertMessage createState() => _InsertMessage();
}


class _InsertMessage extends State<InsertMessage> {
  TextEditingController subjectTextControl = TextEditingController();
  TextEditingController messageTextControl = TextEditingController();
  XFile? file; 
  String? base64;

  XFile? getFile(){
    return file;
  }

  @override
  void dispose() {
    super.dispose();
    subjectTextControl.dispose();
    messageTextControl.dispose();
  } 

  openFiles() async {
    file = await ImagePicker().pickImage(source: ImageSource.gallery);
        if(file != null){
      List<int> bytes = await file!.readAsBytes();
      print(bytes);
      base64 = base64Encode(bytes);
      print(base64);
    }
    setState(() {});
  }

  openCamera() async {
    file = await ImagePicker().pickImage(source: ImageSource.camera);
    if(file != null){
      List<int> bytes = await file!.readAsBytes();
      print(bytes);
      base64 = base64Encode(bytes);
      print(base64);
    }
    setState(() {});
  }

  Future<void> showDialogChoice(BuildContext context) {
    return showDialog(context: context, builder: (BuildContext context){
      return AlertDialog(
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                GestureDetector(
                  child: Text("Camera"),
                  onTap: (){
                    openCamera(); 
                  }
                ),
                SizedBox(height: 20,),
                GestureDetector(
                  child: Text("Files"),
                  onTap: (){
                    openFiles();
                  }
                )
              ],
            )
          )
        );
      }
    );
  }

  @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserData>(context);
    return Scaffold(
      resizeToAvoidBottomInset: false,
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
            Padding(
              padding: EdgeInsetsDirectional.all(6),
              child: Container(
                width: 400.0,
                height: 110.0,
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.deepPurple, width: 6)
                ),
                child: TextButton(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Image.asset('images/link.png'),
                      SizedBox(width: 20,),
                      Text('Add a link')
                    ]
                  ),
                  onPressed: () {
                  }
                ),
              ),
            ),
            Padding(
              padding: EdgeInsetsDirectional.all(6),
              child: Container(
                width: 400.0,
                height: 110.0,
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.deepPurple, width: 6)
                ),
                child: TextButton(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Image.asset('images/attach.png'),
                      SizedBox(width: 20,),
                      Text('Attach a file')
                    ]
                  ),
                  onPressed: () {
                    showDialogChoice(context);
                  }
                ),
              ),
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
