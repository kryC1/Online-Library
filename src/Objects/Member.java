package Objects;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author S.nur
 */

public class Member extends User {
    
    private String firstName, lastName;
    private int uScore_past;
    
    //MEMBERS' ALL LISTS
    private final ArrayList<List> allLists = new ArrayList<>();
    private final ArrayList<Member> friendList = new ArrayList<>();
    //DEFAULT LISTS
    private final List likedBooks =  new List("LIKED BOOKS", uName);
    private final List currentBooks = new List("CURRENTLY READING", uName);
    private final List finishedBooks  = new List("FINISHED BOOKS", uName);
    private final List goalBooks  = new List("GOAL BOOKS", uName);

    
    public Member(String uName, String fName, String lName, String password) throws SQLException {
        super(uName, password);
        this.firstName = fName;
        this.lastName = lName;        
    }
//kullanıcının tekrar puan vermeden önce aynı kitaba önceden kaç puan verdiğini bulur
    public int getPreviousScore_m(String title) {
       
        Statement st;
        String query2 =  "SELECT * FROM book_lists  WHERE `title` ='" + title + "' AND `u_name`= '" + uName + "' AND `list`= 'FINISHED BOOKS'";
       
        try {
            st = MyConnection.getConnection().createStatement(); 
            ResultSet rs = st.executeQuery(query2);
            while(rs.next())
                uScore_past = rs.getInt("score_u");
           
            return uScore_past;
        } catch (SQLException ex) {
            System.out.println(ex );
            return 0;
        }
       
    }
    //puan verildikten sonra book_lists'teki puanları senkronize eder.
    public void changeBookScore_MemberLists(String title, int uScore_now) {
        String query3 = " UPDATE book_lists SET `score_u` = '" + String.valueOf(uScore_now) + "' WHERE `title` ='" + title +"' AND `u_name`= '" + uName + "' AND `list`= 'FINISHED BOOKS'";
        PreparedStatement ps1; 
        try {
            ps1 = MyConnection.getConnection().prepareStatement(query3); 
            ps1.executeUpdate();
        } catch (SQLException ex) {System.out.println(ex);}  
    }
  
