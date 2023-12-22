package com.emmanuel_yegon.cipherguard;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.emmanuel_yegon.cipherguard.databinding.ActivityMainBinding;
import com.emmanuel_yegon.cipherguard.fragments.AboutUsFragment;
import com.emmanuel_yegon.cipherguard.fragments.HomeFragment;
import com.emmanuel_yegon.cipherguard.fragments.MessagesFragment;
import com.emmanuel_yegon.cipherguard.fragments.SecretChangeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = binding.bnView;
        relativeLayout = binding.layoutMain;
        relativeLayout.setVisibility(View.VISIBLE);

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

}