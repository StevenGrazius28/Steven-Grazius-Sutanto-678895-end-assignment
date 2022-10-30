package com.example.JavaEndAssignmnet;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDb {
    public List<User> users;
    public UserDb(){
        users = Users();
    }

    private List<User> Users(){
        List <User> users= new ArrayList<>();
        users.add(new User(1,"Admin","Smith", LocalDate.of(1997, 4, 12), "Admin123",User.userStatus.admin));
        users.add(new User(2,"Jack","Tita", LocalDate.of(1997, 4, 12), "Emma123",User.userStatus.member));
        users.add(new User(3,"Michael","Jor", LocalDate.of(1997, 4, 12), "Emma123",User.userStatus.member));
        users.add(new User(4,"Lisa","BlkP", LocalDate.of(1997, 4, 12), "Emma123",User.userStatus.member));
        users.add(new User(5,"Mark","Fb", LocalDate.of(1997, 4, 12), "Emma123",User.userStatus.member));
        return  users;
    }
    public User getUser(String username,String password){
        for (User us: users) {
            if (Objects.equals(us.getFirstname(), username) && Objects.equals(us.getPassword(), password))
                return us;
        }
        return null;
    }
    public User GetbyId(int id){
        for (User u: users) {
            if(Objects.equals(u.getUserId(),id))
                return u;
        }
        return null;
    }
    public List<User> GetAllUser(){
        return users;
    }

    public void read() throws IOException {
        users.clear();
        FileInputStream fis = new FileInputStream(new File("users.dat"));
        ObjectInputStream ois = new ObjectInputStream(fis);
        while (true) {
            try {
                User u = (User) ois.readObject();
                users.add(u);
            } catch (EOFException eofe) {
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public  void write() {
        //open file Output Stream
        emptyfile();
        try {
            FileOutputStream fos = new FileOutputStream(new File("users.dat"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(User u : users){
                oos.writeObject(u);
            }
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void emptyfile(){
        try{
            FileOutputStream fos = new FileOutputStream("users.dat");
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

