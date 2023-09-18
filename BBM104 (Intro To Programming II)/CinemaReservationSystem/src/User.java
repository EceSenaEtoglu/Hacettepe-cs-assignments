import java.util.Locale;

public class User {


    private final String userName;

    // hash + base64 encoded
    private final String password;

    private boolean isClubMember;
    private boolean isAdmin;
    

    public void setIsClubMember(boolean clubMember) {
        isClubMember = clubMember;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public User(String name, String password, String isAClubMember, String isAdmin) {
       
        this.userName = name;
        this.password = password;
        this.isClubMember = isAClubMember.toLowerCase(Locale.ROOT).equals("true");
        this.isAdmin = isAdmin.toLowerCase(Locale.ROOT).equals("true");
    }

    public String getUserName() {return this.userName;}

    public String getPassword() { return this.password;}

    public boolean getIsAdmin() {return this.isAdmin;}

    public boolean getIsClubMember() {return this.isClubMember;}

    public String getMemberInString() {
         if(this.isClubMember) {
            return "true";
        }
         else{
             return "false";
         }
    }

    public String getAdminInString() {
        if(this.isAdmin) {
            return "true";
        }
        
        else {
            return "false";
        }
    }

    public String toString(){
        return this.userName;
    }
    
}
