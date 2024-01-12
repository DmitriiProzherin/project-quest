package manager;

import entity.Location;
import org.apache.commons.io.file.PathUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceManagerTest {
    @Disabled
    @ParameterizedTest
    @EnumSource(Location.class)
    public void getLocationTextTest(Location location) {
        Path path = Path.of("src/main/resources/text/"+ location.toString().toLowerCase() +".txt");

        String text;

        try {
            text = PathUtils.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (MockedStatic<ResourceManager> mockedStatic = Mockito.mockStatic(ResourceManager.class)) {
            mockedStatic.when(() -> ResourceManager.getLocationText(location)).thenReturn(text);
            assertEquals(text, ResourceManager.getLocationText(location));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}