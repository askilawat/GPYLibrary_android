package com.ask.gpylibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ask.gpylibrary.adapter.BooksAdapter;
import com.ask.gpylibrary.datemodel.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    private int PROFILE_ID = 0;//Should Be Update
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView booklist;
    private List<Book> books;
    private BooksAdapter booksAdapter;

    private TextInputEditText search;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    private SharedPreferences preferences;

    private static final String TAG = "MainActivity";

    @Override
    protected void onStart() {
        super.onStart();

        checkAuth();
        loadingDataToRecyclerView();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);

        preferences = getApplicationContext().getSharedPreferences("gpylibrary",0);
        PROFILE_ID = preferences.getInt("occup",0);

        toolbarAndDrawerSetup();
        recyclerViewWithBooks();

        search = findViewById(R.id.search);
    }

    private void checkAuth() {
        if (mAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void loadingDataToRecyclerView() {
        //Firebase
        firestore = FirebaseFirestore.getInstance();

        //getData
        firestore.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.e(TAG, "onComplete: 1",null);
                        if (task.isSuccessful()) {
                            Log.e(TAG, "onComplete: 2",null);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, "onComplete: 3",null);
                                Book object = document.toObject(Book.class);
                                Log.e(TAG, "onComplete: 4",null);
                                books.add(object);
                                Log.e(TAG, "onComplete: 5",null);
                            }
                            booksAdapter = new BooksAdapter(MainActivity.this,books);
                            Log.e(TAG, "onComplete: 6",null);
                            booklist.setAdapter(booksAdapter);
                            Log.e(TAG, "onComplete: 7",null);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(getApplicationContext(),"Error In Retriving Data",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void recyclerViewWithBooks() {
        books = new ArrayList<>();
        booklist = findViewById(R.id.b_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        booklist.setLayoutManager(layoutManager);
        booklist.setHasFixedSize(true);
    }

    private void toolbarAndDrawerSetup() {
        drawerLayout = findViewById(R.id.d_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.profie:
                        if (PROFILE_ID == 0)//Student
                        {
                            startActivity(new Intent(MainActivity.this, StudentProfile.class));
                        } else {
                            startActivity(new Intent(MainActivity.this, CollageStaffProfile.class));
                        }
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        return true;
                    case R.id.email:
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"atitkilawat@gmail.com"});
                        email.putExtra(Intent.EXTRA_SUBJECT, "Email From GPY Library");

                        email.setType("message/rfc822");

                        startActivity(Intent.createChooser(email, "Choose an Email client :"));

                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return true;
    }

    public void search(View view) {

        String searchtxt = search.getText().toString();

        final List<Book> booksNew = new ArrayList<>();

        if(books.size()!=0)
        {
            for(int i=0;i<books.size();i++){
                if(books.get(i).getName().contains(searchtxt))
                {
                    booksNew.add(books.get(i));
                }
            }

            //Update RecyclerView
            booksAdapter = new BooksAdapter(MainActivity.this,booksNew);
            Log.e(TAG, "onComplete: 6",null);
            booklist.setAdapter(booksAdapter);
        }else {
            Toast.makeText(getApplicationContext(),"No Books Available",Toast.LENGTH_SHORT).show();
        }
    }
}
