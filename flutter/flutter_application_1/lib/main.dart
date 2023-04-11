import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'package:flutter_application_1/insert_message.dart';
import 'package:flutter_application_1/message.dart';
import 'package:flutter_application_1/my_function.dart';
import 'package:flutter_application_1/user.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:flutter_application_1/SignInScreen.dart';

import 'package:flutter_application_1/UserData.dart';
import 'package:provider/provider.dart';

import 'dart:io';
import 'package:device_info/device_info.dart';

import 'myDrawer.dart';

Future<void> main() async {
  runApp(
    ChangeNotifierProvider<UserData>(
        create: (context) => UserData(), child: const MyApp()),
  );
  //runApp(GoogleSignIn());
}

/// Create object from data like: {mId: 124, mSubject: testing dokku, mMessage: please work, mLikes: 0, mCreated: Mar 10, 2023 8:10:10 PM}
class mLine {
  final int mId;
  final String mSubject;
  String mMessage;
  int mLikes;
  final String mCreated;
  mLine({
    required this.mId,
    required this.mSubject,
    required this.mMessage,
    required this.mLikes,
    required this.mCreated,
  });

  factory mLine.fromJson(Map<String, dynamic> json) {
    return mLine(
      mId: json['mId'],
      mSubject: json['mSubject'],
      mMessage: json['mMessage'],
      mLikes: json['mLikes'],
      mCreated: json['mCreated'],
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['mStatus'] = 'ok';
    data['mData'] = {
      'mId': this.mId,
      'mSubject': this.mSubject,
      'mMessage': this.mMessage,
      'mLikes': this.mLikes,
      'mCreated': this.mCreated
    };
    print(jsonEncode(data));
    return data;
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.deepPurple,
      ),
      home: const MyHomePage(title: 'Team M Flutter App'),
      routes: {
        '/insert': (context) => insert_message(),
        '/mymessage': (context) => message(),
        '/SignInScreen': (context) => SignInScreen(),
      },
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".
  final String title;
  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      drawer: const myDrawer(
        selectedPage: 'Profile',
      ),
      body: const Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: HttpReqWords(),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

class HttpReqWords extends StatefulWidget {
  const HttpReqWords({Key? key}) : super(key: key);

  @override
  State<HttpReqWords> createState() => _HttpReqWordsState();
}

class _HttpReqWordsState extends State<HttpReqWords> {
  late Future<List<mLine>> _future_list_numword_pairs;
  late Timer _timer;

  final _biggerFont = const TextStyle(fontSize: 18);

  @override
  void initState() {
    super.initState();
    _timer = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        _future_list_numword_pairs = fetchmLines();
      });
    });
  }

  void _retry() {
    setState(() {
      _future_list_numword_pairs = fetchmLines();
    });
  }

  void reload() {
    setState(() {});
  }

  final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    var fb = FutureBuilder<List<mLine>>(
      future: _future_list_numword_pairs,
      builder: (BuildContext context, AsyncSnapshot<List<mLine>> snapshot) {
        Widget child;
        if (snapshot.hasData) {
          child = Column(
            children: <Widget>[
              Expanded(
                child: ListView.builder(
                  padding: const EdgeInsets.all(16.0),
                  itemCount: snapshot.data!.length,
                  itemBuilder: (context, i) {
                    return Column(
                      children: <Widget>[
                        ListTile(
                          title: Text(
                            "${snapshot.data![i].mMessage} | Like:${snapshot.data![i].mLikes}",
                            style: _biggerFont,
                          ),
                          trailing: ElevatedButton(
                            onPressed: () {
                              //updateLikes(snapshot.data![i].mId);
                              //_retry();
                              List<String> myarg = [];
                              myarg.add((snapshot.data![i].mId).toString());
                              myarg
                                  .add((snapshot.data![i].mSubject).toString());
                              myarg
                                  .add((snapshot.data![i].mMessage).toString());
                              myarg.add((snapshot.data![i].mLikes).toString());
                              myarg
                                  .add((snapshot.data![i].mCreated).toString());
                              Navigator.pushNamed(
                                context,
                                '/mymessage',
                                arguments: myarg,
                              );
                            },
                            child: Text('>>'),
                          ),
                        ),
                        Divider(height: 1.0),
                      ],
                    );
                  },
                ),
              ),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamed(context, '/insert');
                },
                child: Text('Add a message'),
              ),
              ElevatedButton(
                onPressed: () {
                  Navigator.pushNamed(context, '/SignInScreen');
                },
                child: Text('Log in'),
              ),
            ],
          );
        } else if (snapshot.hasError) {
          // newly added
          child = Text('${snapshot.error}');
        } else {
          // awaiting snapshot data, return simple text widget
          // child = Text('Calculating answer...');
          child = const CircularProgressIndicator(); //show a loading spinner.
        }
        return child;
      },
    );

    return fb;
  }
}

Future<List<mLine>> fetchmLines() async {
  final response = await http
      .get(Uri.parse('http://2023sp-team-m.dokku.cse.lehigh.edu/messages'));
  if (response.statusCode == 200) {
    final List<mLine> returnData;
    var res = jsonDecode(response.body);
    List<dynamic> mData = res['mData'];
    // ignore: unnecessary_type_check
    if (mData is List) {
      returnData = (mData).map((x) => mLine.fromJson(x)).toList();
    } else if (mData is Map) {
      returnData = <mLine>[mLine.fromJson(mData as Map<String, dynamic>)];
    } else {
      developer
          .log('ERROR: Unexpected json response type (was not a List or Map).');
      returnData = List.empty();
    }
    return returnData;
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Did not receive success status code from request.');
  }
}

// Future<List<User>> fetchUsers() async {
//   final response = await http
//       .get(Uri.parse('http://2023sp-team-m.dokku.cse.lehigh.edu/messages'));
//   if (response.statusCode == 200) {
//     final List<User> returnUser;
//     var res = jsonDecode(response.body);
//     List<dynamic> mData = res['mData'];
//     // ignore: unnecessary_type_check
//     if (mData is List) {
//       returnUser = (mData).map((x) => mLine.fromJson(x)).toList();
//     } else if (mData is Map) {
//       returnUser = <User>[User.fromJson(mData as Map<String, dynamic>)];
//     } else {
//       developer
//           .log('ERROR: Unexpected json response type (was not a List or Map).');
//       returnData = List.empty();
//     }
//     return returnData;
//   } else {
//     // If the server did not return a 200 OK response,
//     // then throw an exception.
//     throw Exception('Did not receive success status code from request.');
//   }
// }