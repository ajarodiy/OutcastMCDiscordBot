package OutCastMCBot.invites;

import OutCastMCBot.Constants;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class InviteTracking extends ListenerAdapter {
    private final Map<String, InviteData> invites = new ConcurrentHashMap<>();

    @Override
    public void onGuildInviteCreate(@NotNull GuildInviteCreateEvent e) {
        InviteData invitedata = new InviteData(e.getInvite());
        invites.put(e.getCode(), invitedata);
    }

    @Override
    public void onGuildInviteDelete(@NotNull GuildInviteDeleteEvent e) {
        invites.remove(e.getCode());
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent e) {
        if (e.getUser().isBot()) return;
        e.getGuild().retrieveInvites().queue(retrievedInvites -> {
            for (Invite invite : retrievedInvites) {
                InviteData currentInvite = invites.get(invite.getCode());
                if (currentInvite==null) continue;
                if (currentInvite.getUses() == invite.getUses()) continue;
                currentInvite.incrementUses();
                addToDatabase(e.getUser(), invite.getInviter());
//                e.getGuild().getTextChannelById(Constants.invitelogschannelID)
//                        .sendMessage(String.format("%s joined from %s's invite", e.getUser().getAsMention(), invite.getInviter().getAsMention())).queue();
                break;
            }
        });
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent e) {
        try {
            Connection con = DriverManager.getConnection(Constants.sqldbURL, Constants.sqldbUsername, Constants.sqldbPassword);
            Statement statement = con.createStatement();

            ResultSet result = statement.executeQuery("SELECT INVITEDBY FROM InvitesTable WHERE ID = " + e.getUser().getId());
            if (result.next()) {
                String userid = result.getString(1);
                if (!userid.equalsIgnoreCase("null")) {
                    statement.execute("UPDATE InvitesTable SET INVITESLEFT = INVITESLEFT+1 WHERE ID = " + userid);
                }
            }

            con.close();

        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent e) {
        storeInvites(e.getGuild());
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent e) {
        storeInvites(e.getGuild());
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent e) {
        invites.entrySet().removeIf(entry -> entry.getValue().getGuildid().equals(e.getGuild().getId()));
    }

    public void storeInvites(Guild guild) {
        guild.retrieveInvites().queue(retrievedInvites -> {
            retrievedInvites.forEach(invite ->
                    invites.put(invite.getCode(), new InviteData(invite)));
        });
    }

    public void addToDatabase(User whojoined, User invitedby) {
        try {
            Connection con = DriverManager.getConnection(Constants.sqldbURL, Constants.sqldbUsername, Constants.sqldbPassword);
            Statement statement = con.createStatement();
            try {
                statement.execute("SELECT * FROM InvitesTable");
            }catch (Exception exception) {
                exception.printStackTrace();
                statement.execute("CREATE TABLE InvitesTable(ID VARCHAR(20) PRIMARY KEY, INVITEDBY VARCHAR(20), REGULARINVITES INT(5), INVITESLEFT INT(5), BONUSINVITES INT(5));");
            }
            try {
                statement.execute("INSERT INTO InvitesTable VALUES(\"" + whojoined.getId() + "\", \"" +
                        invitedby.getId() + "\", 0, 0, 0)");
            } catch (Exception exception) {
                statement.execute("UPDATE InvitesTable SET INVITEDBY = " + invitedby.getId() + " WHERE ID = " + whojoined.getId());
            }

            ResultSet exists = statement.executeQuery("SELECT COUNT(*) FROM InvitesTable WHERE ID = " + invitedby.getId()); exists.next();
            if (exists.getInt(1)==0) {
                statement.execute("INSERT INTO InvitesTable VALUES(\"" + invitedby.getId() + "\", null, 1, 0, 0)");
            }else{
                statement.execute("UPDATE InvitesTable SET REGULARINVITES = REGULARINVITES+1 WHERE ID = " + invitedby.getId());
            }

            con.close();

        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
