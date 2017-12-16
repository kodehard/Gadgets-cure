package com.gadgetscure.gadgetscure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DescriptionActivity extends AppCompatActivity {
    private String device_issue, problem, cost, description;
    private EditText describe;
    private boolean internetConnectionAvailable(int timeOut) {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (TimeoutException e) {
        }
        return inetAddress!=null && !inetAddress.equals("");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        if(!internetConnectionAvailable(7000)){

            RelativeLayout noConnect =(RelativeLayout)findViewById(R.id.emptyview);
            RelativeLayout yesConnect =(RelativeLayout)findViewById(R.id.lin);
            yesConnect.setVisibility(View.INVISIBLE);
            noConnect.setVisibility(View.VISIBLE);
            Button tryAgain=(Button)findViewById(R.id.try_again);
            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(DescriptionActivity.this,MainActivity.class);
                    startActivity(i);
                }
            });


        }

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.Bookingtoolbar);
        toolbar.setTitle("Description");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        device_issue = extras.getString("Issue");


        problem = extras.getString("Problem");
        cost = extras.getString("Price");

        TextView deviceissue = (TextView) findViewById(R.id.problem);
        TextView problem_type = (TextView) findViewById(R.id.problemtype);
        deviceissue.setText(device_issue);
        problem_type.setText(problem);


        describe = (EditText) findViewById(R.id.editText);
        describe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                describe.setFocusableInTouchMode(true);
            }
        });


        Button next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                                                             //  describe.setSelection(0);
                                        description = describe.getText().toString();
                                        if(TextUtils.isEmpty(description))
                                            Toast.makeText(DescriptionActivity.this," A description would be more helpful !!",Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(DescriptionActivity.this, InfoScreenActivity.class);
                                        Bundle extras = new Bundle();


                                        extras.putString("Issue", device_issue);
                                        extras.putString("Problem", problem);
                                        extras.putString("Price", cost);
                                        extras.putString("Description", description);
                                        i.putExtras(extras);
                                        startActivity(i);


                                    }
                                }
        );

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
