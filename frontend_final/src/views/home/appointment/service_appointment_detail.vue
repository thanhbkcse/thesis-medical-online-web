<template>
  <v-card color="#FCFCFD" width="100%" class="pa-12">
    <div
      class="d-flex flex-column flex-md-row mx-8 justify-center align-center"
    >
      <v-card
        width="800"
        min-height="500"
        elevation="0"
        color="#FCFCFD"
        class="align-self-start"
      >
        <!-- doctor detail -->
        <v-card class="d-flex flex-row pa-6" width="100%">
          <!-- avatar -->
          <v-card width="240" class="align-self-start">
            <v-img :src="getImgOfService(service_info)"></v-img>
          </v-card>
          <!-- detail -->
          <v-card class="d-flex flex-column ml-5" elevation="0">
            <p class="mb-2 font-weight-bold" style="font-size: 20px">
              {{ this.service_info.name }}
            </p>
            <p class="text-body-2 mb-3">
              {{ this.service_info.hospital.name }}
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
                  {{ service_info.registrationNumber }}
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
                <v-icon color="#FFC107" class="align-self-start mr-1 ml-1"
                  >mdi-star</v-icon
                >
                <p style="color: #537da5">{{ service_info.favorite }}</p>
              </v-card>
            </div>
          </v-card>
        </v-card>

        <!-- description -->
        <v-card
          width="100%"
          min-height="200"
          class="mt-8"
          color="#FCFCFD"
          elevation="0"
        >
          <v-card max-width="321" class="rounded-b-0">
            <v-tabs color="#537DA5" slider-size="0" v-model="tab">
              <v-tab
                style="text-transform: none"
                class="font-weight-medium text-body-1"
                :key="'tab-1'"
                >Thông tin</v-tab
              >
              <v-tab
                style="text-transform: none"
                class="font-weight-medium text-body-1"
                :key="'tab-2'"
                >{{ titleFavorite }}</v-tab
              >
              <v-tab
                style="text-transform: none"
                class="font-weight-medium text-body-1"
                :key="'tab-3'"
                >{{ titleQuestion }}</v-tab
              >
            </v-tabs>
          </v-card>
          <v-card class="pa-6 rounded-t-0">
            <!-- all tab -->
            <v-tabs-items v-model="tab">
              <!-- infomation tab -->
              <v-tab-item :key="'tab-1'">
                <v-card class="d-flex flex-column">
                  <p style="white-space: pre-line" class="text-body-1 mb-6">
                    {{ service_info.info }}
                  </p>
                  <div class="d-flex flex-row justify-space-between">
                    <v-card class="d-flex flex-row" width="45%" elevation="0">
                      <v-icon class="align-self-start" size="24" color="#537da5"
                        >mdi-map-marker-outline</v-icon
                      >
                      <v-card class="ml-3" elevation="0">
                        <p class="text-body-2 mb-2 font-weight-medium">
                          {{ get_hospital_address(service_info.hospital) }}
                        </p>
                        <p
                          class="text-body-2 font-weight-medium"
                          style="color: #537da5"
                        >
                          Hiện chỉ đường
                        </p>
                      </v-card>
                    </v-card>
                  </div>
                </v-card>
              </v-tab-item>

              <!-- evaluate tab -->
              <v-tab-item :key="'tab-2'">
                <favorite
                  v-if="service_info && username"
                  :username="username"
                  :object="service_info"
                  :objectType="'service'"
                  @reloadFavorite="reloadFavorite"
                  @setNumFavorite="numFavorite = $event"
                  :key="keyFavorite"
                ></favorite>
              </v-tab-item>

              <!-- question tab -->
              <v-tab-item :key="'tab-3'">
                <question
                  v-if="service_info && username"
                  :username="username"
                  :object="service_info"
                  @reloadQuestion="reloadQuestion"
                  @setNumQuestion="numQuestion = $event"
                  :key="keyQuestion"
                ></question>
              </v-tab-item>
            </v-tabs-items>
          </v-card>
        </v-card>
      </v-card>
      <v-card class="ml-8 align-self-start" width="448">
        <!-- header -->
        <v-card class="pa-6 rounded-b-0 d-flex flex-row" color="#537DA5">
          <v-card
            rounded="circle"
            width="32"
            height="32"
            class="pa-2 mr-3"
            color="#EEF2F6"
          >
            <v-img src="@/assets/img/home/appbar/doctor.svg"></v-img>
          </v-card>

          <p
            class="mb-0 white--text font-weight-medium"
            style="font-size: 20px"
          >
            Chọn khung giờ khám
          </p>
        </v-card>
        <!-- price -->
        <v-card elevation="0" class="pa-6 d-flex flex-column">
          <!--  -->
          <div class="d-flex flex-row align-center">
            <v-card width="60%" elevation="0">
              <p class="text-body-1 font-weight-medium">
                {{ service_info.name }}
              </p>
            </v-card>
            <v-card width="40%" class="d-flex justify-end" elevation="0">
              <p class="ml-3 font-weight-bold" style="color: #537da5">
                {{ get_text_price(service_info.price) }} đ
              </p>
            </v-card>
          </div>

          <!-- location -->
          <v-card class="d-flex flex-row" elevation="0">
            <v-icon class="align-self-start" size="24" color="#537da5"
              >mdi-map-marker-outline</v-icon
            >
            <v-card class="ml-3" elevation="0">
              <p
                class="text-body-2 mb-2 font-weight-medium"
                style="color: #667085"
              >
                {{ get_hospital_address(service_info.hospital) }}
              </p>
            </v-card>
          </v-card>
        </v-card>
        <v-divider></v-divider>
        <!-- date picker -->
        <v-card class="d-flex flex-row justify-center pa-6" elevation="0">
          <p class="mb-0 d-flex align-center font-weight-medium">
            Ngày {{ this.show_date.date }} tháng {{ this.show_date.month }},
            {{ this.show_date.year }}
          </p>
          <v-spacer></v-spacer>
          <v-menu
            ref="select_time_menu"
            v-model="select_time_menu"
            :close-on-content-click="false"
            min-width="auto"
            offset-y
            left
          >
            <template v-slot:activator="{ on, attrs }">
              <v-icon v-bind="attrs" v-on="on"
                >mdi-calendar-month-outline</v-icon
              >
            </template>
            <v-date-picker
              v-model="date_pick"
              no-title
              scrollable
              locale="vi"
              color="#537DA5"
            >
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="select_time_menu = false">
                Hủy
              </v-btn>
              <v-btn text color="primary" @click="get_service_schedule">
                Chọn
              </v-btn>
            </v-date-picker>
          </v-menu>
        </v-card>
        <v-divider
          class="mt-1"
          style="border-color: #f2f4f7 !important"
        ></v-divider>
        <v-card
          min-height="500"
          class="d-flex justify-center align-center"
          v-if="is_loading_schedule"
        >
          <v-progress-circular
            indeterminate
            color="#537DA5"
            :size="70"
            :width="7"
          ></v-progress-circular>
        </v-card>
        <v-card elevation="0" v-if="!is_loading_schedule">
          <v-tabs
            color="#537DA5"
            slider-size="3"
            v-model="calander_tab"
            fixed-tabs
            height="60"
          >
            <v-tab
              style="text-transform: none"
              class="font-weight-medium text-body-1"
              :key="'online'"
              >Tại nhà</v-tab
            >
            <v-tab
              style="text-transform: none"
              class="font-weight-medium text-body-1"
              :key="'offline'"
              >Trực tiếp tại viện</v-tab
            >
          </v-tabs>
          <v-tabs-items v-model="calander_tab">
            <!-- online tab -->
            <v-tab-item :key="'online'">
              <v-card
                v-if="!online.has_schedule"
                min-height="450"
                class="d-flex align-center justify-center"
              >
                Không có lịch khám
              </v-card>
              <v-card
                class="pa-6 d-flex flex-column justify-space-between"
                elevation="0"
                v-if="online.has_schedule"
                min-height="450"
              >
                <!-- morning -->
                <div class="d-flex flex-column">
                  <p class="font-weight-medium">Sáng</p>
                  <div class="d-flex flex-wrap justify-start">
                    <v-item-group v-model="online_selected">
                      <v-item
                        v-for="(item, idx) in online.morning"
                        :key="idx"
                        v-slot="{ active, toggle }"
                        :value="item"
                      >
                        <v-btn
                          :outlined="active ? false : true"
                          color="#537DA5"
                          @click="toggle"
                          class="mb-5 mx-3"
                          active-class="white--text"
                          elevation="0"
                          height="41"
                          width="102"
                          :style="active ? 'border: 1px #537DA5 solid' : ''"
                        >
                          <p
                            class="ma-0 font-weight-medium"
                            :style="active ? 'color:white' : ''"
                          >
                            {{ item }}
                          </p>
                        </v-btn>
                      </v-item>
                    </v-item-group>
                  </div>
                </div>
                <!-- afternoon -->
                <div class="d-flex flex-column">
                  <p class="font-weight-medium">Chiều</p>
                  <div class="d-flex flex-wrap justify-start">
                    <v-item-group v-model="online_selected">
                      <v-item
                        v-for="(item, idx) in online.afternoon"
                        :key="idx"
                        v-slot="{ active, toggle }"
                        :value="item"
                      >
                        <v-btn
                          :outlined="active ? false : true"
                          color="#537DA5"
                          @click="toggle"
                          class="mb-5 mx-3"
                          active-class="white--text"
                          elevation="0"
                          height="41"
                          width="102"
                          :style="active ? 'border: 1px #537DA5 solid' : ''"
                        >
                          <p
                            class="ma-0 font-weight-medium"
                            :style="active ? 'color:white' : ''"
                          >
                            {{ item }}
                          </p>
                        </v-btn>
                      </v-item>
                    </v-item-group>
                  </div>
                </div>
                <!-- booking -->
                <v-btn
                  elevation="0"
                  color="#537DA5"
                  class="btn white--text"
                  @click="submit_online_select_time"
                  ><v-icon left color="#537da5">mdi-plus-circle-outline</v-icon>
                  <p class="ma-0 font-weight-bold text-body-1">
                    Đặt lịch
                  </p>
                </v-btn>
              </v-card>
            </v-tab-item>

            <!-- offline tab -->
            <v-tab-item :key="'offline'">
              <v-card
                v-if="!offline.has_schedule"
                min-height="450"
                class="d-flex align-center justify-center"
              >
                Không có lịch khám
              </v-card>
              <v-card
                class="pa-6 d-flex flex-column justify-space-between"
                elevation="0"
                v-if="offline.has_schedule"
                min-height="450"
              >
                <!-- morning -->
                <div class="d-flex flex-column">
                  <p
                    class="font-weight-medium"
                    v-if="offline.morning.length > 0"
                  >
                    Sáng
                  </p>
                  <div class="d-flex flex-wrap justify-start">
                    <v-item-group v-model="offline_selected">
                      <v-item
                        v-for="(item, idx) in offline.morning"
                        :key="idx"
                        v-slot="{ active, toggle }"
                        :value="item"
                      >
                        <v-btn
                          :outlined="active ? false : true"
                          color="#537DA5"
                          @click="toggle"
                          height="41"
                          width="102"
                          class="mb-5 mx-3"
                          active-class="white--text"
                          elevation="0"
                          :style="active ? 'border: 1px #537DA5 solid' : ''"
                        >
                          <p
                            class="ma-0 font-weight-medium"
                            :style="active ? 'color:white' : ''"
                          >
                            {{ item }}
                          </p>
                        </v-btn>
                      </v-item>
                    </v-item-group>
                  </div>
                </div>

                <!-- afternoon -->
                <div class="d-flex flex-column">
                  <p
                    class="font-weight-medium"
                    v-if="offline.afternoon.length > 0"
                  >
                    Chiều
                  </p>
                  <div class="d-flex flex-wrap justify-start">
                    <v-item-group v-model="offline_selected">
                      <v-item
                        v-for="(item, idx) in offline.afternoon"
                        :key="idx"
                        v-slot="{ active, toggle }"
                        :value="item"
                      >
                        <v-btn
                          :outlined="active ? false : true"
                          color="#537DA5"
                          @click="toggle"
                          class="mb-5 mx-3"
                          active-class="white--text"
                          elevation="0"
                          height="41"
                          width="102"
                          :style="active ? 'border: 1px #537DA5 solid' : ''"
                        >
                          <p
                            class="ma-0 font-weight-medium"
                            :style="active ? 'color:white' : ''"
                          >
                            {{ item }}
                          </p>
                        </v-btn>
                      </v-item>
                    </v-item-group>
                    <!-- booking -->
                  </div>
                </div>

                <v-btn
                  elevation="0"
                  color="#537DA5"
                  class="btn white--text"
                  @click="submit_offline_select_time"
                >
                  <p class="ma-0 font-weight-bold text-body-1">
                    Đặt lịch
                  </p>
                </v-btn>
              </v-card>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-card>
    </div>
  </v-card>
