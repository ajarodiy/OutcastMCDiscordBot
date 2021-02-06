package OutCastMCBot.invites;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class WhoInvited implements CommandInterface {
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
            ResultSet result = statement.executeQuery("SELECT INVITEDBY FROM InvitesTable WHERE ID = " + userid);
            if (result.next()) {
                String invitedby = result.getString(1);
                if (invitedby==null) embed.setDescription(String.format("**%s**'s inviter is unknown.",e.getGuild().getMemberById(userid).getUser().getAsTag()));
                else embed.setDescription(String.format("**%s** was invited by **%s**",e.getGuild().getMemberById(userid).getUser().getAsTag(),
                        e.getGuild().getMemberById(invitedby).getUser().getAsTag()));
            }else embed.setDescription(String.format("**%s**'s inviter is unknown.",e.getGuild().getMemberById(userid).getUser().getAsTag()));
            e.getChannel().sendMessage(embed.build()).queue();
            con.close();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "whoinvited";
    }

    @Override
    public String getHelp() {
        return "Check who invited a specific user\n**Usage:** `!whoinvited {user}";
    }
}
