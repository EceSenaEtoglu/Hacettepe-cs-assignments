import java.util.ArrayList;

public class GameFlow {
    public static String processGame(MonopolyGame myMonopoly, ArrayList<String> commands) {
        // file string
        StringBuilder fileString = new StringBuilder();
        int count = 0;

        for (String command: commands) {
            // to check outside loop
            count ++;
            // check if game has ended on last command
            if (! myMonopoly.isGameOn()){
                break;
            }
            if (command.equals("show()")){
                // get show string
                fileString.append(myMonopoly);
                continue;
            }

            else {
                String user = command.split(";")[0];
                int space = Integer.parseInt(command.split(";")[1]);
                myMonopoly.setDice(space);
                myMonopoly.setPlayer(user);

                // check if player is in jail
                if(myMonopoly.getJailCount()!=0){
                    // get jail string
                    String jailString = myMonopoly.getJailString();
                    // process the command
                    fileString.append(myMonopoly.getProcessString(jailString));
                    // increment jail count
                    myMonopoly.incrementJailCount();
                    continue;
                }
                // check if player is in free parking
                else if (myMonopoly.getFreeParkingCount()!=0) {

                    // get free parking string
                    String freeParkingString = myMonopoly.getFreeParkingString();
                    fileString.append(myMonopoly.getProcessString(freeParkingString));
                    // increment free parking count
                    myMonopoly.incrementFreeParkingCount();
                    continue;
                }

                // if not in free parking or jail, process command
                // movePlayer's default value is dice
                myMonopoly.movePlayer();

                Object element = myMonopoly.getCurrentSquare();
                // if landed on a property
                if (element instanceof Property) {

                    Property tempProperty = (Property) element;
                    String propertyString = myMonopoly.landOnProperty(tempProperty);
                    fileString.append(myMonopoly.getProcessString(propertyString));
                }
                else if (element instanceof String) {
                    String tempString = (String) element;

                    if (tempString.equals("Chance")) {
                        String chanceString = myMonopoly.drawChance();
                        fileString.append(myMonopoly.getProcessString(chanceString));
                    }

                    else if(tempString.equals("Community Chest")) {
                        String communityChestString = myMonopoly.drawCommunityChest();
                        fileString.append(myMonopoly.getProcessString(communityChestString));
                    }

                    else if(tempString.equals("Go")) {
                        String goString = myMonopoly.landOnGo();
                        fileString.append(myMonopoly.getProcessString(goString));
                    }

                    else if(tempString.equals("Super Tax") || tempString.equals("Income Tax")) {
                        String taxString = myMonopoly.payTax();
                        fileString.append(myMonopoly.getProcessString(taxString));
                    }

                    else if(tempString.equals("Go to Jail") || tempString.equals("Jail")) {

                        // sends to jail,return necessary string
                        String jailString = myMonopoly.goJail();
                        fileString.append(myMonopoly.getProcessString(jailString));
                    }
                    else if (tempString.equals("Free Parking")) {

                        // returns free parking string, updates free parking count
                        String freeParking = myMonopoly.goFreeParking();
                        fileString.append(myMonopoly.getProcessString(freeParking));
                    }

                    else {
                        fileString.append("command not found").append("\n");
                    }
                }
            }
            if(myMonopoly.getIsZero()){
                break;
            }
        }
        // if last seen command is show, don't append
        if(!commands.get(count-1).equals("show()")) {
            fileString.append(myMonopoly);
        }
        return fileString.toString();
    }
}
