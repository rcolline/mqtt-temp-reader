package us.bzsf.weatherreader;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * BZSF Weather Reader JavaFX Application
 * 
 * This app displays the current weather read off the MQTT bus provided by weewx's
 * MQTT integration.
 */
public final class App extends Application {

  private static final Logger LOGGER = Logger.getLogger(App.class.getName());
  private BzsfWeatherMqttClient bzsfMqttClient;

  /**
   * Displays window with updating temperature from MQTT weather source.
   * 
   * @param args The arguments of the program.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader =
        new FXMLLoader(this.getClass().getResource("/us/bzsf/weatherreader/weather-reader-window.fxml"));
    Pane weatherReaderDisplayPane =
        loader.load(getClass().getResource("/us/bzsf/weatherreader/weather-reader-window.fxml").openStream());
    bzsfMqttClient = new BzsfWeatherMqttClient();
    WeatherReaderPaneController weatherReaderPaneController =
        loader.<WeatherReaderPaneController>getController();
    weatherReaderPaneController.setOutsideTemp(323.0F);

    bzsfMqttClient.setWeatherInfoCallback(e -> {
      LOGGER.fine("Outside temp: " + String.valueOf(e.getOutsideTempFarenheit()));
      Platform.runLater(() -> {
        weatherReaderPaneController.setOutsideTemp(e.getOutsideTempFarenheit());
        weatherReaderPaneController.setOutsideHumidity(e.getOutsideHumidity());
        weatherReaderPaneController.setInsideTemp(e.getInsideTempFarenheit());
        weatherReaderPaneController.setInsideHumidity(e.getInsideHumidity());
        weatherReaderPaneController.setWindDirection(e.getWindSpeedDirectionDegrees());
        weatherReaderPaneController.setWindGustSpeed(e.getWindGustSpeedMph());
        weatherReaderPaneController.setUpdatedTime();
      });
    });

    primaryStage.setTitle("BZSF Weather Reader");

    Scene scene = new Scene(weatherReaderDisplayPane, 280, 160);
    primaryStage.setScene(scene);
    primaryStage.show();
    bzsfMqttClient.connect();
  }
}
