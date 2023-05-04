import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_application_1/user.dart';
import 'user_data.dart';
import 'my_function.dart';


class Profile {
// the variable we will use in the following files
  late int id;
  late String token;
  late String avatarUrl;
  late String name;
  late String email;
  late String description;
  Profile(
    {required this.token,
    required this.avatarUrl,
    required this.name,
    required this.email,
    required this.description,
    required this.id,});
  factory Profile.fromJson(Map<String, dynamic> json) {
    return Profile(
      token: json['token'] as String,
      avatarUrl: json['avatarUrl'] as String,
      name: json['mName'] as String,
      email: json['mEmail'] as String,
      description: json['mNote'] as String,
      id: json['mId'],);
  }
  Map<String, dynamic> toJson() {
    final Map<String, dynamic> user = new Map<String, dynamic>();
    user['mStatus'] = 'ok';
    user['mData'] = {
      'token': token,
      'avatarUrl': avatarUrl,
      'mName': name,
      'mEmail': email,
      'mNote': description,
      'id': id,
    };
    return user;
  }
}




class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  State<ProfileScreen> createState() =>
      _ProfileScreenState(); // create State life cycle
}

class _ProfileScreenState extends State<ProfileScreen> {
  
  @override
  void initState() {
    super.initState();
    // Set the initial value of the userIdentity controller to the current user's userIdentity value.
  }

// the previous bunch of code is for function
// current bunch of code is  for UI look like
  @override
  Widget build(BuildContext context) {
      final userid = ModalRoute.of(context)!.settings.arguments as String;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Profile'),
      ),
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
              controller: TextEditingController(text: profile.profilename),
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
              controller: genderTextControl,
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
              controller: sexOriTextControl,
            ),
            TextField(
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'Description',
                enabled: true,
              ),
              controller: descTextControl,
            ),
            ElevatedButton(
              onPressed: () {
                String firstInput = genderTextControl.text;
                String secondInput = sexOriTextControl.text;
                String thirdInput = descTextControl.text;
                updateProfile(user.userName, user.userEmail, firstInput, secondInput, thirdInput, int.parse(user.sid));
                user.saveUser(User(
                avatarUrl: user.userAvUrl!,
                token: "_googleUser",
                email: user.userEmail!,
                name: user.userName!,
                identity: firstInput,
                sexOri: secondInput,
                id: user.userId,
                description: thirdInput,
                sid: user.sid));
                Navigator.pushNamed(context, '/home');
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
}
