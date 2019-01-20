package com.chb.chbui.QQDragBall;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chb.chbui.R;

public class QQDragBallActivity extends Activity {

    QQDragBall ball;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_ball);
        ball = findViewById(R.id.qq_ball);
//        ball.setMsgCount(13);
//        ball.setBallColor(Color.RED);
//        ball.setTextColor(Color.BLACK);
        ball.setListener(new QQDragBallListener() {
            @Override
            public void onTouchDown() {
            }

            @Override
            public void onTouchMove() {

            }

            @Override
            public void onTouchUpInRange() {

            }

            @Override
            public void onTouchUpOutRange() {

            }
        });

        editText = findViewById(R.id.et_msg_count);
        findViewById(R.id.btn_set_msg_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                    int count = Integer.parseInt(editText.getText().toString().trim());
                    ball.setMsgCount(count);
                }
            }
        });
        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ball.reset();
            }
        });
    }
}
