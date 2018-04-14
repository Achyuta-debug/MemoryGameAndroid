package com.a317468825.hw1.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startGameButton;
    private EditText nameBox;
    private EditText ageBox;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        startGameButton = (Button) findViewById(R.id.logIn_button);
        nameBox = (EditText) findViewById(R.id.name_box);
        ageBox = (EditText) findViewById(R.id.age_box);

        startGameButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (nameBox.getText().toString().isEmpty() || ageBox.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill out your name and age", Toast.LENGTH_SHORT).show();
            return;
        }

        intent = new Intent(this, MenuActivity.class);
        intent.putExtra("name", nameBox.getText().toString());
        intent.putExtra("age", Integer.valueOf(ageBox.getText().toString()));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
