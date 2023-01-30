<style lang="scss" scoped>
* {
  font-family: "Quicksand", sans-serif;
}
</style>

<template>
  <div class="content">
    <v-card>
      <v-card-title>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Tìm kiếm"
          single-line
          hide-details
        ></v-text-field>
        <v-spacer></v-spacer>
        <v-btn color="primary" tile @click="openDialogCreate()" class="mr-10"
          >Tạo tài khoản</v-btn
        >
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="lstAccountDoctor"
        :search="search"
        @click:row="selectedRow"
      ></v-data-table>
    </v-card>

    <v-dialog v-model="dialogCreate" persistent max-width="700px" scrollable>
      <v-card>
        <v-card-title
          style="justify-content: space-between"
          class="green darken-2"
        >
          <p class="display-1 font-weight-bold my-auto white--text">
            THÔNG TIN TÀI KHOẢN
          </p>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                icon
                @click="closeDialogCreate()"
                class="white--text"
                v-bind="attrs"
                v-on="on"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </template>
            <span>Đóng</span>
          </v-tooltip>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-form
              ref="form"
              v-model="valid"
              lazy-validation
              :readonly="isSelected"
            >
              <div class="row">
                <div class="col" cols="6">
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="accountDoctor.username"
                        label="Tên đăng nhập*"
                        placeholder=""
                        :rules="[(v) => !!v || 'Tên đăng nhập là bắt buộc!']"
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <span>{{ accountDoctor.username }}</span>
                  </v-tooltip>
                </div>
                <div class="col" cols="6">
                  <v-text-field
                    v-model="accountDoctor.password"
                    label="Mật khẩu*"
                    placeholder=""
                    :append-icon="isHidePassword ? 'mdi-eye-off' : 'mdi-eye'"
                    :type="isHidePassword ? 'password' : 'text'"
                    @click:append="() => (isHidePassword = !isHidePassword)"
                    :rules="[(v) => !!v || 'Mật khẩu là bắt buộc!']"
                  ></v-text-field>
                </div>
              </div>
              <div class="row">
                <div class="col" cols="6">
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="accountDoctor.name"
                        label="Họ và tên*"
                        placeholder=""
                        :rules="[(v) => !!v || 'Họ và tên là bắt buộc!']"
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <span>{{ accountDoctor.name }}</span>
                  </v-tooltip>
                </div>
                <div class="col" cols="6">
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="accountDoctor.email"
                        label="Email"
                        placeholder=""
                        :rules="[
                          (v) => /.+@.+\..+/.test(v) || 'E-mail không hợp lệ!',
                        ]"
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <span>{{ accountDoctor.email }}</span>
                  </v-tooltip>
                </div>
              </div>
              <div class="row">
                <div class="col" cols="6">
                  <v-menu
                    v-model="menuDOB"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                    :disabled="isSelected"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="accountDoctor.dob"
                        label="Ngày sinh"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker
                      v-model="accountDoctor.dob"
                      @input="menuDOB = false"
                    ></v-date-picker>
                  </v-menu>
                </div>
                <div class="col" cols="6">
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="accountDoctor.phoneNumber"
                        style="margin-top: 22px"
                        label="Số điện thoại"
                        placeholder=""
                        @keydown="onlyNumber"
                        @paste="onlyNumber"
                        dense
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <span>{{ accountDoctor.phoneNumber }}</span>
                  </v-tooltip>
                </div>
              </div>
              <div class="row">
                <div class="col" cols="6">
                  <v-radio-group v-model="accountDoctor.gender" row>
                    <v-radio label="Nam" value="Nam"></v-radio>
                    <v-radio label="Nữ" value="Nữ"></v-radio>
                  </v-radio-group>
                </div>
              </div>
              <div class="row">
                <div class="col" cols="12">
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="accountDoctor.address"
                        label="Địa chỉ"
                        placeholder=""
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <span>{{ accountDoctor.address }}</span>
                  </v-tooltip>
                </div>
              </div>

              <div class="row">
                <div class="col" cols="6">
                  <v-select
                    v-model="accountDoctor.specialty"
                    :items="specialties"
                    label="Chuyên khoa*"
                    :rules="[(v) => !!v || 'Chuyên khoa là bắt buộc!']"
                    placeholder=""
                    clearable
                  ></v-select>
                </div>
                <div class="col" cols="6">
                  <v-select
                    v-model="accountDoctor.level"
                    :items="levels"
                    label="Học vị"
                    placeholder=""
                    clearable
                  ></v-select>
                </div>
              </div>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            small
            color="grey darken-2"
            dark
            @click="closeDialogCreate"
            class="mr-2 px-5 py-4"
          >
            Đóng
          </v-btn>
          <v-btn
            small
            color="primary"
            @click="saveNewAccount"
            class="mr-2 px-5 py-4"
            depressed
            :disabled="!valid"
          >
            Lưu
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { onlyNumber } from "@/util/index";
export default {
  data() {
    return {
      headers: [
        {
          text: "Tên",
          align: "start",
          sortable: false,
          value: "name",
          class: "font-weight-bold  green darken-2 white--text",
        },
        {
          text: "Giới tính",
          value: "gender",
          sortable: false,
          class: "font-weight-bold  green darken-1 white--text",
        },
        {
          text: "Chuyên khoa",
          value: "specialty",
          sortable: false,
          class: "font-weight-bold  green darken-2 white--text",
        },
        {
          text: "Học vị",
          value: "level",
          sortable: false,
          class: "font-weight-bold  green darken-1 white--text",
        },
        {
          text: "Số điện thoại",
          value: "phoneNumber",
          sortable: false,
          class: "font-weight-bold  green darken-2 white--text",
        },
        {
          text: "Email",
          value: "email",
          sortable: false,
          class: "font-weight-bold  green darken-1 white--text",
        },
      ],
      lstAccountDoctor: [
        {
          name: "Nguyễn Ngọc Tân",
          gender: "Nam",
          username: "abc",
          password: "1234",
          email: "abc@gmail.com",
          address:
            "20/49 Hùng Vương, Tổ Lâm Văn Thạnh, P. 11 Tp. Đà Lạt Lâm Đồng",
          phoneNumber: "0123456789",
          dob: "2000-04-20",
          specialty: "Khoa Nhi",
          level: "BS",
          role: "ROLE_DOCTOR",
        },
        {
          name: "ABC",
          gender: "Nữ",
          username: "abc",
          password: "1234",
          email: "abc@gmail.com",
          address: "abc",
          phoneNumber: "0123456789",
          dob: "2000-04-20",
          specialty: "Khoa Nhi",
          level: "BS",
          role: "ROLE_DOCTOR",
        },
      ],
      accountDoctor: {},
      createAccDoctor: {
        name: undefined,
        gender: undefined,
        username: undefined,
        password: undefined,
        email: undefined,
        address: undefined,
        phoneNumber: undefined,
        dob: undefined,
        specialty: undefined,
        level: undefined,
        role: "ROLE_DOCTOR",
      },
      isSelected: false,
      valid: false,
      specialties: [],
      levels: ["BS", "TS.BS", "BS.CKI", "BS.CKII", "ThS.BS"],
      isHidePassword: true,
      activePicker: null,
      menuDOB: false,
      onlyNumber: onlyNumber,
      dialogCreate: false,
      search: "",
    };
  },
  methods: {
    openDialogCreate() {
      this.accountDoctor = JSON.parse(JSON.stringify(this.createAccDoctor));
      this.isSelected = false;
      this.dialogCreate = true;
    },
    saveNewAccount() {
      this.dialogCreate = false;
    },
    closeDialogCreate() {
      this.dialogCreate = false;
    },
    async getSpecialties() {
      axios.get(`${url}/api/specialties`).then((res) => {
        this.specialties = res.data.results;
      });
    },
    selectedRow(row) {
      this.accountDoctor = row;
      this.isSelected = true;
      this.dialogCreate = true;
    },
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.activePicker = "YEAR"));
    },
  },
};
</script>
