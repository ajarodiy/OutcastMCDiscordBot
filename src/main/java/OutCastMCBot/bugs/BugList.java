package OutCastMCBot.bugs;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class BugList {
    private static List<User> list = new ArrayList<>();

    public static List<User> getList() {
        return list;
    }

    public static void setList(List<User> list) {
        BugList.list = list;
    }
}
