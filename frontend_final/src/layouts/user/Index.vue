<template>
  <v-app>
    <default-view />
  </v-app>
</template>

<script>
export default {
  name: "UserLayout",

  components: {
    DefaultView: () =>
      import(
        /* webpackChunkName: "default-view" */
        "./View"
      )
  },

  data: () => ({
    image: require("@/assets/img/home/home_content.png"),
    links: [
      {
        link_name: "Đặt lịch khám theo ngày",
        name: "ĐẶT LỊCH KHÁM THEO NGÀY"
      },
      {
        link_name: "Đặt lịch khám theo bác sĩ",
        name: "ĐẶT LỊCH KHÁM THEO BÁC SĨ"
      },
      {
        link_name: "Xem lịch sử cuộc hẹn",
        name: "LỊCH SỬ ĐẶT KHÁM"
      }
    ]
  }),
  methods: {
    login() {
      this.$router.push({ name: "Đăng nhập" });
    },
    signup() {
      this.$router.push({ name: "Đăng ký" });
    },
    ChangePage(item_name) {
      this.$router.push({ name: item_name });
    }
  }
};
</script>
