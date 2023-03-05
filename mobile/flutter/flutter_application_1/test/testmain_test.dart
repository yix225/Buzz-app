import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart' as http;
import 'package:http/testing.dart';
import 'dart:convert';
import 'package:mockito/mockito.dart';

import 'package:flutter_application_1/testmain.dart';

class MockClient extends Mock implements http.Client {}

void main() {
  group('MyApp', () {
    testWidgets('renders MyApp widget', (WidgetTester tester) async {
      await tester.pumpWidget(const MyApp());
      expect(find.byType(MyHomePage), findsOneWidget);
    });
  });
}
