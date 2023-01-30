package com.thesis.medicalapp.payload;

import com.thesis.medicalapp.models.Address;
import com.thesis.medicalapp.models.Gender;
import com.thesis.medicalapp.models.Profile;
import com.thesis.medicalapp.pojo.AddressDTO;
import com.thesis.medicalapp.pojo.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdate {
    private String id;
    private String profileNumber;
    @NotEmpty
    @NotBlank
    private String firstName;
    @NotEmpty
    @NotBlank
    private String lastName;
    private AddressDTO address;
    @NotEmpty
    @NotBlank
    private String phone;
    @Email(message = "invalid email address")
    private String email;
    @NotNull
    private Date dob;
    private String job;
    @NotEmpty
    @NotBlank
    private String identityCard;
    private String healthInsurance;
    @NotEmpty
    @NotBlank
    private String folk;
    @NotNull
    private Gender gender;
    private String guardian;
    private String guardianPhone;
    private String guardianIdentityCard;
    @NotNull
    private String relationship;
    private String relationshipWithPatient;
    private String imageUrl;
    private Boolean isContactProfile;

    public static ProfileUpdate from(Profile profile) {
        return ProfileUpdate.builder()
                .id(profile.getId())
                .profileNumber(profile.getProfileNumber())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .address(AddressDTO.from(profile.getAddress()))
                .phone(profile.getPhone())
                .email(profile.getEmail())
                .dob(profile.getDob())
                .job(profile.getJob())
                .identityCard(profile.getIdentityCard())
                .healthInsurance(profile.getHealthInsurance())
                .folk(profile.getFolk())
                .gender(profile.getGender())
                .guardian(profile.getGuardian())
                .guardianPhone(profile.getGuardianPhone())
                .guardianIdentityCard(profile.getGuardianIdentityCard())
                .relationship(profile.getRelationship())
                .relationshipWithPatient(profile.getRelationshipWithPatient())
                .imageUrl(profile.getImageUrl())
                .isContactProfile(profile.getIsContactProfile())
                .build();
    }
}
