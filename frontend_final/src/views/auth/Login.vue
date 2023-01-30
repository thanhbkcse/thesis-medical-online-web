<template>
  <v-app>
    <!-- <div style="height: 100%">
      <v-container fluid full-height style="height: 100%">
        <v-row justify="center">
          <v-col class="d-flex justify-end" cols="12" sm="8" md="4">
            <v-card outlined class="ma-auto" style="width: 600px">
              <v-card-title></v-card-title>
              <v-form v-model="valid" ref="form" lazy-validation>
                <v-card-title>Đăng Nhập!</v-card-title>
                <v-card-text>
                  <span>Nhập thông tin của bạn để đăng nhập.</span>
                </v-card-text>
                <v-card-text>
                  <v-text-field
                    name="username"
                    label="Tên đăng nhập"
                    placeholder="Tên đăng nhập"
                    :rules="[v => !!v || 'Tên đăng nhập là bắt buộc!']"
                    id="username"
                    v-model="user.username"
                    dense
                  ></v-text-field>
                  <v-text-field
                    v-model="user.password"
                    name="password"
                    label="Mật khẩu"
                    placeholder="Mật khẩu"
                    :rules="[v => !!v || 'Mật khẩu là bắt buộc!']"
                    id="password"
                    :append-icon="isHidePassword ? 'mdi-eye-off' : 'mdi-eye'"
                    :type="isHidePassword ? 'password' : 'text'"
                    @click:append="() => (isHidePassword = !isHidePassword)"
                  ></v-text-field>

                  <v-row>
                    <v-col cols="6">
                      <v-checkbox
                        label="Nhớ tài khoản"
                        v-model="value"
                        value="value"
                        class="mt-0 pt-0"
                      ></v-checkbox>
                    </v-col>
                    <v-col cols="6">
                      <a class="float-right text-decoration-underline"
                        >Quên mật khẩu</a
                      >
                    </v-col>
                  </v-row>
                </v-card-text>
                <v-card-actions>
                  <v-btn color="primary" depressed block @click="submit"
                    >Đăng nhập</v-btn
                  >
                </v-card-actions>
              </v-form>
              <v-card-text>
                <div class="text-center">
                  Bạn chưa có tài khoản?
                  <a @click="signup">Đăng ký</a>
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </div> -->
    <div
      style="height: 100%; background:linear-gradient(268.58deg, #DCF5FF 2.14%, #FFFFFF 98.24%);"
    >
      <v-col cols="12" class="fill-height">
        <v-row class="fill-height">
          <v-col cols="6" class="my-auto">
            <div>
              <v-img
                src="@/assets/img/auth/image1-login.png"
                max-height="50%"
                max-width="75%"
                class="mx-auto rounded-xl"
              ></v-img>
            </div>
          </v-col>
          <v-col cols="6" class="my-auto">
            <v-text-field
              label="Solo"
              placeholder="Tên đăng nhập"
              solo
              height="60px"
              style="width: 467px;"
              :rules="[v => !!v || 'Tên đăng nhập là bắt buộc!']"
              rounded
              v-model="user.username"
            ></v-text-field>
            <v-text-field
              label="Solo"
              placeholder="Mật khẩu"
              solo
              height="60px"
              width="467px"
              style="width: 467px"
              type="password"
              :rules="[v => !!v || 'Mật khẩu là bắt buộc!']"
              v-model="user.password"
              rounded
            ></v-text-field>
            <v-row class="mt-8 ml-2" style="width: 467px">
              <v-checkbox
                label="Nhớ tài khoản"
                v-model="value"
                value="value"
                class="mt-0 pt-0"
              ></v-checkbox>
              <v-spacer></v-spacer>
              <a class="text-decoration-underline">Quên mật khẩu</a></v-row
            >
            <v-btn
              height="60"
              width="467"
              class="white--text"
              elevation="1"
              color="#046792"
              rounded
              style="font-size: 16px"
              @click="submit"
              >Đăng Nhập</v-btn
            >
            <v-row class="mt-8 ml-2">
              <div class="text-center">
                Bạn chưa có tài khoản?
                <a class="ml-4" @click="signup">Đăng ký</a>
              </div>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </div>
  </v-app>
</template>

<script>
export default {
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      isHidePassword: true,
      valid: true
    };
  },

  computed: {
    isAuthenticated: function() {
      return this.$store.getters.isAuthenticated;
    }
  },

  methods: {
    async submit() {
      //check login dev
      if (process.env.VUE_APP_LOGIN_DEV === "TRUE") {
        var typedev = process.env.VUE_APP_ROLE_DEV;
        if (typedev === "ROLE_USER") {
          this.$router.push({ name: "Đặt lịch khám theo ngày" });
        }
        if (typedev === "ROLE_DOCTOR") {
          this.$router.push({ name: "Lịch trình" });
        }
        if (typedev === "ROLE_ADMIN") {
          this.$router.push({ name: "Tạo phòng" });
        }
        return;
      }

      if (!this.$refs.form.validate()) {
        return;
      }
      const user = {
        username: this.user.username,
        password: this.user.password
      };
      await this.$store.dispatch("auth/login", user);

      if (this.$store.getters["auth/access_token"] != null) {
        console.log(this.$store.getters["auth/access_token"]);

        var type = this.$store.getters["auth/types"];
        if (type[0].authority === "ROLE_USER") {
          this.$router.push({ name: "Đặt lịch khám theo ngày" });
        }
        if (type[0].authority === "ROLE_DOCTOR") {
          this.$router.push({ name: "Lịch trình" });
        }
        if (type[0].authority === "ROLE_ADMIN") {
          this.$router.push({ name: "Tạo phòng" });
        }
      }
    },
    signup() {
      this.$router.push({ name: "Đăng ký" });
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
