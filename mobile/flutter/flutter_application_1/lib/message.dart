// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables

import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_application_1/UserInfoScreen.dart';
import 'package:flutter_application_1/my_function.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';

import 'comment.dart';
import 'commentList.dart';
import 'commentPage.dart';
// import 'commentList.dart';

class mLine {
  final int mId;
  String mMessage;
  int mLikes;
  final int mUserId;
  final String mCreated;
  mLine({
    required this.mId,
    required this.mMessage,
    required this.mLikes,
    required this.mUserId,
    required this.mCreated,
  });
  factory mLine.fromJson(Map<String, dynamic> json) {
    return mLine(
      mId: json['mId'],
      mMessage: json['mMessage'],
      mLikes: json['mLikes'],
      mUserId: json['mUserId'],
      mCreated: json['mCreated'],
    );
  }
  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['mStatus'] = 'ok';
    data['mData'] = {
      'mId': this.mId,
      'mMessage': this.mMessage,
      'mLikes': this.mLikes,
      'mUserId': this.mUserId,
      'mCreated': this.mCreated
    };
    print(jsonEncode(data));
    return data;
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
    Timer _timer = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        _future_list_numword_pairs = fetchmLines(context);
      });
    });
  }

  void _retry() {
    setState(() {
      _future_list_numword_pairs = fetchmLines(context);
    });
  }

  void reload() {
    setState(() {});
  }

  final TextEditingController _controller = TextEditingController();

  @override
  bool _isButtonPressed = false;

  void _onButtonPressed(int mid) {
    update_Likes(mid);
    print('You like this message');
  }

  void _onButtonPressed2(int mid) {
    _isButtonPressed = false;
    update_disLikes(mid);
    print('You unlike this message');
  }

   @override
  Widget build(BuildContext context) {
    var fb = FutureBuilder<List<mLine>>(
        future: _future_list_numword_pairs,
        builder: (BuildContext context, AsyncSnapshot<List<mLine>> snapshot) {
          final List<String> message =
                ModalRoute.of(context)!.settings.arguments as List<String>;
          Widget child;
          if(snapshot.hasData){
            child = Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Tooltip(
                    message: message[0],
                    child: InkWell(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => UserInfoScreen()),
                        );
                      },
                      child: ListTile(
                        title: Text(
                          "${message[1]}\n"
                          "${message[2]}",
                          style: TextStyle(fontSize: 18),
                        ),
                        trailing: Text("\n${message[3]}\n"
                            "\t\t\t\t\t\t\tLikes:${message[4]}\t\tComments:${message[5]}\n"),
                        subtitle: Text("${message[6]}"),
                      ),
                    ),
                  ),
                  Expanded(
                    child: ListView.builder(
                      padding: const EdgeInsets.all(16.0),
                      itemCount: snapshot.data!.length,
                      itemBuilder: (context, i){
                        return Column(
                          children: <Widget>[
                            ListTile(
                              title: Text(
                                "${snapshot.data![i].mUserId}\n"
                              ),
                              trailing: Text("\n${snapshot.data![i].mCreated}\n"
                                  "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLikes:${snapshot.data![i].mLikes}\n"),
                              subtitle: Text("${snapshot.data![i].mMessage}")
                            )
                          ],
                        );
                      },
                    )
                  ),
                  // Center(
                  //   child: ElevatedButton.icon(
                  //     icon: SizedBox(
                  //       width: 20,
                  //       height: 20,
                  //       child: Image.asset('images/upvote.png'),
                  //     ),
                  //     onPressed: () {
                  //       _onButtonPressed(int.parse(message[0]));
                  //     },
                  //     label: const Text('upvote'),
                  //     //child: Text('Up vote'),

                  //     //child: Image.asset('images/upvote.png'),
                  //   ),
                  // ),
                  // Center(
                  //   child: ElevatedButton.icon(
                  //     icon: SizedBox(
                  //       width: 20,
                  //       height: 20,
                  //       child: Image.asset('images/downvote.png'),
                  //     ),
                  //     onPressed: () {
                  //       _onButtonPressed2(int.parse(message[0]));
                  //     },
                  //     label: const Text('downvote'),
                  //     //child: Text('Down Vote'),
                  //     //child: Image.asset('images/downvote.png'),
                  //   ),
                  // ),
                  // Center(
                  //   child: ElevatedButton(
                  //     onPressed: () {
                  //       Navigator.of(context).pushReplacement(
                  //           // go to the profile page
                  //           MaterialPageRoute(
                  //               builder: (context) =>
                  //                   commentPage(mid: message[0])));
                  //       //_onButtonPressed(int.parse(message[0]));
                  //     },
                  //     child: Text('add the comment'),
                  //   ),
                  // ),
                  // Center(
                  //   child: ElevatedButton(
                  //     onPressed: () {
                  //       Navigator.of(context).pushReplacement(
                  //           // go to the profile page
                  //           MaterialPageRoute(
                  //               builder: (context) => commentList()));
                  //       //_onButtonPressed(int.parse(message[0]));
                  //     },
                  //     child: Text('Comments List'),
                  //   ),
                  // ),
                ],
              );
            } else{
              child = Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Tooltip(
                    message: message[0],
                    child: InkWell(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => UserInfoScreen()),
                        );
                      },
                      child: ListTile(
                        title: Text(
                          "${message[1]}\n"
                          "${message[2]}",
                          style: TextStyle(fontSize: 18),
                        ),
                        trailing: Text("\n${message[3]}\n"
                            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLikes:${message[4]}\n"),
                        subtitle: Text("${message[5]}"),
                      ),
                    ),
                  ),
                  Center(
                    child: ListTile(
                      title: Text(
                          "No Comments :/"
                      )
                    )
                  )
                ],
              );
            }
          return child;       
        }  
      );
    return fb;
  }
}

class message extends StatefulWidget {
  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".
  @override
  State<message> createState() => _messagestate();
}

class _messagestate extends State<message> {
  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
              title: Text('Message Info'),
            ),
      body: const Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: HttpReqWords(),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}



Future<List<mLine>> fetchmLines(BuildContext context) async {
  //print("1");
  final List<String> message =
      ModalRoute.of(context)!.settings.arguments as List<String>;
  final response = await http
      .get(Uri.parse('http://10.0.2.2:8998/GetComment/${message[0]}'));
  
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
