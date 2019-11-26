package dao;

import bll.FoodMenu;
import utils.DbConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodDaoImpl extends UnicastRemoteObject implements FoodDao {
    Connection cn = DbConnection.myConnection();
    public FoodDaoImpl() throws RemoteException {
    }

    @Override
    public ResultSet showMenu() throws RemoteException {
        try {
            String sql = "SELECT * FROM menu";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;

        } catch (SQLException e) {
            return null;

        }



    }

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
}
