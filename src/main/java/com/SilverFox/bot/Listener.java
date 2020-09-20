package com.SilverFox.bot;


import com.SilverFox.bot.config.Config;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} Loaded", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        User user = event.getAuthor();
        Config config = null;
        try {
            config = new Config(new File("Config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

         final long guildId = event.getGuild().getIdLong();
        String prefix = config.getString("prefix");
         String raw = event.getMessage().getContentRaw();

         if (raw.equalsIgnoreCase(prefix + "shutdown")
              && user.getId().equals(config.getString("owner_id"))) {
        LOGGER.info("Shutting down");
        event.getJDA().shutdown();
        BotCommons.shutdown(event.getJDA());

         return; }

        if (raw.startsWith(prefix)) {
            try {
                manager.handle(event, prefix);
            } catch (SQLException throwables) {
               throwables.printStackTrace();
            }
         }
     }}
