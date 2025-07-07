import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class EmpSql {

private static Connection conn;

    public boolean ConnectToSql(){

        try{
            EmpPropHandler propHandler = new EmpPropHandler();
            String DBuser = propHandler.getDBUsername();
            String DBpass = propHandler.getDBPassword();
            String DBhost = propHandler.getDBHost();
            String DBport = propHandler.getDBPort();
            String DBname = propHandler.getDBName();

            String url = "jdbc:mysql://" +DBhost+ ":" +DBport+ "/" +DBname;

            if(conn == null){
                conn = DriverManager.getConnection(url, DBuser, DBpass);
                System.out.println("= SQL Connection done!");   
            }
        } catch(SQLException e) {
            System.out.println("=SQL Connection failed!\n");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void getallEmployee(DefaultTableModel TableModel){

        try(Statement stmt = conn.createStatement()){
            ResultSet result = stmt.executeQuery("Select * from Emp_data_table;");
            Employee rowdata;
            while (result.next()) {
                rowdata = ExtractEmployee(result);
                TableModel.addRow(new Object[]{
                    rowdata.getId(),
                    rowdata.getName(),
                    rowdata.getDepartment(),
                    rowdata.getEmail(),
                    rowdata.getPhoneNumber(),
                    rowdata.getSalary(),
                    rowdata.getDOJ(),  //change done
                    rowdata.getAddress(),
                    rowdata.getGender()
                });   
            }
          }catch(Exception e){
            System.out.println("Error occured: "+e);
        }
    }

    public void getSearchedEmp(String seachedValue, DefaultTableModel DefaultTableModel){

        String SearchQuery = "SELECT * FROM Emp_data_table WHERE " +
                    "CAST(Emp_id AS CHAR) LIKE ? OR Emp_Name LIKE ? OR department LIKE ? OR " +
                    "email LIKE ? OR phoneNumber = ? OR CAST(salary AS CHAR) LIKE ? OR " +
                    "address LIKE ? OR gender LIKE ?";

        // ? Are placeholders, where actual value be replaced

        String likepattern = "%"+seachedValue+"%";
        String likeStart = seachedValue+"%";

        try(PreparedStatement preStatement = conn.prepareStatement(SearchQuery)){

            preStatement.setString(1, likepattern);  // CAST(Emp_id AS CHAR) LIKE ?
            preStatement.setString(2, likepattern);  // Emp_Name LIKE ?
            preStatement.setString(3, likepattern);  // department LIKE ?
            preStatement.setString(4, likepattern);  // email LIKE ?
            preStatement.setString(5, seachedValue); // phoneNumber = ?
            preStatement.setString(6, likepattern);  // salary as CHAR LIKE ?
            preStatement.setString(7, likepattern);  // address LIKE ?
            preStatement.setString(8, likeStart);    // gender LIKE ?

            ResultSet result2 = preStatement.executeQuery();
            
            Employee rowdata;
            while(result2.next())
            {
                rowdata = ExtractEmployee(result2);
                DefaultTableModel.addRow(new Object[] {
                    rowdata.getId(),
                    rowdata.getName(),
                    rowdata.getDepartment(),
                    rowdata.getEmail(),
                    rowdata.getPhoneNumber(),
                    rowdata.getSalary(),
                    rowdata.getDOJ(),
                    rowdata.getAddress(),
                    rowdata.getGender()
                });
            }
        }catch(Exception e){
            System.out.println("Error Ocurred: "+e);
        }
    }

    public boolean addEmptoSQL(Employee retrivedData){
        
        String AddQuery = "INSERT INTO Emp_data_table (Emp_Name, email, phoneNumber, salary, address, Date_Joined, department, gender) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try(PreparedStatement preStatement = conn.prepareStatement(AddQuery)){
            
            preStatement.setString(1, retrivedData.getName());
            preStatement.setString(2, retrivedData.getEmail());
            preStatement.setString(3, retrivedData.getPhoneNumber());
            preStatement.setInt(4, retrivedData.getSalary()); // Salary is int
            preStatement.setString(5, retrivedData.getAddress());
            preStatement.setString(6, retrivedData.getDOJ());   //change done
            preStatement.setString(7, retrivedData.getDepartment());
            preStatement.setString(8, retrivedData.getGender());
            
            int result = preStatement.executeUpdate();
            if(result >0 ){
                System.out.println("Insertion successfull.");
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println("Error Ocurred: "+e);
            return false;
        }
    }

    public boolean deleteEmployee(int ID){

        String deleteQuery = "DELETE FROM Emp_data_table WHERE Emp_id =?;";


        try(PreparedStatement preStatement = conn.prepareStatement(deleteQuery)){

            preStatement.setInt(1, ID);
            int result = preStatement.executeUpdate();
            if(result >0 ){
                System.out.println("Deletion successfull.");
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println("Error Ocurred: "+e);
            return false;
        }
    }

    public Employee getEmployeeDetail(int searchedID){

        String searchQuery = "select * from Emp_data_table where Emp_id =?;";
        Employee EmployeeData=null;

        try(PreparedStatement preStatement = conn.prepareStatement(searchQuery);){
            
            preStatement.setInt(1, searchedID);
            ResultSet result = preStatement.executeQuery();
            
            while(result.next())
            {
                EmployeeData = ExtractEmployee(result);
            }
        }catch(Exception e){
            System.out.println("Error Ocurred: "+e);
        }
        return EmployeeData;
    }

    public boolean UpdateDatainSql(Employee retriveddata,int empid){
       
        String updateQuery = "UPDATE Emp_data_table SET " +
        "Emp_name = ?, Email = ?, PhoneNumber = ?, Salary = ?, " +
        "Department = ?, Address = ?, Date_Joined = ? WHERE Emp_id = ?";

        try(PreparedStatement preStatement = conn.prepareStatement(updateQuery)){
            
            preStatement.setString(1, retriveddata.getName());
            preStatement.setString(2, retriveddata.getEmail());
            preStatement.setString(3, retriveddata.getPhoneNumber()); 
            preStatement.setInt(4, retriveddata.getSalary());
            preStatement.setString(5, retriveddata.getDepartment());
            preStatement.setString(6, retriveddata.getAddress());
            preStatement.setString(7, retriveddata.getDOJ());  //change done
            preStatement.setInt(8, empid); 

            int result2 = preStatement.executeUpdate();
            
            if(result2 >0 ){
                System.out.println("Updation successfull.");
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println("Error Ocurred: "+e);
            return false;
        }
    }

    public int getTotalEmployeeCount() {
        String query = "SELECT COUNT(*) AS total FROM Emp_data_table;";
        int count = 0;
    
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error Occurred in getTotalEmployeeCount: " + e);
        }
    
        return count;
    }

    public int getDepartmentCount(String deptName) {
        String query = "SELECT COUNT(*) AS count FROM Emp_data_table WHERE Department = ?";
        int count = 0;
    
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, deptName);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("Error Occurred in getDepartmentCount: " + e);
        }
    
        return count;
    }    

    public List<Employee> getRecentEmployees(int limit) {
        String query = "SELECT * FROM Emp_data_table ORDER BY Date_Joined DESC LIMIT ?;";
        List<Employee> recentEmployees = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                recentEmployees.add(ExtractEmployee(rs)); // assumes ExtractEmployee is your existing helper method
            }
        } catch (Exception e) {
            System.out.println("Error Occurred in getRecentEmployees: " + e);
        }

        return recentEmployees;
    }

    public Employee ExtractEmployee(ResultSet result) throws SQLException{
        
        return new Employee(
            result.getInt("Emp_id"),
            result.getString("Emp_name"),
            result.getString("email"),
            result.getString("phoneNumber"),
            result.getInt("salary"),
            result.getString("address"),
            result.getString("Date_Joined"), //Change done
            result.getString("department"),
            result.getString("gender"));
    }
}


// conn.close(); IMPOERTANT USE THIS 