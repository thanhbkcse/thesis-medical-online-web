<template>
  <div class="content" style="background-color: white">
    <div class="contentpage">
      <br />
      <p class="text-center display-1 font-weight-bold blue--text first-title">
        CƠ – XƯƠNG – KHỚP
      </p>

      <br />

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        I. Giới thiệu chung
      </p>

      <p class="text-justify body-1">
        Các bệnh Cơ – Xương – Khớp là một nhóm bệnh thường gặp nhất trong mọi
        nhóm bệnh, có nguyên nhân và cơ chế bệnh sinh phức tạp, diễn tiến kéo
        dài liên quan đến nhiều bệnh lý nội ngoại khoa.
      </p>

      <p class="text-justify body-1">
        Các bệnh Cơ – Xương – Khớp rất phổ biến trên thế giới và cũng đang có xu
        hướng gia tăng nhanh ở Việt Nam.
      </p>
      <p class="text-justify body-1">
        Khoa Cơ – Xương – Khớp thuộc Phòng khám với đội ngũ bác sĩ giàu kinh
        nghiệm đã và đang làm việc tại các bệnh viện lớn như (Bệnh viện Đại học
        Y Dược TP.HCM, Chợ Rẫy…). Khoa Cơ – Xương – Khớp của phòng khám quản lý,
        theo dõi và điều trị hiệu quả các bệnh lý về xương khớp, giúp người bệnh
        giảm bớt đau đớn và nhanh chóng hòa nhịp với cuộc sống hàng ngày.
      </p>

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        II. Hoạt động chuyên môn
      </p>

      <p class="text-justify body-1">
        Khoa Cơ – Xương – Khớp thuộc Phòng khám hiện khám và điều trị các bệnh
        lý gồm:
      </p>

      <p class="text-justify body-1">
        - Các bệnh khớp thoái hóa: Thoái hóa khớp gối, thoái hóa cột sống, thoái
        hóa khớp háng, thoái hóa đa khớp, hoại tử chỏm xương đùi…
      </p>

      <p class="text-justify body-1">
        - Bệnh lý phần mềm và rối loạn ngoài khớp: Viêm gân cơ chóp xoay, viên
        gân gấp ngón tay (ngón tay cò súng), viêm mỏm trâm quay, viêm lồi cầu
        cánh tay, viêm cân gan chân, hội chứng ống cổ tay, thoát vị đĩa đệm…
      </p>

      <p class="text-justify body-1">
        - Các bệnh khớp tự miễn hoặc bệnh tổ chức liên kết: Viêm khớp dạng thấp,
        lupus, viêm cột sống dính khớp, xơ cứng bì, viêm đa cơ, bệnh still, viêm
        khớp vảy nến…
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
