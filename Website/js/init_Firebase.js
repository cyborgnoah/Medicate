// Initialize Firebase after validation

var app;
var config = {
  apiKey: "AIzaSyB0BwBC6aiv2oB4d-esbkPiRxHvzumFYOY",
  authDomain: "medicate-c8086.firebaseapp.com",
  databaseURL: "https://medicate-c8086.firebaseio.com",
  projectId: "medicate-c8086",
  storageBucket: "medicate-c8086.appspot.com",
  messagingSenderId: "71702109131"
};

if (!firebase.apps.length) {
    app=firebase.initializeApp(config);
}
