package com.Accounts;

import com.FileSystem.Directory;

public abstract class Account {
    private final String username;
    private final String password;

    Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private boolean canCreate(Directory directory) {
        return directory.hasCreateAccess(username);
    }

    private boolean canDelete(Directory directory) {
        return directory.hasDeleteAccess(username);
    }

    public void createFolder(String[] args, Directory root) {
        if (args.length == 2) {
            String[] path = args[1].split("/");
            Directory directory = getDirectoryFromPath(path, root);
            if (directory == null) {
                System.out.println("Path Not Found");
                return;
            }
            if (canCreate(directory)) {
                directory.createFolder(path[path.length - 1]);
            }
            else
                System.out.println("User doesn't have permission to create");
        } else {
            System.out.println("Invalid Arguments");
        }
    }

    public void createFile(String[] args, Directory root) {
        if (args.length == 3) {
            String[] path = args[1].split("/"); // split the path by '/' characters
            Directory directory = getDirectoryFromPath(path, root);
            if (directory == null) {
                System.out.println("Path Not Found");
                return;
            }
            String size = args[args.length - 1]; // the size of the new file
            for (int i = 0; i < size.length(); i++) {
                if (!Character.isDigit(size.charAt(i))) { // size must only contain digits
                    System.out.println("Size is not valid");
                    return;
                }
            }
            if (canCreate(directory))
                directory.createFile(path[path.length - 1], Integer.parseInt(size)); // create the new file
            else
                System.out.println("User doesn't have permission to create");
        } else {
            System.out.println("Invalid Arguments");
        }
    }

    public void deleteFile(String[] args, Directory root) {
        if (args.length == 2) {
            String[] path = args[1].split("/");
            Directory directory = getDirectoryFromPath(path, root);
            if (directory == null) {
                System.out.println("Path Not Found");
                return;
            }
            if (canDelete(directory))
                directory.deleteFile(path[path.length - 1]); // delete the specified file
            else
                System.out.println("User doesn't have permission to delete");
        } else {
            System.out.println("Invalid Arguments");
        }
    }

    public void deleteFolder(String[] args, Directory root) {
        if (args.length == 2) {
            String[] path = args[1].split("/");
            Directory directory = getDirectoryFromPath(path, root);
            if (directory == null) {
                System.out.println("Path Not Found");
                return;
            }
            if (canDelete(directory))
                directory.deleteFolder(path[path.length - 1]); // delete the specified folder
            else
                System.out.println("User doesn't have permission to delete");
        } else {
            System.out.println("Invalid Arguments");
        }
    }

    private Directory getDirectoryFromPath(String[] path, Directory root) {
        Directory cur = root;
        if (!path[0].equalsIgnoreCase("root")) {
            return null;
        }
        for (int i = 0; i < path.length - 2; i++) {
            if (path[i].equalsIgnoreCase(cur.getDirectoryName())) {
                if (cur.searchForSubDirectory(path[i + 1]) != null) {
                    cur = cur.searchForSubDirectory(path[i + 1]);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return cur;
    }

    public void displayDiskStatus(String[] args, Directory root) {
        if (args.length == 1) {
            System.out.println("1- Empty Space: " + root.getAllocator().getEmptySpace());
            System.out.println("2- Allocated Space: " + root.getAllocator().getAllocatedSpace());
            System.out.println("3- Empty Blocks in Disk (0 For Empty): " + root.getAllocator().getSpace());
            System.out.println("4- Allocated Blocks in Disk for Each File:- ");
            root.displayDiskStatus();
        } else {
            System.out.println("Invalid Arguments");
        }
    }

    public void displayDiskStructure(String[] args, Directory root) {
        if (args.length == 1) {
            root.displayDiskStructure(0);
        } else {
            System.out.println("Invalid Arguments");
        }
    }

    public void tellUser(String[] args) {
        if (args.length == 1) {
            System.out.print("Current User: ");
            System.out.println(username);
        } else {
            System.out.println("Invalid Arguments");
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}