package com.example.mangosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    // Fragments
    private HomeFragment homeFragment;
    private InsightFragment insightFragment;
    private StrategyFragment strategyFragment;
    private EducationFragment educationFragment;

    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.my_toolbar);
        toolbar.setTitle("Mangos");
        setSupportActionBar(toolbar);

        bottomNavigationView=findViewById(R.id.bottomNavigationbar);
        frameLayout=findViewById(R.id.main_frame);
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=findViewById(R.id.naView);
        navigationView.setNavigationItemSelectedListener(this);

        // Instanciando meus fragmentos
        homeFragment = new HomeFragment();
        insightFragment = new InsightFragment();
        strategyFragment = new StrategyFragment();
        educationFragment = new EducationFragment();
        profileFragment = new ProfileFragment();

        setFragment(homeFragment);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.insights:
                        setFragment(insightFragment);
                        return true;

                    case R.id.strategy_decision_board:
                        setFragment(strategyFragment);
                        return true;

                    case R.id.school:
                        setFragment(educationFragment);
                        return true;

                    case R.id.profile:
                        setFragment(profileFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void onBackPressed() {

        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
        }

    }

    public void displaySelectedListener(int itemId){

        Fragment fragment=null;

        switch (itemId){
            case R.id.home:
                fragment=new HomeFragment();
                break;

            case R.id.insights:
                fragment=new InsightFragment();
                break;

            case R.id.strategy_decision_board:
                fragment=new StrategyFragment();
                break;

            case R.id.school:
                fragment=new EducationFragment();
                break;

            case R.id.profile:
                fragment=new ProfileFragment();
                break;

            case R.id.logout:
                // mAuth.signOut();
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

        }

        if (fragment!=null){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListener(item.getItemId());
        return false;
    }


}