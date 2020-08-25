package apim.ui.core;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

public class UpdatesResults {
    private static final InfluxDB INFLXUDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
    private static final String DB_NAME = "ktSelenium";

    static {
        INFLXUDB.setDatabase(DB_NAME);
    }

    public static void post(final Point point) {
        INFLXUDB.write(point);
    }

}
