package roman.com.cryptobox.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import roman.com.cryptobox.R;

/**
 * Launcher activity
 * This activity is the first one to be opened when the app is launched
 * It exists solely to display the EchoPark logo while the app is loading
 */
public class SplashActivity extends AppCompatActivity {

    private final static String RUN_NUMBER = "RUN_NUMBER";
    private SharedPreferences mSharedPreferences;

    /* the logo is displayed through the theme-drawable (check out the manifest + stles + layout file )*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //decide what activity to load based on app run counter
        mSharedPreferences = getSharedPreferences("cryptobox", Context.MODE_PRIVATE);
        if (isFirstRun()) {
            incrementRunCounter();
            goToExplainActivity();
        } else {
            incrementRunCounter();
            goToLoginActivity();
        }
    }

    private boolean isFirstRun() {
        return mSharedPreferences.getInt(RUN_NUMBER, 0) == 0;
    }

    private void incrementRunCounter() {
        mSharedPreferences.edit().putInt(RUN_NUMBER, mSharedPreferences.getInt(RUN_NUMBER, 0) + 1).apply();
    }


    private void goToExplainActivity() {
        //launch the permissions activity
        Intent intent = new Intent(this, ExplainActivity.class);
        startActivity(intent);
        //kill the activity to prevent user from going back to the splash screen
        finish();
    }
    /**
     * this one will open the LoginActivity and prevent from going back to splash screen
     */
    private void goToLoginActivity() {
        //launch the permissions activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //kill the activity to prevent user from going back to the splash screen
        finish();
    }

}