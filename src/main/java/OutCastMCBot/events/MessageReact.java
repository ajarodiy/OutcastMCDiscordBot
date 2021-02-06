package OutCastMCBot.events;


import OutCastMCBot.Constants;
import OutCastMCBot.reports.ReportConfirmation;
import OutCastMCBot.tickets.TicketCloseConfirmation;
import OutCastMCBot.tickets.TicketCreate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class MessageReact extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent e) {
        if (e.getMember().getUser().isBot()) return;
        e.retrieveMessage().queue(m -> {
            if (!m.getEmbeds().isEmpty()) {
                // Create Ticket
                if (m.getEmbeds().get(0).getDescription()!=null && m.getEmbeds().get(0).getDescription().equals(Constants.ticketdescription)) {
                    TicketCreate.run(m,e);
                    return;
                }

                // Ticket Closing Confirmation
                if (m.getEmbeds().get(0).getDescription()!=null && (m.getEmbeds().get(0).getDescription().equals(Constants.closeconfirmationdescription)
                        || m.getEmbeds().get(0).getDescription().equals(Constants.applicationconfirmationdescription)) ) {
                    TicketCloseConfirmation.run(m,e);
                }

                // Reports confirmation
                if (m.getEmbeds().get(0).getTitle()!=null && m.getEmbeds().get(0).getTitle().equals("<:OutcastMC:782035842293039124>  OutcastMC Reports")) {
                    ReportConfirmation.run(m,e);
                }

                // Add staff to ticket
                if (m.getEmbeds().get(0).getTitle()!=null && m.getEmbeds().get(0).getTitle().contains("Ticket")) {
                    String title = m.getEmbeds().get(0).getTitle();
                    String name = title.substring(0, title.indexOf("Ticket")-3);

                    m.delete().queue();

                    Category category = e.getGuild().getCategoryById(Constants.ticketcategoryid);
                    for (TextChannel c : category.getTextChannels()) {
                        if (c.getName()!=null && c.getName().contains(name.toLowerCase())) {
                            c.putPermissionOverride(e.getMember()).setAllow(Permission.VIEW_CHANNEL).queue();
                            c.sendMessage(e.getMember().getAsMention() + " has been added to the ticket.").queue();
                            return;
                        }
                    }
                }
            }
        });
    }
}
