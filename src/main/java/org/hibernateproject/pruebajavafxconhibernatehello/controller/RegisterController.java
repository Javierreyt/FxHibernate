package org.hibernateproject.pruebajavafxconhibernatehello.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.hibernateproject.pruebajavafxconhibernatehello.GestorPeliculas;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.UserDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.HibernateUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private Label txtInfo;
    @FXML
    private TextField txtVisiblePassword2;
    @FXML
    private Button buttonConfirm;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtVisiblePassword;
    @FXML
    private PasswordField txtPassword2;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label labelTitleAlert;
    @FXML
    private BorderPane alertInfo;
    @FXML
    private Label labelInfoAlert;
    @FXML
    private Button btnAcceptInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Enlazamos el texto de ambos campos
        txtPassword.textProperty().bindBidirectional(txtVisiblePassword.textProperty());
        txtVisiblePassword.setVisible(false);
        txtVisiblePassword.setManaged(false);

        // Enlazamos el texto de ambos campos
        txtPassword2.textProperty().bindBidirectional(txtVisiblePassword2.textProperty());
        txtVisiblePassword2.setVisible(false);
        txtVisiblePassword2.setManaged(false);
    }

    @FXML
    public void enterApp(ActionEvent actionEvent) {
        if(!new UserDAO(HibernateUtil.getSessionFactory()).userExists(txtUser.getText())){
            if(txtPassword.getText().equals(txtPassword2.getText())){
                User u= new User();
                u.setNombreUsuario(txtUser.getText());
                u.setContraseña(txtPassword.getText());
                u.setIsAdmin(false);
                new UserDAO(HibernateUtil.getSessionFactory()).save(u);
                alertInfo.setVisible(true);
                labelTitleAlert.setText("Nuevo registro de usuario");
                labelInfoAlert.setText("Enhorabuena! Los datos introducidos son correctos.");
                btnAcceptInfo.setOnAction(event -> GestorPeliculas.loadFXML("views/login-view.fxml","TottiFlix - Login"));
            }else{
                alertInfo.setVisible(true);
                labelTitleAlert.setText("Contraseñas no coinciden");
                labelInfoAlert.setText("Las contraseñas no coinciden. Por favor, inténtalo de nuevo.");
                txtPassword.clear();
                txtPassword2.clear();
                btnAcceptInfo.setOnAction(event -> alertInfo.setVisible(false));
            }
        }else{
            alertInfo.setVisible(true);
            labelTitleAlert.setText("Nombre de Usuario Existente");
            labelInfoAlert.setText("El nombre de usuario ya existe. Por favor, elige otro.");
            txtUser.clear();
            btnAcceptInfo.setOnAction(event -> alertInfo.setVisible(false));
        }
    }

    @FXML
    public void setPassword2Visible(ActionEvent actionEvent) {
        if (txtPassword2.isVisible()) {
            txtPassword2.setVisible(false);
            txtPassword2.setManaged(false);
            txtVisiblePassword2.setVisible(true);
            txtVisiblePassword2.setManaged(true);
        } else {
            txtVisiblePassword2.setVisible(false);
            txtVisiblePassword2.setManaged(false);
            txtPassword2.setVisible(true);
            txtPassword2.setManaged(true);
        }
    }

    @FXML
    public void closeApp(ActionEvent actionEvent) {
        GestorPeliculas.loadFXML("views/login-view.fxml", "TottiFlix - Login");
    }

    @FXML
    public void setPasswordVisible(ActionEvent actionEvent) {
        if (txtPassword.isVisible()) {
            txtPassword.setVisible(false);
            txtPassword.setManaged(false);
            txtVisiblePassword.setVisible(true);
            txtVisiblePassword.setManaged(true);
        } else {
            txtVisiblePassword.setVisible(false);
            txtVisiblePassword.setManaged(false);
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);
        }
    }
}
