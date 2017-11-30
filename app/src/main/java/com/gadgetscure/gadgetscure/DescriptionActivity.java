package com.gadgetscure.gadgetscure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

public class DescriptionActivity extends AppCompatActivity {
    private String device_issue, problem, cost, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

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

        Button next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        EditText describe = (EditText) findViewById(R.id.editText);
                                        describe.setSelection(0);
                                        description = describe.getText().toString();

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
