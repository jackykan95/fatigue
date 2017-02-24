package example.android.jacky.fatigue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Jacky on 11/01/2017.
 */

public class SettingsFragment extends Fragment {

    private NumberPicker energyPicker, fatiguePicker;

    private Button applyButton;

    private FirebaseDatabase database;

    private DatabaseReference fatigueRef , energyRef;

    private MainActivity mainActivity;

    private String TAG = "Settings";

    private boolean displayEnergyStartup = true;

    private boolean displayFatigueStartup = true;

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

        mainActivity = (MainActivity) getActivity();

        database = FirebaseDatabase.getInstance();

        energyRef = database.getReference("energy_prompt_frequency");

        energyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG,"onDataChange for energyRef");

                // Get the value to update the UI
                Integer delay = dataSnapshot.getValue(Integer.class);

                if(delay == null){
                    energyRef.setValue(mainActivity.getEnergyDelay());
                }
                else{
                    mainActivity.setEnergyDelay(delay);
                    energyPicker.setValue(delay/60000);
                }

                if (displayEnergyStartup) {
                    Log.d(TAG, "Display Energy at startup");
                    displayEnergyStartup = false;
                    mainActivity.displayEnergyLevelDialog();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Energy frequency failed, log a message
                Log.w(TAG, "loadEnergy:onCancelled", databaseError.toException());
                // ...
            }
        });

        fatigueRef = database.getReference("fatigue_prompt_frequency");

        fatigueRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG,"onDataChange for fatigueRef");

                // Get the value to update the UI
                Integer delay = dataSnapshot.getValue(Integer.class);

                if(delay == null){
                    fatigueRef.setValue(mainActivity.getFatigueDelay());
                }
                else{
                    mainActivity.setFatigueDelay(delay);
                    fatiguePicker.setValue(delay/60000);
                }

                if (displayFatigueStartup) {
                    Log.d(TAG, "Display Fatigue at startup");
                    displayFatigueStartup = false;
                    mainActivity.displayFatigueDialog();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Fatigue frequency failed, log a message
                Log.w(TAG, "loadFatigue:onCancelled", databaseError.toException());
                // ...
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.setEnergyDelay(energyPicker.getValue()*60000);
                energyRef.setValue(energyPicker.getValue()*60000);
                activity.setFatigueDelay(fatiguePicker.getValue()*60000);
                fatigueRef.setValue(fatiguePicker.getValue()*60000);
            }
        });


        return rootView;
    }



}
