<template>
  <v-app>
    <div style="height: 100%">
      <v-container fluid full-height style="height: 100%">
        <v-row justify="center">
          <v-col class="d-flex justify-end" cols="12" sm="8" md="4">
            <v-card outlined class="ma-auto" style="width: 600px">
              <v-card-title></v-card-title>
              <v-form ref="form" v-model="valid" lazy-validation>
                <v-card-title>Đăng Ký</v-card-title>
                <v-card-text>
                  <span>Nhập thông tin của bạn để đăng ký.</span>
                </v-card-text>
                <v-card-text>
                  <v-text-field
                    label="Họ và tên*"
                    placeholder="Họ và tên"
                    v-model="signup.name"
                    :rules="[v => !!v || 'Họ và tên là bắt buộc!']"
                    dense
                  ></v-text-field>
                  <v-text-field
                    label="Tên đăng nhập*"
                    placeholder="Tên tài khoản"
                    :rules="[v => !!v || 'Tên đăng nhập là bắt buộc!']"
                    v-model="signup.username"
                  ></v-text-field>
                  <v-text-field
                    v-model="signup.password"
                    label="Mật khẩu*"
                    placeholder="Mật khẩu"
                    :append-icon="isHidePassword ? 'mdi-eye-off' : 'mdi-eye'"
                    :type="isHidePassword ? 'password' : 'text'"
                    @click:append="() => (isHidePassword = !isHidePassword)"
                    :rules="[v => !!v || 'Mật khẩu là bắt buộc!']"
                  ></v-text-field>
                  <v-text-field
                    label="Email"
                    placeholder="Email"
                    v-model="signup.email"
                    :rules="[
                      v => /.+@.+\..+/.test(v) || 'E-mail không hợp lệ!'
                    ]"
                  ></v-text-field>
                  <v-text-field
                    label="Địa chỉ"
                    placeholder="Địa chỉ"
                    v-model="signup.address"
                    append-icon="business"
                    dense
                  ></v-text-field>
                  <div class="d-flex">
                    <v-col cols="7" class="pl-0">
                      <v-text-field
                        style="margin-top: 22px"
                        label="Số điện thoại"
                        placeholder="Số điện thoại"
                        v-model="signup.phoneNumber"
                        @keydown="onlyNumber"
                        @paste="onlyNumber"
                        dense
                      ></v-text-field>
                    </v-col>
                    <v-col cols="5" class="pr-0">
                      <v-menu
                        ref="menu"
                        v-model="menu"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="signup.dob"
                            label="Ngày sinh"
                            append-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                          ></v-text-field>
                        </template>
                        <v-date-picker
                          v-model="signup.dob"
                          :active-picker.sync="activePicker"
                          :max="
                            new Date(
                              Date.now() -
                                new Date().getTimezoneOffset() * 60000
                            )
                              .toISOString()
                              .substr(0, 10)
                          "
                          min="1950-01-01"
                          @change="save"
                        ></v-date-picker>
                      </v-menu>
                    </v-col>
                  </div>
                  <v-select
                    :items="roles"
                    v-model="signup.role"
                    label="Loại"
                  ></v-select>
                  <div class="d-flex" v-if="signup.role === 'ROLE_DOCTOR'">
                    <v-col cols="8" class="ml-0 pl-0">
                      <v-select
                        :items="specialties"
                        v-model="signup.specialty"
                        label="Chuyên khoa*"
                        :rules="[v => !!v || 'Chuyên khoa là bắt buộc!']"
                        placeholder="Chuyên khoa"
                        clearable
                      ></v-select>
                    </v-col>
                    <v-col cols="4">
                      <v-select
                        :items="levels"
                        v-model="signup.level"
                        label="Học vị"
                        placeholder="Học vị"
                        clearable
                      ></v-select>
                    </v-col>
                  </div>
                </v-card-text>
                <v-card-actions>
                  <v-btn
                    color="primary"
                    depressed
                    block
                    @click="submit"
                    :disabled="!valid"
                    >Đăng ký</v-btn
                  >
                </v-card-actions>
              </v-form>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </v-app>
</template>

<script>
import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;
import { onlyNumber } from "@/util/index";
export default {
  data() {
    return {
      signup: {
        name: undefined,
        username: undefined,
        password: undefined,
        email: undefined,
        address: undefined,
        phoneNumber: undefined,
        dob: undefined,
        specialty: undefined,
        level: undefined,
        role: undefined
      },
      isHidePassword: true,
      roles: [
        { text: "User", value: "ROLE_USER" },
        { text: "Admin", value: "ROLE_ADMIN" },
        { text: "Doctor", value: "ROLE_DOCTOR" }
      ],
      levels: ["BS", "TS.BS", "BS.CKI", "BS.CKII", "ThS.BS"],
      specialties: [],
      rules: {
        unique: v => !!v || "Không được để trống"
      },
      valid: true,
      activePicker: null,
      menu: false,
      onlyNumber: onlyNumber
    };
  },
  mounted() {
    this.signup.role = "ROLE_USER";
    this.getSpecialties();
  },
  computed: {
    isAuthenticated: function() {
      return this.$store.getters.isAuthenticated;
    }
  },

  methods: {
    async submit() {
      if (!this.$refs.form.validate()) {
        return;
      }
      axios
        .post(`${url}/api/auth/signup`, this.signup)
        .then(() => {
          this.$router.push({ name: "Đăng nhập" });
        })
        .catch(() => {});
    },
    async getSpecialties() {
      axios.get(`${url}/api/specialties`).then(res => {
        this.specialties = res.data.results;
      });
    },
    save(date) {
      this.$refs.menu.save(date);
    }
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.activePicker = "YEAR"));
    }
  }
};
</script>
<style lang="scss" scoped>
html,
body {
  height: 100% !important;
  margin: 0;
}
.container {
  background-color: #dcf5ff;
}
.login-img {
  width: 500px;
  height: 455px;
}
a {
  color: #017acd !important;
}
</style>
