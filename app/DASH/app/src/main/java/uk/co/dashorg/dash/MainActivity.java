package uk.co.dashorg.dash;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uk.co.dashorg.dash.fragments.AboutFragment;
import uk.co.dashorg.dash.fragments.ContactFragment;
import uk.co.dashorg.dash.fragments.DevFragment;
import uk.co.dashorg.dash.fragments.FeedbackFragment;
import uk.co.dashorg.dash.fragments.InfoFragment;
import uk.co.dashorg.dash.fragments.AccountFragment;
import uk.co.dashorg.dash.fragments.NewsFragment;
import uk.co.dashorg.dash.fragments.TrackFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            updateFragment(R.id.nav_account);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        updateFragment(item.getItemId());
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateFragment(int id) {
        Fragment newFragment = getFragment(id);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFragment).commit();
        currentFragment = newFragment;
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(id).setChecked(true);
    }

    public Fragment getFragment(int id) {
        switch (id) {
            case R.id.nav_account: return new AccountFragment();
            case R.id.nav_contact: return new ContactFragment();
            case R.id.nav_track: return new TrackFragment();
            case R.id.nav_news: return new NewsFragment();
            case R.id.nav_info: return new InfoFragment();
            case R.id.nav_feedback: return new FeedbackFragment();
            case R.id.nav_about: return new AboutFragment();
            case R.id.nav_dev: return new DevFragment();
            default:
                throw new IllegalArgumentException("Unrecognised id");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestCodes.INFO_FRAGMENT_CALL_PHONE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ((InfoFragment) currentFragment).dial();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static void setTitle(Fragment fragment, String title) {
        MainActivity activity = ((MainActivity)fragment.getActivity());
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(title);
            }
        }
    }
}
