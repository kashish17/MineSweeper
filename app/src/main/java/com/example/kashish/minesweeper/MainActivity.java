package com.example.kashish.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout rootLayout;
    int m=10,n=6;

    MButton board[][];
    ArrayList<LinearLayout> rows;
   public static final int MINE =-1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout=findViewById(R.id.root);
        createBoard();
        setmines();
    }
    public void createBoard(){
       // currentStatus= incomplete;
        board=  new MButton[m][n];
        rows =new ArrayList<>();

        for(int i=0;i<m ;i++){

            LinearLayout linearLayout =new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            linearLayout.setLayoutParams(layoutParams);
            rootLayout.addView(linearLayout);
            rows.add(linearLayout);
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                MButton button=new MButton(this);
                button.i=i;
                button.j=j;
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(layoutParams);
                LinearLayout list=rows.get(i);
                button.setOnClickListener(this);
                list.addView(button);
                board[i][j]=button;

            }
        }

    }
    public void setmines(){
        Random rand= new Random();
        int mines=10;
        int row[]=new int[m];
        int col[]=new int[n];
        for(int i=0;i<m;i++){
            row[i]=rand.nextInt(m);
        }
        for(int i=0;i<n;i++){
            col[i]=rand.nextInt(n);
        }

        for(int i=0;mines>0;i++,mines--){
            int r=row[i],c=col[i];
            board[r][c].set(-1);
        }
        setVals();
    }

    public void setVals() {
        int nx[]=new int [8];
        int ny[]=new int[8];
        nx[0]=-1; ny[0]=-1;
        nx[1]=-1; ny[1]=0;
        nx[2]=-1; ny[2]=1;
        nx[3]=0; ny[3]=-1;
        nx[4]=0; ny[4]=1;
        nx[5]=1; ny[5]=-1;
        nx[6]=1; ny[6]=0;
        nx[7]=1; ny[7]=1;


        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(board[i][j].getVal()==-1){

                    for(int k=0;k<8;k++){
                        int indexX=i+nx[k];
                        int indexY=j+ny[k];
                        if((indexX>=0&&indexX<m)&&(indexY>=0&&indexY<m)&&(board[indexX][indexY].getVal()!=-1)){
                            int c=board[indexX][indexY].getVal();
                            c++;
                            board[indexX][indexY].set(c);
                        }
                    }
                }

            }
        }
    }

    @Override
    public void onClick(View view) {
    
    }
}
