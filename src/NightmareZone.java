package src;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import src.gui.GUI;
import src.tasks.*;
import src.utils.TimeFormatter;

import javax.swing.*;
import java.awt.*;

@ScriptManifest(
        category = Category.COMBAT,
        name = "NightmareZone V2",
        description = "Completes the Nightmare Zone mini-game.",
        author = "SkillElite",
        version = 2
)
public class NightmareZone extends TaskScript {

    public static State state;

    public Mode mode;

    /**
     * This onStart is called only if the user starts the script using the Start button in the client.
     * We show the GUI to let the user set the script up.
     *
     * @see GUI
     */
    @Override
    public void onStart() {
        SkillTracker.start(Skill.STRENGTH, Skill.HITPOINTS);

        // Set the state to setting up, so we can show the GUI
        setState(State.SETTING_UP);

        // Show our fancy new GUI to let the user set the script up
        SwingUtilities.invokeLater(() -> new GUI(this));

        // Since we no longer add nodes/tasks in our onStart, the script itself
        // won't do anything until the user handles the GUI
    }

    /**
     * This onStart is called only if the user starts the script using QuickStart with parameters.
     * We check the parameters to see how to set the script up without needing to show a GUI that would require user
     * input.
     *
     * @param params the QuickStart parameters passed in from the command line
     */
    @Override
    public void onStart(String... params) {
        SkillTracker.start(Skill.STRENGTH, Skill.HITPOINTS);
        setState(State.SETTING_UP);
    }

    /**
     * This adds the tasks necessary to run the script in the provided {@link Mode}
     *
     * @param mode the script mode to use
     */
    public void setMode(Mode mode) {
        this.mode = mode;
        setState(State.READY);
    }

    public void setState(State newState) {

        removeNodes(getNodes());

        state = newState;

        switch (state) {
            case SETTING_UP -> {
            }
            case READY -> {
                addNodes(new ConsumeOverloadTask());
                addNodes(new ActivatePrayersTask());
                addNodes(new FightTask());
                addNodes(new RestorePrayerTask());
                addNodes(new ActivateBoosterTask());
            }
        }
    }


    private String formatNumeric(long number) {
        return String.format("%,d", number);
    }

    private String formatTime(long time) {
        return TimeFormatter.formatTime(time);
    }

    @Override
    public void onPaint(Graphics2D g) {
        g.drawString(formatTime(System.currentTimeMillis() - SkillTracker.getStartTime(Skill.HITPOINTS)), 10, 140);

        g.drawString("Strength level: " + Skills.getRealLevel(Skill.STRENGTH), 10, 65);
        g.drawString("Strength XP gained: " + formatNumeric(SkillTracker.getGainedExperience(Skill.STRENGTH)), 10, 80);
        g.drawString("Strength XP/hour: " + formatNumeric(SkillTracker.getGainedExperiencePerHour(Skill.STRENGTH)), 10, 95);
        g.drawString("Strength TTL: " + formatTime(SkillTracker.getTimeToLevel(Skill.STRENGTH)), 10, 110);

        g.drawString("Hitpoints level: " + Skills.getRealLevel(Skill.HITPOINTS), 75, 165);
        g.drawString("Hitpoints XP gained: " + formatNumeric(SkillTracker.getGainedExperience(Skill.HITPOINTS)), 75, 80);
        g.drawString("Hitpoints XP/hour: " + formatNumeric(SkillTracker.getGainedExperiencePerHour(Skill.HITPOINTS)), 175, 95);
        g.drawString("Hitpoints TTL: " + formatTime(SkillTracker.getTimeToLevel(Skill.HITPOINTS)), 175, 110);


        if (getLastTaskNode() == null) return;
        g.drawString(getLastTaskNode().getClass().toString(), 10, 135);

        if (getLastTaskNode().getClass() == FightTask.class) {
            g.setColor(new Color(248, 234, 180, 155));
            g.drawString(getLastTaskNode().toString(), 10, 250);
        }
    }

    public enum State {
        SETTING_UP,
        READY,
    }

    public enum Mode {
        DEFAULT;
    }
}
