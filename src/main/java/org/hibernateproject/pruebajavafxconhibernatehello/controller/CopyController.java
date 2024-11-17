package org.hibernateproject.pruebajavafxconhibernatehello.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.hibernateproject.pruebajavafxconhibernatehello.GestorPeliculas;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.SQLiteServices;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Copia;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Movie;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.HibernateUtil;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.UserDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.dto.MovieCopiaDTO;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.PropertiesManager;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.SQLiteUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para la gestión de copias de películas.
 * Permite visualizar, actualizar y eliminar copias de películas.
 * También permite añadir nuevas copias y gestionar la sesión del usuario.
 */
public class CopyController implements Initializable {
    @FXML
    private TableView<MovieCopiaDTO> table;
    @FXML
    private TableColumn<MovieCopiaDTO,String> cTitulo;
    @FXML
    private TableColumn<MovieCopiaDTO,String > cSoporte;
    @FXML
    private TableColumn<MovieCopiaDTO,String> cEstado;
    @FXML
    private MenuItem listaPeliculasItem;

    private User user = PropertiesManager.loadObject("currentuser", User.class);

    private Movie movie;

    private Copia copia;
    @FXML
    private TextArea textAreaId;
    @FXML
    private BorderPane bpInfo;
    private String tutorial = "Bienvenido a TottiFlix, Usuario\n\n" +
            "¡Gracias por usar TottiFlix! A continuación, te presentamos una breve guía para aprovechar todas las funcionalidades de la aplicación:\n\n" +
            "1. Gestión de Copias\n" +
            "\t- Navega a la sección de copias para ver las películas que has guardado. Al seleccionar una copia, podrás ver toda la información relacionada con la película, así como detalles específicos de la copia. Puedes actualizar o eliminar cualquier copia que desees.\n" +
            "\t- También puedes añadir una copia de una película existente en la base de datos para tener un registro en tu colección.\n\n" +
            "2. Sesión de Usuario\n" +
            "\t- Para regresar al menú principal, simplemente utiliza la opción de cerrar sesión y volverás al panel de inicio de sesión.\n\n" +
            "Con estas herramientas, tienes todo lo necesario para gestionar y personalizar tu colección de películas en TottiFlix. ¡Disfruta de la experiencia!";
    @FXML
    private CheckBox checkInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(new SQLiteServices(SQLiteUtil.getConnection()).helpByUser(user.getId())){
            bpInfo.setVisible(false);
            bpInfo.setManaged(false);
        }else{
            textAreaId.setText(tutorial);
        }

        cTitulo.setCellValueFactory( (row) ->{
            return new SimpleStringProperty(row.getValue().getMovie().getTitulo().toString());
        });
        cEstado.setCellValueFactory( (row) ->{
            return new SimpleStringProperty(row.getValue().getCopia().getEstado().toString());
        });
        cSoporte.setCellValueFactory( (row) ->{
            return new SimpleStringProperty(row.getValue().getCopia().getSoporte().toString());
        });

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null) return;
            movie = newValue.getMovie();
            PropertiesManager.saveObject("currentmovie",newValue.getMovie());
            copia = newValue.getCopia();
            PropertiesManager.saveObject("currentcopia",newValue.getCopia());
            GestorPeliculas.loadFXML("views/copydetails-view.fxml", "TottiFilms - "+newValue.getMovie().getTitulo());
        });


        tableRefresh();
        if(!new UserDAO(HibernateUtil.getSessionFactory()).isAdmin(user)){
            listaPeliculasItem.setVisible(false);
        }

    }

    /**
     * Refresca la tabla con la lista de copias del usuario actual.
     */
    private void tableRefresh(){
        table.getItems().clear();
        // Obtener la lista de copias del usuario actual
        List<MovieCopiaDTO> movieCopiaList = new MovieCopiaDTO(HibernateUtil.getSessionFactory())
                .findAllMovieCopiaDTO(user);

        // Llenar la tabla solo con el título de la película, estado y soporte de la copia
        movieCopiaList.forEach(movieCopiaDTO -> {
            table.getItems().add(new MovieCopiaDTO(movieCopiaDTO.getMovie(), movieCopiaDTO.getCopia()));
        });
    }

    /**
     * Cierra la sesión del usuario y vuelve a la vista de inicio de sesión.
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void logout(ActionEvent actionEvent) {
        PropertiesManager.saveObject("currentmovie",null);
        PropertiesManager.saveObject("currentcopia",null);
        new SQLiteServices(SQLiteUtil.getConnection()).updateRemindMe(user.getId(), false);
        GestorPeliculas.loadFXML("views/login-view.fxml","TottiFilms - Login");
    }

    /**
     * Añade una nueva película.
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void addFilm(ActionEvent actionEvent) {
        PropertiesManager.saveObject("currentmovie",null);
        PropertiesManager.saveObject("currentcopia",null);
        PropertiesManager.setSaveCopy(true);
        GestorPeliculas.loadFXML("views/main-view.fxml", "TottiFilms - Lista de Peliculas");
    }

    /**
     * Muestra la lista de películas.
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void listaPeliculasAction(ActionEvent actionEvent) {
        PropertiesManager.saveObject("currentmovie",null);
        PropertiesManager.saveObject("currentcopia",null);
        GestorPeliculas.loadFXML("views/main-view.fxml", "TottiFilms - Lista de Peliculas");
    }

    /**
     * Acepta la información del tutorial y actualiza la preferencia del usuario.
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void btnAceptar(ActionEvent actionEvent) {
        new SQLiteServices(SQLiteUtil.getConnection()).updateHelp(user.getId(), checkInfo.isSelected());
        bpInfo.setVisible(false);
        bpInfo.setManaged(false);
    }
}