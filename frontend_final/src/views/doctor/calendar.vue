<template>
  <v-card class="fill-height d-flex flex-column pa-8">
    <!-- header -->
    <v-card width="100%" class="mb-5" elevation="0">
      <!-- <v-btn
        class="mr-4 btn font-weight-medium"
        color="#667085"
        outlined
        @click.stop="add_dialog = true"
      >
        <v-icon>mdi-calendar-plus-outline</v-icon>
        Tạo lịch mới
      </v-btn>
      <v-btn
        class="mr-4 btn font-weight-medium"
        color="#537DA5"
        outlined
        @click.stop="edit_dialog = true"
      >
        <v-icon>mdi-calendar-edit-outline</v-icon>
        Chỉnh sửa lịch
      </v-btn> -->
      <div class="d-flex">
        <v-spacer></v-spacer>
        <v-btn
          class="white--text btn font-weight-medium text-body-1"
          color="#537DA5"
          @click.stop="absent_dialog = true"
        >
          <v-icon class="mr-2">mdi-calendar-remove</v-icon>
          Báo nghỉ
        </v-btn>
      </div>
      <!-- add dialog -->
      <v-dialog v-model="add_dialog" max-width="800">
        <v-toolbar color="#475467" height="64" class="white--text"
          >Tạo lịch làm việc</v-toolbar
        >
        <v-card class="d-flex flex-column" tile>
          <v-card height="600" class="pa-6 d-flex flex-column">
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
              <v-item-group v-model="selected" class="ma-0" multiple>
                <v-item
                  v-for="(item, idx) in affternoon_time"
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
              <p
                class="d-flex ma-0 align-center font-weight-medium text-body-2"
              >
                Số lương bệnh nhân mỗi khung giờ:
              </p>
              <v-card outlined class="ml-3" width="60">
                <v-combobox
                  :items="patient_count"
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
              <p
                class="d-flex ma-0 align-center font-weight-medium text-body-2"
              >
                Chọn phòng:
              </p>
              <v-card outlined width="50%">
                <v-combobox
                  :items="patient_count"
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
              <p
                class="d-flex ma-0 align-center font-weight-medium text-body-2"
              >
                Chọn loại hình khám:
              </p>
              <v-card outlined width="50%">
                <v-combobox
                  :items="service_type"
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
                @click="add_dialog = false"
                >Tạo mới</v-btn
              >
            </div>
          </v-card>
        </v-card>
      </v-dialog>

      <!-- edit dialog -->
      <v-dialog v-model="edit_dialog" max-width="800">
        <v-toolbar color="#537DA5" height="64" class="white--text">
          Chỉnh sửa lịch làm việc
        </v-toolbar>
        <v-card height="600" class="pa-6 d-flex flex-column" tile>
          <!-- select date -->
          <p class="mb-2 font-weight-medium text-body-2">Chọn ngày:</p>
          <v-card class="d-flex flex-row align-center" elevation="0">
            <v-card outlined width="100%" rounded="">
              <v-menu
                ref="edit_menu"
                v-model="edit_menu"
                :close-on-content-click="false"
                :return-value.sync="edit_date"
                transition="scale-transition"
                offset-y
                min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-combobox
                    flat
                    v-model="edit_date"
                    hide-details=""
                    readonly
                    v-bind="attrs"
                    v-on="on"
                    solo
                    append-icon="mdi-calendar-outline"
                  ></v-combobox>
                </template>
                <v-date-picker
                  v-model="edit_date"
                  no-title
                  scrollable
                  locale="vi"
                  color="#537DA5"
                >
                  <v-spacer></v-spacer>
                  <v-btn text color="primary" @click="edit_menu = false">
                    Cancel
                  </v-btn>
                  <v-btn
                    text
                    color="primary"
                    @click="$refs.edit_menu.save(edit_date)"
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
            <v-item-group v-model="selected" class="ma-0" multiple>
              <v-item
                v-for="(item, idx) in affternoon_time"
                :key="idx"
                v-slot="{ active, toggle }"
                :value="item"
              >
                <v-btn
                  :outlined="active ? false : true"
                  color="#6D91B3"
                  @click="toggle"
                  class="mb-5 mx-3"
                  active-class="white--text"
                  elevation="0"
                  :style="active ? 'border: 1px #6D91B3 solid' : ''"
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
                append-icon=""
                solo
                flat
                hide-details=""
                spellcheck="false"
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
                :items="patient_count"
                append-icon=""
                solo
                flat
                hide-details=""
                spellcheck="false"
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
                append-icon=""
                solo
                flat
                hide-details=""
                spellcheck="false"
                item-color="blue-grey darken-1"
              ></v-combobox>
            </v-card>
          </div>
          <!-- operate -->
          <v-spacer></v-spacer>
          <div class="d-flex flex-row">
            <v-btn
              class="btn mr-5 white--text"
              color="#98A2B3"
              elevation="0"
              @click="add_dialog = false"
              >Hủy</v-btn
            >
            <v-spacer></v-spacer>
            <v-btn
              class="btn white--text mr-3"
              color="#F04438"
              elevation="0"
              @click="add_dialog = false"
              >Xóa lịch</v-btn
            >
            <v-btn
              class="btn white--text"
              color="#6D91B3"
              elevation="0"
              @click="add_dialog = false"
              >Tạo mới</v-btn
            >
          </div>
        </v-card>
      </v-dialog>

      <!-- absent dialog-->
      <v-dialog v-model="absent_dialog" max-width="600">
        <v-toolbar color="#537DA5" height="64" class="white--text">
          Thông báo nghỉ
        </v-toolbar>
        <v-card height="500" class="pa-6 d-flex flex-column" tile>
          <!-- select date -->
          <p class="mb-2 font-weight-medium text-body-2">Chọn ngày:</p>
          <v-card class="d-flex flex-row align-center" elevation="0">
            <v-card outlined width="100%" rounded="">
              <v-menu
                ref="edit_menu"
                v-model="edit_menu"
                :close-on-content-click="false"
                :return-value.sync="edit_date"
                transition="scale-transition"
                offset-y
                min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-combobox
                    flat
                    v-model="edit_date"
                    hide-details=""
                    readonly
                    v-bind="attrs"
                    v-on="on"
                    solo
                    append-icon="mdi-calendar-outline"
                  ></v-combobox>
                </template>
                <v-date-picker
                  v-model="edit_date"
                  no-title
                  scrollable
                  locale="vi"
                  color="#537DA5"
                >
                  <v-spacer></v-spacer>
                  <v-btn text color="primary" @click="edit_menu = false">
                    Hủy
                  </v-btn>
                  <v-btn
                    text
                    color="primary"
                    @click="$refs.edit_menu.save(edit_date)"
                  >
                    Chọn
                  </v-btn>
                </v-date-picker>
              </v-menu>
            </v-card>
          </v-card>
          <!-- type  -->
          <!--seclect timeframe -->
          <p class="mt-2 mb-2 font-weight-medium text-body-2">Chọn ca:</p>
          <v-card outlined width="100%" rounded="">
            <v-select
              multiple
              :items="absentTimes"
              v-model="selected_absent_time"
              chips
              flat
              solo
              counter-value="3"
              hide-details=""
              deletable-chips
              small-chips
            >
              <template v-slot:prepend-item>
                <v-list-item ripple @mousedown.prevent @click="toggle">
                  <v-list-item-action>
                    <v-icon
                      :color="
                        selected_absent_time.length > 0 ? 'indigo darken-4' : ''
                      "
                    >
                      {{ icon }}
                    </v-icon>
                  </v-list-item-action>
                  <v-list-item-content>
                    <v-list-item-title>
                      Chọn tất cả
                    </v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
                <v-divider class="mt-2"></v-divider>
              </template>
            </v-select>
          </v-card>
          <p class="mt-2 mb-2 font-weight-medium text-body-2">Lý do nghỉ:</p>
          <v-card outlined class="mb-3">
            <v-textarea
              v-model="absentReason"
              spellcheck="false"
              solo
              height="148"
              flat
              hide-details
              placeholder="Vui lòng nhập lý do nghỉ lịch làm việc..."
            >
            </v-textarea>
          </v-card>
          <!-- operate -->
          <v-spacer></v-spacer>
          <div class="d-flex flex-row">
            <v-spacer></v-spacer>
            <v-btn
              class="btn mr-5 white--text"
              color="primary"
              outlined
              elevation="0"
              @click="cancelLeave()"
              >Hủy</v-btn
            >
            <v-btn
              class="btn white--text"
              color="#537DA5"
              elevation="0"
              @click="createLeave()"
              >Xác nhận</v-btn
            >
          </div>
        </v-card>
      </v-dialog>
    </v-card>
    <!-- calendar -->
    <v-card width="100%">
      <v-sheet height="64">
        <v-toolbar flat color="#314E6A">
          <v-btn outlined class="mr-4 btn" color="white" @click="setToday" c>
            Hôm nay
          </v-btn>
          <v-btn fab text small color="white" @click="prev">
            <v-icon small> mdi-chevron-left </v-icon>
          </v-btn>
          <v-btn fab text small color="white" @click="next">
            <v-icon small> mdi-chevron-right </v-icon>
          </v-btn>
          <v-toolbar-title v-if="$refs.calendar" class="white--text">
            {{ $refs.calendar.title }}
          </v-toolbar-title>

          <v-spacer></v-spacer>
          <v-menu bottom right>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                outlined
                color="white"
                v-bind="attrs"
                v-on="on"
                class="btn"
              >
                <span>{{ typeToLabel[type] }}</span>
                <v-icon right> mdi-menu-down </v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item-group v-model="type" color="#314E6A">
                <v-list-item key="day" value="day">
                  <v-list-item-title>Ngày</v-list-item-title>
                </v-list-item>
                <v-list-item key="week" value="week">
                  <v-list-item-title>Tuần</v-list-item-title>
                </v-list-item>
                <v-list-item key="month" value="month">
                  <v-list-item-title>Tháng</v-list-item-title>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-menu>
        </v-toolbar>
      </v-sheet>
      <v-sheet height="600">
        <v-calendar
          locale="vi-sa"
          ref="calendar"
          v-model="focus"
          :events="events"
          :event-color="getEventColor"
          :type="type"
          :short-intervals="false"
          :short-months="false"
          :short-weekdays="false"
          :event-text-color="get_event_text_color"
          @click:event="showEvent"
          @click:more="viewDay"
          @click:date="viewDay"
          :event-overlap-mode="'column'"
          @change="updateRange"
          event-more-text="Nhiều hơn"
          :event-overlap-threshold="30"
        >
          <template v-slot:event="{ event }">
            <div class="v-event-draggable">
              <strong>{{ formatEventTime(event.details.time_frame) }} </strong>
              {{ event.name }}
            </div>
          </template>
        </v-calendar>
        <v-menu
          v-model="selectedOpen"
          v-if="selectedOpen"
          :close-on-content-click="false"
          :activator="selectedElement"
          offset-x
        >
          <v-card min-width="800" flat>
            <v-toolbar :color="selectedEvent.color" dark>
              <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <div class="d-flex flex-column">
                <div class="d-flex flex-row">
                  <p class="font-weight-bold mr-2">Thời gian:</p>
                  <p>
                    {{ selectedEvent.details.time_frame }}
                  </p>
                </div>
                <div class="d-flex flex-row">
                  <p class="font-weight-bold mr-2">Phòng:</p>
                  <p>
                    {{ selectedEvent.details.room.name }}
                  </p>
                </div>
                <div class="d-flex flex-row">
                  <p class="font-weight-bold mr-2">
                    Só lượng bệnh nhân hiện tại:
                  </p>
                  <p>
                    {{ selectedEvent.details.current_count }}
                  </p>
                </div>
                <div class="d-flex flex-row">
                  <p class="font-weight-bold mr-2">
                    Só lượng bệnh nhân tối đa:
                  </p>
                  <p>{{ selectedEvent.details.max_count }}</p>
                </div>
              </div>
            </v-card-text>
          </v-card>
        </v-menu>
      </v-sheet>
    </v-card>
  </v-card>
