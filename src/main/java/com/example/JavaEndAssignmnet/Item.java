package com.example.JavaEndAssignmnet;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Item implements Serializable {
    private final int itemId;
    private boolean status;
    private String title;
    private String author;
    private LocalDate date;
    public String getTitle(){
        return title;
    }
    public LocalDate getDate(){
        return  date;
    }
    public String getAuthor(){
        return author;
    }
    public int getItemId() {
        return itemId;
    }

    public boolean getStatusBool() {
        return status;
    }

    public String getStatus(){
        if (Objects.equals(status,true))
            return "Yes";
        return "No";
    }
    public void setStatus(boolean newStatus) { status = newStatus; }

    public void setDateToNow(){
        this.date = LocalDate.now();
    }

    public void setTitle(String newTitle){this.title=newTitle;}
    public void setAuthor(String newAuthor){this.author = newAuthor;}


    public Item(int itemId,boolean status,String title,String author,LocalDate date){
        this.itemId = itemId;
        this.status = status;
        this.title = title;
        this.author = author;
        this.date = date;
    }
}

