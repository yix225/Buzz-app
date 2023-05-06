import 'dart:convert';
import 'package:http/http.dart' as http;

void addMessage(String mySubject, String myMessage, String sessid) async {
  Map<String, String> headers = {'Content-Type': 'application/json'};
  Map<String, dynamic> payload = {
    'mSubject': mySubject,
    'mMessage': myMessage,
  };
  final response = await http.post(
    Uri.parse('http://10.0.2.2:8998//insertIdea/$sessid'),
    headers: headers,
    body: jsonEncode(payload),
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to add message.');
  }
}

void addFileIdea(String filepath, String description, String type, int ideaid, String sessid) async {
  Map<String, String> headers = {'Content-Type': 'application/json'};
  Map<String, dynamic> payload = {
    'mFilePath': filepath,
    'mDescription': description,
    'mFileType': type,
  };
  final response = await http.post(
    Uri.parse('http://10.0.2.2:8998//insertFileIdea/$ideaid/$sessid'),
    headers: headers,
    body: jsonEncode(payload),
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to upload file.');
  }
}

void addComment(String myComment, int ideaid, int sessid) async {
  Map<String, String> headers = {'Content-Type': 'application/json'};
  Map<String, dynamic> payload = {
    'mSubject': '',
    'mMessage': myComment,
  };
  final response = await http.post(
    Uri.parse('http://10.0.2.2:8998//insertComment/$ideaid/$sessid'),
    headers: headers,
    body: jsonEncode(payload),
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to add comment.');
  }
}

void updateComment(String myMessage, int ideaid, int commentid, String sessid) async {
  Map<String, String> headers = {'Content-Type': 'application/json'};
  Map<String, dynamic> payload = {
    'mMessage': myMessage,
  };
  final response = await http.put(
    Uri.parse('http://10.0.2.2:8998//updateComment/$ideaid/$commentid/$sessid'),
    headers: headers,
    body: jsonEncode(payload),
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to update comment.');
  }
}

void upvoteIdea(int ideaid, int sessid) async {
  // Update the mLikes field of the message object.
  final response = await http.put(Uri.parse(
      'http://10.0.2.2:8998//likeIdea/$ideaid/$sessid'));
  if (response.statusCode != 200) {
    throw Exception('Failed to upvote idea.');
  }
}

void downvoteIdea(int ideaid, int sessid) async {
  // Update the mLikes field of the message object.
  final response = await http.put(Uri.parse(
      'http://10.0.2.2:8998//unlikeIdea/$ideaid/$sessid'));
  if (response.statusCode != 200) {
    throw Exception('Failed to downvote idea.');
  }
}


void upvoteComment(int ideaid, int sessid, int commentid) async {
  // Update the mLikes field of the message object.
  final response = await http.put(Uri.parse(
      'http://10.0.2.2:8998//likeComment/$ideaid/$commentid/$sessid'));
  if (response.statusCode != 200) {
    throw Exception('Failed to upvote comment.');
  }
}

void downvoteComment(int ideaid, int sessid, int commentid) async {
  // Update the mLikes field of the message object.
  final response = await http.put(Uri.parse(
      'http://10.0.2.2:8998//unlikeComment/$ideaid/$commentid/$sessid'));
  if (response.statusCode != 200) {
    throw Exception('Failed to downvote comment.');
  }
}

void updateProfile(String? name, String? email, String genId, String sexOtn, String note, int sessid) async {
  Map<String, String> headers = {'Content-Type': 'application/json'};
  Map<String, dynamic> payload = {
    'mName': name,
    'mEmail': email,
    'mGenId': genId,
    'mSexOtn': sexOtn,
    'mNote': note,
  };
  final response = await http.put(
    Uri.parse(
        'http://10.0.2.2:8998//profile/$sessid'),
    headers: headers,
    body: jsonEncode(payload),
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to update profile.');
  }
}