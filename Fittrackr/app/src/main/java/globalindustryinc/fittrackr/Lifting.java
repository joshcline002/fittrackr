package globalindustryinc.fittrackr;

/**
 * Created by jccline on 10/5/2014.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Lifting extends android.support.v4.app.Fragment implements TextView.OnEditorActionListener {

    View rootView;
    EditText liftingInput;
    ListView liftingListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        rootView = inflater.inflate(R.layout.fragment_lifting, container, false);

        // Obtain views from layout
        liftingInput = (EditText) rootView.findViewById(R.id.lifting_input);
        liftingListView = (ListView) rootView.findViewById(R.id.liftingListView);

        // Setup views for use
        setupLiftingInput();
        setupLiftingListView();

        // Setup handles to view objects here
        // etFoo = (EditText) view.findViewById(R.id.etFoo);
        return rootView;
    }

    private void setupLiftingInput() {
        liftingInput.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(EditorInfo.IME_ACTION_DONE==actionId){
            addExercise(liftingInput.getText().toString());
        }
        return false;
    }

    private void addExercise(String exerciseName) {
        Toast.makeText(liftingInput.getContext(),exerciseName,Toast.LENGTH_LONG);
    }

    /**
     * Sets up the adapter for the listview and adds a header to clarify data in list items.
     */
    private void setupLiftingListView() {
        liftingListView.setAdapter(new LiftingListViewAdapter());
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

    private class LiftingListViewAdapter extends BaseAdapter{



        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View reusableView, ViewGroup parent) {
            // Reuse of create new ExerciseItemView
            ExerciseItemView itemView;
            if(reusableView==null){
                itemView = new ExerciseItemView(parent.getContext());
            }
            else itemView = (ExerciseItemView) reusableView;

            // File ExerciseItemView with the appropriate data
            itemView.exerciseName.setText("Execise Type "+position);
            itemView.reps.setText("30");
            itemView.sets.setText("5");
            itemView.weight.setText("75");

            return itemView;
        }
    }
}
