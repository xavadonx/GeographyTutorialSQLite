package com.example.zer.geographytutorialsqlite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends Fragment {

    private String selectedCountryName = "";

    private EditText name;
    private EditText pass;
    private Spinner spinner;
    private Button signUp;
    private TextView validatemsg;

    public static SignUp newInstance() {
        return new SignUp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        name = (EditText) root.findViewById(R.id.fsu_name_et);
        pass = (EditText) root.findViewById(R.id.fsu_pass_et);
        spinner = (Spinner) root.findViewById(R.id.fsu_country_spinner);
        signUp = (Button) root.findViewById(R.id.fsu_sign_up);
        validatemsg = (TextView) root.findViewById(R.id.fsu_pass_validate_message);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                Db.getInstance(getContext()).getCountriesNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedCountryName = parent.getAdapter().getItem(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCountryName = "";
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean loginIsValid = name.getText().toString().length() > 2;
                boolean passIsValid = new PasswordValidator().validate(pass.getText().toString());
                if (!loginIsValid || !passIsValid || selectedCountryName.equals("")) {
                    Toast.makeText(getContext(), "The login, password and/or country you entered is incorrect", Toast.LENGTH_LONG).show();
                } else {

                    Db.getInstance(getContext()).insertUser(name.getText().toString(),
                            new MainActivity().createPass(pass.getText().toString()),
                            selectedCountryName);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.am_logincontainer, LogIn.newInstance())
                            .commit();
                }
            }
        });

        return root;
    }


}
