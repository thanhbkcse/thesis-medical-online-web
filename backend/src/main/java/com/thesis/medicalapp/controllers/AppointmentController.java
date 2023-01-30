package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.*;
import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.payload.AppointmentRequest;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.payload.response.MessageResponse;
import com.thesis.medicalapp.pojo.AppointmentDTO;
import com.thesis.medicalapp.pojo.ScheduleDTO;
import com.thesis.medicalapp.repository.*;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.*;
import com.thesis.medicalapp.utils.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final DoctorService doctorService;
    private final RoomRepository roomRepository;
    private final MedicalFileRepository medicalFileRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final NotificationRepository notificationRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Async
    public void updateRegistrationNumber(Doctor doctor) {
        doctor.setRegistrationNumber(doctor.getRegistrationNumber() + 1);
        doctorRepository.save(doctor);
        Hospital hospital = hospitalRepository.findById(doctor.getHospital().getId()).get();
        hospital.setRegistrationNumber(hospital.getRegistrationNumber() + 1);
        hospitalRepository.save(hospital);
    }

    @PostMapping("/appointments")
    public ResponseEntity<Object> saveAppointment(@RequestBody @Valid AppointmentRequest appointmentRequest) {
        Appointment appointment = new Appointment();
        Profile profile = profileService.findProfileById(appointmentRequest.getProfileId());
        if (profile == null) throw new ApiRequestException("Không tìm thấy hồ sơ!");
        appointment.setProfile(profile);
        if (appointmentRequest.getDoctorId()  == null && appointmentRequest.getServiceId() == null)
            throw new ApiRequestException("doctor or service must not be blank");
        if (appointmentRequest.getDoctorId() != null) {
            Doctor doctor = doctorService.findDoctorById(appointmentRequest.getDoctorId());
            if (doctor == null) throw new ApiRequestException("Không tìm thấy bác sĩ!");
            appointment.setDoctor(doctor);
            updateRegistrationNumber(doctor);
        } else {
           Optional<HospitalService> service = serviceRepository.findById(appointmentRequest.getServiceId());
           if (!service.isPresent()) throw new ApiRequestException("Không tìm thấy dịch vụ!");
           appointment.setService(service.get());
        }
        Date dateFormat = new Date();
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentRequest.getDate());
        } catch (Exception e) {
            throw new ApiRequestException("Invalid date!");
        }
        appointment.setDate(dateFormat);
        if (appointmentRequest.getCategory().equals(CategoryType.DOCTOR)) {
            Room room = roomRepository.findRoomById(appointmentRequest.getRoomId());
            if (room == null) throw new ApiRequestException("Không tìm thấy phòng!");
            appointment.setRoom(room);
        }
        appointment.setTime(appointmentRequest.getTime());
        appointment.setSymptom(appointmentRequest.getSymptom());
        appointment.setFiles(new ArrayList<>());
        if (null != appointmentRequest.getFiles()) {
            for (MedicalFile file : appointmentRequest.getFiles()) {
                MedicalFile File = medicalFileRepository.save(file);
                appointment.getFiles().add(File);
            }
        }
        appointment.setType(appointmentRequest.getType());
        appointment.setCategory(appointmentRequest.getCategory());
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        Long code = sequenceGenerator.nextId();
        appointment.setCode(code);
        appointment.setFee(appointmentRequest.getFee());
        appointment.setIsPaid(appointmentRequest.getIsPaid());
        appointment.setOrderId(appointmentRequest.getOrderId());
        appointment.setRequestId(appointmentRequest.getRequestId());

        appointment.setPaymentType(appointmentRequest.getPaymentType());
        if (appointment.getPaymentType().equals(PaymentType.DIRECT) && !appointment.getIsPaid())
            appointment.setStatus(Status.PENDING);

        String objectName = "";
        if (appointmentRequest.getCategory().equals(CategoryType.DOCTOR)) {
            objectName = appointment.getDoctor().getName();
        } else {
            objectName = appointment.getService().getName();
        }
        String dataQrCode =
                appointment.getProfile().getLastName() + " " + appointment.getProfile().getFirstName() + "\n"
                        + appointment.getTime() + "\n"
                        + appointmentRequest.getDate()
                        + objectName + "\n"
                        + appointment.getStatus() + "\n"
                        + appointment.getCode() + "\n"
                        + appointment.getOrderId();
        String qrcode = QRCode.createQR(dataQrCode, 300, 300);
        appointment.setQrcode(qrcode);
        AppointmentDTO appointmentDTO = appointmentService.saveAppointment(appointment);
        Notification notification = new Notification();
        notification.setTime(new Date());
        String text = "";
        notification.setObjectId(appointmentDTO.getId());
        notification.setToUser(profile.getUser().getUsername());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateNoti = formatter.format(appointmentDTO.getDate());
        if (appointmentDTO.getStatus() != null) {
            if (appointmentDTO.getStatus().equals(Status.PENDING)) {
                notification.setType(NotificationType.SUCCESS_APPOINTMENT);
                notification.setTitle("Đặt lịch khám thành công");
                if (appointmentDTO.getCategory().equals(CategoryType.DOCTOR)) {
                    text = "Lịch khám theo yêu cầu với "
                            + appointmentDTO.getDoctor().getLevel() + ". " + appointmentDTO.getDoctor().getName()
                            + " ngày " + dateNoti
                            + " đã được đăng ký thành công. Vui lòng bấm xem lịch khám để xem thêm chi tiết.";
                } else {
                    text = "Lịch khám theo dịch vụ "
                            + appointmentDTO.getService().getName()
                            + " ngày " + dateNoti
                            + " đã được đăng ký thành công. Vui lòng bấm xem lịch khám để xem thêm chi tiết.";
                }
                notification.setText(text);
                Notification notificationRes = notificationRepository.save(notification);
                simpMessagingTemplate.convertAndSendToUser(notification.getToUser(),"/queue/notification", notificationRes);
            }
            else if (appointmentDTO.getStatus().equals(Status.CANCEL)) {
                notification.setType(NotificationType.FAIL_APPOINTMENT);
                notification.setTitle("Đặt lịch khám thất bại");
                if (appointmentDTO.getCategory().equals(CategoryType.DOCTOR)) {
                    text = "Lịch khám theo yêu cầu với "
                            + appointmentDTO.getDoctor().getLevel() + ". " + appointmentDTO.getDoctor().getName()
                            + " ngày " + dateNoti
                            + " đã đăng ký thất bại do chưa thanh toán thành công. Vui lòng bấm xem lịch khám để đặt khám lại.";

                } else {
                    text = "Lịch khám theo dịch vụ "
                            + appointmentDTO.getService().getName()
                            + " ngày " + dateNoti
                            + " đã đăng ký thất bại do chưa thanh toán thành công. Vui lòng bấm xem lịch khám để đặt khám lại.";
                }
                notification.setText(text);
                Notification notificationRes = notificationRepository.save(notification);
                simpMessagingTemplate.convertAndSendToUser(notification.getToUser(),"/queue/notification", notificationRes);
            }
        }

        if (profile.getIsContactProfile()) {
            Profile profileMakeAppointment = profileRepository.findProfileByRelationshipAndUser_Username("Chủ tài khoản", profile.getUser().getUsername());
            Profile profileContact = profileRepository.findProfileByPhoneAndRelationship(profile.getPhone(), "Chủ tài khoản").get();
            Notification notificationContact = new Notification();
            notificationContact.setTime(new Date());
            notificationContact.setToUser(profileContact.getUser().getUsername());
            notificationContact.setTitle("Đặt khám hộ");
            String textContact = "Bạn được đặt khám hộ từ hồ sơ liên đã liên kết " + profileMakeAppointment.getLastName() + " " + profileMakeAppointment.getFirstName()
                    + ", số điện thoại " + profileMakeAppointment.getPhone() + ". Vui lòng bấm xem lịch khám để xem thêm chi tiết.";
            notificationContact.setText(textContact);
            notificationContact.setObjectId(appointmentDTO.getId());
            notificationContact.setType(NotificationType.SUCCESS_APPOINTMENT);
            Notification notificationContactRes = notificationRepository.save(notificationContact);
            simpMessagingTemplate.convertAndSendToUser(notificationContact.getToUser(),"/queue/notification", notificationContactRes);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(appointmentDTO)
        );
    }

    @GetMapping("/appointments")
    public ResponseEntity<Object> getAppointmentByProfileId(@RequestParam("profileId") String profileId) {
        List<AppointmentDTO> appointmentDTOS = appointmentService.getAppointmentByProfileId(profileId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(appointmentDTOS)
        );
    }

    @GetMapping("/appointments/user")
    public ResponseEntity<Object> getAppointmentByUser(
            @RequestParam Integer size,
            @RequestParam Integer page
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<AppointmentDTO> pageAppointment = appointmentService.getAppointmentByUser(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(pageAppointment.getContent(), pageAppointment)
        );
    }

    @PatchMapping("/appointments")
    public ResponseEntity<Object> partialUpdateAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        Appointment appointment = appointmentService.findAppointmentById(appointmentRequest.getId());
        if (appointment == null) {
            if (appointmentRequest.getOrderId() != null) {
                appointment = appointmentRepository.findByOrderId(appointmentRequest.getOrderId());
            } else throw new ApiRequestException("Không tìm thấy lịch hẹn!");
        }
        if (appointmentRequest.getProfileId() != null) {
            Profile profile = profileService.findProfileById(appointmentRequest.getProfileId());
            if (profile == null) throw new ApiRequestException("Không tìm thấy hồ sơ khám!");
            appointment.setProfile(profile);
        }
        if (appointmentRequest.getDoctorId() != null) {
            Doctor doctor = doctorService.findDoctorById(appointmentRequest.getDoctorId());
            if (doctor == null) throw new ApiRequestException("Không tìm thấy bác sĩ!");
            appointment.setDoctor(doctor);
        }
        if (appointmentRequest.getRoomId() != null) {
            Room room = roomRepository.findRoomById(appointmentRequest.getRoomId());
            if (room == null) throw new ApiRequestException("Không tìm thấy phòng!");
            appointment.setRoom(room);
        }
        if (appointmentRequest.getDate() != null) {
            Date dateFormat = new Date();
            try {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentRequest.getDate());
            } catch (Exception e) {
                throw new ApiRequestException("Invalid date!");
            }
            appointment.setDate(dateFormat);
        }
        if (appointmentRequest.getTime() != null)  appointment.setTime(appointmentRequest.getTime());
        if (appointmentRequest.getSymptom() != null)  appointment.setSymptom(appointmentRequest.getSymptom());
        if (appointmentRequest.getFee() != null)  appointment.setFee(appointmentRequest.getFee());
        if (appointmentRequest.getStatus() != null)  {
            appointment.setStatus(appointmentRequest.getStatus());
        }
        if (appointmentRequest.getIsPaid() != null)  {
            appointment.setIsPaid(appointmentRequest.getIsPaid());
            Notification notification = new Notification();
            notification.setTime(new Date());
            String text = "";
            notification.setObjectId(appointment.getId());
            notification.setToUser(appointment.getProfile().getUser().getUsername());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateNoti = formatter.format(appointment.getDate());
            if (appointment.getStatus().equals(Status.PROCESS)) {
                notification.setType(NotificationType.SUCCESS_APPOINTMENT);
                notification.setTitle("Đặt lịch khám thành công");
                if (appointment.getCategory().equals(CategoryType.DOCTOR)) {
                    text = "Lịch khám theo yêu cầu với "
                            + appointment.getDoctor().getLevel() + ". " + appointment.getDoctor().getName()
                            + " ngày " + dateNoti
                            + " đã được đăng ký thành công. Vui lòng bấm xem lịch khám để xem thêm chi tiết.";
                } else {
                    text = "Lịch khám theo dịch vụ "
                            + appointment.getService().getName()
                            + " ngày " + dateNoti
                            + " đã được đăng ký thành công. Vui lòng bấm xem lịch khám để xem thêm chi tiết.";
                }
                notification.setText(text);
                Notification notificationRes = notificationRepository.save(notification);
                simpMessagingTemplate.convertAndSendToUser(notification.getToUser(),"/queue/notification", notificationRes);
            }
            else if (appointment.getStatus().equals(Status.CANCEL)) {
                notification.setType(NotificationType.FAIL_APPOINTMENT);
                notification.setTitle("Đặt lịch khám thất bại");
                if (appointment.getCategory().equals(CategoryType.DOCTOR)) {
                    text = "Lịch khám theo yêu cầu với "
                            + appointment.getDoctor().getLevel() + ". " + appointment.getDoctor().getName()
                            + " ngày " + dateNoti
                            + " đã đăng ký thất bại do chưa thanh toán thành công. Vui lòng bấm xem lịch khám để đặt khám lại.";

                } else {
                    text = "Lịch khám theo dịch vụ "
                            + appointment.getService().getName()
                            + " ngày " + dateNoti
                            + " đã đăng ký thất bại do chưa thanh toán thành công. Vui lòng bấm xem lịch khám để đặt khám lại.";
                }
                notification.setText(text);
                Notification notificationRes = notificationRepository.save(notification);
                simpMessagingTemplate.convertAndSendToUser(notification.getToUser(),"/queue/notification", notificationRes);
            }
        }

        appointmentService.updateAppointment(appointment);
        return ResponseEntity.ok(
                new ApiResponse(null)
        );
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<ApiResponse> removeAppointment(@PathVariable String id) {
        int result = appointmentService.removeAppointment(id);
        return ResponseEntity.ok(
                new ApiResponse(null)
        );
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<ApiResponse> getAppointment(@PathVariable String id) {
        Appointment appointment = appointmentService.findAppointmentById(id);
        return ResponseEntity.ok(
                new ApiResponse(AppointmentDTO.from(appointment))
        );
    }

    @GetMapping("/appointments/doctor")
    public ResponseEntity<ApiResponse> getAppointmentsByDateAndDoctor(@RequestParam String date) {
        List<AppointmentDTO> list = appointmentService.getAppointmentsByDateAndDoctor(date);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(list)
        );
    }

    @PostMapping("/doctor/appointments/search")
    public ResponseEntity<Object> getAppointmentsByDateAndDoctorAndStatusIn(
            @RequestBody SearchRequest request
    ) {
        SearchSpecification<StoreMedicine> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        Page<Appointment> appointments = appointmentRepository.findAll(specification, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(appointments.getContent(), appointments)
        );
    }

    @GetMapping("/doctor/appointments/schedule")
    public ResponseEntity<ApiResponse> getAllByDateIsBetweenAndDoctor(@RequestParam("dateStart") String dateStart, @RequestParam("dateEnd") String dateEnd) {
        Date DateStart = new Date();
        try {
            DateStart = new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Date DateEnd = new Date();
        try {
            DateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<?> appointment = appointmentService.getAllByDateIsBetweenAndDoctor(DateStart, DateEnd);
        return ResponseEntity.ok(
                new ApiResponse(appointment)
        );
    }
}
