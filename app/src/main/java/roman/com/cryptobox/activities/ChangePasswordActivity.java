package roman.com.cryptobox.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import roman.com.cryptobox.R;
import roman.com.cryptobox.contracts.ChangePasswordContract;
import roman.com.cryptobox.presenters.ChangePasswordPresenter;

/**
 * Created by roman on 11/5/16.
 */

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.View {

    private static final int MAX_PROGRESS_BAR = 10;

    private ChangePasswordContract.Presenter mPresenter;

    private TextInputLayout mPasswordTextInputLayout;
    private EditText mPasswordEditText;

    private TextView mPasswordStrengthTextView;
    private ProgressBar mPasswordStrengthProgressbar;

    private Button mOkButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
//        TODO move this to String resources

        mPresenter = new ChangePasswordPresenter(this);

        //set the toolbar for this activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_change_password_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_change_password_textinputlayout);
        mPasswordEditText = (EditText) findViewById(R.id.activity_change_password_edittext);

        mPasswordStrengthTextView = (TextView) findViewById(R.id.activity_change_password_password_strength_textview);

        mPasswordStrengthProgressbar = (ProgressBar) findViewById(R.id.activity_change_password_password_strength_progressbar);
        mPasswordStrengthProgressbar.setMax(MAX_PROGRESS_BAR);
        mPasswordStrengthTextView.setText(getResources().getString(R.string.time_to_crack, " "));

        mOkButton = (Button) findViewById(R.id.activity_change_password_ok_button);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.userClickedOk();
            }
        });

        mPresenter.start();
    }

    @Override
    public void showInputNewPassword() {
        getSupportActionBar().setTitle(R.string.new_password_title);
        mPasswordEditText.setHint(R.string.new_password_hint);
    }


    @Override
    public void showInputRepeatNewPassword() {
        getSupportActionBar().setTitle(R.string.repeat_password_title);
        mPasswordEditText.setHint(R.string.repeat_password_hint);
    }

    @Override
    public void showPassWordChanged() {
        new AlertDialog.Builder(this)
                .setTitle("Password Changed")
                .setMessage("The password has been changed successfully")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        mPresenter.userClickedConfirmDelete();
                    }
                })
                .setIcon(R.drawable.ic_done)
                .show();
    }


    @Override
    public void showPasswordStrength(int passwordStrength, String passwordStrengthDescription) {
        mPasswordStrengthProgressbar.setProgress(passwordStrength);
        mPasswordStrengthTextView.setText(passwordStrengthDescription);
        mPasswordStrengthTextView.setVisibility(View.VISIBLE);
        mPasswordStrengthProgressbar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hidePasswordStrength() {
        mPasswordStrengthTextView.setVisibility(View.GONE);
        mPasswordStrengthProgressbar.setVisibility(View.GONE);
    }


    @Override
    public void showNotesActivity() {
        super.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        mPresenter.userClickedBack();
    }


    /**
     * show a change password confirmation dialog
     */
    @Override
    public void shwoConfirmChangePassowrd() {
        new AlertDialog.Builder(this)
                .setTitle("Change Password")
                .setMessage("Are you sure you want to change password?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        mPresenter.userClickedConfirmDelete();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do  nothing
            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
