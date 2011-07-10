package org.androidappdev.radiozero;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class RadioZero extends Activity {
	private static final String TAG = "RadioZero";

	private MediaPlayer mp = new MediaPlayer();
	private boolean prepared = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (!isConnected(getApplicationContext())) {
			showConnectionError();
		}
		
		mp.setOnPreparedListener(new OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				prepared = true;
				enableSpeaker();
			}
		});

		// Start buffering radio.
		try {
			mp.setDataSource("http://stream.radiozero.pt:8000/zero64.mp3");
			mp.prepareAsync();
		} catch (Exception e) {
			Log.e(TAG, "prepareAsync", e);
		}
		final ToggleButton togglebutton = (ToggleButton) findViewById(R.id.togglebutton);
		final TextView textView = (TextView) findViewById(R.id.instructions);
		textView.setText(R.string.buffering);
		togglebutton.setEnabled(false);
		togglebutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				if (togglebutton.isChecked()) {
					togglebutton.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.radiozerovolume));
					textView.setText(getResources().getText(R.string.stop));

					if (prepared) {
						try {
							mp.start();
							Log.d(TAG, "start()");
						} catch (Exception e) {
							Log.e(TAG, "start", e);
						}
					}
				} else {
					togglebutton.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.radiozerostop));
					textView.setText(getResources().getText(R.string.listen));
					try {
						mp.pause();
						Log.d(TAG, "pause()");
					} catch (Exception e) {
						Log.e(TAG, "pause", e);
					}
				}
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.mp.release();
		Log.d(TAG, "release()");
	}

	private void enableSpeaker() {
		ToggleButton togglebutton = (ToggleButton) findViewById(R.id.togglebutton);
		togglebutton.setEnabled(true);
		TextView textView = (TextView) findViewById(R.id.instructions);
		textView.setText(R.string.listen);
	}
	
	private void showConnectionError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Internet connection unavailable!")
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                RadioZero.this.finish();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/**
	 * Check if we're connected
	 * 
	 * @param context
	 * @return true if we're connected, false otherwise
	 */
	private static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (cm != null) {
			networkInfo = cm.getActiveNetworkInfo();
		}
		return networkInfo == null ? false : networkInfo.isConnected();
	}

}