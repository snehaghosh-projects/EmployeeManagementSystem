package com.ems.model;

public class User {

    private int    userId;
    private String username;
    private String password;
    private String role;       // "ADMIN" or "EMPLOYEE"
    private boolean active;

    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role     = role;
        this.active   = true;
    }

    public int     getUserId()          { return userId; }
    public void    setUserId(int v)     { this.userId = v; }

    public String  getUsername()        { return username; }
    public void    setUsername(String v){ this.username = v; }

    public String  getPassword()        { return password; }
    public void    setPassword(String v){ this.password = v; }

    public String  getRole()            { return role; }
    public void    setRole(String v)    { this.role = v; }

    public boolean isActive()           { return active; }
    public void    setActive(boolean v) { this.active = v; }

    public boolean isAdmin()            { return "ADMIN".equals(role); }
}
