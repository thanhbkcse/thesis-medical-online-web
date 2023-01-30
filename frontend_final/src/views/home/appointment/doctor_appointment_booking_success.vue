<template>
  <v-card color="#FCFCFD" width="100%" class="pa-12 d-flex justify-center">
    <div class="d-flex flex-column mx-8 align-center">
      <v-avatar size="316" color="#EEF2F6">
        <v-img src="@/assets/img/payment/booking_success.png"></v-img>
      </v-avatar>
      <p class="mt-3 font-weight-bold" style="color: #537da5; font-size: 32px">
        {{ message }}
      </p>
      <div class="d-flex">
        <v-btn
          class="ml-3 btn-not-transform text-body-1 font-weight-medium"
          elevation="0"
          outlined
          color="#667085"
          >Xem lịch khám<v-icon
            class="ml-2"
            small
            color="#537da5"
            @click="go_to_appointment_history"
            >mdi-arrow-right</v-icon
          ></v-btn
        >
      </div>
      <v-card
        width="704"
        min-height="500"
        elevation="0"
        color="#FCFCFD"
        class="align-self-start mt-10"
      >
        <v-card width="700" class="d-flex flex-column">
          <!-- header -->
          <v-card
            class="d-flex flex-column pa-6 font-weight-bold"
            elevation="0"
            tile
            style="font-size: 24px"
            >Thông tin khám bệnh</v-card
          >
          <v-divider style="border-color: #f2f4f7 !important"></v-divider>
          <!-- price -->
          <v-card class="pa-6 d-flex flex-row align-center" tile elevation="0">
            <v-avatar size="28" class="mr-5">
              <v-img src="@/assets/img/home/appbar/doctor.svg"></v-img>
            </v-avatar>
            <p class="ma-0 font-weight-bold">Khám theo yêu cầu</p>
            <v-spacer></v-spacer>
            <p class="ma-0 font-weight-bold" style="color: #537da5">
              100.000VND
            </p>
          </v-card>
          <v-divider style="border-color: #f2f4f7 !important"></v-divider>
          <!-- time -->
          <v-card
            class="pa-6 d-flex flex-row"
            tile
            elevation="0"
            color="#FCFCFD"
          >
            <v-avatar class="mr-5">
              <v-card
                rounded="circle"
                class="pa-3"
                color="#EEF2F6"
                elevation="0"
              >
                <v-icon color="#537DA5"
                  >mdi-clock-time-nine-outline</v-icon
                ></v-card
              >
            </v-avatar>
            <div class="d-flex flex-column">
              <p class="mb-3 font-weight-bold">09:00 - 09:30</p>
              <p class="ma-0 text-body-2" style="color: #667085">24/8/2022</p>
            </div>
          </v-card>
          <v-divider style="border-color: #f2f4f7 !important"></v-divider>
          <!-- doctor -->
          <v-card
            class="pa-6 d-flex flex-row"
            tile
            elevation="0"
            color="#FCFCFD"
          >
            <v-avatar size="48" class="mr-5">
              <v-img src="@/assets/img/user/profile/avatar1.svg"></v-img>
            </v-avatar>
            <div class="d-flex flex-column justify-center">
              <p class="mb-2 font-weight-bold">Nguyễn Xuân Ngũ</p>
              <p class="ma-0 text-body-2" style="color: #667085">
                Phòng khám Vinmec Central Park
              </p>
            </div>
          </v-card>
          <v-divider style="border-color: #f2f4f7 !important"></v-divider>
          <!-- place -->
          <v-card
            class="pa-6 d-flex flex-row"
            tile
            elevation="0"
            color="#FCFCFD"
          >
            <v-avatar size="48" class="mr-5">
              <v-img src="@/assets/img/user/profile/avatar1.svg"></v-img>
            </v-avatar>
            <div class="d-flex flex-column justify-center">
              <p class="mb-2 font-weight-bold">
                Phòng khám Vinmec Central Park
              </p>
              <p class="text-body-2" style="color: #667085">
                08 Đ. Nguyễn Hữu Cảnh, Phường 22, Bình Thạnh, Thành phố Hồ Chí
                Minh
              </p>
              <p class="text-body-2 font-weight-medium" style="color: #537da5">
                Hiện chỉ đường
              </p>
            </div>
          </v-card>
        </v-card>
        <!-- profile after select -->
        <v-card width="700" class="mt-8">
          <!-- header -->
          <v-card
            class="d-flex flex-column pa-6 font-weight-bold"
            elevation="0"
            tile
            style="font-size: 24px"
            >Hồ sơ khám bệnh</v-card
          >
          <v-divider style="border-color: #f2f4f7 !important"></v-divider>
          <v-expansion-panels accordion tile flat>
            <v-expansion-panel
              v-for="profile in profile_list"
              :key="profile.id"
              class="mb-4"
            >
              <v-expansion-panel-header>
                <v-card class="d-flex flex-row align-center" elevation="0">
                  <v-avatar size="40">
                    <img src="@/assets/img/home/appbar/avatar.png" />
                  </v-avatar>
                  <div class="d-flex flex-column ml-3">
                    <p class="ma-0 font-weight-bold text-body-1">
                      {{ profile.lastName }} {{ profile.firstName }}
                    </p>
                    <p
                      class="ma-0 font-weight-normal text-body-2"
                      style="color: #667085"
                    >
                      {{ profile.relationship }}
                    </p>
                  </div>
                </v-card>

                <template v-slot:actions>
                  <v-card
                    elevation="0"
                    rounded="circle"
                    color="#FCFCFD"
                    width="32px"
                    height="32px"
                    class="d-flex justify-center"
                  >
                    <v-icon small> mdi-chevron-down </v-icon>
                  </v-card>
                </template>
              </v-expansion-panel-header>

              <v-expansion-panel-content class="pa-0">
                <!-- user information -->
                <div class="d-flex flex-column">
                  <!-- email -->
                  <v-divider
                    style="border-color: #f2f4f7 !important"
                  ></v-divider>
                  <v-card
                    class="d-flex flec-row justify-space-between align-center"
                    elevation="0"
                    height="72px"
                  >
                    <p class="ma-0 font-weight-medium" style="color: #667085">
                      Email
                    </p>
                    <p class="ma-0 font-weight-medium">
                      {{ profile.email }}
                    </p>
                  </v-card>
                  <!-- phone -->
                  <v-divider
                    style="border-color: #f2f4f7 !important"
                  ></v-divider>
                  <v-card
                    class="d-flex flec-row justify-space-between align-center"
                    elevation="0"
                    height="72px"
                  >
                    <p class="ma-0 font-weight-medium" style="color: #667085">
                      Số điện thoại
                    </p>
                    <p class="ma-0 font-weight-medium">
                      {{ profile.phone }}
                    </p>
                  </v-card>
                  <!-- address -->
                  <v-divider
                    style="border-color: #f2f4f7 !important"
                  ></v-divider>
                  <v-card
                    class="d-flex flec-row justify-space-between align-center"
                    elevation="0"
                    height="72px"
                  >
                    <p class="ma-0 font-weight-medium" style="color: #667085">
                      Địa chỉ
                    </p>
                    <p class="ma-0 font-weight-medium">
                      {{ getAddress(profile) }}
                    </p>
                  </v-card>
                  <!-- gender -->
                  <v-divider
                    style="border-color: #f2f4f7 !important"
                  ></v-divider>
                  <v-card
                    class="d-flex flec-row justify-space-between align-center"
                    elevation="0"
                    height="72px"
                  >
                    <p class="ma-0 font-weight-medium" style="color: #667085">
                      Giới tính
                    </p>
                    <p class="ma-0 font-weight-medium">
                      {{ getGender(profile.gender) }}
                    </p>
                  </v-card>
                  <!-- birthday -->
                  <v-divider
                    style="border-color: #f2f4f7 !important"
                  ></v-divider>
                  <v-card
                    class="d-flex flec-row justify-space-between align-center"
                    elevation="0"
                    height="72px"
                  >
                    <p class="ma-0 font-weight-medium" style="color: #667085">
                      Ngày sinh
                    </p>
                    <p class="ma-0 font-weight-medium">
                      {{ getDate(profile.dob) }}
                    </p>
                  </v-card>
                  <!-- identify -->
                  <v-divider
                    style="border-color: #f2f4f7 !important"
                  ></v-divider>
                  <v-card
                    class="d-flex flec-row justify-space-between align-center"
                    elevation="0"
                    height="72px"
                  >
                    <p class="ma-0 font-weight-medium" style="color: #667085">
                      Số CMND/CCCD
                    </p>
                    <p class="ma-0 font-weight-medium">
                      {{ profile.identityCard }}
                    </p>
                  </v-card>
                  <!-- job -->
                  <v-divider
                    style="border-color: #f2f4f7 !important"
                  ></v-divider>
                  <v-card
                    class="d-flex flec-row justify-space-between align-center"
                    elevation="0"
                    height="72px"
                  >
                    <p class="ma-0 font-weight-medium" style="color: #667085">
                      Nghề nghiệp
                    </p>
                    <p class="ma-0 font-weight-medium">{{ profile.job }}</p>
                  </v-card>
                  <!-- country -->
                  <v-divider
                    style="border-color: #f2f4f7 !important"
                  ></v-divider>
                  <v-card
                    class="d-flex flec-row justify-space-between align-center"
                    elevation="0"
                    height="72px"
                  >
                    <p class="ma-0 font-weight-medium" style="color: #667085">
                      Quốc gia
                    </p>
                    <p class="ma-0 font-weight-medium">
                      {{ profile.address.country }}
                    </p>
                  </v-card>
                  <!-- ethnic -->
                  <v-divider
                    style="border-color: #f2f4f7 !important"
                  ></v-divider>
                  <v-card
                    class="d-flex flec-row justify-space-between align-center"
                    elevation="0"
                    height="72px"
                  >
                    <p class="ma-0 font-weight-medium" style="color: #667085">
                      Dân tộc
                    </p>
                    <p class="ma-0 font-weight-medium">{{ profile.folk }}</p>
                  </v-card>
                </div>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-card>
        <!-- fill patient profile -->
        <v-card width="700" min-height="500" class="mt-8 d-flex flex-column">
          <!-- header -->
          <v-card
            class="d-flex flex-column pa-6 font-weight-bold"
            elevation="0"
            tile
            style="font-size: 24px"
            >Thông tin bệnh nhân</v-card
          >
          <v-divider style="border-color: #f2f4f7 !important"></v-divider>
          <!-- info -->
          <v-card class="pa-6 d-flex flex-column" elevation="0">
            <!-- diagnostic -->
            <p class="font-weight-medium text-body-1">Triệu chứng</p>
            <v-card outlined class="mb-6">
              <v-textarea
                spellcheck="false"
                solo
                height="148"
                flat
                hide-details
                placeholder="Vui lòng mô tả triệu chứng của bạn..."
                readonly
              >
              </v-textarea>
            </v-card>
            <!-- test results type -->
            <div class="d-flex flex-row justify-space-between align-center">
              <p class="font-weight-medium text-body-1 ma-0">
                Kết quả xét nghiệm
              </p>
            </div>

            <!-- list add test result -->
            <div
              class="d-flex flex-wrap justify-space-between align-center mb-5"
              v-for="(image, index) in test_add_list"
              :key="index"
            >
              <p
                class="font-weight-medium text-body-1 ma-0"
                style="color: #667085"
              >
                {{ image.type }}
              </p>
              <v-card min-width="50%" outlined class="pa-3 d-flex flex-row">
                <v-icon class="mr-2">mdi-image-outline</v-icon>
                <v-card min-width="50%" elevation="0">
                  <p class="ma-0" style="">
                    {{ image.file_name }}
                  </p>
                </v-card>
              </v-card>
            </div>
            <!-- Image analysation type -->
            <div
              class="d-flex flex-row justify-space-between align-center mt-5"
            >
              <p class="font-weight-medium text-body-1 ma-0">
                Chuẩn đoán hình ảnh
              </p>
            </div>

            <!-- list add test result -->
            <div
              class="d-flex flex-wrap justify-space-between align-center mb-5"
              v-for="(image, index) in test_add_list"
              :key="index + 3"
            >
              <p
                class="font-weight-medium text-body-1 ma-0"
                style="color: #667085"
              >
                {{ image.type }}
              </p>
              <v-card min-width="50%" outlined class="pa-3 d-flex flex-row">
                <v-icon class="mr-2">mdi-image-outline</v-icon>
                <v-card min-width="50%" elevation="0">
                  <p class="ma-0" style="">
                    {{ image.file_name }}
                  </p>
                </v-card>
              </v-card>
            </div>
          </v-card>
        </v-card>
      </v-card>
    </div>
  </v-card>
