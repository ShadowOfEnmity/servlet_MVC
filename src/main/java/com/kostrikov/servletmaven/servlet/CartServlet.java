package com.kostrikov.servletmaven.servlet;

import com.kostrikov.servletmaven.dto.CartDto;
import com.kostrikov.servletmaven.dto.UserDto;
import com.kostrikov.servletmaven.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final CartService cartService = CartService.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var cartInfo = cartService.getCartInfo(((UserDto) req.getSession().getAttribute("user")).getId());
        req.setAttribute("cartInfo", cartInfo);
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Long, Integer> goodsQuantity = new HashMap<>();
        List<String> names = Collections.list(req.getParameterNames());
        Pattern pattern = Pattern.compile("quantity_(?<number>\\d+)");
        List<String> requiredNames = names.stream().filter(s -> s.matches("quantity_\\d+")).toList();
        for (String name : requiredNames) {
            Matcher matcher = pattern.matcher(name);
            if (matcher.find()) {
                var groupNumber = Long.parseLong(matcher.group("number"));
                var quantity = Integer.parseInt(req.getParameter(name));
                if (quantity > 0) goodsQuantity.put(groupNumber, quantity);
            }
        }
        List<CartDto> cartInfo = null;
        if (goodsQuantity.size() > 0) {
            cartService.getProductsInCart((UserDto) req.getSession().getAttribute("user"), goodsQuantity);
        }
        req.setAttribute("cartInfo", cartInfo);
        resp.sendRedirect("/cart");
    }
}
