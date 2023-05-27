package br.dev.mission.springboot.api.models;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "catalog_product")
@Data
public class ProductModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Size(min = 3, max = 30)
	@NotBlank
	@NotNull(message = "Required field name")
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	@Column(nullable = false)
	private String brand;
}
