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
    },
    LOG_OUT {
        @Override
        public ICommandAction getCommand() {return new CommandLogOut();}
    },
    REGISTER {
        @Override
        public ICommandAction getCommand() {return new CommandRegister();}
    },
    SINGER {
        @Override
        public ICommandAction getCommand() {return new CommandSinger();}
    },
    ALBUM {
        @Override
        public ICommandAction getCommand() {return new CommandAlbum();}
    },
    CURRENT_SINGER {
        @Override
        public ICommandAction getCommand() {return new CommandCurrentSinger();}
    },
    CURRENT_ALBUM {
        @Override
        public ICommandAction getCommand() {return new CommandCurrentAlbum();}
    },
    UNBLOCK_AUDIO {
        @Override
        public ICommandAction getCommand() {return new CommandUnblockAudio();}
    },
    BLOCK_AUDIO {
        @Override
        public ICommandAction getCommand() {return new CommandBlockAudio();}
    },
    ADD_COMMENT{
        @Override
        public ICommandAction getCommand() {return new CommandAddComment();}
    },
    DELETE_COMMENT{
        @Override
        public ICommandAction getCommand() {return new CommandDeleteComment();}
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
