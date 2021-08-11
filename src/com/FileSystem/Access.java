package com.FileSystem;

public class Access {
    public boolean create;
    public boolean delete;
    public String username;

    public Access(boolean create, boolean delete, String username) {
        this.create = create;
        this.delete = delete;
        this.username = username;
    }
}