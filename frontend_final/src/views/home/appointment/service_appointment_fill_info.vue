<template>
  <v-card color="#FCFCFD" width="100%" class="pa-12">
    <div
      class="d-flex flex-column flex-md-row mx-8 justify-center align-center"
    >
      <v-card
        width="704"
        min-height="500"
        elevation="0"
        color="#FCFCFD"
        class="align-self-start"
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
            <v-avatar size="48" class="mr-5">
              <v-img :src="getImgOfService(service_info)"></v-img>
            </v-avatar>
            <p class="ma-0 font-weight-bold">
              {{ service_info.name }}
            </p>
            <v-spacer></v-spacer>
            <p class="ma-0 font-weight-bold" style="color: #537da5">
              {{ get_text_price(service_info.price) }} đ
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
              <p class="mb-3 font-weight-bold">{{ book_time.time }}</p>
              <p class="ma-0 text-body-2" style="color: #667085">
                {{ book_time.date }}
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
              <v-img :src="getImgOfHospital(service_info.hospital)"></v-img>
            </v-avatar>
            <div class="d-flex flex-column justify-center">
              <p class="mb-2 font-weight-bold">
                {{ service_info.hospital.name }}
              </p>
              <p class="text-body-2" style="color: #667085">
                {{ get_hospital_address(service_info.hospital) }}
              </p>
              <p class="text-body-2 font-weight-medium" style="color: #537da5">
                Hiện chỉ đường
              </p>
            </div>
          </v-card>
        </v-card>
        <!-- profile after select -->
        <v-card width="700" class="mt-8" v-if="is_payment">
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
            <v-expansion-panel v-model="selected_profile" class="mb-4">
              <v-expansion-panel-header>
                <v-card class="d-flex flex-row align-center" elevation="0">
                  <v-avatar size="40">
                    <img src="@/assets/img/home/appbar/avatar.png" />
                  </v-avatar>
                  <div class="d-flex flex-column ml-3">
                    <p class="ma-0 font-weight-bold text-body-1">
                      {{ selected_profile.lastName }}
                      {{ selected_profile.firstName }}
                    </p>
                    <p
                      class="ma-0 font-weight-normal text-body-2"
                      style="color: #667085"
                    >
                      {{ selected_profile.relationship }}
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
                      {{ selected_profile.email }}
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
                      {{ selected_profile.phone }}
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
                      {{ getAddress(selected_profile) }}
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
                      {{ getGender(selected_profile.gender) }}
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
                      {{ getDate(selected_profile.dob) }}
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
                      {{ selected_profile.identityCard }}
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
                    <p class="ma-0 font-weight-medium">
                      {{ selected_profile.job }}
                    </p>
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
                      {{ selected_profile.address.country }}
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
                    <p class="ma-0 font-weight-medium">
                      {{ selected_profile.folk }}
                    </p>
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
                v-model="symptom"
                spellcheck="false"
                solo
                height="148"
                flat
                hide-details
                placeholder="Vui lòng mô tả triệu chứng của bạn..."
                :readonly="!is_select_profile"
              >
              </v-textarea>
            </v-card>
            <!-- test results type -->
            <div class="d-flex flex-row justify-space-between align-center">
              <p class="font-weight-medium text-body-1 ma-0">
                Kết quả xét nghiệm
              </p>
            </div>
            <!-- add test result  -->
            <v-card
              outlined
              class="my-5 pa-2 d-flex flex-row"
              v-if="is_select_profile"
            >
              <v-file-input
                v-model="test_file"
                placeholder="Vui lòng chọn hình ảnh"
                solo
                dense
                prepend-inner-icon="mdi-link-variant"
                clear-icon=""
                prepend-icon=""
                hide-details=""
                flat
                multiple
                small-chips
              ></v-file-input>
              <v-btn
                class="btn white--text font-weight-medium text-body-2"
                color="#537DA5"
                elevation="0"
                @click="addTestFile"
                >Tải lên</v-btn
              >
            </v-card>

            <!-- list add test result -->
            <div
              class="d-flex flex-wrap justify-space-between align-center mb-5"
              v-for="(image, index) in test_add_list"
              :key="index"
            >
              <v-card width="221" outlined class="pa-1">
                <v-combobox
                  spellcheck="false"
                  v-model="image.type"
                  :items="test_type"
                  item-text="name"
                  solo
                  dense
                  flat
                  append-icon="mdi-chevron-down"
                  item-color="light-blue darken-4"
                  placeholder="Chọn loại xét nghiệm"
                  hide-details=""
                  :readonly="!is_select_profile"
                ></v-combobox>
              </v-card>
              <v-card min-width="50%" outlined class="pa-3 d-flex flex-row">
                <v-icon class="mr-2">mdi-image-outline</v-icon>
                <v-card min-width="50%" elevation="0">
                  <p class="ma-0" style="">
                    {{ image.file_name }}
                  </p>
                </v-card>

                <v-spacer></v-spacer>
                <v-icon @click="removeTestFile(index)" v-if="is_select_profile"
                  >mdi-close</v-icon
                >
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
            <!-- add test result  -->
            <v-card
              outlined
              class="my-5 pa-2 d-flex flex-row"
              v-if="is_select_profile"
            >
              <v-file-input
                placeholder="Vui lòng chọn hình ảnh"
                solo
                dense
                prepend-inner-icon="mdi-link-variant"
                clear-icon=""
                prepend-icon=""
                hide-details=""
                flat
                v-model="image_file"
                multiple
                small-chips
              ></v-file-input>
              <v-btn
                class="btn white--text font-weight-medium text-body-2"
                color="#537DA5"
                elevation="0"
                @click="addImageAnalystFile"
                >Tải lên</v-btn
              >
            </v-card>

            <!-- list add test result -->
            <div
              class="d-flex flex-wrap justify-space-between align-center mb-5"
              v-for="(image, index) in image_analyst_list"
              :key="index + 3"
            >
              <v-card width="221" outlined class="pa-1">
                <v-combobox
                  spellcheck="false"
                  v-model="image.type"
                  :items="image_analyst_type"
                  item-text="name"
                  item-value="key"
                  solo
                  dense
                  flat
                  append-icon="mdi-chevron-down"
                  item-color="light-blue darken-4"
                  placeholder="Chọn loại hình ảnh"
                  hide-details=""
                  :readonly="!is_select_profile"
                ></v-combobox>
              </v-card>
              <v-card min-width="50%" outlined class="pa-3 d-flex flex-row">
                <v-icon class="mr-2">mdi-image-outline</v-icon>
                <v-card min-width="50%" elevation="0">
                  <p class="ma-0" style="">
                    {{ image.file_name }}
                  </p>
                </v-card>

                <v-spacer></v-spacer>
                <v-icon @click="removeImageFile(index)" v-if="is_select_profile"
                  >mdi-close</v-icon
                >
              </v-card>
            </div>
          </v-card>
        </v-card>
      </v-card>
      <!-- select profile and make payment -->
      <v-card
        class="ml-8 align-self-start d-flex flex-column"
        width="448"
        color="#FCFCFD"
        elevation="0"
      >
        <!-- select profile -->
        <div v-if="is_select_profile">
          <v-card class="d-flex flex-column">
            <v-card
              class="d-flex flex-column pa-6 font-weight-bold"
              elevation="0"
              tile
              style="font-size: 24px"
              >Hồ sơ khám bệnh</v-card
            >
            <v-divider style="border-color: #f2f4f7 !important"></v-divider>
            <!-- price -->
            <v-radio-group v-model="selected_profile" class="ma-0">
              <v-card
                v-for="(profile, index) in profile_list"
                :key="index"
                class="pa-6 d-flex flex-row"
                tile
                elevation="0"
              >
                <v-avatar size="48" class="mr-5">
                  <v-img src="@/assets/img/user/profile/avatar1.svg"></v-img>
                </v-avatar>
                <div class="d-flex flex-column justify-center">
                  <p class="mb-2 font-weight-bold">
                    {{ profile.lastName }} {{ profile.firstName }}
                  </p>
                  <p class="ma-0 text-body-2" style="color: #667085">
                    {{ profile.relationship }}
                  </p>
                </div>
                <v-spacer></v-spacer>
                <v-radio :value="profile" color="#537DA5"></v-radio>
              </v-card>
            </v-radio-group>
          </v-card>
          <v-btn
            class="mt-8 btn white--text text-body-1"
            color="#537DA5"
            elevation="0"
            width="100%"
            @click="select_profile_success"
            >Tiến hành thanh toán
          </v-btn>
        </div>
        <!-- make payment -->
        <div v-if="is_payment">
          <v-card class="d-flex flex-column">
            <v-card
              class="d-flex flex-column pa-6 font-weight-bold"
              elevation="0"
              tile
              style="font-size: 24px"
              >Thanh toán</v-card
            >
            <v-divider style="border-color: #f2f4f7 !important"></v-divider>
            <!-- select payment method -->
            <v-card elevation="0" class="pa-6 d-flex flex-column">
              <v-card color="#FCFCFD" class="d-flex flex-row align-center pa-4">
                <p class="ma-0 text-body-1">Mã ưu đãi</p>
                <v-spacer></v-spacer>
                <v-card class="text-body-2" color="#D0D5DD" outlined>
                  <v-card
                    elevation="0"
                    color="#F9FAFB"
                    class="d-flex v-row justify-center pa-1 align-center"
                  >
                    <v-img src="@/assets/img/payment/discount.svg"></v-img>
                    <p class="ma-0 text-body-1" style="color: #667085">
                      Chọn mã ưu đãi
                    </p></v-card
                  >
                </v-card>
                <v-icon class="ml-6">mdi-chevron-right</v-icon>
              </v-card>
              <!-- payment list -->
              <v-card class="mt-6 d-flex flex-column" elevation="0">
                <p class="ma-0 font-weight-bold" style="font-size: 20px">
                  Phương thức thanh toán
                </p>
                <v-radio-group v-model="payment_selection" class="ma-0">
                  <!-- Visa -->
                  <div>
                    <v-card
                      class="mt-6 d-flex flex-row pa-4"
                      elevation="0"
                      color="#FCFCFD"
                    >
                      <v-img
                        contain
                        height="32"
                        width="32"
                        class="mr-5"
                        src="@/assets/img/payment/visa.png"
                      ></v-img>
                      <v-card class="d-flex flex-column mr-3" elevation="0">
                        <p class="mb-2 font-weight-bold">
                          Thanh toán bằng thẻ Visa
                        </p>
                        <p class="ma-0">
                          Hỗ trợ thanh toán xuyên quốc gia qua thẻ
                          Visa/Mastercard.
                        </p>
                      </v-card>
                      <v-radio value="VISA" color="#537DA5"></v-radio>
                    </v-card>
                    <!-- ATM -->
                    <v-card
                      class="mt-6 d-flex flex-row pa-4"
                      elevation="0"
                      color="#FCFCFD"
                    >
                      <v-img
                        contain
                        height="32"
                        width="32"
                        class="mr-5"
                        src="@/assets/img/payment/atm_card.png"
                      ></v-img>
                      <v-card class="d-flex flex-column mr-3" elevation="0">
                        <p class="mb-2 font-weight-bold">
                          Thanh toán bằng thẻ ATM nội địa
                        </p>
                        <p class="ma-0">
                          Sử dụng tài khoản Internet Banking của ngân hàng bạn
                          đang sử dụng để thanh toán.
                        </p>
                      </v-card>
                      <v-radio value="ATM" color="#537DA5"></v-radio>
                    </v-card>
                    <!-- Momo -->
                    <v-card
                      class="mt-6 d-flex flex-row pa-4"
                      elevation="0"
                      color="#FCFCFD"
                    >
                      <v-img
                        contain
                        height="32"
                        width="32"
                        class="mr-5"
                        src="@/assets/img/payment/momo.png"
                      ></v-img>
                      <v-card class="d-flex flex-column mr-3" elevation="0">
                        <p class="mb-2 font-weight-bold">
                          Thanh toán bằng Momo
                        </p>
                        <p class="ma-0">
                          Quét mã QR trong ứng dụng ví điện tử Momo để thanh
                          toán dịch vụ.
                        </p>
                      </v-card>
                      <v-radio value="MOMO" color="#537DA5"></v-radio>
                    </v-card>
                  </div>
                  <!-- CSYT -->
                  <v-card
                    class="mt-6 d-flex flex-row pa-4"
                    elevation="0"
                    color="#FCFCFD"
                  >
                    <v-img
                      contain
                      height="32"
                      width="32"
                      class="mr-5"
                      src="@/assets/img/payment/direct.jpg"
                    ></v-img>
                    <v-card class="d-flex flex-column mr-3" elevation="0">
                      <p class="mb-2 font-weight-bold">
                        Thanh toán tại Cơ sở y tế
                      </p>
                      <p class="ma-0">
                        Làm thủ tục thanh toán tại quầy của CSYT trước khi thực
                        hiện mỗi dịch vụ
                      </p>
                    </v-card>
                    <v-radio value="DIRECT" color="#537DA5"></v-radio>
                  </v-card>
                </v-radio-group>
                <v-card></v-card>
              </v-card>
            </v-card>
          </v-card>
          <v-btn
            class="mt-8 btn white--text text-body-1"
            color="#537DA5"
            elevation="0"
            width="100%"
            :loading="loading"
            @click="make_payment"
            >Xác nhận thanh toán
          </v-btn>
        </div>
      </v-card>
    </div>
  </v-card>
