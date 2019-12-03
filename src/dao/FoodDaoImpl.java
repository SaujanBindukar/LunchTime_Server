package dao;
import bll.FoodMenu;
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
            return rs;

        } catch (SQLException e) {
            return null;
        } }

    @Override
    public void addMenu(FoodMenu fm) throws RemoteException {

        try {
            String sql = "INSERT INTO menu(food_name, food_price) VALUES(?,?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, fm.getFood_name());
            ps.setInt(2, fm.getFood_price());
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
            return rs;

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
}
