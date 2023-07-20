package src.gui;

import net.miginfocom.swing.MigLayout;
import src.NightmareZone;

import javax.swing.*;

public class GUI extends JFrame {
    public GUI(NightmareZone script) {
        super();

        // Set the title of the GUI
        setTitle("Nightmare Zone");

        // MigLayout is a layout manager that allows us to easily create a grid-like layout
        setLayout(new MigLayout("fill, gap 5, insets 10"));

        // We don't want the user to be able to resize the GUI
        setResizable(false);

        JLabel modeLabel = new JLabel("Mode:");
        JComboBox<NightmareZone.Mode> modeComboBox = new JComboBox<>(NightmareZone.Mode.values());
        JButton startScriptButton = new JButton("Start Script");

        // When the start script button is pressed, we let the script know which mode to run in and remove the GUI
        startScriptButton.addActionListener((_event) -> {
            NightmareZone.Mode selectedMiningMode = (NightmareZone.Mode) modeComboBox.getSelectedItem();

            assert selectedMiningMode != null;
            script.setMode(selectedMiningMode);

            dispose();
        });


        // |-Label-|-ComboBox-|
        // |-Label-|-ComboBox-|
        // |-----Button-------|

        // Split will split the current row
        add(modeLabel, "split");
        // Grow will let it get as large as it can, wrap will end this row
        add(modeComboBox, "grow, wrap");
        // On the second row now, let the button fill the entire row
        add(startScriptButton, "grow");

        // Fit all of our components to the frame
        pack();
        // Center the frame on the screen
        setLocationRelativeTo(null);
        // Show the GUI
        setVisible(true);
    }
}
