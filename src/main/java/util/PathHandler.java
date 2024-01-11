package util;

import entity.Location;

import java.io.File;

public class PathHandler {
    public static File getLocation(Location location){
        return new File( "src/main/resources/text/"+location.toString().toLowerCase()+".txt");
    }
}
