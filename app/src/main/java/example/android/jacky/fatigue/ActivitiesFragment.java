package example.android.jacky.fatigue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sonymobile.lifelog.LifeLog;

/**
 * Created by Jacky on 11/01/2017.
 */

public class ActivitiesFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_activities, container, false);

        Button btn_daily = (Button) rootView.findViewById(R.id.btn_daily_summary);

        Button btn_weekly = (Button) rootView.findViewById(R.id.btn_weekly_summary);

        TextView txt_daily = (TextView) rootView.findViewById(R.id.txt_daily_summary);

        TextView txt_weekly = (TextView) rootView.findViewById(R.id.txt_weekly_summary);

        txt_daily.setText("A summary of the activities logged today");

        txt_weekly.setText("A summary of the activities logged for the past week");

        btn_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ActivitiesDaily.class);
                startActivity(intent);
            }
        });

        btn_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ActivitiesWeekly.class);
                startActivity(intent);
            }
        });


        return rootView;
    }






}
