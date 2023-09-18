import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int rowS = scanner.nextInt();
        int colS = scanner.nextInt();
        int colId = scanner.nextInt() - 1;
        Character[][] matrix = new Character[rowS][colS];

        Integer[][] visited = new Integer[rowS][colS];
        for(int i = 0; i<rowS;i++){
            for(int j = 0; j<colS;j++){
                visited[i][j] = -1;
            }
        }

        for(int i = 0; i<rowS;i++){
            String line = scanner.next();
            for(int j = 0;j<line.length();j++){
                matrix[i][j] = line.charAt(j);
            }
        }

        int steps = 0;
        int stepsInCycle = 0;
        boolean cycle = false;

        int pointerX = 0;
        int pointerY = colId;

        while ( 0<=pointerX && pointerX < rowS && 0<=pointerY && pointerY <colS ) {

            Character curr = matrix[pointerX][pointerY];

            if(visited[pointerX][pointerY] != -1 ){
                stepsInCycle= steps - visited[pointerX][pointerY];
                cycle = true;
                break;
            }

            visited[pointerX][pointerY] = steps;

            switch (curr){
                case 'N':
                    pointerX -= 1;
                    break;

                case 'S':
                    pointerX += 1;
                    break;

                case 'W':
                    pointerY -= 1;
                    break;

                case 'E':
                    pointerY += 1;
                    break;
            }

            steps++;

        }
        if(cycle){
            String msg = String.format("%d step(s) before getting stuck in a loop of %d step(s).",visited[pointerX][pointerY],stepsInCycle);
            System.out.println(msg);

        }
        else{
            String msg = String.format("%d step(s) to freedom. Yay!",steps);
            System.out.println(msg);
        }
    }
}