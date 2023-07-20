package src.tasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;


public class DrinkOverloadPotionTask extends TaskNode {

    @Override
    public boolean accept() {
        int playerHealthPercent = Players.getLocal().getHealthPercent();
        int playerHitPointsLevel = Skills.getRealLevel(Skill.HITPOINTS);
        int playerHitPoints = (int) (playerHitPointsLevel * (playerHealthPercent / 100.0));
        return playerHitPoints > 50;
    }

    @Override
    public int execute() {

        Item overloadPotion = getOverloadPotion();

        if (overloadPotion != null) {
            overloadPotion.interact("Drink");
            Sleep.sleepUntil(() -> Skills.getBoostedLevel(Skill.STRENGTH) > 0, 1000);
            Sleep.sleepUntil(() -> Players.getLocal().getAnimation() == -1, 1000);
        }

        return Calculations.random(4000, 5000);
    }

    private Item getOverloadPotion() {
        return Inventory.get(item -> item != null && item.getName().contains("Overload"));
    }

}
