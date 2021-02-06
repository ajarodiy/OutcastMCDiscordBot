package OutCastMCBot.applications;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.utils.CheckRole;
import OutCastMCBot.utils.Error;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class LockCommand implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        if (!CheckRole.hasRole(e.getMember(), Constants.roleStaffApplicationsID)) {
            Error.run(e.getChannel()); return;
        }
        TextChannel channel = e.getChannel();
        if (channel.getTopic()!=null && channel.getTopic().contains("Application")) {
            Member member = e.getGuild().getMemberById(channel.getTopic().substring(channel.getTopic().indexOf("Application")+14));
            if (ApplicationMap.getLockedchannels().contains(channel.getId())) {
                e.getMessage().delete().queue();
                channel.putPermissionOverride(member).setAllow(Permission.MESSAGE_WRITE).setAllow(Permission.MESSAGE_READ).queue();
                channel.sendMessage("This channel has been unlocked").queue();
                ApplicationMap.getLockedchannels().remove(channel.getId());
            }else {
                e.getMessage().delete().queue();
                channel.putPermissionOverride(member).setDeny(Permission.MESSAGE_WRITE).setAllow(Permission.MESSAGE_READ).queue();
                channel.sendMessage("This channel has been locked").queue();
                ApplicationMap.getLockedchannels().add(channel.getId());
            }
        }
    }

    @Override
    public String getCommand() {
        return "lock";
    }

    @Override
    public String getHelp() {
        return "Lock and unlock an application channel (Staff Only)\n**Usage:** `!lock`";
    }
}
