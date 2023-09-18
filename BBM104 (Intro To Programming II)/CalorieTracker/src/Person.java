import java.util.ArrayList;

public class Person {

    // constant year for project
    public static final int CURRENT_YEAR = 2022;

    private final int DAILY_CALORIE_NEED;
    private final String NAME;
    private final String GENDER;
    private final int AGE;
    private final int WEIGHT_IN_KG;
    private final int HEIGHT_IN_CM;

    // changes with doActivity method
    private int calorieBurned;
    private int calorieTaken;

    // specific for each Person obj.
    final String PERSON_ID;


    // constructor
    public Person(String personId, String name, String gender, String weight, String height, String dateOfBirth) {
        this.PERSON_ID = personId;
        this.NAME= name;
        this.GENDER = gender;
        this.WEIGHT_IN_KG = Integer.parseInt(weight);
        this.HEIGHT_IN_CM = Integer.parseInt(height);
        this.AGE = CURRENT_YEAR - Integer.parseInt(dateOfBirth);
        this.DAILY_CALORIE_NEED = this.calcDailyCalorieNeed();
    }


    //returns the closest int value to the result (in e.g. 432.6 >> 433, 432.49 >> 432)
    private int calcDailyCalorieNeed() {
        // 𝑪𝒂𝒍𝒄𝒖𝒍𝒂𝒕𝒊𝒐𝒏 𝒇𝒐𝒓 𝑴𝒆𝒏 = 66 + (13.75 𝑥 𝑤𝑒𝑖𝑔ℎ𝑡 (𝑘𝑔)) + (5 𝑥 ℎ𝑒𝑖𝑔ℎ𝑡 (𝑐𝑚)) – (6.8 𝑥 𝑎𝑔𝑒)
        // 𝑪𝒂𝒍𝒄𝒖𝒍𝒂𝒕𝒊𝒐𝒏 𝒇𝒐𝒓 𝑾𝒐𝒎𝒆𝒏 = 665 + (9.6 𝑥 𝑤𝑒𝑖𝑔ℎ𝑡 (𝑘𝑔)) + (1.7 𝑥 ℎ𝑒𝑖𝑔ℎ𝑡 (𝑐𝑚)) – (4.7 𝑥 𝑎𝑔𝑒)

        if ("male".equals(this.GENDER)) {
            return (int) Math.round(66 + (13.75 * this.WEIGHT_IN_KG) + (5 * this.HEIGHT_IN_CM) - (6.8 * this.AGE)); }

        // female condition
        else {
            return (int) Math.round(665 + (9.6 * this.WEIGHT_IN_KG) + (1.7 * this.HEIGHT_IN_CM) - (4.7 * this.AGE));}
    }


    /* precondition: coefficient refers to portion of food or duration of sport
     postcondition: update attributes calorieBurned or calorieTaken based on given activity and coefficient */

    public void doActivity(Activity activity,double coefficient) {
        // check if activity is sport
        if (activity instanceof Sport) {
            // calcCalorie method returns abs value for sport, store this value to calorieBurned
            this.calorieBurned += activity.calcCalorie(coefficient);
        }
        // if food
        else if (activity instanceof Food) {
            // store calculated value to calorieTaken
            this.calorieTaken += activity.calcCalorie(coefficient);
        }
    }


    // returns string in format:[name] tab [age] tab [daily calorie needs] tab [calories taken] tab [calories burned] tab [result]
    // result is an int, result =  (calories taken- calories burned - daily calorie need)
    @Override
    public String toString() {
        int result = this.calorieTaken - this.calorieBurned -this.DAILY_CALORIE_NEED;
        return String.format("%s\t%d\t%dkcal\t%dkcal\t%dkcal\t%+dkcal",this.NAME,this.AGE,this.DAILY_CALORIE_NEED,this.calorieTaken,this.calorieBurned,result);
    }


    // Warn if a person exceeds DAILY_CALORIE_NEED
    private boolean checkWarn() {
        return this.calorieTaken-this.calorieBurned - this.DAILY_CALORIE_NEED >0;
    }


    // returns a calorie status string based on toString() method
    // seperated by line for each Person object in given array, does not print \n for last element

    public static String getPrintText(ArrayList<Person> peopleArray) {
        StringBuilder text = new StringBuilder();

        for(Person person: peopleArray) {
            text.append(person.toString()).append("\n");
        }

        // delete last \n char
        text.deleteCharAt(text.length()-1);

        return text.toString();
    }


    // get calorie status of people to be warned, if (calories taken- calories burned - daily calorie) > 0 warn
    // if no people should be warned, return "there is  no  such    person"
    public static String getWarnText(ArrayList<Person> peopleArray) {

        ArrayList<Person> warnList = new ArrayList<>();

        // extract people to be warned from given array
        for(Person person : peopleArray) {
            if (person.checkWarn()) {
                warnList.add(person);}
        }


        if(warnList.size()!= 0) {
            return Person.getPrintText(warnList);}

        else {return "there\tis\tno\tsuch\tperson";}

    }


    // check if two people obj are equal, compare by obj.PERSON_ID
    // could have overridden equals and hashcode method, but this is an easier approach for this project
    public boolean doesNotExist(ArrayList<Person> peopleArray){

        for(Person person:peopleArray) {
            if ((this.PERSON_ID).equals((person.PERSON_ID))){
                return false;
            }
        }
        return true;
    }
}
