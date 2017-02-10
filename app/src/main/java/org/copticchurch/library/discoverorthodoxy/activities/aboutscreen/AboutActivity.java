package org.copticchurch.library.discoverorthodoxy.activities.aboutscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import org.copticchurch.library.discoverorthodoxy.BrainPhaserComponent;
import org.copticchurch.library.discoverorthodoxy.R;
import org.copticchurch.library.discoverorthodoxy.activities.BrainPhaserActivity;
import org.copticchurch.library.discoverorthodoxy.utility.FileUtils;

public class AboutActivity extends BrainPhaserActivity {
    private TextView mAboutText;

    protected void injectComponent(BrainPhaserComponent component) {
        component.inject(this);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.copticchurch.library.discoverorthodoxy.R.layout.activity_about);
        this.mAboutText = (TextView) findViewById(org.copticchurch.library.discoverorthodoxy.R.id.action_about);
        try {
            this.mAboutText.setText(getStringFromRawFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSupportActionBar((Toolbar) findViewById(org.copticchurch.library.discoverorthodoxy.R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    String getStringFromRawFile() throws IOException {
        InputStream is = getResources().openRawResource(org.copticchurch.library.discoverorthodoxy.R.raw.credits);
        String myText = FileUtils.convertStreamToString(is);
        is.close();
        return myText;
    }

    public void process(View view) {
        if (view.getId() == R.id.sendemail) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setData(Uri.parse("mailto:"));
            String[] to = new String[]{"support@copticchurch-library.org", "abanoub.ghipriel@gmail.com"};
            intent.putExtra("android.intent.extra.SUBJECT", "Suggestion for The Coptic Orthodox Faith application");
            intent.putExtra("android.intent.extra.EMAIL", to);
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Send email"));
        }
    }
}
