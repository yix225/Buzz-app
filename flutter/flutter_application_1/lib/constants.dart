import 'dart:io'
    show Platform; // Importing the 'Platform' class from the 'dart:io' package

const REFRESH_TOKEN_KEY =
    'refresh_token'; // Constant variable to hold the key for the refresh token
const BACKEND_TOKEN_KEY =
    'backend_token'; // Constant variable to hold the key for the backend token
const GOOGLE_ISSUER =
    'https://accounts.google.com'; // Constant variable to hold the issuer for Google authentication
const GOOGLE_CLIENT_ID_IOS =
    '<IOS-CLIENT-ID>'; // Constant variable to hold the client ID for iOS app
const GOOGLE_REDIRECT_URI_IOS =
    'com.googleusercontent.apps.<IOS-CLIENT-ID>:/oauthredirect'; // Constant variable to hold the redirect URI for iOS app
const GOOGLE_CLIENT_ID_ANDROID =
    '926558226206-ppmn3bk4ckvrtaq6hun9kpi034sde366.apps.googleusercontent.com'; // Constant variable to hold the client ID for Android app
const GOOGLE_REDIRECT_URI_ANDROID =
    '926558226206-ppmn3bk4ckvrtaq6hun9kpi034sde366.apps.googleusercontent.com:/oauthredirect'; // Constant variable to hold the redirect URI for Android app

// A function to return the client ID based on the platform
String clientID() {
  if (Platform.isAndroid) {
    return GOOGLE_CLIENT_ID_ANDROID;
  } else if (Platform.isIOS) {
    return GOOGLE_CLIENT_ID_IOS;
  }
  return '';
}

// A function to return the redirect URI based on the platform
String redirectUrl() {
  if (Platform.isAndroid) {
    return GOOGLE_REDIRECT_URI_ANDROID;
  } else if (Platform.isIOS) {
    return GOOGLE_REDIRECT_URI_IOS;
  }
  return '';
}

/* 
class GoogleSignInButton extends StatefulWidget {
  @override
  _GoogleSignInButtonState createState() => _GoogleSignInButtonState();
}

class _GoogleSignInButtonState extends State<GoogleSignInButton> {
  bool _isSigningIn = false;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16.0),
      child: _isSigningIn
          ? CircularProgressIndicator(
              valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
            )
          : OutlinedButton(
              style: ButtonStyle(
                backgroundColor: MaterialStateProperty.all(Colors.white),
                shape: MaterialStateProperty.all(
                  RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(40),
                  ),
                ),
              ),
              onPressed: () async {
                setState(() {
                  _isSigningIn = true;
                });
                
                // TODO: Add method call to the Google Sign-In authentication

                setState(() {
                  _isSigningIn = false;
                });
              },
              child: Padding(
                padding: const EdgeInsets.fromLTRB(0, 10, 0, 10),
                child: Row(
                  mainAxisSize: MainAxisSize.min,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    Image(
                      image: AssetImage("assets/google_logo.png"),
                      height: 35.0,
                    ),
                    Padding(
                      padding: const EdgeInsets.only(left: 10),
                      child: Text(
                        'Sign in with Google',
                        style: TextStyle(
                          fontSize: 20,
                          color: Colors.black54,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    )
                  ],
                ),
              ),
            ),
    );
  }
}

onPressed: () async {
  setState(() {
    _isSigningIn = true;
  });

  User? user =
      await Authentication.signInWithGoogle(context: context);

  setState(() {
    _isSigningIn = false;
  });

  if (user != null) {
    Navigator.of(context).pushReplacement(
      MaterialPageRoute(
        builder: (context) => UserInfoScreen(
          user: user,
        ),
      ),
    );
  }
} */