package com.sulca.retotechintercorp.view.interf;

import android.app.Activity;

/**
 * Created by everis on 25/03/19.
 */
public interface RegisterUserView extends LoadingView {

    Activity getActivity();
    void registerOnSuccess();

}
