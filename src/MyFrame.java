import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-12.
 */




public class MyFrame extends JFrame implements ActionListener {
    private static Integer counter = 0;
    enum BUTTONS{
        OK("OK"),
        CANCEL("CANCEL");

        private Integer id;
        private String name;

        BUTTONS( String name){
            id = counter;
            counter ++;
            this.name = name;
        }

        public Integer getId(){
            return id;
        }
        public String getName(){
            return name;
        }

    }
    ArrayList<JButton> buttonList = new ArrayList<>();
    JTextField textField = new JTextField(20);
    JLabel label = new JLabel("Tekst");

    public MyFrame(){
        super("Wykrywanie");

        addButtonToList(BUTTONS.OK);
        addButtonToList(BUTTONS.CANCEL);

        add(label);
        add(textField);

        for(JButton button : buttonList){
            add(button);
            button.addActionListener(this);
        }

        setLayout(new FlowLayout());
        setVisible(true);
        setSize(750,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(buttonList.get(BUTTONS.OK.getId()))){
        }
    }

    private void addButtonToList(BUTTONS button){
        buttonList.add(button.getId(), new JButton(button.getName()));
    }
}
