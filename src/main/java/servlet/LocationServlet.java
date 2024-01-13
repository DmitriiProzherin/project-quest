package servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import entity.Game;
import entity.Location;
import manager.ResourceManager;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.Request;

import java.io.IOException;
import java.net.URISyntaxException;

import static java.util.Objects.isNull;

@WebServlet(urlPatterns = {"/location"})
public class LocationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        Game game = (Game) session.getAttribute("game");

        if (isNull(game)) {
            throw new RuntimeException();
        }

        String text;

        try {
            text = ResourceManager.getLocationText(game.getLocation());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        resp.setContentType("text/plain");

        resp.getWriter().write(text);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        Game game = (Game) session.getAttribute("game");

        String json = Request.getRequestBody(req);

        ObjectMapper mapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();

        JsonNode node = mapper.readTree(json);

        if (isNull(node)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException("Empty is request");
        }

        JsonNode locationNode = node.get("location");

        if (isNull(locationNode)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException("Location is null");
        }

        Location location = Enum.valueOf(Location.class, locationNode.asText().toUpperCase());

        game.setLocation(location);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
