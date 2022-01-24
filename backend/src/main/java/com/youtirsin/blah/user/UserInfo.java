package com.youtirsin.blah.user;

import java.util.Objects;

public class UserInfo {
    private final String name;
    private final String email;
    private final String description;
    public UserInfo() {
        name = null;
        email = null;
        description = null;
    }
    public UserInfo(String name, String email, String description) {
        this.name = name;
        this.email = email;
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getDescription() {
        return description;
    }
    @Override
    public String toString() {
        return "RegistrationRequest [name=" + name + ", email=" + email + ", description=" + description + "]";
    }
    @Override
    public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserInfo other = (UserInfo) obj;
        return Objects.equals(email, other.email) && Objects.equals(name, other.name)
                && Objects.equals(description, other.description);
    }
    @Override
    public int hashCode() {
		return Objects.hash(email, name, description);
    }
}
