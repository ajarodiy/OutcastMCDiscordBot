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
import java.util.Objects;

public class InvitesLeaderboard implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        try {
            Connection con = DriverManager.getConnection(Constants.sqldbURL, Constants.sqldbUsername, Constants.sqldbPassword);
            Statement statement = con.createStatement();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("<" + Constants.ticketemoji + ">  OutcastMC Invites Leaderboard")
                    .setColor(Constants.ticketcolor).setFooter(Constants.serverip, Constants.iconurl);

            ResultSet result = statement.executeQuery("SELECT * FROM InvitesTable ORDER BY REGULARINVITES-INVITESLEFT+BONUSINVITES DESC");
            StringBuilder description = new StringBuilder();
            int cnt=1;
            while (result.next() && cnt<=10) {
                int total=0, regular=0, left=0, bonus=0; String id = result.getString(1);
                regular = result.getInt(3); left = result.getInt(4);
                bonus = result.getInt(5);
                total = regular-left+bonus;
                if (e.getGuild().getMemberById(id)==null) continue;
                description.append(String.format("`%d. `%s **: %d** invites. (**%d** regular, **%d** left, **%d** bonus)",
                        cnt, e.getGuild().getMemberById(id).getUser().getAsMention(), total, regular, left, bonus)).append("\n");
                cnt++;
            }
            embed.setDescription(description.toString());
            e.getChannel().sendMessage(embed.build()).queue();
            con.close();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "leaderboard";
    }

    @Override
    public String getHelp() {
        return "Check the invites leaderboard\n**Usage:** `!leaderboard`";
    }
}
