package Objects;

import java.util.ArrayList;
import java.sql.*;
import java.util.Collections;

/**
 *
 * @author S.nur
 */
public class ReccommendedBooks{

     ArrayList<Book> tempBookList = new ArrayList<Book>();
     private final int max_size_temp = 20; //en fazla 20 tane random kitap oluşturulsun
     private final int max_size_rec = 7; //random kitaplardan 7'si önerilsin
     Member m;
    public ReccommendedBooks(Member m) {
        this.m = m;
    }
     
    public void addBookToTemp(){
        //temp list'te her türden kitap olmasını istediğimiz için kitap counter'ları 0.5 ayarladım. (örn: macera türünde hiç kitap okunulmasa da öneriye eklensin)
        //aşağıdaki tüm sayılar değiştirilebilir. ben beğeninin önerilmede baskın olmasını istedim.
        
        double count_b = 0.5; //bilim kurgu
        double count_f = 0.5; //fantastik
        double count_k = 0.5; //korku gerilim
        double count_m = 0.5; //macera
        double tot=0.0; 
        
        //bitmiş kitaplar arasından kitap türlerine göre counter artsın. (kitap beğenilmişse 1 eklensin, sadece bitmişse 0.2)
        for(Book b : m.getListByName("FINISHED BOOKS").getBooks()){
            if(b.getCategory().equals("Bilim Kurgu")){
                if(List.findBook(m.getListByName("LIKED BOOKS"), b))
                    count_b++;
                count_b+=0.2;
            }
                
            else if(b.getCategory().equals("Korku Gerilim")){
                if(List.findBook(m.getListByName("LIKED BOOKS"), b))
                    count_k++;
                count_k+=0.2;
                
            }
                
            else if(b.getCategory().equals("Macera")){
                if(List.findBook(m.getListByName("LIKED BOOKS"), b))
                    count_k++;
                count_m+=0.2;
                
            }
                
            else if(b.getCategory().equals("Fantastik")){
                if(List.findBook(m.getListByName("LIKED BOOKS"), b))
                    count_f++;
                count_f+=0.2;     
            }
                
            tot++;
        }
        //eğer bitirilmiş kitaplar boş değilse total counter'lar toplamına eşit olsun
        if(tot != 0.0){ 
            tot = count_b + count_k + count_m + count_f ;
            // ilk işlem "(count_b*100/tot)" hangi kategorinin öneri listesinde yüzde kaç etkili olacağını hesaplar
            // ikinci işlem "max_size_temp/100" temp listesinde belirtilen kategoriden kaç tane kitabın bulunacağını hesaplar
            int amountOf_b = (int) ((count_b*100/tot)*max_size_temp/100);
            int amountOf_k = (int) ((count_k*100/tot)*max_size_temp/100);
            int amountOf_m = (int) ((count_m*100/tot)*max_size_temp/100);
            int amountOf_f = (int) ((count_f*100/tot)*max_size_temp/100);
            
            //her türden kaç kitap bulunuyor:
            //System.out.println("Amount of: b k m f " + amountOf_b + amountOf_k + amountOf_m + amountOf_f);
            
            //belirlenen sayıda temp'e kitap eklenir. (kitap eklenirken puan etkilidir)
            addBooksTo_temp("Bilim Kurgu", amountOf_b);
            addBooksTo_temp("Fantastik", amountOf_f);
            addBooksTo_temp("Korku Gerilim", amountOf_k);
            addBooksTo_temp("Macera", amountOf_m); 
            
        }else{ 
            
            //daha önce hiç kitap okunmamışsa her kategoriden eşit sayıda kitap bulunan 
            //ve en çok oy alan kitaplardan oluşan bi random liste olsun. 4: tür sayısı
            
            addPopularBooksTo_temp("Bilim Kurgu", max_size_temp/4);
            addPopularBooksTo_temp("Fantastik", max_size_temp/4);
            addPopularBooksTo_temp("Korku Gerilim", max_size_temp/4);
            addPopularBooksTo_temp("Macera", max_size_temp/4);

        } 
        
    }
    public void addBooksTo_temp( String catg, int num){
        try{
            
            String query= "SELECT * from books WHERE (category = '" + catg + "') ORDER by score DESC" ;
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);  
            Book book;
            int i = 1;
            // bitmiş kitaplarda olmayan kitaplar temp'e eklenir
            while(rs.next() && i<=num){
                book = new Book(rs.getInt("ID"), rs.getString("title"), rs.getString("author"), rs.getString("publisher"), rs.getString("category"), rs.getInt("score"));
                if(List.findBook(m.getListByName("FINISHED BOOKS"), book)==false){
                    System.out.println(" b: " + book.getTitle());
                    tempBookList.add(book);
                    i++;
                }
  
            }
           
        }catch(SQLException ex){
            System.out.println(ex);    
        }
    }
    public ArrayList<Book> reccomendList(){
        ArrayList<Book> reccomendedList = new ArrayList<Book>();
        addBookToTemp();  
        ArrayList<Integer> a = new ArrayList<>();
        //temp listesinde bulunan kitap sayısı kadar oluşan numaralardan bir liste
        for (int i = 0; i < tempBookList.size(); i++){ 
             a.add(i);        
        } 
        Collections.shuffle(a);
     try{
         for(int i=0; i<max_size_rec; i++){
            reccomendedList.add(tempBookList.get(a.get(i))); //karıştırır
             
            }
        return reccomendedList;
     }
     catch(IndexOutOfBoundsException ex){
         System.out.println(ex);
         return tempBookList;
     }
        
    }
    public ArrayList<Book> mostLikedList(String category){
        ArrayList<Book> mostLikedList = new ArrayList<Book>();
        addPopularBooksTo_temp(category, 10); 
        return tempBookList;
    }
    public void addPopularBooksTo_temp( String catg, int num){
        try{
            String query="";
            if(catg == "All")
                 query= "SELECT * from books ORDER by score DESC" ;
            else
                query= "SELECT * from books WHERE (category = '" + catg + "') ORDER by score DESC" ;
            Statement st = MyConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            Book book;
            int i = 1;
            while(rs.next() && i<=num){
                book = new Book(rs.getInt("ID"), rs.getString("title"), rs.getString("author"), rs.getString("publisher"), rs.getString("category"), rs.getInt("score"));
                tempBookList.add(book);
                i++;
            }
            
           
        }catch(SQLException ex){
              System.out.println(ex); 
        }
    }
    
}
