package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.manager.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EmptyCommand implements ICommandAction {
    private static final String PATH_PAGE_MAIN = "path.page.main";
    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        ConfigurationManager config = new ConfigurationManager();
        return config.getProperty(PATH_PAGE_MAIN);
    }
}
