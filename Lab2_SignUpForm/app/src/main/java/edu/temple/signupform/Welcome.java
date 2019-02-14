/*
Programmer: Chau Nguyen
Lab2
Android application containing 2 activities. The first activity contains TextViews to collect a
user’s name, email, a password, and a password confirmation. It also contains a Save button.
The second activity is a rudimentary Welcome screen that just displays a message containing the user’s
name, welcoming them to the app. e.g. “Welcome, Jane Dough, to the SignUpForm App”
 */
package edu.temple.signupform;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            //The key argument here must match that used in the other activity

            // Capture the layout's TextView and set the string as its text
            TextView textView = findViewById(R.id.textView);
            textView.setText("Welcome, "+value+", to the SignUpForm App");

        }


    }
}
