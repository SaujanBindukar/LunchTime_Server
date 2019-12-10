package dao;

import bll.Student;
import com.sun.rowset.CachedRowSetImpl;
import utils.DbConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class StudentImpl extends UnicastRemoteObject implements StudentDao {
    Connection cn= DbConnection.myConnection();
    public StudentImpl() throws RemoteException {
        super();
    }


    @Override
    public void updateUser(String firstName, String lastName, String email, String phoneNumber, int currentBalance, int addBalance, int userId) throws RemoteException {
        try{
            int curBalance= currentBalance;
            int addingBalance= addBalance;
            int newBalance= curBalance+addingBalance;

            String sql= "UPDATE user SET first_name= ?, last_name=?, phone_number=?, balance=?, email=? WHERE id =?";
            PreparedStatement ps =cn.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phoneNumber);
            ps.setInt(4, newBalance);
            ps.setString(5, email);
            ps.setInt(6, userId);
            ps.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Exception"+ e);
        }
    }

    @Override
    public ResultSet getAllUser() throws RemoteException {
        try{
            ResultSet rs= cn.createStatement().executeQuery("select id, first_name, last_name, phone_number,  email, balance from user");
            CachedRowSetImpl crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public ResultSet searchUser(String userName) throws RemoteException {

        try{
            String sql = "select id, first_name,  last_name, phone_number, email, balance from user where first_name=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, userName );
            ResultSet rs = ps.executeQuery();
            CachedRowSetImpl crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;


        }catch (Exception e){
            System.out.println(e);
        }
        return  null;

    }


}
