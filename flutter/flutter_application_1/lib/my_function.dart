import 'dart:convert';
import 'package:http/http.dart' as http;

void addMessage(String mySubject, String myMessage) async {
  DateTime now = DateTime.now();
  String currentTime = now.toString();
  Map<String, String> headers = {'Content-Type': 'application/json'};
  Map<String, dynamic> payload = {
    'mId': 0,
    'mSubject': mySubject,
    'mMessage': myMessage,
    'mLikes': 0,
    'mCreated': currentTime
  };
  final response = await http.post(
    Uri.parse('http://2023sp-team-m.dokku.cse.lehigh.edu/messages'),
    headers: headers,
    body: jsonEncode(payload),
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to update like.');
  }
}

void update_Likes(int myid) async {
  // Update the mLikes field of the message object.
  final response = await http.put(Uri.parse(
      'http://2023sp-team-m.dokku.cse.lehigh.edu/messages/${myid}/like'));
  if (response.statusCode != 200) {
    throw Exception('Failed to update like.');
  }
}

void update_unLikes(int myid) async {
  // Update the mLikes field of the message object.
  final response = await http.put(Uri.parse(
      'http://2023sp-team-m.dokku.cse.lehigh.edu/messages/${myid}/unlike'));
  if (response.statusCode != 200) {
    throw Exception('Failed to update like.');
  }
}
