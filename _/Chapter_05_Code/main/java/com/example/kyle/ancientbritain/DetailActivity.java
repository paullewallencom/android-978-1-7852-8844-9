package com.example.kyle.ancientbritain;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class DetailActivity extends Activity {

    private static final int MIN_DISTANCE = 50;
    private static final int OFF_PATH = 150;
    private static final int VELOCITY_THRESHOLD = 60;

    private static final String DEBUG_TAG = "tag";

    View.OnTouchListener listener;
    private ImageView detailImage;
    private GestureDetector detector;
    private int ImageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detector = new GestureDetector(this, new GalleryGestureDetector());
        listener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        };

        ImageIndex = 0;

        detailImage = (ImageView) findViewById(R.id.detail_image);
        detailImage.setOnTouchListener(listener);

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
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(MainData.detailWebLink[MainActivity.currentItem]));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class GalleryGestureDetector extends GestureDetector.SimpleOnGestureListener {

        private int item = MainActivity.currentItem;


        /**
         * Notified when a tap occurs with the down {@link android.view.MotionEvent}
         * that triggered it. This will be triggered immediately for
         * every down event. All other events should be preceded by this.
         *
         * @param e The down motion event.
         */
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(DEBUG_TAG, "onDown");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(DEBUG_TAG, "onShowPress");
            detailImage.setElevation(4);
        }

        /**
         * Notified of a fling event when it occurs with the initial on down {@link android.view
         * .MotionEvent}
         * and the matching up {@link android.view.MotionEvent}. The calculated velocity is
         * supplied along
         * the x and y axis in pixels per second.
         *
         * @param e1        The first down motion event that started the fling.
         * @param e2        The move motion event that triggered the current onFling.
         * @param velocityX The velocity of this fling measured in pixels per second
         *                  along the x axis.
         * @param velocityY The velocity of this fling measured in pixels per second
         *                  along the y axis.
         * @return true if the event is consumed, else false
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling");
            if (Math.abs(e1.getY() - e2.getY()) > OFF_PATH)
                return false;

            if (MainData.galleryArray[item].length != 0) {
                // Swipe left
                if (e1.getX() - e2.getX() > MIN_DISTANCE && Math.abs(velocityX) >
                        VELOCITY_THRESHOLD) {
                    ImageIndex++;
                    if (ImageIndex == MainData.galleryArray[item].length) ImageIndex = 0;
                    detailImage.setImageResource(MainData.galleryArray[item][ImageIndex]);
                    Log.d(DEBUG_TAG, "left");
                } else {
                    // Swipe right
                    if (e2.getX() - e1.getX() > MIN_DISTANCE && Math.abs(velocityX) >
                            VELOCITY_THRESHOLD) {
                        ImageIndex--;
                        if (ImageIndex < 0) ImageIndex = MainData.galleryArray[item].length - 1;
                        detailImage.setImageResource(MainData.galleryArray[item][ImageIndex]);
                        Log.d(DEBUG_TAG, "right");
                    }
                }
            }
	    detailImage.setElevation(0);
            return true;
        }
    }
}
