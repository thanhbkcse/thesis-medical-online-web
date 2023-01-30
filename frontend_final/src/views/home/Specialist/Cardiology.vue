<template>
  <div class="content" style="background-color: white">
    <div class="contentpage">
      <br />

      <p class="text-center display-1 font-weight-bold blue--text first-title">
        KHOA TIM MẠCH
      </p>

      <br />

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        I. Giới thiệu chung
      </p>

      <p class="text-justify body-1">
        Trực tiếp thăm khám và điều trị tại khoa Nội Tim mạch là đội ngũ các Bác
        sĩ, chuyên gia đầu ngành của TP. HCM. Ngoài chuyên môn cao, các bác sĩ
        luôn mang đến cho bệnh nhân cảm giác yên tâm và được tư vấn đầy đủ theo
        từng bệnh lý. Phòng khám 1 được trang bị cơ sở vật chất và các thiết bị
        hiện đại chuyên về tim mạch giúp phát hiện chính xác và điều trị bệnh
        nhanh chóng, bao gồm:
      </p>

      <p class="text-justify body-1">- 02 Phòng đo điện tâm đồ (ECG);</p>
      <p class="text-justify body-1">
        - Phòng chụp cắt lớp vi tính (128 lát cắt);
      </p>
      <p class="text-justify body-1">- Phòng chụp cộng hưởng từ (MRI) tim;</p>
      <p class="text-justify body-1">
        - Phòng siêu âm tim, siêu âm Doppler màu động mạch, tĩnh mạch.
      </p>

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        II. Các dịch vụ thực hiện tại chuyên khoa Tim Mạch
      </p>

      <p class="text-justify body-1">
        - Khám, chẩn đoán và điều trị các bệnh lý tim mạch: Tăng huyết áp, rối
        lọan lipid máu, xơ vữa động mạch, các bệnh lý mạch vành, bệnh tim thiếu
        máu cục bộ, bệnh cơ tim, các bệnh lý van tim, suy tim, nhiễm trùng tim,
        các bệnh lý mạch máu ngoại biên mạn tính,...
      </p>

      <p class="text-justify body-1">
        - Khám và chẩn đoán các triệu chứng do rối loạn nhịp tim gây nên như:
        ngất, thoáng ngất, cơn chóng mặt không tìm thấy nguyên nhân, cơn hồi hộp
        trống ngực, cơn khó thở, đau ngực, mệt không rõ nguyên nhân, tai biến
        mạch não nghi ngờ do cơn rung nhĩ, hay cuồng nhĩ...
      </p>

      <p class="text-justify body-1">
        - Phát hiện các rối loạn tim mạch không triệu chứng ở người bệnh nhồi
        máu cơ tim, suy tim, bệnh tim phì đại...
      </p>

      <p class="text-justify body-1">
        - Kiểm tra nhịp tim, kiểm tra lưu lượng máu đến cơ tim, chẩn đoán cơn
        đau tim, kiểm tra những vấn đề bất thường của tim (ví dụ cơ tim dày hơn
        bình thường).
      </p>
      <br />
      <p class="text-center display-1 font-weight-bold blue--text">
        Các chuyên khoa khác
      </p>
      <CarouselSpecialist></CarouselSpecialist>
      <br />
    </div>
  </div>
</template>

<script>
import CarouselSpecialist from "../Component/carousel-specialist.vue";

export default {
  components: {
    CarouselSpecialist
  }
};
</script>

<style scoped>
.contentpage {
  margin: 0 100px;
  background: white;
}

.first-title {
  font-size: 30px !important;
}

.second-title {
  font-size: 20px !important;
}
</style>
