package example.android.jacky.fatigue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jacky on 11/01/2017.
 */

public class SettingsFragment extends Fragment {

    private NumberPicker energyPicker, fatiguePicker;

    private Button applyButton;

    private FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView txtEnergy = (TextView) rootView.findViewById(R.id.txt_energy_level);

        TextView txtFatigue = (TextView) rootView.findViewById(R.id.txt_mental_fatigue);

        applyButton = (Button) rootView.findViewById(R.id.apply_button);

        applyButton.setEnabled(false);

        txtEnergy.setText("Change the frequency of the energy level prompt appearing (minutes):");

        txtFatigue.setText("Change the frequency of the mental fatigue prompt appearing (minutes):");

        energyPicker = (NumberPicker) rootView.findViewById(R.id.energy_level_frequency_picker);

        energyPicker.setMinValue(1);
        energyPicker.setMaxValue(180);
        energyPicker.setWrapSelectorWheel(true);

        energyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
            applyButton.setEnabled(true);
            }
        });

        fatiguePicker = (NumberPicker) rootView.findViewById(R.id.mental_fatigue_frequency_picker);

        fatiguePicker.setMinValue(1);
        fatiguePicker.setMaxValue(180);
        fatiguePicker.setWrapSelectorWheel(true);

        fatiguePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                applyButton.setEnabled(true);
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.setEnergyDelay(energyPicker.getValue()*60000);
                activity.setFatigueDelay(fatiguePicker.getValue()*60000);
            }
        });

        database = FirebaseDatabase.getInstance();


        return rootView;
    }



}
