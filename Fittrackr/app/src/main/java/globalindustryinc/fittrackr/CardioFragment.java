package globalindustryinc.fittrackr;

/**
 * Created by jccline on 10/5/2014.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CardioFragment extends ExerciseFragment{

    @Override
    Exercise.EXERCISE_TYPE getExerciseType() {
        return Exercise.EXERCISE_TYPE.CARDIO;
    }

    @Override
    ExerciseItemView generateHeader() {
        ExerciseItemView header = new ExerciseItemView(exerciseListView.getContext());

        header.exerciseName.setText("Exercise");
        header.time.setText("Time");
        header.distance.setText("Distance");

        header.exerciseName.setTypeface(null, Typeface.BOLD);
        header.time.setTypeface(null, Typeface.BOLD);
        header.distance.setTypeface(null, Typeface.BOLD);

        header.time.setVisibility(View.VISIBLE);
        header.distance.setVisibility(View.VISIBLE);

        return header;
    }

    @Override
    public View getView(int position, View reusableView, ViewGroup parent,ExerciseFragment.ExerciseListViewAdapter adapter) {

        // Reuse of create new ExerciseItemView
        ExerciseItemView itemView;
        itemView = new ExerciseItemView(parent.getContext());

        Exercise exercise = (Exercise) adapter.getItem(position);
        // Set name
        itemView.exerciseName.setText(exercise.name);

        // Fill ExerciseItemView with the appropriate data
        itemView.time.setText(exercise.getAttibuteString(Exercise.ATTRIBUTES.TIME));
        itemView.distance.setText(exercise.getAttibuteString(Exercise.ATTRIBUTES.DISTANCE));

        // Set exercises and listeners for when done editing values, exercise can be edited
        itemView.time.addTextChangedListener(adapter.getTextWatcher(exercise, Exercise.ATTRIBUTES.TIME));
        itemView.distance.addTextChangedListener(adapter.getTextWatcher(exercise, Exercise.ATTRIBUTES.DISTANCE));

        // Set data items visibility to true
        itemView.time.setVisibility(View.VISIBLE);
        itemView.distance.setVisibility(View.VISIBLE);

        return itemView;
    }

}