package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Colors")
@AllArgsConstructor
@NoArgsConstructor
public class Color implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String colorName;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "color", fetch = FetchType.EAGER)
    private List<ProductAttribute> productAttributes;

	public Color(Integer id) {
		super();
		this.id = id;
	}
    
    
}
