package uk.appinvent.builditbigger;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import uk.appinvent.builditbigger.tasks.JokeAsyncTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    InterstitialAd mInterstitialAd;
    String version = "free";
    private ProgressBar spinner;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        version = getString(R.string.version);

        spinner=(ProgressBar)root.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        View button = (Button) root.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processViewClick();
            }
        });

        if (version.equalsIgnoreCase("paid")){
//            AdView mAdView = (AdView) findViewById(R.id.adView);
//            AdRequest adRequest = new AdRequest.Builder().build();
//            mAdView.loadAd(adRequest);
        }else {
            mInterstitialAd = new InterstitialAd(this.getContext());
            String adId = getString(R.string.banner_ad_unit_id);
            mInterstitialAd.setAdUnitId(adId);

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    tellJoke(root.findViewById(R.id.instructions_text_view));
                }
            });

            requestNewInterstitial();
        }
        return root;
    }

    private void requestNewInterstitial() {
        String deviceId = getString(R.string.device_id);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(deviceId)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void processViewClick(){
        boolean isPaid = false;
        if (version.equalsIgnoreCase("paid")){
            isPaid = true;
            new JokeAsyncTask(this.getContext(), spinner).execute(isPaid);
        }else {
            isPaid = false;
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }else   {
                new JokeAsyncTask(this.getContext(), spinner).execute(isPaid);
            }
        }
    }

    public void tellJoke(View view){
        processViewClick();
    }
}
