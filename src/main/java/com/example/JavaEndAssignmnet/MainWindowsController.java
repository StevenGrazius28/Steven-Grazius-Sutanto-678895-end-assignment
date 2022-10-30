package com.example.JavaEndAssignmnet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;

public class MainWindowsController implements Initializable{
    private final Duration receiveDeadLine = Duration.ofDays(21);
    @FXML
    private Label labelAuthor;
    @FXML
    private Label labelTitle;
    @FXML
    private Label labelUserFirstName;
    @FXML
    private Label labelUserLastName;
    @FXML
    private Label labelWelcomeUser;
    @FXML
    private Label labelMemberIdChecker;
    @FXML
    private TableColumn<Item, Integer> tableColumnItemId;

    @FXML
    private TableColumn<Item,String> tableColumnItemStatus;
    @FXML
    private TableColumn<Item,String> tableColumnItemTitle;
    @FXML
    private TableColumn<Item,String> tableColumnItemAuthor;
    @FXML
    private TableColumn<User, Integer> tableColumnUserId;

    @FXML
    private TableColumn<User, String> tableColumnUserFirstname;

    @FXML
    private TableColumn<User, String> tableColumnUserLastname;

    @FXML
    private TableColumn<User, LocalDate> tableColumnUserBirthday;

    @FXML
    private DatePicker DatepickerMemberBirthday;
    @FXML
    private TextField textFieldMemberId;
    @FXML
    private TextField textFieldMemberFirstname;
    @FXML
    private TextField textFieldMemberLastname;
    @FXML
    private Label labelMemberBirthday;
    @FXML
    public TableView<Item> tableViewItems;
    @FXML
    private TableView<User> tableviewUser;
    @FXML
    private Pane paneLendingReceiving;
    @FXML
    private Pane paneItem;
    @FXML
    private Pane paneMembers;

    @FXML
    private TextField lblMemberIdentifer;

    @FXML
    private TextField textFieldAuthor;
    @FXML
    private TextField textFieldTitle;
    @FXML
    private TextField textFieldId;
    @FXML
    private TextField itemcodeLend;
    @FXML
    private TextField itemCodeReceive;
    @FXML
    private Label lblItemStatusIndicator;
    @FXML
    private Label lblIndicatorReceiveItem;
    private int itemCode;

    private final ItemDb itemDb = new ItemDb();
    private final UserDb userDb = new UserDb();
    @FXML
    private void onClickBtnLendItem() {
        if (Objects.equals(itemcodeLend.getText(),"") || Objects.equals(lblMemberIdentifer.getText(), "")) {
            lblItemStatusIndicator.setText("Please enter an item Id");
            labelMemberIdChecker.setText("Please enter a member Id");
        }
        else{
            itemCode = Integer.parseInt(itemcodeLend.getText());
            int userCode = Integer.parseInt(lblMemberIdentifer.getText());
            Item item = itemDb.GetById(itemCode);
            User user = userDb.GetbyId(userCode);

            if (Objects.equals(item.getStatusBool(), false)&&(Objects.equals(user.getUserId(), userCode))){
                itemDb.SetItemStatus(item, false);
                lblItemStatusIndicator.setText("Yes, Item is borrowed");
            } else
                lblItemStatusIndicator.setText("Sorry the item is not available");
            itemDb.write();
        }
    }

    @FXML
    private void onClickBtnReceiveItem() {
        if(Objects.equals(itemCodeReceive.getText(),""))
            lblIndicatorReceiveItem.setText("Please enter a item Id");
        else {
            itemCode = Integer.parseInt(itemCodeReceive.getText());
            Item item = itemDb.GetById(itemCode);
            Duration BorrowTime = Duration.between(item.getDate().atStartOfDay(), LocalDate.now().atStartOfDay());
            int durationOfBorrow = BorrowTime.compareTo(receiveDeadLine);
            if (durationOfBorrow > 0)
                lblIndicatorReceiveItem.setText("You are late for " + BorrowTime.toDays() + " Day");
            else
                lblIndicatorReceiveItem.setText(" You have borrowed the book for " + BorrowTime.toDays() + " days");
            itemDb.SetItemStatus(item, true);
            itemDb.write();
        }
    }
    private void hideAndShowItems(int options){
        switch (options) {
            case 1 -> {
                paneLendingReceiving.setVisible(true);
                paneMembers.setVisible(false);
                paneItem.setVisible(false);
            }
            case 2 -> {
                paneItem.setVisible(true);
                paneLendingReceiving.setVisible(false);
                paneMembers.setVisible(false);
            }
            case 3 -> {
                paneMembers.setVisible(true);
                paneLendingReceiving.setVisible(false);
                paneItem.setVisible(false);
            }
        }
    }
    @FXML
    private void onClickBtnLendingReceiving(){
        hideAndShowItems(1);
    }
    @FXML
    private void onClickBtnItems() throws IOException {
        hideAndShowItems(2);
        tableViewItems.getItems().clear();
        itemDb.read();
        ObservableList<Item> listItem = FXCollections.observableArrayList(itemDb.GetAllItem());
        tableColumnItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        tableColumnItemStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableColumnItemTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnItemAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tableViewItems.setItems(listItem);
    }
    @FXML
    private void onClickBtnMembers() throws IOException {
        hideAndShowItems(3);
        tableviewUser.getItems().clear();
        userDb.read();
        ObservableList<User> listUser = FXCollections.observableArrayList(userDb.GetAllUser());
        tableColumnUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnUserFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        tableColumnUserLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        tableColumnUserBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        tableviewUser.setItems(listUser);
    }
    @FXML
    private void onClickBtnAddItem() {
        if (Objects.equals(textFieldTitle.getText(),"") || (Objects.equals(textFieldAuthor.getText(),""))) {
            writeWentValueIsEmptyItem();
        }
        else {
            int biggestId = 0;
            for (Item i:itemDb.items) {
                if (i.getItemId()>biggestId)
                    biggestId = i.getItemId();
            }
            Item item = new Item(biggestId + 1, true, textFieldTitle.getText(), textFieldAuthor.getText(), LocalDate.now());
            itemDb.items.add(item);
            ObservableList<Item> items = tableViewItems.getItems();
            items.add(item);
            tableViewItems.setItems(items);
            itemDb.write();
        }
    }
    @FXML
    private void onClickBtnAddMembers() {
        if (Objects.equals(textFieldMemberFirstname.getText(),"") || (Objects.equals(textFieldMemberLastname.getText(),"")||Objects.isNull(DatepickerMemberBirthday)))
        {
            writeWentValueIsEmptyUser();
        }
        else {
            int biggestId = 0;
            for (User u: userDb.users) {
                if (u.getId()> biggestId)
                    biggestId = u.getId();
            }
            User user = new User(biggestId+1, textFieldMemberFirstname.getText(), textFieldMemberLastname.getText(), DatepickerMemberBirthday.getValue(), textFieldMemberFirstname + "123");
            userDb.users.add(user);
            ObservableList<User> users = tableviewUser.getItems();
            users.add(user);
            tableviewUser.setItems(users);
            userDb.write();
        }
    }

