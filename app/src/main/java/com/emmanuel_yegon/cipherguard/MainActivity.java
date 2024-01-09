package com.emmanuel_yegon.cipherguard;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.emmanuel_yegon.cipherguard.Model.Key;
import com.emmanuel_yegon.cipherguard.database.RoomDB;
import com.emmanuel_yegon.cipherguard.databinding.ActivityMainBinding;
import com.emmanuel_yegon.cipherguard.fragments.AboutUsFragment;
import com.emmanuel_yegon.cipherguard.fragments.HomeFragment;
import com.emmanuel_yegon.cipherguard.fragments.MessagesFragment;
import com.emmanuel_yegon.cipherguard.fragments.SecretChangeFragment;
import com.emmanuel_yegon.cipherguard.utility.AuthenticatorTask;
import com.emmanuel_yegon.cipherguard.utility.FingerPrintAuthenticator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;

    RoomDB database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        persistKey();

        bottomNavigationView = binding.bnView;
        relativeLayout = binding.layoutMain;
        //relativeLayout.setVisibility(View.VISIBLE);

        try {
            AuthenticatorTask authenticatorTask = new AuthenticatorTask() {
                @Override
                public void afterValidationSuccess() {
                    relativeLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onValidationFailed() {
                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                    if (keyguardManager.isKeyguardSecure()){
                        ((Activity)MainActivity.this).finish();
                        System.exit(0);
                    }else {
                        Toast.makeText(MainActivity.this,"Phone doesn't contain password",Toast.LENGTH_SHORT).show();
                    }
                }
            };

            List<Key> keyList = database.keyDao().getAllKey();
            Boolean security = keyList.get(0).getSecurity();

            if (security){
                FingerPrintAuthenticator fingerPrintAuthenticator = new FingerPrintAuthenticator(MainActivity.this, authenticatorTask,relativeLayout);
                fingerPrintAuthenticator.addAuthentication();
            }else {
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    loadFragment(new HomeFragment(), false);
                    return true;
                } else if (item.getItemId() == R.id.nav_message) {
                    loadFragment(new MessagesFragment(), false);
                    return true;
                } else if (item.getItemId() == R.id.nav_aboutUs) {
                    loadFragment(new AboutUsFragment(), false);
                    return true;
                } else if (item.getItemId() == R.id.nav_changekey) {
                    loadFragment(new SecretChangeFragment(), false);
                    return true;
                } else {
                    Toast.makeText(MainActivity.this, "Default CLicked", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void loadFragment(Fragment fragment, boolean flag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (flag) {
            fragmentTransaction.add(R.id.container, fragment);
        } else {
            fragmentTransaction.replace(R.id.container, fragment);
        }
        fragmentTransaction.commit();
    }

    private void persistKey(){
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();

            database = RoomDB.getInstance(this);
            if(!prefs.getBoolean("firstTime",false)){
                Key key  = new Key();
                key.setKey("HFUIWEI(U&JFHDSH#dhgf$");
                key.setMessagebackup(true);
                key.setSecurity(false);
                database.keyDao().saveItem(key);
                editor.putBoolean("firstTime",true);
                editor.commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static final  int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if(mBackPressed+TIME_INTERVAL>System.currentTimeMillis()){
            ((Activity)MainActivity.this).finish();
            System.exit(0);
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(this,"Tap back Button again in order to exit.",Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}