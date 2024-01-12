package servlet;

import entity.Game;
import manager.ResourceManager;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URISyntaxException;

import static java.util.Objects.isNull;

@WebServlet(urlPatterns = {"/location", "/html/location"})
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
}
