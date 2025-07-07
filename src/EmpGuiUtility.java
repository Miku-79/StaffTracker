import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;


public class EmpGuiUtility {

    public static final Color ButtonPressedColor = new Color(60, 60, 100);
    public static final Font customFont = new Font("SansSerif", Font.PLAIN, 24); 
    public static final Color DefaultColor = new Color(30, 30, 47);
    public static final Color Green = Color.GREEN;
    public static final Color Red = Color.RED;
    
    private static Timer timerObject;
    
    public static Employee datavalidationfunc(JTextField[] textemp,JComboBox<String> selectGender,JComboBox<String> selectempdepartment,JLabel errorLabel){
        
        String[] TempDataHolder = new String[9];
        String textfielddata;
        
        for (int i = 0; i < 6; i++) {

            textfielddata = textemp[i].getText();
            if(textfielddata.trim().isEmpty()){
                errorLabel.setText("Error!, Check Fields.");
                return null;
            }

            switch (i) {
                case 0:
                    if(!textfielddata.matches("^[a-zA-Z ]+$"))
                    {
                        errorLabel.setText("Name field error");
                        return null;
                    }
                    break;
                case 1:
                    if(!textfielddata.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
                    {
                        errorLabel.setText("Email field error");
                        return null;
                    }
                    break;
                case 2:
                    if(!textfielddata.matches("^[\\d]{10,12}$"))
                    {
                        errorLabel.setText("Phone field error");
                        return null;
                    }
                    break;
                case 3:
                    if(!textfielddata.matches("^\\d+$"))
                    {
                        errorLabel.setText("Salary field error");
                        return null;
                    }
                    break;
                case 5: 
                if (!textfielddata.matches("^\\d{1,2}/\\d{1,2}/\\d{4}$")) {
                    errorLabel.setText("Date field error");
                    return null;
                }

            }

            TempDataHolder[i] = textfielddata;
        }

        TempDataHolder[6] = ((String) selectempdepartment.getSelectedItem());
        TempDataHolder[7] = ((String) selectGender.getSelectedItem());

        return new Employee(
            -1,                                 // ID 
            TempDataHolder[0],                  // name
            TempDataHolder[1],                  // email
            TempDataHolder[2],                  // phone number
            Integer.parseInt(TempDataHolder[3]),// salary
            TempDataHolder[4],                  // address
            TempDataHolder[5],                  //Chnage done DOJ
            TempDataHolder[6],                  // department
            TempDataHolder[7]                   // Gender
        );
    }

    public static JButton CustomButton(String btntext, int width , int height){
            
        Color DefaultColor = new Color(30, 30, 47);
        
        JButton button = new JButton(btntext);
            button.setFocusPainted(false);
            if(width != -1){
                button.setMaximumSize(new Dimension(width,height));
            }
            button.setBorderPainted(true);
            button.setBackground(DefaultColor);
            button.setForeground(Color.WHITE);
            return button;
    }

    public static void DynamicTitleChange(String msg,int timedelayMS,JFrame mainframe,Color msgColor){

        mainframe.setTitle(msg);
        mainframe.setForeground(msgColor);

        if(timerObject == null || !timerObject.isRunning())
        {
            timerObject = new Timer(timedelayMS, e -> {
                mainframe.setTitle("");
            });
            timerObject.start();
        }

    }

    public static String[] EmpObjectTOString(Employee EmpObj){
       
        String[] returnString = {
            String.valueOf(EmpObj.getId()),
            EmpObj.getName(),
            EmpObj.getEmail(),
            EmpObj.getPhoneNumber(),
            String.valueOf(EmpObj.getSalary()),
            EmpObj.getAddress(),
            EmpObj.getDOJ(),
            EmpObj.getDepartment(),
            EmpObj.getGender()
        };

        return returnString;
    }

}
