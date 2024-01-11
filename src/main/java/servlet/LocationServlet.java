package servlet;

import entity.Game;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static util.PathHandler.getLocation;

@WebServlet(urlPatterns = {"/location", "/html/location"})
public class LocationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        Game game = (Game) session.getAttribute("game");

        String text = FileUtils.readFileToString(getLocation(game.getLocation()), StandardCharsets.UTF_8);

        resp.setContentType("text/plain");

        resp.getWriter().write(text);
    }
}
