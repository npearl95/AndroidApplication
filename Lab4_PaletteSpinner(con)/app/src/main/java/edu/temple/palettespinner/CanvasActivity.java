/*
Programming: Chau (Pearl) Nguyen
Lab3: Spinner and Custom Adapter
 */
package edu.temple.palettespinner;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
public class CanvasActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        //Extra the bundle to get the data and set the background with the parse data
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String selectColor = extras.getString("key");
            final View myLayout =findViewById(R.id.canvas);
            Toast.makeText(myLayout.getContext(),"Color is "+selectColor,Toast.LENGTH_SHORT).show();
            myLayout.setBackgroundColor(Color.parseColor(selectColor));
        }

    }
}
