package com.SilverFox.bot.command.commands.music;

import com.SilverFox.bot.command.CommandContext;
import com.SilverFox.bot.command.ICommand;
import com.SilverFox.bot.music.GuildMusicManager;
import com.SilverFox.bot.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class NowPlayingCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("Плеер не воспроизводит ни одной песни.").queue();

            return;
        }

        AudioTrackInfo info = player.getPlayingTrack().getInfo();

        channel.sendMessage(EmbedUtils.embedMessage(String.format(
                "**На данный момент играет:** [%s](%s)\n%s %s - %s",
                info.title,
                info.uri,
                player.isPaused() ? "\u23F8" : "▶",
                formatTime(player.getPlayingTrack().getPosition()),
                formatTime(player.getPlayingTrack().getDuration())
        )).build()).queue();
    }

    @Override
    public String getHelp() {
        return "Инфо о треке";
    }

    @Override
    public String getName() {
        return "np";
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(2);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(2);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(2) / TimeUnit.SECONDS.toMillis(2);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
