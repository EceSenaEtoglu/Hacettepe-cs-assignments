public class Food extends Activity {

    //constructor
    public Food(String foodId,String nameOfFood,int calorieCount){
        super(foodId,nameOfFood,calorieCount);
    }

    // return format:[person ID] tab has tab taken tab [calories taken]kcal tab from tab [name of food]
    @Override
    public String getActivityData(Person myPerson, double coefficient) {
        return String.format("%s\thas\ttaken\t%dkcal\tfrom\t%s",myPerson.PERSON_ID,this.calcCalorie(coefficient),this.NAME);
    }
}