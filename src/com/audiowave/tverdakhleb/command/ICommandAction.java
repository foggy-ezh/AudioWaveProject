package com.audiowave.tverdakhleb.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ICommandAction {
    String execute(HttpServletRequest request) throws IOException, ServletException;
}