    public void deleteAllScores(){
        for(Book b: getListByName("FINISHED BOOKS").getBooks()){
            Book.bookList(b.getTitle(), 2).get(0).setScore(0, b.getTitle(), this);
            System.out.println("b.score: " + b.getScore());
        }
       
    }
    @Override
    public ArrayList<Member> getUserList(String uName, int uSize) {
        ArrayList<Member> usersList = new ArrayList<>();
        Member user;
        String query = "";
        
                if(uSize == 1)
                    query = "SELECT * FROM members";
                else if(uSize == 2)
                    query = "SELECT * FROM members WHERE (U_NAME = '" + uName + "') OR (F_NAME = '" + uName + "')";
                else if(uSize==3)
                    query = "SELECT * FROM members WHERE U_NAME = '" + uName + "'";
        
        try {
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()){
                user = new Member(rs.getString("U_NAME"), rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("PASSWORD"));
                usersList.add(user);
            }
        }catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usersList;
    }
    @Override
    public boolean checkUsername(String uName){
        PreparedStatement ps;
        ResultSet rs;
        boolean checkUser = false;
        String query = "";
            query = "SELECT * FROM `members` WHERE `U_NAME` =?";        
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
    
    public void getAllLists_from_db() throws SQLException{
        allLists.clear();
        Statement stm = null;
        Connection con = MyConnection.getConnection();
        ResultSet resultSet;     
        try{
            stm = con.createStatement();
            resultSet = stm.executeQuery("SELECT * FROM `list_names`");
            while(resultSet.next()){
                if(resultSet.getString("U_NAME").equals(uName)){
                    List newList = new List(resultSet.getString("list"), uName);
                    allLists.add(newList);
                }
            }     
            if(allLists.isEmpty())
                makeDefaultMemberLists();
        }catch(SQLException exception){System.out.println(exception );}
        finally{
            con.close();        
        }
    }
    
    public void makeDefaultMemberLists() throws SQLException{
        if(getUserList(uName, 3).get(0) != null){
            allLists.add(likedBooks);
            allLists.add(currentBooks);
            allLists.add(finishedBooks);
            allLists.add(goalBooks);

            String query2 = "insert into `list_names` (u_name, list)"  + " values (?, ?)"; 
            PreparedStatement preparedStmt = null;
            try{
                preparedStmt = MyConnection.getConnection().prepareStatement(query2);
                for(List l : allLists){                    
                    preparedStmt.setString(1,uName);
                    preparedStmt.setString(2, l.getListname());  
                    preparedStmt.executeUpdate();
                }
            }catch(SQLException exception){
                System.out.println(exception);
            }
            finally{
                preparedStmt.close();
            }

        }
        
    }
    
    public List getListByName(String listName){
        for(int i=0; i<allLists.size(); i++)
            if(allLists.get(i).getListname().equals(listName))
                return allLists.get(i);
        return null;
    } 
    
    public void makeNewList(String listName) throws SQLException{
        List newList = new List(listName, uName);
        this.allLists.add(newList);
        String query2 = "insert into `list_names` (u_name, list)"  + " values (?, ?)"; 
        PreparedStatement preparedStmt = null;
        try{
            preparedStmt = MyConnection.getConnection().prepareStatement(query2);
            preparedStmt.setString(1,uName);
            preparedStmt.setString(2, listName);  
            preparedStmt.executeUpdate();
            System.out.println("List_names'e eklendi.");
        }catch(SQLException exception){
            
        }
        finally{
            preparedStmt.close();
        }
    }
    
    public boolean isDefault(String listName){
        ArrayList<List> defaultLists = new ArrayList<>();
        defaultLists.add(likedBooks);
        defaultLists.add(goalBooks);
        defaultLists.add(finishedBooks);
        defaultLists.add(currentBooks);
        for(List l : defaultLists)
            if(l.getListname().equals(listName))
                return true;
        return false;
    }
    
    public boolean deleteList(String listName) { 
 PreparedStatement preparedStmt = null, preparedStmt2=null;
        if(getListByName(listName)!=null && isDefault(listName)==false){
            try{
                allLists.remove(getListByName(listName));
                ResultSet resultSet;
                String query = "delete from `list_names` where `list` =  (?) AND u_name = (?)";
                preparedStmt = MyConnection.getConnection().prepareStatement(query);
                preparedStmt.setString(1, listName);
                preparedStmt.setString(2, this.uName);
                preparedStmt.execute();
                System.out.println("Liste silindi.");
                String query2 = "delete from `book_lists` where `list` =  (?) AND u_name = (?)";
                preparedStmt2 = MyConnection.getConnection().prepareStatement(query2);
                preparedStmt2.setString(1, listName);
                preparedStmt2.setString(2, this.uName);
                preparedStmt2.execute();
                System.out.println("Kitaplar silindi.");
                return true;
            }
            catch(SQLException ex){
                System.out.println(ex);
                return false;
            }
            /*finally{
                preparedStmt.close();
                preparedStmt2.close();
            }*/
        }
        return false;
        
        
       
    }
    @Override
    public boolean changePassword(String password) throws SQLException{
        String query = "UPDATE `members` SET password = '"  + password +"' WHERE u_name = '" + uName + "'"; 
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
    
    public boolean changeUserInfo(String first_name, String last_name){
        String query = "UPDATE `members` SET f_name = '"  + first_name +"' WHERE u_name = '" + uName + "'"; 
        String query1 = "UPDATE `members` SET l_name = '"  + last_name +"' WHERE u_name = '" + uName + "'";
        PreparedStatement preparedStmt = null;
        PreparedStatement preparedStmt1 = null;
        try{
            preparedStmt = MyConnection.getConnection().prepareStatement(query);
            preparedStmt.executeUpdate();
            preparedStmt1 = MyConnection.getConnection().prepareStatement(query1);
            preparedStmt1.executeUpdate();
            
            this.firstName= first_name;
            this.lastName= last_name;
            return true;
            
        }catch(SQLException exception){
            System.out.println(exception);
            return false;
        }
        /*finally{
            preparedStmt.close();
            preparedStmt1.close();
           
        }*/
    }
    
    public ArrayList<List> getAllLists() {
        return allLists;
    }

    public String getfName() {
        return firstName;
    }

    public String getlName() {
        return lastName;
    }
    
    public boolean add_to_friends(Member f) {
        
        String query = "insert into `friends` (uName, friends)"  + " values (?, ?)"; 
        PreparedStatement preparedStmt = null;
        try{
            for(Member m : getFriendList()){
                if(m.getuName().equals(f.getuName())){
                    return false;
                }
            }
            friendList.add(f);
            preparedStmt = MyConnection.getConnection().prepareStatement(query);
            preparedStmt.setString(1,uName);
            preparedStmt.setString(2, f.uName);  
            preparedStmt.executeUpdate();
            
            return true;
        }catch(SQLException exception){
            System.out.println(exception);
            return false;
        }
    
    }
    
    public boolean deleteFriends(String friend, int size) {
        try {
            
            if(size == 2){
               boolean flag = false;
                for(Member m : getFriendList()){
                    if(m.getuName().equals(friend)){
                        flag  = true;
                        break;
                    }
                 }
                if(flag){
                   String query ="DELETE FROM friends WHERE uName = '" + uName + "' AND friends = '" + friend + "'";
                    PreparedStatement preparedStmt = MyConnection.getConnection().prepareStatement(query);
                    preparedStmt.execute();
                    System.out.println("Silindi.");
                    return true; 
                }
                return flag;
            }
            else if (size==1){
                PreparedStatement preparedStmt3;
                String query2 = "delete from `friends`  where `friends` = (?) OR `uName` = (?) ";
                preparedStmt3 = MyConnection.getConnection().prepareStatement(query2);
                preparedStmt3.setString(1, this.uName);
                preparedStmt3.setString(2, this.uName);
                preparedStmt3.execute();
                System.out.println("Tüm arkadaşlıklar silindi.");
                return true;
            }
          
        }catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
        return false;
    }

    public ArrayList<Member> getFriendList() {
        Statement stm = null;
        Connection con = MyConnection.getConnection();
        ResultSet resultSet; 
        friendList.clear();
        try{
            stm = con.createStatement();
            resultSet = stm.executeQuery("SELECT * FROM `friends` ");
            while(resultSet.next()){
                if(resultSet.getString("uName").equals(uName)){
                    Member f = getUserList(resultSet.getString("friends"), 2).get(0);
                    friendList.add(f);
                }
            }     
            
        }catch(SQLException exception){System.out.println(exception );}
        
        return friendList;
    }
    @Override
    public String toString(){
        return getuName() + " " + getfName() + " " + getlName() + "\n";
    }

}