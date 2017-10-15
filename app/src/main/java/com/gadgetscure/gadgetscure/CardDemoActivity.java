package com.gadgetscure.gadgetscure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static com.gadgetscure.gadgetscure.R.id.toolbar;


public class CardDemoActivity extends Activity {
    Toolbar toolbar;
    String issue,price;
    private ArrayList<String> issues;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i= getIntent();
        Bundle b = i.getExtras();
        issue=b.getString("Issue");
        price=b.getString("Price");

        issue = issue.substring(0, issue.length() - 1);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(issue+" Issues");
        toolbar.setTitleTextColor(Color.WHITE);
        initViews();
    }

    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        issues = new ArrayList<>();
        issues.add("Not powering ON");
        issues.add("Not Charging");
        issues.add("Display Damage");
        issues.add("Slow Processing");
        issues.add("Battery problem");
        issues.add("Over Heating");
        issues.add("Speaker Issue");
        issues.add("Body Damage");
        issues.add("Camera Issue");
        issues.add("Keys/Buttons Not Working");
        issues.add("No display/Blank display");
        issues.add("Other/None of the Above");
        RecyclerView.Adapter adapter = new DataAdapter(issues);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    context=rv.getContext();
                    Intent i = new Intent(context, InfoScreen.class);
                    Bundle extras = new Bundle();


                    extras.putString("Issue",issue+" issues");
                    extras.putString("Problem",issues.get(position));
                    extras.putString("Price",price);
                    i.putExtras(extras);
                    context.startActivity(i);

                    // Toast.makeText(getApplicationContext(), issues.get(position), Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
}
