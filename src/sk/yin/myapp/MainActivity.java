package sk.yin.myapp;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class MainActivity extends ListActivity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		if (!hasWelcomed()) {
			Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);
		}
		AlgorithmFragment af = (AlgorithmFragment) getFragmentManager().findFragmentById(R.id.algo);
		af.getView().setVisibility(View.GONE);
	}

	private boolean hasWelcomed() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		final boolean hasWelcome = pref.getBoolean("welcome", false);
		return hasWelcome;
	}

	@Override
	public void onStart() {
		super.onStart();
	}
}

