package com.sulca.retotechintercorp.view.presenter;

import com.sulca.retotechintercorp.data.repository.UserRepository;
import com.sulca.retotechintercorp.data.repository.UserRepositoryImpl;
import com.sulca.retotechintercorp.data.repository.datasource.user.CloudFirebaseUserDataSource;
import com.sulca.retotechintercorp.view.interfacee.RegisterUserView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by everis on 25/03/19.
 */
public class RegisterUserPresenter implements Presenter<RegisterUserView> {

    private RegisterUserView view;

    private UserRepository userRepository;

    public RegisterUserPresenter() {
        userRepository = new UserRepositoryImpl(new CloudFirebaseUserDataSource());
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
    public void setView(RegisterUserView view) {
        this.view = view;
    }

    public void register(String name, String lastname, int age, String borndate) {
        userRepository.register(name, lastname, age, borndate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RegisterObserver());
    }

    private class RegisterObserver implements Observer<Boolean> {

        @Override
        public void onSubscribe(Disposable d) {
            view.showLoading();
        }

        @Override
        public void onNext(Boolean status) {
            if (status) view.registerOnSuccess();
        }

        @Override
        public void onError(Throwable e) {
            view.showErrorMessage(e.getMessage());
        }

        @Override
        public void onComplete() {
            view.hideLoading();
        }
    }

}