    @FXML
    private void onClickBtnDeleteItems() {
        try{
            int selectedItem = tableViewItems.getSelectionModel().getSelectedIndex();
            tableViewItems.getItems().remove(selectedItem);
            itemDb.items.remove(selectedItem);
            itemDb.write();
        } catch (Exception e) {
            //ignore
        }
    }

    @FXML
    private void onClickBtnDeleteMembers() {
        try{
            int selectedUser = tableviewUser.getSelectionModel().getSelectedIndex();
            tableviewUser.getItems().remove(selectedUser);
            userDb.users.remove(selectedUser);
            userDb.write();
        } catch (Exception e) {
            //ignore
        }
    }
    @FXML
    private void onSelectedTableViewUser(){
        try {
            User user = tableviewUser.getSelectionModel().getSelectedItem();
            textFieldMemberId.setText(String.valueOf(user.getId()));
            textFieldMemberFirstname.setText(String.valueOf(user.getFirstname()));
            textFieldMemberLastname.setText(String.valueOf(user.getLastname()));
            DatepickerMemberBirthday.setValue(user.getBirthday());
        } catch (Exception e) {
            //ignore
        }
    }
    @FXML
    private void onSelectedTableViewItem(){
        try{
            Item item = tableViewItems.getSelectionModel().getSelectedItem();
            textFieldTitle.setText(String.valueOf(item.getTitle()));
            textFieldAuthor.setText(String.valueOf(item.getAuthor()));
            textFieldId.setText(String.valueOf(item.getItemId()));
        } catch (Exception e) {
            //ignore
        }
    }
    @FXML
    private void onClickBtnEditItems() {
        if (Objects.equals(textFieldAuthor.getText(),"")||Objects.equals(textFieldTitle.getText(),""))
        {
            writeWentValueIsEmptyItem();
        }
        else{
            ObservableList<Item> tableData = tableViewItems.getItems();
            int selectedItem = Integer.parseInt(textFieldId.getText());
            for (Item item : tableData)
            {
                if(item.getItemId()== selectedItem){
                    item.setTitle(textFieldTitle.getText());
                    item.setAuthor(textFieldAuthor.getText());
                    tableViewItems.setItems(tableData);
                    tableViewItems.refresh();
                }
            }
            itemDb.write();
        }
    }

    private void writeWentValueIsEmptyItem(){
        labelAuthor.setText("Please enter an Author");
        labelTitle.setText("Please enter a Title");
    }
    private void writeWentValueIsEmptyUser(){
        labelUserFirstName.setText("Please enter a Firstname");
        labelUserLastName.setText("Please enter a Lastname");
        labelMemberBirthday.setText("Please enter a Birthday");
    }
    @FXML
    private void onClickBtnEditMembers() {
        if (Objects.equals(textFieldMemberFirstname.getText(),"")||Objects.equals(textFieldMemberLastname.getText(),"")||Objects.isNull(DatepickerMemberBirthday))
        {
            writeWentValueIsEmptyUser();
        }
        else{

            ObservableList<User> tableData = tableviewUser.getItems();
            int selectedUser = Integer.parseInt(textFieldMemberId.getText());
            for (User user : tableData){
                if (user.getId() == selectedUser){
                    user.setFirstname(textFieldMemberFirstname.getText());
                    user.setLastname(textFieldMemberLastname.getText());
                    user.setBirthday(DatepickerMemberBirthday.getValue());
                    tableviewUser.setItems(tableData);
                    tableviewUser.refresh();
                }
            }
            userDb.write();
        }
    }

    public void setLabelText(User user){
        labelWelcomeUser.setText("Welcome "+ user.getFullname(user));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideAndShowItems(1);
    }
}
