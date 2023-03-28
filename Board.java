import java.util.*;
import java.sql.*;

public class Board{

    ArrayList<Block> board = new ArrayList<Block>(117);

    public Board(){
        
    }
    //default game board from db
    public void defaultBoard(ArrayList<Block> temp){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/monopoly","root","admin");  
            //here monopoly is database name, root is username and admin is password  
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select boardPosition from poleis");
            while(rs.next()){
                
            }
        }catch(Exception e){ System.out.println(e);}
    }
}