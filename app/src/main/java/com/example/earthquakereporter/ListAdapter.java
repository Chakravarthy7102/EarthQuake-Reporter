package com.example.earthquakereporter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListAdapter extends ArrayAdapter<DataModel> {

    private DataModel currentDataModel;

    public ListAdapter(@NonNull Context context, ArrayList<DataModel> dataModelArrayList) {
        super(context, 0, dataModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.data_items, parent, false);
        }
        currentDataModel = getItem(position);


        TextView magnitude = view.findViewById(R.id.magnitudeText);
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
       GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentDataModel.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        magnitude.setText(currentDataModel.getMagnitude());

        TextView place = view.findViewById(R.id.placeText);
        place.setText(currentDataModel.getPlace());

        TextView date = view.findViewById(R.id.dateText);
        date.setText(currentDataModel.getDate());

        TextView time = view.findViewById(R.id.timeText);
        Date millie = new Date(Long.parseLong(currentDataModel.getTime()));
        time.setText(formatTime(millie));


        return view;
    }

    public int getMagnitudeColor(String magnitude) {

        int backgroundColor;
        double doubleMag = Double.parseDouble(magnitude);
        int intMag = (int) Math.floor(doubleMag);
        switch (intMag) {
            case 0:
            case 1:
                backgroundColor = R.color.magnitude1;
               // backgroundColor= ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                backgroundColor = R.color.magnitude2;
               // backgroundColor= ContextCompat.getColor(getContext(), R.color.magnitude2);

                break;
            case 3:
                backgroundColor = R.color.magnitude3;
                //backgroundColor=ContextCompat.getColor(getContext(),R.color.magnitude3);
                break;
            case 4:
                backgroundColor = R.color.magnitude4;
                break;
            case 5:
                backgroundColor = R.color.magnitude5;
                break;
            case 6:
                backgroundColor = R.color.magnitude6;
               // backgroundColor= ContextCompat.getColor(getContext(), R.color.magnitude6);

                break;
            case 7:
                backgroundColor = R.color.magnitude7;
                break;
            case 8:
                backgroundColor = R.color.magnitude8;
                break;
            case 9:
                backgroundColor = R.color.magnitude9;
                break;
            case 10:
            default:
                backgroundColor = R.color.magnitude10plus;
        }
        //though we use direct reference to the colors.xml we cannot access them directly we have to use some
        //reference.
        return  ContextCompat.getColor(getContext(),backgroundColor);
    }



    // Return the formatted date string (i.e. "4:30 PM") from a Date object.

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}
