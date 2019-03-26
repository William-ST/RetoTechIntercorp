package com.sulca.retotechintercorp.data.repository.datasource.user;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sulca.retotechintercorp.data.entity.UserEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by everis on 25/03/19.
 */
public class CloudFirebaseUserDataSource implements UserDataSource {

    private DatabaseReference databaseReference;

    public CloudFirebaseUserDataSource() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public Observable<Boolean> register(final String name, final String lastname, final int age, final String dateBorn) {
        return Observable.create(emitter -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(name);
            userEntity.setLastname(lastname);
            userEntity.setAge(age);
            userEntity.setDateborn(dateBorn);

            databaseReference.child("user").push().setValue(userEntity)
                    .addOnSuccessListener(aVoid -> {
                        emitter.onNext(true);
                        emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        emitter.onError(e);
                        emitter.onComplete();
                    });
        });
    }

}
