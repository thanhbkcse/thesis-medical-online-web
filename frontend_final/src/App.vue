<template>
  <v-fade-transition mode="out-in">
    <router-view />
  </v-fade-transition>
</template>

<script>
// Styles
import "@/styles/overrides.sass";

export default {
  name: "App",
  metaInfo: {
    title: "Hệ thống hỗ trợ khám online",
    titleTemplate: "%s | Material Dashboard Free",
    htmlAttrs: { lang: "en" },
    meta: [
      { charset: "utf-8" },
      { name: "viewport", content: "width=device-width, initial-scale=1" }
    ]
  }
};
</script>
<style lang="scss">
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}
::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background-color: rgba(189, 189, 189, 0.87);
}
::-webkit-scrollbar-track {
  border-radius: 10px;
  background-color: rgb(241, 241, 241);
}
::-webkit-scrollbar-thumb:hover {
  background: rgb(168, 168, 168);
}
::-webkit-scrollbar-thumb:active {
  background: rgb(120, 120, 120);
}
</style>
