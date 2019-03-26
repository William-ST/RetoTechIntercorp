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

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public CloudFirebaseUserDataSource() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public Observable<Void> register(final String name, final String lastname, final int age, final String dateBorn) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(final ObservableEmitter<Void> emitter) throws Exception {
                UserEntity userEntity = new UserEntity();
                userEntity.setName(name);
                userEntity.setLastname(lastname);
                userEntity.setAge(age);
                userEntity.setDateborn(dateBorn);

                databaseReference.child("user").setValue(userEntity)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                emitter.onNext(null);
                                emitter.onComplete();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                emitter.onError(e);
                            }
                        });
            }
        });
    }

}
