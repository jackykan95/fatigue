package example.android.jacky.fatigue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jacky on 11/01/2017.
 */

public class FatigueFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fatigue, container, false);

        Button btn_energy = (Button) rootView.findViewById(R.id.btn_energy_summary);

        Button btn_fatigue = (Button) rootView.findViewById(R.id.btn_fatigue_summary);

        TextView txt_energy = (TextView) rootView.findViewById(R.id.txt_energy_summary);

        TextView txt_fatigue = (TextView) rootView.findViewById(R.id.txt_fatigue_summary);

        txt_energy.setText("A summary of your energy level during the day");

        txt_fatigue.setText("A summary of your mental fatigue during the day");

        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ActivitiesDaily.class);
                startActivity(intent);
            }
        });

        btn_fatigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ActivitiesWeekly.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
