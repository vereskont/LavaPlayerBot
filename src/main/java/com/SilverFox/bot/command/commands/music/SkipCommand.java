package com.SilverFox.bot.command.commands.music;

import com.SilverFox.bot.command.CommandContext;
import com.SilverFox.bot.command.ICommand;
import com.SilverFox.bot.music.GuildMusicManager;
import com.SilverFox.bot.music.PlayerManager;
import com.SilverFox.bot.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.logging.Logger;

public class SkipCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        Logger logger = Logger.getLogger("MyLog");
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());
        TrackScheduler scheduler = musicManager.scheduler;
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("На данный момент ничего не играет").queue();
            return;
        }

        scheduler.nextTrack();

        ctx.getMessage().addReaction("\u2705").complete();
        Runtime.getRuntime().gc();
    }

    @Override
    public String getHelp() {
        return "Skips the current song";
    }

    @Override
    public String getName() {
        return "skip";
    }
}
