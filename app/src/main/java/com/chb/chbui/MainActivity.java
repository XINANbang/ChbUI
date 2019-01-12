package com.chb.chbui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView mGridView;
    private Sample[] mSamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSamples = new Sample[]{
                new Sample("贝塞尔曲线实现水波动画", Wave.class),
        };

        mGridView = findViewById(R.id.cardlist);
        mGridView.setAdapter(new CardAdapter());
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class CardAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSamples.length;
        }

        @Override
        public Object getItem(int position) {
            return mSamples[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.card_item,
                        container, false);
            }

            ((TextView) convertView.findViewById(R.id.card_text)).setText(
                    mSamples[position].title);

            return convertView;
        }
    }


    private class Sample {
        String title;
        Intent intent;

        private Sample(String title, Intent intent) {
            this.intent = intent;
            this.title = title;
        }

        private Sample(String title, Class<? extends Activity> activityClass) {
            this(title, new Intent(MainActivity.this, activityClass));
        }
    }
}
