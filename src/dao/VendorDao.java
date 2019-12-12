package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.Date;

public interface VendorDao extends Remote  {
    Boolean checkVendor(String vendor_email, String password) throws RemoteException ;
    ResultSet getVendorInfo(String email) throws RemoteException;
    void updateVendorProfile(int vendor_id, String vendor_name, String vendor_email, String vendor_number, String picture) throws RemoteException;
    ResultSet getInfo(int vendor_id) throws RemoteException;

}
