import 'package:flutter/material.dart';
import 'package:flutter_application_1/my_function.dart';
import 'package:provider/provider.dart';
import 'user_data.dart';

class EditComment extends StatelessWidget {
  TextEditingController messageTextControl = TextEditingController();

  void dispose() {
    messageTextControl.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final user = Provider.of<UserData>(context);
    final List<String> idparams =
                ModalRoute.of(context)!.settings.arguments as List<String>;
    messageTextControl.text = idparams[2];
    return Scaffold(
      appBar: AppBar(
        title: const Text('Please enter your new message'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const SizedBox(height: 16),
            TextField(
              decoration: const InputDecoration(
                labelText: 'Edit Message:',
              ),
              controller: messageTextControl,
            ),
            const SizedBox(height: 32),
            ElevatedButton(
              onPressed: () {
                String input = messageTextControl.text;
                updateComment(input, int.parse(idparams[0]), int.parse(idparams[1]), user.sid);
                Navigator.pop(context);
              },
              child: const Text('Add'),
            ),
          ],
        ),
      ),
    );
  }
}
