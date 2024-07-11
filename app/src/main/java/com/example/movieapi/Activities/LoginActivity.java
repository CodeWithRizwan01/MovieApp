package com.example.movieapi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.movieapi.MainActivity;
import com.example.movieapi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText etNameLogin,etPasswordLogin;
    AppCompatButton btnLogin;
    LinearLayout lCreate;
    ProgressDialog pd;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        etNameLogin = findViewById(R.id.et_name_login);
        etPasswordLogin = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_login);
        lCreate = findViewById(R.id.create);

        pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("Login Account");
        pd.setMessage("Login to Your Account");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNameLogin.getText().toString().isEmpty()){
                    etNameLogin.setError("Enter your Email");
                    return;
                }
                if (etPasswordLogin.getText().toString().isEmpty()){
                    etPasswordLogin.setError("Enter your Password");
                    return;
                }
                pd.show();
                String email = etNameLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("email", mAuth.getCurrentUser().getEmail());
                                    intent.putExtra("uid", mAuth.getCurrentUser().getUid());
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                } else {
                                    pd.hide();
                                    etNameLogin.setText("");
                                    etPasswordLogin.setText("");
                                    Toast.makeText(LoginActivity.this, "Invalid Email/Password", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

        lCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });


    }
}