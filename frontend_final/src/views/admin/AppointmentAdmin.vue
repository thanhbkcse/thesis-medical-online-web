<template>
  <div class="content">
    <v-card>
      <v-data-table
        v-model="selected"
        show-select
        :headers="headers"
        :items="desserts"
        item-key="id"
        sort-by="profile.name"
        class="elevation-1"
        :search="search"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-btn color="primary" dark class="mb-2" @click="opennewApp">
              Thêm lịch hẹn
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

            <v-dialog
              v-model="dialogNewAppointment"
              max-width="700px"
              persistent
            >
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

                <v-card-text>
                  <v-form ref="form" max-width="700px">
                    <v-stepper v-model="e13" vertical>
                      <v-stepper-step step="1" @click="e13 = 1">
                        Thông tin bệnh nhân
                      </v-stepper-step>

                      <v-stepper-content step="1">
                        <v-btn color="primary"> Chọn hồ sơ đã có </v-btn>
                        <v-card color="white" class="mb-12" height="auto">
                          <v-text-field
                            label="Tên"
                            v-model="appointment.profile.name"
                          ></v-text-field>

                          <v-text-field
                            label="Dân tộc"
                            v-model="appointment.profile.folk"
                          ></v-text-field>

                          <p
                            style="
                              font-size: 17px;
                              color: rgba(0, 0, 0, 0.87);
                              width: auto;
                            "
                          >
                            Giới tính
                          </p>
                          <v-radio-group
                            v-model="appointment.profile.gender"
                            row
                          >
                            <v-radio
                              label="Nam"
                              value="Nam"
                              style="margin-left: 20px"
                            ></v-radio>
                            <v-radio label="Nữ" value="Nữ"></v-radio>
                          </v-radio-group>

                          <v-text-field
                            label="Địa chỉ"
                            v-model="appointment.profile.address"
                          ></v-text-field>

                          <v-text-field
                            label="Số điện thoại"
                            v-model="appointment.profile.phoneNumber"
                          ></v-text-field>

                          <v-menu
                            v-model="menu2"
                            :close-on-content-click="false"
                            :nudge-right="40"
                            transition="scale-transition"
                            offset-y
                            min-width="auto"
                          >
                            <template v-slot:activator="{ on, attrs }">
                              <v-text-field
                                v-model="appointment.profile.dob"
                                label="Ngày sinh"
                                prepend-icon="mdi-calendar"
                                readonly
                                v-bind="attrs"
                                v-on="on"
                              ></v-text-field>
                            </template>
                            <v-date-picker
                              v-model="appointment.profile.dob"
                              @input="menu2 = false"
                            ></v-date-picker>
                          </v-menu>

                          <v-text-field
                            label="Công việc hiện tại"
                            v-model="appointment.profile.job"
                          ></v-text-field>

                          <v-text-field
                            label="CMND"
                            v-model="appointment.profile.identityCard"
                          ></v-text-field>

                          <v-text-field
                            label="BHYT"
                            v-model="appointment.profile.healthInsurance"
                          ></v-text-field>

                          <v-text-field
                            label="Người giám hộ"
                            v-model="appointment.profile.protector"
                          ></v-text-field>
                        </v-card>

                        <v-btn color="primary" @click="e13 = 2">
                          Tiếp tục
                        </v-btn>
                      </v-stepper-content>

                      <v-stepper-step step="2" @click="e13 = 2">
                        Đặt lịch
                      </v-stepper-step>

                      <v-stepper-content step="2">
                        <v-card color="white" class="mb-12" height="auto">
                          <v-text-field
                            label="Chọn khoa khám"
                            v-model="appointment.doctor.specialty"
                          ></v-text-field>

                          <v-text-field
                            label="Chọn bác sĩ"
                            v-model="appointment.doctor.name"
                          ></v-text-field>

                          <v-text-field
                            label="Chọn ngày khám"
                            v-model="appointment.date"
                          ></v-text-field>

                          <v-text-field
                            label="Chọn thời gian"
                            v-model="appointment.time"
                          ></v-text-field>

                          <v-text-field
                            label="Phòng"
                            v-model="appointment.room"
                          ></v-text-field>

                          <v-text-field
                            label="Triệu chứng"
                            v-model="appointment.symptom"
                          ></v-text-field>

                          <v-text-field
                            label="Mô tả chi tiết triệu chứng"
                            v-model="appointment.description"
                          ></v-text-field>

                          <v-text-field
                            label="Thời gian diễn ra tình trạng trên"
                            v-model="appointment.timeSituation"
                          ></v-text-field>

                          <v-text-field
                            label="Đã tự điều trị"
                            v-model="appointment.selfTreatment"
                          ></v-text-field>

                          <v-text-field
                            label="File đính kèm"
                            v-model="appointment.files"
                          ></v-text-field>
                        </v-card>
                        <v-btn @click="e13 = 1"> Quay lại </v-btn>
                        <v-btn color="primary" @click="e13 = 3">
                          Tiếp tục
                        </v-btn>
                      </v-stepper-content>

                      <v-stepper-step step="3" @click="e13 = 3">
                        Xác nhận thông tin
                      </v-stepper-step>

                      <v-stepper-content step="3">
                        <v-card color="white" class="mb-12" height="auto">
                          <p style="text-align: center">
                            <b>Thông tin bệnh nhân</b>
                          </p>
                          <v-text-field
                            label="Họ và tên"
                            v-model="appointment.profile.name"
                            readonly
                          ></v-text-field>
                          <v-text-field
                            label="Số điện thoại"
                            v-model="appointment.profile.phoneNumber"
                            readonly
                          ></v-text-field>

                          <v-text-field
                            label="Ngày sinh"
                            v-model="appointment.profile.dob"
                            readonly
                          ></v-text-field>

                          <v-text-field
                            label="CMND"
                            v-model="appointment.profile.identityCard"
                            readonly
                          ></v-text-field>

                          <p style="text-align: center">
                            <b>Thông tin khám</b>
                          </p>
                          <v-text-field
                            label="Chuyên khoa"
                            v-model="appointment.doctor.specialty"
                            readonly
                          ></v-text-field>

                          <v-text-field
                            label="Bác sĩ"
                            v-model="appointment.doctor.name"
                            readonly
                          ></v-text-field>

                          <v-text-field
                            label="Ngày khám"
                            v-model="appointment.date"
                            readonly
                          ></v-text-field>

                          <v-text-field
                            label="Thời gian"
                            v-model="appointment.time"
                            readonly
                          ></v-text-field>

                          <v-text-field
                            label="Phòng"
                            v-model="appointment.room"
                            readonly
                          ></v-text-field>

                          <v-text-field
                            label="Triệu chứng"
                            v-model="appointment.symptom"
                            readonly
                          ></v-text-field>
                        </v-card>
                        <v-btn @click="e13 = 2"> Quay lại </v-btn>
                      </v-stepper-content>
                    </v-stepper>
                    <br />
                    <v-btn color="error" class="mr-4" @click="reset">
                      Reset Form
                    </v-btn>
                  </v-form>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="closegetnew">
                    Hủy
                  </v-btn>
                  <v-btn color="blue darken-1" text @click="save"> Lưu </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <!-- <template v-slot:[`item.actions`]="{ item }">
          <v-icon small class="mr-2" @click="editItem(item)">
            mdi-pencil
          </v-icon>
          <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
        </template> -->
      </v-data-table>

      <v-dialog v-model="dialogDelete" max-width="900px" persistent>
        <v-form>
          <v-card>
            <v-card-title class="text-h5">
              Bạn chắc chắn muốn xóa lịch hẹn? <br />
            </v-card-title>

            <v-card-text>
              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left">STT</th>
                      <th class="text-left">Tên bệnh nhân</th>
                      <th class="text-left">Số điện thoại</th>

                      <th class="text-left">Bác sĩ</th>
                      <th class="text-left">Chuyên khoa</th>
                      <th class="text-left">Ngày khám</th>
                      <th class="text-left">Phòng</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(appItem, k) in selected" :key="k">
                      <td>{{ k + 1 }}</td>
                      <td>{{ appItem.profile.name }}</td>
                      <td>{{ appItem.profile.phoneNumber }}</td>

                      <td>{{ appItem.doctor.name }}</td>
                      <td>{{ appItem.doctor.specialty }}</td>
                      <td>{{ appItem.date }}</td>
                      <td>{{ appItem.room }}</td>
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
      </v-dialog>

      <v-dialog v-model="dialogedit" max-width="600px" persistent> </v-dialog>
    </v-card>
  </div>
