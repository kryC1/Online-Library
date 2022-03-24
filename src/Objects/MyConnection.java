package Objects;

import Objects.Member;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author red_k
 */
public class MyConnection {
    
    public static Connection getConnection(){
     
       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ceng201_project", "root", "qrlBnm60");
            return con;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        
         
    }
    
    public static boolean userLogin(String uType, String uName ,String password) throws SQLException{
        boolean check = false;
        PreparedStatement  ps;
        ResultSet rs;
        String query = "";
        
        if(uType.equals("member"))
            query = "SELECT * FROM `members` WHERE `U_NAME` =? AND `PASSWORD` =?";
        else if(uType.equals("admin"))
            query = "SELECT * FROM `admins` WHERE `U_NAME` =? AND `PASSWORD` =?";
        
        try {
            ps = getConnection().prepareStatement(query);
            ps.setString(1, uName);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next())
                check = true;       
            else
                JOptionPane.showMessageDialog(null, "şifre hatalı");
            return check;
        } catch (SQLException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
            return check;
        }finally{
            getConnection().close();
        }
    
    }
    
    public static void getPicInfo(String str, int ID){
        try {
            URL url = new URL(str);
            InputStream is = url.openStream();
            FileOutputStream fo = new FileOutputStream(Integer.toString(ID) + ".jpg");

            int b = 0;

            while((b = is.read()) != -1){
                fo.write(b);
            }

            fo.close();
            is.close();

        } catch (MalformedURLException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getPic(String str){
        int IDS = 0;
        String urlS = "";
        String query = "";

        try {
            query = "SELECT * FROM books WHERE(title = '" + str + "')";
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                IDS = rs.getInt("ID"); 
                urlS = rs.getString("img_url");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        getPicInfo(urlS, IDS);
    }
    
    public static String bookPage(int ID){
        Desktop d = null;
        String query = "SELECT * FROM books WHERE(ID = '" + ID + "')";
        String str  = "";
        
        try {
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next())
                str = rs.getString("info_url");
        } catch (SQLException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return str; 
    }
}
