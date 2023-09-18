import java.util.ArrayList;

public class MonopolyGame {

    private boolean isZero = false;

    private boolean gameOn = true;

    private final ArrayList<String> chanceDeck;
    private final ArrayList<String> communityChestDeck;
    private final Board board;

    private int dice;

    private  Player currentPlayer;
    private final Player player1;
    private final Player player2;
    private final Banker banker;

    public MonopolyGame(Board board, Player player1, Player player2, Banker banker,ArrayList<String> chanceList,ArrayList<String> communityChestList) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.banker = banker;
        this.chanceDeck = chanceList;
        this.communityChestDeck = communityChestList;
    }

    public boolean getIsZero() { return isZero;}

    // return game continuity
    public boolean isGameOn() {
        return gameOn;
    }

    // set current player to specified player name
    public void setPlayer(String playerName) {
        this.currentPlayer = (playerName.equals("Player 1") ? player1 : player2);
    }

    // set dice to given parameter
    public void setDice(int dice) {
        this.dice = dice;
    }

    // move player as specified space
    // only going to be used in movePlayer(-3) so no need to modify go cash

    public void movePlayer(int space) {
        // set new position, mod operation returns 40 if given position is 40
        int newPosition = (currentPlayer.getPosition() + space);
        while (newPosition >0) {
            newPosition += 40;
        }
        newPosition = (((currentPlayer.getPosition() + space) - 1) % 40) + 1;
        currentPlayer.setPosition(newPosition);
    }

    // overload
    // move player as current dice
    public void movePlayer(){
        // set new position, mod operation returns 40 if given position is 40
        int newPosition = (currentPlayer.getPosition() + dice);

        if(newPosition > 41) {
            // if a tour is completed and you are not on go square ! get 200 tl from bank
            // should be greater than 41, because 41 means go square
            // player already gets money from go square in diff function
            makeMoneyTransaction(banker,currentPlayer,200);
        }
        // position takes values from 1 to 40 (included)
        newPosition = (((currentPlayer.getPosition() + dice) - 1) % 40) + 1;
        currentPlayer.setPosition(newPosition);
    }

    // return current player's position square on board,
    public Object getCurrentSquare() {
        return board.boardDesign.get(currentPlayer.getPosition());
    }

    // check bankrupt situation if given cost is payed
    public boolean notGoneBankrupt(User user, int cost) {
        if(user.getMoney() == cost) {
            isZero = true;
        }
        return user.getMoney() >= cost;
    }

    // make money transactions from source to target as given cost
    // assure that player wont go bankrupt
    private void makeMoneyTransaction(User source,User target,int cost) {
        source.addMoney(-1*cost);
        target.addMoney(cost);
    }

    // calc rent for given property
    // diff. rules applied according to type of property
    private int calcRent(Property property) {

        // for Land, rent is calculated based on cost of Land
        if (property instanceof Land) {
            return ((Land) property).calcRent();
        }
        // for Company, rent is calculated based on dice
        else if (property instanceof Company) {
            return 4*dice;
        }
        // for RailRoad, rent is calculated based on railRoadNum
        // railRoadNum is number of railRoads the player that is not current player has
        else {
            int railRoadNum = ((currentPlayer == player1) ? player2 : player1).getRailRoadNum();
            return 25*railRoadNum;
        }
    }

    // assure that player won't go bankrupt
    // make money transactions between player and bank,update ownership of property
    // returns process string
    private String ownProperty(Property property) {
        // make money transactions with bank
        makeMoneyTransaction(currentPlayer,banker,property.getCost());
        // add property to player's properties list
        currentPlayer.addProperty(property);
        // set owner of property to current player
        property.setOwner(currentPlayer);
        return String.format("%s bought %s",currentPlayer.getName(),property.getName());
    }

    // assure that player won't go bankrupt
    // pay the rent, return process string
    private String payRent(Property property) {
        int cost = calcRent(property);
        // make money transactions from current player to owner
        makeMoneyTransaction(currentPlayer,property.getOwner(),cost);
        // Player 2 paid rent for Pentonville Road
        // return process string
        return String.format("%s paid rent for %s",currentPlayer.getName(),property.getName());
    }

    // returns necessary property string without \n when player lands on a property
    // updates money,ownership based on action
    // property strings: bankrupt,has,bought,paid rent
    public String landOnProperty(Property property) {

        String propertyString ;

        // if not owned by anyone
        if (property.getOwner() == null) {
            int cost = property.getCost();
            // if player can afford to buy, must buy the property
            if (notGoneBankrupt(currentPlayer,cost)) {
                // do necessary operations, return the string of these op.
                propertyString = ownProperty(property);
                return propertyString;
            }
            // player goes bankrupt if he can't afford to buy, game ends
            else {
                gameOn = false;
                propertyString = getBankruptString(currentPlayer);
                return propertyString;
            }
        }
        // if player is the owner, state that in message
        else if (property.getOwner() == currentPlayer) {
            propertyString = String.format("%s has %s", currentPlayer.getName(), property.getName());
            return propertyString;
        }

        // if property is owned by other player, player must pay rent to other player
        else {
            // if player does not go bankrupt in case of paying rent, pay the rent
            int cost = calcRent(property);
            if (notGoneBankrupt(currentPlayer,cost)) {
                propertyString = payRent(property);
                return propertyString;
            }
            // if player goes bankrupt, display bankrupt string, end game
            else {
                gameOn = false;
                // bankruptString
                propertyString = getBankruptString(currentPlayer);
                return propertyString;
            }
        }
    }


    // [Player]tab[Dice]tab[New Position]tab[Player1 Money]tab[Player2 Money]tab[Processing]
    public String getProcessString(String actionString) {

        // must call the func after updating position and money
        int money1 = player1.getMoney();
        int money2 = player2.getMoney();
        return String.format("%s\t%d\t%d\t%d\t%d\t%s\n", currentPlayer.getName(), dice, currentPlayer.getPosition(), money1, money2, actionString);
    }


    // return player x goes bankrupt
    public String getBankruptString(User user) {
        if(isZero) {
            return String.format("%s would have 0 money, game ends",user.getName());
        }
        return String.format("%s goes Bankrupt",user.getName());
    }

    // for command "show" and to display at the end of the game
    // Player 1 13080 have: Pentonville Road, Whitehall
    // Player 2 10120 have: King Cross Station, Marylebone Station, Whitechapel Road
    // Banker 106800
    // Winner Player 1
    @Override
    public String toString() {
        StringBuilder show = new StringBuilder();
        // if player2's money is greater than player1 he is the winner, else player 1 is the winner
        // if they have equal money,player1 is the winner
        String winner =  player2.getMoney()> player1.getMoney() ? "Winner Player 2\n":"Winner Player 1\n";
        show.append("----------------------------------------------------------------------------------------------------------------------------\n");
        show.append((player1)).append("\n").append(player2).append("\n").append(banker.toString()).append("\n").append(winner);
        show.append("----------------------------------------------------------------------------------------------------------------------------\n");
        return show.toString();
    }


    // written for cards
    // return String "Advance to Go (Collect $200)", update money and position
    public String advanceToGo() {
        String returnString = "Advance to Go (Collect $200)";
        // go's position
        currentPlayer.setPosition(1);
        // update money
        if(notGoneBankrupt(banker,200)) {
            makeMoneyTransaction(banker,currentPlayer,200);
        }
        // if banker goes bankrupt
        else {
            gameOn = false;
            returnString = returnString + getBankruptString(banker);
        }
        return returnString;
    }


    // makes necessary actions when chance is selected
    // returns action string
    public String drawChance() {
        String operation = chanceDeck.get(0);
        String cardString = String.format("%s draw Chance ",currentPlayer.getName());

        switch (operation) {
            case "Advance to Go (Collect $200)":
                // player x draw chance  "Advance to Go (Collect $200)"
                cardString +=  advanceToGo();
                break;

            case "Advance to Leicester Square":

                // change position to Leicester Square
                int leicesterPos = board.getPropertyPos("Leicester Square");
                //assert leicesterPos == 27;

                if(currentPlayer.getPosition() == 37) {
                    // if makes a full round add 200 tl to player
                    makeMoneyTransaction(banker,currentPlayer,200);
                }

                currentPlayer.setPosition(leicesterPos);
                // player x draw chance advance to leicester square + (land string)
                cardString += operation+" " + landOnProperty((Property) board.boardDesign.get(leicesterPos));
                break;

            case "Go back 3 spaces":
                // change position
                movePlayer(-3);
                // after moving, only positions 5,20,34 are possible

                // if player is in tax square
                if(currentPlayer.getPosition()== 5) {

                    // player x draw chance go back 3 spaces player x paid tax
                    // player x draw change go back 3 spaces  player x goes bankrupt

                    cardString += operation + payTax();
                }
                // if player is in Vine Street
                else if (currentPlayer.getPosition() == 20) {

                    // player x draw chance go back 3 spaces player x bought vine street
                    // player x draw chance go back 3 spaces player x has vine street
                    // player x draw chance go back 3 spaces player x paid rent for vine street
                    // player x draw chance go back 3 spaces player x goes bakrupt

                    String propertyString = landOnProperty((Property) board.boardDesign.get(20));
                    cardString += operation + propertyString;
                }
                // if player is in community chest
                else if (currentPlayer.getPosition() == 34) {
                    String communityChestString = drawCommunityChest();
                    // player x draw chance go back 3 spaces player x draw community chest .....

                    cardString += operation + communityChestString;
                }
                break;

            case "Pay poor tax of $15":
                // Player x draw chance pay poor tax of 15 player x goes bankrupt
                // Player x draw chance pay poor tax of 15

                // if player can afford ,pay 15 tl to banker
                cardString += processMoneyOperation(operation,currentPlayer,banker,15);
                break;

            case "Your building loan matures - collect $150":
                // player gets 150 tl from banker
                cardString += processMoneyOperation(operation,banker,currentPlayer,150);
                break;

            case "You have won a crossword competition - collect $100 ":
                // player x draw chance "You have won a crossword competition - collect $100 "
                // player x draw chance "You have won a crossword competition - collect $100  banker goes bankrupt"

                // player gets 100 tl from banker
                cardString += processMoneyOperation(operation,banker,currentPlayer,100);
                break;
        }
        // get the card at the end of the deck
        chanceDeck.remove(operation);
        chanceDeck.add(operation);
        return cardString;

    }


    // makes necessary actions when chest is selected
    // returns action string
    public String drawCommunityChest() {
        String operation = communityChestDeck.get(0);
        Player otherPlayer = ((currentPlayer == player1) ? player2 : player1);
        String returnString = String.format("%s draw Community Chest ",currentPlayer.getName());
        String cardValue = "";

        switch (operation) {
            case "Advance to Go (Collect $200)":
                cardValue =  returnString + advanceToGo();
                break;

            case "Bank error in your favor - collect $75":
                // get 75 tl from banker
                cardValue = returnString + processMoneyOperation(operation,banker,currentPlayer,75);
                break;

            case "Doctor's fees - Pay $50":
                // pay banker 50 tl
                cardValue =  returnString + processMoneyOperation(operation,currentPlayer,banker,50);
                break;

            case "It is your birthday Collect $10 from each player":
                // get 10 tl from other player
                cardValue =  returnString + processMoneyOperation(operation,otherPlayer,currentPlayer,10);
                break;

            case "Grand Opera Night - collect $50 from every player for opening night seats":
                // get 50 tl from other player
                cardValue = returnString + processMoneyOperation(operation,otherPlayer,currentPlayer,50);
                break;
            case "Income Tax refund - collect $20":
                // get 20 tl from banker
                cardValue =  returnString + processMoneyOperation(operation,banker,currentPlayer,20);
                break;
            case "Life Insurance Matures - collect $100":
                // get 100 tl from banker
                cardValue =  returnString + processMoneyOperation(operation,banker,currentPlayer,100);
                break;
            case  "Pay Hospital Fees of $100":
                // pay 100 tl to banker
                cardValue =  returnString + processMoneyOperation(operation,currentPlayer,banker,100);
                break;

            case "Pay School Fees of $50":
                // pay 50 tl to banker
                cardValue =  returnString + processMoneyOperation(operation,currentPlayer,banker,50);
                break;

            case "You inherit $100":
                // get 100 tl from banker
                cardValue =  returnString + processMoneyOperation(operation,banker,currentPlayer,100);
                break;

            case "From sale of stock you get $50":
                // get 50 tl from banker
                cardValue =  returnString + processMoneyOperation(operation,banker,currentPlayer,50);
                break;
        }
        // get the card at the end of the deck
        communityChestDeck.remove(operation);
        communityChestDeck.add(operation);
        return cardValue;
    }


    // if source does not go bankrupt with operation, does the money operation returns operation's string
    // if bankrup situation occurs, does not do the money operation returns bankrupt string
    private String processMoneyOperation(String operation,User source, User destination, int cost) {
        // do transaction if user not goes bankrupt, return pure cardOperation
        if(notGoneBankrupt(source,cost)) {
            makeMoneyTransaction(source,destination,cost);
            return operation;
        }
        // if user goes bankrupt add bankrupt string
        else {
            gameOn = false;
            return getBankruptString(source);
        }
    }


    // if player can pay, pays the tax (100 tl) returns "player x paid tax"
    // if player cant pay, does not pay the tax returns "player x goes bankrupt"
    public String payTax() {
        String operation = String.format("%s paid tax",currentPlayer.getName());
        // pay 100 tl to banker if you can, return action string
        return processMoneyOperation(operation,currentPlayer,banker,100);
    }


    // sends to jail, updates jail count
    // returns jail string
    public String goJail() {
        // set player's position to jail

        // if current player is in go to jail square, will make a full round when position changes to jail square
        // get 200 tl
        /*if(currentPlayer.getPosition() == 31) {
            makeMoneyTransaction(banker,currentPlayer,200);
        }*/

        // (for command "go to jail")
        currentPlayer.setPosition(11);

        // "%s went to jail"
        String jailString =  getJailString();
        // increment word should not be confusing
        // only called when jail count is 0
        incrementJailCount();
        return jailString;
    }

    // returns jail string based on count
    public String getJailString() {
        String jailString;
        if (currentPlayer.getJailCount() == 0) {
            jailString = String.format("%s went to jail",currentPlayer.getName());
        }
        else {
            jailString = String.format("%s in jail (count = %d)",currentPlayer.getName(),currentPlayer.getJailCount());
        }
        return jailString;
    }

    // increment jail count with respect to period
    public void incrementJailCount() {
        currentPlayer.setInJailCount(currentPlayer.getJailCount()+1);
    }

    // current player's jail count
    public int getJailCount() {
        return currentPlayer.getJailCount();
    }

    // return String player x is in free parking, update free parking count
    public String goFreeParking() {
        // set player's position to free parking
        // not necessary
        currentPlayer.setPosition(21);
        String freeParkingString = getFreeParkingString();
        // increment freeparking
        incrementFreeParkingCount();
        return freeParkingString;
    }

    // returns free parking string

    public String getFreeParkingString() {
        String freeParkingString;
        // not necessary
        if (currentPlayer.getFreeParkingCount() == 0) {
            freeParkingString = String.format("%s is in Free Parking",currentPlayer.getName());
        }
        else {
            freeParkingString = String.format("%s in Free Parking (count = %d)",currentPlayer.getName(),currentPlayer.getFreeParkingCount());
        }
        return freeParkingString;
    }
    public void incrementFreeParkingCount() {
        currentPlayer.setFreeParkingCount(currentPlayer.getFreeParkingCount()+1);
    }

    public int getFreeParkingCount() {
        return currentPlayer.getFreeParkingCount();
    }

    // called when go command comes
    public String landOnGo() {
        // change position to (1) go square
        currentPlayer.setPosition(1);
        // add 200 tl from banker to player
        makeMoneyTransaction(banker,currentPlayer,200);
        return String.format("%s is in GO square",currentPlayer.getName());
    }
}