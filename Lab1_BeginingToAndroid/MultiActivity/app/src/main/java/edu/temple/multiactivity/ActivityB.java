/*
Programmer: Chau Nguyen
CIS 3515 lab
Exercise 2:
This is a Android Application. This handle multiple activities. ActivityA has a button to click
and to jump into ActivityB. Vice versa.
This is ActivityB
 */
package edu.temple.multiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityB extends Activity {
    Button buttonB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        buttonB=findViewById(R.id.buttonB);

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goFromBtoA = new Intent(ActivityB.this,ActivityA.class);
                startActivity(goFromBtoA);
            }
        });

    }
}