</template>

<script>
export default {
  data: () => ({
    menu2: false,
    nameTitle: "",
    e13: 1,
    search: "",
    selected: [],

    dialogNewAppointment: false,
    dialogDelete: false,
    dialogedit: false,
    indexdl: null,
    indexedit: null,
    headers: [
      {
        text: "Tên",
        align: "start",
        sortable: false,
        value: "profile.name",
      },
      { text: "Số điện thoại", value: "profile.phoneNumber", sortable: false },
      { text: "Bác sĩ", value: "doctor.name", sortable: false },
      { text: "Chuyên khoa", value: "doctor.specialty", sortable: false },
      { text: "Ngày khám", value: "date", sortable: false },
      { text: "Thời gian", value: "time", sortable: false },
      { text: "Phòng", value: "room", sortable: false },
      // { text: "Công cụ", value: "actions", sortable: false },
    ],
    desserts: [
      {
        id: 1,
        stt: 1,
        room: "201",
        profile: {
          id: 1,
          profileNumber: 1,
          name: "Nguyễn Ngọc Tân",
          address: "20 Lương thạnh",
          phoneNumber: "0123456798",
          dob: "2000-06-18",
          job: "SV",
          identityCard: "123456789",
          healthInsurance: "123456789",
          folk: "Kinh",
          gender: "Nam",
          protector: "Bố Mỹ",
        },
        doctor: {
          id: 1,
          name: "Cao nhân",
          specialty: "Khoa Nội",
          level: "bla",
        },
        date: "2022-04-05",
        time: "9:00-10:00",
        symptom: "Đau bụng",
        description: "Đau nhiều lần",
        timeSituation: "1 tuần",
        selfTreatment: "True",
        files: [],
      },

      {
        id: 2,
        stt: 2,
        room: "321",
        profile: {
          id: 2,
          profileNumber: 2,
          name: "Nguyễn thị nụ",
          address: "20 quảng bình",
          phoneNumber: "987654321",
          dob: "2000-06-11",
          job: "SV",
          identityCard: "987654321",
          healthInsurance: "987654312",
          folk: "Kinh",
          gender: "Nữ",
          protector: "Lam",
        },
        doctor: {
          id: 2,
          name: "Cao Bá quát",
          specialty: "Khoa Ngoại",
          level: "bla",
        },
        date: "2022-03-07",
        time: "10:00-11:00",
        symptom: "Đau bụng",
        description: "Đau nhiều lần",
        timeSituation: "2 tuần",
        selfTreatment: "True",
        files: [],
      },
    ],

    editedItem: {
      name: "",
      chuyenkhoa: "",
      phonenumber: "",
      startdate: "",
      email: "",
    },
    dlItem: {
      id: null,
      stt: null,
      room: null,
      profile: {
        id: null,
        profileNumber: null,
        name: null,
        address: null,
        phoneNumber: null,
        dob: null,
        job: null,
        identityCard: null,
        healthInsurance: null,
        folk: null,
        gender: null,
        protector: null,
      },
      doctor: {
        id: null,
        name: null,
        specialty: null,
        level: null,
      },
      date: null,
      time: null,
      symptom: null,
      description: null,
      timeSituation: null,
      selfTreatment: null,
      files: [],
    },
    appointment: {
      id: null,
      stt: null,
      room: null,
      profile: {
        id: null,
        profileNumber: null,
        name: null,
        address: null,
        phoneNumber: null,
        dob: null,
        job: null,
        identityCard: null,
        healthInsurance: null,
        folk: null,
        gender: null,
        protector: null,
      },
      doctor: {
        id: null,
        name: null,
        specialty: null,
        level: null,
      },
      date: null,
      time: null,
      symptom: null,
      description: null,
      timeSituation: null,
      selfTreatment: null,
      files: [],
    },
  }),

  watch: {},
  created() {},
  methods: {
    editItem() {
      this.nameTitle = "Chỉnh sửa lịch hẹn";
      this.dialogNewAppointment = true;

      this.appointment.id = this.selected[0].id;
      this.appointment.stt = this.selected[0].stt;
      this.appointment.room = this.selected[0].room;

      this.appointment.profile.id = this.selected[0].profile.id;
      this.appointment.profile.profileNumber =
        this.selected[0].profile.profileNumber;
      this.appointment.profile.name = this.selected[0].profile.name;
      this.appointment.profile.address = this.selected[0].profile.address;
      this.appointment.profile.phoneNumber =
        this.selected[0].profile.phoneNumber;
      this.appointment.profile.dob = this.selected[0].profile.dob;
      this.appointment.profile.job = this.selected[0].profile.job;
      this.appointment.profile.identityCard =
        this.selected[0].profile.identityCard;
      this.appointment.profile.healthInsurance =
        this.selected[0].profile.healthInsurance;
      this.appointment.profile.folk = this.selected[0].profile.folk;
      this.appointment.profile.gender = this.selected[0].profile.gender;
      this.appointment.profile.protector = this.selected[0].profile.protector;

      this.appointment.doctor.id = this.selected[0].doctor.id;
      this.appointment.doctor.name = this.selected[0].doctor.name;
      this.appointment.doctor.specialty = this.selected[0].doctor.specialty;
      this.appointment.doctor.level = this.selected[0].doctor.level;

      this.appointment.date = this.selected[0].date;
      this.appointment.time = this.selected[0].time;
      this.appointment.symptom = this.selected[0].symptom;
      this.appointment.description = this.selected[0].description;
      this.appointment.timeSituation = this.selected[0].timeSituation;
      this.appointment.selfTreatment = this.selected[0].selfTreatment;
      this.appointment.files = this.selected[0].files;
    },
    // deleteItem(item) {
    //   this.dialogDelete = true;
    //   this.indexdl = this.desserts.indexOf(item);

    //   this.dlItem.id = this.desserts[this.indexdl].id;
    //   this.dlItem.stt = this.desserts[this.indexdl].stt;
    //   this.dlItem.room = this.desserts[this.indexdl].room;

    //   this.dlItem.profile.id = this.desserts[this.indexdl].profile.id;
    //   this.dlItem.profile.profileNumber =
    //     this.desserts[this.indexdl].profile.profileNumber;
    //   this.dlItem.profile.name = this.desserts[this.indexdl].profile.name;
    //   this.dlItem.profile.address = this.desserts[this.indexdl].profile.address;
    //   this.dlItem.profile.phoneNumber =
    //     this.desserts[this.indexdl].profile.phoneNumber;
    //   this.dlItem.profile.dob = this.desserts[this.indexdl].profile.dob;
    //   this.dlItem.profile.job = this.desserts[this.indexdl].profile.job;
    //   this.dlItem.profile.identityCard =
    //     this.desserts[this.indexdl].profile.identityCard;
    //   this.dlItem.profile.healthInsurance =
    //     this.desserts[this.indexdl].profile.healthInsurance;
    //   this.dlItem.profile.folk = this.desserts[this.indexdl].profile.folk;
    //   this.dlItem.profile.gender = this.desserts[this.indexdl].profile.gender;
    //   this.dlItem.profile.protector =
    //     this.desserts[this.indexdl].profile.protector;

    //   this.dlItem.doctor.id = this.desserts[this.indexdl].doctor.id;
    //   this.dlItem.doctor.name = this.desserts[this.indexdl].doctor.name;
    //   this.dlItem.doctor.specialty =
    //     this.desserts[this.indexdl].doctor.specialty;
    //   this.dlItem.doctor.level = this.desserts[this.indexdl].doctor.level;

    //   this.dlItem.date = this.desserts[this.indexdl].date;
    //   this.dlItem.time = this.desserts[this.indexdl].time;
    //   this.dlItem.symptom = this.desserts[this.indexdl].symptom;
    //   this.dlItem.description = this.desserts[this.indexdl].description;
    //   this.dlItem.timeSituation = this.desserts[this.indexdl].timeSituation;
    //   this.dlItem.selfTreatment = this.desserts[this.indexdl].selfTreatment;
    //   this.dlItem.files = this.desserts[this.indexdl].files;
    // },
    deleteItemConfirm() {},

    closegetnew() {
      this.$refs.form.reset();
      this.dialogNewAppointment = false;
    },
    closeDelete() {
      this.dialogDelete = false;
    },
    save() {},

    reset() {
      this.$refs.form.reset();
    },
    opennewApp() {
      this.nameTitle = "Tạo lịch hẹn mới";
      this.dialogNewAppointment = true;
    },
  },
};
</script>
