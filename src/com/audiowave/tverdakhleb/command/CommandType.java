package com.audiowave.tverdakhleb.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public enum CommandType {

    CHANGE_LANGUAGE {
        @Override
        public ICommandAction getCommand() {return new CommandChangeLanguage();}
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
        String action = request.getParameter(LAST_COMMAND);
        return defineCommand(action);
    }

    static private ICommandAction defineCommand(String action){
        ICommandAction command = new EmptyCommand();
        if(action == null || action.isEmpty()){
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
