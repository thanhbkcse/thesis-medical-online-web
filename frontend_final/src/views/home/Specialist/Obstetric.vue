<template>
  <div class="content" style="background-color: white">
    <!-- header -->
    <v-card
      width="100%"
      class="d-flex flex-column flex-lg-row justify-space-around align-center"
      min-height="500px"
      tile
    >
      <v-img src="@/assets/img/home/SanPhuKhoaBackground.png">
        <v-card-title class="white--text mt-8">
          <div class="d-flex justify-sm-center">
            <div class="d-flex flex-column pa-16">
              <div elevation="0" class="mb-6">
                <p
                  class="font-weight-bold ma-0 d-flex justify-lg-start justify-center"
                  :style="{ fontSize: section1_title + 'px' }"
                >
                  Khoa Sản Phụ Khoa
                </p>
              </div>
              <div class="d-flex flex-column">
                <div
                  min-height="96px"
                  color="#EEF2F6"
                  elevation="0"
                  style="max-width:640px; min-height:96px"
                  class="d-flex flex-column"
                >
                  <p class="text-center text-lg-start">
                    Người tiêu dùng thông thái, hãy khám bệnh thông minh. Bạn là
                    người tiêu dùng thông thái, bạn yêu gia đình hơn chính bản
                    thân mình. Hãy cùng Blouse Care chăm sóc sức khỏe cho bản
                    thân và gia đình để mỗi ngày bên nhau là một ngày ý nghĩa.
                  </p>
                </div>
              </div>

              <!-- fast book appointment -->
              <div class="d-flex justify-center justify-lg-start">
                <v-dialog v-model="fast_book_dialog" width="640px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      height="48px"
                      width="155px"
                      color="#537DA5"
                      class="white--text text-body-1"
                      style="text-transform: none"
                      v-on="on"
                      v-bind="attrs"
                      >Đặt khám ngay</v-btn
                    >
                  </template>
                  <v-card class="d-flex flex-column">
                    <v-card
                      class="d-flex justify-end mt-10"
                      elevation="0"
                      width="586px"
                    >
                      <v-icon color="#101828" @click="fast_book_dialog = false"
                        >mdi-close</v-icon
                      >
                    </v-card>
                    <v-card
                      class="d-flex flex-column"
                      elevation="0"
                      width="100%"
                    >
                      <div class="d-flex justify-center">
                        <v-img
                          height="107px"
                          width="177px"
                          contain
                          src="@/assets/img/home/fast_book.svg"
                        ></v-img>
                      </div>
                    </v-card>
                    <p
                      class="d-flex justify-center font-weight-bold"
                      style="font-size: 32px"
                    >
                      Đặt khám nhanh
                    </p>
                    <p class="d-flex justify-center">
                      Vui lòng để lại thông tin, chúng tôi sẽ liên hệ lại với
                      bạn để tư vấn.
                    </p>
                    <!-- input -->
                    <v-card
                      class="d-flex justify-center"
                      height="328px"
                      elevation="0"
                    >
                      <v-card width="592px" elevation="0">
                        <div class="d-flex flex-column">
                          <div class="d-flex flex-row">
                            <div class="d-flex flex-column">
                              <label for="" class="font-weight-bold text-body-2"
                                >Họ và tên</label
                              >
                              <v-card
                                width="288px"
                                height="44px"
                                class="d-flex mb-5"
                                elevation="0"
                                outlined
                              >
                                <v-text-field
                                  placeholder="VD: Nguyen Xuan H"
                                  solo
                                  flat
                                  dense
                                  class="text-body-2"
                                ></v-text-field
                              ></v-card>
                            </div>
                            <div class="d-flex flex-column ml-4">
                              <label for="" class="font-weight-bold text-body-2"
                                >Số điện thoại</label
                              >
                              <v-card
                                width="288px"
                                height="44px"
                                class="d-flex mb-5"
                                elevation="0"
                                outlined
                              >
                                <v-text-field
                                  placeholder="VD: 012345689"
                                  solo
                                  flat
                                  dense
                                  class="text-body-2"
                                ></v-text-field
                              ></v-card>
                            </div>
                          </div>
                          <div class="d-flex flex-column">
                            <label for="" class="font-weight-bold text-body-2"
                              >Nhu cầu khám</label
                            >
                            <v-card
                              width="592px"
                              height="128px"
                              class="d-flex mb-5"
                              elevation="0"
                              outlined
                            >
                              <v-textarea
                                solo
                                placeholder="Vui lòng nhập mô tả về nhu cầu khám"
                                class="text-body-2"
                                dense
                                flat
                                height="120px"
                              ></v-textarea
                            ></v-card>
                          </div>
                          <div class="d-flex flex-row">
                            <v-card class="d-flex align-center" elevation="0">
                              <v-img
                                sizes="24px"
                                src="@/assets/img/home/phone_icon.svg"
                                contain
                              ></v-img>
                              <p
                                class="ma-0 ml-3 font-weight-medium text-body-2"
                              >
                                Liên hệ Hotline:
                              </p>
                              <p
                                class="ma-0 ml-2 font-weight-medium text-body-2"
                                style="color: #537da5"
                              >
                                190017473
                              </p>
                            </v-card>

                            <v-card class="ml-auto"
                              ><v-btn
                                class="d-flex align-center btn"
                                color="#537DA5"
                                ><v-img
                                  src="@/assets/img/home/send_mail_icon.svg"
                                  sizes="20px"
                                  class="mr-3"
                                ></v-img>
                                <p class="ma-0 white--text">
                                  Gửi yêu cầu
                                </p></v-btn
                              ></v-card
                            >
                          </div>
                        </div>
                      </v-card>
                    </v-card>
                  </v-card>
                </v-dialog>
              </div>
            </div>
          </div>
        </v-card-title>
      </v-img>
    </v-card>

    <!-- section1 -->
    <v-card
      width="100%"
      class="d-flex flex-column flex-lg-row justify-space-around align-center"
      max-height="682px"
      tile
      elevation="0"
    >
      <v-img src="@/assets/img/home/SanPhuKhoaBackground2.png" contain>
        <v-card-title class="pa-12">
          <div class="d-flex flex-row pa-16">
            <v-img src="@/assets/img/home/SanPhuKhoaBackground2_1.png" contain>
            </v-img>

            <div class="d-flex flex-column pa-16 ml-16">
              <div elevation="0" class="mb-6">
                <p
                  class="font-weight-bold ma-0 d-flex justify-lg-start justify-center"
                  :style="{ fontSize: section1_title + 'px' }"
                >
                  Giới thiệu chung
                </p>
              </div>
              <div class="d-flex flex-column">
                <div
                  min-height="96px"
                  color="#EEF2F6"
                  elevation="0"
                  style="max-width:640px; min-height:96px"
                  class="d-flex flex-column"
                >
                  <p class="font-weight-regular">
                    Chuyên khoa Sản phụ khoa của Phòng khám là đơn vị thăm khám,
                    tầm soát và điều trị các bệnh lý phụ khoa toàn diện và
                    chuyên sâu, giúp giải tỏa nỗi lo lắng cho phụ nữ về các bệnh
                    phụ khoa, ngăn chặn những hậu quả ảnh hưởng đến sức khỏe
                    sinh sản và sức khỏe giới tính cho phụ nữ. Đối với Sản khoa,
                    phòng khám đầu tư nhiều trang thiết bị hiện đại cùng đội ngũ
                    bác sĩ tận tâm, dày dặn kinh nghiệm giúp kiểm tra và theo
                    dõi tình trạng sức của sản phụ và thai nhi từ những ngày đầu
                    cho tới cuối thai kì, hỗ trợ và đem lại sự an tâm cho mẹ và
                    bé “vượt cạn” an toàn.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </v-card-title>
      </v-img>
    </v-card>

    <!-- section2 -->
    <v-card
      width="100%"
      class="d-flex flex-column  justify-space-around pa-16"
      tile
      elevation="0"
    >
      <!-- header-->
      <v-card width="100%" elevation="0">
        <p
          class="d-flex justify-center font-weight-bold"
          :style="{ fontSize: section1_title + 'px' }"
        >
          Các dịch vụ của Chuyên khoa
        </p>
        <p
          class="d-flex justify-center font-weight-bold"
          :style="{ fontSize: section1_title + 'px' }"
        >
          Sản Phụ khoa
        </p>
      </v-card>
      <!-- content  1-->
      <v-card width="100%" class="mt-8" elevation="0">
        <div class="d-flex flex-row justify-space-around">
          <v-card
            width="516"
            height="400"
            class="d-flex flex-column align-center pa-5"
            elevation="0"
          >
            <div class="d-flex flex-column">
              <v-img
                src="@/assets/img/home/SanPhuKhoaIcon1.svg"
                contain
                height="56"
                width="56"
              >
              </v-img>
              <p class="font-weight-bold">Sản Khoa</p>
            </div>
            <v-spacer></v-spacer>
            <div class="d-flex flex-row align-self-start">
              <v-img
                src="@/assets/img/home/SanPhuKhoaIcon3.svg"
                contain
                height="24"
                width="24"
              >
              </v-img>
              <p class="ml-3">
                Chẩn đoán và điều trị các trường hợp thai bệnh lí ở 3 tháng đầu
                thai kì như: dọa sảy thai, nhiễm độc thai nghén, thai lưu, thai
                ngoài tử cung, thai trứng,...
              </p>
            </div>
            <div class="d-flex flex-row align-self-start">
              <v-img
                src="@/assets/img/home/SanPhuKhoaIcon3.svg"
                contain
                height="24"
                width="24"
              >
              </v-img>
              <p class="ml-3">
                Tư vấn tiền sản và thực hiện các kỹ thuật khảo sát để phát hiện
                các bất thường trong gai đoạn sớm của thai kì, đặc biệt là các
                bệnh lý di truyền.
              </p>
            </div>
            <div class="d-flex flex-row align-self-start">
              <v-img
                src="@/assets/img/home/SanPhuKhoaIcon3.svg"
                contain
                height="24"
                width="24"
              >
              </v-img>
              <p class="ml-3 mb-0">
                Theo dõi sức khỏe mẹ, sức khỏe và sự phát triển của bé trong
                suốt thai kỳ.
              </p>
            </div>
          </v-card>
          <v-card width="516" elevation="0">
            <v-img src="@/assets/img/home/SanPhuKhoaImg1.png" contain> </v-img
          ></v-card>
        </div>
      </v-card>
      <!-- content  2-->
      <v-card width="100%" class="mt-8" elevation="0">
        <div class="d-flex flex-row justify-space-around">
          <v-card width="516" elevation="0" class="d-flex align-end">
            <v-img src="@/assets/img/home/SanPhuKhoaImg2.png" contain> </v-img
          ></v-card>
          <v-card
            width="516"
            height="400"
            class="d-flex flex-column align-center pa-5"
            elevation="0"
          >
            <div class="d-flex flex-column">
              <v-img
                src="@/assets/img/home/SanPhuKhoaIcon2.svg"
                contain
                height="56"
                width="56"
              >
              </v-img>
              <p class="font-weight-bold">Sản Khoa</p>
            </div>
            <v-spacer></v-spacer>
            <div class="d-flex flex-row align-self-start">
              <v-img
                src="@/assets/img/home/SanPhuKhoaIcon3.svg"
                contain
                height="24"
                width="24"
              >
              </v-img>
              <p class="ml-3">
                Viêm nhiễm phụ khoa thường gặp (viêm âm hộ - âm đạo, viêm tử
                cung phần phụ,...)
              </p>
            </div>
            <div class="d-flex flex-row align-self-start">
              <v-img
                src="@/assets/img/home/SanPhuKhoaIcon3.svg"
                contain
                height="24"
                width="24"
              >
              </v-img>
              <p class="ml-3">
                Chẩn đoán và điều trị các khối u phụ khoa lành tính ( u xơ tử
                cung, u nang buồng trứng, lạc nội mạc tử cung,...)
              </p>
            </div>
            <div class="d-flex flex-row align-self-start">
              <v-img
                src="@/assets/img/home/SanPhuKhoaIcon3.svg"
                contain
                height="24"
                width="24"
              >
              </v-img>
              <p class="ml-3 mb-0">
                Tầm soát, chẩn đoán và điều trị các loại ung thư phụ khoa (ung
                thư cổ tử cung, ung thư nội mạc tử cung, ung thư buồng
                trứng,...)
              </p>
            </div>
          </v-card>
        </div>
      </v-card>
    </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fast_book_dialog: false
    };
  },
  computed: {
    columns() {
      if (this.$vuetify.breakpoint.xl) {
        return 6;
      }

      if (this.$vuetify.breakpoint.lg) {
        return 6;
      }

      if (this.$vuetify.breakpoint.md) {
        return 4;
      }
      if (this.$vuetify.breakpoint.sm) {
        return 2;
      }

      return 1;
    },
    section1_title() {
      if (this.$vuetify.breakpoint.mdAndDown) {
        return 24;
      }
      return 40;
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
.menu-content {
  margin-top: 20px;
}
</style>
