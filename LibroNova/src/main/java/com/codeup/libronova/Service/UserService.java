/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.codeup.libronova.Service;

import com.codeup.libronova.domain.User;
import com.codeup.libronova.exception.PersistenceException;

/**
 *
 * @author yoshi
 */
public interface UserService {
    
    User login(String username, String password) throws PersistenceException;

}
