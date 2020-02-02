/**

 Purpose/Design rationale:: The purpose of this call is to be able to place more then one or 2 textviews in the listview box dso we have extended the list adapter
 this class also changes the color of the Sys and dia to indicate high or low blood pressure
 Outstanding issues: NO

 */



package com.example.manjum_cardiobook;

import android.content.Context;
import android.graphics.Color;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
/** Source youtube
User: https://www.youtube.com/channel/UCoNZZLhPuuRteu02rh7bzsw
Answer: https://www.youtube.com/watch?v=E6vE8fqQPTE
*/

class BloodPressureListAdapter extends ArrayAdapter<BloodPressure> {
    //______________________________Constants_______________________________________________________
    private static final String TAG = "BloodPresureListAdapter";
    private Range range, range2;
    private Context mContext;
    private int mResource;




    public BloodPressureListAdapter(Context context, int resource, ArrayList<BloodPressure> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //_____________________Initializing_________________________________________________________
        String Date = getItem(position).getDate();
        String Time  = getItem(position).getTime();
        String Systolic = getItem(position).getSystolic();
        String Diastolic = getItem(position).getDiastolic();
        String Heartrate = getItem(position).getHeartrate();
        String Comment = getItem(position).getComment();

        range = new Range(90, 140);
        range2 =new Range(60,  90);

        //_______________Create the blood presure object with the information_______________________
        BloodPressure person = new BloodPressure(Date, Time, Systolic, Diastolic, Heartrate, Comment);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);



        TextView tvDate  = (TextView) convertView.findViewById(R.id.LTV_date);
        TextView tvTime = (TextView) convertView.findViewById(R.id.LTV_time);
        TextView tvSystolic = (TextView) convertView.findViewById(R.id.LTV_systolic);
        TextView tvDiastolic = (TextView) convertView.findViewById(R.id.LTV_diastolic);
        TextView tvHeartrate = (TextView) convertView.findViewById(R.id.LTV_heartrate);
        TextView tvComment = (TextView) convertView.findViewById(R.id.LTV_comment);


        tvDate.setText(Date);
        tvTime .setText(Time);
        tvSystolic.setText(Systolic);
        tvDiastolic.setText(Diastolic);
        tvHeartrate.setText(Heartrate);
        tvComment.setText(Comment);


        int Sys = Integer.valueOf(person.getSystolic());
        int Dia = Integer.valueOf(person.getDiastolic());

        //_____________Indicating high or low blood pressure________________________________________
        if (person.getSystolic().length() > 0 && person.getDiastolic().length() > 0) {



            if (range.contains(Sys)){
                    if (range2.contains(Dia)) {
                        tvSystolic.setTextColor(Color.GREEN);
                        tvDiastolic.setTextColor(Color.GREEN);
                    }
            }

            if ((Sys < 90) && (Dia < 60)) {
                tvSystolic.setTextColor(Color.YELLOW);
                tvDiastolic.setTextColor(Color.YELLOW);
            }
            if (Sys > 140 && Dia > 90) {
                tvSystolic.setTextColor(Color.RED);
                tvDiastolic.setTextColor(Color.RED);

            }
        }


        return convertView;
    }




}
