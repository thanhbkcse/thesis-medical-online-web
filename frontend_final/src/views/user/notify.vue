<template>
  <div :key="componentKey">
    <v-card width="922px" elevation="1" class="pb-8">
      <div class="d-flex flex-column">
        <!-- header -->
        <v-card height="132" class="px-8 d-flex flex-row pt-5" elevation="0">
          <div>
            <p class="font-weight-bold mb-3" style="font-size: 24px">
              Thông báo
            </p>
            <p style="color: #667085" class="text-body-1">
              Theo dõi thông báo trong quá trình bạn sử dụng dịch vụ tại Blouse
              Care
            </p>
          </div>
        </v-card>
        <v-divider
          style="border-color: rgba(16, 24, 40, 0.03) !important"
        ></v-divider>
        <div class="d-flex justify-center mt-8 px-8">
          <v-progress-circular
            v-if="loading"
            indeterminate
            color="primary"
          ></v-progress-circular>
          <div
            class="d-flex flex-column"
            v-if="!notifications.length && !loading"
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
              Bạn chưa có thông báo
            </p>
            <p class="ml-8 d-flex justify-center" style="color: #667085">
              Xin lỗi chúng tôi không tìm thấy kết quả phù hợp với bạn.
            </p>
          </div>

          <div v-if="notifications.length && !loading">
            <v-card class="d-flex flex-column" width="100%" elevation="0">
              <div v-for="(notification, i) in notifications" :key="i">
                <v-alert
                  elevation="2"
                  colored-border
                  :color="notification.color"
                  border="left"
                  width="858px"
                  :icon="notification.icon"
                >
                  <v-row align="center">
                    <v-col class="d-flex grow">
                      <div>
                        <div class="d-flex">
                          <h4>{{ notification.title }}</h4>
                          <p
                            class="d-flex align-end pa-0 ma-0 text-body-2 ml-3"
                            style="color: grey; font-size: 75% !important;"
                          >
                            {{ getTime(notification.time) }}
                          </p>
                          <div
                            v-if="!notification.read"
                            style="position: absolute; right: 15px"
                          >
                            <v-icon color="blue"> mdi-circle-medium </v-icon>
                          </div>
                        </div>
                        <p>{{ notification.text }}</p>
                        <div v-if="notification.type == 'CANCEL_APPOINTMENT'">
                          Bạn có thể đặt khám với
                          <strong
                            >{{ notification.extraData.doctorLevel }}.
                            {{ notification.extraData.doctorName }}</strong
                          >
                          <v-tooltip bottom>
                            <template v-slot:activator="{ on, attrs }">
                              <a
                                class="ml-1"
                                v-bind="attrs"
                                v-on="on"
                                target="_blank"
                                style="text-decoration: none;"
                                :href="
                                  '/doctor-appointment-detail/?id=' +
                                    notification.extraData.doctorId
                                "
                                ><v-icon small color="primary"
                                  >mdi-open-in-new</v-icon
                                ></a
                              >
                            </template>
                            <span>Xem bác sĩ</span>
                          </v-tooltip>
                          với cùng chuyên khoa và khung giờ khám.
                        </div>
                      </div>
                    </v-col>
                    <v-col class="shrink" v-if="isAppoitmentType(notification)">
                      <v-btn
                        class="btn-not-transform"
                        rounded
                        color="primary"
                        outlined
                        @click="goToAppointment(notification)"
                        >Xem lịch khám</v-btn
                      >
                    </v-col>
                    <v-col class="shrink" v-if="isInvitationType(notification)">
                      <v-btn
                        class="btn-not-transform"
                        rounded
                        color="primary"
                        outlined
                        :loading="loadingInvite"
                        @click="acceptInvitation(notification)"
                        >Xác nhận</v-btn
                      >
                    </v-col>
                    <v-menu bottom left>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn icon v-bind="attrs" v-on="on">
                          <v-icon>mdi-dots-vertical</v-icon>
                        </v-btn>
                      </template>

                      <v-list>
                        <v-list-item
                          @click="deleteNotification(notification.id)"
                        >
                          <v-icon>mdi-delete</v-icon>
                          <v-list-item-title class="ml-2"
                            >Xóa thông báo</v-list-item-title
                          >
                        </v-list-item>
                        <v-list-item @click="readNotification(notification)">
                          <v-icon>mdi-eye</v-icon>
                          <v-list-item-title class="ml-2"
                            >Đánh dấu đã xem</v-list-item-title
                          >
                        </v-list-item>
                      </v-list>
                    </v-menu>
                  </v-row>
                </v-alert>
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
                  color="#537DA5"
                  v-model="page"
                  :length="totalPages"
                  :total-visible="7"
                ></v-pagination>
              </div>
            </v-card>
          </div>
        </div>
      </div>
    </v-card>
  </div>
