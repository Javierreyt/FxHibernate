package org.hibernateproject.pruebajavafxconhibernatehello.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.hibernate.annotations.processing.SQL;
import org.hibernateproject.pruebajavafxconhibernatehello.GestorPeliculas;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.SQLiteServices;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.HibernateUtil;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.MovieDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.UserDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Movie;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.PropertiesManager;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.SQLiteUtil;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    private MenuItem menuLogout;
    @FXML
    private Label txtTittle;
    @FXML
    private TableView<Movie> table;
    @FXML
    private TableColumn<Movie,String> cId;
    @FXML
    private TableColumn<Movie,String> cTitulo;
    @FXML
    private TableColumn<Movie,String> cGenero;
    @FXML
    private TableColumn<Movie,String> cYear;
    @FXML
    private TableColumn<Movie,String> cDescripcion;
    @FXML
    private TableColumn<Movie,String> cDirector;
    @FXML
    private Button addFilmButton;
    @FXML
    private MenuItem switchCopy;
    @FXML
    private FlowPane flowPaneButton;

    private User user = PropertiesManager.loadObject("currentuser", User.class);

    private Movie movie;
    @FXML
    private TextArea textAreaId;

    private String tutorial = "Bienvenido a TottiFlix, Administrador\n\n" +
            "¡Gracias por usar TottiFlix! A continuación, te presentamos una breve guía para aprovechar todas las funcionalidades de la aplicación:\n\n" +
            "1. Gestión de Películas\n" +
            "\t- Como usuario registrado, puedes explorar nuestra base de datos de películas. Simplemente haz clic en una película para ver su información detallada, y si deseas, puedes eliminarla de la base de datos.\n" +
            "\t- Además, tienes la opción de añadir nuevas películas a la base de datos, que estarán disponibles para el resto de los usuarios en futuras consultas.\n\n" +
            "2. Gestión de Copias\n" +
            "\t- Navega a la sección de copias para ver las películas que has guardado. Al seleccionar una copia, podrás ver toda la información relacionada con la película, así como detalles específicos de la copia. Puedes actualizar o eliminar cualquier copia que desees.\n" +
            "\t- También puedes añadir una copia de una película existente en la base de datos para tener un registro en tu colección.\n\n" +
            "3. Sesión de Usuario\n" +
            "\t- Para regresar al menú principal, simplemente utiliza la opción de cerrar sesión y volverás al panel de inicio de sesión.\n\n" +
            "Con estas herramientas, tienes todo lo necesario para gestionar y personalizar tu colección de películas en TottiFlix. ¡Disfruta de la experiencia!";
    @FXML
    private CheckBox checkInfo;
    @FXML
    private BorderPane bpInfo;
    @FXML
    private Button btnAcceptInfo;
    @FXML
    private Label labelInfoAlert;
    @FXML
    private Label labelTitleAlert;
    @FXML
    private BorderPane alertInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(new SQLiteServices(SQLiteUtil.getConnection()).helpByUser(user.getId())){
            bpInfo.setVisible(false);
            bpInfo.setManaged(false);
        }else{
            textAreaId.setText(tutorial);
        }
        if(PropertiesManager.getSaveCopy()){
            alertInfo.setVisible(true);
            labelTitleAlert.setText("Añadir copia");
            labelInfoAlert.setText("Elige la pelicula que deseas añadir a tus copias de las siguientes.");
            btnAcceptInfo.setOnAction(event -> alertInfo.setVisible(false));
        }

        cId.setCellValueFactory( (row) ->{
            return new SimpleStringProperty(row.getValue().getId().toString());
        });
        cTitulo.setCellValueFactory( (row) ->{
            return new SimpleStringProperty(row.getValue().getTitulo());
        });
        cGenero.setCellValueFactory( (row) ->{
            return new SimpleStringProperty(row.getValue().getGenero());
        });
        cDescripcion.setCellValueFactory( (row) ->{
            return new SimpleStringProperty(row.getValue().getDescripcion());
        });
        cYear.setCellValueFactory( (row) ->{
            return new SimpleStringProperty(row.getValue().getAño().toString());
        });
        cDirector.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getDirector());
        });

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null) return;
            movie = newValue;
            PropertiesManager.saveObject("currentmovie",movie);
            if(!PropertiesManager.getSaveCopy()){
                GestorPeliculas.loadFXML("views/movieDetails-view.fxml", "TottiFilms - "+newValue.getTitulo());
            }else{
                PropertiesManager.setSaveCopy(false);
                GestorPeliculas.loadFXML("views/copyDetails-view.fxml", "TottiFilms - "+newValue.getTitulo());
            }
        });

        tableRefresh();

        if(!new UserDAO(HibernateUtil.getSessionFactory()).isAdmin(user)) {
            flowPaneButton.setVisible(false);
            flowPaneButton.setManaged(false);
            bpInfo.setVisible(false);
        }

    }

    private void tableRefresh(){
        table.getItems().clear();
        new MovieDAO(HibernateUtil.getSessionFactory()).findAll().forEach(movie -> {
            table.getItems().add(movie);
        });
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        PropertiesManager.saveObject("currentcopia",null);
        PropertiesManager.saveObject("currentmovie",null);
        new SQLiteServices(SQLiteUtil.getConnection()).updateRemindMe(user.getId(), false);
        GestorPeliculas.loadFXML("views/login-view.fxml","TottiFilms - Login");
    }
    @FXML
    public void addFilm(ActionEvent actionEvent) {
        PropertiesManager.saveObject("currentcopia",null);
        PropertiesManager.saveObject("currentmovie",null);
        GestorPeliculas.loadFXML("views/addMovie-view.fxml","TottiFilms - Añadir Películas");
    }
    @FXML
    public void switchCopy(ActionEvent actionEvent) {
        GestorPeliculas.loadFXML("views/copy-view.fxml","TottiFilms - Lista de Copias");
    }

    @FXML
    public void btnAceptar(ActionEvent actionEvent) {
        new SQLiteServices(SQLiteUtil.getConnection()).updateHelp(user.getId(), checkInfo.isSelected());
        bpInfo.setVisible(false);
        bpInfo.setManaged(false);
    }
}
