<template>
  <v-navigation-drawer
    id="default-drawer"
    v-model="drawer"
    :dark="dark"
    :right="$vuetify.rtl"
    :src="drawerImage ? image : ''"
    :mini-variant.sync="mini"
    mini-variant-width="80"
    app
    width="260"
  >
    <template v-if="drawerImage" #img="props">
      <v-img :key="image" :gradient="gradient" v-bind="props" />
    </template>

    <div class="px-2">
      <default-drawer-header />

      <v-divider class="mx-3 mb-2" />

      <default-list :items="items" />
    </div>

    <div class="pt-12" />
  </v-navigation-drawer>
</template>

<script>
// Utilities
import { get, sync } from "vuex-pathify";

export default {
  name: "DefaultDrawer",

  components: {
    DefaultDrawerHeader: () =>
      import(
        /* webpackChunkName: "default-drawer-header" */
        "./widgets/DrawerHeader"
      ),
    DefaultList: () =>
      import(
        /* webpackChunkName: "default-list" */
        "./List"
      ),
  },
  data() {
    return {
      itemDefault: [
        {
          title: "Dashboard",
          icon: "mdi-view-dashboard",
          to: "/",
        },
        {
          title: "User Profile",
          icon: "mdi-account",
          to: "/components/profile/",
        },
        {
          title: "Regular Tables",
          icon: "mdi-clipboard-outline",
          to: "/tables/regular/",
        },
        {
          title: "Typography",
          icon: "mdi-format-font",
          to: "/components/typography/",
        },
        {
          title: "Icons",
          icon: "mdi-chart-bubble",
          to: "/components/icons/",
        },
        {
          title: "Google Maps",
          icon: "mdi-map-marker",
          to: "/maps/google/",
        },
        {
          title: "Notifications",
          icon: "mdi-bell",
          to: "/components/notifications/",
        },
        {
          title: "Notifications",
          icon: "mdi-bell",
          to: "/components/schedule",
        },
      ],

      itemDoctor: [
        {
          title: "Lịch trình",
          icon: "mdi-bell",
          to: "/doctor/doctor-schedule",
        },
        {
          title: "Bệnh án",
          icon: "mdi-bell",
          to: "/doctor/doctor-record",
        },
        {
          title: "Thông báo",
          icon: "mdi-bell",
          to: "/doctor/doctor-notification",
        },
        {
          title: "Quá trình chữa bệnh",
          icon: "mdi-bell",
          to: "/doctor/doctor-healingprocess",
        },
      ],

      itemAdmin: [
        {
          title: "Bác sĩ",
          icon: "mdi-bell",
          to: "/admin/doctor-admin",
        },
        {
          title: "Lịch hẹn",
          icon: "mdi-bell",
          to: "/admin/appointment-admin",
        },
        {
          title: "Bệnh nhân",
          icon: "mdi-bell",
          to: "/admin/patient-admin",
        },
        {
          title: "Bệnh án",
          icon: "mdi-bell",
          to: "/admin/record-admin",
        },
        {
          title: "Lịch trình",
          icon: "mdi-bell",
          to: "/admin/schedule-admin",
        },
        {
          title: "Tài khoản",
          icon: "mdi-bell",
          to: "/admin/account-admin",
        },
        {
          title: "Tạo phòng",
          icon: "mdi-bell",
          to: "/admin/room-admin",
        },
      ],
    };
  },
  computed: {
    ...get("user", ["dark", "gradient", "image"]),

    items() {
      let type = this.$store.getters["auth/types"];
      if (type[0].authority === "ROLE_DOCTOR") {
        return this.itemDoctor;
      }
      if (type[0].authority === "ROLE_ADMIN") {
        return this.itemAdmin;
      }
      return [];
    },
    // ...get('app', [
    //   'items',
    //   'version',
    // ]),
    ...sync("app", ["drawer", "drawerImage", "mini"]),
  },
};
</script>

<style lang="sass">
#default-drawer
  .v-list-item
    margin-bottom: 8px

  .v-list-item::before,
  .v-list-item::after
    display: none

  .v-list-group__header__prepend-icon,
  .v-list-item__icon
    margin-top: 12px
    margin-bottom: 12px
    margin-left: 4px

  &.v-navigation-drawer--mini-variant
    .v-list-item
      justify-content: flex-start !important
</style>
