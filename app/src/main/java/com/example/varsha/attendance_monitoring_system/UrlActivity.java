package com.example.varsha.attendance_monitoring_system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UrlActivity extends AppCompatActivity {
public EditText googleSheetUrl;
public Button submit,checkAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        googleSheetUrl=(EditText)findViewById(R.id.url);
        submit=(Button)findViewById(R.id.submit);
       // checkAttendance=(Button)findViewById(R.id.checkAttendance);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String urn=googleSheetUrl.getText().toString();
                if(urn.isEmpty()) {
                    Toast.makeText(UrlActivity.this, "Please Enter the google sheet url", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(UrlActivity.this,MapActivity.class);
                    intent.putExtra("extra", urn);

                    startActivity(intent);
                    Toast.makeText(UrlActivity.this,"Success",Toast.LENGTH_SHORT).show();
                }


                }
            });
       /* checkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UrlActivity.this,DownloadActivity.class));
            }
        });*/
            }

    }

