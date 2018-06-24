package com.example.kashish.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;

public class MButton extends AppCompatButton {
    MButton(Context context){
        super(context);
    }
    int i,j;
    boolean lc=false;
    boolean shown = false;
    private int value;

    public void set(int i){
        value=i;

        }

    public int getVal(){
        return  value;
    }
}
