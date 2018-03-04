package jkvillavo.co.com.rappiandroidtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_image_TheMovie)
    ImageView splashImageTheMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        WaitTask waitTask = new WaitTask();
        waitTask.execute();
    }

    private class WaitTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {

            if (result == 1) {
                pasarActividad();
            }

        }
    }

    /**
     * TRANSITION
     */
    private void pasarActividad() {

        Intent intent = new Intent(SplashActivity.this, PrincipalActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, splashImageTheMovie, "splash_icono");
        startActivity(intent, options.toBundle());

    }
}
