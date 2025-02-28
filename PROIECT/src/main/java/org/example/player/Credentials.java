package org.example.player;

public class Credentials {
    private final String email;
    private final String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Information{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
