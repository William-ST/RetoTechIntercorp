package com.sulca.retotechintercorp.data.repository;

import com.sulca.retotechintercorp.data.repository.datasource.user.CloudFirebaseUserDataSource;
import com.sulca.retotechintercorp.data.repository.datasource.user.UserDataSource;

import io.reactivex.Observable;

/**
 * Created by everis on 25/03/19.
 */
public class UserRepositoryImpl implements UserRepository {

    private UserDataSource userDataSource;

    public UserRepositoryImpl(CloudFirebaseUserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public Observable<Boolean> register(String name, String lastname, int age, String dateBorn) {
        return userDataSource.register(name, lastname, age, dateBorn);
    }

}
