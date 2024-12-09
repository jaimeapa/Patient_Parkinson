package Pojos;

import java.util.List;
import java.util.Objects;

public class Role {

    private Integer id;
    private String name;
    private List<User> users;

    /**
     * Constructs a new Role with the specified name.
     *
     * @param name The name of the role.
     */
    public Role(String name) {
        super();
        this.name = name;
    }

    /**
     * Gets the unique identifier of the role.
     *
     * @return The role ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the role.
     *
     * @param id The role ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the role.
     *
     * @return The name of the role.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of users assigned to this role.
     *
     * @return A list of users.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the list of users assigned to this role.
     *
     * @param users The list of users to set.
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Returns a hash code value for the object, which is used for equality comparisons.
     *
     * @return A hash code value for the role object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, users);
    }

    /**
     * Compares this role to another object for equality.
     * Two roles are considered equal if they have the same ID, name, and associated users.
     *
     * @param obj The object to compare this role with.
     * @return true if the roles are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(users, other.users);
    }

    /**
     * Returns a string representation of the role.
     * This method returns only the name of the role.
     *
     * @return A string representing the role.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
