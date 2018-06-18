package com.example.varsha.attendance_monitoring_system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText user, password, email, subcode;
    private Button signUp;
    private TextView userLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    //Upload dataBase
                    String user_email = email.getText().toString().trim();
                    String user_password = password.toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserData();
                                Toast.makeText(RegistrationActivity.this, "Registration Succesfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            } else
                                Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }
    private void setupUIViews() {
        user = (EditText) findViewById(R.id.user1);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.emailId);
        subcode = (EditText) findViewById(R.id.SubjectCode);
        signUp = (Button) findViewById(R.id.btn_login);
        userLogin = (TextView) findViewById(R.id.AlreadySignedIn);
    }

    private Boolean validate() {
        Boolean res = false;
        String name = user.getText().toString();
        String pass = password.getText().toString();
        String e = email.getText().toString();
        String code = subcode.getText().toString();
        if (name.isEmpty() || (pass.isEmpty()) || (e.isEmpty()) || (code.isEmpty())) {
            Toast.makeText(this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            res = true;
        }
        return res;
    }

    private void sendUserData() {
        String name = user.getText().toString();
        String pass = password.getText().toString();
        String e = email.getText().toString();
        String code = subcode.getText().toString();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        @SuppressLint("RestrictedApi") DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(name, e, code);

        myref.setValue(userProfile);
    }
}
