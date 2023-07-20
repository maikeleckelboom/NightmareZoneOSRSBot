package src.tasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.List;

public class FightTask extends TaskNode {

    Tile caveCenter = new Tile(14192, 1065, 3);

    Tile lastBoosterTile = null;

    String subState = "Waiting for enemies to spawn...";
    String currentEnemy = "None";


    @Override
    public boolean accept() {
        GameObject booster = GameObjects.closest(gameObject -> gameObject != null
                && gameObject.hasAction("Activate")
                && !gameObject.getName().equalsIgnoreCase("Ultimate force"));

        lastBoosterTile = (booster == null) ? null : booster.getTile();

        return Skill.PRAYER.getBoostedLevel() > Skill.PRAYER.getLevel() / 2 && Skill.STRENGTH.getBoostedLevel() > 0 && booster == null;
    }

    @Override
    public int execute() {
        NPC enemy = getClosestEnemy();


        if (enemy == null) {
            currentEnemy = "None";
            subState = "No enemies found";

            return Calculations.random(100, 500);
        }

        currentEnemy = enemy.getName();

        if (enemy.interact("Attack")) {

            subState = "Attacking enemy";

            Sleep.sleepUntil(this::isFighting, 2500);

            subState = "In combat with " + enemy.getName();

            if (Combat.getSpecialPercentage() >= 25) {
                if (Inventory.get("Dragon dagger(p++)").interact("Wield")) {
                    Sleep.sleepUntil(() -> Inventory.contains("Dragon scimitar"), 1000);
                    subState = "Unleashing special attack on " + enemy.getName();
                    while (Combat.getSpecialPercentage() >= 25) {
                        Combat.toggleSpecialAttack(true);
                        Sleep.sleepUntil(Combat::isSpecialActive, 1000);
                        enemy.interact("Attack");
                    }
                }
            }

            if (Inventory.contains("Dragon scimitar")) {
                if (Inventory.get("Dragon scimitar").interact("Wield")) {
                    Sleep.sleepUntil(() -> Inventory.contains("Dragon dagger(p++)"), 1000);
                    enemy.interact("Attack");
                }
            }

//            Sleep.sleepUntil(() -> !isFighting(), 5000);
        }

        return Calculations.random(500, 1000);
    }

    private NPC getClosestEnemy() {
        List<NPC> allEnemies = NPCs.all(gameObject -> gameObject != null
                && gameObject.hasAction("Attack")
                && gameObject.distance() < 12
        );
        NPC lowestHealthEnemy = null;

        for (NPC enemy : allEnemies) {
            if (lowestHealthEnemy == null) {
                lowestHealthEnemy = enemy;
            } else if (enemy.getHealthPercent() < lowestHealthEnemy.getHealthPercent()) {
                lowestHealthEnemy = enemy;
            }
        }

        return lowestHealthEnemy;
    }

    private boolean isFighting() {
        return Players.getLocal().isInCombat();
    }

    public String toString() {
        return subState;
    }
}