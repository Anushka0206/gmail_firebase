## Gmail Firebase Android App
An Android application that integrates Firebase services for functionalities like user authentication and email management.

##### Features
User Authentication: Sign up and log in using email and password.

Email Management: Compose, send, and receive emails within the app.

Real-time Updates: Utilize Firebase Realtime Database for live data synchronization.

User Profile: Manage user profiles with Firebase Firestore.

##### Technologies Used
Programming Language: Java/Kotlin

Firebase Services:

Authentication

Realtime Database

Firestore

Cloud Functions (if applicable)

### Setup Instructions
##### Clone the Repository:

git clone https://github.com/Anushka0206/gmail_firebase.git
##### Open in Android Studio:
Open the cloned project in Android Studio.

##### Configure Firebase:

Create a new project in Firebase Console.

Add an Android app to your Firebase project using your app's package name.

Download the google-services.json file provided by Firebase.

Place the google-services.json file in the app/ directory of your Android project.

##### Enable Firebase Services:

In the Firebase Console, enable the necessary services:

Authentication (Email/Password)

Realtime Database

Firestore

Cloud Functions (if used)

##### Add Firebase SDKs:

In your project's build.gradle files, add the required Firebase SDK dependencies.

Ensure that the Google Services plugin is applied in your project-level build.gradle file.

##### Run the App:

Build and run the app on an emulator or physical device.
