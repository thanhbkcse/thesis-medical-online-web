package com.thesis.medicalapp.models;

public enum SpecialtyType {
    CHUAN_DOAN_HINH_ANH("Chuẩn Đoán Hình Ảnh"),
    CO_XUONG_KHOP("Cơ - Xương - Khớp"),
    NHI_KHOA("Nhi Khoa"),
    SAN_PHU_KHOA("Sản - Phụ Khoa"),
    TAI_MUI_HONG("Tai - Mũi - Họng"),
    TIM_MACH("Tim Mạch"),
    HO_HAP("Hô Hấp và Miễn Dịch Dị Ứng Lâm Sàng"),
    NOI_SOI("Nội Soi"),
    TIEU_HOA_GAN_MAT("Tiêu Hóa Gan Mật"),
    XET_NGHIEM("Xét Nghiệm"),
    THAN_KINH("Thần Kinh"),
    TAM_THAN_KINH("Tâm Thần Kinh"),
    NGOAI_THAN_KINH("Ngoại Thần Kinh"),
    KHOA_NGOAI("Khoa Ngoại"),
    TU_VAN_TAM_LY("Tư Vấn Tâm Lý"),
    PHOI("Phổi"),
    TONG_QUAT("Tổng Quát"),
    PHUC_HOI_CHUC_NANG("Phục Hồi Chức Năng"),
    XUONG_KHOP_CHINH_HINH("Xương Khớp Chỉnh Hình"),
    NOI_THAN("Nội Thận"),
    HUYET_HOC("Huyết Học"),
    SUY_TIM("Suy Tim"),
    UNG_BUOU("Ung Bướu"),
    NOI_TIET("Nội Tiết"),
    TIEM_NGUA("Tiêm Ngừa"),
    MAT("Mắt"),
    THAM_MY_DA("Thẩm Mỹ Da"),
    Y_HOC_HAT_NHAN("Y Học Hạt Nhân"),
    HAU_MON_HOC("Hậu Môn Học"),
    HOA_TRI("Hóa Trị"),
    TAO_HINH_THAM_MY("Tạo Hình Thẩm Mỹ"),
    VU("Vú"),
    DA_LIEU("Da Liễu"),
    Y_HOC_GIA_DINH("Y Học Gia Đình"),
    TIET_NIEU("Tiết Niệu"),
    LAO_KHOA("Lão Khoa"),
    CHUYEN_GIA_THAN_KINH("Chuyên Gia Thần Kinh"),
    KHAM_VA_TU_VAN_DINH_DUONG("Khám Và Tư Vấn Dinh Dưỡng"),
    PARKINSON_VA_ROI_LOAN_VAN_DONG("PARKINSON Và Rối Loạn Vận Động"),
    LONG_NGUC_MACH_MAU("Lồng Ngực - Mạch Máu"),
    DAU_MAN_TINH("Đau Mạn Tính")
    ;
    private final String name;

    private SpecialtyType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
