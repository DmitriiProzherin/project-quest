package manager;

import entity.Location;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.isNull;

public class ResourceManager {
    public static String getLocationText(Location location) throws IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        URL resourceURL = classLoader.getResource("text/"+location.toString().toLowerCase()+".txt");

        if (isNull(resourceURL)) {
            throw new RuntimeException();
        }

        return FileUtils.readFileToString(new File(resourceURL.toURI()), StandardCharsets.UTF_8);
    }
}
