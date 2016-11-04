package com.example.zer.geographytutorialsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//import static com.example.zer.geographytutorial.LoginScreen.COUNTRIES;
//import static com.example.zer.geographytutorial.LoginScreen.SELECTED_COUNTRY;

public class Questionnaire extends AppCompatActivity {

    private static int cur_question = 0;

    private Country selectedCountry;
    private List<Country> countries;
    private List<Country> tmp_questions;
    private List<Country> countries_tmp;
    private HashMap<Country, Boolean> questions;

    private TextView question;
    private TextView comment;
    private EditText answer;
    private Button checkAnswer;
//    what the capital of

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        question = (TextView) findViewById(R.id.aq_question);
        comment = (TextView) findViewById(R.id.aq_comment);
        answer = (EditText) findViewById(R.id.aq_answer);
        checkAnswer = (Button) findViewById(R.id.aq_check_answer);
        checkAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().toString().toLowerCase().equals(tmp_questions.get(cur_question).name.toLowerCase())) {
                    questions.put(tmp_questions.get(cur_question), true);
                    cur_question++;
                    if(cur_question < (tmp_questions.size() - 1)) {
                        askQuestion(cur_question);
                    }
                    else {
                        cur_question = 0;
                        createQuestionsHash(countries);
                        askQuestion(cur_question);
                    }
                }
                else {
                    Toast.makeText(Questionnaire.this, "wrong answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        countries = new ArrayList<>();

        Intent intent = getIntent();
//        selectedCountry = (Country) intent.getSerializableExtra(SELECTED_COUNTRY);
//        countries_tmp = (List<Country>) intent.getSerializableExtra(COUNTRIES);

        createQuestionsHash(countries_tmp);

        askQuestion(cur_question);
    }

    private void askQuestion(int index) {
//        question.setText(String.format("What the capital of %s", questions.get(tmp_questions.get(index)). ) );
        question.setText(String.format("What the capital of %s?", tmp_questions.get(index).name));
    }

    private void createQuestionsHash(List<Country> countriesList) {
        String curSubRegion = selectedCountry.subregion;
        Random r = new Random();
        questions = new HashMap<>();
        countries = new ArrayList<>();

        for (Country c : countriesList) {
            if (curSubRegion.equals(c.subregion)) {
                countries.add(c);
            }
        }

        while (questions.size() < 5) {
            questions.put(countries.get(r.nextInt(countries.size() - 1)), false);
        }
        tmp_questions = new ArrayList<>();
        for (Map.Entry entry : questions.entrySet()) {
            tmp_questions.add((Country) entry.getKey());
        }
    }
}