</template>

<script>
const url = process.env.VUE_APP_ROOT_API;
export default {
  data() {
    return {
      is_select_profile: true,
      is_payment: false,
      test_type: ["Xét nghiệm máu", "Xét nghiệm nước tiểu", "Xét nghiệm khác"],
      test_select: "",
      image_select: "",
      radioGroup: 1,
      payment_selection: "",
      profile_list: [
        {
          id: "1",
          firstName: "Tho",
          lastName: "Ha Ngoc",
          address: {
            country: "Viet Nam",
            province: "Tinh",
            district: "Huyen",
            ward: "Xa",
            address: "364 Do Bi"
          },
          phone: "phoneNumber",
          email: "email@gmail.com",
          dob: "2012-04-23T18:25:43.511Z",
          job: "job",
          identityCard: "identityCard",
          healthInsurance: "healthInsurance",
          folk: "folk",
          gender: "MALE",
          guardian: "guardian",
          guardianPhone: "guardianPhone",
          guardianIdentityCard: "guardianIdentityCard",
          relationship: "relationship",
          relationshipWithPatient: "relationshipWithPatient"
        }
      ],
      test_add_list: [
        {
          type: "Xét nghiệm máu",
          file_name: "ketquaxetnghiem.jpg"
        },
        {
          type: "Xét nghiệm nước tiểu",
          file_name: "ketquaxetnghiem.jpg"
        },
        {
          type: "Xét nghiệm khác",
          file_name: "ketquaxetnghiem.jpg"
        }
      ],
      image_analyst_type: ["CT", "X-quang", "PET", "Siêu âm", "Hình ảnh khác"],
      message: ""
    };
  },
  async mounted() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const resultCode = urlParams.get("resultCode");
    const orderId = urlParams.get("orderId");
    const paymentType = urlParams.get("paymentType");
    this.message = "Đang xử lý thanh toán...";
    if (resultCode === "0") {
      if (paymentType != "DIRECT")
        await this.updateAppointment(orderId, "PROCESS");
      this.message = "Đặt Khám Thành Công";
    } else {
      if (paymentType != "DIRECT")
        await this.updateAppointment(orderId, "CANCEL");
      this.message = "Đặt Khám Thất Bại";
    }
  },
  methods: {
    async updateAppointment(orderId, status) {
      let token = this.$store.getters["auth/access_token"];

      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const params = {
        orderId: orderId,
        isPaid: status === "PROCESS",
        status: status
      };
      await this.axios.patch(`${url}/api/appointments`, params);
    },
    removeTestFile(index) {
      this.test_add_list.splice(index, 1);
    },

    getAddress(profile) {
      return (
        profile.address.province +
        ", " +
        profile.address.district +
        ", " +
        profile.address.ward +
        ", " +
        profile.address.address
      );
    },

    getDate(date_string) {
      let date = new Date(date_string);
      return date.toLocaleDateString();
    },

    getGender(string_gender) {
      return string_gender === "MALE" ? "Nam" : "Nữ";
    },

    select_profile_success() {
      this.is_select_profile = false;
      this.is_payment = true;
    },

    make_pay_ment() {},
    go_to_appointment_history() {
      this.$router.push({ name: "Lịch sử Đặt khám" });
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
</style>
