public class Sport extends Activity{

    // constructor
    public Sport(String sportId,String nameOfSport,int calorieCount) {
        super(sportId,nameOfSport,calorieCount);
    }


    /*Precondition: given coefficient is in minutes Postcondition: return burned calorie in abs value, with respect to calorieCount of sport */

    // CalorieCount is calculated for 60 minutes
    // casting to int won't result in data loss in the project
    @Override
    public int calcCalorie(double coefficient) {
        return (int)(this.CALORIE_COUNT*coefficient / 60);
    }
}