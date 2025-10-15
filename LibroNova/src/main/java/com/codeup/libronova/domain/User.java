
package com.codeup.libronova.domain;

/**
 *
 * @author Coder
 */
public class User {
    
    private int id;
    private String username;
    private String password;
    private Rol rol;
    
    public enum Rol{
       ADMIN, USER
    }
    
    
    public User ( int id, String username, String password, Rol rol){
        
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
         
    }
    
    public int getId(){ return id;}
    public void setId( int id){ this.id = id;}
    
    public String getUser(){ return username;}
    public void setUserNam( String unsername){ this.username = username;}
    
    public String getPassword(){ return password;}
    public void setPasdword(){ this.password = password;}
    
    public Rol getRol(){ return rol;}
    public void setRol( Rol rol){ this.rol = rol;}
    
}
