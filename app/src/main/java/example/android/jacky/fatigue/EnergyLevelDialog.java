package example.android.jacky.fatigue;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacky on 13/01/2017.
 */

public class EnergyLevelDialog extends DialogFragment {

    private TextView txt_progress;

    private FirebaseDatabase database;

    private DatabaseReference energyDialogRef;

    private SeekBar seekBar;

    public static EnergyLevelDialog newInstance(){
        EnergyLevelDialog energyFrag = new EnergyLevelDialog();

        return energyFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.energy_level_dialog,container, false);

        Button okButton = (Button) view.findViewById(R.id.ok_button);

        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        image.setImageResource(R.mipmap.tired);

        ImageView image2 = (ImageView) view.findViewById(R.id.imageView2);
        image2.setImageResource(R.mipmap.energetic);

        seekBar = (SeekBar) view.findViewById(R.id.energy_level_dialog_seekbar);

        txt_progress = (TextView) view.findViewById(R.id.txt_progress);

        database = FirebaseDatabase.getInstance();

        energyDialogRef = database.getReference("energyDialogResponse");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txt_progress.setText("You have " + progress*10 + "% energy");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Store the subjective data

                Map<String,String> responseMap = new HashMap<String, String>();

                responseMap.put("Energy Level",seekBar.getProgress()*10+"%");

                DatabaseReference energyResponseRef = energyDialogRef.child(Long.toString(System.currentTimeMillis()));

                energyResponseRef.setValue(responseMap);

                getDialog().dismiss();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.displayEnergyLevelDialog();

            }
        });


        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Energy Level");

        return dialog;
    }


}
