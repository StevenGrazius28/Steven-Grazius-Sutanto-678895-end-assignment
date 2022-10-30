package com.example.JavaEndAssignmnet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPasswrod;
    @FXML
    private Label txtLoginIdentificator;

    public void OnClickBtnLogin(ActionEvent event){
        UserDb userDb= new UserDb();
        try {
            if (txtUsername.getText().equals("")||(txtPasswrod.getText().equals("")))
                txtLoginIdentificator.setText("Please enter a username and a password");
            else {
                String username = txtUsername.getText();
                String password = txtPasswrod.getText();
                User user = userDb.getUser(username,password);
                if (user != null && Objects.equals(user.getStatus(),User.userStatus.admin)){
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("MainWindows.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setTitle("MainForm");
                    MainWindowsController mainWindowsController = fxmlLoader.getController();
                    mainWindowsController.setLabelText(user);
                    stage.setScene(scene);
                    stage.show();
                }
                txtLoginIdentificator.setText("your username and password combination is in correct please try again");

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}