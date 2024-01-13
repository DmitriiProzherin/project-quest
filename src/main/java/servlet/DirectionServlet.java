package servlet;

import entity.Game;
import entity.Location;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import manager.GameManager;
import mapper.Mapper;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/directions")
public class DirectionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        Game game = (Game) session.getAttribute("game");

        List<Location> directions = GameManager.getAvailableDirections(game);

        resp.setContentType("application/json");

        resp.getWriter().write(Mapper.objAsJSONString(directions));
    }

}
