package example.android.jacky.fatigue;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Jacky on 13/01/2017.
 */

public class EnergyLevelDialog extends DialogFragment {

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

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To-do store the subjective data

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
