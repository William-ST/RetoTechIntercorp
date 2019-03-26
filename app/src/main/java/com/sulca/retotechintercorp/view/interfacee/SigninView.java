package com.sulca.retotechintercorp.view.interfacee;

import android.app.Activity;

/**
 * Created by everis on 25/03/19.
 */
public interface SigninView extends LoadingView {

    Activity getActivity();
    void singinOnSuccess();
    void waitingSms();
    void singinOnFailure();

}
