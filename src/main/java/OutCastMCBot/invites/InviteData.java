package OutCastMCBot.invites;

import net.dv8tion.jda.api.entities.Invite;

public class InviteData {

    private final String guildid;
    private int uses;

    public InviteData(Invite invite) {
        this.guildid = invite.getGuild().getId();
        this.uses = invite.getUses();
    }

    public String getGuildid() {
        return guildid;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public void incrementUses() {
        this.uses++;
    }

}
