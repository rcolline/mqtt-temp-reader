package us.bzsf.weatherreader;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import us.bzsf.weatherreader.BzsfWeatherMqttClient.WeatherInfoCallback;

/**
 * A model class for relevant weather info collected from the BZSF weather station.
 */
public class WeatherInfo {

  private float outsideTempFarenheit;
  private float insideTempFarenheit;
  private float outsideHumidity;
  private float insideHumidity;
  private float rainFallTotal24hrInches;
  private float gustWindSpeed;
  private float windGustSpeedMph;
  private float windSpeedDirectionDegrees;

  // Default no-op weather info callback.
  private WeatherInfoCallback weatherInfoCallback = e -> { return; };

  /**
   * Extracts weather data from the BZSF mqtt weather topic.
   * 
   * @param topic incoming topic which is a parameter from the Davis weather station.
   * @param message the contents of the message to be parsed.
   */
  public void parseMessageAndUpdateWeatherInfo(String topic, MqttMessage message) {
    // Parse the topic to set the right variable.
    switch (topic) {
      case "weather/inTemp_F":
        insideTempFarenheit = Float.parseFloat(new String(message.getPayload()));
        break;
      case "weather/outTemp_F":
        outsideTempFarenheit = Float.parseFloat(new String(message.getPayload()));
        break;
      case "weather/inHumidity":
        insideHumidity = Float.parseFloat(new String(message.getPayload()));
        break;
      case "weather/outHumidity":
        outsideHumidity = Float.parseFloat(new String(message.getPayload()));
        break;
      case "weather/rain24_in":
        rainFallTotal24hrInches = Float.parseFloat(new String(message.getPayload()));
        break;
      case "weather/windGust_mph":
        windGustSpeedMph = Float.parseFloat(new String(message.getPayload()));
        break;
      case "weather/windDir":
        windSpeedDirectionDegrees = Float.parseFloat(new String(message.getPayload()));
        break;
    }

    // Update interested party
    weatherInfoCallback.onWeatherInfoArrived(this);
  }

  public float getOutsideTempFarenheit() {
    return outsideTempFarenheit;
  }

  public float getInsideTempFarenheit() {
    return insideTempFarenheit;
  }

  public float getOutsideHumidity() {
    return outsideHumidity;
  }

  public float getInsideHumidity() {
    return insideHumidity;
  }

  public float getRainFallTotal24hrInches() {
    return rainFallTotal24hrInches;
  }

  public float getGustWindSpeed() {
    return gustWindSpeed;
  }

  public float getWindSpeedDirectionDegrees() {
    return windSpeedDirectionDegrees;
  }

  public String getWindSpeedStringCardinalDirection() {
    return CardinalDirection.getCardinalDirectionByDegrees((int) windSpeedDirectionDegrees)
        .toString();
  }

  public float getWindGustSpeedMph() {
    return windGustSpeedMph;
  }

  public void setWeatherInfoCallback(WeatherInfoCallback weatherInfoCallback) {
    this.weatherInfoCallback = weatherInfoCallback;
  }
}