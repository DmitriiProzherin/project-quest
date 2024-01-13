package manager;

import entity.Game;
import entity.Location;

import java.util.List;

public class GameManager {
    public static List<Location> getAvailableDirections(Game game){
        if (game.getLocation() == null) {
            System.out.println("Game location is null");
            throw new RuntimeException();
        }
        return switch (game.getLocation()) {
            case HOME -> List.of(Location.CITY_TEMPLE, Location.CITY_GATE, Location.TAVERN);
            case CITY_GATE -> List.of(Location.CITY_TEMPLE, Location.HOME, Location.TAVERN, Location.FOREST);
            case TAVERN -> List.of(Location.CITY_GATE, Location.HOME);
            case TAVERN_BACKYARD -> List.of(Location.TAVERN);
            case CITY_TEMPLE -> List.of(Location.HOME, Location.TAVERN, Location.CITY_GATE);
            case FOREST -> List.of(Location.CITY_GATE, Location.RIVER, Location.HIDDEN_TEMPLE);
            case RIVER -> List.of(Location.FOREST, Location.CAVE);
            case CAVE -> List.of(Location.RIVER);
            case HIDDEN_TEMPLE, FOREST_CAMP -> List.of(Location.FOREST);
        };
    }
}
