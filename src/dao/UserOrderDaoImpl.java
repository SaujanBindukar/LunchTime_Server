package dao;

import com.sun.rowset.CachedRowSetImpl;
import utils.DbConnection;

import javax.sql.rowset.CachedRowSet;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class UserOrderDaoImpl extends UnicastRemoteObject implements UserOrderDao {
    Connection cn= DbConnection.myConnection();
    CachedRowSetImpl crs= new CachedRowSetImpl();
    private static final long serialVersionUID = -7273230871957691871L;
    public UserOrderDaoImpl() throws RemoteException, SQLException {
    }

    @Override
    public ResultSet getUserOrder() throws RemoteException, SQLException{
        try{
            ResultSet rs= cn.createStatement().executeQuery(
                    "select user_order.order_id,user_order.quantity,user_order.date, " +
                            " user_order.total_price, user_order.status, menu.food_name, " +
                            "user.first_name, user.last_name " +
                            "FROM user_order " +
                            "INNER JOIN menu ON user_order.food_id= menu.food_id " +
                            "INNER JOIN user ON user_order.id=user.id");

            CachedRowSetImpl crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;

        }catch(Exception e){
            System.out.print(e);
        }
        return null;
    }

    @Override
    public void updateStatus(int order_id) throws RemoteException {
        try{
            String sql= "UPDATE user_order SET status= 'Received' WHERE order_id =?";
            PreparedStatement ps =cn.prepareStatement(sql);
            ps.setInt(1, order_id);
            ps.executeUpdate();
        }
        catch (Exception e){
            System.out.print(e);
        }
    }

    @Override
    public ResultSet getUserOrderByDate(LocalDate initialDate, LocalDate finalDate) throws RemoteException {
        try{
            String sql = "select user_order.order_id,user_order.quantity, user_order.total_price,user_order.status,user_order.date, menu.food_name, user.first_name" +
                    " FROM user_order " +
                    "INNER JOIN menu ON user_order.food_id= menu.food_id " +
                    "INNER JOIN user ON user_order.id=user.id where date between '"+initialDate+"' and '"+finalDate+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            crs.populate(rs);
            return crs;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public ResultSet getUserOrderByName(String firstName) throws RemoteException {
        try{
            String sql = "select user_order.order_id ,user_order.quantity, user_order.total_price," +
                    " user_order.status,user_order.date, menu.food_name," +
                    " user.first_name, user.last_name " +
                    "FROM user_order " +
                    "INNER JOIN menu ON user_order.food_id= menu.food_id " +
                    "INNER JOIN user ON user_order.id=user.id " +
                    "where first_name=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, firstName);
            ResultSet rs = ps.executeQuery();
            crs.populate(rs);
            return crs;

        }catch (Exception e){
            System.out.println(e);

        }
        return null;
    }

    @Override
    public ResultSet getSalesDetail() throws RemoteException {
        try{
            ResultSet rs= cn.createStatement().executeQuery("SELECT date, sum(total_price) total_price " +
                    "FROM user_order  " +
                    "where status='Received' group by date ");
            CachedRowSetImpl crc = new CachedRowSetImpl();
            crc.populate(rs);
            return  crc;

        }catch(Exception e){

        }
        return null;
    }


}
