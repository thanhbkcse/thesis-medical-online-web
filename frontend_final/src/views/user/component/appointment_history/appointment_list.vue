<template>
  <div>
    <!-- header -->
    <v-card height="132" class="px-8" elevation="0">
      <p class="font-weight-bold mb-3 pt-5" style="font-size: 24px">
        Phiếu khám bệnh
      </p>
      <p style="color: #667085" class="text-body-1">
        Danh sách và thông tin các cuộc hẹn gặp khám của bạn
      </p>
    </v-card>
    <v-divider
      style="border-color: rgba(16, 24, 40, 0.03) !important"
    ></v-divider>
    <!-- body -->
    <div class="d-flex justify-center mt-8 px-8">
      <v-progress-circular
        v-if="loading"
        indeterminate
        color="primary"
      ></v-progress-circular>
      <div
        class="d-flex flex-column"
        v-if="!appointment_list.length && !loading"
      >
        <v-card
          width="100%"
          class="d-flex justify-center"
          elevation="0"
          style="background: none"
        >
          <v-img
            class="d-flex"
            src="@/assets/img/user/profile/lookingNotFound.png"
            width="183px"
            height="186px"
            contain
          ></v-img>
        </v-card>
        <p
          class="font-weight-bold ml-8 d-flex justify-center"
          style="font-size: 20px"
        >
          Bạn chưa có lịch sử khám
        </p>
        <p class="ml-8 d-flex justify-center" style="color: #667085">
          Xin lỗi chúng tôi không tìm thấy kết quả phù hợp với bạn.
        </p>
      </div>
      <v-card
        v-if="appointment_list.length && !loading"
        class="d-flex flex-column"
        width="100%"
        elevation="0"
      >
        <v-card
          width="100%"
          height="120"
          class="d-flex flex-row mb-5"
          v-for="appointment in appointment_list"
          :key="appointment.id"
          elevation="1"
        >
          <v-card
            elevation="0"
            tile
            width="20%"
            class="d-flex flex-column justify-center align-center"
          >
            <p class="ma-0 font-weight-bold text-body-1" style="color:#537DA5">
              {{ getDate(appointment.date) }}
            </p>
            <p class="ma-0 font-weight-medium text-body-2" style="color: grey">
              {{ appointment.time }}
            </p>
            <p
              class="ma-0 font-weight-medium text-body-2"
              style="color: #667085"
              v-if="appointment.status == 'PENDING'"
            >
              <v-chip text-color="white" color="orange" small class="ma-2">
                Chờ duyệt
              </v-chip>
            </p>
            <p
              v-else-if="appointment.status == 'CANCEL'"
              class="ma-0 font-weight-medium text-body-2"
              style="color: red"
            >
              <v-chip small class="ma-2" color="red" text-color="white">
                Đã hủy
              </v-chip>
            </p>
            <p
              class="ma-0 font-weight-medium text-body-2"
              style="color: #667085"
              v-else-if="appointment.status === 'PROCESS'"
            >
              <v-chip color="primary" small class="ma-2">
                Chưa tiến hành
              </v-chip>
            </p>

            <p
              class="ma-0 font-weight-medium text-body-2"
              style="color: #12B76A"
              v-else-if="appointment.status === 'COMPLETE'"
            >
              <v-chip small color="green" text-color="white" class="ma-2">
                Đã hoàn tất
              </v-chip>
            </p>
          </v-card>
          <v-divider class="mx-3" inset vertical></v-divider>
          <v-card class="d-flex flex-row align-center ml-3" elevation="0">
            <v-avatar size="40">
              <img src="@/assets/img/home/appbar/avatar.png" />
            </v-avatar>
            <div class="d-flex flex-column ml-3">
              <p class="ma-0 font-weight-bold text-body-1">
                {{ appointment.profile.lastName }}
                {{ appointment.profile.firstName }}
              </p>
              <p
                class="ma-0 font-weight-normal text-body-2"
                style="color: #667085"
              >
                {{ appointment.profile.isContactProfile ? "Chủ tài khoản" : appointment.profile.relationship }}
              </p>
              <p
                class="ma-0 font-weight-normal text-body-2 d-flex"
                style="color: #667085"
              >
                <v-icon small color="#537da5" class="mr-2"
                  >mdi-medical-bag</v-icon
                >
                {{ getServiceName(appointment) }}
              </p>
              <p
                class="ma-0 font-weight-normal text-body-2 d-flex"
                style="color: #667085"
              >
                <v-icon small color="#537da5" class="mr-2">mdi-domain</v-icon>
                {{
                  appointment.category === "DOCTOR"
                    ? appointment.doctor.hospital.name
                    : appointment.service.hospital.name
                }}
              </p>
            </div>
          </v-card>
          <v-spacer></v-spacer>
          <div class="d-flex align-center mr-4">
            <v-card
              class="d-flex flex-column justify-center"
              rounded="circle"
              color="#FCFCFD"
              width="32px"
              height="32px"
              elevation="0"
            >
              <v-icon small @click="view_appointment_detail(appointment)">
                mdi-chevron-right
              </v-icon>
            </v-card>
          </div>
        </v-card>
        <v-card
          style="background: none"
          width="100%"
          elevation="0"
          class="d-flex justify-center mt-10"
        >
          <div class="text-center">
            <v-pagination
              v-if="appointment_list.length"
              color="#537DA5"
              v-model="page"
              :length="totalPages"
              :total-visible="7"
            ></v-pagination>
          </div>
        </v-card>
      </v-card>
    </div>
  </div>
</template>

<script>
export default {
  async mounted() {
    await this.get_appointment_by_user();
  },
  data() {
    return {
      appointment_list: [],
      loading: false,
      params: {
        filters: [],
        sorts: [],
        page: 0,
        size: 5
      },
      totalPages: 0,
      page: 1
    };
  },
  watch: {
    page: {
      handler() {
        this.get_appointment_by_user();
      }
    }
  },
  methods: {
    getDate(date) {
      let date_array = date.split("-");
      return `${date_array[2]}/${date_array[1]}/${date_array[0]}`;
    },
    getServiceName(appointment) {
      if (appointment.category == "DOCTOR") {
        if (appointment.type == "ONLINE") {
          return `Tư vấn trực tuyến với ${appointment.doctor.level}. ${appointment.doctor.name}`;
        }
        return `Khám trực tiếp với ${appointment.doctor.level}. ${appointment.doctor.name}`;
      } else {
        if (appointment.type == "ONLINE") {
          return `Dịch vụ khám tại nhà ${appointment.service.name}`;
        }
        return `Dịch vụ khám tại viện ${appointment.service.name}`;
      }
    },
    view_appointment_detail(appointment) {
      this.$emit("viewDetail", appointment);
    },

    async get_appointment_by_user() {
      let payload = this._.cloneDeep(this.params);
      payload.page = this.page - 1;
      this.loading = true;
      const params = {
        token: this.$store.getters["auth/access_token"],
        size: payload.size,
        page: payload.page
      };
      const res = await this.$store.dispatch(
        "appointment/get_appointment_by_user",
        params
      );
      this.appointment_list = this.$store.getters[
        "appointment/user_appointment_list"
      ];
      this.totalPages = res.meta?.totalPages;
      this.loading = false;
    }
  }
};
</script>
<style scoped>
.btn-not-transform {
  text-transform: none;
}
</style>
