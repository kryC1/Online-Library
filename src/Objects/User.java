package Objects;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author red_k
 */
public abstract class User implements LoginValidation {
      protected String uName, password;

    public User(String uName, String password) {
        this.uName = uName;
        this.password = password;
    }     
    
    public abstract ArrayList<?> getUserList(String uName, int uSize);
    
    public static void registerUser(String uType, String uName, String fName, String lName, String password){      
        PreparedStatement ps;
        String query = "";
        
        if(uType.equals("Member"))
            query = "INSERT INTO `members` (`U_NAME`, `F_NAME`, `L_NAME`, `PASSWORD`) VALUES (?, ?, ?, ?)";
        else if(uType.equals("Admin"))
            query = "INSERT INTO `admins` (`U_NAME`, `F_NAME`, `L_NAME`, `PASSWORD`) VALUES (?, ?, ?, ?)";
        
        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            
            ps.setString(1, uName);
            ps.setString(2, fName);
            ps.setString(3, lName);
            ps.setString(4, password);
            
            if(ps.executeUpdate() > 0){
                Member m = new Member(uName, fName, lName, password);
                JOptionPane.showMessageDialog(null, "Succes! New user added.");
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "This user is alredy admin.");
        } 
        catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }      
    } 
    
    public static void delUser(String username, String usertype){
        PreparedStatement ps;
        ResultSet rs;
        String query = "";
        
        if(usertype.equals("Member"))
            query = "DELETE FROM `members` WHERE (`U_NAME` = '" + username + "')";
        else if(usertype.equals("Admin"))
            query = "DELETE FROM `admins` WHERE (`U_NAME` = '" + username + "')";
        
        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "User deleted.");
                List.deleteUser_fromAllLists(username); //DELETE USER FROM ALL LISTS
            }
            else
                JOptionPane.showMessageDialog(null, "Please check the username.");
            
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public String getuName() {
        return uName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
 
}
