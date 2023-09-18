import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileOperations {

    public static void readCsv(String csvFileName) {

        String line;

        int counter = 0;

        //read file
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {

            while ((line = br.readLine()) != null) {

                if(counter == 0) {
                    counter++;
                    continue;
                }

                String[] data = line.split(",");

               double value = Double.parseDouble(data[6]);

                if(counter <= 500) {
                    Main.datas.get(0)[counter-1] = value;

                }

                if (counter <= 1000) {

                    Main.datas.get(1)[counter-1] = value;

                }

                if(counter <=2000) {

                    Main.datas.get(2)[counter-1] = value;

                }

                if(counter <= 4000) {

                    Main.datas.get(3)[counter-1] = value;

                }

                if(counter <= 8000) {

                    Main.datas.get(4)[counter-1] = value;

                }

                if(counter <= 16000) {

                    Main.datas.get(5)[counter-1] = value;

                }

                if(counter <= 32000) {

                    Main.datas.get(6)[counter-1] = value;


                }

                if(counter <= 64000) {

                    Main.datas.get(7)[counter-1] = value;


                }

                if(counter <= 128000) {
                    Main.datas.get(8)[counter-1] = value;

                }

                if(counter <= 250000) {

                    Main.datas.get(9)[counter-1] = value;
                }

                counter ++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
