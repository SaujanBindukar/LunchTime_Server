/**
 * @author Saujan Bindukar
 * This is the implementation of the interface UserOrderDao.
 */
package dao;
import com.sun.rowset.CachedRowSetImpl;
import utils.DbConnection;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDate;

public class UserOrderDaoImpl extends UnicastRemoteObject implements UserOrderDao {
    /** Database Connection */
    Connection cn= DbConnection.myConnection();

    /** Object for serialization of ResultSet. */
    CachedRowSetImpl crs= new CachedRowSetImpl();

    private static final long serialVersionUID = -7273230871957691871L;
    /** Constructor*/
    public UserOrderDaoImpl() throws RemoteException, SQLException {
    }

    /**
     * Fetch all the food orders of the user.
     * @return
     * @throws RemoteException
     * @throws SQLException
     */
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
            crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        }catch(Exception e){
            System.out.print(e);
        }
        return null;
    }

    /**
     * Updates the order status of food using the order_id.
     * @param order_id
     * @param status
     * @throws RemoteException
     */
    @Override
    public void updateStatus(int order_id, String status) throws RemoteException {
        try{
            String sql= "UPDATE user_order SET status= '"+status+"' WHERE order_id =?";
            PreparedStatement ps =cn.prepareStatement(sql);
            ps.setInt(1, order_id);
            ps.executeUpdate();
        }
        catch (Exception e){
            System.out.print(e);
        }
    }

    /**
     * Fetch the user order using the dates.
     * @param initialDate
     * @param finalDate
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getUserOrderByDate(LocalDate initialDate, LocalDate finalDate) throws RemoteException {
        try{
            String sql = "select user_order.order_id,user_order.quantity," +
                    " user_order.total_price,user_order.status,user_order.date, menu.food_name, user.first_name" +
                    " FROM user_order " +
                    "INNER JOIN menu ON user_order.food_id= menu.food_id " +
                    "INNER JOIN user ON user_order.id=user.id where date between '"+initialDate+"' and '"+finalDate+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            crs = new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Fetch the user order of specific user by using the name of user as search parameter.
     * @param firstName
     * @return
     * @throws RemoteException
     */
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
            crs = new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Count the total sum of sales if the order status is Received only.
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getSalesDetail() throws RemoteException {
        try{
            ResultSet rs= cn.createStatement().executeQuery("SELECT date, sum(total_price) total_price " +
                    "FROM user_order  " +
                    "where status='Received' group by date ");
            crs = new CachedRowSetImpl();
            crs.populate(rs);
            return  crs;
        }catch(Exception e){
            System.out.print(e);
        }
        return null;
    }

    /**
     * Get the total sales by using the dates.
     * @param initialDate
     * @param finalDate
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getSalesDetailByDate(LocalDate initialDate, LocalDate finalDate) throws RemoteException {
        try{
            ResultSet rs= cn.createStatement().executeQuery("SELECT date, sum(total_price) total_price" +
                    "                    FROM user_order " +
                    "                    where status='Received' and date between '"+initialDate+"' and '"+finalDate+"' group by date  ");
            crs = new CachedRowSetImpl();
            crs.populate(rs);
            return  crs;
        }catch(Exception e) {
            System.out.print(e);
        }
        return null;
    }

    /**
     * Count the total sales of food order till date.
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getTotalSales() throws RemoteException {
        try{
            ResultSet rs= cn.createStatement().executeQuery("SELECT sum(total_price) as total_price FROM user_order ");
            CachedRowSetImpl crc = new CachedRowSetImpl();
            crc.populate(rs);
            return  crc;
        }catch(Exception e){
            System.out.print(e);
        }
        return null;
    }

    /**
     * Get the highest number of food sold.
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getFoodPreference() throws RemoteException {
        try{
            ResultSet rs= cn.createStatement().executeQuery("SELECT menu.food_name,SUM(user_order.quantity) as quantity " +
                    "FROM user_order " +
                    "INNER JOIN menu on user_order.food_id=menu.food_id " +
                    "GROUP by user_order.food_id ");
            CachedRowSetImpl crc = new CachedRowSetImpl();
            crc.populate(rs);
            return  crc;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     *
     * @param initialDate
     * @param finalDate
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getFoodPreferenceByDate(LocalDate initialDate, LocalDate finalDate) throws RemoteException {
        try{
            ResultSet rs= cn.createStatement().executeQuery("SELECT menu.food_name,SUM(user_order.quantity)" +
                    "                    FROM user_order" +
                    "                    INNER JOIN menu on user_order.food_id=menu.food_id\n" +
                    "                    WHERE date BETWEEN '"+initialDate+"' AND '"+finalDate+"'" +
                    "                    GROUP by user_order.food_id");
            CachedRowSetImpl crc = new CachedRowSetImpl();
            crc.populate(rs);
            return  crc;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Get the detail of student spending the highest number of canteen coins.
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getTopUser() throws RemoteException {
        try{
            String sql= "SELECT id, sum(total_price) as total_price from user_order GROUP by id " +
                    "ORDER BY total_price DESC LIMIT 5";
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

    /**
     * et the detail of student spending the highest number of canteen coins using the dates.
     * @param initialDate
     * @param finalDate
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getTopUserByDate(LocalDate initialDate, LocalDate finalDate) throws RemoteException {
        try{
            String sql= "SELECT id, sum(total_price) as total_price from user_order WHERE date BETWEEN '"+initialDate+"' and '"+finalDate+"'  GROUP by id ORDER BY total_price DESC LIMIT 5";
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

    /**
     * Fetch the order of the user if the order status is pending only.
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getPendingOrder() throws RemoteException {
        try{
            String sql = "select user_order.order_id ,user_order.quantity, user_order.total_price," +
                    " user_order.status,user_order.date, menu.food_name," +
                    " user.first_name, user.last_name " +
                    "FROM user_order " +
                    "INNER JOIN menu ON user_order.food_id= menu.food_id " +
                    "INNER JOIN user ON user_order.id=user.id " +
                    "where status='Pending'";
            PreparedStatement ps= cn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Fetch the user order using the current date.
     * @return
     * @throws RemoteException
     */
    @Override
    public ResultSet getTodaysOrder() throws RemoteException {
        try{
            String sql= "select user_order.order_id ,user_order.quantity, user_order.total_price, " +
                    "user_order.status,user_order.date, menu.food_name, user.first_name, user.last_name " +
                    "FROM user_order " +
                    "INNER JOIN menu ON user_order.food_id= menu.food_id " +
                    "INNER JOIN user ON user_order.id=user.id " +
                    "where date=CURRENT_DATE";
            PreparedStatement ps= cn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            crs= new CachedRowSetImpl();
            crs.populate(rs);
            return crs;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }


}
