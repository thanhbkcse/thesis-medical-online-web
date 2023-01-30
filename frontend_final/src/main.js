import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import "./plugins";
import store from "./store";
import { sync } from "vuex-router-sync";
import lodash from "lodash";
import axios from "axios";

Vue.config.productionTip = false;
Vue.prototype._ = lodash;
Vue.prototype.axios = axios;
Vue.filter("empty", function(value) {
  return value || "——";
});
Vue.filter("phone", function(value) {
  if (value.length > 3) {
    value = "0" + value.substring(3, value.length);
  }
  return value;
});
sync(store, router);

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount("#app");
