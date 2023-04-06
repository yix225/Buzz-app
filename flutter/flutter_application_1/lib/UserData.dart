import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'dart:convert';
import 'main.dart';
import 'package:flutter_application_1/constants.dart';
import 'user.dart';

class UserData extends ChangeNotifier {
  User? _user;
  GoogleSignInAccount? _googleUser;
  final GoogleSignIn _googleSignIn = GoogleSignIn(
    // clientID(),
    // redirectUrl(),
    // issuer: GOOGLE_ISSUER,
    scopes: ['email', 'profile'],
  );
  User? get user => _user;

  set user(User? user) {
    if (_user == user)
      return; // if they are equal. the compiler do not need to assigned them equal.
    _user = user;
    //notifyListeners(); // check the any change
  }

  String? get userName => _user != null ? _user!.name : '';
  String? get userEmail => _user != null ? _user!.email : '';
  String? get userIdentity => _user != null ? _user!.identity : '';
  String? get userSexOri => _user != null ? _user!.sexOri : '';

  bool get isLogin => _user != null;

  Future<void> saveUser(User user) async {
    this.user = user; // assign user data
    print(user.token);
  }

  // log out and clean all of the data
  Future<void> removeUser() async {
    _googleUser = null;
    user = null;
  }

  Future<GoogleSignInAccount?> googleSignIn() async {
    // Attempts to sign in a previously authenticated user without interaction.
    _googleUser = await _googleSignIn.signIn();
    _googleUser ??= await _googleSignIn
        .signIn(); // if the previous work successfully, we will not use that one
    if (_googleUser != null) {
      saveUser(User()
        ..email = _googleUser!.email
        ..name = _googleUser!.displayName ?? ''
        ..avatarUrl = _googleUser!.photoUrl?.split('=')[0] ?? ''
        ..sexOri = ''
        ..identity = ''
        ..token = ''); // store user basic data
      return _googleUser;
    }
  }

  Future<void> googleSignOut() async {
    _googleSignIn.signOut().then((_) => removeUser());
  }
}
