package com.kostrikov.servletmaven.servlet;

import com.kostrikov.servletmaven.dto.ProductDto;
import com.kostrikov.servletmaven.exception.DaoException;
import com.kostrikov.servletmaven.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/goods")
public class GoodsServlet extends HttpServlet {
    private final ProductService prodService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<ProductDto> goods = prodService.getAllGoods();
            req.setAttribute("goods", goods);
            req.getRequestDispatcher("/WEB-INF/jsp/goods.jsp").forward(req, resp);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
