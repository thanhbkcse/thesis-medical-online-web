<template>
  <div>
    <v-card
      width="100%"
      class="d-flex flex-column justify-space-around align-center"
      min-height="316px"
      color="#EEF2F6"
    >
      <p class="font-weight-bold mt-12" style="color: #537da5; font-size: 40px">
        Đặt khám tại bệnh viện
      </p>
      <p>
        Để được tiếp đón ưu tiên, không chờ đợi tại các bệnh viện, phòng khám
        hàng đầu
      </p>
      <v-card width="640px" color="#EEF2F6" elevation="0">
        <!-- <p>
          Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet
          sint. Velit officia consequat duis enim velit mollit. Exercitation
          veniam consequat sunt nostrud amet.
        </p> -->
      </v-card>
      <v-card
        elevation="0"
        class="d-flex align-center mt-8 mb-12"
        width="720px"
        height="76px"
        outlined
      >
        <v-text-field
          v-model="search"
          full-width
          solo
          flat
          placeholder="Tìm kiếm tên bệnh viện, phòng khám"
          hide-details=""
        ></v-text-field>
        <v-spacer />
        <v-divider inset vertical></v-divider>

        <v-menu :close-on-content-click="false">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="#537DA5"
              elevation="0"
              class="white--text btn font-weight-medium text-body-1 mr-3 ml-3"
              v-bind="attrs"
              v-on="on"
            >
              <v-icon medium class="mr-2">mdi-filter</v-icon> Lọc
              <div v-if="filterNumber != 0" class="ml-2 numberCircle">
                {{ filterNumber }}
              </div>
            </v-btn>
          </template>
          <v-card width="300px">
            <div class="d-flex justify-center" style="color: #537DA5">
              <p class="font-weight-bold mt-3">Lọc Kết Quả</p>
            </div>
            <v-divider></v-divider>
            <v-list>
              <v-list-item class="mt-3">
                <v-autocomplete
                  v-model="provinceSelect"
                  :items="provinces"
                  prepend-inner-icon="mdi-map-marker"
                  item-text="text"
                  item-value="text"
                  label="Địa Điểm"
                  clearable
                  dense
                  outlined
                  :menu-props="{ offsetY: true }"
                  placeholder="Tìm địa điểm"
                ></v-autocomplete>
              </v-list-item>
              <div class="d-flex justify-center mt-3">
                <v-btn
                  color="#537DA5"
                  elevation="0"
                  class="white--text btn font-weight-medium text-body-1"
                  width="90%"
                  style="margin: 0 auto"
                  @click="clearFilters()"
                >
                  Bỏ lọc
                </v-btn>
              </div>
            </v-list>
          </v-card>
        </v-menu>
      </v-card>
    </v-card>
    <v-progress-linear
      :indeterminate="loading"
      :active="loading"
    ></v-progress-linear>
    <v-card
      width="100%"
      min-height="500px"
      color="#FCFCFD"
      class="py-12 px-16 d-flex justify-center"
    >
      <v-card
        class="mx-4 pa-8 d-flex flex-column"
        width="1280"
        elevation="0"
        color="#FCFCFD"
      >
        <div class="d-flex flex-column" v-if="!hospital_list.length">
          <v-card
            width="100%"
            class="d-flex justify-center"
            elevation="0"
            style="background: none"
          >
            <v-img
              class="d-flex"
              src="@/assets/img/user/profile/lookingNotFound.png"
              width="183px"
              height="186px"
              contain
            ></v-img>
          </v-card>
          <p
            class="font-weight-bold ml-8 d-flex justify-center"
            style="font-size: 20px"
          >
            Không có kết quả tìm kiếm
          </p>
          <p class="ml-8 d-flex justify-center" style="color: #667085">
            Xin lỗi chúng tôi không tìm thấy kết quả phù hợp với bạn.
          </p>
        </div>
        <!-- list hospital -->

        <v-row :justify="hospital_list.length == 1 ? 'center' : undefined">
          <v-col
            :md="6"
            v-for="(hospital, iHospital) in hospital_list"
            :key="iHospital"
          >
            <v-card
              class="d-flex flex-column justify-center align-center px-3"
              width="596"
              min-height="244"
            >
              <v-card
                width="548"
                min-height="112"
                elevation="0"
                class="d-flex flex-column"
              >
                <v-card
                  width="100%"
                  class="d-flex flex-row justify-space-between"
                  elevation="0"
                >
                  <div class="d-flex flex-row">
                    <v-card width="126" height="84" class="align-self-start">
                      <v-img :src="getImgOfHospital(hospital)"></v-img>
                    </v-card>
                    <v-card class="d-flex flex-column ml-3" elevation="0">
                      <p class="text-body-2 mb-2 font-weight-bold">
                        {{ hospital.name }}
                      </p>
                      <div class="d-flex">
                        <v-icon
                          class="align-self-start mr-2"
                          size="24"
                          color="#537da5"
                          >mdi-map-marker-outline</v-icon
                        >
                        <p class="text-body-2 mb-3">
                          {{ get_hospital_address(hospital) }}
                        </p>
                      </div>
                      <div class="d-flex flex-row">
                        <v-card
                          class="d-flex flex-row"
                          height="24"
                          width="50"
                          color="#EEF2F6"
                          style="border-radius: 50px"
                          elevation="0"
                        >
                          <v-icon color="#537DA5" class="align-self-start mr-1"
                            >mdi-calendar-month-outline</v-icon
                          >
                          <p style="color: #537da5">
                            {{ hospital.registrationNumber }}
                          </p>
                        </v-card>
                        <v-card
                          class="d-flex flex-row ml-1"
                          height="24"
                          width="50"
                          color="#EEF2F6"
                          style="border-radius: 50px"
                          elevation="0"
                        >
                          <v-icon
                            color="#FFC107"
                            class="align-self-start mr-1 ml-1"
                            >mdi-star</v-icon
                          >
                          <p style="color: #537da5">
                            {{ hospital.favorite }}
                          </p>
                        </v-card>
                      </div>
                    </v-card>
                  </div>
                </v-card>
              </v-card>
              <!-- price and booking -->
              <v-card class="mt-2" width="548" min-height="40" elevation="0">
                <v-divider
                  class="mt-1"
                  style="border-color: #f2f4f7 !important"
                ></v-divider>
                <div
                  class="d-flex flex-row mt-4 align-center justify-space-between"
                >
                  <v-btn
                    width="110"
                    height="44"
                    class="white--text btn text-body-2"
                    elevation="0"
                    color="#537DA5"
                    @click="moveToInfo(hospital)"
                  >
                    Xem chi tiết
                  </v-btn>
                </div>
              </v-card>
            </v-card>
          </v-col>
        </v-row>

        <!-- pagination -->
        <v-card
          width="100%"
          elevation="0"
          class="d-flex justify-center mt-10"
          style="background: none"
        >
          <div class="text-center">
            <v-pagination
              v-if="hospital_list.length"
              color="#537DA5"
              v-model="page"
              :length="totalPages"
              :total-visible="7"
            ></v-pagination>
          </div>
        </v-card>
      </v-card>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  async created() {
    this.getProvines();
    if (process.env.VUE_APP_LOGIN_DEV === "TRUE") {
      this.logindev();
    }
    else{
      await this.get_hospital_list();
    }
    this.loading = false;
  },

  data() {
    return {
      page: 2,
      hospital_list: [],
      params: {
        filters: [],
        sorts: [],
        page: 0,
        size: 8
      },
      page: 1,
      totalPages: 0,
      provinceSelect: null,
      search: "",
      loading: false,
      provinces: []
    };
  },
  watch: {
    page: {
      handler() {
        this.get_hospital_list();
      }
    },
    search: {
      handler: _.debounce(function() {
        this.get_hospital_list();
      }, 400)
    },
    provinceSelect: {
      handler: _.debounce(function() {
        this.get_hospital_list();
      }, 400)
    }
  },
  computed: {
    filterNumber() {
      let filterNumber = 0;
      if (this.provinceSelect) filterNumber++;
      return filterNumber;
    }
  },
  methods: {
    clearFilters() {
      this.provinceSelect = null;
    },
    async getProvines() {
      const res = await axios.get(
        `https://vn-public-apis.fpo.vn/provinces/getAll?limit=-1`
      );
      let provinces = res.data?.data?.data;
      for (let p of provinces) {
        this.provinces.push({ text: p.name, value: p.code });
      }
    },
    async moveToInfo(hospital) {
      await this.$store.dispatch("hospital/set_hospital_select_info", hospital);
      this.$router
        .push({ name: "Thông tin đặt lịch bệnh viện" })
        .catch(error => {
          if (error == null) {
            return;
          }
          if (error.name != "NavigationDuplicated") {
            throw error;
          }
        });
    },

    async get_hospital_list() {
      this.loading = true;
      let params = this._.cloneDeep(this.params);
      if (this.search) {
        params.filters.push({
          key: "name",
          operator: "LIKE",
          field_type: "STRING",
          value: this.search.trim()
        });
      }
      if (this.provinceSelect) {
        params.filters.push({
          key: "address.province",
          operator: "EQUAL_NESTED",
          field_type: "STRING",
          value: this.provinceSelect
        });
      }
      params.page = this.page - 1;
      let res = await this.$store.dispatch(
        "hospital/read_all_hospital",
        params
      );
      this.totalPages = res.meta?.totalPages;
      let hospital_all_data = this.$store.getters["hospital/hospital_all_data"];
      this.hospital_list = hospital_all_data;
      this.loading = false;
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

    getImgOfHospital(hospital) {
      if (hospital.imageUrl != null) {
        return hospital.imageUrl;
      } else {
        return require("@/assets/img/home/hospital_avt.png");
      }
    },

    logindev(){
      this.hospital_list = [
        {
            "id": "1",
            "name": "Bệnh viện Hữu Nghị Việt Đức",
            "address": {
                "id": "dddf1b04-1003-45a0-b67c-42335d72fd82",
                "country": "Việt Nam",
                "province": "Thành phố Hà Nội",
                "district": "Hoàn Kiếm",
                "ward": "Hàng Bông",
                "address": "Số 18 Phủ Doãn"
            },
            "info": "Bệnh viện Hữu Nghị Việt Đức là một trong 5 bệnh viện tuyến trung ương hạng đặc biệt hàng đầu của Việt Nam. Tọa lạc giữa trung tâm thủ đô Hà Nội, có nhiều cổng nằm trên các mặt đường Tràng Thi, Phủ Doãn, Quán Sứ thuộc quận Hoàn Kiếm, Thành phố Hà Nội.",
            "registrationNumber": 10,
            "favorite": 5.0,
            "mapImageUrl": null,
            "imageUrl": null,
            "isActive": true,
            "hospitalHour": {
                "id": "980afcb5-8c89-4df7-bc16-8dd55dadd9e5",
                "mondayTime": "7:00 - 17:00",
                "mondayTimeBreak": "12:00 - 13:00",
                "tuesdayTime": "7:00 - 17:00",
                "tuesdayTimeBreak": "12:00 - 13:00",
                "wednesdayTime": "7:00 - 17:00",
                "wednesdayTimeBreak": "12:00 - 13:00",
                "thursdayTime": "7:00 - 17:00",
                "thursdayTimeBreak": "12:00 - 13:00",
                "fridayTime": "7:00 - 17:00",
                "fridayTimeBreak": "12:00 - 13:00",
                "saturdayTime": "7:00 - 17:00",
                "saturdayTimeBreak": "12:00 - 13:00",
                "sundayTime": "7:00 - 17:00",
                "sundayTimeBreak": "12:00 - 13:00"
            }
        },
        {
            "id": "2",
            "name": "Bệnh viện Hữu Nghị Việt Đức",
            "address": {
                "id": "dddf1b04-1003-45a0-b67c-42335d72fd82",
                "country": "Việt Nam",
                "province": "Thành phố Hà Nội",
                "district": "Hoàn Kiếm",
                "ward": "Hàng Bông",
                "address": "Số 18 Phủ Doãn"
            },
            "info": "Bệnh viện Hữu Nghị Việt Đức là một trong 5 bệnh viện tuyến trung ương hạng đặc biệt hàng đầu của Việt Nam. Tọa lạc giữa trung tâm thủ đô Hà Nội, có nhiều cổng nằm trên các mặt đường Tràng Thi, Phủ Doãn, Quán Sứ thuộc quận Hoàn Kiếm, Thành phố Hà Nội.",
            "registrationNumber": 10,
            "favorite": 5.0,
            "mapImageUrl": null,
            "imageUrl": null,
            "isActive": true,
            "hospitalHour": {
                "id": "980afcb5-8c89-4df7-bc16-8dd55dadd9e5",
                "mondayTime": "7:00 - 17:00",
                "mondayTimeBreak": "12:00 - 13:00",
                "tuesdayTime": "7:00 - 17:00",
                "tuesdayTimeBreak": "12:00 - 13:00",
                "wednesdayTime": "7:00 - 17:00",
                "wednesdayTimeBreak": "12:00 - 13:00",
                "thursdayTime": "7:00 - 17:00",
                "thursdayTimeBreak": "12:00 - 13:00",
                "fridayTime": "7:00 - 17:00",
                "fridayTimeBreak": "12:00 - 13:00",
                "saturdayTime": "7:00 - 17:00",
                "saturdayTimeBreak": "12:00 - 13:00",
                "sundayTime": "7:00 - 17:00",
                "sundayTimeBreak": "12:00 - 13:00"
            }
        },
                {
            "id": "3",
            "name": "Bệnh viện Hữu Nghị Việt Đức",
            "address": {
                "id": "dddf1b04-1003-45a0-b67c-42335d72fd82",
                "country": "Việt Nam",
                "province": "Thành phố Hà Nội",
                "district": "Hoàn Kiếm",
                "ward": "Hàng Bông",
                "address": "Số 18 Phủ Doãn"
            },
            "info": "Bệnh viện Hữu Nghị Việt Đức là một trong 5 bệnh viện tuyến trung ương hạng đặc biệt hàng đầu của Việt Nam. Tọa lạc giữa trung tâm thủ đô Hà Nội, có nhiều cổng nằm trên các mặt đường Tràng Thi, Phủ Doãn, Quán Sứ thuộc quận Hoàn Kiếm, Thành phố Hà Nội.",
            "registrationNumber": 10,
            "favorite": 5.0,
            "mapImageUrl": null,
            "imageUrl": null,
            "isActive": true,
            "hospitalHour": {
                "id": "980afcb5-8c89-4df7-bc16-8dd55dadd9e5",
                "mondayTime": "7:00 - 17:00",
                "mondayTimeBreak": "12:00 - 13:00",
                "tuesdayTime": "7:00 - 17:00",
                "tuesdayTimeBreak": "12:00 - 13:00",
                "wednesdayTime": "7:00 - 17:00",
                "wednesdayTimeBreak": "12:00 - 13:00",
                "thursdayTime": "7:00 - 17:00",
                "thursdayTimeBreak": "12:00 - 13:00",
                "fridayTime": "7:00 - 17:00",
                "fridayTimeBreak": "12:00 - 13:00",
                "saturdayTime": "7:00 - 17:00",
                "saturdayTimeBreak": "12:00 - 13:00",
                "sundayTime": "7:00 - 17:00",
                "sundayTimeBreak": "12:00 - 13:00"
            }
        },
                {
            "id": "4",
            "name": "Bệnh viện Hữu Nghị Việt Đức",
            "address": {
                "id": "dddf1b04-1003-45a0-b67c-42335d72fd82",
                "country": "Việt Nam",
                "province": "Thành phố Hà Nội",
                "district": "Hoàn Kiếm",
                "ward": "Hàng Bông",
                "address": "Số 18 Phủ Doãn"
            },
            "info": "Bệnh viện Hữu Nghị Việt Đức là một trong 5 bệnh viện tuyến trung ương hạng đặc biệt hàng đầu của Việt Nam. Tọa lạc giữa trung tâm thủ đô Hà Nội, có nhiều cổng nằm trên các mặt đường Tràng Thi, Phủ Doãn, Quán Sứ thuộc quận Hoàn Kiếm, Thành phố Hà Nội.",
            "registrationNumber": 10,
            "favorite": 5.0,
            "mapImageUrl": null,
            "imageUrl": null,
            "isActive": true,
            "hospitalHour": {
                "id": "980afcb5-8c89-4df7-bc16-8dd55dadd9e5",
                "mondayTime": "7:00 - 17:00",
                "mondayTimeBreak": "12:00 - 13:00",
                "tuesdayTime": "7:00 - 17:00",
                "tuesdayTimeBreak": "12:00 - 13:00",
                "wednesdayTime": "7:00 - 17:00",
                "wednesdayTimeBreak": "12:00 - 13:00",
                "thursdayTime": "7:00 - 17:00",
                "thursdayTimeBreak": "12:00 - 13:00",
                "fridayTime": "7:00 - 17:00",
                "fridayTimeBreak": "12:00 - 13:00",
                "saturdayTime": "7:00 - 17:00",
                "saturdayTimeBreak": "12:00 - 13:00",
                "sundayTime": "7:00 - 17:00",
                "sundayTimeBreak": "12:00 - 13:00"
            }
        },
                {
            "id": "5",
            "name": "Bệnh viện Hữu Nghị Việt Đức",
            "address": {
                "id": "dddf1b04-1003-45a0-b67c-42335d72fd82",
                "country": "Việt Nam",
                "province": "Thành phố Hà Nội",
                "district": "Hoàn Kiếm",
                "ward": "Hàng Bông",
                "address": "Số 18 Phủ Doãn"
            },
            "info": "Bệnh viện Hữu Nghị Việt Đức là một trong 5 bệnh viện tuyến trung ương hạng đặc biệt hàng đầu của Việt Nam. Tọa lạc giữa trung tâm thủ đô Hà Nội, có nhiều cổng nằm trên các mặt đường Tràng Thi, Phủ Doãn, Quán Sứ thuộc quận Hoàn Kiếm, Thành phố Hà Nội.",
            "registrationNumber": 10,
            "favorite": 5.0,
            "mapImageUrl": null,
            "imageUrl": null,
            "isActive": true,
            "hospitalHour": {
                "id": "980afcb5-8c89-4df7-bc16-8dd55dadd9e5",
                "mondayTime": "7:00 - 17:00",
                "mondayTimeBreak": "12:00 - 13:00",
                "tuesdayTime": "7:00 - 17:00",
                "tuesdayTimeBreak": "12:00 - 13:00",
                "wednesdayTime": "7:00 - 17:00",
                "wednesdayTimeBreak": "12:00 - 13:00",
                "thursdayTime": "7:00 - 17:00",
                "thursdayTimeBreak": "12:00 - 13:00",
                "fridayTime": "7:00 - 17:00",
                "fridayTimeBreak": "12:00 - 13:00",
                "saturdayTime": "7:00 - 17:00",
                "saturdayTimeBreak": "12:00 - 13:00",
                "sundayTime": "7:00 - 17:00",
                "sundayTimeBreak": "12:00 - 13:00"
            }
        },
      ];
    }
  }
};
</script>
<style scoped>
.btn {
  text-transform: none;
}
</style>
