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

public class MeasureFragment extends ExerciseFragment {
    @Override
    Exercise.EXERCISE_TYPE getExerciseType() {
        return Exercise.EXERCISE_TYPE.MEASURE;
    }

    @Override
    ExerciseItemView generateHeader() {
        ExerciseItemView header = new ExerciseItemView(exerciseListView.getContext());

        header.exerciseName.setText("Exercise");
        header.measurement.setText("Measurement");

        header.exerciseName.setTypeface(null, Typeface.BOLD);
        header.measurement.setTypeface(null, Typeface.BOLD);

        header.measurement.setVisibility(View.VISIBLE);

        return header;
    }

    @Override
    public View getView(int position, View reusableView, ViewGroup parent,ExerciseFragment.ExerciseListViewAdapter adapter) {

        // Reuse of create new ExerciseItemView
        ExerciseItemView itemView;
        if(reusableView==null) itemView = new ExerciseItemView(parent.getContext());
        else itemView = (ExerciseItemView) reusableView;

        Exercise exercise = (Exercise) adapter.getItem(position);
        // Set name
        itemView.exerciseName.setText(exercise.name);

        // Fill ExerciseItemView with the appropriate data
        itemView.measurement.setText(exercise.getAttibuteString(Exercise.ATTRIBUTES.TIME));

        // Set exercises and listeners for when done editing values, exercise can be edited
        itemView.measurement.setTag(
                new Pair<Exercise, Exercise.ATTRIBUTES>(exercise, Exercise.ATTRIBUTES.REPS)
        );
        itemView.measurement.setOnEditorActionListener(adapter);

        // Set data items visibility to true
        itemView.measurement.setVisibility(View.VISIBLE);

        return itemView;
    }
}
