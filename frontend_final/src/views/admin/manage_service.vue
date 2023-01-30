<template>
  <v-card class="d-flex flex-column pa-8">
    <!-- header -->
    <v-divider></v-divider>
    <v-card elevation="0 pa-5">
      <p class="font-weight-bold ma-0" style="font-size: 24px">
        Quản lý dịch vụ
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
        <v-btn icon @click="open_add_dialog()" class="mr-3">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-btn icon @click="open_edit_dialog()" class="mr-3">
          <v-icon>mdi-pencil-outline</v-icon>
        </v-btn>
        <v-btn icon class="mr-3" @click="remove_dialog = true">
          <v-icon>mdi-trash-can-outline</v-icon>
        </v-btn>
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
        :items="listService"
        :items-per-page="5"
        class="elevation-0"
        show-select
        :footer-props="{ itemsPerPageText: 'Số hàng' }"
      >
        <template v-slot:[`item.image`]="{ item }">
          <div class="d-flex justify-center">
            <v-avatar size="60" class="ma-2" elevation="0">
              <v-img :src="getImgOfDoctor(item)"></v-img>
            </v-avatar>
          </div>
        </template>
      </v-data-table>
    </v-card>
  </v-card>
</template>

<script>
import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;
export default {
  data() {
    return {
      params: {
        filters: [],
        sorts: [],
        page: 0,
        size: 8
      },
      page: 1,
      listService: [],
      totalPages: 0,
      //table
      headers: [
        {
          text: "Ảnh đại diện",
          value: "image",
          sortable: false,
          align: "center"
        },
        {
          text: "Tên",
          align: "start",
          sortable: false,
          value: "name"
        },

        {
          text: "Bệnh viện",
          value: "hospital.name",
          sortable: false
        },
        {
          text: "Số lượt đăng ký",
          value: "registrationNumber",
          align: "center"
        },
        {
          text: "Đánh giá",
          value: "favorite",
          align: "center"
        }
      ],
      selected: []
    };
  },
  async created() {
    if (this.$store.getters["auth/isAdmin"]) {
      await this.getListService();
    } else if (this.$store.getters["auth/isHospitalAdmin"]) {
      await this.getServiceByHospital();
    }
  },

  methods: {
    async getListService() {
      this.loading = true;
      let params = this._.cloneDeep(this.params);
      // if (this.search) {
      //   params.filters.push({
      //     key: "name",
      //     operator: "LIKE",
      //     field_type: "STRING",
      //     value: this.search.trim()
      //   });
      // }
      // if (this.specialtySelect) {
      //   params.filters.push({
      //     key: "specialty",
      //     operator: "EQUAL",
      //     field_type: "SPECIALTY",
      //     value: this.specialtySelect
      //   });
      // }
      // if (this.provinceSelect) {
      //   params.filters.push({
      //     key: "hospital.address.province",
      //     operator: "EQUAL_NESTED",
      //     field_type: "STRING",
      //     value: this.provinceSelect
      //   });
      // }
      // if (this.hospitalSelect) {
      //   params.filters.push({
      //     key: "hospital.id",
      //     operator: "EQUAL_NESTED",
      //     field_type: "STRING",
      //     value: this.hospitalSelect
      //   });
      // }
      params.page = this.page - 1;
      const res = await axios.post(`${url}/api/services/search`, params);
      this.loading = false;
      this.listService = res.data.results;
      this.totalPages = res.data?.meta?.totalPages;
    },
    getImgOfDoctor(doctor) {
      if (doctor.imageUrl != null) {
        return doctor.imageUrl;
      } else {
        return require("@/assets/img/home/appbar/avatar.png");
      }
    },

    async getServiceByHospital() {
      const url = process.env.VUE_APP_ROOT_API;
      const params = {
        hospitalId: this.$store.getters["auth/hospitalId"],
        page: 0,
        size: 8
      };
      const res = await axios.get(`${url}/api/services/hospital`, {
        params: params
      });
      this.listService = res.data.results;
      this.totalPages = res.data?.meta?.totalPages;
    }
  }
};
</script>
