package com.example.kyle.ancientbritain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends Activity {
    public static int currentItem;
    static View.OnClickListener mainOnClickListener;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainOnClickListener = new MainOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<MainDataDef> mainData = new ArrayList<MainDataDef>();
        for (int i = 0; i < MainData.nameArray.length; i++) {
            mainData.add(new MainDataDef(
                    MainData.imageArray[i],
                    MainData.nameArray[i],
                    MainData.infoArray[i]
            ));
        }

        RecyclerView.Adapter adapter = new MainAdapter(mainData);
        // this line is the one that shows the cards
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class MainOnClickListener implements View.OnClickListener {
        private final Context context;

        private MainOnClickListener(Context c) {
            this.context = c;
        }

        @Override
        public void onClick(View v) {
            currentItem = recyclerView.getChildPosition(v);
            startActivity(new Intent(getApplicationContext(), DetailActivity.class));
        }

    }
}
