import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmpGuiSideMain {

    public void createSidebar(JFrame frame,ArrayList<JButton> buttonref,CardLayout clayout, JPanel mainpanel){
        
        JPanel sidebar = new JPanel();
        sidebar.setBackground(EmpGuiUtility.DefaultColor);
        sidebar.setPreferredSize(new Dimension(200 , 0));
        sidebar.setLayout(new BoxLayout(sidebar, 1));  //layout aling y-axis
        frame.add(sidebar , BorderLayout.WEST);

        ImageIcon Logo = new ImageIcon("./Assets/Logo.png");
        Logo = new ImageIcon(Logo.getImage().getScaledInstance(180, 160, Image.SCALE_SMOOTH));
        JLabel LogoLabel = new JLabel();
        LogoLabel.setIcon(Logo);
        sidebar.add(Box.createVerticalStrut(10));
        LogoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(LogoLabel);

        sidebar.add(Box.createVerticalStrut(15));
       
        String[] buttonNames = { "Dashboard", "Employees", "Settings", "Logout" };

        for(String bname : buttonNames)
        {   
            JButton button = EmpGuiUtility.CustomButton(bname, 150, 50);
          
            sidebar.add(Box.createVerticalStrut(10));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(button);
        
            button.addActionListener(e -> {
                clayout.show(mainpanel,bname);

                for(JButton tempbutton : buttonref){
                    tempbutton.setBackground(EmpGuiUtility.DefaultColor);
                }

                button.setBackground(EmpGuiUtility.ButtonPressedColor);
            });
            buttonref.add(button);
        }
    }

    public void createMainpanel(JPanel mainpanel,JFrame frame,CardLayout clayout,ArrayList<JButton> buttonref){

        mainpanel.setBorder(BorderFactory.createMatteBorder(10, 0, 10, 10 , EmpGuiUtility.DefaultColor));
        frame.add(mainpanel, BorderLayout.CENTER);

        JPanel Dashboard = new JPanel();
        JPanel Employees = new JPanel();
        JPanel Settings = new JPanel();
        JPanel Logout = new JPanel();

        EmpGuiLogout LogoutObject = new EmpGuiLogout();
        Logout = LogoutObject.createLogout(Logout,clayout,mainpanel,buttonref);
        mainpanel.add(Logout , "Logout");

        EmpGuiEmployee EmployeeObject = new EmpGuiEmployee();
        Employees = EmployeeObject.createEmployees(Employees,frame);
        mainpanel.add(Employees,"Employees");

        EmpGuiSetting SettingObject = new EmpGuiSetting();
        Settings = SettingObject.createSettings(Settings);
        mainpanel.add(Settings,"Settings");

        EmpGuiDashboard DashboardObject = new EmpGuiDashboard();
        Dashboard = DashboardObject.createDashboardPanel(Dashboard,frame);
        mainpanel.add(Dashboard,"Dashboard");

        clayout.show(mainpanel, "Dashboard");
    }
}
