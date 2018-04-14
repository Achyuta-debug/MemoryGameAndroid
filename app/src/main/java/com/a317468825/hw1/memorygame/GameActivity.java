package com.a317468825.hw1.memorygame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private int numOfElements;
    private GameButton[] buttons;
    private int[] buttonGraphicLocation;
    private int[] buttonGraphics;
    private GameButton selectedButton1;
    private GameButton selectedButton2;
    private TextView textName;

    private boolean isBusy = false;

    private int pairedNum = 0;
    private final Handler handler = new Handler();
    private long time;
    private CountDownTimer timer;

    private String name;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extrasBundle = this.getIntent().getExtras();
        GridLayout grid = (GridLayout) findViewById(R.id.game_grid);

        int numCols = extrasBundle.getInt("columns");
        int numRows = extrasBundle.getInt("rows");
        time = (long) extrasBundle.getInt("time");
        name = extrasBundle.getString("name");
        age = extrasBundle.getInt("age");


        textName = (TextView) findViewById(R.id.name_container_text);
        textName.setText(name);

        grid.setColumnCount(numCols);
        grid.setRowCount(numRows);

        numOfElements = numCols * numRows;

        buttons = new GameButton[numOfElements];
        buttonGraphics = new int[numOfElements / 2];

        loadGraphics();

        buttonGraphicLocation = new int[numOfElements];

        shuffleButtonGraphics();

        // fill grid with buttons
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                GameButton tempButton = new GameButton(this, row, col, buttonGraphics[buttonGraphicLocation[(row * numCols) + col]], numOfElements);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttons[(row * numCols) + col] = tempButton;
                grid.addView(tempButton);
            }
        }

        //start timer
        countDownStart();
    }

    private void countDownStart() {
        timer = new CountDownTimer((long) time * 1000, 1000) {

            TextView timer = (TextView) findViewById(R.id.seconds_left_text);

            public void onTick(long millisUntilFinished) {
                if (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) == 10)
                    timer.setTextColor(Color.RED);
                timer.setText("" + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
            }

            public void onFinish() {
                timer.setText("HALT!!!");
                Toast.makeText(GameActivity.this, "Out of time, LOSER!\nEnjoy your main menu.", Toast.LENGTH_LONG).show();
                returnToMenu();
            }
        }.start();
    }

    private void returnToMenu() {
        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        startActivity(intent);
    }

    private void loadGraphics() {
        buttonGraphics[0] = R.drawable.button_1;
        buttonGraphics[1] = R.drawable.button_2;
        if (numOfElements > 4) {
            buttonGraphics[2] = R.drawable.button_3;
            buttonGraphics[3] = R.drawable.button_4;
            buttonGraphics[4] = R.drawable.button_5;
            buttonGraphics[5] = R.drawable.button_6;
            buttonGraphics[6] = R.drawable.button_7;
            buttonGraphics[7] = R.drawable.button_8;
        }
        if (numOfElements > 16) {
            buttonGraphics[8] = R.drawable.button_9;
            buttonGraphics[9] = R.drawable.button_10;
            buttonGraphics[10] = R.drawable.button_11;
            buttonGraphics[11] = R.drawable.button_12;
        }
    }


    protected void shuffleButtonGraphics() {
        Random rand = new Random();

        for (int i = 0; i < numOfElements; i++) {
            buttonGraphicLocation[i] = i % (numOfElements / 2);
        }

        for (int i = 0; i < numOfElements; i++) {
            int temp = buttonGraphicLocation[i];
            int swapIdx = rand.nextInt(numOfElements);
            buttonGraphicLocation[i] = buttonGraphicLocation[swapIdx];
            buttonGraphicLocation[swapIdx] = temp;
        }

    }


    @Override
    public void onClick(View view) {

        if (isBusy)
            return;

        GameButton button = (GameButton) view;

        if (button.isMatched())
            return;

        if (selectedButton1 == null) {
            selectedButton1 = button;
            selectedButton1.flip();
            return;
        }

        if (selectedButton1.getId() == button.getId())
            return;

        if (selectedButton1.getFrontImageDrawableId() == button.getFrontImageDrawableId()) {
            button.flip();
            selectedButton1.setMatched(true);
            selectedButton1.setEnabled(false);
            button.setEnabled(false);
            selectedButton1 = null;
            pairedNum++;
            checkIfWon();
            return;
        } else {
            selectedButton2 = button;
            selectedButton2.flip();
            isBusy = true;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2.flip();
                    selectedButton1.flip();
                    selectedButton1 = null;
                    selectedButton2 = null;
                    isBusy = false;
                }
            }, 1000);
        }
    }

    private void checkIfWon() {
        if (pairedNum == buttonGraphics.length) {
            timer.cancel();
            Toast.makeText(this, "You are the mighty Winrar!\nEnjoy your main menu.",
                    Toast.LENGTH_LONG).show();
            returnToMenu();
        }
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        returnToMenu();
    }
}
