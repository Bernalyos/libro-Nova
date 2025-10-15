
package com.codeup.libronova.Service;

import com.codeup.libronova.Repository.MemberRepository;
import com.codeup.libronova.domain.Member;
import com.codeup.libronova.exception.BusinessException;
import com.codeup.libronova.exception.PersistenceException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public class MemberServiceImpl implements MemberService{ 
        
        
        
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberServiceImpl() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void save(Member member) throws BusinessException {
        // Lógica de negocio: Por ejemplo, podrías validar el campo 'name' aquí
        if (member.getName() == null || member.getName().trim().isEmpty()) {
            throw new BusinessException("Member name cannot be empty.");
        }
        
        try {
            memberRepository.save(member);
        } catch (PersistenceException e) {
            throw new BusinessException("Failed to save member: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Member member) throws BusinessException {
        if (member.getId() <= 0) {
            throw new BusinessException("Cannot update member: ID is required.");
        }
        
        try {
            memberRepository.update(member);
        } catch (PersistenceException e) {
            throw new BusinessException("Failed to update member: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Member> findById(int id) throws BusinessException {
        try {
            return memberRepository.findById(id);
        } catch (PersistenceException e) {
            throw new BusinessException("Error finding member by ID: " + id, e);
        }
    }

    @Override
    public List<Member> findAll() throws BusinessException {
        try {
            return memberRepository.findAll();
        } catch (PersistenceException e) {
            throw new BusinessException("Error retrieving all members.", e);
        }
    }

    @Override
    public List<Member> findActiveMembers() throws BusinessException {
        try {
            // Nota: El método en tu repositorio lanza SQLException.
            // Para mantener la coherencia, asumo que lo corregirás para lanzar PersistenceException,
            // y lo capturo aquí como tal.
            return memberRepository.findActiveMembers(); 
        } catch (Exception e) { 
            // Capturo Exception si no se ha corregido el repositorio, pero idealmente sería PersistenceException
            if (e instanceof PersistenceException) {
                 throw new BusinessException("Error retrieving active members.", e);
            }
            throw new BusinessException("An unexpected error occurred while listing active members.", e);
        }
    }

    @Override
    public void delete(int id) throws BusinessException {
        try {
            memberRepository.delete(id);
        } catch (PersistenceException e) {
            throw new BusinessException("Failed to logically delete member with ID " + id, e);
        }
    }
   @Override
    public void deleteById(int id) throws PersistenceException {
            memberRepository.deleteById(id);
}
}

