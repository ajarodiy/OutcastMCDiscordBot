package OutCastMCBot.reports;

import OutCastMCBot.Constants;
import com.google.common.collect.Multimap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;

public class ReportListener {
    public static void run(GuildMessageReceivedEvent e) {
        User user = e.getMember().getUser();
        String msg = e.getMessage().getContentRaw();
        Multimap<User,String> map = ReportMap.getMap();
        if (!map.containsKey(user)) return;
        if (e.getChannel() != ReportMap.getChannelmap().get(user)) return;
        TextChannel channel = e.getChannel();
        switch (map.get(user).size()) {
            case 1:
                map.get(user).add(msg);
                channel.sendMessage("**Enter reason for punishment: **").queue();
                break;
            case 2:
                map.get(user).add(msg);
                channel.sendMessage("**Enter punishment: **").queue();
                break;
            case 3:
                map.get(user).add(msg);
                channel.sendMessage("**Enter link to proof: **").queue();
                break;
            case 4:
                map.get(user).add(msg);
                EmbedBuilder embed = new EmbedBuilder();
                ArrayList<String> list = new ArrayList<>(map.get(user));
                embed.setTitle("<:OutcastMC:782035842293039124>  OutcastMC Reports").setColor(Constants.ticketcolor)
                        .setDescription("**PLAYER :** " + list.get(1) + "\n**REASON :** " + list.get(2) +
                                "\n**PUNISHMENT :** " + list.get(3) + "**\nPROOF :** [Link](" + list.get(4) + ")")
                        .setFooter("By " + user.getName(),Constants.iconurl);
                e.getChannel().sendMessage(embed.build()).queue(msg1 -> {
                    msg1.addReaction(Constants.checkmark).queue();
                    msg1.addReaction(Constants.crossmark).queue();
                });
                ReportMap.getMap().removeAll(user); ReportMap.getChannelmap().remove(user);
                break;
        }
    }
}
