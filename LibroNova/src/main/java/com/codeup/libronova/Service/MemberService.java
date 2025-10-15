/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.codeup.libronova.Service;

import com.codeup.libronova.domain.Member;
import com.codeup.libronova.exception.BusinessException;
import com.codeup.libronova.exception.PersistenceException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public interface MemberService {
    
    
    void save(Member member) throws BusinessException;

    void update(Member member) throws BusinessException;

    Optional<Member> findById(int id) throws BusinessException;

    List<Member> findAll() throws BusinessException;

    List<Member> findActiveMembers() throws BusinessException;

    void delete(int id) throws BusinessException;
    
    void deleteById(int id) throws PersistenceException;
}

