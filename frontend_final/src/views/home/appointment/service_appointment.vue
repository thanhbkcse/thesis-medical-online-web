<template>
  <div>
    <v-card
      width="100%"
      class="d-flex flex-column justify-space-around align-center"
      min-height="316px"
      color="#EEF2F6"
    >
      <p class="font-weight-bold mt-12" style="color: #537da5; font-size: 40px">
        Đặt khám theo dịch vụ
      </p>
      <p>
        Lựa chọn dịch vụ theo nhu cầu của bạn
      </p>
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
          placeholder="Tìm kiếm tên dịch vụ, mô tả dịch vụ"
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
              <v-list-item>
                <v-autocomplete
                  v-model="hospitalSelect"
                  :items="hospitals"
                  prepend-inner-icon="mdi-domain"
                  label="Bệnh Viện"
                  clearable
                  dense
                  outlined
                  :menu-props="{ offsetY: true }"
                  placeholder="Tìm bện viện"
                ></v-autocomplete>
              </v-list-item>
              <v-list-item>
                <v-select
                  v-model="specialtySelect"
                  :items="specialties"
                  label="Chuyên Khoa"
                  prepend-inner-icon="mdi-stethoscope"
                  item-text="text"
                  item-value="value"
                  clearable
                  dense
                  outlined
                  :menu-props="{ offsetY: true }"
                ></v-select>
              </v-list-item>
              <v-list-item class="justify-center">
                Giá từ
                {{ get_text_price(range[0]) }} đ đến
                {{ get_text_price(range[1]) }} đ
              </v-list-item>
              <v-list-item>
                <v-range-slider
                  v-model="range"
                  :max="max"
                  :min="min"
                  hide-details
                  class="align-center"
                  step="100000"
                >
                </v-range-slider>
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
        width="1200"
        elevation="0"
        color="#FCFCFD"
      >
        <div class="d-flex flex-column" v-if="!listService.length">
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
        <!-- list service -->
        <v-row :justify="listService.length == 1 ? 'center' : undefined">
          <v-col :md="6" v-for="(service, i) in listService" :key="i">
            <v-card
              class="d-flex flex-column pa-6"
              @click="moveToInfo(service)"
            >
              <v-card elevation="0" class="d-flex flex-row">
                <v-card
                  width="126"
                  min-height="100"
                  class="align-self-start"
                  elevation="0"
                >
                  <v-img :src="getImgOfService(service)"></v-img>
                </v-card>
                <v-card
                  class="d-flex flex-column ml-3"
                  elevation="0"
                  width="100%"
                >
                  <!-- service name -->
                  <div class="d-flex flex-row justify-space-between">
                    <p class="text-body-2 mb-2 font-weight-bold">
                      {{ service.name }}
                    </p>
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
                          {{ service.registrationNumber }}
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
                        <p style="color: #537da5">{{ service.favorite }}</p>
                      </v-card>
                    </div>
                  </div>

                  <div class="d-flex mb-2">
                    <v-icon small color="#537da5">mdi-domain</v-icon>
                    <p class="ma-0  text-body-2 ml-2" style="color: #667085">
                      {{ service.hospital.name }}
                    </p>
                  </div>
                  <v-spacer></v-spacer>
                  <div
                    class="d-flex flex-row align-center justify-space-between"
                  >
                    <p
                      class="font-weight-bold text-body-2"
                      style="color: #537da5; margin: 0"
                    >
                      {{ get_text_price(service.price) }} đ
                    </p>
                    <v-icon small color="#537da5">mdi-arrow-right</v-icon>
                  </div>
                </v-card>
              </v-card>
            </v-card>
          </v-col>
        </v-row>
        <!-- pagination -->
        <v-card
          style="background: none"
          width="100%"
          elevation="0"
          class="d-flex justify-center mt-10"
        >
          <div class="text-center">
            <v-pagination
              v-if="listService.length"
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
const url = process.env.VUE_APP_ROOT_API;
export default {
  data() {
    return {
      page: 1,
      listService: [],
      totalPages: 0,
      params: {
        filters: [],
        sorts: [],
        page: 0,
        size: 8
      },
      loading: false,
      hospitals: [],
      specialties: [],
      hospitalSelect: null,
      specialtySelect: null,
      provinceSelect: null,
      search: "",
      min: 0,
      max: 5000000,
      range: [100000, 1000000],
      provinces: []
    };
  },
  async created() {
    this.getProvines();
    this.getHospitals();
    this.getSpecialties();
    await this.getListService();
    this.loading = false;
  },
  watch: {
    page: {
      handler() {
        this.getListService();
      }
    },
    hospitalSelect: {
      handler: _.debounce(function() {
        this.getListService();
      }, 400)
    },
    specialtySelect: {
      handler: _.debounce(function() {
        this.getListService();
      }, 400)
    },
    provinceSelect: {
      handler: _.debounce(function() {
        this.getListService();
      }, 400)
    },
    search: {
      handler: _.debounce(function() {
        this.getListService();
      }, 400)
    }
  },
  computed: {
    filterNumber() {
      let filterNumber = 0;
      if (this.provinceSelect) filterNumber++;
      if (this.hospitalSelect) filterNumber++;
      if (this.specialtySelect) filterNumber++;
      return filterNumber;
    }
  },
  methods: {
    clearFilters() {
      this.provinceSelect = null;
      this.hospitalSelect = null;
      this.specialtySelect = null;
    },
    async getProvines() {
      const res = await this.axios.get(
        `https://vn-public-apis.fpo.vn/provinces/getAll?limit=-1`
      );
      let provinces = res.data?.data?.data;
      for (let p of provinces) {
        this.provinces.push({ text: p.name, value: p.code });
      }
    },
    async getListService() {
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
      if (this.specialtySelect) {
        params.filters.push({
          key: "specialty",
          operator: "EQUAL",
          field_type: "SPECIALTY",
          value: this.specialtySelect
        });
      }
      if (this.provinceSelect) {
        params.filters.push({
          key: "hospital.address.province",
          operator: "EQUAL_NESTED",
          field_type: "STRING",
          value: this.provinceSelect
        });
      }
      if (this.hospitalSelect) {
        params.filters.push({
          key: "hospital.id",
          operator: "EQUAL_NESTED",
          field_type: "STRING",
          value: this.hospitalSelect
        });
      }
      params.page = this.page - 1;
      const res = await this.axios.post(`${url}/api/services/search`, params);
      this.loading = false;
      this.listService = res.data.results;
      this.totalPages = res.data?.meta?.totalPages;
    },
    async getHospitals() {
      this.axios.get(`${url}/api/hospitals/list`).then(res => {
        this.hospitals = res.data.results;
      });
    },
    async getSpecialties() {
      this.axios.get(`${url}/api/specialties`).then(res => {
        this.specialties = res.data.results;
      });
    },
    async moveToInfo(service) {
      await this.$store.dispatch(
        "appointment/set_service_select_to_make_appointment",
        service
      );
      this.$router.push({ name: "Thông tin đặt lịch dịch vụ" }).catch(error => {
        if (error == null) {
          return;
        }
        if (error.name != "NavigationDuplicated") {
          throw error;
        }
      });
    },
    getImgOfService(service) {
      if (service.imageUrl != null) {
        return service.imageUrl;
      } else {
        return require("@/assets/img/home/service_avt.png");
      }
    },
    get_text_price(price) {
      return price.toLocaleString().replaceAll(",", ".");
    }
  }
};
</script>
<style scoped>
.btn {
  text-transform: none;
}
.numberCircle {
  border-radius: 50%;
  width: 25px;
  height: 25px;
  padding: 3px;

  background: #fff;
  color: #537da5;
  text-align: center;
}
</style>
