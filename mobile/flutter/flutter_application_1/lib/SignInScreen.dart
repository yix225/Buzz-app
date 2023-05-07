// signin screen
import 'dart:convert';
import 'package:flutter_application_1/UserInfoscreen.dart';
import 'package:flutter_application_1/main.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_application_1/constants.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'UserData.dart';
//import '../images/google.png';

class SignInScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    UserData user = Provider.of<UserData>(context);
    //final Size size = MediaQuery.of(context).size;
    return Scaffold(
      appBar: AppBar(
        title: const Text("Google login"),
        backgroundColor: Colors.grey,
      ),
      body: Center(
        child: ElevatedButton.icon(
          icon: SizedBox(
            width: 20,
            height: 20,
            child: Image.asset('images/google.png'),
          ),
          //onPressed: _handleSignIn,
          onPressed: //signIn,
              () {
            // when we click the botton
            user.googleSignIn().then((account) async {
              if (account != null) {
                final bool? check = await showSignInDialog(context);
                if (check != null && check) {
                  Navigator.pop(context); // change to other page
                }
              }
            }).catchError((error) {
              // report the error
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(
                  content: Text('Login failed: $error'),
                ),
              );
            });
          },
          label: const Text('Sign in with Google'),
        ),
      ),
    );
  }

  Future<bool?> showSignInDialog(BuildContext context) {
    return showDialog<bool>(
        context: context,
        builder: (context) => AlertDialog(
              title: const Text('Sign in successful'),
              content:
                  const Text('You have successfully signed in with Google'),
              actions: [
                TextButton(
                  onPressed: () =>
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                    builder: (context) => const UserInfoScreen(),
                  )),
                  child: const Text('OK'),
                ),
              ],
            ));
  }
}
