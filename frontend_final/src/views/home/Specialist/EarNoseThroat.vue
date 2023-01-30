<template>
  <div class="content" style="background-color: white">
    <div class="contentpage">
      <br />

      <p class="text-center display-1 font-weight-bold blue--text first-title">
        TAI – MŨI – HỌNG
      </p>

      <br />

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        I. Giới thiệu chung
      </p>

      <p class="text-justify body-1">
        Khoa Tai – Mũi – Họng thuộc Phòng khám là đơn vị thăm khám, chẩn đoán và
        điều trị các bệnh lý về Tai – Mũi – Họng. Bên cạnh đội ngũ bác sĩ có
        trình độ chuyên môn cao, chuyên khoa Tai – Mũi – Họng còn được đầu tư
        các trang thiết bị hiện đại với 02 phòng khám và nội soi, cùng các thiết
        bị đo sức nghe, đo nhĩ lượng với buồng cách âm, giúp các bác sĩ chẩn
        đoán và điều trị hiệu quả và tầm soát các bệnh lý Tai – Mũi – Họng một
        cách toàn diện và chuyên sâu.
      </p>

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        Hoạt động chuyên môn
      </p>

      <p class="text-justify body-1">
        Các dịch vụ được thực hiện tại khoa Tai – Mũi – Họng của Phòng khám :
      </p>
      <p class="text-justify body-1">
        - Thăm dò chức năng, đo thính lực đơn âm, đo nhĩ lượng, nghe kém…
      </p>
      <p class="text-justify body-1">
        - Chẩn đoán và điều trị các bệnh về tai: Đau tai, ù tai, nặng tai, chảy
        mủ tai do viêm tai giữa (VTG), viêm tai xương chũm (VTXC), chóng mặt do
        hội chứng tiền đình…
      </p>
      <p class="text-justify body-1">
        - Chẩn đoán và điều trị các bệnh về mũi: Nghẹt mũi, sổ mũi, nhảy mũi
        (hắt xì) nhức đầu, viêm mũi cấp, viêm mũi xoang dị ứng, viêm mũi xoang
        do nhiễm trùng, viêm xoang do răng, viêm xoang polype mũi, polype mũi.
      </p>
      <p class="text-justify body-1">
        - Chẩn đoán và điều trị các bệnh về họng và thanh quản: Viêm VA ở trẻ
        em, viêm, ngủ ngáy và ngưng thở khi ngủ, viêm thanh quản cấp, viêm thanh
        quản mãn, khàn tiếng, khó thở do viêm thanh quản, hạt dây thanh, polype
        thanh quản.
      </p>
      <p class="text-justify body-1">
        - Tầm soát ung thư hầu, họng, thanh quản.
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
