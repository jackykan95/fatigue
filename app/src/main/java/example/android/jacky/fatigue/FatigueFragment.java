package example.android.jacky.fatigue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by Jacky on 11/01/2017.
 */

public class FatigueFragment extends Fragment {

    private static int zeroPercent, tenPercent, twentyPercent, thirtyPercent, fortyPercent, fiftyPercent, sixtyPercent,
            seventyPercent, eightyPercent, ninetyPercent, hundredPercent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fatigue, container, false);

        Button btn_daily_energy = (Button) rootView.findViewById(R.id.btn_daily_energy_summary);

        Button btn_weekly_energy = (Button) rootView.findViewById(R.id.btn_weekly_energy_summary);

        Button btn_daily_fatigue = (Button) rootView.findViewById(R.id.btn_daily_fatigue_summary);

        Button btn_weekly_fatigue = (Button) rootView.findViewById(R.id.btn_weekly_fatigue_summary);

        TextView txt_daily_energy = (TextView) rootView.findViewById(R.id.txt_daily_energy_summary);

        TextView txt_weekly_energy = (TextView) rootView.findViewById(R.id.txt_weekly_energy_summary);

        TextView txt_daily_fatigue = (TextView) rootView.findViewById(R.id.txt_daily_fatigue_summary);

        TextView txt_weekly_fatigue = (TextView) rootView.findViewById(R.id.txt_weekly_fatigue_summary);

        txt_daily_energy.setText("A daily summary of energy level");

        txt_weekly_energy.setText("A weekly summary of energy level");

        txt_daily_fatigue.setText("A daily summary of mental fatigue");

        txt_weekly_fatigue.setText("A weekly summary of mental fatigue");

        btn_daily_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EnergyLevelDaily.class);
                startActivity(intent);
            }
        });

        btn_weekly_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EnergyLevelWeekly.class);
                startActivity(intent);
            }
        });

        btn_daily_fatigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MentalFatigueDaily.class);
                startActivity(intent);
            }
        });

        btn_weekly_fatigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MentalFatigueWeekly.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public static int countResponse(Map<String,String> responseMap){

        resetCounters();

        for(String s: responseMap.values()){

            if (s.equalsIgnoreCase("0%")){
                zeroPercent++;
            }
            else if (s.equalsIgnoreCase("10%")){
                tenPercent++;
            }
            else if (s.equalsIgnoreCase("20%")){
                twentyPercent++;
            }
            else if (s.equalsIgnoreCase("30%")){
                thirtyPercent++;
            }
            else if (s.equalsIgnoreCase("40%")){
                fortyPercent++;
            }
            else if (s.equalsIgnoreCase("50%")){
                fiftyPercent++;
            }
            else if (s.equalsIgnoreCase("60%")){
                sixtyPercent++;
            }
            else if (s.equalsIgnoreCase("70%")){
                seventyPercent++;
            }
            else if (s.equalsIgnoreCase("80%")){
                eightyPercent++;
            }
            else if (s.equalsIgnoreCase("90%")){
                ninetyPercent++;
            }
            else if (s.equalsIgnoreCase("100%")){
                hundredPercent++;
            }
        }

        int totalResponse = zeroPercent + tenPercent + twentyPercent + thirtyPercent + fortyPercent + fiftyPercent +sixtyPercent +
                seventyPercent + eightyPercent + ninetyPercent + hundredPercent;

        return totalResponse;
    }

    public static int getCounter(int num){

        switch(num) {
            case 0:
                return zeroPercent;
            case 1:
                return tenPercent;
            case 2:
                return twentyPercent;
            case 3:
                return thirtyPercent;
            case 4:
                return fortyPercent;
            case 5:
                return fiftyPercent;
            case 6:
                return sixtyPercent;
            case 7:
                return seventyPercent;
            case 8:
                return eightyPercent;
            case 9:
                return ninetyPercent;
            case 10:
                return hundredPercent;
        }

        // return an arbitrary number for any invalid num
        return -1;
    }

    public static void resetCounters(){
        zeroPercent = 0;
        tenPercent = 0;
        twentyPercent = 0;
        thirtyPercent = 0;
        fortyPercent = 0;
        fiftyPercent = 0;
        sixtyPercent = 0;
        seventyPercent = 0;
        eightyPercent = 0;
        ninetyPercent = 0;
        hundredPercent = 0;
    }

}
