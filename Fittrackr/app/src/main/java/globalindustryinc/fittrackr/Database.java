package globalindustryinc.fittrackr;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by Ian on 11/1/2014.
 */
public class Database {

    public static String EXERCISE_PREFERENCES = "exercise_preferences";

    public static LinkedList<Exercise> retrieveExercises(Context context, Exercise.EXERCISE_TYPE exerciseType) {
        String jsonString = getSharedPrefs(context).getString(Exercise.getExerciseTypeString(exerciseType),"");
        LinkedList<Exercise> exercises = new LinkedList<Exercise>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject exerciseObject = jsonArray.getJSONObject(i);
                Log.d("exercise",exerciseObject.toString());
                Exercise exercise = new Exercise(exerciseType,exerciseObject.getString("name"));
                switch (exerciseType) {
                    case CARDIO:
                        exercise.setValue(Exercise.ATTRIBUTES.TIME,exerciseObject.getInt("time"));
                        exercise.setValue(Exercise.ATTRIBUTES.DISTANCE,exerciseObject.getInt("distance"));
                        break;
                    case MEASURE:
                        exercise.setValue(Exercise.ATTRIBUTES.MEASUREMENT,exerciseObject.getInt("measurement"));
                        break;
                    case LIFTING:
                        exercise.setValue(Exercise.ATTRIBUTES.REPS,exerciseObject.getInt("reps"));
                        exercise.setValue(Exercise.ATTRIBUTES.SETS,exerciseObject.getInt("sets"));
                        exercise.setValue(Exercise.ATTRIBUTES.WEIGHT,exerciseObject.getInt("weight"));
                        break;
                }
                exercises.add(exercise);
            }
        }catch(JSONException e){ }
        return exercises;
    }

    public static void saveExercises(Context context, Exercise.EXERCISE_TYPE exerciseType, LinkedList<Exercise> exercises) {
        JSONArray jsonArray = new JSONArray();
        // Create the json object representing exercise
        for(Exercise exercise : exercises){
            JSONObject exerciseObject = new JSONObject();
            try {
                switch (exerciseType) {
                    case CARDIO:
                        exerciseObject.put("time", exercise.getAttribute(Exercise.ATTRIBUTES.TIME));
                        exerciseObject.put("distance", exercise.getAttribute(Exercise.ATTRIBUTES.DISTANCE));
                        break;
                    case MEASURE:
                        exerciseObject.put("measurement", exercise.getAttribute(Exercise.ATTRIBUTES.MEASUREMENT));
                        break;
                    case LIFTING:
                        exerciseObject.put("reps", exercise.getAttribute(Exercise.ATTRIBUTES.REPS));
                        exerciseObject.put("sets", exercise.getAttribute(Exercise.ATTRIBUTES.SETS));
                        exerciseObject.put("weight", exercise.getAttribute(Exercise.ATTRIBUTES.WEIGHT));
                        break;
                }
                exerciseObject.put("name",exercise.name);
                Log.d("adding",exerciseObject.toString());
            }catch (JSONException e) { }

            jsonArray.put(exerciseObject);
        }
        // Add json array to the database
        getSharedPrefs(context)
                .edit()
                .putString(Exercise.getExerciseTypeString(exerciseType),jsonArray.toString())
                .commit();
    }

    private static SharedPreferences getSharedPrefs(Context context){
        return context.getSharedPreferences(EXERCISE_PREFERENCES,Context.MODE_PRIVATE);
    }
}
