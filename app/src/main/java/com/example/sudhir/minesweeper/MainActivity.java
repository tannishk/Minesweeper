package com.example.sudhir.minesweeper;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int n = 9;
    int mines = 10;
    mybutton button[][];
    LinearLayout rows[];
    LinearLayout mainLayout;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.mainlayout);
        setUpBoard();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setUpBoard();
        return true;
    }

    private void setUpBoard() {
        button = new mybutton[n][n];
        rows = new LinearLayout[n];

        mainLayout.removeAllViews();
        mainLayout.removeAllViews();
        for (int i = 0; i < n; i++) {
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            //params.setMargins(5,5,5,5);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                button[i][j] = new mybutton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(5, 5, 5, 5);
                button[i][j].setLayoutParams(params);
                button[i][j].setText("");
                button[i][j].setMine(false);
                button[i][j].setBackgroundColor(Color.LTGRAY);
                button[i][j].i = i;
                button[i][j].j = j;
                rows[i].addView(button[i][j]);
            }
        }
        setUpMines();
        setUpCount();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                button[i][j].setOnClickListener(this);
            }
        }
    }

    private void setUpMines() {
        int count = 1;
        Random rn = new Random();
        int x;
        int y;
        while (count <= mines) {
            x = rn.nextInt(n);
            y = rn.nextInt(n);
            if (!button[x][y].isMine()) {
                // button[x][y].setText("*");
                button[x][y].setMine(true);
                button[x][y].setCount(-1);
                count++;
            }
        }
    }

    private void setUpCount() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!button[i][j].isMine()) {
                    button[i][j].setCount(count(i, j));
                    //   button[i][j].setText(button[i][j].getCount()+"");
                }
            }
        }
    }

    private int count(int i, int j) {
        int count = 0;
        if (isValid(i - 1, j - 1)) {
            if (button[i - 1][j - 1].isMine())
                count++;
        }
        if (isValid(i - 1, j)) {
            if (button[i - 1][j].isMine())
                count++;
        }
        if (isValid(i - 1, j + 1)) {
            if (button[i - 1][j + 1].isMine())
                count++;
        }
        if (isValid(i, j - 1)) {
            if (button[i][j - 1].isMine())
                count++;
        }
        if (isValid(i, j + 1)) {
            if (button[i][j + 1].isMine())
                count++;
        }
        if (isValid(i + 1, j - 1)) {
            if (button[i + 1][j - 1].isMine())
                count++;
        }
        if (isValid(i + 1, j)) {
            if (button[i + 1][j].isMine())
                count++;
        }
        if (isValid(i + 1, j + 1)) {
            if (button[i + 1][j + 1].isMine())
                count++;
        }
        return count;

    }

    boolean isValid(int i, int j) {
        return (i >= 0 && i < n) && (j >= 0 && j < n);
    }

    public void onClick(View v) {
        mybutton b = (mybutton) v;
        if (b.isMine()) {
            b.setText(b.getCount() + "");
            Toast t = Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT);
            t.show();
            setUpBoard();
        } else {
            b.setText(b.getCount() + "");
            if (b.getCount() == 0) {
                expand(b.i, b.j);
            }
            if (win()) {

                Toast t = Toast.makeText(this, "You Won", Toast.LENGTH_SHORT);
                t.show();
                setUpBoard();
            }
        }
    }
    boolean win()
    {
        int count = 1;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(!(button[i][j].getText().toString().equals("-1")||button[i][j].getText().toString().equals("")))
                    count++;
            }
        }
        if(count==(n*n-mines))
            return true;
        else
            return false;

    }
    private void expand(int i, int j) {
        if (isValid(i - 1, j - 1)) {

            if (button[i - 1][j - 1].getCount() == 0 && button[i - 1][j - 1].getText().toString().equals("")) {
                button[i - 1][j - 1].setText("" + button[i - 1][j - 1].getCount());
                expand(i - 1, j - 1);
            }
             else if (button[i - 1][j - 1].getCount() > 0 && button[i - 1][j - 1].getText().toString().equals("")) {
                button[i - 1][j - 1].setText("" + button[i - 1][j - 1].getCount());
                //expand(i - 1, j - 1);
            }
        }
        if (isValid(i - 1, j)) {

            if (button[i - 1][j].getCount() == 0 && button[i - 1][j].getText().toString().equals("")){
                button[i - 1][j].setText("" + button[i - 1][j].getCount());
                expand(i - 1, j);
            }
            else if (button[i - 1][j].getCount() > 0 && button[i - 1][j].getText().toString().equals("")){
                button[i - 1][j].setText("" + button[i - 1][j].getCount());
                //expand(i - 1, j);
            }
        }
        if (isValid(i - 1, j + 1)) {

            if (button[i - 1][j + 1].getCount() == 0 && button[i - 1][j + 1].getText().toString().equals("")){
                button[i - 1][j + 1].setText("" + button[i - 1][j + 1].getCount() + "");
                expand(i - 1, j + 1);
           }
           else if (button[i - 1][j + 1].getCount() > 0 && button[i - 1][j + 1].getText().toString().equals("")){
                button[i - 1][j + 1].setText("" + button[i - 1][j + 1].getCount() + "");
                //expand(i - 1, j + 1);
            }
        }
        if (isValid(i, j - 1)) {

            if (button[i][j - 1].getCount() == 0 && button[i][j - 1].getText().toString().equals("")){
                button[i][j - 1].setText("" + button[i][j - 1].getCount() + "");
                expand(i, j - 1);
            }
            else  if (button[i][j - 1].getCount() > 0 && button[i][j - 1].getText().toString().equals("")){
                button[i][j - 1].setText("" + button[i][j - 1].getCount() + "");
                //expand(i, j - 1);
            }
        }
        if (isValid(i, j + 1)) {

            if (button[i][j + 1].getCount() == 0 && button[i][j + 1].getText().toString().equals("")){
                button[i][j + 1].setText("" + button[i][j + 1].getCount() + "");
                expand(i, j + 1);
           }
            else  if (button[i][j + 1].getCount() > 0 && button[i][j + 1].getText().toString().equals("")){
                button[i][j + 1].setText("" + button[i][j + 1].getCount() + "");
               // expand(i, j + 1);
            }
        }
        if (isValid(i + 1, j - 1)) {

            if (button[i + 1][j - 1].getCount() == 0 && button[i + 1][j - 1].getText().toString().equals("")){
                button[i + 1][j - 1].setText("" + button[i + 1][j - 1].getCount() + "");
                expand(i + 1, j - 1);
           }
            else  if (button[i + 1][j - 1].getCount() > 0 && button[i + 1][j - 1].getText().toString().equals("")){
                button[i + 1][j - 1].setText("" + button[i + 1][j - 1].getCount() + "");
               // expand(i + 1, j - 1);
            }
        }
        if (isValid(i + 1, j)) {

            if (button[i + 1][j].getCount() == 0 && button[i + 1][j].getText().toString().equals("")){
                button[i + 1][j].setText("" + button[i + 1][j].getCount() + "");
            expand(i + 1, j);
        }
            else  if (button[i + 1][j].getCount() > 0 && button[i + 1][j].getText().toString().equals("")){
                button[i + 1][j].setText("" + button[i + 1][j].getCount() + "");
               // expand(i + 1, j);
            }
        }
        if (isValid(i + 1, j + 1)) {

            if (button[i + 1][j + 1].getCount() == 0 && button[i + 1][j + 1].getText().toString().equals("")) {
                button[i + 1][j + 1].setText("" + button[i + 1][j + 1].getCount() + "");
                expand(i + 1, j + 1);
            }
            else if (button[i + 1][j + 1].getCount() > 0 && button[i + 1][j + 1].getText().toString().equals("")) {
                button[i + 1][j + 1].setText("" + button[i + 1][j + 1].getCount() + "");
               // expand(i + 1, j + 1);
            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
