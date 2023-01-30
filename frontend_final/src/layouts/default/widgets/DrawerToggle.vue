<template>
  <v-btn
    class="ml-3 mr-4"
    elevation="1"
    fab
    small
    @click="mini = !mini"
    color="#eef2f6"
  >
    <v-icon color="#537da5">
      {{ mini ? "mdi-format-list-bulleted" : "mdi-dots-vertical" }}
    </v-icon>
  </v-btn>
</template>

<script>
// Utilities
import { sync } from "vuex-pathify";

export default {
  name: "DefaultDrawerToggle",

  computed: {
    mini: sync("app/mini")
  }
};
</script>
