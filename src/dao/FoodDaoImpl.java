package dao;
import bll.FoodMenu;
import com.sun.rowset.CachedRowSetImpl;
import utils.DbConnection;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class FoodDaoImpl extends UnicastRemoteObject implements FoodDao {
    Connection cn = DbConnection.myConnection();
    public FoodDaoImpl() throws RemoteException {
    }

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

    @Override
    public void addMenu(FoodMenu fm) throws RemoteException {

        try {
            String sql = "INSERT INTO menu(food_name, food_price, picture) VALUES(?,?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, fm.getFood_name());
            ps.setInt(2, fm.getFood_price());
            ps.setString(3, fm.getPicture());
            ps.execute();

        } catch (SQLException e) {
            System.out.print(e);

        }

    }

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

    @Override
    public void deleteMenu(String foodName) throws RemoteException {
        try {

            String sql = "DELETE FROM menu WHERE food_name=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, foodName );
            ps.executeUpdate();
        } catch (SQLException e) {

        }

    }

    @Override
    public void updateMenu(String food_name, int food_price, int foodId, String picture) throws RemoteException {
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
}
