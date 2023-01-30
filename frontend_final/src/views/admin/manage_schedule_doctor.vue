<template>
  <v-card class="d-flex flex-column pa-8">
    <!-- header -->
    <v-divider></v-divider>
    <v-card elevation="0 pa-5">
      <p class="font-weight-bold ma-0" style="font-size: 24px">
        Quản lý lịch của bác sĩ
      </p>
    </v-card>
    <v-divider class="mb-5"></v-divider>
    <!-- operation -->
    <v-card class="d-flex flex-row mb-5 justify-space-between" elevation="0">
      <v-card outlined width="30%">
        <v-text-field
          label="Tìm kiếm"
          prepend-inner-icon="mdi-magnify"
          solo
          flat
          hide-details=""
        ></v-text-field>
      </v-card>
      <v-card class="d-flex flex-row" elevation="0">
        <v-btn
          class="mr-3 white--text btn font-weight-medium text-body-1"
          elevation="0"
          color="#476D92"
          @click.stop="select_doctor_dialog = true"
        >
          <v-icon medium class="mr-2">mdi-doctor</v-icon>
          Chọn bác sĩ</v-btn
        >

        <v-btn icon @click.stop="add_dialog = true" class="mr-3">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-btn icon @click="open_edit_dialog()" class="mr-3">
          <v-icon>mdi-pencil-outline</v-icon>
        </v-btn>
        <v-btn icon class="mr-3" @click="remove_dialog = true">
          <v-icon>mdi-trash-can-outline</v-icon>
        </v-btn>
        <v-btn icon>
          <v-icon>mdi-filter-variant</v-icon>
        </v-btn>
      </v-card>
    </v-card>
    <!-- table data -->
    <v-card outlined>
      <v-data-table
        v-model="selected"
        :headers="headers"
        :items="listSchedule"
        :items-per-page="5"
        class="elevation-0"
        show-select
        :footer-props="{ itemsPerPageText: 'Số hàng' }"
        no-data-text="Chưa có thông tin"
      >
        <template v-slot:[`item.time`]="{ item }">
          {{ getTimeFrame(item) }}
        </template>
      </v-data-table>
    </v-card>
    <!-- dialog select doctor -->
    <v-dialog width="500" v-model="select_doctor_dialog">
      <v-card height="300" class="pa-5 d-flex flex-column">
        <p class="font-weight-bold text-h6 mb-5">
          Chọn bác sĩ xem lịch
        </p>
        <v-card outlined>
          <v-combobox
            v-model="doctor_select"
            flat
            solo
            hide-details=""
            :items="doctorList"
            item-text="name"
            :spellcheck="false"
          >
          </v-combobox>
        </v-card>
        <v-spacer></v-spacer>
        <div class="d-flex flex-row justify-space-between">
          <v-btn
            elevation="0"
            outlined
            color="#D0D5DD"
            @click="select_doctor_dialog = false"
            class="btn"
            ><p class="ma-0 font-weight-bold" style="color:#667085">
              Hủy
            </p></v-btn
          >
          <v-btn
            class="btn white--text"
            color="#537DA5"
            elevation="0"
            @click="getScheduleDoctorById"
            >Xác nhận</v-btn
          >
        </div>
      </v-card>
    </v-dialog>
    <!-- some dialog here -->
    <!-- add/edit dialog -->
    <v-dialog v-model="add_dialog" max-width="800">
      <v-toolbar color="#475467" height="64" class="white--text"
        >Tạo lịch làm việc</v-toolbar
      >
      <v-card class="d-flex flex-column" tile>
        <v-card height="650" class="pa-6 d-flex flex-column">
          <!-- select doctor -->
          <p class="mb-2 font-weight-medium text-body-2">Chọn bác sĩ:</p>
          <v-card outlined>
            <v-combobox
              v-model="create_doctor_select"
              flat
              solo
              hide-details=""
              :items="doctorList"
              item-text="name"
              :spellcheck="false"
            >
            </v-combobox>
          </v-card>
          <!-- select date -->
          <p class="mb-2 font-weight-medium text-body-2">Chọn ngày:</p>
          <v-card class="d-flex flex-row align-center" elevation="0">
            <v-icon @click="add_menu = true" class="mr-3"
              >mdi-calendar-outline</v-icon
            >
            <v-card outlined width="100%">
              <v-menu
                ref="add_menu"
                v-model="add_menu"
                :close-on-content-click="false"
                :return-value.sync="add_date"
                transition="scale-transition"
                offset-y
                min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-combobox
                    flat
                    v-model="add_date"
                    hide-details=""
                    multiple
                    chips
                    small-chips
                    readonly
                    v-bind="attrs"
                    v-on="on"
                    solo
                    append-icon=""
                  ></v-combobox>
                </template>
                <v-date-picker
                  v-model="add_date"
                  multiple
                  no-title
                  scrollable
                  locale="vi"
                  color="#537DA5"
                >
                  <v-spacer></v-spacer>
                  <v-btn text color="primary" @click="add_menu = false">
                    Cancel
                  </v-btn>
                  <v-btn
                    text
                    color="primary"
                    @click="$refs.add_menu.save(add_date)"
                  >
                    OK
                  </v-btn>
                </v-date-picker>
              </v-menu>
            </v-card>
          </v-card>
          <!--seclect timeframe -->
          <p class="mt-2 mb-2 font-weight-medium text-body-2">Chọn ca:</p>
          <div class="d-flex flex-wrap justify-start">
            <v-item-group v-model="create_time_select" class="ma-0" multiple>
              <v-item
                v-for="(item, idx) in time"
                :key="idx"
                v-slot="{ active, toggle }"
                :value="item"
              >
                <v-btn
                  :outlined="active ? false : true"
                  color="#667085"
                  @click="toggle"
                  class="mb-5 mx-3"
                  active-class="white--text"
                  elevation="0"
                  :style="active ? 'border: 1px #667085 solid' : ''"
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
          <!-- patient count  -->
          <div class="d-flex flex-row align-center">
            <p class="d-flex ma-0 align-center font-weight-medium text-body-2">
              Số lương bệnh nhân mỗi khung giờ:
            </p>
            <v-card outlined class="ml-3" width="60">
              <v-combobox
                :items="patient_count"
                v-model="create_patient_count"
                append-icon=""
                solo
                flat
                hide-details=""
                spellcheck="false"
                color="#667085"
                item-color="blue-grey darken-1"
              ></v-combobox>
            </v-card>
          </div>
          <div class="d-flex flex-column mt-2">
            <p class="d-flex ma-0 align-center font-weight-medium text-body-2">
              Chọn phòng:
            </p>
            <v-card outlined width="50%">
              <v-combobox
                :items="roomList"
                v-model="create_room_select"
                item-text="name"
                append-icon=""
                solo
                flat
                hide-details=""
                spellcheck="false"
                color="#667085"
                item-color="blue-grey darken-1"
              ></v-combobox>
            </v-card>
          </div>

          <!-- type  -->
          <div class="d-flex flex-column mt-2">
            <p class="d-flex ma-0 align-center font-weight-medium text-body-2">
              Chọn loại hình khám:
            </p>
            <v-card outlined width="50%">
              <v-combobox
                :items="service_type"
                item-text="name"
                v-model="create_service_type"
                append-icon=""
                solo
                flat
                hide-details=""
                spellcheck="false"
                color="#667085"
                item-color="blue-grey darken-1"
              ></v-combobox>
            </v-card>
          </div>
          <v-spacer></v-spacer>
          <div class="d-flex flex-row">
            <v-btn
              class="btn mr-5 white--text"
              color="#98A2B3"
              elevation="0"
              @click="add_dialog = false"
              >Hủy</v-btn
            >
            <v-btn
              class="btn white--text"
              color="#667085"
              elevation="0"
              @click="createScheduleDoctor"
              >Tạo mới</v-btn
            >
          </div>
        </v-card>
      </v-card>
    </v-dialog>
  </v-card>
