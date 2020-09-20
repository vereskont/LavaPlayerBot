package com.SilverFox.bot.command.commands;

import com.SilverFox.bot.CommandManager;
import com.SilverFox.bot.command.commands.music.PlayCommand;
import com.SilverFox.bot.config.Config;
import com.SilverFox.bot.Constants;
import com.SilverFox.bot.command.CommandContext;
import com.SilverFox.bot.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    Config config = null;

    {
        try {
            config = new Config(new File("Config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getAuthor().getIdLong() != Constants.OWNER) {
            ctx.getChannel().sendMessage(":exclamation: **Команда заблокированна**").queue();
            return;
        }

        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if (args.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            builder.append("Лист команд\n");

            manager.getCommands().stream().map(ICommand::getName).forEach(
                    (it) -> builder.append('`').append(config.getString("prefix")).append(it).append("`\n")
            );

            channel.sendMessage(builder.toString()).queue();

        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("Ничего не найдено " + search).queue();
        }

        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "null";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("commands", "cmds", "commandlist");
    }
}
