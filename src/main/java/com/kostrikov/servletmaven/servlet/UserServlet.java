package com.kostrikov.servletmaven.servlet;

import com.kostrikov.servletmaven.dto.NewUserDto;
import com.kostrikov.servletmaven.dto.UserDto;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.exception.ValidationException;
import com.kostrikov.servletmaven.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String button = req.getParameter("action").toUpperCase();
        if (button.equals("EDIT")) {
            UserDto userDto = UserDto.builder()
                    .id(Long.parseLong(req.getParameter("id")))
                    .name(req.getParameter("name"))
                    .age(req.getParameter("age"))
                    .email(req.getParameter("email"))
                    .build();
            try {
                userService.update(userDto);
                resp.sendRedirect("/user");
            } catch (ValidationException e) {
                req.setAttribute("errors", e.getErrors());
                doGet(req, resp);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        } else if (button.equals("DELETE")) {
            userService.delete(Long.parseLong(req.getParameter("id")));
            resp.sendRedirect("/login");

        }
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        Long id = user.getId();
        req.setAttribute("user", userService.findUserById(id).get());
        req.getRequestDispatcher("/WEB-INF/jsp/userCard.jsp").forward(req, resp);
    }
}
