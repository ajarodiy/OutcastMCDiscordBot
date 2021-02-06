package OutCastMCBot;

import OutCastMCBot.events.MemberJoin;
import OutCastMCBot.events.MessageReact;
import OutCastMCBot.events.PrivateMessage;
import OutCastMCBot.events.PrivateReact;
import OutCastMCBot.invites.InviteTracking;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {
    public static void main(String args[]) throws Exception {
        JDA jda = JDABuilder.createDefault("NzgyMDIzMjAzNjk3ODUyNDU2.X8GJ3Q.jIAYlPw_OpNOwTaKcj1CzFtatMw")
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("play.outcastmc.org"))
                .addEventListeners(new MemberJoin()).addEventListeners(new MessageReact())
                .addEventListeners(new CommandListener()).addEventListeners(new InviteTracking())
                .addEventListeners(new PrivateMessage()).addEventListeners(new PrivateReact())
                .build().awaitReady();
    }
}
