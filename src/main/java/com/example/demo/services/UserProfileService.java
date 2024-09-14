package com.example.demo.services;


import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.demo.entity.UserProfile;

public interface UserProfileService {
	
	public UserProfile getUserProfileByUserId(Integer userId);

	@Secured("hasRole('USER')")
	public UserProfile update(Integer userId, UserProfile profile) throws Exception;
	
	@PreAuthorize("hasRole('USER')")
	public UserProfile createUserProfile(UserProfile userProfile);
	
}
