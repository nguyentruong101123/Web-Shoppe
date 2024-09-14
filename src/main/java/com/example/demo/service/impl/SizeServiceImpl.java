package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Size;
import com.example.demo.repository.SizeRepository;
import com.example.demo.services.SizeService;

@Service
public class SizeServiceImpl implements SizeService{

	@Autowired
	SizeRepository repository;
	
	@Override
	public Size createSize(Size size) {
		return repository.save(size);
	}

	@Override
	public List<Size> getAll() {
		return repository.findAll();
	}

}
