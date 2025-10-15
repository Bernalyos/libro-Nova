
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
    
    
// ...existing code...
    public User(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        if (rol != null) {
            this.rol = Rol.valueOf(rol.trim().toUpperCase());
        }
    }
// ...existing code...
    
    public int getId(){ return id;}
    public void setId( int id){ this.id = id;}
    
    public String getUserName(){ return username;}
    public void setUserName( String unsername){ this.username = username;}
    
    public String getPassword(){ return password;}
    public void setPasdword(){ this.password = password;}
    
    public Rol getRol(){ return rol;}
    public void setRol( Rol rol){ this.rol = rol;}
    
}
