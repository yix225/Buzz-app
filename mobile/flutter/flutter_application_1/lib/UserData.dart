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
    serverClientId:
        "926558226206-ppmn3bk4ckvrtaq6hun9kpi034sde366.apps.googleusercontent.com",
  );
  User? get user => _user;

  set user(User? user) {
    if (_user == user) // if they are equal. the compiler do not need to assigned them equal.
      return; 
    _user = user;
    //notifyListeners(); // check the any change
  }

  ImageProvider get userAvatar => _user != null
      ? _user!.avatarUrl != ''
          ? NetworkImage(_user!.avatarUrl) as ImageProvider
          : const AssetImage("images/default_avatar.png")
      : const AssetImage("images/default_avatar.png");

  String? get userName => _user?.name;
  String? get userEmail => _user?.email;
  String? get userIdentity => _user?.identity;
  String? get userSexOri => _user?.sexOri;
  String? get userDescription => _user?.description;
  int get userId => _user!.id;
  String get sid => _user!.sid;

  bool get isLogin => _user != null;

  Future<void> saveUser(User user) async {
    this.user = user; // assign user data
    print("yes");
  }

  // log out and clean all of the data
  Future<void> removeUser() async {
    _googleUser = null;
    user = null;
  }

  Future<GoogleSignInAccount?> googleSignIn() async {
    String sessionid = '';
    String email = '';
    String genid = '';
    String sexotn = '';
    String note = '';
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
      try {
        final response = await http.post(
          Uri.parse('http://10.0.2.2:8998/login'),
          body: googleAuth.idToken,
        );
        print(response.statusCode);
        print(jsonDecode(response.body));
        final sessId = jsonDecode(response.body)['mData'][0];
        sessionid = sessId.toString();
        email = jsonDecode(response.body)['mData'][1];
        genid = jsonDecode(response.body)['mData'][2];
        sexotn = jsonDecode(response.body)['mData'][3];
        note = jsonDecode(response.body)['mData'][4];
        print(sessionid);
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
                id: 0,
                description: '',
                sid: sessionid)
              ..email = googleUser.email
              ..name = googleUser.displayName ?? ''
              ..avatarUrl = googleUser.photoUrl?.split('=')[0] ?? ''
              ..sexOri = sexotn
              ..identity = genid
              ..description = note
              ..token = "_googleUser"
              ..id = 0); // store user basic data
            print("yes3");
            return googleUser;
          }
        } 
      } catch (e) {
        print(e);
      }
    }
    else  // Authentication failed, return null   
      return null;
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
          id: 0,
          description: '',
          sid: sessionid
          )
        ..email = _googleUser!.email
        ..name = _googleUser!.displayName ?? ''
        ..avatarUrl = _googleUser!.photoUrl?.split('=')[0] ?? ''
        ..sexOri = sexotn
        ..identity = genid
        ..description = note
        ..token = "_googleUser"); // store user basic data
      return _googleUser;
    }
  }

  Future<void> googleSignOut() async {
    _googleSignIn.signOut().then((_) => removeUser());
  }
}
