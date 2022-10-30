package com.example.JavaEndAssignmnet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class User implements Serializable {
    private final int id;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String password;
    public int getId(){ return id;}
    public LocalDate getBirthday(){return birthday;}

    public String getFirstname() {
        return firstname;
    }
    public  String getPassword() {
        return password;
    }
    public String getLastname(){
        return lastname;
    }
    public void setFirstname(String newFirstname){firstname = newFirstname;}
    public void setLastname(String newLastname){lastname = newLastname;}
    public void setBirthday(LocalDate newBirthday){birthday = newBirthday;}
    public void setPassword(String newPassword){password = newPassword;}
    public int getUserId(){
        return id;
    }
    public String getFullname(User user){
        return user.getFirstname()+ " "+ user.getLastname();
    }
    public User(int id, String firstName, String lastName, LocalDate birthdate, String password) {
        this.id = id;
        this.firstname = firstName;
        this.lastname = lastName;
        this.birthday = birthdate;
        int Age = Period.between(birthday, LocalDate.now()).getYears();
        this.password = password;
    }
}
