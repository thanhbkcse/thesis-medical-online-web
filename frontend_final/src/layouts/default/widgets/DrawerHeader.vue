<template>
  <v-list-item class="mb-0 justify-space-between pl-3 align-center py-2">
    <v-list-item-avatar>
      <v-img :src="require('@/assets/img/home/appbar/logo.png')" />
    </v-list-item-avatar>

    <v-list-item-content class="pl-2">
      <v-list-item-title>
        <!-- <strong class="mr-1 font-weight-black">VMD</strong>

        <span class="primary--text">FREE</span> -->
        <p class="text-body-1 font-weight-bold ma-0">Blouse Care</p>
      </v-list-item-title>
    </v-list-item-content>
  </v-list-item>
</template>

<script>
// Utilities
import { get } from "vuex-pathify";

export default {
  name: "DefaultDrawerHeader",

  computed: { version: get("app/version") }
};
</script>
