package util;

import entity.Location;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PathHandlerTest {
    @ParameterizedTest
    @EnumSource(Location.class)
    public void getLocationTest(Location location) {
        String path = "src/main/resources/text/"+location.toString().toLowerCase()+".txt";
        File file = new File(path);

        try (MockedStatic<PathHandler> mockedStatic = Mockito.mockStatic(PathHandler.class)) {
            mockedStatic.when(() -> PathHandler.getLocation(location)).thenReturn(file);
            assertEquals(file, PathHandler.getLocation(location));
        }
    }

}