
package com.codeup.libronova.Service;

import com.codeup.libronova.Repository.UserRepository;
import com.codeup.libronova.Repository.UserRepositoryImpl;
import com.codeup.libronova.domain.User;
import com.codeup.libronova.exception.PersistenceException;

/**
 *
 * @author yoshi
 */
public class UserServiceImpl implements UserService {
    
    
     private final UserRepository repo = new UserRepositoryImpl ();

    public UserServiceImpl() {
        // Constructor por defecto para inicializar el repositorio
    }

    public UserServiceImpl(UserRepository userRepo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
     
    @Override
    public User login(String username, String password) throws PersistenceException {
         return repo.login(username, password);
    }
    
    
}
