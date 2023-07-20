package src.tasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.Sleep;

public class BankTask extends TaskNode {
    @Override
    public boolean accept() {
        return Inventory.isFull();
    }

    @Override
    public int execute() {
        if (Bank.open()) {
            Bank.depositAllExcept(item -> item.getName().contains("pickaxe"));
        } else {
            Sleep.sleepUntil(Walking::shouldWalk, () -> Players.getLocal().isMoving(), 1000, 100);
            return 100;
        }
        return Calculations.random(300, 600);
    }
}
