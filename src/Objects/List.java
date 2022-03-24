package Objects;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author S.nur
 */

public class List {
    private final ArrayList<Book> books = new ArrayList<Book>();  
    private final String listname;
    private final String u_name;
    
    List(String listName, String u_name){
        this.listname = listName;
        this.u_name = u_name; 
    }
    //listede aynı isimli bi kitap var mı?
    public static boolean findBook(List l, Book b){
        boolean flag = false;
        for(Book book : l.getBooks()){
             if(book.getTitle().equals(b.getTitle()))
                 flag = true;
             
        }
        return flag;
    }
    
    public static void db_to_memberLists(Member m, String listName) throws SQLException{
        Statement stm = null;
        ResultSet resultSet;
        m.getListByName(listName).getBooks().clear();
        try{
            stm = MyConnection.getConnection().createStatement();
            resultSet = stm.executeQuery("SELECT * FROM `book_lists`");
            while(resultSet.next()){
                if(resultSet.getString("list").equalsIgnoreCase(listName) && resultSet.getString("U_NAME").equals(m.uName)){
                    m.getListByName(listName).getBooks().add(new Book( resultSet.getInt("ID"), resultSet.getString("title"), resultSet.getString("author"), 
                         resultSet.getString("publisher"), resultSet.getString("category"), resultSet.getInt("score")));
                }
            }
                
        }catch(SQLException exception){
            System.out.println(exception);
            
        }
        finally{
            MyConnection.getConnection().close();        
        }
        
    }

    public static boolean addBooks(List l, Book b, List f) throws SQLException{
        if(findBook(l, b)){ 
            JOptionPane.showMessageDialog(null, b.getTitle() + " already in the list!");
            return false;
        }
        else{
            if(l.listname.equals("LIKED BOOKS") && List.findBook(f, b)==false){
                JOptionPane.showMessageDialog(null, "Okumadığınız kitabı beğenemezsiniz.");
                return false;
            }
            l.books.add(b);
            PreparedStatement preparedStmt = null;
            ResultSet resultSet;
            String query = "insert into `book_lists` (`title`, author, publisher, category, list, u_name, id, score)"  + " values (?, ?, ?, ?, ?, ?, ?, ?)";
                try{
                    preparedStmt = MyConnection.getConnection().prepareStatement(query);
                    preparedStmt.setString(1, b.getTitle());
                    preparedStmt.setString(2, b.getAuthor()); //variable ekle
                    preparedStmt.setString(3, b.getPublisher());
                    preparedStmt.setString(4, b.getCategory());
                    preparedStmt.setString(5, l.listname);
                    preparedStmt.setString(6, l.u_name);
                    preparedStmt.setInt(7, b.getID());
                    preparedStmt.setInt(8, b.getScore());
                    preparedStmt.executeUpdate();
                    System.out.println("Eklendi.");
                    return true;
                }catch(SQLException exception){   
                    System.out.println( exception);  
                    return false; 
                }
                finally{
                    preparedStmt.close();
                } 
           
        }   
    }
    
    public static boolean deleteBook(List l, Book b) {
        if(findBook(l, b)){
            PreparedStatement preparedStmt = null;
            ResultSet resultSet;
            String query = "delete from `book_lists` where `title` =  (?) AND u_name = (?)";

            try{
                l.books.remove(b);
                preparedStmt = MyConnection.getConnection().prepareStatement(query);
                preparedStmt.setString(1, b.getTitle());
                preparedStmt.setString(2, l.u_name);
                preparedStmt.execute();
                System.out.println("Deleted.");
                
                
                return true;

            }catch(SQLException exception){
                System.out.println( exception);   
                return false;
            }
            
        }
        return false;
        
    }
    
    public static void deleteBook_fromAllLists(int ID) throws SQLException{
            PreparedStatement preparedStmt = null;
            ResultSet resultSet;
            String query = "delete from `book_lists` where `ID` =  (?)";
            try{
                
                preparedStmt = MyConnection.getConnection().prepareStatement(query);
                preparedStmt.setInt(1, ID);
                preparedStmt.execute();
                System.out.println("Kitap tüm listelerden silindi.");

            }catch(SQLException exception){
                System.out.println( exception);   
                
            }
            finally{
                preparedStmt.close();
            } 
    }
    
    public static void deleteUser_fromAllLists(String uName) throws SQLException{
            PreparedStatement preparedStmt = null;
            PreparedStatement preparedStmt2 = null;
            ResultSet resultSet;
            String query = "delete from `book_lists` where `u_name` =  (?)";
            String query2 = "delete from `list_names` where `u_name` =  (?)";
            try{
                
                preparedStmt = MyConnection.getConnection().prepareStatement(query);
                preparedStmt.setString(1, uName);
                preparedStmt.execute();
                preparedStmt2 = MyConnection.getConnection().prepareStatement(query2);
                preparedStmt2.setString(1, uName);
                preparedStmt.execute();preparedStmt2.execute();
                System.out.println("User has been deleted from all lists.");

            }catch(SQLException exception){
                System.out.println( exception);   
                
            }
            finally{
                preparedStmt.close();
            } 
    }
    public ArrayList<Book> getBooks() {
        return books;
    }

    public String getListname() {
        return listname;
    }
   
}
