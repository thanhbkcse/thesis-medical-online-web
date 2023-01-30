<template>
  <div class="content" style="background-color: white">
    <div class="contentpage">
      <br />
      <p class="text-center display-1 font-weight-bold blue--text first-title">
        TIÊU HOÁ GAN MẬT
      </p>

      <br />

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        I. Giới thiệu chung
      </p>

      <p class="text-justify body-1">
        Khoa Tiêu hóa - Gan Mật thuộc Phòng khám với đội ngũ Bác sĩ giàu kinh
        nghiệm của và được trang bị hệ thống nội soi tiêu hóa Olympus hiện đại,
        máy siêu âm, phòng xét nghiệm H.P qua hơi thở…, giúp các bác sĩ có những
        chẩn đoán đúng, phát hiện biến đổi do ung thư ở giai đoạn sớm. Từ đó đưa
        ra liệu pháp điều trị kịp thời, giúp bệnh nhân rút ngắn thời gian, tiết
        kiệm chi phí và có được kết quả điều trị hiệu quả nhất.
      </p>

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        Hoạt động chuyên môn
      </p>

      <p class="text-justify body-1">
        Khoa Nội Tiêu hóa – Gan Mật tiếp nhận tất cả các trường hợp khám ngoại
        trú, chẩn đoán và điều trị gần hết các bệnh liên quan đến tiêu hóa, gan,
        mật, cụ thể là:
      </p>

      <p class="text-justify body-1">
        - Điều trị và theo dõi: viêm gan siêu vi B, C, viêm gan do rượu và các
        nguyên nhân khác, theo các hướng dẫn mới nhất của Hội Gan Mật Hoa Kỳ.
      </p>

      <p class="text-justify body-1">
        - Chẩn đoán và điều trị các bệnh lý ống tiêu hóa từ thực quản, dạ dày,
        ruột non, đại tràng như: bệnh crohn, viêm loét dạ dày – tá tràng có hoặc
        không nhiễm HP, viêm loét đại trực tràng, xuất huyết tiêu hóa, viêm thực
        quản trào ngược, polyp thực quản – dạ dày, polyp đại trực tràng, ung thư
        đường tiêu hóa…
      </p>

      <p class="text-justify body-1">
        - Hướng dẫn bệnh nhân theo dõi và điều trị bệnh lý phổ biến nhất của hệ
        tiêu hóa: hội chứng ruột đại tràng kích thích.
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
