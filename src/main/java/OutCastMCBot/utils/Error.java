package OutCastMCBot.utils;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Error {
    public static void run(TextChannel tc) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("<" + Constants.ticketemoji + "> OutcastMC ┃ Error!")
                .setDescription("You do not have permission to execute this command!")
                .setColor(Color.RED);
        tc.sendMessage(embed.build()).queue();
    }
}
