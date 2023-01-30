package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Global;
import com.thesis.medicalapp.models.Notification;
import com.thesis.medicalapp.models.NotificationType;
import com.thesis.medicalapp.models.Profile;
import com.thesis.medicalapp.payload.ProfileUpdate;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.payload.response.ProfileSearch;
import com.thesis.medicalapp.pojo.ProfileDTO;
import com.thesis.medicalapp.repository.NotificationRepository;
import com.thesis.medicalapp.repository.ProfileRepository;
import com.thesis.medicalapp.services.ProfileService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final NotificationRepository notificationRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/profiles")
    public ResponseEntity<ApiResponse> saveProfile(@RequestBody @Valid ProfileDTO profileDTO) {
        if (profileService.existsByIdentityCard(profileDTO.getIdentityCard()))
            throw new ApiRequestException("Identity card already exists!");
        ProfileDTO profileResponse = profileService.saveProfile(profileDTO, Global.user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(profileResponse)
        );
    }

    @GetMapping("/profiles")
    public ResponseEntity<ApiResponse> getProfilesByUser() {
        List<ProfileDTO> profileDTOS = profileService.getProfilesByUser();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(profileDTOS)
        );
    }

    @GetMapping("/profiles/search")
    public ResponseEntity<ApiResponse> getProfilesByPhone(@RequestParam("phone") String phone) {
        ProfileSearch profileSearch = profileService.searchProfile(phone);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(profileSearch)
        );
    }

    @GetMapping("/profiles/all")
    public ResponseEntity<ApiResponse> getProfiles() {
        List<ProfileDTO> profileDTOS = profileService.getProfiles();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(profileDTOS)
        );
    }

    @Data
    public static class AcceptInviteProfileRequest {
        private String username;
    }

    @PostMapping("/profiles/invite/accept")
    public ResponseEntity<ApiResponse> acceptInviteProfile(@RequestBody AcceptInviteProfileRequest acceptInviteProfileRequest) {
        Profile profile = profileRepository.findProfileByRelationshipAndUser_Username("Chủ tài khoản", Global.user.getUsername());
        ProfileDTO profileDTO = ProfileDTO.from(profile);
        profileDTO.setRelationship(null);
        profileDTO.setIsContactProfile(Boolean.TRUE);
        String username = acceptInviteProfileRequest.getUsername();
        ProfileDTO profileResponse = profileService.saveProfile(profileDTO, username);
        Notification notification = new Notification();
        notification.setTime(new Date());
        notification.setToUser(username);
        notification.setFromUser(Global.user.getUsername());
        notification.setTitle("Liên kết hồ sơ thành công");
        String text = profile.getLastName() + " " + profile.getFirstName()
                + " đã xác nhận lời mời liên kết hồ sơ của bạn.";
        notification.setText(text);
        Notification notificationRes = notificationRepository.save(notification);
        simpMessagingTemplate.convertAndSendToUser(notification.getToUser(),"/queue/notification", notificationRes);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(profileResponse)
        );
    }

    @Data
    public static class InviteProfileRequest {
        private String profileId;
    }

    @PostMapping("/profiles/invite")
    public ResponseEntity<ApiResponse> inviteProfile(@RequestBody InviteProfileRequest inviteProfileRequest) {
        Profile profile = profileService.findProfileById(inviteProfileRequest.getProfileId());
        Profile profileUser = profileRepository.findProfileByRelationshipAndUser_Username("Chủ tài khoản", Global.user.getUsername());
        Notification notification = new Notification();
        notification.setTime(new Date());
        notification.setToUser(profile.getUser().getUsername());
        notification.setFromUser(Global.user.getUsername());
        notification.setType(NotificationType.INVITE);
        notification.setTitle("Lời mời liên kết hồ sơ");
        String text = "Bạn nhận được lời mời liên kết hồ sơ từ " + profileUser.getLastName() + " " + profileUser.getFirstName()
                + ", số điện thoại " + profileUser.getPhone()
                + ". Vui lòng bấm xác nhận để liên kết hồ sơ.";
        notification.setText(text);
        Notification notificationRes = notificationRepository.save(notification);
        simpMessagingTemplate.convertAndSendToUser(notification.getToUser(),"/queue/notification", notificationRes);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(null)
        );
    }

    @PatchMapping("/profiles")
    public ResponseEntity<ApiResponse> updateProfile(@RequestBody ProfileUpdate profileDTO) {
        profileService.updateProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(HttpStatus.OK.value(), "Success")
        );
    }

    @DeleteMapping("/profiles/{id}")
    public ResponseEntity<ApiResponse> removeProfile(@PathVariable String id) {
        profileService.removeProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(HttpStatus.OK.value(), "Success")
        );
    }
}
