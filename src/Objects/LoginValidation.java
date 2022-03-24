/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.sql.SQLException;

/**
 *
 * @author red_k
 */
public interface LoginValidation {
    
    boolean checkUsername(String uName);
    boolean changePassword(String password)throws SQLException;
    
}
