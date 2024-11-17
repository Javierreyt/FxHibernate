package org.hibernateproject.pruebajavafxconhibernatehello;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.SQLiteServices;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.PropertiesManager;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.SQLiteUtil;

import java.io.IOException;

public class GestorPeliculas extends Application {
    private static Stage ventana;
    @Override
    public void start(Stage stage) throws IOException {
        ventana=stage;
        User user = PropertiesManager.loadObject("currentuser", User.class);
        if(new SQLiteServices(SQLiteUtil.getConnection()).remindMeByUser(user.getId())){
            if(user.getIsAdmin()){
                loadFXML("views/main-view.fxml","TottiFlix - "+ user.getNombreUsuario());
            }else{
                loadFXML("views/copy-view.fxml","TottiFlix - "+ user.getNombreUsuario());
            }
        }
        else {
            loadFXML("views/login-view.fxml", "TottiFlix - Login");
        }
        stage.show();
    }

    public static void loadFXML(String view, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(GestorPeliculas.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(),1500,900);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ventana.setTitle(title);
        ventana.setScene(scene);
        ventana.getIcons().add(new Image("file:/Users/javierreyt/Desktop/Hibernate/PruebaJavaFXconHibernate/src/main/resources/org/hibernateproject/pruebajavafxconhibernatehello/media/icon.png"));

        //ventana.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}