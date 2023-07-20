package src.tasks;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;

public class WalkToCenterTask extends TaskNode {

    Tile centerTile = new Tile(3184, 3436, 0);

    @Override
    public boolean accept() {
        return Players.getLocal().getTile() != null && Players.getLocal().getTile() != centerTile;
    }

    @Override
    public int execute() {


        if (Players.getLocal().isMoving()) {
            Logger.log("Waiting for player to stop moving");
            Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);
        }
        Logger.log("Walking to center");
        Walking.walk(centerTile); // walk to center

        Sleep.sleepUntil(() -> Players.getLocal().getTile() != null && Players.getLocal().getTile() == centerTile, 5000);

        return 100;
    }
}
