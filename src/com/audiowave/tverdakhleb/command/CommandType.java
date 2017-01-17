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

    abstract public ICommandAction getCommand();

    static public ICommandAction getCurrentCommand(HttpServletRequest request){
        ICommandAction command = new EmptyCommand();

        String action = request.getParameter(COMMAND);
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
