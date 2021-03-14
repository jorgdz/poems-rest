package com.jorgediaz.hbsb.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.JoinColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonInclude(Include.NON_NULL)
public class User implements Serializable {
	
	private static final long serialVersionUID = 1041007460204211110L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El primer nombre es obligatorio.")
	private String firstname;
	
	@NotEmpty(message = "El segundo nombre es obligatorio.")
	private String lastname;
	
	@NotEmpty(message = "El correo es obligatorio.")
	@Email(message = "El correo no tiene un formato válido")
	private String email;
	
	private Boolean enabled;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotEmpty(message = "La contraseña es obligatoria.")
	private String password;
	
	private Boolean tokenexpired;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_roles", 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	)
	@JsonIgnoreProperties({"users", "privileges"})
	@OrderBy("id ASC")
	private Set<Role> roles;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
			+ ", enabled=" + enabled + ", password=" + password + ", tokenexpired=" + tokenexpired + "]";
	}

}
