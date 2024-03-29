import 'package:flutter/material.dart';
import 'package:flutter_application_1/sign_in_screen.dart';
import 'package:flutter_application_1/user_data.dart';
import 'package:flutter_application_1/user_info_screen.dart';
import 'package:provider/provider.dart';

// BASIC CONTRUSCT
class MyDrawer extends StatefulWidget {
  const MyDrawer({super.key, required this.selectedPage});

  final String selectedPage;

  // Create the state
  @override
  State<MyDrawer> createState() => _MyDrawerState(); // create the state
}

class _MyDrawerState extends State<MyDrawer> {
  @override
  void dispose() {
    // Remove any references to this object here
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserData>(
        context); // return type: UserModel and return the context
    return Drawer(
      child: (ListView(
        // see all of the componet on the pages
        padding: EdgeInsets.zero, // add noting at the edge
        children: <Widget>[
          GestureDetector(
            onTap: () {
              // onTap : one click
              if (user.isLogin) {
                // if user logins success
                Navigator.push(
                  // Use Navigator.push() to jump pages
                  context,
                  MaterialPageRoute(
                    builder: (context) =>
                        const UserInfoScreen(), // connect with the profile page
                  ),
                );
              } else {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) =>
                        SignInScreen(), // connect with the login page
                  ),
                );
              }
            },
            // DrawerHeader is used to define the header part of the sidebar
            child: DrawerHeader(
              decoration: BoxDecoration(
                // the box for drawer
                color:
                    Theme.of(context).drawerTheme.backgroundColor, // the theme
              ),
              padding: EdgeInsets.zero, // set the edge
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment
                      .center, //  Positions children at the middle of the main axis.
                  children: <Widget>[
                    // the selections in the drawer
                    CircleAvatar(
                      // A circle that represents a user.
                      backgroundImage: user.userAvatar, // the photo
                      radius: 40, // size
                    ),

                    const SizedBox(
                      height: 10,
                    ),

                    Text(
                      user.userName ?? '',
                      style: Theme.of(context).textTheme.titleLarge,
                    ),

                    Text(
                      user.userEmail ?? '',
                      style: Theme.of(context).textTheme.titleMedium,
                    ),

                    const SizedBox(
                      height: 10,
                    ),
                  ],
                ),
              ),
            ),
          ),
          ListTile(
            // list the Profile page components
            selected: widget.selectedPage == 'UserInfoScreen',
            leading: const Icon(Icons.account_circle),
            title: const Text('Profile'),
            onTap: widget.selectedPage ==
                    'UserInfoScreen' // when click the profile
                ? () => Navigator.pop(
                    context) // go back the previous page with the context
                : () => Navigator.of(context).pushReplacement(
                      // go to the profile page
                      MaterialPageRoute(
                          builder: (context) => const UserInfoScreen()),
                    ),
          ),
        ],
      )),
    );
  }
}
