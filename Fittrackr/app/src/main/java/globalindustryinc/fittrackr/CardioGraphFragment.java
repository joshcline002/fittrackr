package globalindustryinc.fittrackr;

/**
 * Created by jccline on 10/5/2014.
 */
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.util.LinkedList;

import static com.jjoe64.graphview.GraphView.GraphViewData;

public class CardioGraphFragment extends Fragment {
    MySQLiteHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // first init data
        db = new MySQLiteHelper(getActivity());
        LinkedList<Exercise> exercises;
        int f = 0;
        int max =0;
        int min =100;
        GraphView graphView = new LineGraphView(
                container.getContext()
                , "Cardio Graph"
        );
        GraphViewData[] data;

        exercises = Database.retrieveExercises(getActivity(), Exercise.EXERCISE_TYPE.CARDIO);

        int size = exercises.size();
        String[] names = new String[size];
        for(Exercise exercise : exercises) {
            names[f] = exercise.name;
            f++;
        }
        for (f =0; f<size; f++) {
            double[] mph = db.getMPHCardio(names[f]);
            for (int i = 0; i < mph.length; i++) {
                if (max < mph[i]){
                    max = (int)mph[i];
                }
                if (min > mph[i]){
                    min = (int)mph[i];
                }
            }

            data = new GraphViewData[mph.length];

            for (int i = 0; i < mph.length; i++) {
                data[i] = new GraphViewData(i+1, mph[i]);
            }
            GraphViewSeries lifting = new GraphViewSeries(names[f], new GraphViewSeries.GraphViewSeriesStyle(Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)), size), data);
            graphView.addSeries(lifting);
        }

// optional - set exercise port, start=2, size=10

        graphView.setViewPort(1, 10);
        graphView.setScalable(true);
// optional - legend
        graphView.setShowLegend(true);
        graphView.setLegendAlign(GraphView.LegendAlign.BOTTOM);
        graphView.setManualYAxisBounds(max, 0);
        graphView.setLegendWidth(250);
        return graphView;
    }
}
