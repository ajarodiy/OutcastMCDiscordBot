package OutCastMCBot.commands;

import OutCastMCBot.CommandInterface;
import OutCastMCBot.CommandManager;
import OutCastMCBot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class Help implements CommandInterface {

    public final CommandManager manager;

    public Help(CommandManager m) {
        this.manager = m;
    }

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent e) {
//        if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) return;
        EmbedBuilder embed = new EmbedBuilder();
        StringBuilder desc = embed.getDescriptionBuilder();

        if (args.isEmpty()) {
            embed.setColor(Constants.ticketcolor).setTitle("<:OutcastMC:782035842293039124> OutcastMC Commands")
                    .setFooter("!help {command}",Constants.iconurl);
            embed.addField("Members", "`close`, `invites`, `leaderboard`, `suggest`, `apply`, `bug`", false)
                    .addField("Administration", "`report`, `add`, `announce`, `ticketmessage`, `bonus`, `reset`, `claim`, `whoinvited`, `membercount`, `lock`", false);
            e.getChannel().sendMessage(embed.build()).queue();
        }else{
            if (args.get(0).equalsIgnoreCase("help")) return;

            CommandInterface command = manager.getCommand(args.get(0));
            if (command == null) {
                embed.setTitle("<" + Constants.ticketemoji + "> OutcastMC ┃ Error!")
                        .setFooter(Constants.serverip,Constants.iconurl).setColor(Color.red).setDescription("`" + args.get(0)
                        + "` command does not exist.");
                e.getChannel().sendMessage(embed.build()).queue();
                return;
            }
            embed.setTitle(command.getCommand().substring(0,1).toUpperCase() + command.getCommand().substring(1))
                    .setFooter(Constants.serverip,Constants.iconurl).setColor(Constants.ticketcolor).setDescription(command.getHelp());

            e.getChannel().sendMessage(embed.build()).queue();
        }

//        if (args.isEmpty()) {
//            embed.setColor(Constants.ticketcolor).setTitle("<:OutcastMC:782035842293039124> OutcastMC Commands")
//                    .setFooter(Constants.serverip,Constants.iconurl);
//            manager.getCommands().forEach(command -> {
//                if (command.getCommand()!="help") desc.append("**"+Constants.prefix + command.getCommand() + "** - " + command.getHelp() + "\n");
//            });
//            e.getChannel().sendMessage(embed.build()).queue();
//            return;
//        }
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Helps";
    }
}
