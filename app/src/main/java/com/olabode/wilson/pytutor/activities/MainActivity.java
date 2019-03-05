package com.olabode.wilson.pytutor.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.olabode.wilson.pytutor.QuizFiles.QuizStartScreen;
import com.olabode.wilson.pytutor.R;
import com.olabode.wilson.pytutor.fragment_exercises.DisplayExercisesFragment;
import com.olabode.wilson.pytutor.fragments_topics.LearnTopicsListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        setFirstItemChecked();
        if (savedInstanceState == null) {
            displaySelectedScreen(R.id.nav_Learn);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.anim.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * update the main screen as new fragment ..
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * get the current item clicked in the navigation drawer and switch to the
     * corresponding fragment.
     * @param id id for the items in the navigation view.
     */
    public void displaySelectedScreen(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_Learn:
                fragment = new  LearnTopicsListFragment();
                break;
            case R.id.nav_questions:
                fragment = new DisplayExercisesFragment();
                break;

            case R.id.nav_quiz:
                fragment = new QuizStartScreen();
                break;

            case R.id.nav_feedback:
                sendFeedback();
                break;

            case R.id.nav_share:
                break;
        }
        if (fragment != null) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }

    }


    /**
     * check the first item in navigation drawer.
     */
    public void setFirstItemChecked() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_Learn).setChecked(true);
    }


    /**
     * send feedback email
     */
    public void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "whilson03@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "feedback from PyTutor app");
        intent.putExtra(Intent.EXTRA_TEXT, "message");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }



}