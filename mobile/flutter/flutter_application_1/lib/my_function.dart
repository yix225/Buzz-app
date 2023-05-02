import 'dart:convert';
import 'package:flutter_application_1/user.dart';
import 'package:http/http.dart' as http;
import 'package:http/http.dart';

void addMessage(String mySubject, String myMessage, String sessId) async {
  DateTime now = DateTime.now();
  String currentTime = now.toString();
  Map<String, String> headers = {'Content-Type': 'application/json'};
  Map<String, dynamic> payload = {
    'mSubject': mySubject,
    'mMessage': myMessage,
  };
  final response = await http.post(
    Uri.parse('http://10.0.2.2:8998/insertIdea/${sessId}'),
    headers: headers,
    body: jsonEncode(payload),
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to update like.');
  }
}

void addComment(String myComment, int id, int sessId) async {
  DateTime now = DateTime.now();
  String currentTime = now.toString();
  Map<String, String> headers = {'Content-Type': 'application/json'};
  Map<String, dynamic> payload = {
    'mSubject': '',
    'mMessage': myComment,
  };
  final response = await http.post(
    Uri.parse('http://10.0.2.2:8998/insertComment/:${id}/:${sessId}'),
    headers: headers,
    body: jsonEncode(payload),
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to update like.');
  }
}

void upvoteIdea(int myid, int sessid) async {
  // Update the mLikes field of the message object.
  final response = await http.put(Uri.parse(
      'http://10.0.2.2:8998//likeIdea/${myid}/${sessid}'));
  if (response.statusCode != 200) {
    throw Exception('Failed to update like.');
  }
}

void downvoteIdea(int myid, int sessid) async {
  // Update the mLikes field of the message object.
  final response = await http.put(Uri.parse(
      'http://10.0.2.2:8998//unlikeIdea/${myid}/${sessid}'));
  if (response.statusCode != 200) {
    throw Exception('Failed to update like.');
  }
}

void updateProfile(String? name, String? email, String genId, String sexOtn, String note, int sessId) async {
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
        'http://10.0.2.2:8998/profile/:${sessId}'),
    headers: headers,
    body: jsonEncode(payload),
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to update like.');
  }
}

// Future<String?> addSexOri(String? sexOri, int sessId) async {
  
//   Map<String, String> headers = {'Content-Type': 'application/json'};
//   Map<String, dynamic> payload = {
//     'oriSex': sexOri,
//   };
//   final response = await http.put(
//     Uri.parse(
//         'http://10.0.2.2:8998/profile/:${sessId}'),
//     headers: headers,
//     body: jsonEncode(payload),
//   );
//   if (response.statusCode != 200) {
//     throw Exception('Failed to update like.');
//   }
//   return User(
//           avatarUrl: '',
//           email: '',
//           name: '',
//           token: '',
//           identity: '',
//           sexOri: '',
//           id: 0,
//           description: '',
//           sid: sessId.toString())
//       .sexOri;
// }

// Future<String?> addGender(String? identity, int sessId) async {
//   print(identity! + " " + sessId.toString());
//   Map<String, String> headers = {'Content-Type': 'application/json'};
//   Map<String, dynamic> payload = {
//     'mSexOtn': identity,
//   };
//   final response = await http.put(
//     Uri.parse(
//         'http://10.0.2.2:8998/profile/:${sessId}'),
//     headers: headers,
//     body: jsonEncode(payload),
//   );
//   print(response.statusCode);
//   if (response.statusCode != 200) {
//     throw Exception('Failed to update like.');
//   }
//   return User(
//           avatarUrl: '',
//           email: '',
//           name: '',
//           token: '',
//           identity: '',
//           sexOri: '',
//           id: 0,
//           description: '',
//           sid: sessId.toString())
//       .identity;
// }

// Future<String?> addDescription(String? description, int sessId) async {
//   Map<String, String> headers = {'Content-Type': 'application/json'};
//   Map<String, dynamic> payload = {
//     'description': description,
//   };
//   final response = await http.put(
//     Uri.parse(
//         'http://10.0.2.2:8998/profile/:${sessId}'),
//     headers: headers,
//     body: jsonEncode(payload),
//   );
//   if (response.statusCode != 200) {
//     throw Exception('Failed to update like.');
//   }
//   return User(
//           avatarUrl: '',
//           email: '',
//           name: '',
//           token: '',
//           identity: '',
//           sexOri: '',
//           id: 0,
//           description: '',
//           sid: sessId.toString())
//       .identity;
// }
