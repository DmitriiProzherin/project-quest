package servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Classes;
import entity.Player;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.EnumUtils;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(value = "/player")
public class PlayerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Object object = session.getAttribute("player");

        if (object != null && object.getClass() == Player.class) {
            ObjectMapper mapper = new ObjectMapper();
            String player = mapper.writeValueAsString(object);

            response.getWriter().write(player);
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        ObjectMapper mapper = new ObjectMapper();

//        Player player = mapper.readValue(getRequestBody(request), Player.class);

        JsonNode node = mapper.readTree(getRequestBody(request));

        JsonNode name = node.get("name");
        JsonNode gameClass = node.get("class");

        if (name == null || gameClass == null || !EnumUtils.isValidEnum(Classes.class, gameClass.asText().toUpperCase())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (session.getAttribute("player") != null) {
            Player player = (Player) session.getAttribute("player");
            player.setName(name.asText());
            player.setPlayerClass(EnumUtils.getEnum(Classes.class, gameClass.asText().toUpperCase()));

            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            Player player = new Player(name.asText(), EnumUtils.getEnum(Classes.class, gameClass.asText().toUpperCase()));

            session.setAttribute("player", player);

            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();

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
