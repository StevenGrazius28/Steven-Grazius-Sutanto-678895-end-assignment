package com.example.JavaEndAssignmnet;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemDb {
    public List<Item> items;
    public ItemDb(){
        items = Items();
    }
    private List<Item> Items(){
        List<Item> itemsList = new ArrayList<>();
        itemsList.add(new Item(1,true,"How To Program","Steven", LocalDate.of(2020,9,12)));
        itemsList.add(new Item(2,true,"This is Java", "Jun",LocalDate.of(2020,9,12)));
        itemsList.add(new Item(3,false,"java is next","Jason",LocalDate.of(2020,9,12)));
        itemsList.add(new Item(4,false,"Java ?","Yvan",LocalDate.of(2020,9,12)));
        itemsList.add(new Item(5,false, "Clean Code","Agus",LocalDate.of(2020,9,12)));
        itemsList.add(new Item(6,true,"Best Book", "Naruto",LocalDate.of(2020,9,12)));
        itemsList.add(new Item(7,false,"One Piece","Oda",LocalDate.of(2020,9,6)));
        itemsList.add(new Item(8,true,"C#","Salah",LocalDate.of(2020,3,11)));
        return itemsList;
    }
    public Item GetById(int id){
        for (Item i: items) {
            if(Objects.equals(i.getItemId(),id))
                return i;
        }
        return null;
    }
    public List<Item> GetAllItem(){
        return items;
    }

    public void SetItemTitle(Item item,String newTitle){
        item.setTitle(newTitle);
    }
    public void SetItemAuthor(Item item,String newAuthor){
        item.setAuthor(newAuthor);
    }
    public void SetItemStatus(Item item, boolean newStatus){
        item.setStatus(newStatus);
        item.setDateToNow();
    }
    public void read() throws IOException {
        items.clear();
        FileInputStream fis = new FileInputStream(new File("items.dat"));
        ObjectInputStream ois = new ObjectInputStream(fis);
        while (true) {
            try {
                Item i = (Item) ois.readObject();
                items.add(i);
            } catch (EOFException eofe) {
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void write() {
        //open file Output Stream
        emptyfile();
        try {
            FileOutputStream fos = new FileOutputStream(new File("items.dat"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Item i : items){
                oos.writeObject(i);
            }
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void emptyfile(){
        try{
            FileOutputStream fos = new FileOutputStream("items.dat");
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
