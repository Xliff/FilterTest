package com.example.cliff.filtertest.data;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Cliff on 8/20/2015.
 */
public class DragData {
    public ImageView i_Team = null;
    public TextView tv_pos, tv_fn, tv_ln, tv_bye, tv_rank;
    public PlayerData pd = null;
    public int origListPosition, width, height;
    public View itemView;
    public View originView;
}
