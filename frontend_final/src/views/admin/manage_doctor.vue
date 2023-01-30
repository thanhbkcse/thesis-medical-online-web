<template>
  <v-card class="d-flex flex-column pa-8">
    <!-- header -->
    <v-divider></v-divider>
    <v-card elevation="0 pa-5">
      <p class="font-weight-bold ma-0" style="font-size: 24px">
        Quản lý bác sĩ
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
        <v-btn icon @click="open_add_dialog()" class="mr-3">
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
        :items="doctor_list"
        :items-per-page="5"
        class="elevation-0"
        show-select
        :footer-props="{ itemsPerPageText: 'Số hàng' }"
      >
        <template v-slot:[`item.image`]="{ item }">
          <div class="d-flex justify-center">
            <v-avatar size="60" class="ma-2" elevation="0">
              <v-img :src="getImgOfDoctor(item)"></v-img>
            </v-avatar>
          </div>
        </template>
      </v-data-table>
    </v-card>
    <!-- some dialog here -->
    <!-- add/edit dialog -->
    <v-dialog width="1000" v-model="submit_dialog">
      <v-card class="pa-5 d-flex flex-column">
        <!-- header -->
        <p v-if="is_add_dialog" class="font-weight-bold text-h6 mb-5">Thêm</p>
        <p v-else class="font-weight-bold text-h6 mb-5">Chỉnh sửa</p>
        <!-- list field item -->
        <v-card class="d-flex flex-wrap justify-space-between" elevation="0">
          <!-- username field -->
          <v-card class="d-flex flex-column mb-5" width="45%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Tên tài khoản</p>
            <v-card outlined width="100%" elevation="0">
              <v-text-field
                :spellcheck="false"
                flat
                solo
                hide-details=""
              ></v-text-field>
            </v-card>
          </v-card>
          <!-- password field -->
          <v-card class="d-flex flex-column mb-5" width="45%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Mật khẩu</p>
            <v-card outlined width="100%" elevation="0">
              <v-text-field
                type="password"
                :spellcheck="false"
                flat
                solo
                hide-details=""
              ></v-text-field>
            </v-card>
          </v-card>

          <!-- hospital field -->
          <v-card class="d-flex flex-column mb-5" width="100%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Chọn bệnh viện</p>
            <v-card outlined width="100%" elevation="0" class="pa-1">
              <v-combobox
                :items="hospital_list"
                item-text="name"
                item-value="text"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
                hide-details=""
                :spellcheck="false"
              ></v-combobox>
            </v-card>
          </v-card>
          <!-- name field -->
          <v-card class="d-flex flex-column mb-5" width="45%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Tên bác sĩ</p>
            <v-card outlined width="100%" elevation="0">
              <v-text-field
                :spellcheck="false"
                flat
                solo
                hide-details=""
              ></v-text-field>
            </v-card>
          </v-card>

          <!-- avatar field -->
          <v-card
            width="45%"
            flat
            class="d-flex flex-row align-center mb-5"
            max-width="400px"
            max-height="172px"
          >
            <v-avatar size="64">
              <v-img contain :src="getImgOfDoctor({})" ref="avt" />
            </v-avatar>

            <v-card
              class="d-flex align-center justify-center ml-8 pa-2"
              width="100%"
              color="#FCFCFD"
              height="100%"
              elevation="0"
            >
              <div class="d-flex flex-column">
                <div class="d-flex justify-center">
                  <v-card
                    class="d-flex align-center justify-center pa-2"
                    rounded="circle"
                    color="#EEF2F6"
                  >
                    <v-icon
                      color="#537DA5"
                      large
                      @click="$refs.avt_file.click()"
                      >mdi-cloud-upload-outline
                    </v-icon>
                    <input
                      type="file"
                      ref="avt_file"
                      accept="image/*"
                      style="display: none"
                      @change="previewAvatarFiles"
                    />
                  </v-card>
                </div>
                <p class="d-flex font-weight-medium justify-center">
                  Nhấn để tải ảnh đại diện
                </p>
              </div>
            </v-card>
          </v-card>

          <!-- gender field -->
          <v-card class="d-flex flex-column mb-5" width="45%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Giới tính</p>
            <v-card outlined width="100%" elevation="0">
              <v-text-field
                :spellcheck="false"
                flat
                solo
                hide-details=""
              ></v-text-field>
            </v-card>
          </v-card>
          <!-- Dob field -->
          <v-card class="d-flex flex-column mb-5" width="45%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Ngày sinh</p>
            <v-card outlined width="100%" elevation="0">
              <v-text-field
                :spellcheck="false"
                flat
                solo
                hide-details=""
              ></v-text-field>
            </v-card>
          </v-card>
          <!-- email field -->
          <v-card class="d-flex flex-column mb-5" width="45%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Email</p>
            <v-card outlined width="100%" elevation="0">
              <v-text-field
                :spellcheck="false"
                flat
                solo
                hide-details=""
              ></v-text-field>
            </v-card>
          </v-card>
        </v-card>

        <!-- submit/cancle -->
        <v-card
          width="100%"
          class="d-flex flex-row justify-space-between mt-16"
          elevation="0"
        >
          <v-btn
            elevation="0"
            outlined
            color="#D0D5DD"
            @click="closeForm()"
            class="btn"
            ><p class="ma-0 font-weight-bold" style="color:#667085">
              Hủy
            </p></v-btn
          >
          <v-btn
            v-if="is_add_dialog"
            class="btn white--text"
            color="#537DA5"
            elevation="0"
            @click="createHospital()"
            >Thêm mới</v-btn
          >
          <v-btn
            v-else
            class="btn white--text"
            color="#537DA5"
            elevation="0"
            @click="editHospital()"
            >Lưu chỉnh sửa</v-btn
          >
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
      headers: [
        {
          text: "Ảnh đại diện",
          value: "image",
          sortable: false,
          align: "center"
        },
        {
          text: "Tên",
          align: "start",
          sortable: false,
          value: "name"
        },

        {
          text: "Bệnh viện",
          value: "hospital.name",
          sortable: false
        },
        {
          text: "Số lượt đăng ký",
          value: "registrationNumber",
          align: "center"
        },
        {
          text: "Đánh giá",
          value: "favorite",
          align: "center"
        }
      ],
      selected: [],
      doctor_list: [],
      //dialog
      hospital_list: [],
      submit_dialog: false,
      is_add_dialog: true,
      remove_dialog: false,
      doctor_form: {
        imageUrl: ""
      }
    };
  },
  created() {
    if (this.$store.getters["auth/isAdmin"]) {
      this.get_doctor_list();
    } else if (this.$store.getters["auth/isHospitalAdmin"]) {
      this.get_doctor_list_by_hospital();
    }
    this.get_hospital_list();
  },
  methods: {
    async get_doctor_list() {
      await axios.get(`${url}/api/doctors/all`).then(res => {
        this.doctor_list = res.data.results;
      });
    },
    getImgOfDoctor(doctor) {
      if (doctor.imageUrl != null) {
        return doctor.imageUrl;
      } else {
        return require("@/assets/img/home/appbar/avatar.png");
      }
    },

    open_add_dialog() {
      this.is_add_dialog = true;
      //this.refreshForm();
      this.submit_dialog = true;
    },
    open_edit_dialog() {
      this.is_add_dialog = false;
      this.submit_dialog = true;
    },
    previewAvatarFiles(event) {
      let file_1 = event.target.files[0];
      let reader = new FileReader();
      reader.addEventListener(
        "load",
        () => {
          // convert image file to base64 string
          this.doctor_form.imageUrl = reader.result;
        },
        false
      );
      if (file_1) {
        reader.readAsDataURL(file_1);
        this.avatar_img = file_1;
      } else {
        this.doctor_form.imageUrl = require("@/assets/img/home/appbar/avatar.png");
      }
    },
    async get_hospital_list() {
      await axios.get(`${url}/api/hospitals`).then(res => {
        this.hospital_list = res.data.results;
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
      this.doctor_list = this.$store.getters["hospital/doctor_by_hospital"];
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
</style>
