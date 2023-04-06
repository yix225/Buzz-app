// import 'package:flutter/widgets.dart';
// import 'package:flutter_application_1/SignInScreen.dart';
// import 'package:flutter_application_1/UserInfoScreen.dart';
// import 'package:flutter_application_1/main.dart';
// import 'package:google_sign_in/google_sign_in.dart';
// import 'package:firebase_auth/firebase_auth.dart';

// class Authentication {
//   handleAuthState() {
//     return StreamBuilder(
//         stream: FirebaseAuth.instance.authStateChanges(),
//         builder: (BuildContext context, snapshot) {
//           if (snapshot.hasData) {
//             return UserInfoScreen();
//           } else {
//             return SignInScreen();
//           }
//         });
//   }
// }

// signInWithGoogle() async {
//   // trigger the authentication
//   final GoogleSignIn 
//   final GoogleSignInAccount? googleUser =
//       await GoogleSignIn(scopes: <String>["email"]).signIn();

//   final GoogleSignInAuthentication googleSignInAuthentication =
//       await googleUser!.authentication;
//   final credential = GoogleAuthProvider.credential(
//     accessToken: googleSignInAuthentication.accessToken,
//     idToken: googleSignInAuthentication.idToken,
//   );
//   return await FirebaseAuth.instance.signInWithCredential(credential);
// }

// signout() {
//   FirebaseAuth.instance.signOut();
// }

/*   static Future<User?> signInWithGoogle({required BuildContext context}) async {
    FirebaseAuth auth = FirebaseAuth.instance;
    User? user;

    //final GoogleSignIn googleSignIn = GoogleSignIn();

    final GoogleSignInAccount? googleSignInAccount =
        await googleSignIn.signIn();

    if (googleSignInAccount != null) {
      final GoogleSignInAuthentication googleSignInAuthentication =
          await googleSignInAccount.authentication;

      final AuthCredential credential = GoogleAuthProvider.credential(
        accessToken: googleSignInAuthentication.accessToken,
        idToken: googleSignInAuthentication.idToken,
      );

      try {
        final UserCredential userCredential =
            await auth.signInWithCredential(credential);

        user = userCredential.user;
      } on FirebaseAuthException catch (e) {
        if (e.code == 'account-exists-with-different-credential') {
          ScaffoldMessenger.of(context).showSnackBar(
            Authentication.customSnackBar(
              content:
                  'The account already exists with a different credential',
            ),
          );
        } else if (e.code == 'invalid-credential') {
          ScaffoldMessenger.of(context).showSnackBar(
            Authentication.customSnackBar(
              content:
                  'Error occurred while accessing credentials. Try again.',
            ),
          );
        }
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          Authentication.customSnackBar(
            content: 'Error occurred using Google Sign In. Try again.',
          ),
        );
      }
    }

    return user;
  }

static SnackBar customSnackBar({required String content}) {
  return SnackBar(
    backgroundColor: Colors.black,
    content: Text(
      content,
      style: TextStyle(color: Colors.redAccent, letterSpacing: 0.5),
    ),
  );
}
static Future<FirebaseApp> initializeFirebase({
  required BuildContext context,
}) async {
  FirebaseApp firebaseApp = await Firebase.initializeApp();

  User? user = FirebaseAuth.instance.currentUser;

  if (user != null) {
    Navigator.of(context).pushReplacement(
      MaterialPageRoute(
        builder: (context) => UserInfoScreen(
          user: user,
        ),
      ),
    );
  }

  return firebaseApp;
} */