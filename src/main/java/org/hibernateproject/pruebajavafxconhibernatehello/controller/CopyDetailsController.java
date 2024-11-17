package org.hibernateproject.pruebajavafxconhibernatehello.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import org.hibernateproject.pruebajavafxconhibernatehello.GestorPeliculas;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Movie;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.HibernateUtil;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.CopiaDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.MovieDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.UserDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.dto.MovieCopiaDTO;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Copia;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.PropertiesManager;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CopyDetailsController implements Initializable {
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonBack;
    @FXML
    private ImageView imgJPG;

    private MovieDAO movieDAO = new MovieDAO(HibernateUtil.getSessionFactory());

    private CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());

    @FXML
    private ComboBox statusCombo;
    @FXML
    private ComboBox soporteCombo;
    @FXML
    private Label titleText;
    @FXML
    private Label descriptionText;
    @FXML
    private Label genreText;
    @FXML
    private Label directorText;
    @FXML
    private Label yearText;
    @FXML
    private WebView trailerURL;
    @FXML
    private Button editButton;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button backMovieButton;
    @FXML
    private Button nextMovieButton;
    @FXML
    private MenuItem listaPeliculasItem;

    private User user = PropertiesManager.loadObject("currentuser", User.class);

    private Movie movie = PropertiesManager.loadObject("currentmovie", Movie.class);

    private Copia copia = PropertiesManager.loadObject("currentcopia", Copia.class);
    @FXML
    private Button btnAcceptInfo;
    @FXML
    private Label labelInfoAlert;
    @FXML
    private Label labelTitleAlert;
    @FXML
    private BorderPane alertInfo;

    @FXML
    public void close(ActionEvent actionEvent) {
        GestorPeliculas.loadFXML("views/copy-view.fxml", "TottiFilms - Lista de Copias");
    }

    @FXML
    public void saveOrUpdate(ActionEvent actionEvent) {
        // Verificar si los campos están vacíos
        if (statusCombo.getValue() == null || soporteCombo.getValue() == null) {
            alertInfo.setVisible(true);
            labelTitleAlert.setText("Error al guardar");
            labelInfoAlert.setText("Debes de rellenar todos los campos");
            btnAcceptInfo.setOnAction(event -> alertInfo.setVisible(false));
        } else {
            if (copia == null) {
                // Guardar nueva copia
                new CopiaDAO(HibernateUtil.getSessionFactory()).save(
                        new Copia(
                                movie,
                                user,
                                (String) statusCombo.getValue(),
                                (String) soporteCombo.getValue()
                        ));
            } else {
                // Actualizar copia existente
                copia.setEstado((String) statusCombo.getValue());
                copia.setSoporte((String) soporteCombo.getValue());
                PropertiesManager.saveObject("currentcopia",copia);
                new CopiaDAO(HibernateUtil.getSessionFactory()).update(copia);
            }
            alertInfo.setVisible(true);
            labelTitleAlert.setText("Datos guardados");
            labelInfoAlert.setText("Los datos se han introducido de forma correcta.");
            btnAcceptInfo.setOnAction(event -> GestorPeliculas.loadFXML("views/copy-view.fxml", "TottiFilms - Lista de Copias"));
        }
    }


    @FXML
    public void nextMovie(ActionEvent actionEvent) {
        List<MovieCopiaDTO> movieCopiaList = new MovieCopiaDTO(HibernateUtil.getSessionFactory())
                .findAllMovieCopiaDTO(user);

        // Encontrar el índice de la película actual
        int currentIndex = -1;
        boolean found = false;
        for (int i = 0; i < movieCopiaList.size() && !found; i++) {
            if (Objects.equals(movieCopiaList.get(i).getCopia().getId(), copia.getId())) {
                currentIndex = i;
                found = true;
            }
        }

        // Verificar si hay una película siguiente
        if (currentIndex >= 0 && currentIndex < movieCopiaList.size() - 1) {
            // Obtener la siguiente película
            MovieCopiaDTO nextMovieCopia = movieCopiaList.get(currentIndex + 1);

            // Actualizar la sesión actual
            PropertiesManager.saveObject("currentmovie",nextMovieCopia.getMovie());
            PropertiesManager.saveObject("currentcopia",nextMovieCopia.getCopia());

            // Cargar la vista de la película
            GestorPeliculas.loadFXML("views/copydetails-view.fxml", "TottiFilms - " + movie.getTitulo());
        }
    }

    @FXML
    public void backMovie(ActionEvent actionEvent) {
        List<MovieCopiaDTO> movieCopiaList = new MovieCopiaDTO(HibernateUtil.getSessionFactory())
                .findAllMovieCopiaDTO(user);

        // Encontrar el índice de la película actual
        int currentIndex = -1;
        boolean found = false;
        for (int i = 0; i < movieCopiaList.size() && !found; i++) {
            if (Objects.equals(movieCopiaList.get(i).getCopia().getId(), copia.getId())) {
                currentIndex = i;
                found = true;
            }
        }

        // Verificar si hay una película anterior
        if (currentIndex > 0) {
            // Obtener la película anterior
            MovieCopiaDTO previousMovieCopia = movieCopiaList.get(currentIndex - 1);

            // Actualizar la sesión actual
            PropertiesManager.saveObject("currentmovie", previousMovieCopia.getMovie());
            PropertiesManager.saveObject("currentcopia", previousMovieCopia.getCopia());

            // Cargar la vista de la película
            GestorPeliculas.loadFXML("views/copydetails-view.fxml", "TottiFilms - " + movie.getTitulo());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusCombo.getItems().addAll(copiaDAO.findEstado());
        soporteCombo.getItems().addAll(copiaDAO.findSoporte());
        if(copia == null){
            backMovieButton.disableProperty().setValue(true);
            nextMovieButton.disableProperty().setValue(true);
            buttonDelete.disableProperty().setValue(true);
            titleText.setText(movie.getTitulo());
            descriptionText.setText(movie.getDescripcion());
            yearText.setText(movie.getAño().toString());
            genreText.setText(movie.getGenero());
            directorText.setText(movie.getDirector());
            imgJPG.setImage(new Image("file:src/main/resources/org/hibernateproject/pruebajavafxconhibernatehello/covers/" + movie.getImagen()));
            WebEngine engine = trailerURL.getEngine();
            engine.load(movie.getUrl());
        }
        else {
            buttonSave.setText("Actualizar");
            titleText.setText(movie.getTitulo());
            descriptionText.setText(movie.getDescripcion());
            yearText.setText(movie.getAño().toString());
            genreText.setText(movie.getGenero());
            directorText.setText(movie.getDirector());
            statusCombo.setValue(copia.getEstado());
            soporteCombo.setValue(copia.getSoporte());
            imgJPG.setImage(new Image("file:src/main/resources/org/hibernateproject/pruebajavafxconhibernatehello/covers/" + movie.getImagen()));
            WebEngine engine = trailerURL.getEngine();
            engine.load(movie.getUrl());
        }
        if(new UserDAO(HibernateUtil.getSessionFactory()).isAdmin(user)){
            PropertiesManager.setSaveCopy(false);
        }else{
            listaPeliculasItem.setVisible(false);
        }
    }

    @FXML
    public void edit(ActionEvent actionEvent) {
        if(statusCombo.isDisable()){
            statusCombo.disableProperty().setValue(false);
            soporteCombo.disableProperty().setValue(false);
        }else {
            statusCombo.disableProperty().setValue(true);
            soporteCombo.disableProperty().setValue(true);
        }
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        copiaDAO.delete(copia);
        alertInfo.setVisible(true);
        labelTitleAlert.setText("Datos eliminados");
        labelInfoAlert.setText("Los datos se han eliminado de forma correcta.");
        btnAcceptInfo.setOnAction(event -> GestorPeliculas.loadFXML("views/copy-view.fxml", "TottiFilms - Lista de Copias"));
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        PropertiesManager.saveObject("currentmovie",null);
        PropertiesManager.saveObject("currentcopia",null);
        GestorPeliculas.loadFXML("views/login-view.fxml","TottiFilms - Login");
    }
    @FXML
    public void switchCopy(ActionEvent actionEvent) {
        GestorPeliculas.loadFXML("views/copy-view.fxml","TottiFilms - Lista de Copias");
    }

    @FXML
    public void listaPeliculasAction(ActionEvent actionEvent) {
        PropertiesManager.saveObject("currentmovie",null);
        PropertiesManager.saveObject("currentcopia",null);
        GestorPeliculas.loadFXML("views/main-view.fxml", "TottiFilms - Lista de Peliculas");
    }
}
