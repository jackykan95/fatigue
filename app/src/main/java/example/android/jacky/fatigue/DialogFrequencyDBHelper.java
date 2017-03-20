package example.android.jacky.fatigue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jacky on 06/03/2017.
 */

public class DialogFrequencyDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fatigue.db";
    private static final String INTEGER_TYPE = " INTEGER";

    private static final String CREATE_ENERGY_ENTRIES =
            "CREATE TABLE " + DialogFrequencyDAO.TABLE_NAME_ENERGY_FREQUENCY + " (" +
                    DialogFrequencyDAO.COLUMN_NAME_FREQUENCY + INTEGER_TYPE + ")";

    private static final String CREATE_FATIGUE_ENTRIES =
            "CREATE TABLE " + DialogFrequencyDAO.TABLE_NAME_FATIGUE_FREQUENCY + " (" +
                    DialogFrequencyDAO.COLUMN_NAME_FREQUENCY + INTEGER_TYPE + ")";

    private static final String DELETE_ENERGY_ENTRIES = "DROP TABLE IF EXISTS " +
            DialogFrequencyDAO.TABLE_NAME_ENERGY_FREQUENCY;

    private static final String DELETE_FATIGUE_ENTRIES = "DROP TABLE IF EXISTS " +
            DialogFrequencyDAO.TABLE_NAME_FATIGUE_FREQUENCY;

    public DialogFrequencyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENERGY_ENTRIES);
        db.execSQL(CREATE_FATIGUE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENERGY_ENTRIES);
        db.execSQL(DELETE_FATIGUE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
