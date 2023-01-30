package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.ThesisMedicalAppApplication;
import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Address;
import com.thesis.medicalapp.models.Global;
import com.thesis.medicalapp.models.Profile;
import com.thesis.medicalapp.models.User;
import com.thesis.medicalapp.payload.ProfileUpdate;
import com.thesis.medicalapp.payload.response.ProfileSearch;
import com.thesis.medicalapp.pojo.ProfileDTO;
import com.thesis.medicalapp.repository.AddressRepository;
import com.thesis.medicalapp.repository.ProfileRepository;
import com.thesis.medicalapp.repository.UserRepository;
import com.thesis.medicalapp.services.ProfileService;
import com.thesis.medicalapp.utils.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public ProfileDTO saveProfile(ProfileDTO profileDTO, String username) {
        log.info("Saving new profile of {} to the database", profileDTO.getFirstName());
        Profile profile = new Profile();
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        Address address = addressRepository.save(profileDTO.getAddress());
        if (address == null) throw new ApiRequestException("Can not save address!");
        profile.setAddress(address);
        profile.setPhone(profileDTO.getPhone());
        profile.setEmail(profileDTO.getEmail());
        profile.setJob(profileDTO.getJob());
        profile.setDob(profileDTO.getDob());
        profile.setFolk(profileDTO.getFolk());
        profile.setGender(profileDTO.getGender());
        profile.setIdentityCard(profileDTO.getIdentityCard());
        profile.setHealthInsurance(profileDTO.getHealthInsurance());
        profile.setGuardian(profileDTO.getGuardian());
        profile.setGuardianPhone(profileDTO.getGuardianPhone());
        profile.setGuardianIdentityCard(profileDTO.getGuardianIdentityCard());
        profile.setRelationship(profileDTO.getRelationship());
        profile.setRelationshipWithPatient(profileDTO.getRelationshipWithPatient());
        profile.setImageUrl(profileDTO.getImageUrl());
        profile.setIsContactProfile(Boolean.FALSE);
        Optional<User> userOp = userRepository.findByUsername(username);
        if (!userOp.isPresent()) throw new ApiRequestException("Không tìm thấy user!");
        User user = userOp.get();
        profile.setUser(user);
        String profileNumberRequest = profileDTO.getProfileNumber();
        if (profileNumberRequest == null) {
            SequenceGenerator sequenceGenerator = new SequenceGenerator();
            Long profileNumber = sequenceGenerator.nextId();
            profileNumberRequest = String.valueOf(profileNumber);
        }
        profile.setProfileNumber(profileNumberRequest);
        Profile profileEntity = profileRepository.save(profile);
        ProfileDTO profileDTOResponse = ProfileDTO.from(profileEntity);
        return profileDTOResponse;
    };
    @Override
    public List<ProfileDTO> getProfilesByUser(){
        Optional<User> userOp = userRepository.findByUsername(Global.user.getUsername());
        User user = userOp.get();
        List<Profile> profiles = profileRepository.findAllByUser(user)
                .stream()
                .collect(Collectors.toList());

        List<ProfileDTO> profileDTOS = profiles.stream().map(p -> {
            ProfileDTO profileDTO = ProfileDTO.from(p);
            return profileDTO;
        }).collect(Collectors.toList());
        return profileDTOS;
    };
    @Override
    public List<ProfileDTO> getProfiles(){
        List<Profile> profiles = profileRepository.findAll()
                .stream()
                .collect(Collectors.toList());

        List<ProfileDTO> profileDTOS = profiles.stream().map(p -> {
            ProfileDTO profileDTO = ProfileDTO.from(p);
            return profileDTO;
        }).collect(Collectors.toList());
        return profileDTOS;
    };
    @Override
    public void updateProfile(ProfileUpdate profileDTO) {
        Profile profile = profileRepository.findById(profileDTO.getId());
        if (profile == null) throw new ApiRequestException("Can not found profile!");
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setJob(profileDTO.getJob());
        profile.setPhone(profileDTO.getPhone());
        profile.setEmail(profileDTO.getEmail());
        profile.setIdentityCard(profileDTO.getIdentityCard());
        profile.setHealthInsurance(profileDTO.getHealthInsurance());
        profile.setGender(profileDTO.getGender());
        Address address = new Address();
        if (profileDTO.getAddress().getId() != null && profileDTO.getAddress().getId() != "") {
            address = addressRepository.findById(profileDTO.getAddress().getId()).get();
        }
        address.setCountry(profileDTO.getAddress().getCountry());
        address.setProvince(profileDTO.getAddress().getProvince());
        address.setDistrict(profileDTO.getAddress().getDistrict());
        address.setWard(profileDTO.getAddress().getWard());
        address.setAddress(profileDTO.getAddress().getAddress());
        Address address1 = addressRepository.save(address);
        System.out.println(address1);
        if (profile.getAddress() == null) {
            System.out.println("dddddd");
            profile.setAddress(address1);
        }
        System.out.println(profile.getAddress());
        profile.setDob(profileDTO.getDob());
        profile.setFolk(profileDTO.getFolk());
        profile.setGuardian(profileDTO.getGuardian());
        profile.setRelationship(profileDTO.getRelationship());
        profile.setRelationshipWithPatient(profileDTO.getRelationshipWithPatient());
        profile.setImageUrl(profileDTO.getImageUrl());
        profileRepository.save(profile);
    }
    @Override
    public void removeProfile(String id) {
        Profile profile = profileRepository.findById(id);
        profileRepository.delete(profile);
    }
    @Override
    public Profile findProfileById(String id) {
        return profileRepository.findById(id);
    }
    @Override
    public Boolean existsByIdentityCard(String ic) {
        return profileRepository.existsByIdentityCard(ic);
    }
    @Override
    public ProfileSearch searchProfile(String phone) {
        System.out.println(phone);
        Optional<Profile> profile = profileRepository.findProfileByPhoneAndRelationship(phone, "Chủ tài khoản");
        if (!profile.isPresent()) {
            System.out.println("no result");
            return null;
        }
        ProfileSearch profileSearch = new ProfileSearch();
        profileSearch.setId(profile.get().getId());
        profileSearch.setPhone(profile.get().getPhone());
        profileSearch.setFirstName(profile.get().getFirstName());
        profileSearch.setLastName(profile.get().getLastName());
        return profileSearch;
    }
}
