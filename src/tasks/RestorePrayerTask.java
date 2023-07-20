package src.tasks;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.items.Item;

public class RestorePrayerTask extends TaskNode {
    private static final String PRAYER_POTION_KEYWORD = "Prayer potion";


    @Override
    public boolean accept() {
        int boostedPrayerLevel = Skill.PRAYER.getBoostedLevel();
        return boostedPrayerLevel <= 24 && hasPrayerPotion();
    }

    @Override
    public int execute() {
        Item prayerPotion = Inventory.get(item -> item != null && item.getName().contains(PRAYER_POTION_KEYWORD));

        if (prayerPotion != null) {
            prayerPotion.interact("Drink");
            return 100;
        }

        return 1000;
    }


    private boolean hasPrayerPotion() {
        return this.getPrayerPotion() != null;
    }

    private Item getPrayerPotion() {
        return Inventory.get(item -> item != null && item.getName().contains(PRAYER_POTION_KEYWORD));
    }

    public String toString() {
        return "Restoring prayer";
    }
}
