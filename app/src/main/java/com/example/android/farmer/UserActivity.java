package com.example.android.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    private FirebaseAuth mAuth;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();
        user = getIntent().getExtras().getString("phoneNumber");
        final Bundle bundle = new Bundle();
        bundle.putString("phoneNumber",user);

        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new HomeFragment()).commit();
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.home_nav:
                        selectedFragment = new HomeFragment();
                        selectedFragment.setArguments(bundle);
                        break;
                    case R.id.camera_nav:
                        selectedFragment = new CameraFragment();
                        selectedFragment.setArguments(bundle);
                        break;
                    case R.id.profile_nav:
                        selectedFragment = new ProfileFragment();
                        selectedFragment.setArguments(bundle);
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, selectedFragment).addToBackStack(null).commit();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.Sign_out:
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return true;
    }

   /* private void addFilePaths(){
        String path = System.getenv("EXTERNAL_STORAGE");
        pathArray.add(path+"/Pictures/Portal/image1.jpg");
        pathArray.add(path+"/Pictures/Portal/image2.jpg");
        pathArray.add(path+"/Pictures/Portal/image3.jpg");
        loadImageFromStorage();
    }

    private void loadImageFromStorage() {
        try {
            String path = pathArray.get(array_position);
            File f = new File(path, "");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        }catch (FileNotFoundException e){

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = UserActivity.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += UserActivity.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            String [] a = new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE};
            if(permissionCheck != 0){
                this.requestPermissions(a,permissionCheck);
            }else{
                Log.v("hell","helll");
            }
        }
    }*/
}
