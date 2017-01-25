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
    }
    ,
    REGISTER {
        @Override
        public ICommandAction getCommand() {return new CommandRegister();}
    };

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "command";

    abstract public ICommandAction getCommand();

    public static ICommandAction defineCommand(HttpServletRequest request){
        String action = request.getParameter(COMMAND);
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
