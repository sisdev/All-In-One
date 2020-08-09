package in.sisoft.all_in_one;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setTitle(getTitle());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView w1 = findViewById(R.id.about_app);
        try {
            w1.loadUrl("file:///android_asset/about_aii.html");
        } catch (Exception e){
            Log.d("WebView", e.getMessage());
        }

    }
}
