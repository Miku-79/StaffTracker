import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EmpGuiSetting {
    public JPanel createSettings(JPanel Settings){
        
        Settings.setLayout(new GridLayout(8,2,15,15));
        Settings.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField[] TextFieldObjects = new JTextField[7];
        String[] LabelStrings = {"Username for Login","Password for Login","Username for Database",
                        "Password for Database","Host IP for Database","Port for Database","Name of Database"};

        for(int i=0;i<7;i++)
        {
            JLabel label = new JLabel(LabelStrings[i]);
            Settings.add(label);
            JTextField TextField = new JTextField();
            Settings.add(TextField);
            TextFieldObjects[i] = TextField;
        }
        JLabel EmptyLabel = new JLabel();
        Settings.add(EmptyLabel);

        EmpPropHandler prophandle = new EmpPropHandler();
        TextFieldObjects[0].setText(prophandle.getUsername());
        TextFieldObjects[1].setText(prophandle.getPassword());
        TextFieldObjects[2].setText(prophandle.getDBUsername());
        TextFieldObjects[3].setText(prophandle.getDBPassword());
        TextFieldObjects[4].setText(prophandle.getDBHost());
        TextFieldObjects[5].setText(prophandle.getDBPort());
        TextFieldObjects[6].setText(prophandle.getDBName());

        JButton ApplySettingButton = EmpGuiUtility.CustomButton("Apply", -1, 0);
        Settings.add(ApplySettingButton);

        ApplySettingButton.addActionListener(e -> {

            prophandle.setUsername(TextFieldObjects[0].getText());
            prophandle.setPassword(TextFieldObjects[1].getText());
            prophandle.setDBUsername(TextFieldObjects[2].getText());
            prophandle.setDBPassword(TextFieldObjects[3].getText());
            prophandle.setDBHost(TextFieldObjects[4].getText());
            prophandle.setDBPort(TextFieldObjects[5].getText());
            prophandle.setDBName(TextFieldObjects[6].getText());
            
            if(prophandle.SaveSettings())
            {
                EmptyLabel.setText("Settings Saved successfully!");
                EmptyLabel.setForeground(Color.GREEN);
            }
            else{
                EmptyLabel.setText("Setting not Saved, Try again");
                EmptyLabel.setForeground(Color.RED);
            }
        });

        return Settings;
    }
}
