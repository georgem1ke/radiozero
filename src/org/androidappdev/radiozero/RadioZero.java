package org.androidappdev.radiozero;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class RadioZero extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MediaPlayer mp = new MediaPlayer();
        try {
			mp.setDataSource("http://stream.radiozero.pt:8000/zero64.mp3");
			mp.prepare();
			mp.start();
		} catch (Exception e) {
			Log.e("RadioZero", e.getMessage());
		}
        
    }
}