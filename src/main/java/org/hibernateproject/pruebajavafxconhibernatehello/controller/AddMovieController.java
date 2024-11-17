package org.hibernateproject.pruebajavafxconhibernatehello.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.hibernateproject.pruebajavafxconhibernatehello.GestorPeliculas;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.HibernateUtil;
import org.hibernateproject.pruebajavafxconhibernatehello.dao.MovieDAO;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Movie;
import org.hibernateproject.pruebajavafxconhibernatehello.utils.PropertiesManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * Controlador para agregar una película.
 */
public class AddMovieController implements Initializable {
    @FXML
    private Button buttonSave;
    @FXML
    private TextField titleText;
    @FXML
    private TextArea txtArea;
    @FXML
    private Spinner yearSpinner;
    @FXML
    private ComboBox genreCombo;
    @FXML
    private Button buttonBack;
    @FXML
    private Button imgCover;
    @FXML
    private ImageView imgJPG;

    private MovieDAO movieDAO = new MovieDAO(HibernateUtil.getSessionFactory());
    @FXML
    private TextField textDirector;
    @FXML
    private TextField urlTrailer;

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

    /**
     * Cierra la ventana actual y carga la vista principal.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void close(ActionEvent actionEvent) {
        GestorPeliculas.loadFXML("views/main-view.fxml","Usuario: " + user.getNombreUsuario());
    }

    /**
     * Guarda o actualiza la información de la película.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void saveOrUpdate(ActionEvent actionEvent) {
        if (titleText.getText().isEmpty() ||
                genreCombo.getValue() == null ||
                yearSpinner.getValue() == null ||
                txtArea.getText().isEmpty() ||
                textDirector.getText().isEmpty() ||
                urlTrailer.getText().isEmpty() ||
                imgJPG.getImage().getUrl().contains("informacion.png")) {
            alertInfo.setVisible(true);
            labelTitleAlert.setText("Error al guardar");
            labelInfoAlert.setText("Debes de rellenar todos los campos, incluyendo la imagen");
            btnAcceptInfo.setOnAction(event -> alertInfo.setVisible(false));
        } else {
            // Validar y formatear la URL de YouTube
            String formattedUrl = formatYouTubeURL(urlTrailer.getText());
            if (Objects.equals(formattedUrl, "")) {
                alertInfo.setVisible(true);
                labelTitleAlert.setText("Error al guardar");
                labelInfoAlert.setText("La URL debe ser de YouTube y en este formato " +
                        "'www.youtube.com/' + 'watch?v=' + 'xxxxxxxxxxx'");
                btnAcceptInfo.setOnAction(event -> alertInfo.setVisible(false));
                return;
            }

            Movie movie = new Movie(
                    titleText.getText(),
                    (String) genreCombo.getValue(),
                    (Integer) yearSpinner.getValue(),
                    txtArea.getText(),
                    textDirector.getText(),
                    "temp.jpg",
                    formattedUrl // Usar la URL formateada
            );

            movieDAO.save(movie);

            if (movie.getImagen().startsWith("temp")) {
                String extension = movie.getImagen().substring(movie.getImagen().lastIndexOf("."));
                String finalImageName = movie.getId() + extension;
                Path finalDestination = Path.of("src/main/resources/org/hibernateproject/pruebajavafxconhibernatehello/covers", finalImageName);

                try {
                    Files.move(Path.of("src/main/resources/org/hibernateproject/pruebajavafxconhibernatehello/covers", movie.getImagen()),
                            finalDestination, StandardCopyOption.REPLACE_EXISTING);

                    movie.setImagen(finalImageName);
                    movieDAO.update(movie);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            alertInfo.setVisible(true);
            labelTitleAlert.setText("Película guardada");
            labelInfoAlert.setText("Los datos se han introducido de forma correcta.");
            btnAcceptInfo.setOnAction(event -> GestorPeliculas.loadFXML("views/main-view.fxml","Usuario: " + user.getNombreUsuario()));
        }
    }

    /**
     * Selecciona una imagen de carátula para la película.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void selectImg(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar una imagen de carátula");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.jpg")
        );
        fileChooser.setInitialDirectory(new File("src/main/resources/org/hibernateproject/pruebajavafxconhibernatehello/covers"));

        // Abrir el cuadro de diálogo de selección de archivo
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Generar el nombre del archivo como "id.jpg" o "id.png"
                String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                String newFileName = "temp" + extension;

                // Ruta de destino
                Path destination = Path.of("src/main/resources/org/hibernateproject/pruebajavafxconhibernatehello/covers", newFileName);

                // Copiar el archivo seleccionado a la ruta de destino
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                // Actualizar el atributo de la imagen en el objeto Movie
                if (movie != null) {
                    movie.setImagen(newFileName);
                }

                // Mostrar la imagen seleccionada en el ImageView
                imgJPG.setImage(new Image(destination.toUri().toString()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Inicializa el controlador.
     *
     * @param url URL de la ubicación.
     * @param resourceBundle Conjunto de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2024, 2021));
        genreCombo.getItems().addAll(movieDAO.findGenres());
        imgJPG.setImage(new Image("file:src/main/resources/org/hibernateproject/pruebajavafxconhibernatehello/media/informacion.png"));
    }

    /**
     * Cierra la sesión del usuario.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void logout(ActionEvent actionEvent) {
        PropertiesManager.saveObject("currentmovie",null);
        PropertiesManager.saveObject("currentcopia",null);
        GestorPeliculas.loadFXML("views/login-view.fxml","TottiFilms - Login");
    }

    /**
     * Cambia a la vista de copias.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void switchCopy(ActionEvent actionEvent) {
        GestorPeliculas.loadFXML("views/copy-view.fxml","TottiFilms - Lista de Copias");
    }

    /**
     * Cambia a la vista principal de películas.
     *
     * @param actionEvent Evento de acción.
     */
    @FXML
    public void switchFilms(ActionEvent actionEvent) {
        GestorPeliculas.loadFXML("views/main-view.fxml","Usuario: " + user.getNombreUsuario());
    }

    /**
     * Formatea una URL de YouTube.
     *
     * @param url URL a formatear.
     * @return URL formateada.
     */
    private String formatYouTubeURL(String url) {
        // Comprobar si la URL comienza con "https://"
        if (!url.startsWith("https://")) {
            url = "https://" + url;
        }
        String formattedUrl = "";

        // Verificar que la URL sea de YouTube y contenga "watch?v="
        if (url.contains("www.youtube.com") && url.contains("watch?v=")) {
            // Extraer el ID del video después de "watch?v="
            String videoId = url.substring(url.indexOf("watch?v=") + 8, url.indexOf("watch?v=") + 19);
            // Formatear la URL en el nuevo formato y añadir "?fs=1" al final
            formattedUrl = "https://www.youtube.com/embed/" + videoId + "?fs=1";
        }
        return formattedUrl;
    }
}