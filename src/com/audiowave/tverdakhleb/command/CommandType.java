package com.audiowave.tverdakhleb.command;

import javax.servlet.http.HttpServletRequest;

public enum CommandType {
    CHANGE_LANGUAGE {
        @Override
        public ICommandAction getCommand() {return new CommandChangeLanguage();}
    };

    private static final String COMMAND = "command";

    abstract public ICommandAction getCommand();

    static public ICommandAction getCurrentCommand(HttpServletRequest request){
        ICommandAction command = new EmptyCommand();

        String action = request.getParameter(COMMAND);
        if(action == null || action.isEmpty()){
            return command;
        }
        CommandType enumCommand = CommandType.valueOf(action.toUpperCase());
        command = enumCommand.getCommand();
        return command;
    }
}
