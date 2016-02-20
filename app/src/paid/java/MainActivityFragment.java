package uk.appinvent.builditbigger;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ProgressBar;

import uk.appinvent.builditbigger.tasks.JokeAsyncTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

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

        return root;

    }

    public void processViewClick(){
        boolean isPaid = true;

            new JokeAsyncTask(this.getContext(), spinner).execute(isPaid);

    }
}
