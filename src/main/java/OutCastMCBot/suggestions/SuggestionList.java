package OutCastMCBot.suggestions;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class SuggestionList {
    private static List<User> list = new ArrayList<>();

    public static List<User> getList() {
        return list;
    }

    public static void setList(List<User> list) {
        SuggestionList.list = list;
    }
}
