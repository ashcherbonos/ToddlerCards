package dagidagi.toddlercards.animals.full;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import dagidagi.studio.toddlercards.animals.full.R;

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.textViewDagiDagi:
		case R.id.textViewMoreAps:
			try {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:\"DagiDagi\"")));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=DagiDagi")));
			}
			break;
		case R.id.textViewRateUs:
			try {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
			}
			break;
		case R.id.textViewMainScreen:
			finish();
		}
	}
}
