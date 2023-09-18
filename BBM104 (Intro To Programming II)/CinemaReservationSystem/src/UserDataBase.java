import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class UserDataBase {

    // class for user operations

    // holds current user

    // hold user map

    static User currentUser;

    private static HashMap<String,User> userMap  =new HashMap<>();


    // initializes data base
    public static void initializeDataBase(HashMap<String,User> userData) {


        // if no user data is provided in backup.dat
        // create user admin

        if(userData.size() == 0) {

            initializeDataBase();

        }
        else {
            userMap = userData;
        }
    }

    public static void initializeDataBase () {

        String password = hashPassword("password");

        User user = new User("admin",password,"true","true");
        userMap.put(user.getUserName(),user);

    }


    // returns hashed + base 64 encoded version of given plain text password
    private static String hashPassword ( String password ) {
        byte [ ] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8) ;

        byte [ ] md5Digest;

        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword ) ;
        } catch ( NoSuchAlgorithmException e ) {
            return null ;
        }
        return Base64.getEncoder().encodeToString( md5Digest ) ;
    }



    public static HashMap<String, User> getUserMap() {
        return userMap;
    }


    public static User getUserFromName(String userName) { return userMap.get(userName);}

    public static void addUser(String userName,String password) {

        User user = new User(userName,hashPassword(password),"false","false");
        userMap.put(user.getUserName(),user);
    }

    // return true if exist
    public static boolean doesUserExist(String userName) {
        return userMap.containsKey(userName);
    }


    // if is admin return false
    public static boolean isANormalUser(String userName) {
        return !userMap.get(userName).getIsAdmin();
    }


    // if an existing user !
    // if pass matches with given pass
    public static boolean isACredential(String userName, String givenPassword) {

        String systemPassword = userMap.get(userName).getPassword();
        return systemPassword.equals(hashPassword(givenPassword));

    }

    public static ArrayList<User> getFilteredUsers(User filter) {

        ArrayList<User> copiedFilteredUser = new ArrayList<>(userMap.values());
        copiedFilteredUser.remove(filter);
        return copiedFilteredUser;


    }

    public static User getCurrentUser() {
        return currentUser;
    }





}


