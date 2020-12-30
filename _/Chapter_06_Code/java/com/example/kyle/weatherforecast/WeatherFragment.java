package com.example.kyle.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherFragment extends Fragment {
    private static final String DEBUG_TAG = "tag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        Bundle args = getArguments();
        int i = args.getInt("day");

        TextView textOutlook = ((TextView) rootView.findViewById(R.id.text_outlook));
        ImageView symbolView = ((ImageView) rootView.findViewById(R.id.image_symbol));
        TextView tempsView = ((TextView) rootView.findViewById(R.id.text_temp));
        TextView windView = ((TextView) rootView.findViewById(R.id.text_min));
        TextView realFeelView = ((TextView) rootView.findViewById(R.id.text_real_feel));

        textOutlook.setText(WeatherData.outlookArray[i]);
        symbolView.setImageResource(WeatherData.symbolArray[i]);
        tempsView.setText(WeatherData.tempsArray[i] + "°c");
        windView.setText(("Min " + WeatherData.minArray[i] + "°c"));
        realFeelView.setText("Real feel " + WeatherData.realFeelArray[i] + "°c");

        return rootView;
    }
}
