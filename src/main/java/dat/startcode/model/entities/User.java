package dat.startcode.model.entities;

import java.util.Objects;

public class User
{
    private String email;
    private String phoneNo;
    private String name;
    private String role;
    private int familyId;
    private String password;

    public User(String email, String phoneNo, String name, String role, int familyId, String password)
    {
        this.email = email;
        this.phoneNo = phoneNo;
        this.name = name;
        this.familyId = familyId;
        this.role = role;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", phoneNo=" + phoneNo +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", familyId=" + familyId +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getName() {
        return name;
    }

    public int getFamilyId() {
        return familyId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword()) &&
                getRole().equals(user.getRole());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getEmail(), getPassword(), getRole());
    }
}
