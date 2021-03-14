package com.jorgediaz.hbsb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "privilege")
public class Privilege implements Serializable {

	private static final long serialVersionUID = 2696996947969705926L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 3, max = 255, message = "El privilegio debe ser mayor a 3 y menor a 255 caracteres.")
	@NotEmpty(message = "El privilegio es obligatorio.")
	private String name;
	
	@ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"privileges"})
	private List<Role> roles;
	
	@Override
	public String toString() {
		return "Privilege [id=" + id + ", name=" + name + "]";
	}
}
