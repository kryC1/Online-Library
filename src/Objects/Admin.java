package Objects;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author red_k
 */
public class Admin extends User {

    public Admin(String uName, String password) {
        super(uName, password);
    }

    @Override
    public ArrayList<Admin> getUserList(String uName, int uSize) {
        ArrayList<Admin> usersList = new ArrayList<>();
        Admin user;
        String query = "";
        
                if(uSize == 1)
                    query = "SELECT * FROM admins";
                else if(uSize == 2)
                    query = "SELECT * FROM admins WHERE(U_NAME = '" + uName + "')";
        
        try {
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()){
                user = new Admin(rs.getString("U_NAME"), rs.getString("PASSWORD"));
                usersList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usersList;
    }    
    @Override
    public boolean checkUsername(String uName){
        PreparedStatement ps;
        ResultSet rs;
        boolean checkUser = false;
        String query = "";
        
            query = "SELECT * FROM `admins` WHERE `U_NAME` =?";

        
        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            
            ps.setString(1, uName);
            
            rs = ps.executeQuery();
            
            if(rs.next())
                checkUser = true;
        } catch (SQLException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return checkUser;
    }
    @Override
    public boolean changePassword(String password) throws SQLException{
        String query = "UPDATE `admin` SET password = '"  + password +"' WHERE u_name = '" + uName + "'"; 
        PreparedStatement preparedStmt = null;
        try{
            preparedStmt = MyConnection.getConnection().prepareStatement(query);
            preparedStmt.executeUpdate();
            this.password = password;
            return true;
        }catch(SQLException exception){
            System.out.println(exception);
            return false;
        }
        finally{
            preparedStmt.close();
           
        }
    }

    public Admin getCurrentUser(String uName){
        Admin currentAdmin = null;
        String query = "SELECT * FROM admins WHERE(U_NAME = '" + uName + "')";
        
        try {
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            
            currentAdmin = new Admin(rs.getString("U_NAME"), rs.getString("PASSWORD"));
        } catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return currentAdmin;
    }
    
}
