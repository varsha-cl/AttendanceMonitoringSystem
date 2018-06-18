package com.example.varsha.attendance_monitoring_system;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText nUser,nPassword;
    private Button nLogin;
    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private TextView userReg;
    private TextView forgotPassword;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        nAuth=FirebaseAuth.getInstance();
        firebaseAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {
                    Intent intent=new Intent(MainActivity.this,MapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

            }
        };
        nUser=(EditText) findViewById(R.id.user1);
        nPassword= (EditText) findViewById( R.id.password);
        nLogin=(Button)findViewById( R.id.btn_login);
        userReg=(TextView)findViewById(R.id.NewUser);
        forgotPassword=(TextView)findViewById(R.id.forgotPassword);

        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegActivity.class));
            }
        });
        /*nRegister.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                final String email=nEmail.getText().toString();
                final String password=nPassword.getText().toString();
                nAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"sign in error",Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            String user_id=nAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db= FirebaseDatabase.getInstance().getReference().child("Users").child("Teachers").child(user_id);
                            current_user_db.setValue(true);
                        }
                    }
                });
            }
        });*/
        progressDialog =new ProgressDialog(this);
        nLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user=nUser.getText().toString();
                final String password=nPassword.getText().toString();
                validate(user,password);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PasswordActivity.class));
            }
        });

    }
private void validate(String username,String password)
{
    progressDialog.setMessage("Verifying");
    progressDialog.show();
    if(username.isEmpty())
        Toast.makeText(MainActivity.this,"Please Enter Username",Toast.LENGTH_SHORT);
    if(password.isEmpty())
        Toast.makeText(MainActivity.this,"Please Enter Password",Toast.LENGTH_SHORT);
    nAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful())
            {
                progressDialog.dismiss();
                startActivity(new Intent(MainActivity.this,UrlActivity.class));
                Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();

            }
            else
            {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,"Incorrect Username/Password",Toast.LENGTH_SHORT).show();

            }
        }
    });
}
    /*private void checkEmailVerification()
    {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag)
        {
            startActivity(new Intent(MainActivity.this,MapActivity.class));
        }
        else
        {
            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT);
            nAuth.signOut();
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        nAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        nAuth.removeAuthStateListener(firebaseAuthListener);
    }

}

