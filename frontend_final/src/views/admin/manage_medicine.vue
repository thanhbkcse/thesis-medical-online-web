<template>
  <v-card class="d-flex flex-column pa-8">
    <!-- header -->
    <v-divider></v-divider>
    <v-card elevation="0 pa-5">
      <p class="font-weight-bold ma-0" style="font-size: 24px">
        Quản lý thuốc
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
        <v-btn icon @click="create_dialog = true" class="mr-3">
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
        :items="listMedicine"
        :items-per-page="5"
        class="elevation-0"
        show-select
        :footer-props="{ itemsPerPageText: 'Số hàng' }"
      >
      </v-data-table>
    </v-card>
    <!-- dialog create room -->
    <v-dialog width="500" v-model="create_dialog">
      <v-card height="500" class="pa-5 d-flex flex-column">
        <p class="font-weight-bold text-h6 mb-5">
          Tạo thuốc
        </p>
        <p class="mb-2 font-weight-medium text-body-2">Chọn bệnh viện:</p>
        <v-card outlined>
          <v-combobox
            v-model="hospital_select"
            flat
            solo
            hide-details=""
            :items="hospital_list"
            item-text="name"
            :spellcheck="false"
          >
          </v-combobox>
        </v-card>
        <p class="mb-2 mt-3 font-weight-medium text-body-2">Nhập tên thuốc</p>
        <v-card outlined width="100%" elevation="0">
          <v-text-field
            :spellcheck="false"
            flat
            solo
            hide-details=""
            v-model="medicine_name"
          ></v-text-field>
        </v-card>
        <p class="mb-2 mt-3 font-weight-medium text-body-2">Nhập giá</p>
        <v-card outlined width="100%" elevation="0">
          <v-text-field
            :spellcheck="false"
            flat
            solo
            hide-details=""
            v-model="price"
          ></v-text-field>
        </v-card>
        <p class="mb-2 mt-3 font-weight-medium text-body-2">Nhập mô tả</p>
        <v-card outlined width="100%" elevation="0">
          <v-text-field
            :spellcheck="false"
            flat
            solo
            hide-details=""
            v-model="info"
          ></v-text-field>
        </v-card>

        <v-spacer></v-spacer>
        <div class="d-flex flex-row justify-space-between">
          <v-btn
            elevation="0"
            outlined
            color="#D0D5DD"
            @click="select_doctor_dialog = false"
            class="btn"
            ><p class="ma-0 font-weight-bold" style="color:#667085">
              Hủy
            </p></v-btn
          >
          <v-btn
            class="btn white--text"
            color="#537DA5"
            elevation="0"
            @click="create_store_medicine"
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
      selected: [],
      create_dialog: false,
      headers: [
        {
          text: "Tên thuốc",
          align: "start",
          sortable: false,
          value: "name"
        },

        {
          text: "Thông tin",
          value: "info",
          sortable: false
        },

        {
          text: "Giá",
          value: "price",
          sortable: false
        },
        {
          text: "Bệnh viện",
          value: "hospital.name",
          sortable: false
        }
      ],
      listMedicine: [],
      hospital_list: [],
      hospital_select: "",
      medicine_name: "",
      price: "",
      info: ""
    };
  },
  created() {
    this.getMedicineList();
    this.get_hospital_list();
  },
  methods: {
    async getMedicineList() {
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      await axios.get(`${url}/api/store-medicines`).then(res => {
        this.listMedicine = res.data.results;
      });
    },
    async get_hospital_list() {
      await axios.get(`${url}/api/hospitals`).then(res => {
        this.hospital_list = res.data.results;
      });
    },
    async create_store_medicine() {
      const param = {
        name: this.medicine_name,
        price: this.price,
        info: this.info,
        imageUrl: null,
        hospitalId: this.hospital_select.id
      };
      await axios.post(`${url}/api/store-medicines`, param).then(res => {
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Tạo thuốc thành công",
          type: "success"
        });
      });
      await this.getMedicineList();
      this.create_dialog = false;
    }
  }
};
</script>
<style scoped>
.btn {
  text-transform: none;
}
</style>
