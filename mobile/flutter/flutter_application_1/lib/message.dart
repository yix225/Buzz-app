// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables

import 'dart:async';
import 'package:provider/provider.dart';
import 'user_data.dart';
import 'package:flutter/material.dart';
import 'package:flutter_application_1/user_info_screen.dart';
import 'package:flutter_application_1/my_function.dart';
import 'package:flutter_application_1/insert_message.dart';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:url_launcher/url_launcher.dart';
import 'package:image_picker/image_picker.dart';
import 'package:provider/provider.dart';
import 'package:file_picker/file_picker.dart';
import 'package:mime/mime.dart';
import 'user_data.dart';
import 'dart:io';


class Comment {
  final int mId;
  String mMessage;
  int mLikes;
  final int mUserId;
  final String mCreated;
  Comment({
    required this.mId,
    required this.mMessage,
    required this.mLikes,
    required this.mUserId,
    required this.mCreated,
  });
  factory Comment.fromJson(Map<String, dynamic> json) {
    return Comment(
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
      'mId': mId,
      'mMessage': mMessage,
      'mLikes': mLikes,
      'mUserId': mUserId,
      'mCreated': mCreated
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
  File? file; String? base64;
  late Future<List<Comment>> _future_list_numword_pairs;
  late Timer timer;

  @override
  void initState() {
    super.initState();
    Timer timer = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        _future_list_numword_pairs = fetchComments(context);
      });
    });
  }

  void _retry() {
    setState(() {
      _future_list_numword_pairs = fetchComments(context);
    });
  }

  void reload() {
    setState(() {});
  }

  TextEditingController messageTextControl = TextEditingController();

    openFiles() async {
    final result = await FilePicker.platform.pickFiles();
    file = File(result!.files.first.path.toString());
        if(file != null){
      List<int> bytes = await file!.readAsBytes();
      print(bytes);
      base64 = base64Encode(bytes);
      print(base64);
    }
    setState(() {});
  }

  openCamera() async {
    final result = await ImagePicker().pickImage(source: ImageSource.camera);
    file = File(result!.path);
    if(file != null){
      List<int> bytes = await file!.readAsBytes();
      print(bytes);
      base64 = base64Encode(bytes);
      print(base64);
    }
    setState(() {});
  }

   @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserData>(context);
    var fb = FutureBuilder<List<Comment>>(
        future: _future_list_numword_pairs,
        builder: (BuildContext context, AsyncSnapshot<List<Comment>> snapshot) {
          final List<String> message = ModalRoute.of(context)!.settings.arguments as List<String>;
          final linkMessage = message[6];
          List<String> links = [];
          RegExp exp = new RegExp(r'(?:(?:https?|ftp):\/\/)?[\w/\-?=%.]+\.[\w/\-?=%.]+');
          Iterable<RegExpMatch> matches = exp.allMatches(linkMessage);

          matches.forEach((match) {
            links.add(linkMessage.substring(match.start, match.end));
          });
          Widget child;
            child = Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Tooltip(
                  message: message[0],
                  child: InkWell(
                    onTap: () {
                      if(int.parse(message[1]) ==  user.userId){
                        print("here");
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => UserInfoScreen()),
                        );
                      } else {
                        String myarg = message[1];
                        Navigator.pushNamed(
                          context,
                          '/profile',
                          arguments: myarg,
                        );
                      }
                    },
                    child: ListTile(
                      title: Text(
                        "${message[1]}\n"
                        "${message[2]}",
                        style: TextStyle(fontSize: 18),
                      ),
                      trailing: Text("\n${message[3]}\n"
                          "\t\t\t\t\t\t\tLikes:${message[4]}\t\tComments:${message[5]}\n"),
                      subtitle: Column( children: <Widget>[
                          Text("${message[6]}"),
                          for(String link in links)
                            TextButton(
                              onPressed: () async{
                                var launchable = await canLaunchUrl(Uri.parse(link));
                                if(launchable){
                                  await launchUrl(Uri.parse(link));
                                }
                                else{
                                  print("not launcable");
                                }
                              },
                              child: Text(link)
                            )
                        ]
                      ),
                    ),
                  ),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children:<Widget>[
                    ElevatedButton.icon(
                      icon: SizedBox(
                        width: 20,
                        height: 20,
                        child: Image.asset('images/upvote.png'),
                      ),
                      onPressed: () {
                        upvoteIdea(int.parse(message[0]), int.parse(user.sid));
                      },
                      label: const Text('upvote'),
                    ),
                    ElevatedButton.icon(
                      icon: SizedBox(
                        width: 20,
                        height: 20,
                        child: Image.asset('images/downvote.png'),
                      ),
                      onPressed: () {
                        downvoteIdea(int.parse(message[0]), int.parse(user.sid));
                      },
                      label: const Text('downvote'),
                    ),
                  ] 
                ),
                if(int.parse(message[5]) > 0)...{
                  Expanded(
                    child: ListView.builder(
                      padding: const EdgeInsets.all(16.0),
                      itemCount: snapshot.data!.length,
                      itemBuilder: (context, i){
                        final linkMessage = snapshot.data![i].mMessage;
                        List<String> links = [];
                        RegExp exp = new RegExp(r'(?:(?:https?|ftp):\/\/)?[\w/\-?=%.]+\.[\w/\-?=%.]+');
                        Iterable<RegExpMatch> matches = exp.allMatches(linkMessage);

                        matches.forEach((match) {
                          links.add(linkMessage.substring(match.start, match.end));
                        });
                        return Column(
                          children: <Widget>[
                            ListTile(
                              title: Text(
                                "${snapshot.data![i].mUserId}\n"
                              ),
                              trailing: Text("\n${snapshot.data![i].mCreated}\n"
                                  "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLikes:${snapshot.data![i].mLikes}\n"),
                              subtitle: Column( children: <Widget>[
                                Text("${snapshot.data![i].mMessage}"),
                                for(String link in links)
                                  TextButton(
                                    onPressed: () async{
                                      var launchable = await canLaunchUrl(Uri.parse(link));
                                      if(launchable){
                                        await launchUrl(Uri.parse(link));
                                      }
                                      else{
                                        print("not launcable");
                                      }
                                    },
                                    child: Text(link)
                                  )
                                ]
                              ),
                              onTap: () {
                                if(snapshot.data![i].mUserId ==  user.userId){
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) => UserInfoScreen()),
                                  );
                                } else {
                                  String myarg = (snapshot.data![i].mUserId).toString();
                                  Navigator.pushNamed(
                                    context,
                                    '/profile',
                                    arguments: myarg,
                                  );
                                }
                              },
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children:<Widget>[
                                ElevatedButton.icon(
                                  icon: SizedBox(
                                    width: 20,
                                    height: 20,
                                    child: Image.asset('images/upvote.png'),
                                  ),
                                  onPressed: () {
                                    upvoteComment(int.parse(message[0]), int.parse(user.sid), snapshot.data![i].mId);
                                  },
                                  label: const Text('upvote'),
                                ),
                                ElevatedButton.icon(
                                  icon: SizedBox(
                                    width: 20,
                                    height: 20,
                                    child: Image.asset('images/downvote.png'),
                                  ),
                                  onPressed: () {
                                    downvoteComment(int.parse(message[0]), int.parse(user.sid), snapshot.data![i].mId);
                                  },
                                  label: const Text('downvote'),
                                ),
                                if(snapshot.data![i].mUserId ==  user.userId)...{
                                  ElevatedButton(
                                    onPressed: () {
                                      List<String> args = [message[0], snapshot.data![i].mId.toString(), snapshot.data![i].mMessage];
                                      Navigator.pushNamed(
                                        context, '/edit',
                                        arguments: args,);
                                    },
                                    child: Text('Edit'),
                                  ),
                                }
                              ] 
                            ),
                          ],
                        );
                      },
                    )
                  ),
                } else...{
                  Center(
                    child: ListTile(
                      title: Text(
                          "No Comments :/"
                      )
                    )
                  ),
                }, 
                TextField(
                  decoration: InputDecoration(
                    contentPadding: 
                      new EdgeInsets.symmetric(vertical:5.0, horizontal: 8.0),
                    labelText: 'Enter your Comment here:',
                    fillColor: Colors.white,
                    filled: true,
                  ),
                  controller: messageTextControl,
                  onSubmitted: (String str){
                    str = messageTextControl.text;
                    addComment(messageTextControl.text, int.parse(message[0].toString()), int.parse(user.sid));
                  },
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsetsDirectional.all(6),
                      child: Container(
                        width: 100.0,
                        height: 100.0,
                        decoration: BoxDecoration(
                          border: Border.all(color: Colors.deepPurple, width: 6)
                        ),
                        child: TextButton(
                          child: Image.asset('images/camera.png'),
                          onPressed: () {
                            openCamera();
                          }
                        ),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsetsDirectional.all(6),
                      child: Container(
                        width: 100.0,
                        height: 100.0,
                        decoration: BoxDecoration(
                          border: Border.all(color: Colors.deepPurple, width: 6)
                        ),
                        child: TextButton(
                          child: Image.asset('images/attach.png'),
                          onPressed: () {
                            openFiles();
                          }
                        ),
                      ),
                    ),
                  ]
                ),
                if(file != null)...{
                  Text('File: ${file!.path.split('/').last}'),
                  ElevatedButton(
                    onPressed: (){
                      file = null;
                      setState(() {});
                    },
                    child: Text("Remove"),
                  )
                }
              ],
            );
            return child;  
          } 
        );
      return fb;   
    }
  }

class Message extends StatefulWidget {
  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".
  @override
  State<Message> createState() => _MessageState();
}

class _MessageState extends State<Message> {
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



Future<List<Comment>> fetchComments(BuildContext context) async {
  //print("1");
  final List<String> message =
      ModalRoute.of(context)!.settings.arguments as List<String>;
  final response = await http
      .get(Uri.parse('http://10.0.2.2:8998//GetComment/${message[0]}'));
  
  if (response.statusCode == 200) {
    final List<Comment> returnData;
    var res = jsonDecode(response.body);
    List<dynamic> mData = res['mData'];
    // ignore: unnecessary_type_check
    if (mData is List) {
      returnData = (mData).map((x) => Comment.fromJson(x)).toList();
    } else if (mData is Map) {
      returnData = <Comment>[Comment.fromJson(mData as Map<String, dynamic>)];
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
