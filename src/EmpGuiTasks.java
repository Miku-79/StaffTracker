import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class EmpGuiTasks {

    private EmpSql sqldata = new EmpSql();

    public JPanel createTasksPanel(JPanel Tasks, JFrame mainframe) {

        String[] colName = {"Task ID", "Task Description", "Employee ID", "Employee Name"};

        DefaultTableModel TaskTableModel = new DefaultTableModel(null, colName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        boolean isConnected = sqldata.ConnectToSql();
        if (isConnected) {
            sqldata.getAllTasks(TaskTableModel);
            EmpGuiUtility.DynamicTitleChange("Connected to DB", 3000, mainframe, EmpGuiUtility.Green);
        } else {
            EmpGuiUtility.DynamicTitleChange("Failed to Connect to DB", 3000, mainframe, EmpGuiUtility.Red);
        }

        JTable table = new JTable(TaskTableModel);
        JScrollPane scroll = new JScrollPane(table);

        Tasks.setLayout(new BorderLayout());

        // Task ID input field (for Delete and Update)
        JTextField taskIdField = new JTextField(10);
        taskIdField.setPreferredSize(new Dimension(20, 27));

        JButton addTaskButton = EmpGuiUtility.CustomButton("Add Task", -1, 0);
        JButton deleteTaskButton = EmpGuiUtility.CustomButton("Delete Task", -1, 0);
        JButton updateTaskButton = EmpGuiUtility.CustomButton("Update Task", -1, 0);
        JButton ReconnectButton = EmpGuiUtility.CustomButton(null, -1, 0);

        ImageIcon RetryIcon = new ImageIcon("./Assets/Retry.png");
        RetryIcon = new ImageIcon(RetryIcon.getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH));
        ReconnectButton.setIcon(RetryIcon);

        addTaskButton.addActionListener(e -> {
            createTaskWindow("Add Task", "Add", 1, 0, mainframe, TaskTableModel);
        });

        deleteTaskButton.addActionListener(e -> {
            String taskIdText = taskIdField.getText().trim();
            if (taskIdText.isEmpty() || !taskIdText.matches("^\\d+$")) {
                EmpGuiUtility.DynamicTitleChange("Invalid Task ID", 3000, mainframe, EmpGuiUtility.Red);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Delete Task ID " + taskIdText + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean isDeleted = sqldata.deleteTask(Integer.parseInt(taskIdText));
                if (isDeleted) {
                    EmpGuiUtility.DynamicTitleChange("Task Deleted", 3000, mainframe, EmpGuiUtility.Green);
                    TaskTableModel.setRowCount(0);
                    sqldata.getAllTasks(TaskTableModel);
                } else {
                    EmpGuiUtility.DynamicTitleChange("Task Not Deleted", 3000, mainframe, EmpGuiUtility.Red);
                }
            }
        });

        updateTaskButton.addActionListener(e -> {
            String taskIdText = taskIdField.getText().trim();
            if (taskIdText.isEmpty() || !taskIdText.matches("^\\d+$")) {
                EmpGuiUtility.DynamicTitleChange("Invalid Task ID", 3000, mainframe, EmpGuiUtility.Red);
                return;
            }
            createTaskWindow("Update Task", "Update", 0, Integer.parseInt(taskIdText), mainframe, TaskTableModel);
        });

        ReconnectButton.addActionListener(e -> {
            if(sqldata.connClose() && sqldata.ConnectToSql()){
                TaskTableModel.setRowCount(0);
                sqldata.getAllTasks(TaskTableModel); 
                EmpGuiUtility.DynamicTitleChange("Connected to DB", 3000, mainframe, EmpGuiUtility.Green);
            }
            else{
                EmpGuiUtility.DynamicTitleChange("Failed to Connect to DB", 3000, mainframe, EmpGuiUtility.Red);
            }
        });

        // Top toolbar
        JToolBar TaskToolButton = new JToolBar();
        TaskToolButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        TaskToolButton.add(deleteTaskButton);
        TaskToolButton.addSeparator(new Dimension(3, 0));
        TaskToolButton.add(taskIdField);
        TaskToolButton.addSeparator(new Dimension(3, 0));
        TaskToolButton.add(addTaskButton);
        TaskToolButton.addSeparator(new Dimension(3, 0));
        TaskToolButton.add(updateTaskButton);
        TaskToolButton.addSeparator(new Dimension(3, 0));
        TaskToolButton.add(ReconnectButton);

        Tasks.add(TaskToolButton, BorderLayout.NORTH);
        Tasks.add(scroll, BorderLayout.CENTER);

        return Tasks;
    }

    public void createTaskWindow(String frameName, String buttonText, int mode, int taskId, JFrame mainframe, DefaultTableModel tableModel) {
        JFrame taskFrame = new JFrame(frameName);
        taskFrame.setSize(350, 200);
        taskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel[] labels = new JLabel[2];
        JTextField[] textFields = new JTextField[2];
        String[] fieldNames = {"Task Description", "Employee ID"};

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 2; i++) {
            labels[i] = new JLabel(fieldNames[i]);
            textFields[i] = new JTextField(15);
            panel.add(labels[i]);
            panel.add(textFields[i]);
        }

        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        JButton actionButton = EmpGuiUtility.CustomButton(buttonText, -1, 0);

        if (mode == 0) {
            String[] taskData = sqldata.getTaskDetail(taskId);
            if (taskData != null) {
                textFields[0].setText(taskData[0]);
                textFields[1].setText(taskData[1]);
            } else {
                errorLabel.setText("Task not found.");
            }
        }

        actionButton.addActionListener(e -> {
            String taskDesc = textFields[0].getText().trim();
            String empIdText = textFields[1].getText().trim();

            if (taskDesc.isEmpty() || !empIdText.matches("^\\d+$")) {
                errorLabel.setText("Invalid input fields.");
                return;
            }

            boolean success;
            if (mode == 1) {
                success = sqldata.addTask(taskDesc, Integer.parseInt(empIdText));
            } else {
                success = sqldata.updateTask(taskId, taskDesc, Integer.parseInt(empIdText));
            }

            if (success) {
                EmpGuiUtility.DynamicTitleChange("Task Saved", 3000, mainframe, EmpGuiUtility.Green);
                taskFrame.dispose();
                tableModel.setRowCount(0);
                sqldata.getAllTasks(tableModel);
            } else {
                EmpGuiUtility.DynamicTitleChange("Error Saving Task", 3000, mainframe, EmpGuiUtility.Red);
            }
        });

        panel.add(errorLabel);
        panel.add(actionButton);

        taskFrame.add(panel);
        taskFrame.setLocationRelativeTo(null);
        taskFrame.setVisible(true);
    }
}
