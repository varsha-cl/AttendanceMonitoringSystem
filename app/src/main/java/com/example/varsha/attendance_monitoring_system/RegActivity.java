package com.example.varsha.attendance_monitoring_system;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity{

    private EditText userName, userPassword, userEmail, subcode;
    private Button regButton;
    private TextView useLogin;
    private FirebaseAuth firebaseAuth;

    String email, name, code, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to the database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                //sendEmailVerification();
                                sendUserData();
                                firebaseAuth.signOut();
                                Toast.makeText(RegActivity.this, "Successfully Registered, Upload complete!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(RegActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        useLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegActivity.this, MainActivity.class));
            }
        });

    }

    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        regButton = (Button)findViewById(R.id.btnRegister);
        useLogin = (TextView)findViewById(R.id.tvUserLogin);
        subcode = (EditText)findViewById(R.id.sub_code);
    }

    private Boolean validate(){
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        code = subcode.getText().toString();


        if(name.isEmpty() || password.isEmpty() || email.isEmpty() ||code.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }


   /* private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(RegActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegActivity.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        @SuppressLint("RestrictedApi")DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(name, email, code);
        myRef.setValue(userProfile);
    }
}