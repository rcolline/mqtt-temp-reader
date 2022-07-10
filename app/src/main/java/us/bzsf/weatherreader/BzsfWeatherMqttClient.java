package us.bzsf.weatherreader;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/*
 * Configures the Paho MQTT client to BZSF specificaitons and provides tailored utility methods to
 * interact with it ideally.
 */
public class BzsfWeatherMqttClient {

  private final String BZSF_MQTT_BROKER = "tcp://home.kains.bzsf.us:1883";
  private final String BZSF_TEMP_VIEWER_SUFFIX = "bzsf-temp-viewer";
  private static final Logger LOGGER = Logger.getLogger(BzsfWeatherMqttClient.class.getName());
  private static final String BZSF_WEATHER_TOPIC_FILTER = "weather/+";
  private MqttClient mqttClient;
  private WeatherInfo currentWeatherInfo = new WeatherInfo();

  /**
   * Creates new MQTT Client that is configured to talk to the BZSF broker
   */
  public BzsfWeatherMqttClient() {
    String hostname;
    try {
      hostname = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      hostname = "UNKNOWN";
    }
    String clientId =
        new StringBuilder().append(BZSF_TEMP_VIEWER_SUFFIX).append(' ').append(hostname).toString();
    try {
      this.mqttClient = new MqttClient(BZSF_MQTT_BROKER, clientId, new MemoryPersistence());
      this.mqttClient.setCallback(mqttCallback);
    } catch (MqttException e) {
      throw new RuntimeException("Cannot create mqtt client.");
    }
  }

  /**
   * Connects the mqtt client to the BZSF broker for the Weather Topic
   */
  public void connect() {
    try {
      mqttClient.connect();
      LOGGER.info("Connected to BZSF mqtt broker");
      mqttClient.subscribe(BZSF_WEATHER_TOPIC_FILTER);
    } catch (MqttException e) {
      LOGGER.log(Level.SEVERE, "MQTT connection failed", e);
      throw new RuntimeException("Cannot connect to mqtt broker.");
    }
  }

  public void setWeatherInfoCallback(WeatherInfoCallback weatherInfoCallback) {
    currentWeatherInfo.setWeatherInfoCallback(weatherInfoCallback);
  }

  /**
   * Updates weather info everytime a new message arrives
   */
  private MqttCallback mqttCallback = new MqttCallback() {

    @Override
    public void connectionLost(Throwable cause) {}

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
      currentWeatherInfo.parseMessageAndUpdateWeatherInfo(topic, message);
      LOGGER.fine(new StringBuilder().append("topic: ").append(topic).append('\n')
          .append("message: ").append(new String(message.getPayload())).append('\n').append("qos")
          .append(message.getQos()).append('\n').toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {}
  };

  /**
   * Callback interface when weather info is updated.
   */
  public static interface WeatherInfoCallback {

    /**
     * Returns the weather info when its updated by the weather station.
     * 
     * @param weatherInfo the current weather info.
     */
    public void onWeatherInfoArrived(WeatherInfo weatherInfo);
  }
};
