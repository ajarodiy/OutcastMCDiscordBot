package OutCastMCBot.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class CheckRole {
    public static boolean hasRole(Member user, String roleid) {
        Role role = user.getRoles().stream()
                .filter(r -> r.getId().equalsIgnoreCase(roleid))
                .findFirst().orElse(null);
        return role!=null;
    }
}
