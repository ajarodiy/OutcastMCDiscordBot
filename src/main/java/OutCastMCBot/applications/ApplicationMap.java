package OutCastMCBot.applications;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.*;

public class ApplicationMap {
    private static Multimap<User,String> map = ArrayListMultimap.create();
    private static List<String> questions = new ArrayList<>();
    private static Multimap<User, String> messages = ArrayListMultimap.create();
    private static List<String> lockedchannels = new ArrayList<>();

    static {
        questions.addAll(Arrays.asList("temporary",
                "What is your IGN? (In Game Name)",
                "What is your Discord? ( Example : callum#2918 )",
                "How old are you?",
                "What is your timezone?",
                "Do you have a mic to talk in discord?",
                "How long can you be online per day? ( Discord and In-Game )",
                "Why would you like to be considered as staff on OutcastMC?",
                "What makes you unique to our other applicants?",
                "Have you had any moderation experience before?",
                "A player is using a hacked client on OutcastMC. How would you manage this situation?",
                "A player is insulting you. How would you manage this situation?",
                "Is there anything else you'd like to tell us?"));
    }

    public static List<String> getLockedchannels() {
        return lockedchannels;
    }

    public static void setLockedchannels(List<String> lockedchannels) {
        ApplicationMap.lockedchannels = lockedchannels;
    }

    public static Multimap<User, String> getMessages() {
        return messages;
    }

    public static void setMessages(Multimap<User, String> messages) {
        ApplicationMap.messages = messages;
    }

    public static List<String> getQuestions() {
        return questions;
    }

    public static String getQuestion(int i) {
        return i + ". " + questions.get(i);
    }

    public static void setQuestions(List<String> questions) {
        ApplicationMap.questions = questions;
    }

    public static Multimap<User, String> getMap() {
        return map;
    }

    public static void setMap(Multimap<User, String> map) {
        ApplicationMap.map = map;
    }

    public static void removeOldMessages(User user) {
        List<String> messages = new ArrayList<>(ApplicationMap.getMessages().get(user));
        user.openPrivateChannel().queue(channel -> {
            channel.purgeMessagesById(messages);
        });
        ApplicationMap.getMessages().removeAll(user);
    }

}
