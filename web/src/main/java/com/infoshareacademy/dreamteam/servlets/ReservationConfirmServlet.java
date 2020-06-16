package com.infoshareacademy.dreamteam.servlets;

import com.infoshareacademy.dreamteam.domain.view.ReservationView;
import com.infoshareacademy.dreamteam.freemarker.TemplatePrinter;
import com.infoshareacademy.dreamteam.initializer.ModelInitializer;
import com.infoshareacademy.dreamteam.service.ReservationService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebServlet("/confirm")
public class ReservationConfirmServlet extends HttpServlet {


    @Inject
    private ReservationService reservationService;

    @Inject
    private TemplatePrinter templatePrinter;

    @Inject
    private ModelInitializer modelInitializer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        Map<String, Object> model = modelInitializer.initModel(req);
        String reservationToken = req.getParameter("token");
        Optional<ReservationView> reservationOpt = reservationService.findReservationViewByToken(reservationToken);
        if (reservationOpt.isPresent()) {
            model.put("result", reservationService.getConfirmationResult(reservationOpt.get()));
            model.put("title", reservationOpt.get().getBookView().getTitle());
            templatePrinter.printTemplate(resp, model, getServletContext(),
                    "confirmation.ftlh");
        } else {
            templatePrinter.printTemplate(resp, model, getServletContext(),
                    "wrong-token.ftlh");
        }
    }

}