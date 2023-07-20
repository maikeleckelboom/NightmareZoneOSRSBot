package src.tasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.GameObject;

public class ActivateBoostersTask extends TaskNode {

    @Override
    public boolean accept() {
        return getClosestBooster() != null;
    }

    @Override
    public int execute() {
        GameObject closestBooster = getClosestBooster();

        if (closestBooster != null) {
            closestBooster.interact("Activate");
        }


        return Calculations.random(300, 600);
    }


    private GameObject getClosestBooster() {
        return GameObjects.closest(gameObject -> gameObject != null && gameObject.hasAction("Activate") && !gameObject.getName().contains("Ultimate force"));
    }
}
