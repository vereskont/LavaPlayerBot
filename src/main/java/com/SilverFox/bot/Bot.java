package com.SilverFox.bot;

import com.SilverFox.bot.config.Config;
import net.dv8tion.jda.api.JDABuilder;
import java.io.FileWriter;
import java.io.IOException;

import javax.security.auth.login.LoginException;
import java.io.File;

public class Bot {

    public static void main(String[] args) throws LoginException, IOException {
        new Bot();
        File file = new File("Config.json");
        if(!file.exists())
            if(file.createNewFile()) {
                System.out.println("Created File");

            }

    }
         private Bot() throws LoginException, IOException {
             File file = new File("Config.json");
             if(!file.exists())
                 if(file.createNewFile()) {
                     System.out.println("Created File");

                 }
             if(file.length() == 0) {
                 System.out.println("File is empty. Please add bot TOKEN and PREFIX in the file Config.json!");
                 FileWriter writer = new FileWriter("Config.json");
                 writer.write("{\n" +
                         "\"token\":" + "\"" + "YOU TOKEN HERE" + "\"," + "\n" +
                         "\"prefix\":" + "\"" + "YOU PREFIX HERE" + "\"," + "\n" +
                         "}");
                 writer.close();
             }
             Config config = new Config(new File("Config.json"));
            new JDABuilder()
                    .setToken(config.getString("token"))

                    .addEventListeners(new Listener())
                    .build();

        }

}
