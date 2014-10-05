package globalindustryinc.fittrackr;

/**
 * Created by jccline on 10/5/2014.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class Lifting extends android.support.v4.app.Fragment implements TextView.OnEditorActionListener {

    View rootView;
    EditText liftingInput;
    ListView liftingListView;
    LinkedList<Exercise> exercises;
    LinkedList<Exercise> changedExercises = new LinkedList<Exercise>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        rootView = inflater.inflate(R.layout.fragment_lifting, container, false);

        // Obtain views from layout
        liftingInput = (EditText) rootView.findViewById(R.id.lifting_input);
        liftingListView = (ListView) rootView.findViewById(R.id.liftingListView);

        // Initialize and fetch data
        fetchExerciseData();

        // Setup views for use
        setupLiftingInput();
        setupLiftingListView();

        setHasOptionsMenu(true);
        return rootView;
    }

    private void fetchExerciseData() {
        exercises = new LinkedList<Exercise>();
    }

    private void setupLiftingInput() {
        liftingInput.setOnEditorActionListener(this);
        liftingInput.setImeActionLabel("Add",EditorInfo.IME_ACTION_DONE);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(EditorInfo.IME_ACTION_DONE==actionId){
            addExercise(liftingInput.getText().toString());
        }
        return false;
    }

    private void addExercise(String exerciseName) {
        exercises.add(new Exercise(exerciseName,0,0,0));
        liftingInput.getText().clear();
        ((LiftingListViewAdapter)((HeaderViewListAdapter)liftingListView.getAdapter()).getWrappedAdapter()).notifyDataSetChanged();    }

    /**
     * Sets up the adapter for the listview and adds a header to clarify data in list items.
     */
    private void setupLiftingListView() {
        if(exercises!=null) liftingListView.setAdapter(new LiftingListViewAdapter());
        liftingListView.addHeaderView(generateHeader());
    }

    /**
     * Generates header for the listview. By default sets all items to bold
     * @return ExerciseItemView representing items
     */
    private ExerciseItemView generateHeader(){
        ExerciseItemView header = new ExerciseItemView(liftingListView.getContext());

        header.exerciseName.setText("Exercise");
        header.reps.setText("Reps");
        header.sets.setText("Sets");
        header.weight.setText("Weight");

        header.exerciseName.setTypeface(null, Typeface.BOLD);
        header.reps.setTypeface(null, Typeface.BOLD);
        header.sets.setTypeface(null, Typeface.BOLD);
        header.weight.setTypeface(null, Typeface.BOLD);

        return header;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.save==item.getItemId()){
            notifyDatabaseOfChangedExercise();
        }
        return super.onOptionsItemSelected(item);
    }

    private void notifyDatabaseOfChangedExercise(){
        Toast.makeText(getActivity(),"Exercises saved.",Toast.LENGTH_LONG).show();
    }

    private class LiftingListViewAdapter extends BaseAdapter implements TextView.OnEditorActionListener{

        public int EXERCISE_KEY = 11;
        public int ATTRIBUTE_KEY = 22;

        @Override
        public int getCount() {
            return exercises.size();
        }

        @Override
        public Object getItem(int position) {
            return exercises.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View reusableView, ViewGroup parent) {

            // Reuse of create new ExerciseItemView
            ExerciseItemView itemView;
            if(reusableView==null){
                itemView = new ExerciseItemView(parent.getContext());
            }
            else itemView = (ExerciseItemView) reusableView;

            Exercise exercise = (Exercise) getItem(position);

            // File ExerciseItemView with the appropriate data
            itemView.exerciseName.setText(exercise.name);
            itemView.reps.setText(exercise.reps==0 ? "" : exercise.reps+"");
            itemView.sets.setText(exercise.sets==0 ? "" : exercise.sets+"");
            itemView.weight.setText(exercise.weight==0 ? "" : exercise.weight+"");

            // Set exercises and listeners for when done editing values, exercise can be edited
            itemView.reps.setTag(
                    new Pair<Exercise, Exercise.ATTRIBUTES>(exercise, Exercise.ATTRIBUTES.REPS)
            );
            itemView.reps.setOnEditorActionListener(this);
            itemView.sets.setTag(
                    new Pair<Exercise, Exercise.ATTRIBUTES>(exercise, Exercise.ATTRIBUTES.SETS)
            );
            itemView.sets.setOnEditorActionListener(this);
            itemView.weight.setTag(
                    new Pair<Exercise,Exercise.ATTRIBUTES>(exercise, Exercise.ATTRIBUTES.WEIGHT)
            );
            itemView.weight.setOnEditorActionListener(this);

            return itemView;
        }

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if(EditorInfo.IME_ACTION_DONE==actionId){
                int newValue = Integer.parseInt(textView.getText().toString());
                Pair<Exercise,Exercise.ATTRIBUTES> pair =
                        (Pair<Exercise, Exercise.ATTRIBUTES>) textView.getTag();
                Exercise exercise = pair.first;
                exercise.setValue(pair.second, newValue);
                if(!changedExercises.contains(exercise)) changedExercises.add(exercise);
            }
            return false;
        }
    }
}
