<template>
  <v-app>
    <default-bar />
    <default-view />
    <default-footer />
    <v-snackbar v-model="snackbar.showing" bottom :color="snackbar.color">
      {{ snackbar.text }}

      <template v-slot:action="{ attrs }">
        <v-icon
          small
          class="mr-3"
          v-bind="attrs"
          @click="snackbar.showing = false"
          >mdi-close</v-icon
        >
      </template>
    </v-snackbar>
  </v-app>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "HomeLayout",

  computed: {
    ...mapState("snackbar", ["snackbar"])
  },
  components: {
    DefaultBar: () =>
      import(
        /* webpackChunkName: "default-app-bar" */
        "./AppBar"
      ),
    DefaultDrawer: () =>
      import(
        /* webpackChunkName: "default-drawer" */
        "./Drawer"
      ),
    DefaultFooter: () =>
      import(
        /* webpackChunkName: "default-footer" */
        "./Footer"
      ),
    DefaultSettings: () =>
      import(
        /* webpackChunkName: "default-settings" */
        "./Settings"
      ),
    DefaultView: () =>
      import(
        /* webpackChunkName: "default-view" */
        "./View"
      )
  }
};
</script>
