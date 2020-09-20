package com.SilverFox.bot.command.commands;

import com.SilverFox.bot.command.CommandContext;
import com.SilverFox.bot.command.ICommand;
import net.dv8tion.jda.api.JDA;

public class Ping implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping) -> ctx.getChannel()
                        .sendMessageFormat("Пинг бота: %sms\nПинг Сокета: %sms", ping, jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getHelp() {
        return "Нет информации";
    }

    @Override
    public String getName() {
        return "ping";
    }
}
