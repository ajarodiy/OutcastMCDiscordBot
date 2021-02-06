package OutCastMCBot.events;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MemberJoin extends ListenerAdapter {

    // Welcome Message
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent e) {
        e.getGuild().getTextChannelById(Constants.welcomemessagechannelID).sendMessage(
                "**(!)** Welcome, " + e.getMember().getUser().getAsMention() + " to the Official **OutcastMC** Discord!\n" +
                        "\n" +
                        ":scroll: **OutcastMC Information**\n" +
                        "<#804786704387932200>"+
                        "\n\n" +
                        "**IP:** play.outcastmc.org\n" +
                        "**SHOP:** http://store.outcastmc.org/\n" +
                        "**DISCORD:** https://discord.gg/DXS9HrK\n" +
                        "\n" +
                        "(( Tip: Create a new Discord Link for Invite Rewards! ))"
        ).queue();
    }

}
