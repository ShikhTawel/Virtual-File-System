package com.Accounts;

import com.FileSystem.Directory;

import java.util.ArrayList;

public class Admin extends Account {
    public ArrayList<User> users;

    public Admin(String username, String password, ArrayList<User> users) {
        super(username, password);
        this.users = users;
    }

    public void createUser(String[] args) {
        if (args.length == 3) {
            createUser(args[1], args[2]);
        } else {
            System.out.println("Invalid Arguments");
        }
    }

    public void createUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                System.out.println("User already registered");
                return;
            }
        }
        User user = new User(username, password);
        users.add(user);
    }

    public void grantAccess(String[] args, Directory root) {
        if (args.length == 4) {
            String[] path = args[2].split("/");
            if (path[path.length - 1].contains(".")) {
                System.out.println("Can't grant access to files");
                return;
            }
            Directory cur = root;
            if (!path[0].equalsIgnoreCase("root")) {
                System.out.println("Path Not found");
                return;
            }
            for (int i = 0; i < path.length - 1; i++) {
                if (path[i].equalsIgnoreCase(cur.getDirectoryName())) {
                    if (cur.searchForSubDirectory(path[i + 1]) != null) {
                        cur = cur.searchForSubDirectory(path[i + 1]);
                    } else {
                        System.out.println("Path Not Found");
                        return;
                    }
                } else {
                    System.out.println("Path Not Found");
                    return;
                }
            }
            grantAccess(args[1], cur, args[3]);
        } else {
            System.out.println("Invalid Arguments");
        }
    }

    public void grantAccess(String username, Directory directory, String access) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                if (access.equalsIgnoreCase("00")) {
                    directory.grantAccess(false, false, username);
                } else if (access.equalsIgnoreCase("11")) {
                    directory.grantAccess(true, true, username);
                } else if (access.equalsIgnoreCase("10")) {
                    directory.grantAccess(true, false, username);
                } else {
                    directory.grantAccess(false, true, username);
                }
                return;
            }
        }
        System.out.println("User Not Found");
    }
}