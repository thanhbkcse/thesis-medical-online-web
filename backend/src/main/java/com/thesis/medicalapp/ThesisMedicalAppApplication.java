package com.thesis.medicalapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.medicalapp.config.ESConfig;
import com.thesis.medicalapp.controllers.DoctorController;
import com.thesis.medicalapp.controllers.ServiceController;
import com.thesis.medicalapp.indices.Indices;
import com.thesis.medicalapp.models.*;
import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.payload.ServiceRequest;
import com.thesis.medicalapp.pojo.HospitalDTO;
import com.thesis.medicalapp.repository.*;
import com.thesis.medicalapp.services.*;
import com.thesis.medicalapp.utils.SequenceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.event.BeforeConvertCallback;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@EnableAsync
@SpringBootApplication
@RestController
@EnableJpaAuditing
@EnableScheduling
@EnableJpaRepositories("com.thesis.medicalapp.repository")
@Slf4j
public class ThesisMedicalAppApplication {
    private final ModelMapper modelMapper = new ModelMapper();
    public static void main(String[] args) {
        SpringApplication.run(ThesisMedicalAppApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    CommandLineRunner run(
            UserService userService,
            ProfileRepository profileRepository,
            ScheduleRepository scheduleRepository,
            DoctorRepository doctorRepository,
            RoomRepository roomRepository,
            HospitalRepository hospitalRepository,
            AddressRepository addressRepository,
            HospitalHourRepository hospitalHourRepository,
            ESConfig esConfig,
            HospitalESService hospitalESService,
            DoctorESService doctorESService,
            ServiceRepository serviceRepository,
            ServiceESService serviceESService,
            com.thesis.medicalapp.services.HospitalService hospitalService,
            DoctorController doctorController,
            ServiceController serviceController,
            HospitalESRepository hospitalESRepository,
            ServiceESRepository serviceESRepository,
            DoctorESRepository doctorESRepository
    ) {
        return args -> {
            // Remove all data in ES
            esConfig.elasticsearchTemplate().indexOps(IndexCoordinates.of(Indices.HOSPITAL_SERVICE_INDEX)).delete();
            esConfig.elasticsearchTemplate().indexOps(IndexCoordinates.of(Indices.HOSPITAL_INDEX)).delete();
            esConfig.elasticsearchTemplate().indexOps(IndexCoordinates.of(Indices.DOCTOR_INDEX)).delete();

            esConfig.elasticsearchTemplate().indexOps(HospitalES.class).create();
            esConfig.elasticsearchTemplate().indexOps(DoctorES.class).create();
            esConfig.elasticsearchTemplate().indexOps(HospitalServiceES.class).create();
            // Init data
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_DOCTOR"));
            userService.saveRole(new Role(null, "ROLE_HOSPITAL_ADMIN"));
            Address address = new Address(null, "Việt Nam", "Hồ Chí Minh", "5", "11", "215 Hồng Bàng");
            Address address1 = addressRepository.save(address);
            String hospitalTime = "7:00 - 17:00";
            String hospitalBreak = "12:00 - 13:00";
            HospitalHour hospitalHour = new HospitalHour(null, hospitalTime, hospitalBreak, hospitalTime, hospitalBreak, hospitalTime, hospitalBreak, hospitalTime, hospitalBreak, hospitalTime, hospitalBreak, hospitalTime, hospitalBreak, hospitalTime, hospitalBreak);
            HospitalHour hospitalHour1 = hospitalHourRepository.save(hospitalHour);
            Hospital hospital = new Hospital();
            hospital.setName("Bệnh Viện Đại Học Y Dược HCM");
            hospital.setAddress(address1);
            hospital.setInfo("Bệnh viện có 3 cơ sở, trụ sở chính tại 215 Hồng Bàng, P.11, Q.5. Từ năm 2013, Bệnh viện đưa vào hoạt động tòa nhà 17 tầng đã mở rộng khả năng đáp ứng số lượng người bệnh đến khám ngoại trú và điều trị nội trú tại BV. Đây là tòa nhà thiết kế theo tiêu chuẩn bệnh viện quốc tế, đem đến môi trường khám chữa bệnh tiện nghi, xanh – sạch – đẹp, an ninh giúp người bệnh và gia đình cảm thấy thoải mái hơn khi vào Bệnh viện. Mỗi năm, Bệnh viện tiếp nhận trung bình hơn 2 triệu lượt người khám ngoại trú (khoảng 7.000 người khám/ngày), điều trị nội trú 55.000 người, phẫu thuật khoảng 30.000 trường hợp.");
            hospital.setFavorite(0.0);
            hospital.setRegistrationNumber(0);
            hospital.setIsActive(Boolean.TRUE);
            hospital.setMapImageUrl("https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/so-do-benh-vien-viet-duc-isofhcare2-(1)_03053edd_8673_4b36_8237_2c214b3f1c28.jpg");
            hospital.setHospitalHour(hospitalHour1);
            hospital.setImageUrl("https://ump.edu.vn/img/image-default.png");
            Hospital hospital1 = hospitalRepository.save(hospital);
            HospitalES hospitalES = modelMapper.map(hospital1, HospitalES.class);
            hospitalESRepository.save(hospitalES);
            // init service
            HospitalService service = new HospitalService();
            service.setSpecialty(SpecialtyType.CHUAN_DOAN_HINH_ANH);
            service.setType(ScheduleType.ONLINE);
            service.setHospital(hospital1);
            service.setNumOfServicePerHour(2);
            service.setPrice(100000);
            service.setName("Khám cơ xương khớp");
            service.setImageUrl("https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/20190927_121721_552975_tai-mui-hong-crop-1.max-1800x1800_e35067f6_29c0_47b2_bf9a_0149821990b6.jpg");
            service.setRegistrationNumber(0);
            service.setFavorite(0.0);
            service.setInfo("Dịch vụ Khám Tai - Mũi - Họng theo yêu cầu của Bệnh viện Việt Đức chuyên điều trị các bệnh lý và triệu chứng");
            HospitalService service1 = serviceRepository.save(service);
            HospitalServiceES hospitalServiceES = modelMapper.map(service1, HospitalServiceES.class);
            serviceESRepository.save(hospitalServiceES);
            // init user
            String bio = "Là giảng viên của trường Đại học Y dược Thái Nguyên nhiều năm kinh nghiệm, tận tình, nhiệt huyết. Đi đầu trong lĩnh vực dịch vụ y tế tại nhà trong khu vực.";
            User user = new User(null, "user", "+84326185287", "1234", true, null, new ArrayList<>());
            User user1 = new User(null, "user1", "+84326185285", "1234", true, null, new ArrayList<>());
            User admin = new User(null, "admin", "+84326185283","1234", true, null, new ArrayList<>());
            User admin1 = new User(null, "admin1", "+84326185288","1234", true, null, new ArrayList<>());
            User doctor = new Doctor(null, "doctor", "+84326185284","1234", true, "https://znews-photo.zingcdn.me/w660/Uploaded/ngogtn/2022_03_30/yoo_yeon_seok_3_7704_1629893125.jpeg", new ArrayList<>(), "Đinh Ngọc Sơn", Gender.MALE, new Date(), "doctor@gmail.com", SpecialtyType.CHUAN_DOAN_HINH_ANH, "PGS.TS.BS", bio, 100000, hospital, 0.0);
            User doctor1 = new Doctor(null, "doctor1", "+84326185286","1234", true, "https://myduchospital.vn/vnt_upload/doctors/03_2022/BS_Nguyen_Thanh_Nam.jpg", new ArrayList<>(), "Nguyễn Thành Nam", Gender.MALE, new Date(), "doctor1@gmail.com", SpecialtyType.CHUAN_DOAN_HINH_ANH, "PGS.TS.BS", bio, 100000, hospital, 0.0);
            User userEntity = userService.saveUser(user);
            User userEntity1 = userService.saveUser(user1);
            userService.saveUser(admin);
            userService.saveUser(admin1);

            Date dateFormat = new Date();
            try {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-11");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            List<String> times = new ArrayList<>();
            times.add("9:00 - 10:00");
            times.add("10:00 - 11:00");
            times.add("11:00 - 12:00");
            times.add("13:00 - 14:00");
            times.add("14:00 - 15:00");
            userService.saveUser(doctor);
            userService.saveUser(doctor1);
            Doctor doctorEntity = doctorRepository.findDoctorByUsername("doctor");
            DoctorES doctorES = modelMapper.map(doctorEntity, DoctorES.class);
            doctorES.setSpecialty(doctorEntity.getSpecialty().getName());
            doctorESRepository.save(doctorES);
            Doctor doctorEntity1 = doctorRepository.findDoctorByUsername("doctor1");
            DoctorES doctorES1 = modelMapper.map(doctorEntity1, DoctorES.class);
            doctorES1.setSpecialty(doctorEntity1.getSpecialty().getName());
            doctorESRepository.save(doctorES1);
            Room room = roomRepository.save(new Room(null,"H2", null, hospital));
            scheduleRepository.save(new Schedule(null, ScheduleType.OFFLINE, dateFormat, room, times, 2, doctorEntity));
            scheduleRepository.save(new Schedule(null, ScheduleType.OFFLINE, dateFormat, room, times, 2, doctorEntity1));
            userService.addRoleToUser("user", "ROLE_USER");
            userService.addRoleToUser("user1", "ROLE_USER");
            userService.addRoleToUser("doctor", "ROLE_DOCTOR");
            userService.addRoleToUser("doctor1", "ROLE_DOCTOR");
            userService.addRoleToUser("admin", "ROLE_ADMIN");
            userService.addRoleToUser("admin1", "ROLE_HOSPITAL_ADMIN");
            SequenceGenerator sequenceGenerator = new SequenceGenerator();
            Long profileNumber = sequenceGenerator.nextId();
            Long profileNumber1 = sequenceGenerator.nextId();
            profileRepository.save(new Profile(null, String.valueOf(profileNumber), "Thọ", "Hà Ngọc", address1, "+84326185289", "tho@gmail.com", new Date(), "Developer", "038200008299", "0322889971", "Kinh", Gender.MALE, "Trịnh Thị Thanh","0983839989", "038299988877", "Chủ tài khoản", "Mẹ", null, false, userEntity));
            profileRepository.save(new Profile(null, String.valueOf(profileNumber1), "Thanh", "Nguyễn Duy", address1, "+84326185287", "thanh@gmail.com", new Date(), "Developer", "038200008298", "0322889972", "Kinh", Gender.MALE, "guardian","0983839989", "038299988877", "Chủ tài khoản", "Ba", null, false, userEntity1));
            // init data json
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<HospitalDTO>> typeReference = new TypeReference<>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/hospitals.json");
            try {
                List<HospitalDTO> hospitals = mapper.readValue(inputStream, typeReference);
                for (HospitalDTO hospitalDTO : hospitals) {
                    Hospital hospitalJson = hospitalService.saveHospital(hospitalDTO);
                    hospitalESService.save(hospitalJson);
                }
            } catch (IOException e) {
                System.out.println("Unable to save hospital: " + e.getMessage());
            }

            TypeReference<List<DoctorController.DoctorCreateDTO>> typeReferenceDoctor = new TypeReference<>() {};
            InputStream inputStreamDoctor = TypeReference.class.getResourceAsStream("/json/doctors.json");
            try {
                List<DoctorController.DoctorCreateDTO> doctorCreateDTOS = mapper.readValue(inputStreamDoctor, typeReferenceDoctor);
                for (DoctorController.DoctorCreateDTO doctorCreateDTO : doctorCreateDTOS) {
                    Hospital hospitalDoctor = hospitalRepository.findByName(doctorCreateDTO.getHospitalName());
                    doctorCreateDTO.setHospitalId(hospitalDoctor.getId());
                    doctorController.saveDoctor(doctorCreateDTO);
                }
            } catch (IOException e) {
                System.out.println("Unable to save doctor: " + e.getMessage());
            }

            TypeReference<List<ServiceRequest>> typeReferenceService = new TypeReference<>() {};
            InputStream inputStreamService = TypeReference.class.getResourceAsStream("/json/services.json");
            try {
                List<ServiceRequest> serviceRequests = mapper.readValue(inputStreamService, typeReferenceService);
                for (ServiceRequest serviceRequest : serviceRequests) {
                    Hospital hospitalDoctor = hospitalRepository.findByName(serviceRequest.getHospitalName());
                    serviceRequest.setHospitalId(hospitalDoctor.getId());
                    serviceController.saveHospitalService(serviceRequest);
                }
            } catch (IOException e) {
                System.out.println("Unable to save service: " + e.getMessage());
            }
        };
    }
    @GetMapping
    public String hello() {
        return "It works!";
    }
}
