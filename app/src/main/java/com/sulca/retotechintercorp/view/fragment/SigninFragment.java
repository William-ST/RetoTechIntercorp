package com.sulca.retotechintercorp.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sulca.retotechintercorp.R;
import com.sulca.retotechintercorp.view.interf.SigninView;
import com.sulca.retotechintercorp.view.presenter.SigninPresenter;
import com.sulca.retotechintercorp.view.util.ValidationUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by everis on 25/03/19.
 */
public class SigninFragment extends BaseFragment implements SigninView {

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    private SigninPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SigninPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.setView(this);

        initUI();
    }

    @Override
    public void initUI() {

        etPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    requestCode();
                    return true;
                }
                return false;
            }
        });

    }

    @OnClick(R.id.btn_request_sms)
    public void requestCode() {
        final String phoneNumber = etPhoneNumber.getText().toString();
        if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
            showErrorMessage(getString(R.string.valid_message_invalid_phone));
            return;
        }

        presenter.requestSms(phoneNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }


    @Override
    public void singinOnSuccess() {

    }

    @Override
    public void waitingSms() {
        Toast.makeText(getContext(), "Esperando SMS...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void singinOnFailure() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
