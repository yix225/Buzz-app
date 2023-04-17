import 'dart:convert';

class Comment {
  late int mId;
  late String mContents;
  late int mLikes;
  late String mCreated;

  Comment({
    required this.mId,
    required this.mContents,
    required this.mLikes,
    required this.mCreated,
  });

  factory Comment.fromJson(Map<String, dynamic> json) {
    return Comment(
      mId: json['mId'],
      mContents: json['mContents'],
      mLikes: json['mLikes'],
      mCreated: json['mCreated'],
    );
  }
  Map<String, dynamic> toJson() {
    final Map<String, dynamic> comment = new Map<String, dynamic>();
    comment['mStatus'] = 'ok';
    comment['mData'] = {
      'mId': mId,
      'mContents': mContents,
      'mLikes': mLikes,
      'mCreated': mCreated,
    };
    print(jsonEncode(comment));
    return comment;
  }
}
