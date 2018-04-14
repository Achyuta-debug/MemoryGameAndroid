package com.a317468825.hw1.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private Button btn_2x2;
    private Button btn_4x4;
    private Button btn_5x5;
    private TextView textNameAge;
    private String name;
    private int age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Bundle extrasBundle = this.getIntent().getExtras();
        name = extrasBundle.getString("name");
        age = extrasBundle.getInt("age");

        textNameAge = (TextView) findViewById(R.id.text_name_age);
        textNameAge.setText("Hello " + name + "(" + age + ")");


        btn_2x2 = findViewById(R.id.button_2X2);
        btn_2x2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterGame(2, 2, 30);
            }
        });

        btn_4x4 = findViewById(R.id.button_4X4);
        btn_4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterGame(4, 4, 45);
            }
        });

        btn_5x5 = findViewById(R.id.button_5X5);
        btn_5x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterGame(5, 5, 60);
            }
        });
    }

    private void EnterGame(int rows, int cols, int time) {
        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        intent.putExtra("rows", rows);
        intent.putExtra("columns", cols);
        intent.putExtra("time", time);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
