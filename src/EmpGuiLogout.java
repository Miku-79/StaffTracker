import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmpGuiLogout {
    
    public JPanel createLogout(JPanel Logout,CardLayout clayout,JPanel mainpanel,ArrayList<JButton> buttonref){
        Logout.setLayout(new BoxLayout(Logout,1));

        JLabel logoutMsg = new JLabel("Are you sure About that!");
        logoutMsg.setFont(EmpGuiUtility.customFont);

        JButton logoutButtonNO = EmpGuiUtility.CustomButton("NO",100,50);
        JButton logoutButtonYES = EmpGuiUtility.CustomButton("YES", 100, 50);

        JButton tempref1 = buttonref.get(0);
        JButton tempref2 = buttonref.get(3);
        logoutButtonNO.addActionListener(e -> {
            clayout.show(mainpanel , "Dashboard");
            tempref1.setBackground(EmpGuiUtility.ButtonPressedColor);
            tempref2.setBackground(EmpGuiUtility.DefaultColor);
        });

        logoutButtonYES.addActionListener(e -> {
            System.exit(0);
        });

        logoutButtonYES.setMaximumSize(new Dimension(100,50));

        Logout.add(Box.createVerticalGlue());
        Logout.add(logoutMsg);
        Logout.add(Box.createVerticalStrut(10));
        Logout.add(logoutButtonNO);
        Logout.add(Box.createVerticalStrut(10));
        Logout.add(logoutButtonYES);

        logoutMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButtonYES.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButtonNO.setAlignmentX(Component.CENTER_ALIGNMENT);

        Logout.add(Box.createVerticalGlue());
        return Logout;
    }
}
