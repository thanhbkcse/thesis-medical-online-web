<template>
  <div class="content">
    <v-footer color="#DCF5FF" height="auto" :padless="padless" class="mb-10">
      <v-col cols="3">icon</v-col>
      <v-col cols="5">
        <v-row justify="center" no-gutters>
          <v-btn
            v-for="link in links"
            :key="link.label"
            color="#046792"
            text
            rounded
            class="my-2"
            @click="getpage(link.name)"
          >
            {{ link.label }}
          </v-btn>
        </v-row></v-col
      >
      <v-col cols="4">
        <v-row justify="end" no-gutters>
          <v-btn
            color="#046792"
            rounded
            class="my-2 white--text"
            @click="login"
          >
            Đăng nhập
          </v-btn>
          <v-btn
            color="#046792"
            rounded
            class="my-2 white--text"
            @click="signup"
          >
            Đăng ký
          </v-btn>
        </v-row></v-col
      >
    </v-footer>

    <v-footer class="mt-15">
      <v-row justify="center" class="mt-9 mb-9">
        <v-col cols="2 mr-5">
          <v-row justify="center "
            ><p class="body-1 font-weight-bold blue--text">Về trang web</p>
          </v-row>
          <v-row justify="center"
            ><p class="text-justify body-1">
              Được xây dựng bằng VueJs và tạo ra từ chính công sức của tập thể.
            </p></v-row
          >
        </v-col>

        <v-col cols="2">
          <v-row justify="center"
            ><p class="body-1 font-weight-bold blue--text">Ngôn ngữ sử dụng</p>
          </v-row>
          <v-row justify="center"><p class="text-justify body-1">Vue</p></v-row>
          <v-row justify="center"
            ><p class="text-justify body-1">HTML</p></v-row
          >
          <v-row justify="center"><p class="text-justify body-1">CSS</p></v-row>
          <v-row justify="center"><p class="text-justify body-1">Js</p></v-row>
        </v-col>
        <v-col cols="2">
          <v-row justify="center"
            ><p class="body-1 font-weight-bold blue--text">Đối tượng sử dụng</p>
          </v-row>
          <v-row justify="center"
            ><p class="text-justify body-1">Bệnh nhân</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">Quản lí</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">Bác sĩ</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">Nhân viên</p></v-row
          >
        </v-col>
        <v-col cols="2">
          <v-row justify="center "
            ><p class="body-1 font-weight-bold blue--text">Liên hệ</p>
          </v-row>
          <v-row justify="center"
            ><p class="text-justify body-1">Email: t3@gmail.com</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">SĐT: 0123456798</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">
              Địa chỉ: 22 abc Dĩ An Bình Dương
            </p></v-row
          >
        </v-col>
      </v-row>
    </v-footer>
  </div>
</template>

links:[ { label: "Trang chủ", name: "home" }, { label: "Giới thiệu", name: "Giới
thiệu", }, { label: "Quy trình", name: "Quy trình", }, { label: "Dịch vụ", name:
"Dịch vụ", }, { label: "Liên hệ", name: "Liên hệ", }, ], methods: { login() {
this.$router.push({ name: "Đăng nhập" }); }, signup() { this.$router.push({
name: "Đăng ký" }); }, getlink(index){ this.$router.push({ name:
this.card_list_2[index].name }); }, getpage(name){ this.$router.push({ name:
name }); } },
