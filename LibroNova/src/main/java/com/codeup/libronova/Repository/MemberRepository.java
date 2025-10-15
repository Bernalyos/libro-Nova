/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.codeup.libronova.Repository;

import com.codeup.libronova.domain.Member;
import com.codeup.libronova.exception.PersistenceException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public interface MemberRepository {
    
    
      void save(Member member) throws PersistenceException;

    void update(Member member) throws PersistenceException;

    void delete(int id) throws PersistenceException;

    Optional<Member> findById(int id) throws PersistenceException;

    List<Member> findAll() throws PersistenceException;

    List<Member> findActiveMembers() throws PersistenceException;

    void deleteById(int id) throws PersistenceException;
}

