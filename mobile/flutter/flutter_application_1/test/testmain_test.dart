import 'package:flutter_application_1/main.dart';
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
}
