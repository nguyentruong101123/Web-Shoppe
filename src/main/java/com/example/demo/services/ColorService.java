package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.Color;

public interface ColorService {

	public Color createColor(Color color);
	
	public List<Color> getAll();
	
}
