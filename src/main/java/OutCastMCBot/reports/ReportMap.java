package OutCastMCBot.reports;

import com.google.common.collect.Multimap;
import com.google.common.collect.ArrayListMultimap;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;

public class ReportMap {
    private static Multimap<User,String> map = ArrayListMultimap.create();
    private static HashMap<User, TextChannel> channelmap = new HashMap<>();

    public static HashMap<User, TextChannel> getChannelmap() {
        return channelmap;
    }

    public static void setChannelmap(HashMap<User, TextChannel> channelmap) {
        ReportMap.channelmap = channelmap;
    }

    public static Multimap<User, String> getMap() {
        return map;
    }

    public static void setMap(Multimap<User, String> map) {
        ReportMap.map = map;
    }
}
