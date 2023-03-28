import java.util.*;
import java.sql.*;

public class Action extends Block{//class for apofaseis and entoles
    
    //variable declareation
    protected int type;
    
    //constructor
    public Action(int blockPosition,int type){
        super(blockPosition);
        this.type = type;//0 for apofasi, 1 for entoli,type 2 for corners
    }
    
    public int getType(){
        return this.type;
    }

    public String generateNewActionTypeContent(int[] excluding){
        String actionContent = "error"; //value to return
        Random rnd = new Random();
        int val;
        val = getRandomWithExclusion(rnd, 1, 51, excluding);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/monopoly","root","admin");  
            //here monopoly is database name, root is username and admin is password  
            Statement stmt=con.createStatement();
            String sqlString = "select * from actions where type = "+this.type+" and actionID = "+val+"";
            ResultSet rs=stmt.executeQuery(sqlString);
            actionContent = rs.getString(1);
            excluding = addElement(excluding, rs.getInt(3));
            con.close();
        }catch(Exception e){System.out.println(e);}
        return actionContent;
    }

    public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude){
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for(int ex : exclude){
            if(random < ex){
                break;
            }
            random ++;
        }
        return random;
    }

    public int[] addElement(int[] a, int e){
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    
}