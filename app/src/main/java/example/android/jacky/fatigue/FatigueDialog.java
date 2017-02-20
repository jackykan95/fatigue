package example.android.jacky.fatigue;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jacky on 17/02/2017.
 */

public class FatigueDialog extends DialogFragment {

    public static FatigueDialog newInstance(){
        FatigueDialog fatigueFrag = new FatigueDialog();

        return fatigueFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_fatigue_dialog,container, false);

        Button okButton = (Button) view.findViewById(R.id.ok_button);

        TextView txt_left = (TextView) view.findViewById(R.id.textView);

        TextView txt_right = (TextView) view.findViewById(R.id.textView2);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To-do store the subjective data

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
