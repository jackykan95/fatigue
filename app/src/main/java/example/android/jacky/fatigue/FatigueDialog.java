package example.android.jacky.fatigue;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacky on 17/02/2017.
 */

public class FatigueDialog extends DialogFragment {

    private TextView txt_progress;

    private FirebaseDatabase database;

    private DatabaseReference fatigueDialogRef;

    public static FatigueDialog newInstance(){
        FatigueDialog fatigueFrag = new FatigueDialog();

        return fatigueFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_fatigue_dialog,container, false);

        Button okButton = (Button) view.findViewById(R.id.ok_button);

        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.fatigue_dialog_seekbar);

        txt_progress = (TextView) view.findViewById(R.id.txt_progress);

        database = FirebaseDatabase.getInstance();

        fatigueDialogRef = database.getReference("fatigueDialogResponse");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txt_progress.setText("You have " + progress*10 + "% concentration");
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

                Map <String,String> responseMap = new HashMap<String, String>();

                responseMap.put("Concentration",seekBar.getProgress()*10+"%");

                DatabaseReference fatigueResponseRef = fatigueDialogRef.child(Long.toString(System.currentTimeMillis()));

                fatigueResponseRef.setValue(responseMap);

                getDialog().dismiss();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.displayFatigueDialog();

            }
        });


        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Fatigue");

        return dialog;
    }
}
