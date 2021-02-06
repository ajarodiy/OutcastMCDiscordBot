package OutCastMCBot.reports;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.utils.CheckRole;
import OutCastMCBot.utils.Error;
import com.google.common.collect.Multimap;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class ReportCommand implements CommandInterface {

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        if (!CheckRole.hasRole(e.getMember(), Constants.roleOutcastTeamID)){
            Error.run(e.getChannel());return;
        }
        Multimap<User,String> map = ReportMap.getMap();
        User user = e.getMember().getUser();
        if (map.containsKey(user)) {
            map.removeAll(user);
        }
        map.put(user, "temporary");
        ReportMap.setMap(map);
        ReportMap.getChannelmap().put(user, e.getChannel());
        e.getChannel().sendMessage("**Enter Username: **").queue();
    }

    @Override
    public String getCommand() {
        return "report";
    }

    @Override
    public String getHelp() {
        return "Generate a Punishment Report (Staff Only)\n**Usage:** `!report`";
    }
}
