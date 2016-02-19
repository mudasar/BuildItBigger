package uk.appinvent.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import uk.appinvent.builditbigger.tasks.JokeAsyncTask;
import uk.appinvent.jokedisplaylibrary.JokeActivity;


public class MainActivity extends AppCompatActivity implements IAysncTaskCompleted {

    private ProgressBar spinner;
    InterstitialAd mInterstitialAd;
    String version = "free";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner=(ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

         version = getString(R.string.version);


        if (version.equalsIgnoreCase("paid")){
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            mInterstitialAd = new InterstitialAd(this);
            String adId = getString(R.string.banner_ad_unit_id);
            mInterstitialAd.setAdUnitId(adId);

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    tellJoke(findViewById(R.id.instructions_text_view));
                }
            });

            requestNewInterstitial();
        }


    }

    private void requestNewInterstitial() {
        String deviceId = getString(R.string.device_id);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(deviceId)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
            boolean isPaid = false;
            if (version.equalsIgnoreCase("paid")){
                isPaid = true;
                new JokeAsyncTask(this, spinner).execute(isPaid);
            }else {
                isPaid = false;
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }else   {
                    new JokeAsyncTask(this, spinner).execute(isPaid);
                }
            }

    }

    @Override
    public void OnAsyncTaskCompleted(String result) {

    }
}
