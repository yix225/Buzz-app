// ignore_for_file: prefer_const_constructors, non_constant_identifier_names

import 'package:flutter/material.dart';
import 'package:flutter_application_1/my_function.dart';
import 'package:image_picker/image_picker.dart';
import 'package:provider/provider.dart';
import 'package:file_picker/file_picker.dart';
import 'user_data.dart';
import 'dart:convert';
import 'dart:io';

class InsertMessage extends StatefulWidget {

  @override
  _InsertMessage createState() => _InsertMessage();
}


class _InsertMessage extends State<InsertMessage> {
  TextEditingController subjectTextControl = TextEditingController();
  TextEditingController messageTextControl = TextEditingController();
  TextEditingController linkTextControl = TextEditingController();
  File? file; 
  String? base64;

  @override
  void dispose() {
    super.dispose();
    subjectTextControl.dispose();
    messageTextControl.dispose();
    linkTextControl.dispose();
  } 

  openFiles() async {
    final result = await FilePicker.platform.pickFiles();
    file = File(result!.files.first.path.toString());
        if(file != null){
      List<int> bytes = await file!.readAsBytes();
      print(bytes);
      base64 = base64Encode(bytes);
      print(base64);
    }
    setState(() {});
  }

  openCamera() async {
    final result = await ImagePicker().pickImage(source: ImageSource.camera);
    file = File(result!.path);
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

  Future<void> showLinkInsert(BuildContext context) {
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
                if(file != null){
                  print('there is an file we are attaching');
                }
                else{
                  Future<int> newid = addMessage(firstInput, secondInput, user.sid);
                }
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
                    width: 100.0,
                    height: 100.0,
                    decoration: BoxDecoration(
                      border: Border.all(color: Colors.deepPurple, width: 6)
                    ),
                    child: TextButton(
                      child: Image.asset('images/camera.png'),
                      onPressed: () {
                        openCamera();
                      }
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsetsDirectional.all(6),
                  child: Container(
                    width: 100.0,
                    height: 100.0,
                    decoration: BoxDecoration(
                      border: Border.all(color: Colors.deepPurple, width: 6)
                    ),
                    child: TextButton(
                      child: Image.asset('images/attach.png'),
                      onPressed: () {
                        openFiles();
                      }
                    ),
                  ),
                ),
              ]
            ),
            if(file != null)...{
              Text('File: ${file!.path.split('/').last}'),
              ElevatedButton(
                onPressed: (){
                  file = null;
                  setState(() {});
                },
                child: Text("Remove"),
              )
            }
          ],
        ),
      ),
    );
  }
}