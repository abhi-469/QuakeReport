package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter {

    public EarthquakeAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list,parent,false);
        }
        Earthquake currentEarthquake= (Earthquake) getItem(position);

        TextView magnitude=(TextView)listItemView.findViewById(R.id.magnitude);
        magnitude.setText(currentEarthquake.getmMagnitude());
        float fMagnitude=Float.parseFloat(currentEarthquake.getmMagnitude());
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();


        int magnitudeColorResourceId;
        int colorValue;
        int magnitudeFloor = (int) Math.floor(fMagnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                colorValue=ContextCompat.getColor(getContext(), magnitudeColorResourceId);
                break;
        }

        magnitudeCircle.setColor(colorValue);


        TextView locationOffset=(TextView)listItemView.findViewById(R.id.location_offset);
        locationOffset.setText(currentEarthquake.getmLocationOffset());

        TextView primaryLocation=(TextView)listItemView.findViewById(R.id.primary_location);
        primaryLocation.setText(currentEarthquake.getmPrimaryLocation());

        TextView date=(TextView)listItemView.findViewById(R.id.date);
        date.setText(currentEarthquake.getmDate());

        TextView time=(TextView)listItemView.findViewById(R.id.time);
        time.setText(currentEarthquake.getmTime());



        return listItemView;
    }
}
