package edu.temple.colorfragment;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
class ColorAdapter extends BaseAdapter {
    private Context context;
    private String[] colorValues;
    private String[] colorNames;
    ColorAdapter(Context context, String[] colorValues, String[] colorNames) {
        this.context = context;
        this.colorValues = colorValues;
        this.colorNames = colorNames;
    }
    @Override
    public int getCount() {
        return colorValues.length;
    }
    @Override
    public Object getItem(int i) {
        return colorValues[i];
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(colorNames[i]);
        textView.setBackgroundColor(Color.parseColor(colorValues[i]));
        textView.setHeight(200);
        textView.setTextSize(20);
        textView.setGravity(10);
        textView.setPadding(100, 40, 0, 0);
        return textView;
    }
}
