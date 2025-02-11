SMS Reader Android App

Overview

This Android application reads SMS messages from the device's inbox and displays them in real-time. It fetches all SMS messages when started and keeps listening for new incoming messages. Users can also filter SMS messages dynamically by entering a sender ID (phone number).

Features

Fetch and display all SMS messages on startup 
Listen for new incoming SMS and update UI instantly 
Display SMS Body, Sender ID, and Sent Time 
Dynamically filter messages by Sender ID 
Uses BroadcastReceiver to listen for new SMS events

Tech Stack

Kotlin 
BroadcastReceiver for SMS listening
RecyclerView for displaying messages

Permissions Required
To read SMS messages, add these permissions to your AndroidManifest.xml:
<uses-permission android:name="android.permission.RECEIVE_SMS" />
<uses-permission android:name="android.permission.READ_SMS" />

Setup & Installation
Clone the repository:
git clone https://github.com/anuj-2255/MessagingApp.git

Open in Android Studio
Run the app on a real device (Emulator might not support SMS reception)
Grant SMS Permissions when prompted

📲 How It Works

The app reads SMS messages from the device inbox on startup.
A BroadcastReceiver listens for new SMS messages in real-time.
The UI updates with the latest messages.
Users can filter SMS by sender ID dynamically.
