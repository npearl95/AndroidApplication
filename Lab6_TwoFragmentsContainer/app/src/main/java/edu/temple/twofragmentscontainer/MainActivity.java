package edu.temple.twofragmentscontainer;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements PaletteFragment.OnFragmentInteractionListener {
    boolean twoPanes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if two fragments are being displayed
        twoPanes = (findViewById(R.id.canvas_fragment) != null);
        // Create a new instance of the palette fragment that displays all the colors
        //It should not be on the stack
        PaletteFragment paletteFragment = new PaletteFragment();
        //load the main fragment, PlaceOnBackStack is false because it's a main activity
        loadFragment(R.id.main_fragment, paletteFragment, false);


        // If two fragments are being displayed then load the 2nd panes
        // or else remove the canvas fragment if it is being displayed
        //PlaceOnBackStack is false because it's a main activity. it shouldn't be on the stack
        if(twoPanes){
            loadFragment(R.id.canvas_fragment, new CanvasFragment(), false);
        } else {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.canvas_fragment);
            if(fragment != null) {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }
    //Display a new color on the canvas fragment
    @Override
    public void displayNewColor(String colorValue) {
        Fragment canvasFragment;
        // If two panes are being displayed then display the color on the second pane
        // or else if one pane is being displayed then display the canvas fragment on top o the palette fragment
        if(twoPanes){
            canvasFragment = getFragmentManager().findFragmentById(R.id.canvas_fragment);

            //change the canvas fragment
            ((CanvasFragment) canvasFragment).changeCanvasColor(colorValue);
        } else {
            canvasFragment = new CanvasFragment();
            //if 2panes, change load it to canvas Fragment, and don't add placeOnBackStack (false)
            //if not, replace the mainFragment to CanvasFragment and add placeOnBackStack (true)
            //loadFragment(twoPanes ? R.id.canvas_fragment : R.id.main_fragment, canvasFragment, !twoPanes);
            loadFragment(R.id.main_fragment, canvasFragment, true);
            //change the canvas fragment
            ((CanvasFragment) canvasFragment).changeCanvasColor(colorValue);
        }
    }
    // Load fragment in a specified frame
    private void loadFragment(int paneId, Fragment fragment, boolean placeOnBackStack){
        FragmentManager fm = getFragmentManager();
        //replace container id with a chosen fragment
        FragmentTransaction ft = fm.beginTransaction().replace(paneId, fragment);
        //Add to stack if it true
        if(placeOnBackStack){
            ft.addToBackStack(null);
        }
        //commit()
        ft.commit();
        fm.executePendingTransactions();
    }
}
