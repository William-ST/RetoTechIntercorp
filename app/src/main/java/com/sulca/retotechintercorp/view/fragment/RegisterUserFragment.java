package com.sulca.retotechintercorp.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;

import com.sulca.retotechintercorp.R;
import com.sulca.retotechintercorp.view.dialog.RegisterUserSuccessDialogFragment;
import com.sulca.retotechintercorp.view.interfacee.RegisterUserView;
import com.sulca.retotechintercorp.view.navigation.Navigator;
import com.sulca.retotechintercorp.view.presenter.RegisterUserPresenter;
import com.sulca.retotechintercorp.view.util.Constants;
import com.sulca.retotechintercorp.view.util.ValidationUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by everis on 25/03/19.
 */
public class RegisterUserFragment extends BaseFragment implements RegisterUserView {

    private RegisterUserPresenter presenter;
    private static final String CERO = "0";
    private static final String BARRA = "/";

    @BindView(R.id.et_name)
    AppCompatEditText etName;

    @BindView(R.id.et_lastname)
    AppCompatEditText etLastname;

    @BindView(R.id.et_age)
    AppCompatEditText etAge;

    @BindView(R.id.et_borndate)
    AppCompatEditText etBorndate;

    @BindView(R.id.btn_register)
    Button btnRegister;

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
        if (getActivity() != null) getActivity().setTitle(getString(R.string.title_register));

        etBorndate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR, -18);
                    DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), (DatePickerDialog.OnDateSetListener) (view1, year, month, dayOfMonth) -> {
                        //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                        final int mesActual = month + 1;
                        //Formateo el día obtenido: antepone el 0 si son menores de 10
                        String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                        //Formateo el mes obtenido: antepone el 0 si son menores de 10
                        String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                        //Muestro la fecha con el formato deseado
                        etBorndate.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                        etBorndate.clearFocus();

                    },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                    recogerFecha.show();
                }
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void registerUser() {
        final String name = etName.getText() != null ? etName.getText().toString() : Constants.EMPTY;
        final String lastname = etLastname.getText() != null ? etLastname.getText().toString() : Constants.EMPTY;
        final String textAge = etAge.getText() != null ? etAge.getText().toString() : Constants.EMPTY;
        final String borndate = etBorndate.getText() != null ? etBorndate.getText().toString() : Constants.EMPTY;

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

    private void cleanForm() {
        etName.setText(Constants.EMPTY);
        etLastname.setText(Constants.EMPTY);
        etAge.setText(Constants.EMPTY);
        etBorndate.setText(Constants.EMPTY);
        etName.requestFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showLoading() {
        Navigator.getInstance().showLoading(getFragmentManager());
    }

    @Override
    public void hideLoading() {
        Navigator.getInstance().dismissLoader(getFragmentManager());
    }

    @Override
    public void registerOnSuccess() {
        Navigator.getInstance().showRegisterSuccess(getFragmentManager(), this::cleanForm);
    }

}
