package uk.appinvent.jokedisplaylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String SHARED_MESSAGE = "uk.appinvent.jokedisplaylibrary.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        Intent intent = getIntent();

        String message = intent.getStringExtra(SHARED_MESSAGE);
        TextView jokeTextView = (TextView) findViewById(R.id.joke_text_veiw);
        jokeTextView.setText(message);
    }
}
