package com.gadgetscure.gadgetscure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;



public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private static final String TAG = "MainActivity";

    private SliderLayout mDemoSlider;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private static final int RC_SIGN_IN=1;
    private static final int RC_PHOTO_PICKER = 2;

   private static String mUsername,memail;

    private TextView nav_user,nav_mail,nav_picker;
    private ImageView nav_dp;
    private static Uri downloadUrl;
    private static  String url="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);





//
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Gadgets Cure");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // drawerLayout =(DrawerLayout) findViewById(R.id.drawer);
       // toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
       // drawerLayout.addDrawerListener(toggle);


      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationView nv =(NavigationView) findViewById(R.id.nav);
        View hView =  nv.getHeaderView(0);
        nav_user = (TextView)hView.findViewById(R.id.profile_name);
        nav_mail = (TextView)hView.findViewById(R.id.email);
        nav_picker = (TextView)hView.findViewById(R.id.picker);

        nav_dp=(ImageView)hView.findViewById(R.id.dp);

        SharedPreferences myPrefrence = getPreferences(MODE_PRIVATE);
        String imageS = myPrefrence.getString("imagePreferance", "");
        Bitmap imageB;
        if(!imageS.equals("")) {
            imageB = decodeToBase64(imageS);
            nav_dp.setImageBitmap(imageB);
        }



    mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("profile_pics");


        nav_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

            }
        });


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();
                if(id==R.id.sign_out)
                AuthUI.getInstance().signOut(MainActivity.this);
                else if(id == R.id.contact) {
                    Toast.makeText(MainActivity.this, mUsername+" It's Under Construction " , Toast.LENGTH_SHORT).show();

                }
                else if(id== R.id.rate)
                    Toast.makeText(MainActivity.this, mUsername+" It's Under Construction " , Toast.LENGTH_SHORT).show();
                else if(id == R.id.history)
                {
                    Intent i = new Intent(MainActivity.this,OrderActivity.class);
                    startActivity(i);
                }


                drawerLayout.closeDrawers();

                return true;
            }
        });










        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Refer and earn ",R.drawable.refer);
        file_maps.put("Flat 25% discount",R.drawable.discount);
        file_maps.put("Permium Services",R.drawable.premium);
        file_maps.put("Low Charges", R.drawable.lowprice);
        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(this);






        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    mUsername=user.getDisplayName();
                    memail=user.getEmail();
                    nav_user.setText(mUsername);
                    nav_mail.setText(memail);


                } else {
                    // User is signed out
                     mUsername="Anonymous";
                    memail="abc@xyz.com";
                    nav_user.setText(mUsername);
                    nav_mail.setText(memail);



                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder().setIsSmartLockEnabled(false).setProviders(
                                    AuthUI.EMAIL_PROVIDER, AuthUI.GOOGLE_PROVIDER).setTheme(R.style.LoginTheme)
                                    .setLogo(R.mipmap.bg_login).build(), RC_SIGN_IN);
                }


            }
        };

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
















    }


    public static String getMyString(){
        return mUsername;
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
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
               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
       }







    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, " your are Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }}
            if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK)
            {

                InputStream stream;
                try {
                    Toast.makeText(MainActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
                    stream = getContentResolver().openInputStream(data.getData());
                    Bitmap realImage = BitmapFactory.decodeStream(stream);
                    nav_dp.setImageBitmap(realImage);


                    SharedPreferences myPrefrence = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = myPrefrence.edit();
                    editor.putString("imagePreferance", encodeToBase64(realImage));

                    editor.commit();
                }
                catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
               /* Uri selectedImageUri = data.getData();

                // Get a reference to store file at chat_photos/<FILENAME>
                StorageReference photoRef = mStorageReference.child(selectedImageUri.getLastPathSegment());

                // Upload file to Firebase Storage
                photoRef.putFile(selectedImageUri)
                        .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // When the image has successfully uploaded, we get its download URL
                                 downloadUrl = taskSnapshot.getDownloadUrl();
                                String url=downloadUrl.toString();
                                Glide.with(nav_dp.getContext())
                                        .load(url)
                                        .into(nav_dp);




                                // Set the download URL to the message box, so that the user can send it to the database

                                                           }
                        });*/
            }

    }

    @Override
    protected void onResume() {
        super.onResume();

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }







    @Override
    protected void onPause() {



        super.onPause();

            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);


    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}