</template>

<script>
import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;
export default {
  data: () => ({
    add_dialog: false,
    edit_dialog: false,
    absent_dialog: false,
    add_menu: false,
    edit_menu: false,
    add_date: [],
    edit_date: "",
    selected: [],
    affternoon_time: [
      "07:00 - 08:00",
      "08:00 - 09:00",
      "09:00 - 10:00",
      "10:00 - 11:00",
      "11:00 - 12:00",
      "13:00 - 14:00",
      "14:00 - 15:00",
      "15:00 - 16:00",
      "16:00 - 17:00",
      "17:00 - 18:00"
    ],
    patient_count: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
    patient_select: 1,
    service_type: ["Tại phòng khám", "Trực tuyến"],
    service_edit_selection: "Tại phòng khám",
    focus: "",
    type: "month",
    typeToLabel: {
      month: "Tháng",
      week: "Tuần",
      day: "Ngày"
    },
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],

    colors: {
      online: { color: "#98A2B3", text: "white" },
      offline_color: { color: "#6D91B3", text: "white" }
    },
    doctor_calendar: [],
    selected_absent_time: [],
    absentTimes: [],
    absentReason: ""
  }),
  watch: {
    edit_date: {
      handler(val) {
        if (val) this.getScheduleByDate();
      }
    }
  },
  computed: {
    userId() {
      return this.$store.getters["auth/userId"];
    },
    select_absent_all_time() {
      return this.selected_absent_time.length === this.absentTimes.length;
    },
    select_some_absent_time() {
      return (
        this.selected_absent_time.lengthh > 0 && !this.select_absent_all_time
      );
    },
    icon() {
      if (this.select_absent_all_time) return "mdi-close-box";
      if (this.select_some_absent_time) return "mdi-minus-box";
      return "mdi-checkbox-blank-outline";
    }
  },
  mounted() {
    this.$refs.calendar.checkChange();
  },

  created() {
    this.updateRange({
      start: { date: "2022-11-01" },
      end: { date: "2022-11-30" }
    });
  },
  methods: {
    formatEventTime(time) {
      let times = time.split("-");
      return times[0] || "";
    },
    cancelLeave() {
      this.edit_date = null;
      this.absentTimes = [];
      this.selected_absent_time = [];
      this.absentReason = "";
      this.absent_dialog = false;
    },
    getScheduleByDate() {
      let token = this.$store.getters["auth/access_token"];

      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const params = {
        date: this.edit_date,
        doctorId: this.userId
      };
      axios
        .get(`${url}/api/schedules`, {
          params: params
        })
        .then(res => {
          this.absentTimes = res.data.results[0]?.times;
        });
    },
    createLeave() {
      let token = this.$store.getters["auth/access_token"];

      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const data = {
        date: this.edit_date,
        doctorId: this.userId,
        times: this.selected_absent_time,
        reason: this.absentReason
      };
      axios.post(`${url}/api/leave-requests`, data).then(res => {
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Thông báo nghỉ thành công",
          type: "success"
        });
        this.cancelLeave();
      });
    },
    viewDay({ date }) {
      this.focus = date;
      this.type = "day";
    },
    getEventColor(event) {
      return event.color;
    },
    get_event_text_color(event) {
      return event.text_color;
    },
    setToday() {
      this.focus = "";
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        requestAnimationFrame(() =>
          requestAnimationFrame(() => (this.selectedOpen = true))
        );
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        requestAnimationFrame(() => requestAnimationFrame(() => open()));
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    async updateRange({ start, end }) {
      const events = [];

      let token = this.$store.getters["auth/access_token"];

      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const params = {
        dateStart: start.date,
        dateEnd: end.date,
        doctorId: this.userId
      };
      await axios
        .get(`${url}/api/doctor/schedules/date`, {
          params: params
        })
        .then(res => {
          this.doctor_calendar = res.data.results;
        });
      this.doctor_calendar.forEach(calendar => {
        calendar.times.forEach(time => {
          const time_frame = time.split(" - ");

          const start_string =
            parseInt(time_frame[0]) < 10
              ? `${calendar.date}T0${time_frame[0]}:00`
              : `${calendar.date}T${time_frame[0]}:00`;
          const end_string =
            parseInt(time_frame[1]) < 10
              ? `${calendar.date}T0${time_frame[1]}:00`
              : `${calendar.date}T${time_frame[1]}:00`;

          const color =
            calendar.type === "ONLINE"
              ? this.colors.online
              : this.colors.offline_color;
          let name =
            calendar.type === "ONLINE" ? "Tư vấn trực tuyến" : "Khám tại viện";
          events.push({
            name: name,
            start: new Date(start_string),
            end: new Date(end_string),
            color: color.color,
            text_color: color.text,
            timed: true,
            details: {
              time_frame: time,
              max_count: calendar.numOfAppointmentPerHour,
              current_count: 0,
              room: calendar.room
            }
          });
        });
      });
      this.events = events;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
    intervalFormatter(locale, getOptions) {
      return locale.time;
    },

    toggle() {
      this.$nextTick(() => {
        if (this.select_absent_all_time) {
          this.selected_absent_time = [];
        } else {
          this.selected_absent_time = this.absentTimes.slice();
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
