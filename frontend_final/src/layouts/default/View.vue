<template>
  <v-main>
    <v-container class="pa-0" fluid>
      <v-card width="100%" min-height="1000" class="pa-3" color="#eef2f6">
        <router-view :key="$route.path" />
      </v-card>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "DefaultView",
};
</script>
