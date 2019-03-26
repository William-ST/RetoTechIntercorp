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
import com.sulca.retotechintercorp.data.ConstantsMessage;
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
        return Observable.create(emitter -> mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        if (task.getResult() != null) {
                            FirebaseUser user = task.getResult().getUser();
                            UserEntity userEntity = new UserEntity();
                            //userEntity.setId(String.valueOf(user.getUid()));
                            emitter.onNext(userEntity);
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
                    emitter.onComplete();
                }));
    }

    @Override
    public Observable<Void> logout() {
        return Observable.create(emitter -> {
            mAuth.signOut();
            emitter.onNext(null);
            emitter.onComplete();
        });
    }

}
