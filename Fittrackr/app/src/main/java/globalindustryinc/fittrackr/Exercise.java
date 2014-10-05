package globalindustryinc.fittrackr;

import java.util.HashMap;

/**
 * Created by Ian on 10/5/2014.
 */
public class Exercise {

    public static enum EXERCISE_TYPE {
        LIFTING, CARDIO, MEASURE;
    }

    public static enum ATTRIBUTES {
        NAME, REPS, SETS, WEIGHT, TIME, DISTANCE, MEASUREMENT;
    }

    String name;
    EXERCISE_TYPE type;
    HashMap<ATTRIBUTES,Integer> attributes;

    public Exercise(EXERCISE_TYPE type,String name){
        attributes = new HashMap<ATTRIBUTES, Integer>();
        this.type = type;
        this.name = name;
    }

    public void setValue(ATTRIBUTES attribute,int value){
        attributes.put(attribute,value);
    }

    public int getAttribute(ATTRIBUTES attribute){
        return attributes.containsKey(attribute) ? attributes.get(attribute) : 0;
    }

    public String getAttibuteString(ATTRIBUTES attribute){
        int attr = getAttribute(attribute);
        return attr==0 ? "" : attr+"";
    }

}