</template>

<script>
import Favorite from "../Component/favorite.vue";
import Question from "../Component/question.vue";
export default {
  components: {
    Favorite,
    Question
  },
  async mounted() {
    await this.get_service_select();
    await this.analyst_work_time_of_hospital(this.service_info.hospital);
    let currentDate = new Date().toJSON().slice(0, 10);
    this.date_pick = currentDate;
    this.get_service_schedule();
  },
  data() {
    return {
      tab: null,
      calander_tab: null,
      selected: null,
      service_info: {
        hospital: {
          name: "",
          address: {}
        },
        price: 0
      },
      hospital_work_time: {
        monday: [],
        tuesday: [],
        wednesday: [],
        thursday: [],
        friday: [],
        saturday: [],
        sunday: []
      },
      select_time_menu: false,
      is_loading_schedule: false,
      date_pick: "",
      online: {
        morning: [],
        afternoon: [],
        has_schedule: false,
        room: {
          id: "",
          name: "",
          link: null
        }
      },

      offline: {
        morning: [],
        afternoon: [],
        has_schedule: false,
        room: {
          id: "",
          name: "",
          link: null
        }
      },
      show_date: {
        date: "",
        month: "",
        year: ""
      },
      online_selected: null,
      offline_selected: null,
      keyFavorite: 0,
      keyQuestion: 0,
      numFavorite: 0,
      numQuestion: 0
    };
  },
  computed: {
    username() {
      return this.$store.getters["auth/username"];
    },
    titleFavorite() {
      if (this.tab !== 1) return "Đánh giá";
      return `Đánh giá (${this.numFavorite})`;
    },
    titleQuestion() {
      if (this.tab !== 2) return "Câu hỏi";
      return `Câu hỏi (${this.numQuestion})`;
    }
  },
  methods: {
    reloadFavorite() {
      this.keyFavorite++;
    },
    reloadQuestion() {
      this.keyQuestion++;
    },
    submit_select_time() {
      this.$router
        .push({ name: "Điền thông tin đặt lịch dịch vụ" })
        .catch(error => {
          if (error == null) {
            return;
          }
          if (error.name != "NavigationDuplicated") {
            throw error;
          }
        });
    },

    async get_service_select() {
      this.service_info = await this.$store.getters[
        "appointment/make_appointment_service_select"
      ];
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

    analyst_work_time_of_hospital(hospital) {
      //monday
      if (hospital.hospitalHour.mondayTime !== "") {
        let start_time = hospital.hospitalHour.mondayTime.split(" - ")[0];
        let end_time = hospital.hospitalHour.mondayTime.split(" - ")[1];
        let start_hour = parseInt(start_time.split(":")[0]);
        let end_hour = parseInt(end_time.split(":")[0]);
        for (; start_hour < end_hour; start_hour++) {
          let item = `${start_hour}:00 - ${start_hour + 1}:00`;
          if (item !== hospital.hospitalHour.mondayTimeBreak) {
            this.hospital_work_time.monday.push(item);
          }
        }
      }
      //tuesday
      if (hospital.hospitalHour.tuesdayTime !== "") {
        let start_time = hospital.hospitalHour.tuesdayTime.split(" - ")[0];
        let end_time = hospital.hospitalHour.tuesdayTime.split(" - ")[1];
        let start_hour = parseInt(start_time.split(":")[0]);
        let end_hour = parseInt(end_time.split(":")[0]);
        for (; start_hour < end_hour; start_hour++) {
          let item = `${start_hour}:00 - ${start_hour + 1}:00`;
          if (item !== hospital.hospitalHour.tuesdayTimeBreak) {
            this.hospital_work_time.tuesday.push(item);
          }
        }
      }
      //wednesday
      if (hospital.hospitalHour.wednesdayTime !== "") {
        let start_time = hospital.hospitalHour.wednesdayTime.split(" - ")[0];
        let end_time = hospital.hospitalHour.wednesdayTime.split(" - ")[1];
        let start_hour = parseInt(start_time.split(":")[0]);
        let end_hour = parseInt(end_time.split(":")[0]);
        for (; start_hour < end_hour; start_hour++) {
          let item = `${start_hour}:00 - ${start_hour + 1}:00`;
          if (item !== hospital.hospitalHour.wednesdayTimeBreak) {
            this.hospital_work_time.wednesday.push(item);
          }
        }
      }
      //thursday
      if (hospital.hospitalHour.thursdayTime !== "") {
        let start_time = hospital.hospitalHour.thursdayTime.split(" - ")[0];
        let end_time = hospital.hospitalHour.thursdayTime.split(" - ")[1];
        let start_hour = parseInt(start_time.split(":")[0]);
        let end_hour = parseInt(end_time.split(":")[0]);
        for (; start_hour < end_hour; start_hour++) {
          let item = `${start_hour}:00 - ${start_hour + 1}:00`;
          if (item !== hospital.hospitalHour.thursdayTimeBreak) {
            this.hospital_work_time.thursday.push(item);
          }
        }
      }
      //friday
      if (hospital.hospitalHour.fridayTime !== "") {
        let start_time = hospital.hospitalHour.fridayTime.split(" - ")[0];
        let end_time = hospital.hospitalHour.fridayTime.split(" - ")[1];
        let start_hour = parseInt(start_time.split(":")[0]);
        let end_hour = parseInt(end_time.split(":")[0]);
        for (; start_hour < end_hour; start_hour++) {
          let item = `${start_hour}:00 - ${start_hour + 1}:00`;
          if (item !== hospital.hospitalHour.fridayTimeBreak) {
            this.hospital_work_time.friday.push(item);
          }
        }
      }
      //saturday
      if (hospital.hospitalHour.saturdayTime !== "") {
        let start_time = hospital.hospitalHour.saturdayTime.split(" - ")[0];
        let end_time = hospital.hospitalHour.saturdayTime.split(" - ")[1];
        let start_hour = parseInt(start_time.split(":")[0]);
        let end_hour = parseInt(end_time.split(":")[0]);
        for (; start_hour < end_hour; start_hour++) {
          let item = `${start_hour}:00 - ${start_hour + 1}:00`;
          if (item !== hospital.hospitalHour.saturdayTimeBreak) {
            this.hospital_work_time.saturday.push(item);
          }
        }
      }
      //sunday
      if (hospital.hospitalHour.sundayTime !== "") {
        let start_time = hospital.hospitalHour.sundayTime.split(" - ")[0];
        let end_time = hospital.hospitalHour.sundayTime.split(" - ")[1];
        let start_hour = parseInt(start_time.split(":")[0]);
        let end_hour = parseInt(end_time.split(":")[0]);
        for (; start_hour < end_hour; start_hour++) {
          let item = `${start_hour}:00 - ${start_hour + 1}:00`;
          if (item !== hospital.hospitalHour.sundayTimeBreak) {
            this.hospital_work_time.sunday.push(item);
          }
        }
      }
    },

    get_service_schedule() {
      let date_array = this.date_pick.split("-");
      this.show_date.year = date_array[0];
      this.show_date.month = date_array[1];
      this.show_date.date = date_array[2];
      let date = new Date(this.date_pick);
      this.select_time_menu = false;
      this.refresh_schedule();
      let schedule = [];
      switch (date.getDay()) {
        case 0:
          schedule = this.hospital_work_time.sunday;
          break;
        case 1:
          schedule = this.hospital_work_time.monday;
          break;
        case 2:
          schedule = this.hospital_work_time.tuesday;
          break;
        case 3:
          schedule = this.hospital_work_time.wednesday;
          break;
        case 4:
          schedule = this.hospital_work_time.thursday;
          break;
        case 5:
          schedule = this.hospital_work_time.friday;
          break;
        case 6:
          schedule = this.hospital_work_time.saturday;
          break;
        default:
      }
      if (schedule != []) {
        this.online.has_schedule = true;
        this.offline.has_schedule = true;
        schedule.forEach(time_frame => {
          if (this.check_is_morning(time_frame)) {
            this.offline.morning.push(time_frame);
            this.online.morning.push(time_frame);
          } else {
            this.offline.afternoon.push(time_frame);
            this.online.afternoon.push(time_frame);
          }
        });
      }

      this.select_time_menu = false;
      this.is_loading_schedule = false;
    },
    check_is_morning(time) {
      let end_frame_time = time.split(" - ")[1];
      let hour = end_frame_time.split(":")[0];
      return parseInt(hour) <= 12;
    },
    refresh_schedule() {
      (this.online.morning = []),
        (this.online.afternoon = []),
        (this.online.has_schedule = false);
      (this.offline.morning = []),
        (this.offline.afternoon = []),
        (this.offline.has_schedule = false);
    },
    submit_online_select_time() {
      let is_login = this.$store.getters["auth/isLogin"];
      if (is_login) {
        if (this.online_selected != null) {
          this.$store.dispatch(
            "appointment/set_time_to_make_appointment_service",
            {
              time: this.online_selected,
              type: "ONLINE",
              date: this.date_pick,
              room: this.online.room
            }
          );
          this.$router
            .push({ name: "Điền thông tin đặt lịch dịch vụ" })
            .catch(error => {
              if (error == null) {
                return;
              }
              if (error.name != "NavigationDuplicated") {
                throw error;
              }
            });
        } else {
          this.$store.dispatch("snackbar/set_snackbar", {
            text: "Vui lòng chọn lịch khám",
            type: "warn"
          });
        }
      } else {
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Vui lòng đăng nhập trước khi đặt lịch hẹn",
          type: "warn"
        });
      }
    },
    submit_offline_select_time() {
      let is_login = this.$store.getters["auth/isLogin"];
      if (is_login) {
        if (this.offline_selected != null) {
          this.$store.dispatch(
            "appointment/set_time_to_make_appointment_service",
            {
              time: this.offline_selected,
              type: "OFFLINE",
              date: this.date_pick,
              room: this.offline.room
            }
          );
          this.$router
            .push({ name: "Điền thông tin đặt lịch dịch vụ" })
            .catch(error => {
              if (error == null) {
                return;
              }
              if (error.name != "NavigationDuplicated") {
                throw error;
              }
            });
        } else {
          this.$store.dispatch("snackbar/set_snackbar", {
            text: "Vui lòng chọn lịch khám",
            type: "warn"
          });
        }
      } else {
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Vui lòng đăng nhập trước khi đặt lịch hẹn",
          type: "warn"
        });
      }
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
</style>
