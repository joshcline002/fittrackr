package globalindustryinc.fittrackr;

/**
 * Created by jccline on 10/5/2014.
 */
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import static com.jjoe64.graphview.GraphView.GraphViewData;

public class CardioGraphFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // first init data
        int num = 150;
        GraphViewData[] data = new GraphViewData[num];
        double v=0;
        for (int i=0; i<num; i++) {
            v += 0.2;
            data[i] = new GraphViewData(i, Math.sin(v));
        }
        GraphViewSeries seriesSin = new GraphViewSeries("Cardio3", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(200, 50, 00), 3), data);

        data = new GraphViewData[num];
        v=0;
        for (int i=0; i<num; i++) {
            v += 0.2;
            data[i] = new GraphViewData(i, Math.cos(v));
        }
        GraphViewSeries seriesCos = new GraphViewSeries("Cardio2", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(90, 250, 00), 3), data);

        num = 1000;
        data = new GraphViewData[num];
        v=0;
        for (int i=0; i<num; i++) {
            v += 0.2;
            data[i] = new GraphViewData(i, Math.sin(Math.random()*v));
        }
        GraphViewSeries seriesRnd = new GraphViewSeries("Cardio1", null, data);

/*
 * create graph
 */
        GraphView graphView = new LineGraphView(
                container.getContext()
                , "Cardio Graph"
        );
// add data
        graphView.addSeries(seriesCos);
        graphView.addSeries(seriesSin);
        graphView.addSeries(seriesRnd);
// optional - set view port, start=2, size=10
        graphView.setViewPort(2, 10);
        graphView.setScalable(true);
// optional - legend
        graphView.setShowLegend(true);
        graphView.setLegendAlign(GraphView.LegendAlign.BOTTOM);
        graphView.setLegendWidth(500);
        return graphView;
    }
}