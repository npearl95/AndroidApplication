/*
Programmer: Chau Nguyen
CIS 3515 lab
Exercise 2:
This is a Android Application. This handle multiple activities. ActivityA has a button to click
and to jump into ActivityB. Vice versa.
This is ActivityA
 */
package edu.temple.multiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityA extends Activity {
    Button buttonA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        buttonA=findViewById(R.id.buttonA);


        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goFromAtoB = new Intent(ActivityA.this, ActivityB.class);
                startActivity(goFromAtoB);
            }
        });

    }
}
