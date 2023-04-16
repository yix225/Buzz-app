import 'package:flutter_application_1/comment.dart';
import 'package:flutter_application_1/main.dart';
import 'package:flutter_application_1/user.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  test('mLine should be created from json data', () {
    final jsonData = {
      "mId": 124,
      "mSubject": "testing dokku",
      "mMessage": "please work",
      "mLikes": 0,
      "mCreated": "Mar 10, 2023 8:10:10 PM"
    };
    final mline = mLine.fromJson(jsonData);
    expect(mline.mId, equals(124));
    expect(mline.mSubject, equals('testing dokku'));
    expect(mline.mMessage, equals('please work'));
    expect(mline.mLikes, equals(0));
    expect(mline.mCreated, equals('Mar 10, 2023 8:10:10 PM'));
  });

  test('mLine should be encoded to json', () {
    final mline = mLine(
      mId: 3,
      mSubject: 'Test Test',
      mMessage: 'Hello',
      mLikes: 9,
      mCreated: 'Mar 11, 2023 2:41:02 AM',
    );
    final jsonData = mline.toJson();
    expect(jsonData['mStatus'], equals('ok'));
    expect(jsonData['mData'], isNotNull);
    final mData = jsonData['mData'];
    expect(mData['mId'], equals(3));
    expect(mData['mSubject'], equals('Test Test'));
    expect(mData['mMessage'], equals('Hello'));
    expect(mData['mLikes'], equals(9));
    expect(mData['mCreated'], equals('Mar 11, 2023 2:41:02 AM'));
  });

  test('mUser should be created from json data', () {
    final jsonData = {
      "token": " ",
      "avatarUrl": " ",
      "mName": "tmessi",
      "mEmail": "tmessi2023@gmail.com",
      "mSexOrn": " ",
      "mGenId": " ",
      "mNote": " ",
      "id": "  "
    };
    final user = User.fromJson(jsonData);
    expect(user.name, equals('tmessi'));
    expect(user.email, equals('tmessi2023@gmail.com'));
    expect(user.identity, equals(' '));
    expect(user.sexOri, equals(' '));
    expect(user.description, equals(' '));
    expect(user.token, equals(' '));
    expect(user.avatarUrl, equals(' '));
    expect(user.id, equals(' '));
  });
  test('mLine should be encoded to json', () {
    final user = User(
      avatarUrl: ' ',
      description: ' ',
      email: 'tmessi2023@gmail.com',
      identity: ' ',
      name: 'tmessi',
      id: ' ',
      token: ' ',
      sexOri: ' ',
    );
    final jsonData = user.toJson();
    expect(jsonData['mStatus'], equals('ok'));
    expect(jsonData['mData'], isNotNull);
    final mData = jsonData['mData'];
    expect(mData['mName'], equals('tmessi'));
    expect(mData['mEmail'], equals('tmessi2023@gmail.com'));
    expect(mData['mSexOrn'], equals(' '));
    expect(mData['mGenId'], equals(' '));
    expect(mData['mNote'], equals(' '));
  });
  test('mUser should be created from json data', () {
    final jsonData = {
      "mContents": 'good',
      "mLike": 1,
      "mCreate": "April 16, 2023 2:41:02 AM ",
      "mId": 125
    };
    final comment = Comment.fromJson(jsonData);
    expect(comment.mId, equals(125));
    expect(comment.mContents, equals('good'));
    expect(comment.mLikes, equals(1));
    expect(comment.mCreated, equals('April 16, 2023 2:41:02 AM'));
  });
  test('mLine should be encoded to json', () {
    final comment = Comment(
      mContents: 'good',
      mCreated: 'April 16, 2023 2:41:02 AM',
      mId: 125,
      mLikes: 1,
    );
    final jsonData = comment.toJson();
    expect(jsonData['mStatus'], equals('ok'));
    expect(jsonData['mData'], isNotNull);
    final mData = jsonData['mData'];
    expect(mData['mId'], equals(125));
    expect(mData['mCotents'], equals('good'));
    expect(mData['mLikes'], equals(1));
    expect(mData['mCreated'], equals('April 16, 2023 2:41:02 AM'));
  });
}
