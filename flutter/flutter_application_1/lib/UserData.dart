import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'dart:convert';
import 'main.dart';
import 'package:flutter_application_1/constants.dart';
import 'package:http/http.dart' as http;
import 'user.dart';

class UserData extends ChangeNotifier {
  User? _user;
  GoogleSignInAccount? _googleUser;
  final GoogleSignIn _googleSignIn = GoogleSignIn(
    clientId:
        "926558226206-ppmn3bk4ckvrtaq6hun9kpi034sde366.apps.googleusercontent.com",
    // redirectUrl(),
    // issuer: GOOGLE_ISSUER,
    //scopes: ['email', 'profile'],
  );
  User? get user => _user;

  set user(User? user) {
    if (_user == user)
      return; // if they are equal. the compiler do not need to assigned them equal.
    _user = user;
    //notifyListeners(); // check the any change
  }

  // String? get userName => _user != null ? _user!.name : '';
  // String? get userEmail => _user != null ? _user!.email : '';
  // String? get userIdentity => _user != null ? _user!.identity : '';
  // String? get userSexOri => _user != null ? _user!.sexOri : '';
  ImageProvider get userAvatar => _user != null
      ? _user!.avatarUrl != ''
          ? NetworkImage(_user!.avatarUrl) as ImageProvider
          : const AssetImage("images/default_avatar.png")
      : const AssetImage("images/default_avatar.png");

  String? get userName => _user?.name;
  String? get userEmail => _user?.email;
  String? get userIdentity => _user?.identity;
  String? get userSexOri => _user?.sexOri;

  bool get isLogin => _user != null;

  Future<void> saveUser(User user) async {
    this.user = user; // assign user data
    print("yes");
    //print(user.);
  }

  // log out and clean all of the data
  Future<void> removeUser() async {
    _googleUser = null;
    user = null;
  }

  Future<GoogleSignInAccount?> googleSignIn() async {
    print("hello");
    // Attempts to sign in a previously authenticated user without interaction.
    _googleUser = await _googleSignIn.signIn();
    print("yes");
    final GoogleSignInAuthentication googleAuth =
        await _googleUser!.authentication;
    final String idToken = googleAuth.idToken ?? '';
    final String accessToken = googleAuth.accessToken ?? '';
    if (googleAuth.idToken != null) {
      print(googleAuth.idToken);
      final response = await http.post(
        Uri.parse('http://2023sp-team-m.dokku.cse.lehigh.edu/messages'),
        body: {
          'idToken': googleAuth.idToken,
        },
      );
      print("yes3");
      print(response.statusCode);
      if (response.statusCode == 500) {
        // Get the user's profile information
        final googleUser = await _googleSignIn.signInSilently();

        if (googleUser != null) {
          print("yes4");
          saveUser(User(
              avatarUrl: '',
              token: '',
              email: '',
              name: '',
              identity: '',
              sexOri: '',
              id: '',
              description: '')
            ..email = googleUser.email
            ..name = googleUser.displayName ?? ''
            ..avatarUrl = googleUser.photoUrl?.split('=')[0] ?? ''
            ..sexOri = ''
            ..identity = ''
            ..token = "_googleUser"); // store user basic data
          print("yes3");
          return googleUser;
        }
      } else {
        // Authentication failed, return null
        return null;
      }
    }
    print("hello2");
    _googleUser ??= await _googleSignIn
        .signIn(); // if the previous work successfully, we will not use that one
    if (_googleUser != null) {
      saveUser(User(
          avatarUrl: '',
          token: '',
          email: '',
          name: '',
          identity: '',
          sexOri: '',
          id: '',
          description: '')
        ..email = _googleUser!.email
        ..name = _googleUser!.displayName ?? ''
        ..avatarUrl = _googleUser!.photoUrl?.split('=')[0] ?? ''
        ..sexOri = ''
        ..identity = ''
        ..token = "_googleUser"); // store user basic data
      return _googleUser;
    }

    // print("hello");
    // // Attempts to sign in a previously authenticated user without interaction.
    // _googleUser = await _googleSignIn.signIn();
    // _googleUser.authentication
    // print("hello2");
    // _googleUser ??= await _googleSignIn
    //     .signIn(); // if the previous work successfully, we will not use that one
    // if (_googleUser != null) {
    //   saveUser(User()
    //     ..email = _googleUser!.email
    //     ..name = _googleUser!.displayName ?? ''
    //     ..avatarUrl = _googleUser!.photoUrl?.split('=')[0] ?? ''
    //     ..sexOri = ''
    //     ..identity = ''
    //     ..token = "_googleUser"); // store user basic data
    //   return _googleUser;
    // }
  }

  Future<void> googleSignOut() async {
    _googleSignIn.signOut().then((_) => removeUser());
  }
}
