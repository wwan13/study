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
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping(value = "api/profiles", produces = "application/json")
    public ResponseEntity createProfile(@RequestBody Profile profile) {

        Profile newProfile = this.profileService.createProfile(profile);
        URI createdUri = linkTo(methodOn(ProfileController.class).createProfile(profile)).slash(newProfile.getId()).toUri();

        return ResponseEntity.created(createdUri).body(profile);
    }

    @GetMapping(value = "api/profiles", produces = "application/json")
    public ResponseEntity getAllProfiles() {

        List<Profile> allProfiles = profileService.getAllProfiles();

        return ResponseEntity.ok().body(allProfiles);
    }

    @GetMapping(value = "api/profiles/{id}", produces = "application/json")
    public ResponseEntity getProfileById(@PathVariable Integer id) {

        Optional<Profile> profile = profileService.findProfileById(id);
        if (!profile.isPresent()) {
            return ResponseEntity.badRequest().body("invalid ID");
        }

        return ResponseEntity.ok().body(profile);
    }

    @DeleteMapping(value = "api/profiles/{id}", produces = "application/json")
    public ResponseEntity deleteProfile(@PathVariable Integer id) {

        Optional<Profile> profile = profileService.findProfileById(id);
        if (!profile.isPresent()) {
            return ResponseEntity.badRequest().body("invalid ID");
        }

        profileService.deleteProfileById(id);
        return ResponseEntity.ok().body(id);
    }

}
