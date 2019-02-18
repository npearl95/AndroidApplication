/*
Programming: Chau (Pearl) Nguyen
Lab3: Spinner and Custom Adapter
 */
package edu.temple.palettespinner;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.graphics.Color;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String color[];
    public CustomAdapter(Context context, String[] color){
        this.color=color;
        this.context=context;
    }
    @Override
    public int getCount() {
        return color.length;
    }
    @Override
    public Object getItem(int i){
        return color[i];
    }
    @Override
    public long getItemId(int i){
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        TextView textView = new TextView(context);
        textView.setTextSize(40);
        textView.setText(color[i]);
        if(i>0)
            textView.setBackgroundColor(Color.parseColor(color[i]));

        return textView;
    }
}
