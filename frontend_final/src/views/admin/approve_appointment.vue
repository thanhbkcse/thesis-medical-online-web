<template>
  <v-card class="d-flex flex-column pa-8">
    <!-- header -->
    <v-divider></v-divider>
    <v-card elevation="0 pa-5">
      <p class="font-weight-bold ma-0" style="font-size: 24px">
        Duyệt đơn khám tại viện
      </p>
    </v-card>
    <v-divider class="mb-5"></v-divider>

    <!-- table data -->
    <v-card outlined>
      <v-data-table
        v-model="selected"
        :headers="headers_appointment"
        :items="list_appointment"
        :single-select="true"
        :search="search"
        item-key="id"
        show-select
        class="elevation-1"
        checkbox-color="#3C5E7E"
        :loading="loading"
        :no-data-text="'Không có danh sách khám'"
        :footer-props="{
          'items-per-page-options': [10, 20, 50]
        }"
        @pagination="pagination = $event"
        :server-items-length="totalPages"
        @update:page="updatePage()"
      >
        <template v-slot:top>
          <div class="pa-6 d-flex flex-row align-center">
            <v-card outlined width="50%">
              <v-text-field
                v-model="search"
                label="Tìm kiếm"
                prepend-inner-icon="mdi-magnify"
                solo
                flat
                hide-details=""
              ></v-text-field>
            </v-card>
            <v-spacer></v-spacer>
            <v-btn
              class="mr-3 white--text btn font-weight-medium text-body-1"
              elevation="0"
              color="#476D92"
              :disabled="_.isEmpty(selected)"
              @click.stop="confirm_approve_dialog = true"
            >
              <v-icon medium class="mr-2">mdi-text-box-check-outline</v-icon>
              Duyệt</v-btn
            >
            <v-menu :close-on-content-click="false">
              <template v-slot:activator="{ on, attrs }">
                <v-icon v-bind="attrs" v-on="on" medium class="mr-2"
                  >mdi-filter-variant</v-icon
                >
              </template>
              <v-card width="300px">
                <div class="d-flex justify-center" style="color: #537DA5">
                  <p class="font-weight-bold mt-3">Lọc Kết Quả</p>
                </div>
                <v-divider></v-divider>
                <v-list>
                  <v-list-item class="mt-3">
                    <v-autocomplete
                      prepend-inner-icon="mdi-map-marker"
                      item-text="text"
                      item-value="text"
                      label="Địa Điểm"
                      clearable
                      dense
                      outlined
                      :menu-props="{ offsetY: true }"
                      placeholder="Tìm địa điểm"
                    ></v-autocomplete>
                  </v-list-item>
                  <div class="d-flex justify-center mt-3">
                    <v-btn
                      color="#537DA5"
                      elevation="0"
                      class="white--text btn font-weight-medium text-body-1"
                      width="90%"
                      style="margin: 0 auto"
                      @click="clearFilters()"
                    >
                      Bỏ lọc
                    </v-btn>
                  </div>
                </v-list>
              </v-card>
            </v-menu>
          </div>
        </template>
        <template v-slot:[`item.profile.fullName`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">
              {{ item.profile.lastName }} {{ item.profile.firstName }}
            </p>
          </div>
        </template>
        <template v-slot:[`item.status`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p
              class="ma-0 font-weight-medium text-body-2"
              style="color: #667085"
              v-if="item.status == 'PENDING'"
            >
              <v-chip text-color="white" color="orange" small>
                Chờ duyệt
              </v-chip>
            </p>
            <p
              v-else-if="item.status == 'CANCEL'"
              class="ma-0 font-weight-medium text-body-2"
              style="color: red"
            >
              <v-chip small color="red" text-color="white">
                Đã hủy
              </v-chip>
            </p>
            <p
              class="ma-0 font-weight-medium text-body-2"
              style="color: #667085"
              v-else-if="item.status === 'PROCESS'"
            >
              <v-chip color="primary" small>
                Chưa tiến hành
              </v-chip>
            </p>

            <p
              class="ma-0 font-weight-medium text-body-2"
              style="color: #12B76A"
              v-else-if="item.status === 'COMPLETE'"
            >
              <v-chip small color="green" text-color="white">
                Đã hoàn tất
              </v-chip>
            </p>
          </div>
        </template>
        <template v-slot:[`item.symptom`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p v-if="!item.symptom.length" class="ma-0">
              {{ item.symptom | empty }}
            </p>
            <p class="d-flex align-center ma-0" v-else>
              <v-menu :close-on-content-click="false">
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    v-bind="attrs"
                    v-on="on"
                    class="d-flex align-center"
                    meidum
                    color="#537da5"
                    >mdi-comment-text-outline</v-icon
                  >
                </template>
                <v-card max-width="300px">
                  <v-list>
                    <v-list-tile-content>
                      <p class="pa-3">{{ item.symptom }}</p>
                    </v-list-tile-content>
                  </v-list>
                </v-card>
              </v-menu>
            </p>
          </div>
        </template>
        <template v-slot:[`item.profile.dob`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">{{ convert_date(item.profile.dob) }}</p>
          </div>
        </template>
        <template v-slot:[`item.profile.phone`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">{{ item.profile.phone | phone }}</p>
          </div>
        </template>
        <template v-slot:[`item.date`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">{{ convert_date(item.date) }}</p>
          </div>
        </template>
        <template v-slot:[`item.room`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">{{ item.room.name }}</p>
            <v-icon v-if="item.type === 'ONLINE'" class="ml-2"
              >mdi-video-outline</v-icon
            >
          </div>
        </template>
      </v-data-table>
    </v-card>
    <v-dialog v-model="confirm_approve_dialog" width="408px">
      <v-card height="192px" class="d-flex flex-column">
        <div class="d-flex flex-column mt-8 ml-8">
          <p style="font-size: 24px" class="font-weight-bold">
            Xác nhận duyệt
          </p>
          <p style="color: #667085">
            Bạn có muốn duyện lịch hẹn khám này không
          </p>
        </div>
        <div class="d-flex flex-row justify-space-between ml-8 mr-8">
          <v-btn
            class="btn font-weight-medium"
            width="160px"
            outlined
            color="primary"
            @click="confirm_approve_dialog = false"
            :disabled="loadingConfirm"
            >Huỷ bỏ</v-btn
          >
          <v-btn
            class="btn white--text font-weight-medium"
            width="160px"
            color="#537DA5"
            elevation="0"
            @click="postApproveAppointment"
            :loading="loadingConfirm"
            >Xác nhận</v-btn
          >
        </div>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;
export default {
  data() {
    return {
      confirm_approve_dialog: false,
      loadingConfirm: false,
      headers_appointment: [
        {
          text: "Họ và tên",
          align: "start",
          sortable: false,
          value: "profile.fullName",
          filterable: true
        },
        { text: "Tình trạng", value: "status", sortable: false },
        { text: "Triệu chứng", value: "symptom", sortable: false },
        { text: "Ngày sinh", value: "profile.dob", sortable: false },
        { text: "Số điện thoại", value: "profile.phone", sortable: false },
        { text: "Ngày khám", value: "date", sortable: false },
        { text: "Khung giờ khám", value: "time", sortable: false },
        { text: "Phòng", value: "room", sortable: false }
      ],
      loading: false,
      selected: [],
      list_appointment: [],
      params: {
        filters: [],
        sorts: [],
        page: 0,
        size: 1
      },
      totalPages: 0,
      pagination: {
        itemsPerPage: 10,
        page: 1
      },
      search: ""
    };
  },
  watch: {
    "pagination.page": {
      handler(val, newVal) {
        if (val != newVal) this.get_appointment();
      },
      deep: true
    },
    "pagination.itemsPerPage": {
      handler(val, newVal) {
        if (val != newVal) this.get_appointment();
      },
      deep: true
    }
  },

  async mounted() {
    await this.get_appointment();
  },

  methods: {
    async get_appointment() {
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const cancelToken = axios.CancelToken.source().token;
      let params = this._.cloneDeep(this.params);
      params.page = this.pagination.page - 1;
      params.size = this.pagination.itemsPerPage;

      params.filters.push({
        key: "status",
        operator: "IN",
        field_type: "STATUS",
        values: ["PENDING", "PROCESS"]
      });
      this.loading = true;
      await axios
        .post(`${url}/api/doctor/appointments/search`, params, {
          cancelToken
        })
        .then(res => {
          this.list_appointment = res.data.results;
          this.totalPages = res.data?.meta?.totalPages;
        })
        .finally(() => {
          this.loading = false;
        });
    },
    convert_date(time) {
      let date = new Date(time);
      return (
        (date.getDate() > 9 ? date.getDate() : "0" + date.getDate()) +
        "/" +
        (date.getMonth() > 8
          ? date.getMonth() + 1
          : "0" + (date.getMonth() + 1)) +
        "/" +
        date.getFullYear()
      );
    },
    async postApproveAppointment() {
      let appointment = this.selected[0];
      console.log(appointment);
      appointment.status = "PROCESS";
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };

      await axios.patch(`${url}/api/appointments`, appointment).then(res => {
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Duyệt đơn thành công",
          type: "success"
        });
      });
      await this.get_appointment();
      this.confirm_approve_dialog = false;
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
.v-data-table > .v-data-table__wrapper > table > tbody > tr > th,
.v-data-table > .v-data-table__wrapper > table > thead > tr > th,
.v-data-table > .v-data-table__wrapper > table > tfoot > tr > th {
  font-size: 20px !important;
}
</style>
