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
    <p class="text-center display-1 font-weight-bold blue--text my-8">
      Tất cả các khoa
    </p>

    <v-row justify="center"
      ><v-card
        class="mx-2"
        v-for="(image_card, index) in card_list_2"
        :key="index"
        width="200px"
        height="134px"
        hover="true"
        style="background-color: #e6f0f5"
        @click="getlink(index)"
      >
        <v-row justify="center" class="mt-2">
          <v-avatar height="48px" width="48px" color="white">
            <img :src="image_card.image" alt="" />
          </v-avatar>
        </v-row>
        <br />
        <v-row justify="center" class="mt-2">
          <strong
            class="font-weight-regular ml-2"
            style="color: #046792; font-size: 16px"
            >{{ image_card.label }}</strong
          >
        </v-row>
      </v-card>
    </v-row>

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

<script>
export default {
  data: () => ({
    links: [
      {
        label: "Trang chủ",
        name: "home",
      },
      {
        label: "Giới thiệu",
        name: "Giới thiệu",
      },
      {
        label: "Quy trình",
        name: "Quy trình",
      },
      {
        label: "Dịch vụ",
        name: "Dịch vụ",
      },
      {
        label: "Liên hệ",
        name: "Liên hệ",
      },
    ],
    card_list_2: [
      {
        image: require("@/assets/img/home/home_icon1.png"),
        label: "Chẩn đoán hình ảnh",
        name: "Chẩn đoán hình ảnh",
      },
      {
        image: require("@/assets/img/home/home_icon2.png"),
        label: "Cơ - Xương - Khớp",
        name: "Cơ - Xương - Khớp",
      },
      {
        image: require("@/assets/img/home/home_icon3.png"),
        label: "Nhi khoa",
        name: "Nhi khoa",
      },
      {
        image: require("@/assets/img/home/home_icon4.png"),
        label: "Sản - Phụ Khoa",
        name: "Sản - Phụ khoa",
      },
      {
        image: require("@/assets/img/home/home_icon5.png"),
        label: "Tai - Mũi - Họng",
        name: "Tai - Mũi - Họng",
      },
      {
        image: require("@/assets/img/home/home_icon6.png"),
        label: "Tim mạch",
        name: "Tim mạch",
      },
      {
        image: require("@/assets/img/home/home_icon7.png"),
        label: "Hô hấp",
        name: "Hô hấp và miễn dịch dị ứng lâm sàng",
      },
      {
        image: require("@/assets/img/home/home_icon8.png"),
        label: "Nội soi",
        name: "Nội soi",
      },
      {
        image: require("@/assets/img/home/home_icon9.png"),
        label: "Tiêu hóa gan mật",
        name: "Tiêu hóa gan mật",
      },
      {
        image: require("@/assets/img/home/home_icon10.png"),
        label: "Xét nghiệm",
        name: "Xét nghiệm",
      },
      {
        image: require("@/assets/img/home/home_icon11.png"),
        label: "Khoa ngoại",
        name: "Khoa ngoại",
      },
      {
        image: require("@/assets/img/home/home_icon12.png"),
        label: "Khoa tâm thần",
        name: "Tâm thần kinh",
      },
    ],
  }),
  methods: {
    login() {
      this.$router.push({ name: "Đăng nhập" });
    },
    signup() {
      this.$router.push({ name: "Đăng ký" });
    },
    getlink(index) {
      this.$router.push({ name: this.card_list_2[index].name });
    },
    getpage(name) {
      this.$router.push({ name: name });
    },
  },
};
</script>
