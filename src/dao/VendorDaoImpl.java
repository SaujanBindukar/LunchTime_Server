package dao;
import com.sun.rowset.CachedRowSetImpl;
import utils.DbConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class VendorDaoImpl extends UnicastRemoteObject implements VendorDao {
    Connection cn= DbConnection.myConnection();
    public VendorDaoImpl() throws RemoteException, SQLException {
        super();
    }

    @Override
    public Boolean checkVendor(String vendor_email, String password) throws RemoteException {
        try{
            String sql="SELECT  vendor_id, vendor_email, password from vendor where vendor_email=? and password=?";
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

    @Override
    public ResultSet getVendorInfo(String email) throws RemoteException {
        try{
            String sql="SELECT vendor_id FROM vendor WHERE vendor_email =?";
            PreparedStatement ps=cn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs= ps.executeQuery();
            CachedRowSetImpl crc= new CachedRowSetImpl();
            crc.populate(rs);
            return crc;
        }
        catch (Exception e){
            System.out.println("Exception "+e);

        }
        return null;
    }

    @Override
    public void updateVendorProfile(int vendor_id, String vendor_name, String vendor_email, String vendor_number, String picture) throws RemoteException {
            try{
                String sql= "UPDATE vendor SET vendor_name= ?, vendor_email=?, vendor_number=?, picture=? WHERE vendor_id =?";
                PreparedStatement ps =cn.prepareStatement(sql);
                ps.setString(1, vendor_name);
                ps.setString(2, vendor_email);
                ps.setString(3, vendor_number);
                ps.setString(4, picture);
                ps.setInt(5, vendor_id);
                ps.executeUpdate();
            }catch(Exception e){
                System.out.println(e);
            }
    }

    @Override
    public ResultSet getInfo(int vendor_id) throws RemoteException {
        try{
            Statement statement = cn.createStatement();
            ResultSet getInfo = statement.executeQuery("SELECT vendor_email, picture, vendor_name , vendor_number " +
                    "FROM vendor WHERE vendor_id ='" + vendor_id + "'");

            CachedRowSetImpl crs = new CachedRowSetImpl();
            crs.populate(getInfo);
            return crs;
        }
        catch(Exception e){
            System.out.print("Exception: "+e);
        }

        return null;
    }

    @Override
    public ResultSet getAllVendorInfo() throws RemoteException {

        try{
            String sql="select * from vendor";
            PreparedStatement ps=cn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

            CachedRowSetImpl crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;

        }catch (Exception e){
            System.out.println(e);

        }
        return null;
    }


}
