package com.sulca.retotechintercorp.view.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.sulca.retotechintercorp.view.interf.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by everis on 25/03/19.
 */
public class BaseFragment extends Fragment implements BaseView {

    private Unbinder mUnbinder;

    private void injectView(View view) {
        ButterKnife.setDebug(true);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectView(view);
    }

    @Override
    public void initUI() {

    }

    @Override
    public void showErrorMessage(String message) {
        if (getContext() == null && TextUtils.isEmpty(message)) return;

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorNetworkMessage(String message) {
        if (getContext() == null && TextUtils.isEmpty(message)) return;

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }


}