</template>

<script>
const url = process.env.VUE_APP_ROOT_API;

export default {
  data() {
    return {
      loading: false,
      params: {
        filters: [],
        sorts: [],
        page: 0,
        size: 5
      },
      totalPages: 0,
      page: 1,
      notifications: [],
      componentKey: 0,
      loadingInvite: false
    };
  },
  watch: {
    page: {
      handler() {
        this.fetchNotifications();
      }
    }
  },
  async mounted() {
    await this.fetchNotifications();
  },
  computed: {
    token() {
      return this.$store.getters["auth/access_token"];
    }
  },
  methods: {
    async acceptInvitation(notification) {
      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${this.token}`
      };
      this.loadingInvite = true;
      const params = {
        username: notification.fromUser
      };
      await this.axios
        .post(`${url}/api/profiles/invite/accept`, params)
        .then(res => {
          this.$store.dispatch("snackbar/set_snackbar", {
            text: "Xác nhận lời mời thành công",
            type: "success"
          });
        })
        .catch(() => {})
        .finally(() => {
          this.loadingInvite = false;
        });
    },
    isAppoitmentType(notification) {
      return [
        "CANCEL_APPOINTMENT",
        "SUCCESS_APPOINTMENT",
        "FAIL_APPOINTMENT"
      ].includes(notification.type);
    },
    isInvitationType(notification) {
      return notification.type === "INVITE";
    },
    goToAppointment(notification) {
      this.$router.push(
        "/home/user/appointment-history/?id=" + notification.objectId
      );
      this.readNotification(notification);
    },
    async readNotification(item) {
      if (item.read) return;
      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${this.token}`
      };
      await this.axios.post(`${url}/api/notifications/read/${item.id}`);
      await this.fetchNotifications();
    },
    async fetchNotifications() {
      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${this.token}`
      };
      let payload = this._.cloneDeep(this.params);
      payload.page = this.page - 1;
      this.loading = true;
      const params = {
        size: payload.size,
        page: payload.page
      };
      const res = await this.axios
        .get(`${url}/api/notifications`, {
          params: params
        })
        .then(res => {
          this.notifications = res.data?.results;
          this.notifications.forEach(e => {
            if (e.type == "CANCEL_APPOINTMENT") {
              e["icon"] = "mdi-alert-circle-outline";
              e["color"] = "warning";
            } else if (e.type == "SUCCESS_APPOINTMENT") {
              e["icon"] = "mdi-calendar-check";
              e["color"] = "success";
            } else if (e.type == "FAIL_APPOINTMENT") {
              e["icon"] = "mdi-calendar-remove";
              e["color"] = "error";
            } else if (e.type == "INVITE") {
              e["icon"] = "fa fa-envelopes-bulk";
              e["color"] = "primary";
            } else {
              e["color"] = "#537DA5";
            }
          });
          this.totalPages = res.data?.meta?.totalPages;
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getTime(date) {
      if (typeof date !== "object") {
        date = new Date(date);
      }
      let hour = date.getHours();
      let minutes = date.getMinutes();
      let day = date.toLocaleDateString();
      return `${hour}:${minutes} - ${day}`;
    },
    async deleteNotification(id) {
      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${this.token}`
      };
      await this.axios.delete(`${url}/api/notifications/${id}`);
      await this.fetchNotifications();
    }
  }
};
</script>

<style scoped>
.btn-not-transform {
  text-transform: none;
}
</style>
