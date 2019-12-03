package dao;

import utils.DbConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VendorDaoImpl extends UnicastRemoteObject implements VendorDao {
    Connection cn = DbConnection.myConnection();
    public VendorDaoImpl() throws RemoteException {
        super();
    }

    @Override
    public Boolean checkVendor(String vendor_email, String password) throws RemoteException {
        try{
            String sql="SELECT vendor_email, password from vendor where vendor_email=? and password=?";
            PreparedStatement ps= cn.prepareStatement(sql);
            ps.setString(1, vendor_email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e){
            System.out.print("Exception"+e);
            return null;
        }
    }
}
