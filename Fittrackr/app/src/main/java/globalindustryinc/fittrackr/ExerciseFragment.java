package globalindustryinc.fittrackr;

/**
 * Created by jccline on 10/5/2014.
 */

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public abstract class ExerciseFragment extends android.support.v4.app.Fragment implements TextView.OnEditorActionListener {

    View rootView;
    EditText exerciseInput;
    ListView exerciseListView;
    CheckBox checkView;
    LinkedList<Exercise> exercises;
    MySQLiteHelper db;
    LinkedList<Exercise> sqlExercises = new LinkedList<Exercise>();
    Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        mHandler = new Handler();
        rootView = inflater.inflate(R.layout.fragment_exercise, container, false);

        // Obtain views from layout
        checkView = (CheckBox) rootView.findViewById(R.id.checkbox);
        exerciseInput = (EditText) rootView.findViewById(R.id.exercise_input);
        exerciseListView = (ListView) rootView.findViewById(R.id.exerciseListView);

        // Initialize and fetch data
       new Thread(new Runnable() {
           public void run() {
               fetchExerciseData();
               mHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       setupLiftingListView();
                   }
               });
           }
       }).start();

        // Setup views for use
        new Thread(new Runnable(){
            @Override
            public void run() {
                setupLiftingInput();
            }
        }).start();

        setHasOptionsMenu(true);

        new Thread(new Runnable(){
            @Override
            public void run() {
                db = new MySQLiteHelper(getActivity());
            }
        }).start();
        return rootView;
    }

    private void fetchExerciseData() {
        exercises = SharedPref.retrieveExercises(getActivity(), getExerciseType());
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
        if(!exerciseName.isEmpty()) {
            Exercise exercise = new Exercise(Exercise.EXERCISE_TYPE.LIFTING, exerciseName);
            exercises.add(exercise);
            exerciseInput.getText().clear();
            ((ExerciseListViewAdapter) ((HeaderViewListAdapter) exerciseListView.getAdapter()).getWrappedAdapter()).notifyDataSetChanged();
            notifySharedPrefOfChangedExercise();
        }else{
            Toast.makeText(getActivity(),"No Exercise Name Given.",Toast.LENGTH_LONG).show();
        }
    }


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
            notifySharedPrefOfChangedExercise();
            notifydbOfChangedExercises();
        }
        return super.onOptionsItemSelected(item);
    }

    private void notifydbOfChangedExercises(){
                db.createExercise(getExerciseType(),sqlExercises);
    }

    private void notifySharedPrefOfChangedExercise(){
        SharedPref.saveExercises(getActivity(),getExerciseType(),exercises);
        Toast.makeText(getActivity(),"Exercises saved.",Toast.LENGTH_LONG).show();
    }

    public class ExerciseListViewAdapter extends BaseAdapter {

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
            return ExerciseFragment.this.getView(position, reusableView, parent, this);
        }

        public TextWatcher getTextWatcher(Exercise view,Exercise.ATTRIBUTES attribute){
            return new TextWatcher(view,attribute);
        }

        public checkBox getCheckBox(Exercise view){
            return new checkBox(view);
        }

    }

    public class checkBox implements View.OnClickListener{
        Exercise exercise;

        public checkBox(Exercise exercise){
            this.exercise = exercise;
        }

        @Override
        public void onClick(View v) {
            if(!(sqlExercises.contains(exercise))) {
                sqlExercises.add(exercise);
            } else {
                sqlExercises.remove(exercise);
            }
        }
    }

    public class TextWatcher implements android.text.TextWatcher{

        Exercise exercise;
        Exercise.ATTRIBUTES attribute;

        public TextWatcher(Exercise exercise,Exercise.ATTRIBUTES attribute){
            this.exercise = exercise;
            this.attribute = attribute;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                int newValue = Integer.parseInt(editable.toString());
                Exercise exercise = this.exercise;
                exercise.setValue(attribute, newValue);

            }catch(NumberFormatException e){

            }
        }
    }

    abstract Exercise.EXERCISE_TYPE getExerciseType();

    abstract ExerciseItemView generateHeader();

    abstract public View getView(int position, View reusableView, ViewGroup parent,
                                 ExerciseListViewAdapter exerciseListViewAdapter);

}
