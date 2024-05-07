package com.kostrikov.servletmaven.servlet;

import com.kostrikov.servletmaven.dto.NewUserDto;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.exception.ValidationException;
import com.kostrikov.servletmaven.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        NewUserDto userDto = NewUserDto.builder()
                .name(req.getParameter("name"))
                .age(req.getParameter("age"))
                .email(req.getParameter("email"))
                .login(req.getParameter("login"))
                .pwd(req.getParameter("pwd"))
                .build();
        try {
            userService.create(userDto);
            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
    }
}
