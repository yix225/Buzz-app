import 'package:flutter/material.dart';
import 'package:flutter_application_1/my_function.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:flutter_application_1/user_data.dart';
import 'package:provider/provider.dart';

class Profile {
// the variable we will use in the following files
  late int id;
  late String token;
  late String avatarUrl;
  late String name;
  late String email;
  late String description;
  Profile({
    required this.name,
    required this.email,
    required this.description,
    required this.id,
  });
  factory Profile.fromJson(Map<String, dynamic> json) {
    return Profile(
      name: json['mData']['mName'] as String,
      email: json['mData']['mEmail'] as String,
      description: json['mData']['mNote'] as String,
      id: json['mData']['mId'],
    );
  }
  Map<String, dynamic> toJson() {
    print('here2');
    final Map<String, dynamic> user = new Map<String, dynamic>();
    user['mStatus'] = 'ok';
    user['mData'] = {
      'mName': name,
      'mEmail': email,
      'mNote': description,
      'mId': id,
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
  late Future<Profile> _future_list_numword_pairs;
  late Timer timer;

  @override
  void initState() {
    super.initState();
    Timer timer = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        _future_list_numword_pairs = fetchProfile(context);
      });
    });
  }

// the previous bunch of code is for function
// current bunch of code is  for UI look like
  @override
  Widget build(BuildContext context) {
    var profile = FutureBuilder<Profile>(
        future: _future_list_numword_pairs,
        builder: (BuildContext context, AsyncSnapshot<Profile> snapshot) {
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
                    radius: 70,
                    child: Image.asset('images/default_avatar.png'),
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
                  controller: TextEditingController(text: snapshot.data!.name),
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
                  controller: TextEditingController(text: snapshot.data!.email),
                ),
                const SizedBox(
                  height: 50,
                ),
                TextField(
                  decoration: const InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Description',
                    enabled: true,
                  ),
                  controller: TextEditingController(text: snapshot.data!.description),
                ),
              ],
            ),
          ),
        );
    });
    return profile;
  }
}

Future<Profile> fetchProfile(BuildContext context) async {
  final user = Provider.of<UserData>(context, listen: false);
  final userid = ModalRoute.of(context)!.settings.arguments as String;
  Profile returnData = Profile(name: '', email: '', description: '', id: 0);
  final response = await http
      .get(Uri.parse('http://10.0.2.2:8998/getProfile/$userid/${user.sid}'));

  if (response.statusCode == 200){
    if(jsonDecode(response.body) != null){
      returnData = Profile.fromJson(jsonDecode(response.body));
    } else{
      developer.log('ERROR: Unexpected json response type.');
    }
    return returnData;
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Did not receive success status code from request.');
  }
}
