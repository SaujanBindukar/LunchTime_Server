/**
 * @author Saujan Bindukar
 * Implementation of FoodDao Interface which connects database and retrieve value.
 */
package com.lunchtime.dao;
import com.lunchtime.bll.FoodMenu;
import com.sun.rowset.CachedRowSetImpl;
import com.lunchtime.utils.DbConnection;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class FoodDaoImpl extends UnicastRemoteObject implements FoodDao {
	private static final long serialVersionUID = 1L;
	/** Database Connection */
	Connection cn = DbConnection.myConnection();
	/** Constructor */
    public FoodDaoImpl() throws RemoteException {
    }

    /**
     * Fetch all the list of food menu from the database.
     * @throws RemoteException
     */
    @Override
    public ResultSet showMenu() throws RemoteException {
        try {
            ResultSet rs= cn.createStatement().executeQuery("select * from menu");
            CachedRowSetImpl crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;

        } catch (SQLException e) {
            return null;
        } }

    /**
     * Insert the food menu i.e. food name, food price and food picture into the database table menu.
     * @param fm
     * @return
     * @throws RemoteException
     */
    @Override
    public String addMenu(FoodMenu fm) throws RemoteException {
        try {
            String sql = "INSERT INTO menu(food_name, food_price, picture) VALUES(?,?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, fm.getFood_name());
            ps.setInt(2, fm.getFood_price());
            ps.setString(3, fm.getPicture());
            ps.execute();
            return "Success";
        } catch (SQLIntegrityConstraintViolationException dup){
            return "Duplicate";
        } catch (SQLException e) {
            System.out.print(e);
            return "Error";
        }
    }

    /**
     * Selects the specific food detail using the food name from the database.
     * @param foodName
     * @throws RemoteException
     */
    @Override
    public ResultSet getFoodByName(String foodName) throws RemoteException {
        try {
            String sql = "SELECT * FROM menu WHERE food_name=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, foodName);
            ResultSet rs = ps.executeQuery();
            CachedRowSetImpl crs = new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        } catch (SQLException e) {
            throw  new Error(e);
        }
    }

    /**
     * Delete the detail of food using the food name from the database.
     * @param foodName
     * @throws RemoteException
     */
    @Override
    public void deleteMenu(String foodName){
        try {
            String sql = "DELETE FROM menu WHERE food_name=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, foodName );
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Update the food menu using the food id.
     * @param food_name
     * @param food_price
     * @param foodId
     * @param picture
     */
    @Override
    public void updateMenu(String food_name, int food_price, int foodId, String picture){
        try{
            String sql= "UPDATE menu SET food_name= ?, food_price=?, picture=? WHERE food_id =?";
            PreparedStatement ps =cn.prepareStatement(sql);
            ps.setString(1, food_name);
            ps.setInt(2, food_price);
            ps.setInt(4, foodId);
            ps.setString(3, picture);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println("Exception"+e);
        }
    }

    /**
     * Count the total number of food available form the database.
     * @throws RemoteException
     */
    @Override
    public ResultSet getTotalFood() throws RemoteException {
        try{
            String sql="Select count(food_id) as food_id from menu";
            PreparedStatement ps= cn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            CachedRowSetImpl crs = new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
