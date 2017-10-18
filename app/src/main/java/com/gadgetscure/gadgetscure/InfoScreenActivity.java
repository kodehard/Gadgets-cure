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
   public String device_issue,problem,cost,message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.infotoolbar);
        toolbar.setTitle("Booking Info");
        toolbar.setTitleTextColor(Color.WHITE);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        device_issue = extras.getString("Issue");
        device_issue = device_issue.substring(0, device_issue.length() - 1);

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
                EditText name = (EditText) findViewById(R.id.user_name);
                EditText address = (EditText) findViewById(R.id.address);
                EditText phone = (EditText) findViewById(R.id.phone_num);
                String Name = name.getText().toString();

                String Address = address.getText().toString();
                String Phone = phone.getText().toString();
                String message = "Name : " + Name + "\n Address : " + Address + "\n Phone number : " + Phone + "\n Device : " + device_issue
                        + "\n Problem : " + problem + "\n Inspection Charges : " + cost;
                if(TextUtils.isEmpty(Name)||TextUtils.isEmpty(Address)||TextUtils.isEmpty(Phone)) {
                    //Toast.makeText(InfoScreenActivity.this, " !! Please enter all the fields !! ", Toast.LENGTH_LONG).show();
                    Snackbar.make(v,"                !!   Please Fill All The Fields   !! ",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else
                    {

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

