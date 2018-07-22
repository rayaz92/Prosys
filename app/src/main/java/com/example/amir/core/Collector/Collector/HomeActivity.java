package com.example.amir.core.Collector.Collector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;

public class HomeActivity extends AppCompatActivity {

    EditText colltxtsaleName;
    LinearLayout  llCollPayment , llCollDailyWork ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collactivity_home);

        colltxtsaleName = (EditText)findViewById(R.id.colltxtsaleName);
        llCollPayment =(LinearLayout) findViewById(R.id.llCollPayment);
        llCollDailyWork = (LinearLayout) findViewById(R.id.llCollDailyWork);

        colltxtsaleName.setText(CoreLogInActivity.UserName);

        llCollPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, com.example.amir.core.Collector.Collector.Main.class);
                startActivity(intent);
            }
        });

        llCollDailyWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DailyWork.class);
                startActivity(intent);
            }
        });

    }
}
