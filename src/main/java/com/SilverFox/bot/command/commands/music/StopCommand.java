package com.SilverFox.bot.command.commands.music;

import com.SilverFox.bot.command.CommandContext;
import com.SilverFox.bot.command.ICommand;
import com.SilverFox.bot.music.GuildMusicManager;
import com.SilverFox.bot.music.PlayerManager;

import java.util.logging.Logger;

public class StopCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());
            musicManager.scheduler.getQueue().clear();
            musicManager.player.stopTrack();
            musicManager.player.setPaused(false);
        Runtime.getRuntime().gc();
        ctx.getMessage().addReaction("\u2705").complete();

       }

    @Override
    public String getHelp() {
        return "Останавливает плеер";
    }

    @Override
    public String getName() {
        return "stop";
    }
}
