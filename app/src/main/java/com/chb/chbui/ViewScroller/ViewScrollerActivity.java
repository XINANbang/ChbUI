package com.chb.chbui.ViewScroller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chb.chbui.R;

/**
 * create by chenhanbin at 2019/3/26
 **/
public class ViewScrollerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_scroller);
        findViewById(R.id.scrollView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("chenhanbin", "onClick: v = " + v);
                ((ViewScroller)v).smoothScrollTo(300, 300);
            }
        });
    }
}
