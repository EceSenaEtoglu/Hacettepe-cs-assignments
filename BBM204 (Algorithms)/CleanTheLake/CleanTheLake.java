import java.util.*;

public class Main {

    static class Shrimp{

        int cost;
        int dailyEaten;
        int number;
        int id;
        public Shrimp(int cost,int dailyEaten,int number,int id){
            this.cost = cost;
            this.dailyEaten = dailyEaten;
            this.number = number;
            this.id = id;
        }
    }

    // calc if it is infeasible
    public static boolean isInfeasible(ArrayList<Shrimp> shrimps,int amount){
        int total = 0;
        for (Shrimp shrimp:shrimps){
            total += shrimp.dailyEaten*shrimp.number;
            if(total>=amount){
                return false;
            }
        }
        return true;
    }
    public static int findShrimps(ArrayList<Shrimp> shrimps,int target){

        //traverse sorted shrimp arraylist to find shrimps
        int total = 0;
        int cost = 0;
        for(Shrimp shrimp:shrimps){
            int i = 0;
            // find sufficient shrimp number
            for(; i<shrimp.number;i++) {
                total += shrimp.dailyEaten;
                cost += shrimp.cost;
                if(total >= target){
                    i++;
                    break;
                }
            }
            System.out.printf("Bought %s of shrimp %s.%n",i,shrimp.id);
            
            //break if target has been reached
            if(total >= target){
                break;
            }
        }
        return cost;

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int A = scanner.nextInt();
        int day = scanner.nextInt();
        int shrimpTypeNum = scanner.nextInt();

        scanner.nextLine();
        String shrimpData = scanner.nextLine();
        ArrayList<Shrimp> shrimps = new ArrayList<>();

        // get shrimp data
        String[] datas = shrimpData.split(" ");
        for(int i = 0; i<shrimpTypeNum;i++){

            int[] data = Arrays.stream(datas[i].split(",")).mapToInt(Integer::parseInt).toArray();
            Shrimp shrimp = new Shrimp(data[0],data[1],data[2],i+1);
            shrimps.add(shrimp);
        }

        //reversely sort shrimps
        Collections.sort(shrimps, new Comparator<Shrimp>() {
            @Override
            public int compare(Shrimp o1, Shrimp o2) {
                double priorityo1 = (double)o1.dailyEaten / o1.cost;
                double priorityo2 = (double)o2.dailyEaten / o2.cost;

                return Double.compare(priorityo2,priorityo1);

            }
        });
        int target = A/day;
        boolean isInfeasible = isInfeasible(shrimps,target);
        if(isInfeasible){
            System.out.println("Infeasible");
        }
        else{
            int cost = findShrimps(shrimps,target);
            System.out.println(String.format("Total: %s$.",cost));

        }
    }
}