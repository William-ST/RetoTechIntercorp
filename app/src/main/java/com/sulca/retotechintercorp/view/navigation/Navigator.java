package com.sulca.retotechintercorp.view.navigation;

import com.sulca.retotechintercorp.view.dialog.ProgressDialogFragment;
import com.sulca.retotechintercorp.view.dialog.RegisterUserSuccessDialogFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Navigator {

    private static Navigator navigator;

    public static Navigator getInstance() {
        if (navigator == null) {
            navigator = new Navigator();
        }
        return navigator;
    }

    public void showLoading(FragmentManager fragmentManager) {
        if (fragmentManager == null) return;

        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.setShowsDialog(false);
        progressDialogFragment.setCancelable(false);
        progressDialogFragment.setRetainInstance(true);
        fragmentManager.beginTransaction()
                .add(progressDialogFragment, ProgressDialogFragment.TAG)
                .commitAllowingStateLoss();
    }

    public void dismissLoader(FragmentManager fragmentManager) {
        if (fragmentManager == null) return;

        Fragment f = fragmentManager.findFragmentByTag(ProgressDialogFragment.TAG);
        if (f != null) {
            fragmentManager.beginTransaction()
                    .remove(f)
                    .commitAllowingStateLoss();
        }
    }

    public void showRegisterSuccess(FragmentManager fragmentManager, RegisterUserSuccessDialogFragment.OnClickDialogOption onClickDialogOption) {
        if (fragmentManager == null) return;

        RegisterUserSuccessDialogFragment registerUserSuccessDialogFragment = new RegisterUserSuccessDialogFragment();
        registerUserSuccessDialogFragment.setCancelable(false);
        registerUserSuccessDialogFragment.setOnClickOption(onClickDialogOption);
        fragmentManager.beginTransaction()
                .add(registerUserSuccessDialogFragment, ProgressDialogFragment.TAG)
                .commitAllowingStateLoss();
    }

}
