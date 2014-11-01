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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class ExerciseFragment extends android.support.v4.app.Fragment implements TextView.OnEditorActionListener {

    View rootView;
    EditText exerciseInput;
    ListView exerciseListView;
    LinkedList<Exercise> exercises;
    LinkedList<Exercise> changedExercises = new LinkedList<Exercise>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        rootView = inflater.inflate(R.layout.fragment_lifting, container, false);

        // Obtain views from layout
        exerciseInput = (EditText) rootView.findViewById(R.id.exercise_input);
        exerciseListView = (ListView) rootView.findViewById(R.id.exerciseListView);

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
        exerciseInput.setOnEditorActionListener(this);
        exerciseInput.setImeActionLabel("Add", EditorInfo.IME_ACTION_DONE);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(EditorInfo.IME_ACTION_DONE==actionId){
            addExercise(exerciseInput.getText().toString());
        }
        return false;
    }

    private void addExercise(String exerciseName) {
        Exercise exercise = new Exercise(Exercise.EXERCISE_TYPE.LIFTING,exerciseName);
        exercises.add(exercise);
        exerciseInput.getText().clear();
        ((ExerciseListViewAdapter)((HeaderViewListAdapter) exerciseListView.getAdapter()).getWrappedAdapter()).notifyDataSetChanged();    }

    /**
     * Sets up the adapter for the listview and adds a header to clarify data in list items.
     */
    private void setupLiftingListView() {
        if(exercises!=null) exerciseListView.setAdapter(new ExerciseListViewAdapter());
        exerciseListView.addHeaderView(generateHeader());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
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

    public class ExerciseListViewAdapter extends BaseAdapter implements TextView.OnEditorActionListener{

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
            return ExerciseFragment.this.getView(position,reusableView,parent,this);
        }

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if(EditorInfo.IME_ACTION_DONE==actionId){
                try {
                    int newValue = Integer.parseInt(textView.getText().toString());
                    Pair<Exercise, Exercise.ATTRIBUTES> pair =
                            (Pair<Exercise, Exercise.ATTRIBUTES>) textView.getTag();
                    Exercise exercise = pair.first;
                    exercise.setValue(pair.second, newValue);
                    if (!changedExercises.contains(exercise)) changedExercises.add(exercise);
                }catch(NumberFormatException e){

                }
            }
            return false;
        }
    }

    abstract Exercise.EXERCISE_TYPE getExerciseType();

    abstract ExerciseItemView generateHeader();

    abstract public View getView(int position, View reusableView, ViewGroup parent,
                                 ExerciseListViewAdapter exerciseListViewAdapter);

}
