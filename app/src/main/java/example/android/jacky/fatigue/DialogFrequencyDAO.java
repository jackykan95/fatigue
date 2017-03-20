package example.android.jacky.fatigue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Jacky on 06/03/2017.
 */

public class DialogFrequencyDAO {

    private DialogFrequencyDBHelper dbHelper;
    private SQLiteDatabase db;

    public static final String TABLE_NAME_ENERGY_FREQUENCY = "energy_frequency";
    public static final String TABLE_NAME_FATIGUE_FREQUENCY = "fatigue_frequency";

    public static final String COLUMN_NAME_FREQUENCY = "frequency";

    public static final int COLUMN_INDEX_ZERO = 0;

    public DialogFrequencyDAO(Context context) {
        dbHelper = new DialogFrequencyDBHelper(context);
    }


    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void createEnergyFrequency (int frequency){

        ContentValues values = new ContentValues();

        values.put(DialogFrequencyDAO.COLUMN_NAME_FREQUENCY,
                frequency);

        db.insert(DialogFrequencyDAO.TABLE_NAME_ENERGY_FREQUENCY,null,values);
    }

    public void updateEnergyFrequency (int frequency){

        ContentValues values = new ContentValues();

        values.put(DialogFrequencyDAO.COLUMN_NAME_FREQUENCY,
                frequency);

        db.update(DialogFrequencyDAO.TABLE_NAME_ENERGY_FREQUENCY,values,null,null);
    }

    public Integer getEnergyFrequency(){
        Cursor cursor = db.query(
                DialogFrequencyDAO.TABLE_NAME_ENERGY_FREQUENCY,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        int frequency = 0;
        while(!cursor.isAfterLast()){
            frequency = cursor.getInt(
                    DialogFrequencyDAO.COLUMN_INDEX_ZERO);

            cursor.moveToNext();
        }

        return frequency;
    }

    public void createFatigueFrequency (int frequency){

        ContentValues values = new ContentValues();

        values.put(DialogFrequencyDAO.COLUMN_NAME_FREQUENCY,
                frequency);

        db.insert(DialogFrequencyDAO.TABLE_NAME_FATIGUE_FREQUENCY,null,values);
    }

    public void updateFatigueFrequency (int frequency){

        ContentValues values = new ContentValues();

        values.put(DialogFrequencyDAO.COLUMN_NAME_FREQUENCY,
                frequency);

        db.update(DialogFrequencyDAO.TABLE_NAME_FATIGUE_FREQUENCY,values,null,null);
    }

    public Integer getFatigueFrequency(){
        Cursor cursor = db.query(
                DialogFrequencyDAO.TABLE_NAME_FATIGUE_FREQUENCY,
                null,null,null,null,null,null);
        cursor.moveToFirst();
        int frequency = 0;
        while(!cursor.isAfterLast()){
            frequency = cursor.getInt(
                    DialogFrequencyDAO.COLUMN_INDEX_ZERO);

            cursor.moveToNext();
        }

        return frequency;
    }


}
