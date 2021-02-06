package OutCastMCBot.invites;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class InviteCommand implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        try {
            Connection con = DriverManager.getConnection(Constants.sqldbURL, Constants.sqldbUsername, Constants.sqldbPassword);
            Statement statement = con.createStatement();
            EmbedBuilder embed = new EmbedBuilder();
            String userid = e.getMember().getId();

            if (args.size() > 0 && args.get(0).startsWith("<@")) {
                userid = args.get(0).replaceAll("<@","").replaceAll(">","").replaceAll("!","");
            }
            embed.setTitle(e.getGuild().getMemberById(userid).getUser().getAsTag())
                    .setColor(Constants.ticketcolor).setFooter(Constants.serverip, Constants.iconurl);
            ResultSet result = statement.executeQuery("SELECT * FROM InvitesTable WHERE ID = " + userid);
            int total=0, regular=0, left=0, bonus=0;
            if (result.next()) {
                regular = result.getInt(3); left = result.getInt(4);
                bonus = result.getInt(5);
            }
            total = regular-left+bonus;
            embed.setDescription(String.format("%s currently have **%d** invites. (**%d** regular, **%d** left, **%d** bonus)", e.getGuild().getMemberById(userid).getUser().getAsTag(),
                    total,regular,left,bonus));
            e.getChannel().sendMessage(embed.build()).queue();
            con.close();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "invites";
    }

    @Override
    public String getHelp() {
        return "Check your invites\n**Usage:** `!invites {user}`";
    }
}
