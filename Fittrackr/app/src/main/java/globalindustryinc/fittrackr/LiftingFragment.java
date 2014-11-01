package globalindustryinc.fittrackr;

import android.graphics.Typeface;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by jccline on 10/5/2014.
 */

public class LiftingFragment extends ExerciseFragment{

    @Override
    Exercise.EXERCISE_TYPE getExerciseType() {
        return Exercise.EXERCISE_TYPE.LIFTING;
    }

    @Override
    ExerciseItemView generateHeader() {
        ExerciseItemView header = new ExerciseItemView(exerciseListView.getContext());

        header.exerciseName.setText("Exercise");
        header.reps.setText("Reps");
        header.sets.setText("Sets");
        header.weight.setText("Weight");

        header.exerciseName.setTypeface(null, Typeface.BOLD);
        header.reps.setTypeface(null, Typeface.BOLD);
        header.sets.setTypeface(null, Typeface.BOLD);
        header.weight.setTypeface(null, Typeface.BOLD);

        header.reps.setVisibility(View.VISIBLE);
        header.sets.setVisibility(View.VISIBLE);
        header.weight.setVisibility(View.VISIBLE);

        return header;
    }

    @Override
    public View getView(int position, View reusableView, ViewGroup parent,ExerciseListViewAdapter adapter) {

        // Reuse of create new ExerciseItemView
        ExerciseItemView itemView;
        itemView = new ExerciseItemView(parent.getContext());

        Exercise exercise = (Exercise) adapter.getItem(position);
        // Set name
        itemView.exerciseName.setText(exercise.name);

        // Fill ExerciseItemView with the appropriate data
        itemView.reps.setText(exercise.getAttibuteString(Exercise.ATTRIBUTES.REPS));
        itemView.sets.setText(exercise.getAttibuteString(Exercise.ATTRIBUTES.SETS));
        itemView.weight.setText(exercise.getAttibuteString(Exercise.ATTRIBUTES.WEIGHT));

        // Set exercises and listeners for when done editing values, exercise can be edited
        itemView.reps.addTextChangedListener(adapter.getTextWatcher(exercise, Exercise.ATTRIBUTES.REPS));
        itemView.sets.addTextChangedListener(adapter.getTextWatcher(exercise, Exercise.ATTRIBUTES.SETS));
        itemView.weight.addTextChangedListener(adapter.getTextWatcher(exercise, Exercise.ATTRIBUTES.WEIGHT));

        // Set data items visibility to true
        itemView.reps.setVisibility(View.VISIBLE);
        itemView.sets.setVisibility(View.VISIBLE);
        itemView.weight.setVisibility(View.VISIBLE);

        return itemView;
    }
}
