/**
 * This is the implementation of interface Student Dao.
 */
package com.lunchtime.dao;
import com.sun.rowset.CachedRowSetImpl;
import com.lunchtime.utils.DbConnection;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class StudentImpl extends UnicastRemoteObject implements StudentDao {
	private static final long serialVersionUID = 1L;
	/** Database Connection */
	Connection cn= DbConnection.myConnection();

	/** Constructor */
    public StudentImpl() throws RemoteException {
        super();
    }

    /**
     * This method is used to update the information of the user using the user id.
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param currentBalance
     * @param addBalance
     * @param userId
     * @throws RemoteException
     */
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

    /**
     * This method is fetch all the detail information of the user.
     * @return
     * @throws RemoteException
     */
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

    /**
     * This method is used to fetch all the detail of user using the first name of the user.
     * @param userName
     * @return
     * @throws RemoteException
     */
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

    /**
     * Count the total number of students.
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getTotalStudent() throws RemoteException {
        try{
            String sql= "SELECT sum(balance) as balance, count(id) as id from user";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            CachedRowSetImpl crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Counts the total number of canteen coins from the user table.
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getCanteenCoins() throws RemoteException {
        try{
            String sql= "SELECT sum(balance) as balance, count(id) as id from user";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            CachedRowSetImpl crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }


}
