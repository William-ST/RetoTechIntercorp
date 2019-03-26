package com.sulca.retotechintercorp.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sulca.retotechintercorp.R;
import com.sulca.retotechintercorp.view.interf.RegisterUserView;
import com.sulca.retotechintercorp.view.presenter.RegisterUserPresenter;
import com.sulca.retotechintercorp.view.util.ValidationUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by everis on 25/03/19.
 */
public class RegisterUserFragment extends BaseFragment implements RegisterUserView {

    private RegisterUserPresenter presenter;

    @BindView(R.id.et_name)
    AppCompatEditText etName;

    @BindView(R.id.et_lastname)
    AppCompatEditText etLastname;

    @BindView(R.id.et_age)
    AppCompatEditText etAge;

    @BindView(R.id.et_borndate)
    AppCompatEditText etBorndate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterUserPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userregister, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.setView(this);

        initUI();
    }

    @Override
    public void initUI() {


    }

    @OnClick(R.id.btn_register)
    public void registerUser() {
        final String name = etName.getText().toString();
        final String lastname = etName.getText().toString();
        final String textAge = etName.getText().toString();
        final String borndate = etName.getText().toString();
        int age = 0;

        if (!ValidationUtil.isValidField(name)) {
            showErrorMessage(getString(R.string.valid_message_invalid_name));
            return;
        }

        if (!ValidationUtil.isValidField(lastname)) {
            showErrorMessage(getString(R.string.valid_message_invalid_lastname));
            return;
        }

        if (!ValidationUtil.isValidField(textAge)) {
            showErrorMessage(getString(R.string.valid_message_invalid_age));
            return;
        } else {
            age = Integer.parseInt(textAge);
        }

        if (!ValidationUtil.isValidField(borndate)) {
            showErrorMessage(getString(R.string.valid_message_invalid_borndate));
            return;
        }

        presenter.register(name, lastname, age, borndate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void registerOnSuccess() {

    }

}
