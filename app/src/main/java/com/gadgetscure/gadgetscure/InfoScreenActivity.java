package com.gadgetscure.gadgetscure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoScreenActivity extends AppCompatActivity {
    public String device_issue, problem, cost;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_screen);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.infotoolbar);
        toolbar.setTitle("Booking Info");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mDatabaseReference = mFirebaseDatabase.getReference().child("messages");



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        device_issue = extras.getString("Issue");


        problem = extras.getString("Problem");
        cost = extras.getString("Price");

        TextView deviceissue = (TextView) findViewById(R.id.problem);
        TextView problem_type = (TextView) findViewById(R.id.problemtype);
        TextView price = (TextView) findViewById(R.id.price);
        deviceissue.setText(device_issue);
        problem_type.setText(problem);
        price.setText(cost);



        Button msg = (Button) findViewById(R.id.msg);

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                EditText name = (EditText) findViewById(R.id.user_name);
                EditText address = (EditText) findViewById(R.id.address);
                EditText phone = (EditText) findViewById(R.id.phone_num);
                String Name = name.getText().toString();

                String Address = address.getText().toString();
                String Phone = phone.getText().toString();
                EditText date = (EditText) findViewById(R.id.date);
                String Date = date.getText().toString();
                EditText time = (EditText) findViewById(R.id.time);
                String Time = time.getText().toString();


                String message = " Name : " + Name +
                        ",  Address : " + Address +
                        ",  Phone number : " + Phone +
                        ",  Pickup Date : " + Date +
                        ",  Pickup Time : " + Time +
                        ",  Device/Problem : " + device_issue +
                        ",  Problem : " + problem +
                        ",  Inspection Charges : " + cost;

                if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Address) || TextUtils.isEmpty(Phone) || TextUtils.isEmpty(Date) || TextUtils.isEmpty(Time)) {

                    Snackbar.make(v, "                !!   Please Fill All The Fields   !! ",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    flag=1;
                }

               else if (!TextUtils.isDigitsOnly(Phone) || (Phone.length()!=10))
                {
                    Snackbar.make(v, "                !!   Please Enter a Valid Number   !! ",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();



                    flag=1;
                }
                else if(!Name.matches("[a-zA-Z]+")){
                    flag=1;
                    Snackbar.make(v, "                !!   Please Enter a Valid Name   !! ",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();


                }

                if(flag==0) {
                   /* Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse("mailto:"));

                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"sysadmin@gadgetscure.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, device_issue + " : " + problem);
                    i.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(i, "Send Email"));
                    if (i.resolveActivity(getPackageManager()) != null) {
                        startActivity(i);
                    }*/
                    Toast.makeText(InfoScreenActivity.this, "!!   Your appointment has been booked   !!", Toast.LENGTH_SHORT).show();


                    mDatabaseReference.push().setValue(message);

                    Intent i = new Intent(InfoScreenActivity.this, MainActivity.class);
                    startActivity(i);





                }


            }
        });
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

