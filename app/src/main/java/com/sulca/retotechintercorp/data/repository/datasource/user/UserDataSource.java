package com.sulca.retotechintercorp.data.repository.datasource.user;

import io.reactivex.Observable;

/**
 * Created by everis on 25/03/19.
 */
public interface UserDataSource {

    Observable<Void> register(String name, String lastname, int age, String dateBorn);

}
