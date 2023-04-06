// signin screen

import 'package:flutter/material.dart';
import 'package:flutter_application_1/authentication.dart';
import 'package:provider/provider.dart';
import 'package:flutter_application_1/constants.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'UserData.dart';
//import '../images/google.png';

class SignInScreen extends StatelessWidget {
/* 
  final GoogleSignIn _googleSignIn = GoogleSignIn(
    // clientId: clientID(),
    // redirectUrl(),
    // issuer: GOOGLE_ISSUER,
    scopes: ['email', 'profile'],
  );
  void _handleSignIn() async {
    try {
      final GoogleSignInAccount? googleUser = await _googleSignIn.signIn();
      if (googleUser == null) {
        // User cancelled sign-in
        return;
      }

      final GoogleSignInAuthentication googleAuth =
          await googleUser.authentication;
      final String idToken = googleAuth.idToken ?? '';
      final String accessToken = googleAuth.accessToken ?? '';

      // TODO: Store the idToken and accessToken in the database

      // Navigate to the home page

      // Show a success message
      showDialog(
        context: context,
        builder: (context) => AlertDialog(
          title: const Text('Sign in successful'),
          content: const Text('You have successfully signed in with Google'),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('OK'),
            ),
          ],
        ),
      );
    } catch (error) {
      print('Error signing in: $error');
    }
  } */

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
          icon: const SizedBox(
            width: 20,
            height: 20,
            child: Image(image: AssetImage('images/google.png')),
          ),
          //onPressed: _handleSignIn,
          onPressed: () {
            // when we click the botton
            user.googleSignIn().then((account) {
              if (account != null) {
                Navigator.pop(context); // change to other page
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
        /*  left: 20,
            right: 20,
            top: size.height * 0.2,
            bottom: size.height * 0.5),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            const Text("google sign in", style: TextStyle(fontSize: 30)),
            GestureDetector(
              onTap: () {
                Authentication().signInWithGoogle();
              },
              //child: const Image(width:100,image:AssetImage(assets/google.png))
            ),
          ],
        ), */
      ),
    );
  }
}
