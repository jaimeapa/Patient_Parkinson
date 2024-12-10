package Pojos;


import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a user in the system.
 * It contains details such as the user's ID, email, password, and role.
 */
public class User {
    /**
     * The ID of the user.
     */
    private Integer id;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user, stored as a byte array.
     */
    private byte[] password;

    /**
     * The role assigned to the user (e.g., Admin, User).
     */
    private Role role;


    /**
     * Constructor to initialize a user with all attributes.
     * @param id the unique identifier for the user.
     * @param email the email address of the user.
     * @param password the hashed password of the user.
     * @param role the role assigned to the user.
     */
    public User (int id, String email, byte[] password, Role role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    /**
     * Constructor to initialize a user without an ID.
     * Useful for creating new users before assigning an ID.
     * @param email the email address of the user.
     * @param password the hashed password of the user.
     * @param role the role assigned to the user.
     */
    public User(String email, byte[] password, Role role) {
        super();
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the ID of the user.
     *
     * @return The ID of the user.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id The ID to set for the user.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for the user.
     *
     * @param email The email address to set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user, stored as a byte array.
     *
     * @return The user's password.
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * Sets the password for the user, stored as a byte array.
     *
     * @param password The password to set for the user.
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * Returns the role assigned to the user (e.g., Admin, User).
     *
     * @return The role of the user.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role for the user.
     *
     * @param role The role to set for the user.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Generates a hash code for the User object.
     * @return the hash code for the user.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(password);
        result = prime * result + Objects.hash(email, id, role);
        return result;
    }
    /**
     * Compares this user object with another for equality.
     * @param obj the object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return Objects.equals(email, other.email) && Objects.equals(id, other.id)
                && Arrays.equals(password, other.password) && Objects.equals(role, other.role);
    }
    /**
     * Returns a string representation of the user.
     * Includes ID, email, password, and role.
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + Arrays.toString(password) + ", role=" + role
                + "]";
    }
}
