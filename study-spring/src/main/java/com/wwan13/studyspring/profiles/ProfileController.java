package com.wwan13.studyspring.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "api/profile", produces = "application/json")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping
    public ResponseEntity createProfile(@RequestBody Profile profile) {

        Profile newProfile = this.profileService.createProfile(profile);
        URI createdUri = linkTo(methodOn(ProfileController.class).createProfile(profile)).slash(newProfile.getId()).toUri();

        return ResponseEntity.created(createdUri).body(profile);
    }

    @GetMapping
    public ResponseEntity getAllProfiles() {

        List<Profile> allProfiles = this.profileService.getAllProfiles();

        return ResponseEntity.ok().body(allProfiles);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getProfileById(@PathVariable Integer id) {

        Profile profile = this.profileService.findProfileById(id);
        if (profile == null) {
            return ResponseEntity.badRequest().body("invalid ID");
        }

        return ResponseEntity.ok().body(profile);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteProfile(@PathVariable Integer id) {

        Profile profile = this.profileService.findProfileById(id);
        if (profile == null) {
            return ResponseEntity.badRequest().body("invalid ID");
        }

        this.profileService.deleteProfileById(id);

        return ResponseEntity.ok().body(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateProfile(@PathVariable Integer id, @RequestBody Profile profile) {

        Profile updatedProfile = this.profileService.updateProfile(id, profile);
        return ResponseEntity.ok().body(updatedProfile);
    }

}
