package OutCastMCBot;

import net.dv8tion.jda.api.entities.Emote;

import java.awt.*;

public class Constants {
    public static String prefix = "!";
    public static String welcomemessagechannelID = "782634487631839232";
    public static String invitelogschannelID = "786845067288510504";
    public static String ticketdescription = "If you require support from staff then please create a ticket. Our team will\n" +
            "respond to your ticket as soon as we can!\n" +
            "\n" +
            "**Estimated Time :** 10 Minutes\n" +
            "\n" +
            "To create a support ticket, please react to this message below with <:OutcastMC:782035842293039124>";
    public static String ticketemoji = ":OutcastMC:782035842293039124";
    public static String serveremote = "782035842293039124";
    public static String ticketcategoryid = "782336471515398164";
    public static String ticketrequestschannel = "807263240752594965";
    public static String closeconfirmationdescription = "Are you sure you want to close this ticket ?";
    public static String applicationconfirmationdescription = "Are you sure you want to close this application ?";
    public static Color ticketcolor = Color.decode("#31bbac");
    public static String checkmark = "✅";
    public static String crossmark = "❎";
    public static String thumbsup = "\uD83D\uDC4D";
    public static String thumbsdown = "\uD83D\uDC4E";
    public static String iconurl = "https://media.discordapp.net/attachments/782237057979383852/782622393901907978/768x768.png?width=703&height=703";
    public static String serverip = "play.outcastmc.org";
    public static String reportchannelid = "782379575698915359";
    public static String sqldbURL = "jdbc:mysql://eu01-sql.pebblehost.com:3306/customer_149976_invitesmanager?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String sqldbUsername = "customer_149976_invitesmanager";
    public static String sqldbPassword = "SPJMigqf09KdlS$3xYQz";
    public static String suggestionChannelID = "804361273264570368";
    public static String guildID = "699654181979684966";
    public static String roleOutcastTeamID = "722586284610158613";
    public static String roleStaffApplicationsID = "722875027132776542";
    public static String applicationCategoryID = "805291617493188609";
    public static String applicationInfo = "Welcome to the OutcastMC staff applications!\n" +
            "\n" +
            "Please use as much detail as possible when completing the form content. Correct grammar, spelling, and punctuation will increase your chances of getting accepted for an interview. If you are not contacted back within 1-2 weeks, your application will have been rejected and you may re-apply 4 weeks after the date of your first submission. \n" +
            "\n" +
            "The list below are the staff requirements:\n" +
            "✔ You are of the age 16 years or older.\n" +
            "✔ You are actively interacting on discord and on the server.\n" +
            "✔ You have no previous punishment history [2 months].\n" +
            "\n" +
            "Thanks for reading & Good luck,\n" +
            "- OutcastMC Management Team\n\n" + "Type `cancel` at any point to cancel the application.\n"
            + "(You will be asked for confirmation after each answer.)";
}
