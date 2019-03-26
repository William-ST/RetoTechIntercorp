package com.sulca.retotechintercorp.data.repository;

import com.google.firebase.auth.PhoneAuthCredential;
import com.sulca.retotechintercorp.data.entity.UserEntity;

import io.reactivex.Observable;

/**
 * Created by everis on 25/03/19.
 */
public interface AuthenticatePhone {

    Observable<UserEntity> loginSms(PhoneAuthCredential credential);

    Observable<Void> logout();

}
