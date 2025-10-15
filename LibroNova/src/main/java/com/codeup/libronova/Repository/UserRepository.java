
package com.codeup.libronova.Repository;

import com.codeup.libronova.domain.User;
import com.codeup.libronova.exception.PersistenceException;

/**
 *
 * @author yoshi
 */
public interface UserRepository {
    
     User login(String username, String password) throws PersistenceException;
}

