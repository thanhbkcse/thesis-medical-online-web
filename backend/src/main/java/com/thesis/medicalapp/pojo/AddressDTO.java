package com.thesis.medicalapp.pojo;

import com.thesis.medicalapp.models.Address;
import com.thesis.medicalapp.payload.ProfileUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String id;
    private String country;
    private String province;
    private String district;
    private String ward;
    private String address;

    public static AddressDTO from(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .country(address.getCountry())
                .province(address.getProvince())
                .district(address.getDistrict())
                .ward(address.getWard())
                .address(address.getAddress())
                .build();
    }
}
