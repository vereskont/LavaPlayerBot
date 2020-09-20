package com.SilverFox.bot.command;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx) throws SQLException;


    String getName();

    String getHelp();

    default List<String> getAliases() {
        return Arrays.asList(); // use Arrays.asList if you are on java 8
    }
}