</template>

<script>
import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;
export default {
  created() {
    let time_data = this.$store.getters[
      "appointment/make_appointment_set_time_service"
    ];
    if (time_data != null) {
      this.book_time.time = time_data.time;
      this.book_time.date = time_data.date;
      this.book_time.type = time_data.type;
      this.book_time.room = time_data.room;
    }
    this.get_service_select();
    this.getProfileList();
  },
  data() {
    return {
      book_time: {
        time: "",
        date: "",
        type: "",
        room: {}
      },
      service_info: {
        hospital: {
          name: "",
          address: {}
        },
        price: 0
      },
      symptom: "",
      selected_profile: {},
      loading: false,
      is_select_profile: true,
      is_payment: false,
      test_type: [
        { name: "Xét nghiệm máu", key: "BLOOD_TEST" },
        { name: "Xét nghiệm nước tiểu", key: "URINE_TEST" },
        { name: "Xét nghiệm khác", key: "DIFFERENT_TEST" }
      ],
      radioGroup: 1,
      payment_selection: "",
      profile_list: [],
      test_file: [],
      test_add_list: [],
      image_analyst_type: [
        { name: "CT", key: "CT_SCAN" },
        { name: "X-quang", key: "X_RAY " },
        { name: "PET", key: "PET_SCAN" },
        { name: "Siêu âm", key: "SUPERSONIC" },
        { name: "MRI", key: "MRI" },
        { name: "Hình ảnh khác", key: "DIFFERENT_IMAGE" }
      ],
      image_analyst_list: [],
      image_file: [],
      submit_file_list: []
    };
  },
  methods: {
    removeTestFile(index) {
      this.test_add_list.splice(index, 1);
    },

    removeImageFile(index) {
      this.image_analyst_list.splice(index, 1);
    },

    addTestFile() {
      this.test_file.forEach(file => {
        this.test_add_list.push({
          type: { key: "", name: "" },
          file_name: file.name,
          data: file
        });
      });
    },

    addImageAnalystFile() {
      this.image_file.forEach(file => {
        this.image_analyst_list.push({
          type: { key: "", name: "" },
          file_name: file.name,
          data: file
        });
      });
    },

    getAddress(profile) {
      if (profile.address == null) return "";
      return (
        profile.address.address +
        ", " +
        profile.address.ward +
        ", " +
        profile.address.district +
        ", " +
        profile.address.province
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

    get_text_price(price) {
      return price.toLocaleString().replaceAll(",", ".");
    },

    async get_service_select() {
      this.service_info = await this.$store.getters[
        "appointment/make_appointment_service_select"
      ];
    },
    get_hospital_address(hospital) {
      let address_str = "";
      if (hospital.address.address != null) {
        if (address_str.length == 0) {
          address_str = hospital.address.address;
        }
      }
      if (hospital.address.ward != null) {
        address_str =
          address_str.length == 0
            ? hospital.address.ward
            : address_str + ", " + hospital.address.ward;
      }
      if (hospital.address.district != null) {
        address_str =
          address_str.length == 0
            ? hospital.address.district
            : address_str + ", " + hospital.address.district;
      }
      if (hospital.address.province != null) {
        address_str =
          address_str.length == 0
            ? hospital.address.province
            : address_str + ", " + hospital.address.province;
      }

      if (hospital.address.country != null) {
        address_str =
          address_str.length == 0
            ? hospital.address.country
            : address_str + ", " + hospital.address.country;
      }
      return address_str;
    },

    async getProfileList() {
      let token = this.$store.getters["auth/access_token"];
      const param = {
        token: token
      };
      await this.$store.dispatch("profile/profile_list", param);
      this.profile_list = this.$store.getters["profile/profile_list"];
    },
    getImgOfHospital(hospital) {
      if (hospital.imageUrl != null) {
        return hospital.imageUrl;
      } else {
        return require("@/assets/img/home/hospital_avt.png");
      }
    },
    getImgOfService(service) {
      if (service.imageUrl != null) {
        return service.imageUrl;
      } else {
        return require("@/assets/img/user/profile/avatar1.svg");
      }
    },
    async make_payment() {
      let orderId = new Date().getTime();
      if (this.payment_selection != "DIRECT") await this.paymentOnline(orderId);
      this.createAppointment(orderId);
    },
    async paymentOnline(orderId) {
      let token = this.$store.getters["auth/access_token"];
      const mapPaymentType = {
        ATM: "payWithATM",
        MOMO: "captureWallet",
        VISA: "payWithCC"
      };
      const params = {
        token: token,
        data: {
          requestId: new Date().getTime() + "id",
          orderId: orderId,
          ipnUrl: `${url}/api/payment/momo/verify`,
          redirectUrl:
            "http://blousecare.online/doctor-appointment-booking-success/",
          // redirectUrl:
          //   "http://localhost:8080/doctor-appointment-booking-success/",
          amount: "100000",
          orderInfo: "Khám theo yêu cầu tại đại học y dược",
          extraData: "",
          requestType: mapPaymentType[this.payment_selection]
        }
      };

      axios.defaults.headers.common = {
        Authorization: `Bearer ${params.token}`
      };
      let responseData = await axios
        .post(`${url}/api/payment/momo`, params.data)
        .then(response => {
          return {
            data: response.data
          };
        });

      window.open(responseData.data.results.payUrl, "_self");
    },
    async createAppointment(orderId) {
      this.loading = true;
      let post_file_list = this.test_add_list.concat(this.image_analyst_list);

      await Promise.all(
        post_file_list.map(async file => {
          await this.post_file(file.data, file.type);
        })
      );

      let b = this.submit_file_list;
      let token = this.$store.getters["auth/access_token"];
      let param = {
        token: token,
        data: {
          profileId: this.selected_profile.id,
          serviceId: this.service_info.id,
          //roomId: this.book_time.room.id,
          date: this.book_time.date,
          time: this.book_time.time,
          symptom: this.symptom,
          type: this.book_time.type,
          files: b,
          fee: this.service_info.price,
          isPaid: false,
          category: "SERVICE",
          orderId: orderId,
          paymentType: this.payment_selection
        }
      };
      await this.$store.dispatch("appointment/createAppointment", param);
      this.loading = false;
      if (this.payment_selection == "DIRECT") {
        this.$router.push({
          path:
            "/doctor-appointment-booking-success/?paymentType=DIRECT&resultCode=0"
        });
      }
    },

    async post_file(file, type) {
      let form_data = new FormData();
      form_data.append("file", file);
      let token = this.$store.getters["auth/access_token"];

      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      await axios
        .post(`${url}/api/files/upload`, form_data, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(response => {
          if (response.data.code === 200) {
            this.submit_file_list.push({
              imageUrl: response.data.results,
              type: type.key
            });
          }
        });
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
</style>
