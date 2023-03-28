import java.util.*;


public class Player{

    //variable decleration
    protected String name;
    protected int playerOrder;
    protected int playerPosition;
    protected ArrayList<Block> properties = new ArrayList<>();
    protected ArrayList<Action> actions = new ArrayList<>();//ArrayList with card that the player could hold to use later
    protected int cashBalance;
    protected boolean isInPrison;

    //constructor w/o properties ArrayList
    public Player(String name,int playerOrder, int playerPosition, int cashBalance){
        this.name = name;
        this.playerOrder = playerOrder;
        this.playerPosition = playerPosition;
        this.cashBalance = cashBalance;
    }

    
    //changes the current position of a player for the given amount, that dice have rolled in main
    public int movePlayer(int dice){
        this.playerPosition += dice;
        return this.playerPosition;
    }

    //automatically pays rent for current player
    public void payRent(Player owner, int rent){
        this.cashBalance -= rent;
        owner.cashBalance += rent;
    }

    //purchase give property for current player, adds the property to ArrayList
    public void purchaseProperty(Property property){
        this.properties.add(property);
    }

    //checks if a player is bankrupt
    public boolean isBankrupt(){
        boolean res;
        if(this.cashBalance <= 0)
            res = true;
        else 
            res = false;
        return res;
    }

    //lets the player hold an action card for later use
    public void holdActionCard(Action action){
        this.actions.add(action);
    }

    //lets the player use a previously acquired action card
    public void useAcquiredActionCard(Action action){
        this.actions.remove(action);
    }

}