</template>
<script>
import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;
export default {
  data() {
    return {
      select_doctor_dialog: false,
      add_dialog: false,
      add_menu: false,
      add_date: [],
      time: [
        "7:00 - 8:00",
        "8:00 - 9:00",
        "9:00 - 10:00",
        "10:00 - 11:00",
        "11:00 - 12:00",
        "13:00 - 14:00",
        "14:00 - 15:00",
        "15:00 - 16:00",
        "16:00 - 17:00",
        "17:00 - 18:00"
      ],
      create_time_select: [],
      service_type: [
        { name: "Tại phòng khám", type: "OFFLINE" },
        { name: "Trực tuyến", type: "ONLINE" }
      ],
      create_service_type: "",
      create_doctor_select: [],
      create_patient_count: "",
      create_room_select: "",
      patient_count: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      headers: [
        {
          text: "Ngày",
          align: "start",
          sortable: false,
          value: "date"
        },

        {
          text: "Khung giờ khám",
          value: "time",
          sortable: false
        },
        {
          text: "Loại hình khám",
          value: "type",
          sortable: false
        },
        {
          text: "Bác sĩ",
          value: "doctor.name",
          sortable: false
        },
        {
          text: "Bệnh viện",
          value: "doctor.hospital.name",
          sortable: false
        }
      ],
      selected: [],
      listSchedule: [],
      doctor_select: "",
      doctorList: [],
      roomList: [],
      params: {
        filters: [],
        sorts: [],
        page: 0,
        size: 1
      }
    };
  },

  created() {
    if (this.$store.getters["auth/isAdmin"]) {
      this.getAllDoctor();
    } else if (this.$store.getters["auth/isHospitalAdmin"]) {
      this.get_doctor_list_by_hospital();
    }
    this.getRoomList();
  },
  methods: {
    async getScheduleDoctorById() {
      let token = this.$store.getters["auth/access_token"];

      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const params = {
        dateStart: "2022-12-19",
        dateEnd: "2023-03-12",
        doctorId: this.doctor_select.id
      };
      await axios
        .get(`${url}/api/doctor/schedules/date`, {
          params: params
        })
        .then(res => {
          this.listSchedule = res.data.results;
        });

      this.select_doctor_dialog = false;
    },
    async createScheduleDoctor() {
      let type = this.create_service_type.type;
      let times = this.create_time_select;
      let roomid = this.create_room_select.id;
      let doctorid = this.create_doctor_select.id;
      let numOfAppointment = this.create_patient_count;
      let list_submit = [];
      this.add_date.forEach(date => {
        list_submit.push({
          type: type,
          date: date,
          times: times,
          room: {
            id: roomid
          },
          numOfAppointmentPerHour: numOfAppointment
        });
      });
      const param = {
        list: list_submit,
        doctor: {
          id: doctorid
        }
      };
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };

      await axios.post(`${url}/api/schedules`, param).then(res => {
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Tạo lịch thành công",
          type: "success"
        });
      });
      this.add_dialog = false;
    },
    async getAllDoctor() {
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };

      let params = this._.cloneDeep(this.params);
      params.page = 0;
      params.size = 100;

      await axios.post(`${url}/api/doctors/search`, params).then(res => {
        this.doctorList = res.data.results;
      });
    },
    getTimeFrame(schedule) {
      let time = schedule.times.join("; ");
      return time;
    },
    async getRoomList() {
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      await axios.get(`${url}/api/rooms`).then(res => {
        this.roomList = res.data.results;
      });
    },
    async get_doctor_list_by_hospital() {
      const param = {
        hospitalId: this.$store.getters["auth/hospitalId"],
        page: 0,
        size: 8
      };
      await this.$store.dispatch("hospital/get_doctor_by_hospital", param);
      //this.totalPages = res.meta?.totalPages;
      this.doctorList = this.$store.getters["hospital/doctor_by_hospital"];
    }
  }
};
</script>
<style scoped>
.btn {
  text-transform: none;
}
</style>
