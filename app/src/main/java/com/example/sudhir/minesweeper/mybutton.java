package com.example.sudhir.minesweeper;

import android.content.Context;
import android.widget.Button;

/**
 * Created by sudhir on 03/02/17.
 */

public class mybutton extends Button {
    int count;
    boolean mine;
    public int i;
    public int j;
    public mybutton(Context c)
    {
        super(c);
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
