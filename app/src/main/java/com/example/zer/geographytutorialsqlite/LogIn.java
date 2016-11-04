package com.example.zer.geographytutorialsqlite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends Fragment {

    private EditText login;
    private EditText password;

    private Button logInBt;
    private Button signUpBt;

    public static LogIn newInstance() {
        return new LogIn();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_log_in, container, false);

        login = (EditText) root.findViewById(R.id.fli_login);
        password = (EditText) root.findViewById(R.id.fli_pass);

        logInBt = (Button) root.findViewById(R.id.fli_log_in);
        signUpBt = (Button) root.findViewById(R.id.fli_sign_up);

        logInBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginTmp = login.getText().toString();
                String passTmp = password.getText().toString();

                if (!loginTmp.equals("") && !passTmp.equals("")) {

                } else {
                    Toast.makeText(getContext(), "The login and/or password you entered is incorrect or the account does not exist",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        signUpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.am_logincontainer, SignUp.newInstance())
                        .commit();
            }
        });

        return root;
    }
}
