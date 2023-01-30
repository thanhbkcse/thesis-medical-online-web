<template>
  <div class="content" style="background-color: white">
    <div class="contentpage">
      <br />

      <p class="text-center display-1 font-weight-bold blue--text first-title">
        KHOA NHI
      </p>

      <br />

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        I. Giới thiệu chung
      </p>

      <p class="text-justify body-1">
        Khoa Nhi thuộc Phòng khám được trang thiết bị y khoa hiện đại cùng đội
        ngũ Bác sĩ giàu kinh nghiệm, có ý thức cao về y đức nghê nghiệp, luôn
        tận tâm, không ngừng học hỏi nâng cao trình độ chuyên môn và luôn đặt
        lợi ích của bệnh nhi lên hàng đầu trong mọi điều trị.
      </p>

      <p class="text-justify body-1">
        - Chẩn đoán và điều trị các trường hợp thai bệnh lý ở 3 tháng đầu thai
        kỳ như: dọa sảy thai, nhiễm độc thai nghén, thai lưu, thai ngoài tử
        cung, thai trứng…
      </p>

      <p class="text-justify body-1">
        Các dịch vụ được thực hiện tại Khoa Nhi của Phòng khám :
      </p>

      <p class="text-justify body-1">
        - Khám và chữa bệnh cho trẻ sơ sinh đến 16 tuổi: Các bệnh về hô hấp, tim
        mạch, tiêu hóa, bệnh truyền nhiễm và các bệnh của trẻ sơ sinh khác…
      </p>

      <p class="text-justify body-1">
        - Khám định kỳ và theo dõi phát triển thể chất, tâm thần vận động;
      </p>

      <p class="text-justify body-1">
        - Tư vấn dinh dưỡng và cách chăm sóc trẻ;
      </p>

      <p class="text-justify body-1">
        - Dịch vụ tiêm ngừa;
      </p>

      <p class="text-justify body-1">
        - Khám sức khoẻ tổng quát cho trẻ và một số dịch vụ chuyên sâu khác…
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
