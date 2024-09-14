package com.example.demo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.services.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	UserProfileRepository profileRepository;

	@Override
	public UserProfile getUserProfileByUserId(Integer userId) {
		return  profileRepository.findByUserId(userId);
	}

	@Override
	public UserProfile update(Integer userId, UserProfile profile) throws Exception {
		UserProfile userProfile = profileRepository.findByUserId(userId);
		if (userProfile != null) {
			userProfile.setStreetAddress(profile.getStreetAddress());
			userProfile.setCity(profile.getCity());
			userProfile.setState(profile.getState());
			userProfile.setZipCode(profile.getZipCode());
			userProfile.setCountry(profile.getCountry());
			userProfile.setPhoneNumber(profile.getPhoneNumber());
			userProfile.setDateOfBirth(profile.getDateOfBirth());
			userProfile.setPlaceOfBirth(profile.getPlaceOfBirth());
			
			return profileRepository.save(userProfile);
		}else {
			throw new Exception("UserProfile with userId " + userId + " not found.");
		}
		
	}

	@Override
	public UserProfile createUserProfile(UserProfile userProfile) {
		return profileRepository.save(userProfile);
	}

}
