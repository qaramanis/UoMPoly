import java.sql.*;  
import java.util.*;


class Main{  
    public static void main(String args[]){  
        
        //localhost@3306 database connection w/ username: root, password:admin
        

        //devleration of int[] excluding, needed for generateActionTypeContext
        int[] excluding = null;
        //different ArrayLists w/ all cities, entoles and apofaseis
        ArrayList<Property> properties = defaultProperties();
        ArrayList<Property> availableProperties = defaultProperties();
        ArrayList<Action> actions = defaultActions();
        ArrayList<Action> availableActions = defaultActions();

        //new board creation w/ cities, entoles and apofaseis
        ArrayList<Block> board = new ArrayList<Block>(40);
        for(Property p : properties)
            board.add(p.blockPosition, p);//adding the cities to the board
        
        for(Action a : actions)
            board.add(a.blockPosition, a);//adding action blocks to the board

        
        ArrayList<Player> players = new ArrayList<>();
        for(int i=0;i<4;i++){
            Player p = new Player("A", i, 0, 1000);
            players.add(p);
        }
        
        //main game loop w/ variable decleration
        int d1=0;
        int d2=0;
        boolean areEqual = true;
        boolean rollsAgain = false;
        int dice;
        outer:
        while(true){
            for(Player pl : players){
                do{
                    d1 = (int)(Math.random()*6) + 1;
                    d2 = (int)(Math.random()*6) + 1;
                    if(d1 == d2){
                        areEqual = true;//player gets out of prison, or rolls again
                        //add connection to ActionListener + visuals 
                        if(pl.isInPrison == true)
                            pl.isInPrison = false;//this scenario gets player out of prison
                    }
                    dice = d1 + d2;
                    pl.movePlayer(dice);
                    Block b = board.get(pl.playerPosition);
                    if(b instanceof Action){
                        Action a = (Action)b;
                        a.generateNewActionTypeContent(excluding);
                        //will generate correctly,however we need to do smth about actually executing the actions
                    }else if(b instanceof Property){
                        Property p = (Property)b;
                        //here add some sort of print depending on case + possible actions
                        if(p.owner == null){
                            //in this case nobody owns the property ->
                            //print smth about wanting to purchase y/n
                            //and execute accordingly
                        }else if(p.owner.equals(pl.name)){//check if player has all properties of the color
                            //ask player if he wants to build smth                        
                        }else if(p.owner != null && !p.owner.equals(pl.name)){//current player pays rent if property is owned by another player
                            pl.payRent(pl, p.calculateRent(pl.playerPosition));
                        }
                    }
                    if(pl.isInPrison == false && areEqual == true)
                        rollsAgain = true;
                    if(pl.isBankrupt()){
                        players.remove(pl);
                        if(players.size() == 1)
                            break outer;
                    }
                }while(rollsAgain);//rollAgain determined earlier
            }
        }
        //print smth about winner -> last in ArrayList players
    }

    //default starting cities list w/ no houses, no hotels and no owner from db
    public static ArrayList<Property> defaultProperties(){
        ArrayList<Property> temp = new ArrayList<>();
        try{//database connection 
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/monopoly","root","admin");  
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from poleis");//all elements from table poleis
            while(rs.next()){
                Property p = new Property(rs.getInt(1), //blockPosition - boardPosition
                                        rs.getString(2), //name - cityName
                                        rs.getInt(3), //price - initialCityPrice
                                        0,  //housesAmount = 0
                                        false, //hasHotel = false
                                        0); //initial rent price = 0, cant calculate rent if is p not iniitalized
                p.rent = p.calculateRent(p.blockPosition);//rent price after c initialization
                temp.add(p);
            }
            con.close();
        }catch(Exception e){System.out.println(e);}
        return temp;
    }

    //default starting ArrayList of all Actions from db, where Actions are entoles and apofaseis
    public static ArrayList<Action> defaultActions(){
        ArrayList<Action> temp = new ArrayList<>();
        try{//daabase connection
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/monopoly","root","admin");  
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from actions");//all elements from entoles table
            while(rs.next()){
                Action a = new Action(rs.getInt(2), //blockPosition
                                        rs.getInt(3));//action type
                temp.add(a);
            }
            con.close();
        }catch(Exception ex){System.out.println(ex);}
        return temp;
    }

    //generates the current block, given the numerical position of a player
    public Block getBlock(int position, ArrayList<Block> board){
        Block b = board.get(position);
        if(b instanceof Property){
            Property p = (Property)b;
            return p;
        }else{
            Action a = (Action)b;
            return a;
        }
    }


}  