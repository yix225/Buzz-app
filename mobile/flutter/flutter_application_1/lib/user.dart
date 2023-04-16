import 'dart:convert';

class User {
// the variable we will use in the following files
  late String id;
  late String token;
  late String avatarUrl;
  late String name;
  late String email;
  late String sexOri;
  late String identity;
  late String description;
  User(
      {required this.token,
      required this.avatarUrl,
      required this.name,
      required this.email,
      required this.sexOri,
      required this.identity,
      required this.description,
      required this.id});
  factory User.fromJson(Map<String, dynamic> json) {
    return User(
        token: json['token'] as String,
        avatarUrl: json['avatarUrl'] as String,
        name: json['mName'] as String,
        email: json['mEmail'] as String,
        sexOri: json['mSexOrn'] as String,
        identity: json['mGenId'] as String,
        description: json['mNote'] as String,
        id: json['mId'] as String);
  }
  Map<String, dynamic> toJson() {
    final Map<String, dynamic> user = new Map<String, dynamic>();
    user['mStatus'] = 'ok';
    user['mData'] = {
      'token': token,
      'avatarUrl': avatarUrl,
      'mName': name,
      'mEmail': email,
      'mSexOrn': sexOri,
      'mGenId': identity,
      'mNote': description,
      'id': id
    };
    print(jsonEncode(user));
    return user;
  }
}
