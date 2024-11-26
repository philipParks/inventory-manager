package parks.inventorymanager.model;

/** Defines a user. */
public class User {

    private int userId;
    private String userName;
    private String password;
    private boolean isAdmin;

    /** Overrides the toString() method to only return the username of the user.
     * @return The username of the user. */
    @Override
    public String toString() {
        return userName;
    }

    /** Constructs a user.
     * @param id Automatically assigned unique value.
     * @param name The user's name.
     * @param password The user's password.*/
    public User(int id, String name, String password, boolean isAdmin) {
        this.userId = id;
        this.userName = name;
        this.password = password;
        this.isAdmin = isAdmin;
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
    public void setPassword(String password) {
        this.password = password;
    }

    /** Gets the user admin status.
     * @return The user admin status. */
    public boolean getAdmin() {
        return isAdmin;
    }

    /** Sets the user admin status.
     * @param admin The user admin status. */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
