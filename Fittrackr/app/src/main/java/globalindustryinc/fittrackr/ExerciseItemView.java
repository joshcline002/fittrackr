package globalindustryinc.fittrackr;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ian on 10/5/2014.
 */
public class ExerciseItemView extends LinearLayout{

    View rootView;
    public TextView exerciseName;
    public TextView reps;
    public TextView sets;
    public TextView weight;
    public TextView time;
    public TextView distance;
    public TextView measurement;
    public TextView save;
    public TextView checkbox;

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
        save = (TextView) findViewById(R.id.save);
        checkbox = (TextView) findViewById(R.id.checkbox);
        reps = (TextView) findViewById(R.id.repsTextView);
        sets = (TextView) findViewById(R.id.setsTextView);
        weight = (TextView) findViewById(R.id.weightTextView);
        time = (TextView) findViewById(R.id.timeTextView);
        distance = (TextView) findViewById(R.id.distanceTextView);
        measurement = (TextView) findViewById(R.id.measurementTextView);
    }

}
