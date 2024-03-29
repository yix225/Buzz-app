import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_application_1/user.dart';

import 'user_data.dart';
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
  TextEditingController descTextControl = TextEditingController();
  
  @override
  void initState() {
    final user = Provider.of<UserData>(context, listen: false);
    super.initState();
    // Set the initial value of the userIdentity controller to the current user's userIdentity value.
    sexOriTextControl.text = user.userSexOri!;
    genderTextControl.text = user.userIdentity!;
    descTextControl.text = user.userDescription!;
  }

// the previous bunch of code is for function
// current bunch of code is  for UI look like
  @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserData>(context);
    return Scaffold(
      resizeToAvoidBottomInset: false,
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
            const SizedBox(
              height: 20,
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
