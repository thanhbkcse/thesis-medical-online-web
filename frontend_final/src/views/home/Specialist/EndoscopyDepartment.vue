<template>
  <div class="content" style="background-color: white">
    <div class="contentpage">
      <br />
      <p class="text-center display-1 font-weight-bold blue--text first-title">
        NỘI SOI
      </p>

      <br />

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        I. Giới thiệu chung
      </p>

      <p class="text-justify body-1">
        Đơn vị Nội soi Tiêu hóa tại Phòng Khám được trang bị 2 hệ thống nội soi,
        3 ống soi dạ dày, 2 ống soi đại – trực tràng của hãng Olympus – V.190 có
        khả năng phát hiện sớm tổn thương tiền ung thư.
      </p>
      <p class="text-justify body-1">
        Nội soi chẩn đoán: Đơn vị Nội soi Phòng Khám thực hiện hầu hết các kỹ
        thuật nội soi chẩn đoán bao gồm: nội soi thực quản – dạ dày – tá tràng,
        nội soi đại – trực tràng. Đơn vị cũng triển khai phương pháp nội soi an
        thần nhằm nâng cao chất lượng phục vụ người bệnh.
      </p>

      <p class="text-left title font-weight-bold red--text mb-6 second-title">
        Các phương pháp nội soi điều trị (nội soi can thiệp) thực hiện tại Phòng
        khám :
      </p>

      <p class="text-justify body-1">
        + Điều trị xuất huyết tiêu hóa với các phương pháp chích cầm máu qua nội
        soi, kẹp cầm máu qua nội soi để điều trị loét chảy máu, thắt thun tĩnh
        mạch thực quản.
      </p>
      <p class="text-justify body-1">
        + Cắt polyp ống tiêu hóa qua nội soi để phòng ngừa nguy cơ ung thư
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
