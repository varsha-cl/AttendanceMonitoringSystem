package com.example.varsha.attendance_monitoring_system;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {
private EditText passwordE;
private Button ResetPassword;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        passwordE=(EditText)findViewById(R.id.passwordemail);
        ResetPassword=(Button)findViewById(R.id.btn_passwordReset);
        firebaseAuth=FirebaseAuth.getInstance();
        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email=passwordE.getText().toString().trim();
                if(user_email.isEmpty())
                {
                    Toast.makeText(PasswordActivity.this,"Please enter your registered email-id",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PasswordActivity.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                            }
                            else {
                                Toast.makeText(PasswordActivity.this,"Error sending email/Enter registered email",Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
            }
        });
    }
}
