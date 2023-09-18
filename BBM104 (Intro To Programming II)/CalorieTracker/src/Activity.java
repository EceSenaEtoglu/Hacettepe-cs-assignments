public class Activity {

    final String ID;
    final String NAME;
    final int CALORIE_COUNT;


    // constructor
    public Activity(String activityId,String name,int calorieCount) {
        this.ID = activityId;
        this.NAME = name;
        this.CALORIE_COUNT = calorieCount;
    }

    /* return calculated calorie  ( calorie = coefficient * calorie count of object) */
    // casting won't result in data loss for this project
    public int calcCalorie(double coefficient) {
        return (int) (this.CALORIE_COUNT*coefficient);
    }


    // return String of activity data for given parameters Person obj and double coefficient
    // [person ID] tab has tab burned tab [calories burned]kcal tab thanks to tab [name of sport]

    public String getActivityData(Person myPerson, double coefficient) {
        return String.format("%s\thas\tburned\t%dkcal\tthanks\tto\t%s",myPerson.PERSON_ID,this.calcCalorie(coefficient),this.NAME);
    }

}