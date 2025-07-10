import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class EmpGuiEmployee {

    private EmpSql sqldata = new EmpSql();
    
    public JPanel createEmployees(JPanel Employees,JFrame mainframe){
        
        String[] colName = {"ID","Name","Department","Email","PhoneNumber","Salary","Date Joined","Address","Gender"};
        
        //created a object of Table model and also override/changed behaviour of table
        DefaultTableModel DefTableModel = new DefaultTableModel(null , colName){
            @Override
            public boolean isCellEditable(int row , int column){
                //return column != 0;
                return false;

                //runs this function on each cell and check if editable or not by return value
            }
        };

        
        boolean isConnected = sqldata.ConnectToSql();
        if(isConnected){
            sqldata.getallEmployee(DefTableModel); 
            EmpGuiUtility.DynamicTitleChange("Connected to DB", 3000, mainframe, EmpGuiUtility.Green);
        }
        else{
            EmpGuiUtility.DynamicTitleChange("Failed to Connect to DB", 3000, mainframe, EmpGuiUtility.Red);
        }

        JTable table = new JTable(DefTableModel);
        JScrollPane scroll = new JScrollPane(table);
        
        Employees.setLayout(new BorderLayout());

        JButton searchButton = EmpGuiUtility.CustomButton("Search", -1, 0);
        JButton addEmpButton = EmpGuiUtility.CustomButton("Add Record", -1, 0);
        JButton DeleteEmpButton = EmpGuiUtility.CustomButton("Delete Record", -1, 0);
        JButton UpdateEmpButton = EmpGuiUtility.CustomButton("Update record", -1, 0);
        JButton ReconnectButton = EmpGuiUtility.CustomButton(null, -1, 0);

        ImageIcon RetryIcon = new ImageIcon("./Assets/Retry.png");
        RetryIcon = new ImageIcon(RetryIcon.getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH));
        ReconnectButton.setIcon(RetryIcon);

        JTextField searchArea = new JTextField(20);
        searchArea.setPreferredSize(new Dimension(20,27));
        // searchArea.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));

        searchButton.addActionListener(e -> {
            DefTableModel.setRowCount(0);
            String searcherdValue = searchArea.getText();
            sqldata.getSearchedEmp(searcherdValue, DefTableModel);
        });
        addEmpButton.addActionListener(e -> {
            createEmpdataWindow("Add Record", "Add", 1, 0, mainframe);
        });
        DeleteEmpButton.addActionListener(e -> {
            
            String searchedValue = searchArea.getText();
            if(searchedValue.trim().isEmpty() || (!searchedValue.matches("^\\d+$")))
            {
                EmpGuiUtility.DynamicTitleChange("No Valid ID Provided", 3000, mainframe, EmpGuiUtility.Green);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(
                null,                                // parent component
                "Are you sure you want to delete?",  // message
                "Confirm Deletion",                  // dialog title
                JOptionPane.YES_NO_OPTION            // button options
            );


            if(searchedValue.matches("^\\d+") && confirm == JOptionPane.YES_OPTION)
            {
                boolean isDeleted = sqldata.deleteEmployee(Integer.parseInt(searchedValue));
                if(isDeleted){
                    EmpGuiUtility.DynamicTitleChange("Record Deleted successfully", 3000, mainframe, EmpGuiUtility.Green);
                    DefTableModel.setRowCount(0);
                    sqldata.getallEmployee(DefTableModel);
                }
                else{
                    EmpGuiUtility.DynamicTitleChange("Error Occured: Deletion Failed", 3000, mainframe, EmpGuiUtility.Red);
                }
            }
            else{
                System.out.println("Deletion Cancelled");
                return;   
            }   
        });

        UpdateEmpButton.addActionListener(e ->{

            String empid = searchArea.getText().trim();
            if(empid.isEmpty() || !empid.matches("^\\d+$"))
            {
                EmpGuiUtility.DynamicTitleChange("Search Field Empty, Enter ID to Update", 3000, mainframe, EmpGuiUtility.Green);
                return;
            }
            createEmpdataWindow("Update Record", "Update", 0, Integer.parseInt(empid),mainframe);
            DefTableModel.setRowCount(0);
            sqldata.getAllTasks(DefTableModel);
        });

        ReconnectButton.addActionListener(e -> {
            if(sqldata.connClose() && sqldata.ConnectToSql()){
                DefTableModel.setRowCount(0);
                sqldata.getallEmployee(DefTableModel); 
                EmpGuiUtility.DynamicTitleChange("Connected to DB", 3000, mainframe, EmpGuiUtility.Green);
            }
            else{
                EmpGuiUtility.DynamicTitleChange("Failed to Connect to DB", 3000, mainframe, EmpGuiUtility.Red);
            }
        });

        JToolBar EmpToolButton = new JToolBar();
        EmpToolButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        EmpToolButton.add(DeleteEmpButton);
        EmpToolButton.addSeparator(new Dimension(3, 0));
        EmpToolButton.add(searchArea);
        EmpToolButton.addSeparator(new Dimension(3, 0));
        EmpToolButton.add(searchButton);
        EmpToolButton.addSeparator(new Dimension(3, 0));
        EmpToolButton.add(addEmpButton);
        EmpToolButton.addSeparator(new Dimension(3, 0));
        EmpToolButton.add(UpdateEmpButton);
        EmpToolButton.addSeparator(new Dimension(3,0));
        EmpToolButton.add(ReconnectButton);

        Employees.add(EmpToolButton,BorderLayout.NORTH);
        Employees.add(scroll,BorderLayout.CENTER);

        return Employees;
    }

    public void createEmpdataWindow(String frameName,String ButtonText,int mode,int empid,JFrame mainframe) {
        JFrame addEmpFrame = new JFrame(frameName);
        addEmpFrame.setSize(400, 400);
        addEmpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        String[] labelStringemp = {
             "Name","Email",
            "Phone Number", "Salary", "Address","Date Joined(YYYY-MM-DD)" //chnage done
        };

        JLabel[] labelemp = new JLabel[6];
        JTextField[] textemp = new JTextField[6];  //CHANGE DOEN
    
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 10, 10)); // 8 rows, 2 columns
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    
        for (int i = 0; i < 6; i++) { //chnage done
            labelemp[i] = new JLabel(labelStringemp[i]);
            textemp[i] = new JTextField(15);
    
            panel.add(labelemp[i]);
            panel.add(textemp[i]);
        }

        JLabel empDepartment = new JLabel("Department");
        JLabel empGender = new JLabel("Gender");

        
        String[] departmentString = {"IT","Finance","HR","Management"};
        JComboBox<String> selectempdepartment = new JComboBox<>(departmentString);
        JComboBox<String> selectGender = new JComboBox<>(new String[] {"Male","Female","Other"});
        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        JButton DetailWindowButton;
        if(mode==1){
            DetailWindowButton = EmpGuiUtility.CustomButton(ButtonText, -1, 0);
            DetailWindowButton.addActionListener(e ->{

                Employee retriveddataTextfield = null;
                retriveddataTextfield = EmpGuiUtility.datavalidationfunc(textemp,selectGender,selectempdepartment,errorLabel);
              
                if(retriveddataTextfield == null)
                {
                    return;
                }
               
                boolean isadded = sqldata.addEmptoSQL(retriveddataTextfield);
                if(isadded){
                    EmpGuiUtility.DynamicTitleChange("Record Added successfully", 3000, mainframe, EmpGuiUtility.Green);
                }
                else{
                    EmpGuiUtility.DynamicTitleChange("Error Occured: Could't Add", 3000, mainframe, EmpGuiUtility.Red);
                }
                
                addEmpFrame.dispose();
            });
        }
        else{
            DetailWindowButton = EmpGuiUtility.CustomButton("Update", -1, 0);
            Employee EmployeeData = sqldata.getEmployeeDetail(empid);
            
            String[] EmpData = EmpGuiUtility.EmpObjectTOString(EmployeeData);
            for (int i = 0; i < 6; i++) {
                textemp[i].setText(String.valueOf(EmpData[i+1])); 
            }

            selectempdepartment.setSelectedItem(EmpData[7]);
            selectGender.setSelectedItem(EmpData[8]);

            DetailWindowButton.addActionListener(e ->{

                Employee retriveddataTextfield = null;
                retriveddataTextfield = EmpGuiUtility.datavalidationfunc(textemp,selectGender,selectempdepartment,errorLabel);
                if(retriveddataTextfield == null)
                {
                    return;
                }
                boolean isUpdated = sqldata.UpdateDatainSql(retriveddataTextfield,empid);

                if(isUpdated){
                    EmpGuiUtility.DynamicTitleChange("Record Updated successfully", 3000, mainframe, EmpGuiUtility.Green);
                }
                else{
                    EmpGuiUtility.DynamicTitleChange("Error Occured: Updation Failed", 3000, mainframe, EmpGuiUtility.Red);
                }

                addEmpFrame.dispose();
            });

            }

        panel.add(empDepartment);
        panel.add(selectempdepartment);
        panel.add(empGender);
        panel.add(selectGender);
        panel.add(errorLabel);
        panel.add(DetailWindowButton);

        addEmpFrame.add(panel);
        addEmpFrame.setLocationRelativeTo(null); // center on screen
        addEmpFrame.setVisible(true);
        
    }

}
