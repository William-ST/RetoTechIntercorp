package com.sulca.retotechintercorp.view.presenter;

import android.util.Log;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sulca.retotechintercorp.data.entity.UserEntity;
import com.sulca.retotechintercorp.data.ConstantsMessage;
import com.sulca.retotechintercorp.data.repository.AuthenticatePhone;
import com.sulca.retotechintercorp.data.repository.AuthenticatePhoneImpl;
import com.sulca.retotechintercorp.view.interfacee.SigninView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.sulca.retotechintercorp.view.util.Constants.PE_CODE;
import static com.sulca.retotechintercorp.view.util.Constants.TIME_WAITING_SMS;

/**
 * Created by everis on 25/03/19.
 */
public class SigninPresenter implements Presenter<SigninView> {

    private final static String TAG = SigninPresenter.class.getCanonicalName();
    private SigninView view;

    private AuthenticatePhone authenticate;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    public SigninPresenter() {
        authenticate = new AuthenticatePhoneImpl();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void setView(SigninView view) {
        this.view = view;
    }

    public void requestSms(String phonenumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(PE_CODE+phonenumber, TIME_WAITING_SMS, TimeUnit.SECONDS, view.getActivity(), mCallbacks);
        mVerificationInProgress = true;
    }

    private void signin(PhoneAuthCredential credential) {
        authenticate.loginSms(credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SigninObserver());
    }

    private class SigninObserver implements Observer<UserEntity> {

        @Override
        public void onSubscribe(Disposable d) {
            view.showLoading();
        }

        @Override
        public void onNext(UserEntity userEntity) {
            if (userEntity != null) {
                view.singinOnSuccess();
            } else {
                view.singinOnFailure();
            }
        }

        @Override
        public void onError(Throwable e) {
            view.hideLoading();
            view.singinOnFailure();
        }

        @Override
        public void onComplete() {
            view.hideLoading();
        }
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.d(TAG, "onVerificationCompleted:" + credential);
            mVerificationInProgress = false;
            signin(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.w(TAG, "onVerificationFailed", e);
            mVerificationInProgress = false;
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                view.showErrorMessage(ConstantsMessage.SERVER_ERROR_LOGIN_CODE);
            } else if (e instanceof FirebaseTooManyRequestsException) {
                view.showErrorMessage(ConstantsMessage.SERVER_ERROR);
            } else {
                view.showErrorMessage(ConstantsMessage.SERVER_ERROR);
            }
            view.singinOnFailure();
        }

        @Override
        public void onCodeSent(String verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            Log.d(TAG, "onCodeSent:" + verificationId);
            mVerificationId = verificationId;
            mResendToken = token;
            view.waitingSms();
        }
    };
}
