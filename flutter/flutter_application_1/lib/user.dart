import 'dart:convert';

class User {
// the variable we will use in the following files
  final String id;
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
        name: json['name'] as String,
        email: json['email'] as String,
        sexOri: json['sexOri'] as String,
        identity: json['identity'] as String,
        description: json['description'] as String,
        id: json['mId'] as String);
  }
  Map<String, dynamic> toJson() {
    final Map<String, dynamic> user = new Map<String, dynamic>();
    user['mStatus'] = 'ok';
    user['mData'] = {
      'token': token,
      'avatarUrl': avatarUrl,
      'name': name,
      'sexOri': sexOri,
      'identity': identity,
      'description': description,
      'id': id
    };
    print(jsonEncode(user));
    return user;
  }
}
