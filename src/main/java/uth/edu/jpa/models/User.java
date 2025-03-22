package uth.edu.jpa.models;
import jakarta.persistence.*;
@Entity
@Table (name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    private String username;
    private String password;
    private String email;
    private String fullname;

    @Enumerated(EnumType.STRING)
    private Role role;
    //Getter Setter

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public Role getRole() {
        return role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {

        this.fullname = fullname;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Constructors
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.PLAYER;
    }
    public User() {}
    public User(String username, String password, String email, String fullname, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
    }




    public enum Role {
        ADMIN, COURT_MANAGER, COACH, PLAYER
    }
}
