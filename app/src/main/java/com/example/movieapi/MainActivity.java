package com.example.movieapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieapi.AdapterClasses.popularAdapter;
import com.example.movieapi.Classes.MovieArray;
import com.example.movieapi.Interfaces.PopularApi;
import com.example.movieapi.ModelClasses.PopularMovies;
import com.example.movieapi.ModelClasses.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tvName;
    CircleImageView ivProfileImage;
    FirebaseAuth mAuth;
    AppCompatButton btnWatch;
    DatabaseReference userRef;
    EditText etSearch;
    RecyclerView recyclerView;
    popularAdapter adapter;
    List<PopularMovies> movieModelList = new ArrayList<>();
    ImageView imageView;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.black));


        tvName = findViewById(R.id.home_name);
        ivProfileImage = findViewById(R.id.iv_home);
        etSearch = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.recyclerView);
        imageView = findViewById(R.id.image_slider);
        btnWatch = findViewById(R.id.watch);



        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

            fetchUserData();
        } else {
            // Handle the case where the user is not signed in
            Toast.makeText(MainActivity.this, "User not signed in", Toast.LENGTH_SHORT).show();
            // Redirect to login activity or handle accordingly
            // startActivity(new Intent(MainActivity.this, LoginActivity.class));

        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PopularApi popularApi = retrofit.create(PopularApi.class);
        Call<MovieArray> call = popularApi.getMovies();

        call.enqueue(new Callback<MovieArray>() {
            @Override
            public void onResponse(Call<MovieArray> call, Response<MovieArray> response) {
                MovieArray movieArray = response.body();
                movieModelList = new ArrayList<>(Arrays.asList(movieArray.getResults()));

                putDataIntoRecyclerview(movieModelList);
            }

            @Override
            public void onFailure(Call<MovieArray> call, Throwable throwable) {

            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filterList(s.toString());
                recyclerView.scrollToPosition(0); // Scroll to the top
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
btnWatch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "this feature is available soon", Toast.LENGTH_SHORT).show();
    }
});
    }

    private void fetchUserData() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);

                    if (user != null) {
                        tvName.setText("Hlo, " + user.getName());

                        if (user.getpImage() != null) { // Check if fragment is added
                            Glide.with(MainActivity.this)
                                    .load(Uri.parse(user.getpImage()))
                                    .into(ivProfileImage);
//                        if (user.getpImage() != null) {
//                            Glide.with(getContext())
//                                    .load(Uri.parse(user.getpImage()))
//                                    .into(ivHomeProfile);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void putDataIntoRecyclerview(List<PopularMovies> movieModelList) {
        adapter = new popularAdapter(MainActivity.this,movieModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }


}