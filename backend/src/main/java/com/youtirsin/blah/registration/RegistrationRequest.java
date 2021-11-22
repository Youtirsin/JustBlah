package com.youtirsin.blah.registration;

import java.util.Objects;

public class RegistrationRequest {
	private final String name;
	private final String password;
	private final String email;
	
	public RegistrationRequest(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	@Override
	public String toString() {
		return "RegistrationRequest [name=" + name + ", password=" + password + ", email=" + email + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, name, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationRequest other = (RegistrationRequest) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}
	
}
