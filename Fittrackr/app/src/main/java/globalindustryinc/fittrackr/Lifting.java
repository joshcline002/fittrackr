package globalindustryinc.fittrackr;

/**
 * Created by jccline on 10/5/2014.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Lifting extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_lifting, container, false);
        // Setup handles to view objects here
        // etFoo = (EditText) view.findViewById(R.id.etFoo);
        return view;
    }
}
