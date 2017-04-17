package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Marcin on 2017-04-17.
 */
public class ParityPanel extends JPanel {
    private JScrollPane parityScrollPane;
    private JTextPane parityTextPane;
    private ActionListener actionListener;


    public ParityPanel(ActionListener actionListener) {
        super(new GridBagLayout());
        this.actionListener = actionListener;

        createComponents();
        addComponents();
    }

    private void createComponents(){
        parityTextPane = new JTextPane();
        parityScrollPane = new JScrollPane(parityTextPane);
    }

    private void addComponents(){
        GridBagConstraints c = new GridBagConstraints();
        add(parityScrollPane, c);
    }
}
