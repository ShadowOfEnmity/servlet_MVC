package com.kostrikov.servletmaven.servlet;

import com.kostrikov.servletmaven.dto.UserDto;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/login")
public class AuthenticationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userService.login(req.getParameter("login"), req.getParameter("pwd")).ifPresentOrElse(userDto -> onLoginSuccess(userDto, req, resp), () ->
                    onLoginFail(req, resp));
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login?error&email=" + req.getParameter("login"));
    }

    @SneakyThrows
    private void onLoginSuccess(UserDto userDto, HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.setAttribute("user", userDto);
        resp.sendRedirect("/menu");
    }
}
