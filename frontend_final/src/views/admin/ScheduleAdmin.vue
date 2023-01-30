<template>
  <div class="content">
    <v-card>
      <v-data-table
        v-model="selected"
        show-select
        :headers="headers"
        :items="desserts"
        item-key="idschedule"
        class="elevation-1"
        :search="search"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-btn color="primary" dark class="mb-2" @click="opennewRecord">
              Thêm lịch trình
            </v-btn>
            <v-spacer></v-spacer>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Tìm kiếm"
              single-line
              hide-details
            ></v-text-field>
            <v-spacer></v-spacer>
            <div v-if="selected.length == 1">
              <v-btn
                tile
                color="success"
                style="margin-right: 20px"
                @click="editItem"
              >
                <v-icon left> mdi-pencil </v-icon>
                Chỉnh sửa
              </v-btn>
            </div>
            <div v-else>
              <v-btn tile color="success" style="margin-right: 20px" disabled>
                <v-icon left> mdi-pencil </v-icon>
                Chỉnh sửa
              </v-btn>
            </div>
            <div v-if="selected.length > 0">
              <v-btn tile color="error" @click="dialogDelete = true">
                <v-icon left> mdi-delete</v-icon>
                Xóa
              </v-btn>
            </div>
            <div v-else>
              <v-btn tile color="error" disabled>
                <v-icon left> mdi-delete</v-icon>
                Xóa
              </v-btn>
            </div>

            <v-dialog v-model="dialogNewrecord" max-width="1200px" persistent>
              <v-card>
                <v-card-title style="justify-content: space-between">
                  <span class="text-h5">{{ nameTitle }}</span>
                  <v-btn
                    color="#333"
                    small
                    style="color: #ffffff; padding: 0px"
                    @click="closegetnew"
                  >
                    Đóng
                  </v-btn>
                </v-card-title>
                <!-- form bệnh án ở đây ----------------------------------------------------------- -->
                <!-- form bệnh án ở đây ----------------------------------------------------------- -->
                <v-form ref="form">
                  <v-card-text>
                    <v-container>
                      <v-row no-gutters>
                        <v-col
                          cols="12"
                          sm="6"
                          md="3"
                          style="margin-right: 30px"
                        >
                          <v-date-picker
                            v-model="dates"
                            multiple
                            @click:date="newdate"
                            full-width
                          ></v-date-picker>
                        </v-col>
                        <v-col>
                          <v-autocomplete
                            label="Chọn bác sĩ"
                            v-model="schedule.doctor"
                            :items="doctorselect"
                            item-text="name"
                            item-value="id"
                            dense
                            filled
                          ></v-autocomplete>
                          <div v-if="dates.length > 0">
                            <h4>Danh sách các ngày đã chọn</h4>
                            <v-simple-table>
                              <template v-slot:default>
                                <thead>
                                  <tr>
                                    <th class="text-center">STT</th>
                                    <th class="text-center">Ngày</th>
                                    <th class="text-center">Thời gian</th>
                                    <th class="text-center">Phòng</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <tr
                                    v-for="(dateItem, k) in schedule.schedules"
                                    :key="k"
                                  >
                                    <td>{{ k + 1 }}</td>
                                    <td>{{ dateItem.date }}</td>
                                    <td>
                                      <v-autocomplete
                                        v-model="dateItem.times"
                                        :items="timeselect"
                                        dense
                                        multiple
                                        filled
                                      ></v-autocomplete>
                                    </td>
                                    <td>
                                      <v-autocomplete
                                        v-model="dateItem.room"
                                        :items="roomselect"
                                        dense
                                        filled
                                      ></v-autocomplete>
                                    </td>
                                  </tr>
                                </tbody>
                              </template>
                            </v-simple-table>
                          </div>
                          <div v-else><h4>Danh sách các ngày đã chọn</h4></div>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-text>

                  <!-- form bệnh án ở đây ----------------------------------------------------------- -->
                  <!-- form bệnh án ở đây ----------------------------------------------------------- -->

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="closegetnew">
                      Hủy
                    </v-btn>
                    <v-btn color="blue darken-1" text @click="save">
                      Lưu
                    </v-btn>
                  </v-card-actions>
                </v-form>
                {{ schedule }}
                <br />
                {{ selected }}
                <br />
                {{ desserts[0] }}
                <br />
                {{ copydesserts[0] }}
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
      </v-data-table>

      <!-- <v-dialog v-model="dialogDelete" max-width="700px" persistent>
        <v-form>
          <v-card>
            <v-card-title class="text-h5">
              Bạn chắc chắn muốn xóa bệnh án? <br />
            </v-card-title>
            <v-card-text>
              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left">STT</th>
                      <th class="text-left">Họ tên</th>
                      <th class="text-left">Ngày sinh</th>
                      <th class="text-left">Số điện thoại</th>
                      <th class="text-left">Bác sĩ</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(recordItem, k) in selected" :key="k">
                      <td>{{ k + 1 }}</td>
                      <td>{{ recordItem.appointment.profile.name }}</td>
                      <td>{{ recordItem.appointment.profile.dob }}</td>
                      <td>{{ recordItem.appointment.profile.phoneNumber }}</td>
                      <td>{{ recordItem.appointment.doctor.name }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete">Hủy</v-btn>
              <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                >Xóa</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-form>
      </v-dialog> -->
    </v-card>
  </div>
</template>

<script>
export default {
  data: () => ({
    dates: [],
    copydate: [],
    nameTitle: "",
    dem: 0,
    indexUnselected: 0,

    search: "",
    selected: [],

    dialogNewrecord: false,
    dialogDelete: false,

    indexdl: null,
    indexedit: null,
    doctorselect: [
      {
        name: "Dr.A",
        id: "1",
      },
      {
        name: "Dr.B",
        id: "2",
      },
      {
        name: "Dr.C",
        id: "3",
      },
    ],
    timeselect: [
      "7:00-8:00",
      "8:00-9:00",
      "9:00-10:00",
      "10:00-11:00",
      "11:00-12:00",
      "12:00-13:00",
      "14:00-15:00",
      "15:00-16:00",
    ],
    roomselect: ["H1", "H2", "H3", "H4", "H5"],
    headers: [
      {
        text: "Tên bác sĩ",
        align: "start",
        sortable: false,
        value: "doctor.name",
      },
    ],
    desserts: [
      {
        idschedule: "1",
        schedules: [
          {
            date: "2022-04-08",
            times: ["9:00-10:00", "10:00-11:00"],
            room: "H1",
          },
          {
            date: "2022-04-09",
            times: ["8:00-9:00", "10:00-11:00"],
            room: "H2",
          },
        ],

        doctor: {
          name: "Dr.A",
          id: "1",
        },
      },
      {
        idschedule: "2",
        schedules: [
          {
            date: "2022-04-10",
            times: ["9:00-10:00", "10:00-11:00"],
            room: "H3",
          },
          {
            date: "2022-04-07",
            times: ["8:00-9:00", "10:00-11:00"],
            room: "H4",
          },
        ],

        doctor: {
          name: "Dr.B",
          id: "2",
        },
      },
    ],

    copydesserts: [
      {
        idschedule: "1",
        schedules: [
          {
            date: "2022-04-08",
            times: ["9:00-10:00", "10:00-11:00"],
            room: "H1",
          },
          {
            date: "2022-04-09",
            times: ["8:00-9:00", "10:00-11:00"],
            room: "H2",
          },
        ],

        doctor: {
          name: "Dr.A",
          id: "1",
        },
      },
      {
        idschedule: "2",
        schedules: [
          {
            date: "2022-04-10",
            times: ["9:00-10:00", "10:00-11:00"],
            room: "H3",
          },
          {
            date: "2022-04-07",
            times: ["8:00-9:00", "10:00-11:00"],
            room: "H4",
          },
        ],

        doctor: {
          name: "Dr.B",
          id: "2",
        },
      },
    ],

    schedule: {
      idschedule: null,
      schedules: [],

      doctor: {
        name: null,
        id: null,
      },
    },
  }),

  watch: {},
  created() {},
  methods: {
    editItem() {
      this.nameTitle = "Chỉnh sửa lịch trình";
      this.schedule = this.selected[0];
      for (let i = 0; i < this.schedule.schedules.length; i++) {
        this.dates.push(this.schedule.schedules[i].date);
        this.copydate.push(this.schedule.schedules[i].date);
      }
      this.dialogNewrecord = true;
    },

    deleteItemConfirm() {},

    closegetnew() {
      this.dates = [];
      this.copydate = [];
      this.desserts[0] = this.copydesserts[0];
      this.schedule = this.copydesserts[0];
      this.selected[0] = this.schedule;

      this.dialogNewrecord = false;
    },
    closeDelete() {
      this.dialogDelete = false;
    },
    save() {},

    opennewRecord() {
      this.nameTitle = "Tạo lịch trình mới";
      this.dates = [];
      this.copydate = [];
      this.dialogNewrecord = true;
    },
    newdate() {
      if (this.dates.length > this.copydate.length) {
        this.schedule.schedules.push({
          date: this.dates[this.dates.length - 1],
          times: [],
          room: null,
        });
        this.copydate = this.dates;
      } else {
        for (let i = 0; i < this.copydate.length; i++) {
          for (let j = 0; j < this.dates.length; j++) {
            if (this.copydate[i] == this.dates[j]) {
              this.dem += 1;
            }
          }
          if (this.dem == 0) {
            this.indexUnselected = i;
          }
          this.dem = 0;
        }

        this.schedule.schedules.splice(this.indexUnselected, 1);
        this.copydate = this.dates;
      }
    },
  },
};
</script>
