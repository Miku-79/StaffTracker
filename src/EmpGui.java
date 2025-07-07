import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class EmpGui {

    private JFrame frame;
    
    private CardLayout clayout;
    private ArrayList<JButton> buttonref = new ArrayList<JButton>();

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {
            new EmpGui().showLoginForm();
        });
    }

    private void showLoginForm() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 180);
        loginFrame.setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField("admin");

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passText = new JPasswordField("1234");

        JButton loginButton = EmpGuiUtility.CustomButton("Login", -1, -1);
        JLabel message = new JLabel("");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);
        panel.add(message);
        panel.add(loginButton);

        EmpPropHandler propHandler = new EmpPropHandler();
        String USERNAME = propHandler.getUsername();
        String PASSWORD = propHandler.getPassword();

        loginButton.addActionListener(e -> {
            String user = userText.getText();
            String pass = new String(passText.getPassword());

            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                loginFrame.dispose();
                createGui();
            } else {
                message.setText("Invalid credentials!");
                message.setForeground(Color.RED);
            }
        });

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    private void createGui(){

        frame = new JFrame();
        frame.setSize(800,500);
        frame.setMinimumSize(new Dimension(800,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        EmpGuiSideMain createPanel = new EmpGuiSideMain();
        JPanel mainpanel = new JPanel(new CardLayout());
        clayout = (CardLayout) mainpanel.getLayout();

        createPanel.createSidebar(frame,buttonref,clayout,mainpanel);
        createPanel.createMainpanel(mainpanel,frame,clayout,buttonref);

        frame.setVisible(true);
    }
}
