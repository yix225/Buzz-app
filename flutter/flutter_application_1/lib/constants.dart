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
