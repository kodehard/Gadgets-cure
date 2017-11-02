package com.gadgetscure.gadgetscure;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InfoScreenActivity extends AppCompatActivity {
    public String device_issue, problem, cost;


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


                String message = "Name : " + Name + "\n \nAddress : " + Address + "\n \nPhone number : " + Phone + "\n\n Pickup Date : " + Date + "\n \nPickup Time : " + Time + "\n\n Device/Problem : " + device_issue
                        + "\n\n Problem : " + problem + "\n\n Inspection Charges : " + cost;

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
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse("mailto:"));

                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"sysadmin@gadgetscure.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, device_issue + " : " + problem);
                    i.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(i, "Send Email"));
                    if (i.resolveActivity(getPackageManager()) != null) {
                        startActivity(i);
                    }



                }


            }
        });
    }


}

