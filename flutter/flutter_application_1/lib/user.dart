class User {
// the variable we will use in the following files
  String? token;
  String? avatarUrl;
  String? name;
  String? email;
  String? sexOri;
  String? identity;
  User(
      {this.token,
      this.avatarUrl,
      this.name,
      this.email,
      this.sexOri,
      this.identity});
  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      token: json['token'],
      avatarUrl: json['avatarUrl'],
      name: json['name'],
      email: json['email'],
      sexOri: json['sexOri'],
      identity: json['identity'],
    );
  }
}
