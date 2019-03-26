package com.sulca.retotechintercorp.data.repository;

import io.reactivex.Observable;

/**
 * Created by everis on 25/03/19.
 */
public interface UserRepository {

    Observable<Void> register(String name, String lastname, int age, String dateBorn);

}
