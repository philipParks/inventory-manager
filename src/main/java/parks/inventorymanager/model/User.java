package parks.inventorymanager.model;

/** Defines a user object */
public class User {

    private int userId;
    private String userName;
    private String password;

    /** Overrides the toString() method to only return the name of the division.
     * @return Only the name of the first level divisions. */
    @Override
    public String toString() {
        return userName;
    }

    /** Constructs a user object.
     * @param id Automatically assigned unique value.
     * @param name The user's name.
     * @param password The user's password.*/
    public User(int id, String name, String password) {
        this.userId = id;
        this.userName = name;
        this.password = password;
    }

    /** Gets user id.
     * @return Automatically assigned unique value. */
    public int getUserId() {
        return userId;
    }

    /** Sets the user id.
     * @param id The id of the authorized user. */
    public void setUserId(int id) {
        this.userId = id;
    }

    /** Gets username.
     * @return User name. */
    public String getUserName() {
        return userName;
    }

    /** Sets the userName.
     * @param userName The name of the authorized user. */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Gets user password.
     * @return User password. */
    public String getPassword() {
        return password;
    }

    /** Sets the user password. */
    public void setPassword() {
        this.password = null;
    }

}
