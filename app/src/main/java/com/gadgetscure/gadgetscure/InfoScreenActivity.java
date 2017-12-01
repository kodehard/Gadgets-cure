package com.gadgetscure.gadgetscure;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;



public class InfoScreenActivity extends AppCompatActivity {
    public String device_issue, problem, cost,description,Date,Time,ampm;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
        private final Random rand = new Random();
    private int t=0,d=0;

    private  int dd,mm,yy;
    private EditText mPickDate;
    private EditText time;
   private int mYear;
    private int mMonth;
   private int mDay;
    private int hr,min;
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID=1;





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
        description = extras.getString("Description");

        TextView deviceissue = (TextView) findViewById(R.id.problem);
        TextView problem_type = (TextView) findViewById(R.id.problemtype);
        final TextView price = (TextView) findViewById(R.id.price);
        deviceissue.setText(device_issue);
        problem_type.setText(problem);
        price.setText(cost);
        final TextView Description = (TextView) findViewById(R.id.description);
        Description.setText(description);

        mPickDate = (EditText) findViewById(R.id.mPickDate);//button for showing date picker dialog
        mPickDate.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) { showDialog(DATE_DIALOG_ID); }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
       dd=mDay;
        mm=mMonth;
        yy=mYear;

        // display the current date
        updateDisplay();

         time = (EditText) findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // showTimePickerDialog(view);
                showDialog(TIME_DIALOG_ID);
            }
        });
        Calendar initialTime = Calendar.getInstance();

        hr = initialTime.get(Calendar.HOUR_OF_DAY);
        min= initialTime.get(Calendar.MINUTE);
        updateDisplayTime();


        ImageView add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent n= new Intent(InfoScreenActivity.this,DescriptionActivity.class);
                                       Bundle extras = new Bundle();
                                       extras.putString("Issue",device_issue);
                                       extras.putString("Problem",problem);
                                       extras.putString("Price",cost);
                                       n.putExtras(extras);
                                       startActivity(n);
                                   }
                               }


        );






        Button msg = (Button) findViewById(R.id.msg);

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;

                EditText name = (EditText) findViewById(R.id.user_name);
                EditText address = (EditText) findViewById(R.id.address);
                EditText phone = (EditText) findViewById(R.id.phone_num);
                String Name = name.getText().toString();

                String Address = address.getText().toString();
                String Phone = phone.getText().toString();
               // EditText date = (EditText) findViewById(R.id.datetext);
                //String Date = date.getText().toString();
                Date= mPickDate.getText().toString();
               // EditText time = (EditText) findViewById(R.id.time);
               Time = time.getText().toString();
                long x = 10011100011000l;
                long y = 10001001000101l;
                long n = x+((long)(rand.nextDouble()*(y-x)));




                String message = "Ref : "+n+",  Name : " + Name +
                        ",  Address : " + Address +
                        ",  Phone number : " + Phone +
                        ",  Pickup Date : " + Date +
                        ",  Pickup Time : " + Time +
                        ",  Device/Problem : " + device_issue +
                        ",  Problem : " + problem +
                        ",  Inspection Charges : " + cost+
                        ",  Description : "+description;

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

                 if(d==1)
                {

                    Snackbar.make(v, "                !!   Please Enter a Valid Date  !! ",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                 if(t==1)
                {

                    Snackbar.make(v, "                !!   Please Enter a Valid Time  !! ",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }



                if(flag==0 && t==0 && d==0) {
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

                    //FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                   // Toast.makeText(InfoScreenActivity.this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                    message = "Name : " + Name +
                            "\nAddress : " + Address +
                            "\nPhone number : " + Phone +
                            "\nPickup Date : " + Date +
                            "\nPickup Time : " + Time +
                            "\nDevice/Problem : " + device_issue +
                            "\nProblem : " + problem +
                            "\nInspection Charges : " + cost;


                    Intent i = new Intent(InfoScreenActivity.this, Receipt.class);
                    Bundle extras = new Bundle();
                    extras.putString("UserId" ,String.valueOf(n));
                    extras.putString("Message",message);
                    i.putExtras(extras);
                    startActivity(i);







                }


            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if(id== DATE_DIALOG_ID)
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
      else if(id==TIME_DIALOG_ID) {
            return new TimePickerDialog(this, mTimeSetListener, hr, min,false);
        }

        return null;
    }



    //update month day year
    private void updateDisplay() {
            if(mDay<dd || mMonth<mm || mYear<yy) {
                d=1;
            mPickDate.setText("Enter a valid Date");
            Toast.makeText(this, "Unfortunately we don't have a time machine !!", Toast.LENGTH_SHORT).show();
        }
        else {
                d=0;
                mPickDate.setText(//this is the edit text where you want to show the selected date
                        new StringBuilder()
                                // Month is 0 based so add 1
                                .append(mDay).append("-")
                                .append(mMonth + 1).append("-")
                                .append(mYear).append(""));
            }


        //.append(mMonth + 1).append("-")
        //.append(mDay).append("-")
        //.append(mYear).append(" "));
    }


    private void updateDisplayTime() {
        String newtime;
        int flag=0;
        if(hr< 12) {
            ampm = "AM";
        } else {
            ampm = "PM";
        }
        if(min<10)
            newtime= "0"+String.valueOf(min);
        else
            newtime=String.valueOf(min);
        if(hr<9)
            flag=1;
        if(hr>=19) {
            if ((hr + min) > 19)
                flag = 1;
            else
                flag=0;
        }
        if(flag==1) {
            t=1;
            time.setText("Enter between 9 AM to 7 PM");
        }
        else {
            t=0;
            time.setText(//this is the edit text where you want to show the selected date
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(hr).append(":")
                            .append(newtime).append(" ")
                            .append(ampm));
        }
    }
        // the call back received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int i, int i1) {
                    hr=i;
                    min=i1;
                    updateDisplayTime();
                }

            };


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

