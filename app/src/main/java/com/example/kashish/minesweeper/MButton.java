package com.example.kashish.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;

public class MButton extends AppCompatButton {
    MButton(Context context){
        super(context);
    }
    int i,j;
    boolean shown;
    private int value;

    public void set(int i){
        value=i;
        if(i==MainActivity.MINE){

        }
        }

    public int getVal(){
        return  value;
    }
}
