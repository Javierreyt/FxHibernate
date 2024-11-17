package org.hibernateproject.pruebajavafxconhibernatehello.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.hibernateproject.pruebajavafxconhibernatehello.GestorPeliculas;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.HibernateUtil;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.MovieDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Movie;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.PropertiesManager;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MovieDetailsController implements Initializable {
    @FXML
    private Button buttonBack;
    @FXML
    private Button backMovieButton;
    @FXML
    private Button nextMovieButton;
    @FXML
    private ImageView imgJPG;

    private MovieDAO movieDAO = new MovieDAO(HibernateUtil.getSessionFactory());
    @FXML
    private WebView webURL;
    @FXML
    private Label yearArea;
    @FXML
    private Label directorArea;
    @FXML
    private Label titleArea;
    @FXML
    private Label genreArea;
    @FXML
    private Label descriptionArea;

    private User user = PropertiesManager.loadObject("currentuser", User.class);

    private Movie movie = PropertiesManager.loadObject("currentmovie", Movie.class);
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
        GestorPeliculas.loadFXML("views/main-view.fxml","Gestor de Películas - Copias");
    }

    @FXML
    public void nextMovie(ActionEvent actionEvent) {
        List<Movie> movieList = new MovieDAO(HibernateUtil.getSessionFactory())
                .findAll();

        // Encontrar el índice de la película actual
        int currentIndex = -1;
        boolean found = false;
        for (int i = 0; i < movieList.size() && !found; i++) {
            if (Objects.equals(movieList.get(i).getId(), movie.getId())) {
                currentIndex = i;
                found = true;
            }
        }

        // Verificar si hay una película siguiente
        if (currentIndex >= 0 && currentIndex < movieList.size() - 1) {
            // Obtener la siguiente película
            Movie nextMovie = movieList.get(currentIndex + 1);

            // Actualizar la sesión actual
            movie = nextMovie;
            PropertiesManager.saveObject("currentmovie",movie);

            // Cargar la vista de la película
            GestorPeliculas.loadFXML("views/movieDetails-view.fxml", "TottiFilms - " + movie.getTitulo());
        }
    }

    @FXML
    public void backMovie(ActionEvent actionEvent) {
        List<Movie> movieList = new MovieDAO(HibernateUtil.getSessionFactory())
                .findAll();

        // Encontrar el índice de la película actual
        int currentIndex = -1;
        boolean found = false;
        for (int i = 0; i < movieList.size() && !found; i++) {
            if (Objects.equals(movieList.get(i).getId(), movie.getId())) {
                currentIndex = i;
                found = true;
            }
        }

        // Verificar si hay una película anterior
        if (currentIndex > 0) {
            // Obtener la película anterior
            Movie previousMovie = movieList.get(currentIndex - 1);

            // Actualizar la sesión actual
            movie = previousMovie;
            PropertiesManager.saveObject("currentmovie",movie);

            // Cargar la vista de la película
            GestorPeliculas.loadFXML("views/movieDetails-view.fxml", "TottiFilms - " + movie.getTitulo());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleArea.setText(movie.getTitulo());
        descriptionArea.setText(movie.getDescripcion());
        yearArea.setText(String.valueOf(movie.getAño()));
        genreArea.setText(movie.getGenero());
        directorArea.setText(movie.getDirector());
        imgJPG.setImage(new Image("file:src/main/resources/org/hibernateproject/pruebajavafxconhibernatehello/covers/"+movie.getImagen()));
        WebEngine engine = webURL.getEngine();
        engine.load(movie.getUrl());
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
    public void switchFilms(ActionEvent actionEvent) {
        GestorPeliculas.loadFXML("views/main-view.fxml","Usuario: " + user.getNombreUsuario());
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        movieDAO.delete(movie);
        alertInfo.setVisible(true);
        labelTitleAlert.setText("Película eliminada");
        labelInfoAlert.setText("Los datos se han eliminado de forma correcta.");
        btnAcceptInfo.setOnAction(event -> GestorPeliculas.loadFXML("views/main-view.fxml","Usuario: " + user.getNombreUsuario()));
    }
}
