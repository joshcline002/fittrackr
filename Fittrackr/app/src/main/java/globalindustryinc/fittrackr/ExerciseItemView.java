package globalindustryinc.fittrackr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ian on 10/5/2014.
 */
public class ExerciseItemView extends LinearLayout{

    View rootView;
    public TextView exerciseName;
    public TextView reps;
    public TextView sets;
    public TextView weight;

    public ExerciseItemView(Context context){
        super(context);
        sharedConstructor();
    }

    public ExerciseItemView(Context context,AttributeSet attrs){
        super(context,attrs);
        sharedConstructor();
    }

    private void sharedConstructor(){
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_item,this,true);
        exerciseName = (TextView) findViewById(R.id.exerciseNameTextView);
        reps = (TextView) findViewById(R.id.repsTextView);
        sets = (TextView) findViewById(R.id.setsTextView);
        weight = (TextView) findViewById(R.id.weightTextView);
    }

}
