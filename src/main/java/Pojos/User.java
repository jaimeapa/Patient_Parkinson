package Pojos;


import java.util.Arrays;
import java.util.Objects;


public class User {
    private Integer id;
    private String email;
    private byte[] password;
    private Role role;

    public User (int id, String email, byte[] password, Role role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User(String email, byte[] password, Role role) {
        super();
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email)  {
        this.email = email;
    }
    public byte[] getPassword() {
        return password;
    }
    public void setPassword(byte[] password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(password);
        result = prime * result + Objects.hash(email, id, role);
        return result;
    }
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
    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + Arrays.toString(password) + ", role=" + role
                + "]";
    }
}
