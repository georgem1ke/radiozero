package org.androidappdev.radiozero;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class RadioZero extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final ToggleButton togglebutton = (ToggleButton) findViewById(R.id.togglebutton);
		final TextView textView = (TextView) findViewById(R.id.instructions);
		togglebutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				if (togglebutton.isChecked()) {
					togglebutton.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.radiozerovolume));
					textView.setText(getResources().getText(R.string.stop));
				} else {
					togglebutton.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.radiozerostop));
					textView.setText(getResources().getText(R.string.listen));
				}
			}
		});

		// MediaPlayer mp = new MediaPlayer();
		// try {
		// mp.setDataSource("http://stream.radiozero.pt:8000/zero64.mp3");
		// mp.prepare();
		// mp.start();
		// } catch (Exception e) {
		// Log.e("RadioZero", e.getMessage());
		// }

	}
}