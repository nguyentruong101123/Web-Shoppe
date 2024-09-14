package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Color;
import com.example.demo.repository.ColorRepository;
import com.example.demo.services.ColorService;

@Service
public class ColorServiceImpl implements ColorService{
	
	@Autowired
	ColorRepository colorRepository;

	@Override
	public Color createColor(Color color) {
		return colorRepository.save(color);
	}

	@Override
	public List<Color> getAll() {
		return colorRepository.findAll();
	}
}
