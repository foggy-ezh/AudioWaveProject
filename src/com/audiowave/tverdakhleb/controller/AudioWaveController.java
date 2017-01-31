package com.audiowave.tverdakhleb.controller;

import com.audiowave.tverdakhleb.command.CommandType;
import com.audiowave.tverdakhleb.command.ICommandAction;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebServlet("/AudioWave")
@MultipartConfig
public class AudioWaveController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CONNECTIONS_COUNT = 20;
    private static final String PROCESS = "process";
    private static final String REDIRECT = "redirect";

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, e);
            throw new RuntimeException(e);
        }
        ConnectionPool.getInstance(CONNECTIONS_COUNT);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ICommandAction command = CommandType.defineCommand(request);
        String page =  command.execute(request);
        if(REDIRECT.equals(request.getSession().getAttribute(PROCESS))){
            response.sendRedirect(page);
        } else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().closeConnections();
    }
}
