<template>
  <v-footer absolute inset app class="ma-0 pa-0">
    <v-card
      height="392px"
      color="#314E6A"
      tile
      class="d-flex justify-center"
      width="100%"
    >
      <v-card
        class="d-flex justify-space-around mt-12"
        min-width="1280px"
        max-height="200px"
        color="#314E6A"
        elevation="0"
      >
        <v-card class="d-flex flex-column" color="#314E6A" elevation="0">
          <div class="d-flex flex-row align-center mb-2">
            <v-card width="30" height="30" tile>
              <v-img src="@/assets/img/home/appbar/logo.png"></v-img>
            </v-card>
            <p class="ml-3 white--text font-weight-medium ma-0">Blouse Care</p>
          </div>

          <p class="white--text">Sinh viên: Nguyễn Duy Thanh</p>
          <p class="white--text">Sinh viên: Hà Ngọc THọ</p>
          <p class="white--text">GVHD: TS. Phan Trọng Nhân</p>
        </v-card>
        <v-card class="d-flex flex-column" color="#314E6A" elevation="0">
          <p class="font-weight-regular" style="color: #d0d5dd">
            Điều khoản sử dụng
          </p>
          <p class="font-weight-regular" style="color: #d0d5dd">
            Chính sách bảo mật
          </p>
          <p class=" font-weight-regular" style="color: #d0d5dd">
            Trung tâm trợ giúp
          </p>
          <p class="font-weight-regular" style="color: #d0d5dd">
            Quy chế hoạt động
          </p>
        </v-card>

        <v-card class="d-flex flex-column" color="#314E6A" elevation="0">
          <p class="font-weight-regular" style="color: #d0d5dd">Trang chủ</p>
          <p class="font-weight-regular" style="color: #d0d5dd">Cộng đồng</p>
          <p class="font-weight-regular" style="color: #d0d5dd">Cẩm nang</p>
          <p class="font-weight-regular" style="color: #d0d5dd">
            Quy chế hoạt động
          </p>
        </v-card>
        <v-card class="d-flex flex-column" color="#314E6A" elevation="0">
          <p class="font-weight-regular" style="color: #d0d5dd">Đặt lịch</p>
          <p class="font-weight-regular" style="color: #d0d5dd">Bác sĩ</p>
          <p class="font-weight-regular" style="color: #d0d5dd">Bệnh viện</p>
          <p class="font-weight-regular" style="color: #d0d5dd">Dịch vụ</p>
        </v-card>
      </v-card>
    </v-card>
  </v-footer>
</template>
