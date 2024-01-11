package servlet;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import entity.Player;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;

import static java.util.Objects.isNull;

@WebServlet(urlPatterns = {"/player", "/html/player"})
public class PlayerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        Object object = session.getAttribute("player");

        if (object != null && object.getClass() == Player.class) {
            ObjectMapper mapper = JsonMapper.builder()
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                    .build();

            String playerJSON = mapper.writeValueAsString(object);

            resp.setContentType("application/json");

            resp.getWriter().write(playerJSON);
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        ObjectMapper mapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();

        try {
            String json = getRequestBody(request);

            Player player = mapper.readValue(json, Player.class);

            if (player.getName().isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (isNull(player.getPlayerClass())) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (session.getAttribute("player") == null) {
                response.setStatus(HttpServletResponse.SC_CREATED);
            }
            else {
                response.setStatus(HttpServletResponse.SC_OK);
            }

            session.setAttribute("player", player);

            response.setContentType("application/json");

            Writer writer = response.getWriter();

            mapper.writeValue(writer, player);
        }
        catch (Exception exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("player") == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        session.removeAttribute("player");

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
}
