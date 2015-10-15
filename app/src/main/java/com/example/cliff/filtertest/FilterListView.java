package com.example.cliff.filtertest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

/**
 * TODO: document your custom view class.
 */
public class FilterListView extends LinearLayout {
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private Button b_OffenseButton;
    private Button b_DefenseButton;
    private Button b_TeamButton;
    private Button b_NameButton;

    private LinearLayout l_offPosLayout;
    private ToggleButton b_QBButton;
    private ToggleButton b_RBButton;
    private ToggleButton b_TEButton;
    private ToggleButton b_WRButton;
    private ToggleButton b_KButton;
    private ToggleButton b_offLBButton;

    private LinearLayout l_defPosLayout;
    private ToggleButton b_CBButton;
    private ToggleButton b_DEButton;
    private ToggleButton b_DTButton;
    private ToggleButton b_LBButton;
    private ToggleButton b_SButton;
    
    private LinearLayout l_teamLayout;
    private HorizontalScrollView s_TeamSelect;
    
    private LinearLayout l_NameLayout;
    private EditText t_nameText;

    protected View.OnClickListener h_buttonClickListener;
    
    private DragDropListView selectionList;

    public FilterListView(Context context) {
        super(context);
        init(null, 0);
    }

    public FilterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public FilterListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.filter_list_view, this);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
            attrs, R.styleable.FilterListView, defStyle, 0
        );

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        
        // cw: Set up UI Members -- Android Studio needs a generator for this.
        b_OffenseButton = (Button)findViewById(R.id.offButton);
        b_DefenseButton = (Button)findViewById(R.id.deButton);
        b_TeamButton = (Button)findViewById(R.id.teamButton);
        b_NameButton = (Button)findViewById(R.id.nameButton);

        l_offPosLayout = (LinearLayout)findViewById(R.id.offensePosLayout);
        b_QBButton = (ToggleButton)findViewById(R.id.qbButton);
        b_RBButton = (ToggleButton)findViewById(R.id.rbButton);
        b_TEButton = (ToggleButton)findViewById(R.id.teButton);
        b_WRButton = (ToggleButton)findViewById(R.id.wrButton);
        b_KButton = (ToggleButton)findViewById(R.id.kButton);
        b_offLBButton = (ToggleButton)findViewById(R.id.olbButton);

        l_defPosLayout = (LinearLayout)findViewById(R.id.defensePosLayout);
        b_CBButton = (ToggleButton)findViewById(R.id.cbButton);
        b_DEButton = (ToggleButton)findViewById(R.id.deButton);
        b_DTButton = (ToggleButton)findViewById(R.id.dtButton);
        b_LBButton = (ToggleButton)findViewById(R.id.dlbButton);
        b_SButton = (ToggleButton)findViewById(R.id.sButton);

        l_teamLayout = (LinearLayout)findViewById(R.id.teamLayout);
        s_TeamSelect = (HorizontalScrollView)findViewById(R.id.helmetSelector);

        l_NameLayout = (LinearLayout)findViewById(R.id.nameLayout);
        t_nameText = (EditText)findViewById(R.id.nameText);

        ToggleButton [] posButtons = {
            b_QBButton,
            b_RBButton,
            b_TEButton,
            b_WRButton,
            b_KButton ,
            b_CBButton,
            b_DEButton,
            b_DTButton,
            b_LBButton,
            b_SButton ,
        };

        final DragDropListView selectionList = (DragDropListView)findViewById(R.id.selectionList);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

        // cw: Set listener for position toggles.
        h_buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAdapter ca = (CustomAdapter)selectionList.getAdapter();
                // cw: On/Off text is the same, just pick one.
                ToggleButton tb = (ToggleButton)v;
                String pos = (String)tb.getTextOn();
                int posIdx;

                posIdx = ca.findPosition(pos);
                if (posIdx >= 0) {
                    ca.removePositionFilterIdx(posIdx);
                    tb.setChecked(false);
                } else {
                    ca.addPositionFilter(pos);
                    tb.setChecked(true);
                }
            }
        };
        for (ToggleButton tb: posButtons) {
            tb.setOnClickListener(h_buttonClickListener);
        }

    }

    private void invalidateTextPaintAndMeasurements() {

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

    }
}
