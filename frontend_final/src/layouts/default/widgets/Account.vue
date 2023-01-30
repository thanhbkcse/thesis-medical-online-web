<template>
  <v-menu
    bottom
    left
    min-width="200"
    offset-y
    origin="top right"
    transition="scale-transition"
  >
    <template v-slot:activator="{ attrs, on }">
      <v-btn
        class="mr-10 btn-not-hover"
        min-width="0"
        icon
        :ripple="false"
        v-bind="attrs"
        v-on="on"
      >
        <v-avatar size="40">
          <img src="@/assets/img/home/appbar/avatar.png" />
        </v-avatar>
      </v-btn>
    </template>

    <v-list :tile="false" flat nav>
      <template v-for="(p, i) in profile">
        <v-divider v-if="p.divider" :key="`divider-${i}`" class="mb-2 mt-2" />

        <app-bar-item v-else :key="`item-${i}`" to="/">
          <v-list-item-title v-text="p.title" @click="logout" />
        </app-bar-item>
      </template>
    </v-list>
  </v-menu>
</template>

<script>
export default {
  name: "DefaultAccount",

  data: () => ({
    profile: [
      { title: "Hồ sơ" },
      { title: "Cài đặt" },
      { divider: true },
      { title: "Đăng xuất" }
    ]
  }),
  methods: {
    async logout() {
      await this.$store.dispatch("auth/logout", {});
      this.$router.push({ name: "Trang chủ" }).catch(error => {
        if (error == null) {
          return;
        }
        if (error.name != "NavigationDuplicated") {
          throw error;
        }
      });
    }
  }
};
</script>
<style scoped>
.btn-not-hover:hover::before {
  opacity: 0 !important;
}
</style>
