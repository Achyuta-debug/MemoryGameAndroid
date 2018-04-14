package com.a317468825.hw1.memorygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.GridLayout;

/**
 * Created by Daniel on 13-Apr-18.
 */

public class GameButton extends AppCompatButton {

    protected int row;
    protected int col;
    protected int size;
    protected int frontImageDrawableId;
    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;

    @SuppressLint("RestrictedApi")
    public GameButton(Context context, int row, int col, int frontImageDrawableId, int size) {
        super(context);

        this.row = row;
        this.col = col;
        this.frontImageDrawableId = frontImageDrawableId;

        front = AppCompatDrawableManager.get().getDrawable(context, frontImageDrawableId);
        back = AppCompatDrawableManager.get().getDrawable(context, R.drawable.button_0);

        setBackground(back);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col));


        if (size == 4)
            this.size = 170;
        else if (size == 16)
            this.size = 90;
        else
            this.size = 70;

        params.width = (int) getResources().getDisplayMetrics().density * this.size;
        params.height = (int) getResources().getDisplayMetrics().density * this.size;

        setLayoutParams(params);

    }


    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public int getFrontImageDrawableId() {
        return frontImageDrawableId;
    }

    public void flip() {
        if (isMatched)
            return;
        if (isFlipped) {
            setBackground(back);
            isFlipped = false;
        } else {
            setBackground(front);
            isFlipped = true;
        }
    }
}
