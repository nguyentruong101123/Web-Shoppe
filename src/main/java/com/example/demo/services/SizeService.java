package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.Size;

public interface SizeService {
	
	public Size createSize(Size size);
	
	public List<Size> getAll();
}
