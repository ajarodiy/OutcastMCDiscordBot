package OutCastMCBot.invites;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.utils.Error;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class InviteBonus implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            Error.run(e.getChannel()); return;
        };
        if (args.size()<2) {
            e.getChannel().sendMessage("Please mention a user and an amount.").queue(); return;
        }
        try {
            Connection con = DriverManager.getConnection(Constants.sqldbURL, Constants.sqldbUsername, Constants.sqldbPassword);
            Statement statement = con.createStatement();
            EmbedBuilder embed = new EmbedBuilder();
            String userid = e.getMember().getId();

            if (args.get(0).startsWith("<@")) {
                userid = args.get(0).replaceAll("<@","").replaceAll(">","").replaceAll("!","");
            }else{
                e.getChannel().sendMessage("Please mention a valid user.").queue(); return;
            }
            int amount=0;
            try{
                amount = Integer.parseInt(args.get(1));
            }catch (Exception exception) {
                e.getChannel().sendMessage("Please enter a valid amount.").queue(); return;
            }
            ResultSet exists = statement.executeQuery("SELECT COUNT(*) FROM InvitesTable WHERE ID = " + userid); exists.next();
            if (exists.getInt(1)==0) {
                statement.execute(String.format("INSERT INTO InvitesTable VALUES(%s, null, 0, 0, %d", userid, amount));
            }else{
                statement.execute(String.format("UPDATE InvitesTable SET BONUSINVITES = BONUSINVITES + %d WHERE ID = %s", amount, userid));
            }
            e.getChannel().sendMessage(amount + " invites have been successfully added for " + e.getGuild().getMemberById(userid).getAsMention()).queue();
            con.close();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "bonus";
    }

    @Override
    public String getHelp() {
        return "Give invites bonus to a user (Staff Only)\n**Usage:** `!bonus {user} [amount]`";
    }
}
