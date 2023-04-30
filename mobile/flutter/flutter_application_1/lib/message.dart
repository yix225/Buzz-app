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



class message extends StatelessWidget {
  @override
  late Timer _timer;
  late final Comment _comment;
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

  Widget build(BuildContext context) {
    final List<String> message =
        ModalRoute.of(context)!.settings.arguments as List<String>;
    final List<String> comment =
        ModalRoute.of(context)!.settings.arguments as List<String>;
    return Scaffold(
      appBar: AppBar(
        title: Text('Message Info'),
      ),
      body: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Tooltip(
              message: message[0],
              child: InkWell(
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => UserInfoScreen()),
                  );
                },
                child: ListTile(
                  title: Text(
                            "${message[0]}\n"
                            "${message[1]}", 
                            style: TextStyle(fontSize: 18),
                          ),
                          trailing: Text(
                            "\n${message[2]}\n"
                            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLikes:${message[3]}\n"),
                          subtitle: Text("${message[4]}"),
                          
                ),
              ),
            ),
            Center(
              child: ElevatedButton.icon(
                icon: SizedBox(
                  width: 20,
                  height: 20,
                  child: Image.asset('images/upvote.png'),
                ),
                onPressed: () {
                  _onButtonPressed(int.parse(message[0]));
                },
                label: const Text('upvote'),
                //child: Text('Up vote'),

                //child: Image.asset('images/upvote.png'),
              ),
            ),
            Center(
              child: ElevatedButton.icon(
                icon: SizedBox(
                  width: 20,
                  height: 20,
                  child: Image.asset('images/downvote.png'),
                ),
                onPressed: () {
                  _onButtonPressed2(int.parse(message[0]));
                },
                label: const Text('downvote'),
                //child: Text('Down Vote'),
                //child: Image.asset('images/downvote.png'),
              ),
            ),
            Center(
              child: ElevatedButton(
                onPressed: () {
                  Navigator.of(context).pushReplacement(
                      // go to the profile page
                      MaterialPageRoute(
                          builder: (context) =>
                              commentPage(mid: message[0] as String)));
                  //_onButtonPressed(int.parse(message[0]));
                },
                child: Text('add the comment'),
              ),
            ),
            Center(
              child: ElevatedButton(
                onPressed: () {
                  Navigator.of(context).pushReplacement(
                      // go to the profile page
                      MaterialPageRoute(builder: (context) => commentList()));
                  //_onButtonPressed(int.parse(message[0]));
                },
                child: Text('Comments List'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}


Future<List<mLine>> fetchmLines() async {
  //print("1");
  final response = await http
      .get(Uri.parse('https://2023sp-team-m.dokku.cse.lehigh.edu/GetComments'));
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

Future<List<Comment>> fetchComment() async {
  final response =
      await http.get(Uri.parse('http://2023sp-team-m.dokku.cse.lehigh.edu/'));
  if (response.statusCode == 200) {
    final List<Comment> returnComment;
    var res = jsonDecode(response.body);
    List<dynamic> mData = res['mData'];
    // ignore: unnecessary_type_check
    if (mData is List) {
      returnComment = (mData).map((x) => Comment.fromJson(x)).toList();
    } else if (mData is Map) {
      returnComment = <Comment>[
        Comment.fromJson(mData as Map<String, dynamic>)
      ];
    } else {
      developer
          .log('ERROR: Unexpected json response type (was not a List or Map).');
      returnComment = List.empty();
    }
    return returnComment;
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Did not receive success status code from request.');
  }
}

class MyHttpOverrides extends HttpOverrides {
  @override
  HttpClient createHttpClient(SecurityContext? context) {
    return super.createHttpClient(context)
      ..badCertificateCallback =
          (X509Certificate cert, String host, int port) => true;
  }
}
