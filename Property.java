import java.sql.*;
//import java.util.*;

public class Property extends Block{
    
    //variable decleration
    protected String name;
    protected int price;
    protected int housesAmount;
    protected boolean hasHotel;
    protected String owner;
    protected int rent;

    //constructor, w/ owner filled
    public Property(int blockPosition, String name, int price, int housesAmount, boolean hasHotel,String owner, int rent){
        super(blockPosition);
        this.name = name;
        this.price = price;
        this.housesAmount = housesAmount;
        this.hasHotel = hasHotel;
        this.owner = owner;
        this.rent = rent;
    }

    //constructor, w/o owner filed
    public Property(int blockPosition, String name, int price, int housesAmount, boolean hasHotel, int rent){
        super(blockPosition);
        this.name = name;
        this.price = price;
        this.housesAmount = housesAmount;
        this.hasHotel = hasHotel;
        this.rent = rent;
    }

    //constructor for station blocks
    public Property(int blockPosition, String name, int price, String owner, int rent){
        super(blockPosition);
        this.name = name;
        this.price = price;
        this.owner = owner;
        this.rent = rent;
    }

    //rent calculator for classic property
    public int calculateRent(int boardPosition){
        int res = 0;
        try{//database connection
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/monopoly","root","admin");  
            //here monopoly is database name, root is username and admin is password  
            Statement stmt=con.createStatement();
            String sqlQuery = "select * from poleis where boardPosition=" + boardPosition + "";
            ResultSet rs=stmt.executeQuery(sqlQuery);
            if(this.hasHotel){
                res = rs.getInt(9);
            }else{
                switch(this.housesAmount){
                    case 1:
                        res = rs.getInt(5);
                        break;
                    case 2:
                        res = rs.getInt(6);
                        break;
                    case 3:
                        res = rs.getInt(7);
                        break;
                    case 4:
                        res = rs.getInt(8);
                        break;
                    default:
                        res = rs.getInt(4);
                }
            }
        }catch(Exception e){System.out.println(e);}
        return res;
    }

    //increase houseAmount and calculate new rent
    public void increaseHousesAmount(){
        this.housesAmount ++;
        this.rent = calculateRent(this.blockPosition);
    }

    //purchese of a hotel, sets housesAmount to 0, calculates new rent
    public void purchaseHotel(){
        this.housesAmount = 0;
        this.hasHotel = true;
        rent = calculateRent(this.blockPosition);
    }

    //sets new price in case a player wants to sell the property
    public void setPrice(int price){
        this.price = price;
    }

}