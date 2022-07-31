package us.bzsf.weatherreader;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for BZSF Weather Reader Pane
 */
public class WeatherReaderPaneController {
  
  @FXML
  private Label label_outside_temp;
  @FXML
  private Label label_outside_humidity;
  @FXML
  private Label label_inside_temp;
  @FXML
  private Label label_inside_humidity;
  @FXML
  private Label label_wind_direction;
  @FXML
  private Label label_wind_gust_speed;
  @FXML
  private Label label_last_updated;

  public void setOutsideTemp(Float outsideTemp) {
    this.label_outside_temp.setText(String.valueOf(outsideTemp) + "°");
  }

  public void setOutsideHumidity(Float outsideHumidity) {
    this.label_outside_humidity.setText(String.valueOf(outsideHumidity + "%"));
  }

  public void setInsideTemp(Float insideTemp) {
    this.label_inside_temp.setText(String.valueOf(insideTemp)+ "°");
  }

  public void setInsideHumidity(Float insideHumidity) {
    this.label_inside_humidity.setText(String.valueOf(insideHumidity + "%"));
  }

  public void setWindDirection(Float windDirectionDegrees, String windDirectionCardinal) {
    this.label_wind_direction.setText(
      String.valueOf(windDirectionDegrees) + "° " + windDirectionCardinal);
  }

  public void setWindGustSpeed(Float windGustSpeed) {
    this.label_wind_gust_speed.setText(String.valueOf(windGustSpeed) + " mph");
  }

  /**
   * Sets "Last Updated" time in the UI to the current time
   */
  public void setUpdatedTime() {
    this.label_last_updated.setText(
      new SimpleDateFormat("hh:mm:ss a").format(new Date()));
  }
}
 