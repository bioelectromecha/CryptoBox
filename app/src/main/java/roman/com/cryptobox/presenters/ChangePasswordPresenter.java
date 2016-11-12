package roman.com.cryptobox.presenters;

import android.support.annotation.NonNull;

import roman.com.cryptobox.contracts.ChangePasswordContract;

/**
 * Created by roman on 11/5/16.
 */

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {

    private ChangePasswordContract.View mView;
    private boolean mIsRepeat = false;

    public ChangePasswordPresenter(ChangePasswordContract.View view) {
        mView = view;
    }

    @Override
    public void passwordChanged(@NonNull String password) {

    }

    @Override
    public void userClickedOk() {
        if (!mIsRepeat) {
            mIsRepeat = true;
            mView.showInputRepeatNewPassword();
        } else {
            mView.shwoConfirmChangePassowrd();
            mView.showPasswordStrength(0, "");
        }
    }

    @Override
    public void userClickedBack() {
        if (!mIsRepeat) {
            mView.showNotesActivity();
        } else {
            mIsRepeat = false;
            mView.showInputNewPassword();
            mView.hidePasswordStrength();
        }
    }

    @Override
    public void start() {
        mView.showInputNewPassword();
    }
}
