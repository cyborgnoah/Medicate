// Initialize Firebase after validation
var config = {
  apiKey: "AIzaSyB0BwBC6aiv2oB4d-esbkPiRxHvzumFYOY",
  authDomain: "medicate-c8086.firebaseapp.com",
  databaseURL: "https://medicate-c8086-ee60d.firebaseio.com",
  projectId: "medicate-c8086",
  storageBucket: "medicate-c8086.appspot.com",
  messagingSenderId: "71702109131"
};

if (!firebase.apps.length) {
    firebase.initializeApp(config);
}
