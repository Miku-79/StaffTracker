import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EmpGuiDashboard {

    private EmpSql sqldata = new EmpSql(); //Created object like EmpGuiEmployee

    public EmpGuiDashboard() {
        // Empty constructor
    }

    public JPanel createDashboardPanel(JPanel dashboard, JFrame mainframe) {
        dashboard.setLayout(new BorderLayout());
        dashboard.setBackground(new Color(245, 245, 245));

        // Title
        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        dashboard.add(title, BorderLayout.NORTH);

        // Main container with vertical layout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.setBorder(new EmptyBorder(30, 100, 30, 100));

        boolean isConnected = sqldata.ConnectToSql();
        if (!isConnected) {
            EmpGuiUtility.DynamicTitleChange("Failed to Connect to DB", 3000, mainframe, EmpGuiUtility.Red);
        }

        int totalEmp = sqldata.getTotalEmployeeCount();
        int deptIT = sqldata.getDepartmentCount("IT");
        int deptHR = sqldata.getDepartmentCount("HR");
        int deptFin = sqldata.getDepartmentCount("Finance");
        int deptMgmt = sqldata.getDepartmentCount("Management");

        Font labelFont = new Font("SansSerif", Font.PLAIN, 16);

        centerPanel.add(createAlignedLabel("Total Employees: " + totalEmp, labelFont));
        centerPanel.add(createAlignedLabel("IT Department: " + deptIT, labelFont));
        centerPanel.add(createAlignedLabel("HR Department: " + deptHR, labelFont));
        centerPanel.add(createAlignedLabel("Finance Department: " + deptFin, labelFont));
        centerPanel.add(createAlignedLabel("Management Department: " + deptMgmt, labelFont));

        JLabel recentTitle = new JLabel("Recently Added Employees");
        recentTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        recentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        centerPanel.add(recentTitle);

        List<Employee> recent = sqldata.getRecentEmployees(3);
        for (Employee emp : recent) {
            centerPanel.add(createAlignedLabel(emp.getId() + ". " + emp.getName() + " | Joined: " + emp.getDOJ(), new Font("SansSerif", Font.PLAIN, 14)));
        }

        dashboard.add(centerPanel, BorderLayout.CENTER);
        return dashboard;
    }

    private JLabel createAlignedLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(5, 0, 5, 0));
        return label;
    }
}
