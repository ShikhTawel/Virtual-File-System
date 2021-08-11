package com;

import com.Accounts.*;
import com.Allocation.*;
import com.FileSystem.*;

import java.util.*;

public class Main {
    private static Directory root;
    private static final ArrayList<User> users = new ArrayList<>();
    private static final Admin admin = new Admin("admin", "admin", users);
    private static Account loggedInAccount = admin;
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] arg) {
        System.out.println("Enter the allocation type:");
        System.out.println("1- Contiguous Allocation" +
                "\n2- Linked Allocation" +
                "\nelse- Indexed Allocation");
        int allocationType = in.nextInt();
        System.out.println("Enter the disk size (in KB): ");
        int N = in.nextInt();
        if (allocationType == 1) {
            root = new Directory("root", new ContiguousAllocator(N));
        } else if (allocationType == 2) {
            root = new Directory("root", new LinkedAllocator(N));
        } else {
            root = new Directory("root", new IndexedAllocator(N));
        }
        in.skip("\n");
        root.grantAccess(true, true, "admin");
        while (true) {
            System.out.print("Enter command: ");
            String command = in.nextLine();
            String[] args = command.split(" ");
            if (command.equalsIgnoreCase("exit"))
                break;
            else if (args[0].equalsIgnoreCase("Login"))
                login(args);
            else if (args[0].equalsIgnoreCase("CreateFile"))
                loggedInAccount.createFile(args, root);
            else if (args[0].equalsIgnoreCase("CreateFolder"))
                loggedInAccount.createFolder(args, root);
            else if (args[0].equalsIgnoreCase("DeleteFile"))
                loggedInAccount.deleteFile(args, root);
            else if (args[0].equalsIgnoreCase("DeleteFolder"))
                loggedInAccount.deleteFolder(args, root);
            else if (args[0].equalsIgnoreCase("DisplayDiskStatus"))
                loggedInAccount.displayDiskStatus(args, root);
            else if (args[0].equalsIgnoreCase("DisplayDiskStructure"))
                loggedInAccount.displayDiskStructure(args, root);
            else if (args[0].equalsIgnoreCase("TellUser"))
                loggedInAccount.tellUser(args);
            else if (args[0].equalsIgnoreCase("Grant"))
                grantAccess(args);
            else if (args[0].equalsIgnoreCase("CUser"))
                createUser(args);
            else
                System.out.println("Invalid Option!");
        }
    }

    private static void login(String[] args) {
        if (args.length == 3) {
            String username = args[1];
            String password = args[2];
            if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
                loggedInAccount = admin;
                return;
            } else {
                for (User user : admin.users) {
                    if (user.getUsername().equalsIgnoreCase(username)) {
                        if (user.getPassword().equalsIgnoreCase(password)) {
                            loggedInAccount = user;
                            return;
                        }
                    }
                }
            }
            System.out.println("Username and password don't match");
        } else {
            System.out.println("Invalid arguments");
        }
    }

    private static void createUser(String[] args) {
        if (!loggedInAccount.getUsername().equalsIgnoreCase("admin")) {
            System.out.println("Can't create user");
            return;
        }
        admin.createUser(args);
    }

    private static void grantAccess(String[] args) {
        if (!loggedInAccount.getUsername().equalsIgnoreCase("admin")) {
            System.out.println("Can't grant access");
            return;
        }
        admin.grantAccess(args, root);
    }
}