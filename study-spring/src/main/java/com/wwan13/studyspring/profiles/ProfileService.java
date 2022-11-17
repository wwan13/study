package com.wwan13.studyspring.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    public Profile createProfile(Profile profile) {
        return this.profileRepository.save(profile);
    }

    public List<Profile> getAllProfiles() {
        return this.profileRepository.findAll();
    }

    public Profile findProfileById(Integer id) {
        return this.profileRepository.findById(id).orElse(null);
    }

    public void deleteProfileById(Integer id) {
        this.profileRepository.deleteById(id);
    }

    public Profile updateProfile(Integer id, Profile profile) {
        Profile originProfile = this.findProfileById(id);
        originProfile = profile;
        Profile updatedProfile = this.profileRepository.save(originProfile);

        return updatedProfile;
    }
    
}
