package com.example.manjum_cardiobook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class BloodPressureListAdapter extends ArrayAdapter<BloodPressure> {

    private static final String TAG = "BloodPresureListAdapter";

    private Context mContext;
    private int mResource;

    public BloodPressureListAdapter(Context context, int resource, ArrayList<BloodPressure> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String Date = getItem(position).getDate();
        String Time  = getItem(position).getTime();
        String Systolic = getItem(position).getSystolic();
        String Diastolic = getItem(position).getDiastolic();
        String Heartrate = getItem(position).getHeartrate();
        String Comment = getItem(position).getComment();

        //Create the bloodpresure object with the information
        BloodPressure person = new BloodPressure(Date, Time, Systolic, Diastolic, Heartrate, Comment);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


        TextView tvDate  = (TextView) convertView.findViewById(R.id.LTV_date);
        TextView tvTime = (TextView) convertView.findViewById(R.id.LTV_time);
        TextView tvSystolic = (TextView) convertView.findViewById(R.id.LTV_systolic);
        TextView tvDiastolic = (TextView) convertView.findViewById(R.id.LTV_diastolic);
        TextView tvHeartrate = (TextView) convertView.findViewById(R.id.LTV_heartrate);
        TextView tvComment = (TextView) convertView.findViewById(R.id.LTV_comment);
        TextView tvInfo = (TextView)  convertView.findViewById(R,id)
        tvDate.setText(Date);
        tvTime .setText(Time);
        tvSystolic.setText(Systolic);
        tvDiastolic.setText(Diastolic);
        tvHeartrate.setText(Heartrate);
        tvComment.setText(Comment);

        return convertView;
    }






}
