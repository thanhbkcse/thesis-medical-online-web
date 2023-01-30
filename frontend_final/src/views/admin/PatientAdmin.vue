<template>
  <div class="content">
    <v-card>
      <v-data-table
        v-model="selected"
        show-select
        :headers="headers"
        :items="desserts"
        item-key="id"
        sort-by="name"
        class="elevation-1"
        :search="search"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-btn color="primary" dark class="mb-2" @click="opennewPantient">
              Thêm bệnh nhân
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

            <v-dialog v-model="dialogNewpatient" max-width="700px" persistent>
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
                    <v-row class="mb-6" no-gutters>
                      <v-col sm="6" md="5" lg="6">
                        <v-card-text>
                          <v-text-field
                            v-model="patient.name"
                            label="Họ và tên"
                          ></v-text-field>
                          <p
                            style="
                              font-size: 17px;
                              color: rgba(0, 0, 0, 0.87);
                              width: auto;
                              margin-bottom: 0px;
                            "
                          >
                            Giới tính
                          </p>
                          <v-radio-group
                            v-model="patient.gender"
                            row
                            style="margin-top: 0px"
                          >
                            <v-radio
                              label="Nam"
                              value="Nam"
                              style="margin-left: 20px"
                            ></v-radio>
                            <v-radio label="Nữ" value="Nữ"></v-radio>
                          </v-radio-group>

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
                                v-model="patient.dob"
                                label="Ngày sinh"
                                prepend-icon="mdi-calendar"
                                readonly
                                v-bind="attrs"
                                v-on="on"
                              ></v-text-field>
                            </template>
                            <v-date-picker
                              v-model="patient.dob"
                              @input="menu2 = false"
                            ></v-date-picker>
                          </v-menu>
                          <v-text-field
                            v-model="patient.phoneNumber"
                            label="Số điện thoại"
                          ></v-text-field>
                          <v-text-field
                            v-model="patient.address"
                            label="Địa chỉ"
                          ></v-text-field>
                        </v-card-text>
                      </v-col>
                      <v-col sm="6" md="5" offset-md="2" lg="6" offset-lg="0">
                        <v-card-text>
                          <v-text-field
                            v-model="patient.folk"
                            label="Dân tộc"
                          ></v-text-field>
                          <v-text-field
                            v-model="patient.job"
                            label="Công việc"
                          ></v-text-field>
                          <v-text-field
                            v-model="patient.identityCard"
                            label="CMND"
                          ></v-text-field>
                          <v-text-field
                            v-model="patient.healthInsurance"
                            label="Mã BHYT"
                          ></v-text-field>
                          <v-text-field
                            v-model="patient.protector"
                            label="Người giám hộ"
                          ></v-text-field>
                        </v-card-text> </v-col
                    ></v-row>
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

      <v-dialog v-model="dialogDelete" max-width="700px" persistent>
        <v-form>
          <v-card>
            <v-card-title class="text-h5">
              Bạn chắc chắn muốn xóa bệnh nhân? <br />
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
                      <th class="text-left">Người giám hộ</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(patientItem, k) in selected" :key="k">
                      <td>{{ k + 1 }}</td>
                      <td>{{ patientItem.name }}</td>
                      <td>{{ patientItem.dob }}</td>
                      <td>{{ patientItem.phoneNumber }}</td>
                      <td>{{ patientItem.protector }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>

              <!-- <v-text-field
                v-model="dlItem.name"
                label="Họ và tên"
                readonly
              ></v-text-field>
              <v-text-field
                v-model="dlItem.dob"
                label="Ngày sinh"
                readonly
              ></v-text-field>
              <v-text-field
                v-model="dlItem.phoneNumber"
                label="Số điện thoại"
                readonly
              ></v-text-field> -->
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

    search: "",
    selected: [],

    dialogNewpatient: false,
    dialogDelete: false,
    dialogedit: false,
    indexdl: null,
    indexedit: null,
    headers: [
      {
        text: "Tên",
        align: "start",
        sortable: false,
        value: "name",
      },
      { text: "Giới tính", value: "gender", sortable: false },
      { text: "Ngày sinh", value: "dob", sortable: false },
      { text: "Số điện thoại", value: "phoneNumber", sortable: false },
      { text: "Ngày bắt đầu", value: "", sortable: false },
    ],
    desserts: [
      {
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
      {
        id: 2,
        profileNumber: 2,
        name: "Nguyễn thị nụ",
        address: "20 quảng bình",
        phoneNumber: "987654321",
        dob: "2000-06-11",
        job: "SV",
        identityCard: "987654321",
        healthInsurance: "987654321",
        folk: "Kinh",
        gender: "Nữ",
        protector: "Mẹ Lam",
      },
    ],

    dlItem: {
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
    patient: {
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
  }),

  watch: {},
  created() {},
  methods: {
    editItem() {
      this.nameTitle = "Chỉnh sửa thông tin";
      this.dialogNewpatient = true;

      this.patient.id = this.selected[0].id;
      this.patient.profileNumber = this.selected[0].profileNumber;
      this.patient.name = this.selected[0].name;
      this.patient.address = this.selected[0].address;
      this.patient.phoneNumber = this.selected[0].phoneNumber;
      this.patient.dob = this.selected[0].dob;
      this.patient.job = this.selected[0].job;
      this.patient.identityCard = this.selected[0].identityCard;
      this.patient.healthInsurance = this.selected[0].healthInsurance;
      this.patient.folk = this.selected[0].folk;
      this.patient.gender = this.selected[0].gender;
      this.patient.protector = this.selected[0].protector;
    },
    // deleteItem(item) {
    //   this.dialogDelete = true;
    //   this.indexdl = this.desserts.indexOf(item);

    //   this.dlItem.id = this.desserts[this.indexdl].id;
    //   this.dlItem.profileNumber = this.desserts[this.indexdl].profileNumber;
    //   this.dlItem.name = this.desserts[this.indexdl].name;
    //   this.dlItem.address = this.desserts[this.indexdl].address;
    //   this.dlItem.phoneNumber = this.desserts[this.indexdl].phoneNumber;
    //   this.dlItem.dob = this.desserts[this.indexdl].dob;
    //   this.dlItem.job = this.desserts[this.indexdl].job;
    //   this.dlItem.identityCard = this.desserts[this.indexdl].identityCard;
    //   this.dlItem.healthInsurance = this.desserts[this.indexdl].healthInsurance;
    //   this.dlItem.folk = this.desserts[this.indexdl].folk;
    //   this.dlItem.gender = this.desserts[this.indexdl].gender;
    //   this.dlItem.protector = this.desserts[this.indexdl].protector;
    // },
    deleteItemConfirm() {},

    closegetnew() {
      this.$refs.form.reset();
      this.dialogNewpatient = false;
    },
    closeDelete() {
      this.dialogDelete = false;
    },
    save() {},

    reset() {
      this.$refs.form.reset();
    },
    opennewPantient() {
      this.nameTitle = "Tạo bệnh nhân mới";
      this.dialogNewpatient = true;
    },
  },
};
</script>
