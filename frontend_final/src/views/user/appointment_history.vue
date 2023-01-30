<template>
  <div>
    <v-card width="922px" elevation="1" class="pb-8">
      <v-expand-transition>
        <appointment-list
          :key="componentKey"
          v-show="!visible_appointment_detail"
          v-on:viewDetail="view_appointment_detail"
        ></appointment-list>
      </v-expand-transition>
      <appointment-detail
        :appointment="appointment_detail"
        v-on:appointmentDetailClose="close_view_appointment_detail"
        v-if="visible_appointment_detail"
        @reloadDetail="reloadDetail"
      ></appointment-detail>
    </v-card>
  </div>
</template>

<script>
import appointmentList from "./component/appointment_history/appointment_list.vue";
import appointmentDetail from "./component/appointment_history/appointment_detail.vue";
const url = process.env.VUE_APP_ROOT_API;
import axios from "axios";
export default {
  components: {
    appointmentList,
    appointmentDetail
  },
  async mounted() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get("id");
    if (id) {
      const res = await axios.get(`${url}/api/appointments/${id}`);
      this.appointment_detail = res.data.results;
      this.visible_appointment_detail = true;
    }
  },
  data() {
    return {
      visible_appointment_detail: false,
      appointment_detail: null,
      componentKey: 0
    };
  },
  methods: {
    reloadDetail() {
      this.visible_appointment_detail = false;
      this.componentKey += 1;
    },
    view_appointment_detail(appointment) {
      this.appointment_detail = appointment;
      this.visible_appointment_detail = true;
    },

    close_view_appointment_detail() {
      this.appointmentDetail = null;
      this.visible_appointment_detail = false;
    }
  }
};
</script>
