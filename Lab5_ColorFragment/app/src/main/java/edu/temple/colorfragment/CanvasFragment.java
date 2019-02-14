package edu.temple.colorfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CanvasFragment extends Fragment {
    FrameLayout canvasFragment;
    public CanvasFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canvas, container, false);
        // Get the canvas fragment that will display the color
        canvasFragment = (FrameLayout) view.findViewById(R.id.canvas_fragment);
        return view;
    }
    //Change the background color of the canvas with a new color
    public void changeCanvasColor(String colorValue){
        canvasFragment.setBackgroundColor(Color.parseColor(colorValue));
    }
}
