package com.example.zer.geographytutorialsqlite;

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

public class SignUp extends Fragment{

    private static boolean countryIsValid = false;

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

        View root = inflater.inflate(R.layout.fragment_log_in, container, false);

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
                countryIsValid = position >= 0;
//                setVisibilityLogIn();
//                if (countryIsValid) {
//                    selectedCountry = countries.get(position - 1);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                countryIsValid = false;
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean loginIsValid = name.getText().toString().length() > 2;
                boolean passIsValid = new PasswordValidator().validate(pass.getText().toString());
                if(!loginIsValid || !passIsValid || !countryIsValid) {
                    Toast.makeText(getContext(), "The login, password and/or country you entered is incorrect", Toast.LENGTH_LONG).show();
                }
                else {

                }
            }
        });

        return root;
    }
}
