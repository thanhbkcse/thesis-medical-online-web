<template>
  <v-main>
    <v-container fluid class="pa-0">
      <router-view :key="$route.path" />
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: "DefaultView"
};
</script>
