package com.example.cliff.filtertest.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.example.cliff.filtertest.Application;
import com.example.cliff.filtertest.R;
import com.example.cliff.filtertest.data.DragData;

/**
 * Created by Cliff on 10/6/2015.
 */
public class Activity extends android.app.Activity {

    protected Context context;

    public Activity() {
        context = this;
    }

    public int getThemeBackgroundColor() {
        TypedArray array = context.getTheme().obtainStyledAttributes(
                new int[]{ android.R.attr.colorBackground }
        );

        return array.getColor(0, 0xFF00FF);
    }

    //region Stubbed code required for reuse.
    public void resetBackgrounds() {
        // cw: If needed, this will be overridden by consiming code.
    }

    public void setDragData(DragData dd) {

    }

    public DragData getDragData() {
        return null;
    }
    //endregion

}
