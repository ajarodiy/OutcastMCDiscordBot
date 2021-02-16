package OutCastMCBot.applications;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.Constants;
import OutCastMCBot.bugs.BugList;
import OutCastMCBot.suggestions.SuggestionList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class ApplyCommand implements CommandInterface {
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) throws IOException {
        e.getMessage().addReaction(Constants.ticketemoji).queue();
        if (ApplicationConfirmation.applicationExists(e.getMember().getUser())) {
            e.getMember().getUser().openPrivateChannel().queue(channel -> {
                channel.sendMessage("You already have an open application.").queue();
            });
            return;
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("<" + Constants.ticketemoji + ">  OutcastMC Staff Application")
                .setDescription(Constants.applicationInfo)
                .setColor(Constants.ticketcolor).setFooter(Constants.serverip, Constants.iconurl);
        e.getMember().getUser().openPrivateChannel().queue(channel -> {
            User user = e.getMember().getUser();
            channel.sendMessage(embed.build()).queue();
            channel.sendMessage(ApplicationMap.getQuestion(1)).queue(m -> {
                ApplicationMap.getMessages().get(user).add(m.getId());
            });
            ApplicationMap.getMap().removeAll(user);
            ApplicationMap.getMap().put(user, "temporary");
            SuggestionList.getList().remove(user);
            BugList.getList().remove(user);
        });
    }

    @Override
    public String getCommand() {
        return "apply";
    }

    @Override
    public String getHelp() {
        return "Apply for staff\n**Usage:** `!apply`";
    }
}
