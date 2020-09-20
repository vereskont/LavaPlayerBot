package com.SilverFox.bot.command.commands.music;

import com.SilverFox.bot.command.CommandContext;
import com.SilverFox.bot.command.ICommand;
import com.SilverFox.bot.music.PlayerManager;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;


public class PlayCommand implements ICommand {

    private final YouTube youTube;

    public PlayCommand() {
        YouTube temp = null;


        try {
            temp = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null
            )
                    .setApplicationName("Bot")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        youTube = temp;
    }

    @Override
    public void handle(CommandContext ctx) {
        VoiceChannel voiceChannel = Objects.requireNonNull(ctx.getMember().getVoiceState()).getChannel();
        AudioManager audioManager = ctx.getGuild().getAudioManager();
        List<String> args = ctx.getArgs();
        audioManager.openAudioConnection(voiceChannel);
        String input = String.join(" ", args);

        if (args.isEmpty()) {
        ctx.getChannel().sendMessage("Укажите название музыки").queue();
        return;
        }
        if (!isUrl(input)) {
            String ytSearched = searchYoutube(input);

            if(ytSearched == null) {
                    ctx.getChannel().sendMessage("Ютуб ничего не нашёл по вашему запросу").queue();

            }

            input = ytSearched;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(ctx.getChannel(), input);

    }

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Nullable
    private String searchYoutube(String input) {
        try {
            List<SearchResult> results = youTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey("AIzaSyBxN2sxyCUJFv9lV6SQmelNCZmGWElJkS0")
                    .execute()
                    .getItems();

            if (!results.isEmpty()) {
                String videoId = results.get(0).getId().getVideoId();

                return "https://www.youtube.com/watch?v=" + videoId;
            }
            Runtime.getRuntime().gc();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public String getName() {
        return "p";
    }

    @Override
    public String getHelp() {
        return "Старт музыки";
    }


    @Override
    public List<String> getAliases() {
        return Arrays.asList("play", "плей");
    }
}
