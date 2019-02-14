package edu.temple.colorfragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
public class PaletteFragment extends Fragment {
    private OnFragmentInteractionListener listener;
    public PaletteFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_palette, container, false);
        //Get the grid view for the menu
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        //Get the context
        Context context = getActivity();
        //Get the resources from the context
        Resources resources = context.getResources();
        //Store the color values and names in strings
        String[] colorValues= resources.getStringArray(R.array.colorValues);
        String[] colorNames = resources.getStringArray(R.array.colorNames);
        //Create the color adapter
        ColorAdapter colorAdapter = new ColorAdapter(context, colorValues, colorNames);
        //Link adapter to gridView
        gridView.setAdapter(colorAdapter);
        //Change the color of the canvas to the corresponding color
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.displayNewColor(adapterView.getItemAtPosition(i).toString());
            }

        });
        return view;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    //Interface allow interaction in this fragment to be communicated
    // to the activity and other fragments using the activity
    interface OnFragmentInteractionListener {
        void displayNewColor(String colorValue);
    }
}