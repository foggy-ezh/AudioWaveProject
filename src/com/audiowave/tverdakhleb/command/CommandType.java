package com.audiowave.tverdakhleb.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public enum CommandType {

    CHANGE_LANGUAGE {
        @Override
        public ICommandAction getCommand() {return new CommandChangeLanguage();}
    },
    LOG_IN {
        @Override
        public ICommandAction getCommand() {return new CommandLogIn();}
    };

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "command";
    private static final String LAST_COMMAND = "last_command";

    abstract public ICommandAction getCommand();

    static public ICommandAction getCurrentCommand(HttpServletRequest request){
        String action = request.getParameter(COMMAND);
        return defineCommand(action);
    }

    static public ICommandAction getLastCommand(HttpServletRequest request){
        String action = String.valueOf(request.getSession().getAttribute(LAST_COMMAND));
        return defineCommand(action);
    }

    static private ICommandAction defineCommand(String action){
        ICommandAction command = new EmptyCommand();
        if(action == null || action.isEmpty() || action.equals("null")){
            return command;
        }
        try {
            CommandType enumCommand = CommandType.valueOf(action.toUpperCase());
            command = enumCommand.getCommand();
        } catch (IllegalArgumentException ex){
            LOGGER.log(Level.ERROR, ex);
        }
        return command;

    }
}
