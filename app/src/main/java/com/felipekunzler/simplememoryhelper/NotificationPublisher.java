package com.felipekunzler.simplememoryhelper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Random;

public class NotificationPublisher extends BroadcastReceiver {

    private final String meaningNotification = "Meaning: ";
    private final String wordNotification = "Word: ";

    public void onReceive(Context context, Intent intent) {

        DatabaseHandler db = new DatabaseHandler(context);
        ArrayList<Word> words =  db.getAllWords();

        String nTitle = "";
        String nText = "";
        int smallIcon = 0;

        int leastSeenId = -1;
        if (words.size() > 0){

            // Get next word to be sent through the notification
            long leastSeenWord = words.get(0).getLastTimeNotificationSent();
            leastSeenId = words.get(0).getId();
            for (Word word : words){
                if (leastSeenWord > word.getLastTimeNotificationSent()){
                    leastSeenWord =  word.getLastTimeNotificationSent();
                    leastSeenId = word.getId();
                }
            }

            Word word = db.getWord(leastSeenId);
            word.setLastTimeNotificationSent(System.currentTimeMillis());

            db.updateWord(word);

            nText = meaningNotification + word.getMeaning();
            nTitle = wordNotification + word.getWord();
            smallIcon = R.drawable.app_icon;
        }

        Intent contentIntent = new Intent(context, EditWordActivity.class);
        contentIntent.putExtra(Word.WORD_ID_NOTIFICATION, Integer.toString(leastSeenId));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, new Random().nextInt(), contentIntent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(smallIcon)
                        .setContentTitle(nTitle)
                        .setContentText(nText);

        mBuilder.setContentIntent(pendingIntent);
        // mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}