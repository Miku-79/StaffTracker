import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EmpPropHandler {

    private Properties file;
    
    EmpPropHandler(){
        file = new Properties();
        try(FileInputStream FileStream = new FileInputStream("./Assets/Settings.properties");){ //create a Input straem for file
            file.load(FileStream);
            System.out.println("Settings.Properties file Loaded to memory");
        }
        catch(IOException e){
            System.out.println("Error Loading Settings: "+e.getMessage());
        } 
    }

    public boolean SaveSettings(){

        try (FileOutputStream FileStream = new FileOutputStream("./Assets/Settings.properties")) {
            file.store(FileStream, "Setting Saved");
        } catch (IOException e) {
            System.out.println("Error OCcured: "+e.getMessage());
            return false;
        }
        return true;
    }

    public String getUsername(){ 
        return file.getProperty("Username");
    }

    public String getPassword(){
        return file.getProperty("Password");
    }

    public String getDBUsername(){
        return file.getProperty("DBUsername");
    }

    public String getDBPassword(){
        return file.getProperty("DBPassword");
    }

    public String getDBHost() {
        return file.getProperty("DBHost");
    }
    
    public String getDBPort() {
        return file.getProperty("DBPort");
    }
    
    public String getDBName() {
        return file.getProperty("DBName");
    }

//Setters

    public void setUsername(String value) {
        file.setProperty("Username", value);
    }

    public void setPassword(String value) {
        file.setProperty("Password", value);
    }

    public void setDBUsername(String value) {
        file.setProperty("DBUsername", value);
    }

    public void setDBPassword(String value) {
        file.setProperty("DBPassword", value);
    }

    public void setDBHost(String value) {
        file.setProperty("DBHost", value);
    }
    
    public void setDBPort(String value) {
        file.setProperty("DBPort", value);
    }
    
    public void setDBName(String value) {
        file.setProperty("DBName", value);
    }
    
}

