package org.hibernateproject.pruebajavafxconhibernatehello.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.hibernateproject.pruebajavafxconhibernatehello.GestorPeliculas;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.SQLiteServices;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.HibernateUtil;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.UserDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.PropertiesManager;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.SQLiteUtil;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private Button exitButton;
    @FXML
    private Label txtInfo;
    @FXML
    private Button buttonConfirm;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtVisiblePassword;
    @FXML
    private CheckBox remind_me;
    @FXML
    private Label register;
    @FXML
    private Label labelTitleAlert;
    @FXML
    private Button btnAcceptInfo;
    @FXML
    private Label labelInfoAlert;
    @FXML
    private BorderPane alertInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Enlazamos el texto de ambos campos
        txtPassword.textProperty().bindBidirectional(txtVisiblePassword.textProperty());
        txtVisiblePassword.setVisible(false);
        txtVisiblePassword.setManaged(false);
        register.setOnMouseClicked(event -> {
            GestorPeliculas.loadFXML("views/register.fxml", "Nuevo Usuario");
        });
    }

    @FXML
    public void enterApp(ActionEvent event) {
        User user = new UserDAO(HibernateUtil.getSessionFactory()).validateUser(
                txtUser.getText(),
                txtPassword.getText()
        );
        if(user == null){
            alertInfo.setVisible(true);
            labelTitleAlert.setText("Credenciales incorrectas");
            labelInfoAlert.setText("Usuario o contraseña incorrectos");
            txtUser.clear();
            txtPassword.clear();
            btnAcceptInfo.setOnAction(event1 -> {
                alertInfo.setVisible(false);
            });
        }else{
            if(remind_me.isSelected()){
                PropertiesManager.setUserNombreUsuario(txtUser.getText());
                PropertiesManager.setUserContraseña(txtPassword.getText());
            }else{
                PropertiesManager.setUserNombreUsuario("");
                PropertiesManager.setUserContraseña("");
            }
            alertInfo.setVisible(true);
            labelTitleAlert.setText("Inicio de sesión correcto");
            labelInfoAlert.setText("Usuario logueado correctamente: Bienvenido " + user.getNombreUsuario());
            PropertiesManager.saveObject("currentuser", user);
            if(new SQLiteServices(SQLiteUtil.getConnection()).verificarUsuarioPorId(user)) {
                new SQLiteServices(SQLiteUtil.getConnection()).updateRemindMe(user.getId(), remind_me.isSelected());
            }else{
                new SQLiteServices(SQLiteUtil.getConnection()).insertarUsuario(user, remind_me.isSelected(), false);

            }
            btnAcceptInfo.setOnAction(event1 -> {
                if(user.getIsAdmin()){
                    GestorPeliculas.loadFXML("views/main-view.fxml","Usuario: " + user.getNombreUsuario());
                }else {
                    GestorPeliculas.loadFXML("views/copy-view.fxml", "TottiFilms - Lista de Copias");
                }
            });
        }
    }


    @FXML
    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
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
