<template>
  <v-card class="d-flex flex-column pa-8">
    <!-- header -->
    <v-divider></v-divider>
    <v-card elevation="0 pa-5">
      <p class="font-weight-bold ma-0" style="font-size: 24px">
        Quản lý đơn nghỉ
      </p>
    </v-card>
    <v-divider class="mb-5"></v-divider>
    <!-- operation -->
    <v-card class="d-flex flex-row mb-5 justify-space-between" elevation="0">
      <v-card outlined width="30%">
        <v-text-field
          label="Tìm kiếm"
          prepend-inner-icon="mdi-magnify"
          solo
          flat
          hide-details=""
        ></v-text-field>
      </v-card>
      <v-card class="d-flex flex-row" elevation="0">
        <v-btn
          class="mr-3 white--text btn font-weight-medium text-body-1"
          elevation="0"
          color="#476D92"
          :disabled="_.isEmpty(selected)"
          @click.stop="confirm_approve_dialog = true"
        >
          <v-icon medium class="mr-2">mdi-text-box-check-outline</v-icon>
          Duyệt đơn</v-btn
        >

        <v-btn icon>
          <v-icon>mdi-filter-variant</v-icon>
        </v-btn>
      </v-card>
    </v-card>
    <!-- table data -->
    <v-card outlined>
      <v-data-table
        v-model="selected"
        :headers="headers"
        :items="listLeave"
        :items-per-page="5"
        class="elevation-0"
        show-select
        single-select
        :footer-props="{ itemsPerPageText: 'Số hàng' }"
      >
        <template v-slot:[`item.isApproved`]="{ item }">
          <p
            class="ma-0 font-weight-medium text-body-2"
            style="color: #667085"
            v-if="item.isApproved == false"
          >
            <v-chip text-color="white" color="orange" small>
              Chờ duyệt
            </v-chip>
          </p>
          <p
            class="ma-0 font-weight-medium text-body-2"
            style="color: #12B76A"
            v-if="item.isApproved == true"
          >
            <v-chip small color="green" text-color="white">
              Đã hoàn tất
            </v-chip>
          </p>
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
            Bạn có muốn duyện đơn nghỉ này không
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
            @click="postApproveLeave"
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
      headers: [
        {
          text: "Ngày",
          align: "start",
          sortable: false,
          value: "date"
        },

        {
          text: "Bác sĩ ID",
          value: "doctorId",
          sortable: false
        },

        {
          text: "Lý do",
          value: "reason",
          sortable: false
        },
        {
          text: "Tình trạng",
          value: "isApproved",
          sortable: false
        }
      ],
      listLeave: [],
      params: {
        filters: [],
        sorts: [],
        page: 0,
        size: 1
      },
      selected: [],
      loadingConfirm: false
    };
  },
  async created() {
    await this.getAllLeave();
  },
  methods: {
    async getAllLeave() {
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };

      let params = this._.cloneDeep(this.params);
      params.page = 0;
      params.size = 10;

      await axios
        .get(`${url}/api/leave-requests`, { params: params })
        .then(res => {
          this.listLeave = res.data.results;
        });
    },

    async postApproveLeave() {
      let leaveId = this.selected[0].id;
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      await axios
        .post(`${url}/api/leave-requests/approve?leaveId=${leaveId}`)
        .then(res => {
          this.$store.dispatch("snackbar/set_snackbar", {
            text: "Duyệt đơn thành công",
            type: "success"
          });
        });
      await this.getAllLeave();
      this.confirm_approve_dialog = false;
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
</style>
