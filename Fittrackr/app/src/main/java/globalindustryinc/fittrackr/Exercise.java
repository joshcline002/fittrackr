package globalindustryinc.fittrackr;

/**
 * Created by Ian on 10/5/2014.
 */
public class Exercise {

    public static enum ATTRIBUTES{
        NAME, REPS, SETS, WEIGHT;
    }

    String name;
    int reps;
    int sets;
    int weight;

    public Exercise(String name, int reps, int sets, int weight){
        this.name = name;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
    }

    public void setValue(ATTRIBUTES attribute,int value){
        switch (attribute){
            case REPS:
                reps = value;
                break;
            case SETS:
                sets = value;
                break;
            case WEIGHT:
                weight = value;
                break;
        }
    }

}
