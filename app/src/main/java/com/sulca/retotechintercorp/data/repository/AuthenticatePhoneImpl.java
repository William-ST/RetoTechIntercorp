package com.sulca.retotechintercorp.data.repository;

import androidx.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.sulca.retotechintercorp.data.exception.ConstantsMessage;
import com.sulca.retotechintercorp.data.exception.ServerExceptionMessage;
import com.sulca.retotechintercorp.data.entity.UserEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by everis on 25/03/19.
 */
public class AuthenticatePhoneImpl implements AuthenticatePhone {

    private final String TAG = AuthenticatePhoneImpl.class.getCanonicalName();

    private FirebaseAuth mAuth;

    public AuthenticatePhoneImpl() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public Observable<UserEntity> loginSms(final PhoneAuthCredential credential) {
        //final PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        return Observable.create(new ObservableOnSubscribe<UserEntity>() {
            @Override
            public void subscribe(final ObservableEmitter<UserEntity> emitter) throws Exception {

                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithCredential:success");
                                    if (task.getResult() != null) {
                                        FirebaseUser user = task.getResult().getUser();
                                        UserEntity userEntity = new UserEntity();
                                        //userEntity.setId(String.valueOf(user.getUid()));
                                        emitter.onNext(userEntity);
                                        emitter.onComplete();
                                    } else {
                                        emitter.onError(new ServerExceptionMessage(ConstantsMessage.SERVER_ERROR));
                                    }
                                } else {
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        emitter.onError(new ServerExceptionMessage(ConstantsMessage.SERVER_ERROR_LOGIN_CODE));
                                    } else {
                                        emitter.onError(new ServerExceptionMessage(ConstantsMessage.SERVER_ERROR));
                                    }
                                }
                            }
                        });

            }
        });
    }

    @Override
    public Observable<Void> logout() {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
                mAuth.signOut();
                emitter.onNext(null);
                emitter.onComplete();
            }
        });
    }

}
