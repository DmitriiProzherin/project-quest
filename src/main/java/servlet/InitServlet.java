package servlet;

import entity.Game;
import entity.Location;
import entity.Player;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"/init"})
public class InitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("player") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (session.getAttribute("game") == null) {
            Player player = (Player) session.getAttribute("player");

            Game game = new Game(player, Location.HOME);

            session.setAttribute("game", game);

            session.setAttribute("ip", req.getRemoteAddr());

            resp.setStatus(HttpServletResponse.SC_CREATED);
        }
    }
}
