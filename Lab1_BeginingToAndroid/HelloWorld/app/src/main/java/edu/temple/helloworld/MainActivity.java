/*
Programmer: Chau Nguyen
CIS3515
Exercise 1: This is a first android App.
This taught me how to edit/add new XML under resources and android properties.
 */
package edu.temple.helloworld;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
