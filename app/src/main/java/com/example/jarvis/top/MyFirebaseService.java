package com.example.jarvis.top;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.Main.Main;
import com.example.jarvis.top.Utils.NotificationUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Objects;

import static com.example.jarvis.top.Utils.SafeLog.Logd;

public class MyFirebaseService extends FirebaseMessagingService {
    public MyFirebaseService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Logd("From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Logd("Message data payload: " + remoteMessage.getData());

            Map<String, String> teste = remoteMessage.getData();

            Intent intent = new Intent(this, Splash.class);
            intent.putExtra("action", "NovoChamado");
            intent.putExtra("ID_Chamado", teste.get("tag"));

            NotificationUtil.showNotification(
                    this,
                    teste.get("title"),
                    teste.get("body"),
                    getString(R.string.group_chamados),
                    getString(R.string.channel_chamados_id),
                    intent,
                    Integer.parseInt(Objects.requireNonNull(teste.get("tag"))),
                    5,
                    R.drawable.ic_chat_bubble_outline_black_24dp
            );

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                // scheduleJob();
            } else {
                // Handle message within 10 seconds
                // handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Logd("Message Notification Body: " + remoteMessage.getData());

            Intent intent = new Intent(this, Main.class);
            intent.putExtra("ID_Chamado", remoteMessage.getNotification().getTag());

            NotificationUtil.showNotification(
                    this,
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody(),
                    getString(R.string.group_chamados),
                    getString(R.string.channel_chamados_id),
                    intent,
                    Integer.parseInt(Objects.requireNonNull(remoteMessage.getNotification().getTag())),
                    5,
                    R.drawable.ic_chat_bubble_outline_black_24dp
            );
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public void teste(){
        Toast.makeText(this, "TESTE", Toast.LENGTH_LONG).show();
    }
}
