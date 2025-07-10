import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EmpGuiDashboard {

    private EmpSql sqldata = new EmpSql();

    private JLabel totalEmpLabel;
    private JLabel deptITLabel;
    private JLabel deptHRLabel;
    private JLabel deptFinLabel;
    private JLabel deptMgmtLabel;
    private JPanel recentEmpPanel;

    public JPanel createDashboardPanel(JFrame mainframe) {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Dashboard Overview", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setBorder(new EmptyBorder(20, 10, 10, 10));
        dashboard.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(30, 100, 30, 100));
        centerPanel.setBackground(new Color(245, 245, 245));

        totalEmpLabel = createStatLabel(centerPanel, "Total Employees: ");
        deptITLabel   = createStatLabel(centerPanel, "IT Department: ");
        deptHRLabel   = createStatLabel(centerPanel, "HR Department: ");
        deptFinLabel  = createStatLabel(centerPanel, "Finance Department: ");
        deptMgmtLabel = createStatLabel(centerPanel, "Management Department: ");

        JLabel recentTitle = new JLabel("Recently Added Employees");
        recentTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        recentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        centerPanel.add(recentTitle);

        recentEmpPanel = new JPanel();
        recentEmpPanel.setLayout(new BoxLayout(recentEmpPanel, BoxLayout.Y_AXIS));
        recentEmpPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(recentEmpPanel);

        dashboard.add(centerPanel, BorderLayout.CENTER);
        updateDashboardData(mainframe); // initial load

        return dashboard;
    }

    private JLabel createStatLabel(JPanel parent, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(5, 0, 5, 0));
        parent.add(label);
        return label;
    }

    public void updateDashboardData(JFrame mainframe) {
        if (!sqldata.ConnectToSql()) {
            EmpGuiUtility.DynamicTitleChange("Failed to Connect to DB", 3000, mainframe, EmpGuiUtility.Red);
            return;
        }

        totalEmpLabel.setText("Total Employees: " + sqldata.getTotalEmployeeCount());
        deptITLabel.setText("IT Department: " + sqldata.getDepartmentCount("IT"));
        deptHRLabel.setText("HR Department: " + sqldata.getDepartmentCount("HR"));
        deptFinLabel.setText("Finance Department: " + sqldata.getDepartmentCount("Finance"));
        deptMgmtLabel.setText("Management Department: " + sqldata.getDepartmentCount("Management"));

        // Clear and rebuild recent employees
        recentEmpPanel.removeAll();
        List<Employee> recent = sqldata.getRecentEmployees(3);
        for (Employee emp : recent) {
            JLabel label = new JLabel(emp.getId() + ". " + emp.getName() + " | Joined: " + emp.getDOJ());
            label.setFont(new Font("SansSerif", Font.PLAIN, 14));
            label.setBorder(new EmptyBorder(2, 0, 2, 0));
            recentEmpPanel.add(label);
        }

        recentEmpPanel.revalidate();
        recentEmpPanel.repaint();
    }
}
