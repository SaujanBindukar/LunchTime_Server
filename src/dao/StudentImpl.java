package dao;

import bll.Student;
import utils.DbConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

            String sql= "UPDATE user SET FirstName= ?, LastName=?, PhoneNumber=?, Balance=?, Email=? WHERE user_id =?";
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


}
