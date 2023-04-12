import 'package:flutter/material.dart';

import 'package:flutter_application_1/SignInScreen.dart';
import 'package:flutter_application_1/myDrawer.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:http/http.dart';
import 'package:provider/provider.dart';
import 'dart:convert';
import 'package:flutter_application_1/user.dart';
import 'package:http/http.dart' as http;

import 'UserData.dart';
import 'my_function.dart';

class UserInfoScreen extends StatefulWidget {
  const UserInfoScreen({super.key});

  @override
  State<UserInfoScreen> createState() =>
      _UserInfoScreenState(); // create State life cycle
}

class _UserInfoScreenState extends State<UserInfoScreen> {
  Future<bool?> showLogoutDialog(BuildContext context) {
    // log out process
    return showDialog<bool>(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Logout'),
          content:
              const Text('Are you sure to logout?'), // conversation with user
          actions: <Widget>[
            TextButton(
              child: const Text('Cancel'), // keep in app
              onPressed: () => Navigator.of(context).pop(false),
            ),
            TextButton(
              child: const Text('OK'), // log out
              onPressed: () => Navigator.of(context).pop(true),
            ),
          ],
        );
      },
    );
  }

  TextEditingController sexOriTextControl = TextEditingController();
  TextEditingController genderTextControl = TextEditingController();
  void initState() {
    super.initState();
    // Set the initial value of the userIdentity controller to the current user's userIdentity value.
    final user = Provider.of<UserData>(context, listen: false);
    sexOriTextControl.text = user.userSexOri!;
    genderTextControl.text = user.userIdentity!;
  }

// the previous bunch of code is for function
// current bunch of code is  for UI look like
  @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserData>(context);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Profile'),
      ),
      // drawer: const myDrawer(
      //   selectedPage: 'Profile',
      // ),
      body: Padding(
        // padding left and right
        padding: const EdgeInsets.symmetric(horizontal: 20),
        child: ListView(
          children: <Widget>[
            const SizedBox(
              height: 20,
            ),
            Align(
              child: CircleAvatar(
                backgroundImage: user.userAvatar,
                radius: 70,
              ),
            ),
            const SizedBox(
              height: 20,
            ),
            TextField(
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'Name',
                enabled: false,
              ),
              controller: TextEditingController(text: user.userName),
            ),
            const SizedBox(
              height: 20,
            ),
            const SizedBox(
              height: 20,
            ),
            TextField(
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'Email',
                enabled: false,
              ),
              controller: TextEditingController(text: user.userEmail),
            ),
            const SizedBox(
              height: 50,
            ),
            TextField(
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'Gender',
                enabled: true,
              ),
              controller:
                  genderTextControl, //TextEditingController(text: user.userIdentity),
              onChanged: (newValue) {
                // When the user changes the value of the TextField, update the userIdentity value in the UserData object and in the database.
                add_gender(newValue);
              },
            ),
            const SizedBox(
              height: 20,
            ),
            TextField(
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'Sexual orientation',
                enabled: true,
              ),
              controller:
                  sexOriTextControl, //TextEditingController(text: user.userSexOri),
              onChanged: (newValue) {
                // When the user changes the value of the TextField, update the userIdentity value in the UserData object and in the database.
                add_sexOri(newValue);
              },
            ),
            TextField(
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'Description',
                enabled: true,
              ),
              controller: TextEditingController(),
              onChanged: (newValue) {
                // When the user changes the value of the TextField, update the userIdentity value in the UserData object and in the database.
                add_description(newValue);
              },
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.pushNamed(context, '/');
              },
              child: const Text("Save"),
            ),
            user.isLogin
                ? ElevatedButton(
                    onPressed: () async {
                      final bool? isLogout = await showLogoutDialog(context);
                      if (isLogout != null && isLogout) {
                        user.googleSignOut().then((_) {
                          user.removeUser();
                          Navigator.pop(context);
                        });
                      }
                    },
                    child: const Text("Logout"),
                  )
                : const SizedBox(),
          ],
        ),
      ),
    );
  }

  void add_sexOri(String firstInput) {
    addSexOri(firstInput);
  }

  void add_gender(String secondInput) {
    addGender(secondInput);
  }

  void add_description(String thirdInput) {
    addDescription(thirdInput);
  }
}
