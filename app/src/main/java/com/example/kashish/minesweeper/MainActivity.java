package com.example.kashish.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    LinearLayout rootLayout;
    int m=10,n=6;
    int nx[]={-1,-1,-1,0,0,1,1,1};
    int ny[]={-1,0,1,-1,1,-1,0,1};
  //  int nx[] = new int[8];
    //int ny[] = new int[8];

    public static boolean INCOMPLETE = true;
    public static int WIN=-1;
    MButton board[][];
    ArrayList<MButton> mineArray;
    ArrayList<LinearLayout> rows;
   public static final int MINE =-1;
   public static  int no_of_mine=10;
   public static  int SIZE=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout=findViewById(R.id.root);

//        nx[0]=-1; ny[0]=-1;
//        nx[1]=-1; ny[1]=0;
//        nx[2]=-1; ny[2]=1;
//        nx[3]=0; ny[3]=-1;
//        nx[4]=0; ny[4]=1;
//        nx[5]=1; ny[5]=-1;
//        nx[6]=1; ny[6]=0;
//        nx[7]=1; ny[7]=1;

        createBoard();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.reset){
            if(SIZE==0)
            no_of_mine=10;
            else if ( SIZE==1)
                no_of_mine=20;
            else if ( SIZE==2)
                no_of_mine=25;

            createBoard();
        }
        else if(id==R.id.size1){
            m=15;  n=10;
            SIZE=1;
            no_of_mine=20;
            createBoard();
        }
        else if(id==R.id.size2){
            m=20;  n=12;
            SIZE=2;
            no_of_mine=25;
            createBoard();
        }
//        else if(id==R.id.size1){
//            m=25;  n=15;
//            createBoard();
//        }
        return super.onOptionsItemSelected(item);
    }

    public void createBoard(){
       // currentStatus= incomplete;
        INCOMPLETE=true;
        rootLayout.removeAllViews();
        board=  new MButton[m][n];
        rows =new ArrayList<>();
        mineArray = new ArrayList<>();

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
                button.setBackgroundResource(android.R.drawable.btn_default);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(layoutParams);
                LinearLayout list=rows.get(i);
                button.setOnClickListener(this);
                button.setOnLongClickListener(this);
                list.addView(button);
                board[i][j]=button;

            }
        }
        setmines();
    }
    public void setmines(){
        Random rand= new Random();

        while(no_of_mine > 0)
        {
         int row = rand.nextInt(m);
         int col = rand.nextInt(n);

         if(board[row][col].getVal()!=-1){
             board[row][col].set(-1);
             mineArray.add(board[row][col]);
             no_of_mine--;
         }

        }

//        int row[]=new int[m];
//        int col[]=new int[n];
//        for(int i=0;i<m;i++){
//            row[i]=rand.nextInt(m);
//        }
//        for(int i=0;i<n;i++){
//            col[i]=rand.nextInt(n);
//        }
//
//        for(int i=0;mines>0;i++,mines--){
//            int r=row[i],c=col[i];
//            board[r][c].set(-1);
//            Log.i("MainActivity","Mine Set On i = "+r+" j = "+c);
//            mines--;
//        }
        setVals();
    }

    public void setVals() {


        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(board[i][j].getVal()==-1){
                    Log.i("MainActivity","Index with mine i = "+i+" j = "+j);

                    for(int k=0;k<8;k++){
                        int indexX=i+nx[k];
                        int indexY=j+ny[k];

                        Log.i("MainActivity","Index with mine i = "+i+" j = "+j);
                        if(indexX>=0&&indexX<m&&indexY>=0&&indexY<n&&board[indexX][indexY].getVal()!=-1){
                            int c=board[indexX][indexY].getVal();
                            c++;
                            board[indexX][indexY].set(c);
                        }
                    }
                }

            }
        }

        //show();
    }
//    public void show(){
//        for(int i=0;i<m;i++){
//            for(int j=0;j<n;j++){
//                board[i][j].setText(board[i][j].getVal()+"");
//            }
//        }
//    }
    @Override
    public void onClick(View view) {


        if(INCOMPLETE== true){
            game(view);
            checkGameStatus();
        }


    }
    public void game(View view){
        int id=view.getId();
        MButton button=(MButton)view;

        if(button.getVal()==-1 && button.lc==false){
            Toast.makeText(this, "gameOver", Toast.LENGTH_LONG).show();
            for(MButton b : mineArray){
                b.setText("M");
            }
            INCOMPLETE=false;
        }
        else if (button.getVal()==0 && button.lc == false){
            showNeighbours(button);
        }
        else  if(button.getVal()!=-1 && button.lc == false){
            button.setText(button.getVal()+"");
            button.shown=true;
        }

    }

    public void checkGameStatus(){
        int flag=0;
        for(int i=0;i<m;i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j].getVal() != -1 && board[i][j].shown == false) {
                    flag = 1;
                    break;
                }
            }
        }
            if (flag == 1)
                return;
            else if (flag == 0)
                Toast.makeText(this, "YOU WIN", Toast.LENGTH_LONG).show();


    }

    public void showNeighbours(MButton button){
//        if(button.getVal()!=0&&button.getVal()==-1){
//            return;
//        }

        button.shown=true;
        button.setText(button.getVal()+"");

        for(int k=0;k<8;k++){
            int indexX=button.i+nx[k];
            int indexY=button.j+ny[k];

            //bounds
            if(indexX>=0&&indexX<m&&indexY>=0&&indexY<n) {

                if (board[indexX][indexY].shown == false && board[indexX][indexY].getVal() == 0 && board[indexX][indexY].lc == false) {
                    board[indexX][indexY].shown = true;
                    board[indexX][indexY].setText(board[indexX][indexY].getVal()+"");
                    showNeighbours(board[indexX][indexY]);
                } else if (board[indexX][indexY].getVal() != -1 && board[indexX][indexY].getVal() != 0 && board[indexX][indexY].shown == false) {
                    board[indexX][indexY].setText(board[indexX][indexY].getVal() + "");
                    board[indexX][indexY].shown = true;
                }
            }
        }


    }
    public boolean onLongClick(View view){
        MButton button=(MButton)view;

        if(button.lc==false) {
            button.setBackgroundResource(R.drawable.ic_flag_black_24dp);
            button.lc = true;
            checkGameStatus();
        }
        else{
            button.setBackgroundResource(android.R.drawable.btn_default);
           button .setText("");
           button.lc=false;
        }

        return true;
    }

}
