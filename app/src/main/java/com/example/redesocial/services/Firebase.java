package com.example.redesocial.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Firebase extends FirebaseMessagingService {

    public Firebase(){
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SessionManager.setToken(this, s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
