package globalindustryinc.fittrackr;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jccline on 11/6/2014.
 */


public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_LIFTING = "lifting";
    public static final String TABLE_CARDIO = "cardio";
    public static final String TABLE_MEASURE = "measure";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EXERCISE = "exercise";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DISTANCE = "distance";
    public static final String COLUMN_MEASUREMENT= "measurement";

    private static final String DATABASE_NAME = "fittrackr.db";
    private static final int DATABASE_VERSION = 1;

    // Database Creation sql statement
    private static final String CREATE_TABLE_LIFTING = "CREATE TABLE "
            + TABLE_LIFTING + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EXERCISE + " TEXT,"
            + COLUMN_REPS + " INTEGER,"
            + COLUMN_SETS + " INTEGER,"
            + COLUMN_WEIGHT + " INTEGER)";

    private static final String CREATE_TABLE_CARDIO =  "CREATE TABLE "
            + TABLE_CARDIO + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EXERCISE + " TEXT,"
            + COLUMN_TIME + " INTEGER,"
            + COLUMN_DISTANCE + " INTEGER)";

    private static final String CREATE_TABLE_MEASUREMENT = "CREATE TABLE "
            + TABLE_MEASURE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EXERCISE + " TEXT,"
            + COLUMN_MEASUREMENT + " INTEGER)";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_LIFTING);
        database.execSQL(CREATE_TABLE_CARDIO);
        database.execSQL(CREATE_TABLE_MEASUREMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIFTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASURE);

        // create new tables
        onCreate(db);
    }

    public void createExercise(Exercise.EXERCISE_TYPE exerciseType, LinkedList<Exercise> exercises){

        SQLiteDatabase db = this.getWritableDatabase();
        for (Exercise exercise : exercises) {
            ContentValues values = new ContentValues();

            if(exerciseType == Exercise.EXERCISE_TYPE.LIFTING) {
                values.put(COLUMN_EXERCISE, exercise.name);
                values.put(COLUMN_REPS, exercise.getAttribute(Exercise.ATTRIBUTES.REPS));
                values.put(COLUMN_SETS, exercise.getAttribute(Exercise.ATTRIBUTES.SETS));
                values.put(COLUMN_WEIGHT, exercise.getAttribute(Exercise.ATTRIBUTES.WEIGHT));

                db.insert(TABLE_LIFTING, null, values);

            } else if (exerciseType == Exercise.EXERCISE_TYPE.CARDIO){
                values.put(COLUMN_EXERCISE, exercise.name);
                values.put(COLUMN_DISTANCE, exercise.getAttribute(Exercise.ATTRIBUTES.DISTANCE));
                values.put(COLUMN_TIME, exercise.getAttribute(Exercise.ATTRIBUTES.TIME));

                db.insert(TABLE_CARDIO, null, values);

            } else if (exerciseType == Exercise.EXERCISE_TYPE.MEASURE){
                values.put(COLUMN_EXERCISE, exercise.name);
                values.put(COLUMN_MEASUREMENT, exercise.getAttribute(Exercise.ATTRIBUTES.MEASUREMENT));

                db.insert(TABLE_MEASURE, null, values);

            }
        }
    }

    public int[] getWeightLifting(String name){
        String selectQuery = "SELECT  " + COLUMN_WEIGHT + " FROM " + TABLE_LIFTING + " WHERE EXERCISE = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        int[] weight = new int[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                weight[i] = c.getInt(c.getColumnIndex(COLUMN_WEIGHT));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return weight;
    }

    public int[] getAllMeasure(String name){
        String selectQuery = "SELECT  " + COLUMN_MEASUREMENT + " FROM " + TABLE_MEASURE + " WHERE EXERCISE = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        int[] measure = new int[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                measure[i] = c.getInt(c.getColumnIndex(COLUMN_MEASUREMENT));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return measure;
    }

    public double[] getMPHCardio(String name){
        String selectQuery = "SELECT " + COLUMN_DISTANCE + ", " + COLUMN_TIME + " FROM " + TABLE_CARDIO + " WHERE EXERCISE = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] mph = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                mph[i] = (double)(c.getInt(c.getColumnIndex(COLUMN_DISTANCE)))/(((double)(c.getInt(c.getColumnIndex(COLUMN_TIME))))/60.0);
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return mph;
    }


}
