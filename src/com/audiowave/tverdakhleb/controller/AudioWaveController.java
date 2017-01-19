package com.audiowave.tverdakhleb.controller;

import com.audiowave.tverdakhleb.command.CommandType;
import com.audiowave.tverdakhleb.command.ICommandAction;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebServlet("/AudioWave")
public class AudioWaveController extends HttpServlet {
    private static final int CONNECTIONS_COUNT = 20;

    @Override
    public void init() throws ServletException {
        super.init();
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
        ICommandAction command = CommandType.getCurrentCommand(request);

        String page = command.execute(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().closeConnections();
    }
}
