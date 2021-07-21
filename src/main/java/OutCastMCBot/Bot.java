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

import java.util.Timer;
import java.util.TimerTask;

public class Bot {
    public static void main(String args[]) throws Exception {
        JDA jda = JDABuilder.createDefault("token")
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("play.outcastmc.org"))
                .addEventListeners(new MemberJoin()).addEventListeners(new MessageReact())
                .addEventListeners(new CommandListener()).addEventListeners(new InviteTracking())
                .addEventListeners(new PrivateMessage()).addEventListeners(new PrivateReact())
                .build().awaitReady();



        new Timer().schedule(new TimerTask() {
            private int index=0;
            @Override
            public void run() {
                if (index==0) jda.getPresence().setActivity(Activity.watching("store.outcastmc.org"));
                else jda.getPresence().setActivity(Activity.playing("play.outcastmc.org"));
                index ^= 1;
            }
        }, 10000, 10000);
    }
}
