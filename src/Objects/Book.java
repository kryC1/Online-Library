/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Book {
    
    private int ID, score;
    private String title, author, publisher, category;

    public Book(int ID, String title, String author, String publisher, String category, int score) {
        this.ID = ID;
        this.score = score;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
    }

    public int getID() {
        return ID;
    }

    public int getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCategory() {
        return category;
    }
    public static ArrayList<Book> bookList(String category, int num){
        ArrayList<Book> booksList = new ArrayList<>();
        String query = "";

        try {
            if(num == 1){
                if(category.equals("All"))
                    query = "SELECT * FROM books";
                else
                    query = "SELECT * FROM books WHERE(category = '" + category + "') ";
            }
            else if(num == 2){
                query = "SELECT * FROM books WHERE(title = '" + category + "') OR (author = '" + category + "') OR (publisher = '" + category + "') OR (ID = '" + category + "')";
            }

            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            Book book;

            while(rs.next()){
                book = new Book(rs.getInt("ID"), rs.getString("title"), rs.getString("author"), rs.getString("publisher"), rs.getString("category"), rs.getInt("score"));
                booksList.add(book);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return booksList;
    }
    
    public static void delBook(int ID){
        PreparedStatement ps;
        ResultSet rs;
        
        String query = "DELETE FROM `books` WHERE (`ID` = ?)";
        
        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, ID);
            
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Deletion Succesful!");
                List.deleteBook_fromAllLists(ID); //DELETE BOOK FROM ALL LIST
                
            }
            else
                JOptionPane.showMessageDialog(null, "Please check the book ID.");
            
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addBook(String str1, String str2, String str3, String str4){
        PreparedStatement ps;
        String query = "INSERT INTO `books` (`title`, `author`, `publisher`, `category`) VALUES (?, ?, ?, ?)";
        
        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            
            ps.setString(1, str1);
            ps.setString(2, str2);
            ps.setString(3, str3);
            ps.setString(4, str4);
            
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Succes! New book added.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//kitabın puanı değiştirilir
    public void setScore(int uScore_now, String title, Member m) {
        System.out.println("score_ö: " + this.score);
        int uScore_past= m.getPreviousScore_m(title); //önceki puan
        System.out.println("score_ö_u: " + uScore_past);
        this.score = this.score + uScore_now - uScore_past;
        System.out.println("score_n_u: " + uScore_now);
        System.out.println("score_n: " + this.score + "\n");
        PreparedStatement ps; 
        String query = " UPDATE books SET `score` = '" + String.valueOf(this.score) + "' WHERE `title` ='" + title + "'";
        try {
            ps = MyConnection.getConnection().prepareStatement(query);   
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Oy verildi.");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Oy verilemedi.");   
        }
        
        m.changeBookScore_MemberLists(title, uScore_now);
        PreparedStatement ps1; 
        String query1 = " UPDATE book_lists SET `score` = '" + String.valueOf(this.score) + "' WHERE `title` ='" + title + "'"; 
        try {
            ps1 = MyConnection.getConnection().prepareStatement(query1); 
            ps1.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);    
        }
    }
    
    public String toString(){
        return title + "\n";
    }
}