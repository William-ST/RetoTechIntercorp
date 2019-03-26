package com.sulca.retotechintercorp.view.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.sulca.retotechintercorp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class RegisterUserSuccessDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = RegisterUserSuccessDialogFragment.class.getCanonicalName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setCancelable(false);
        return inflater.inflate(R.layout.dialog_registerusersuccess, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnAccept = view.findViewById(R.id.btn_accept);
        btnAccept.setOnClickListener(this);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().getDecorView().getBackground().setAlpha(0);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_accept) {
            if (onClickDialogOption != null) onClickDialogOption.accept();
            dismiss();
        }
    }

    private OnClickDialogOption onClickDialogOption;

    public void setOnClickOption(OnClickDialogOption onClickDialogOption) {
        this.onClickDialogOption = onClickDialogOption;
    }

    public interface OnClickDialogOption {
        void accept();
    }
}
