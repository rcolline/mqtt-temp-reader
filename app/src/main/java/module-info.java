module us.bzsf.weatherreader {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.logging;
    requires transitive org.eclipse.paho.client.mqttv3;

    opens us.bzsf.weatherreader to javafx.fxml;
    exports us.bzsf.weatherreader;
}
