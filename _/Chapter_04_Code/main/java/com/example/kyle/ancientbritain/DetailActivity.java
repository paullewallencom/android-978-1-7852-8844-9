package com.example.kyle.ancientbritain;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class DetailActivity extends Activity {
    private ArrayList<MainDataDef> detailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView detailImage = (ImageView) findViewById(R.id.detail_image);
        TextView detailName = (TextView) findViewById(R.id.detail_name);
        TextView detailDistance = (TextView) findViewById(R.id.detail_distance);
        TextView detailText = (TextView) findViewById(R.id.detail_text);
        detailText.setMovementMethod(new ScrollingMovementMethod());
        ImageView detailWebLink = (ImageView) findViewById(R.id.detail_web_link);

        int i = MainActivity.currentItem;
        Random n = new Random();
        int m = n.nextInt((600 - 20) + 1) + 20;
        setTitle(getString(R.string.app_name) + " - " + MainData.nameArray[i]);
        detailImage.setImageResource(MainData.detailImageArray[i]);
        detailName.setText(MainData.nameArray[i]);
        detailDistance.setText(String.valueOf(m) + " miles");
        detailText.setText(MainData.detailTextArray[i]);

        detailWebLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                //intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(MainData.detailWebLink[MainActivity.currentItem]));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
