package com.yashodainfotech.queuingapp;

        import android.content.Context;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Build;

public class Ringtone {
    public static android.media.Ringtone ringtone;

    public static void RingtoneManager(Context context) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);  // so killing the ringtone process not needed
        ringtone = RingtoneManager.getRingtone(context, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone.setLooping(true);
        }
        ringtone.play();
    }

    public static void StopRingtone() {
        if (ringtone.isPlaying()){
            ringtone.stop();
        }
    }
}
