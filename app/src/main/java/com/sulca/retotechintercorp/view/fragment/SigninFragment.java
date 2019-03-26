package com.sulca.retotechintercorp.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sulca.retotechintercorp.R;
import com.sulca.retotechintercorp.view.activity.RegisterUserActivity;
import com.sulca.retotechintercorp.view.interfacee.SigninView;
import com.sulca.retotechintercorp.view.navigation.Navigator;
import com.sulca.retotechintercorp.view.presenter.SigninPresenter;
import com.sulca.retotechintercorp.view.util.ValidationUtil;

import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by everis on 25/03/19.
 */
public class SigninFragment extends BaseFragment implements SigninView {

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.btn_request_sms)
    Button btnRequestSms;

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
        etPhoneNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                requestCode();
                return true;
            }
            return false;
        });

    }

    @OnClick(R.id.btn_request_sms)
    void requestCode() {
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
        enableControlls(true);
        if (getActivity() != null) {
            startActivity(RegisterUserActivity.getIntent(getActivity()));
            getActivity().finish();
        }
    }

    private void enableControlls(boolean enable) {
        etPhoneNumber.setEnabled(enable);
        btnRequestSms.setEnabled(enable);
        if (enable) btnRequestSms.setText(getString(R.string.btn_text_signin));
        else btnRequestSms.setText(getString(R.string.btn_text_waiting));
    }

    @Override
    public void waitingSms() {
        Toast.makeText(getContext(), "Esperando SMS...", Toast.LENGTH_SHORT).show();
        enableControlls(false);
    }

    @Override
    public void singinOnFailure() {
        enableControlls(true);
    }

    @Override
    public void showLoading() {
        Navigator.getInstance().showLoading(getFragmentManager());
    }

    @Override
    public void hideLoading() {
        Navigator.getInstance().dismissLoader(getFragmentManager());
    }
}
