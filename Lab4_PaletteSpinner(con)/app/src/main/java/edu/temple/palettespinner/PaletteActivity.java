/*
Programming: Chau (Pearl) Nguyen
Lab3: Spinner and Custom Adapter
 */
package edu.temple.palettespinner;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
public class PaletteActivity extends Activity {
    //Variable
    private Spinner spinner;
    //Color array
    String[] color={ "Select Color","Red", "Yellow", "Green", "Aqua","White"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
        colorSpinner();

    }

    //Create this class call ColorSpinner
    public void colorSpinner(){
        //Connect the spinner and view
        spinner = (Spinner) findViewById(R.id.color_spinner);
        final View myLayout =findViewById(R.id.activity_palette);

        //Create an custom adapter
        final CustomAdapter myAdapter = new CustomAdapter(this, color);

        //Attach the array data to the spinner
        spinner.setAdapter(myAdapter);
        //Attach spinner behavior
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //First first item on the list
                if(position==0) {
                    Toast.makeText(myLayout.getContext(), "Please select" , Toast.LENGTH_SHORT).show();
                }else {
                    //get the color from the array
                    String selectColor = parent.getItemAtPosition(position).toString();
                    //sent a Toast
                    Toast.makeText(myLayout.getContext(), "Color is " + selectColor, Toast.LENGTH_SHORT).show();
                    //parse color to next activity
                    Intent intent = new Intent(PaletteActivity.this, CanvasActivity.class);
                    intent.putExtra("key",selectColor);
                    startActivity(intent);
                    }

            }

                @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}
