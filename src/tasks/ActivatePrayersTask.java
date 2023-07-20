package src.tasks;

import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.prayer.Prayers;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.TaskNode;


public class ActivatePrayersTask extends TaskNode {
    public Prayer[] prayers = {Prayer.PROTECT_FROM_MELEE, Prayer.ULTIMATE_STRENGTH};

    @Override
    public boolean accept() {
        int totalPrayerPoints = Skills.getRealLevel(Skill.PRAYER);
        double percentage = (double) Skill.PRAYER.getBoostedLevel() / (double) totalPrayerPoints;
        return !hasPrayersActive() && percentage > 0.6;
    }

    @Override
    public int execute() {
        for (Prayer prayer : prayers) {
            if (!Prayers.isActive(prayer)) {
                Prayers.toggle(true, prayer);
            }
        }
        return 0;
    }

    private boolean hasPrayersActive() {
        for (Prayer prayer : prayers) {
            if (Prayers.isActive(prayer)) {
                return true;
            }
        }
        return false;
    }
}
