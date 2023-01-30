<template>
  <div>
    <div style="padding: 10px 30px">
      <v-row class="mt-3">
        <v-card style="flex-grow: 1;">
          <v-card-title primary-title>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Tìm kiếm"
              single-line
              hide-details
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-btn class="mr-2" color="primary" tile
              ><v-icon right dark class="mr-1"> mdi-plus-circle-outline </v-icon
              >Tạo bệnh án</v-btn
            >
            <v-btn color="primary" tile
              ><v-icon right dark class="mr-1"> mdi-pencil </v-icon>Chỉnh
              sửa</v-btn
            >
          </v-card-title>
          <v-data-table
            v-model="selected"
            item-key="name"
            show-select
            :headers="headers"
            :items="desserts"
            :items-per-page="5"
            class="elevation-1"
            style="flex-basis: 100%"
            :search="search"
          >
          </v-data-table>
        </v-card>
      </v-row>
    </div>
  </div>
</template>
<script>
import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;
export default {
  data() {
    return {
      search: "",
      selected: [],
      headers: [
        {
          text: "Họ và tên",
          sortable: false,
          value: "name",
          class: "primary--text"
        },
        {
          text: "Giới tính",
          sortable: false,
          value: "calories",
          class: "primary--text"
        },
        {
          text: "Số điện thoại",
          sortable: false,
          value: "fat",
          class: "primary--text"
        },
        {
          text: "Mã bệnh án",
          sortable: false,
          value: "carbs",
          class: "primary--text"
        },
        {
          text: "Ngày khám",
          sortable: false,
          value: "protein",
          class: "primary--text"
        },
        {
          text: "Chuyên khoa",
          sortable: false,
          value: "iron",
          class: "primary--text"
        }
      ],
      desserts: [
        {
          name: "Frozen Yogurt",
          calories: 159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          iron: "1%"
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
          fat: 9.0,
          carbs: 37,
          protein: 4.3,
          iron: "1%"
        },
        {
          name: "Eclair",
          calories: 262,
          fat: 16.0,
          carbs: 23,
          protein: 6.0,
          iron: "7%"
        },
        {
          name: "Cupcake",
          calories: 305,
          fat: 3.7,
          carbs: 67,
          protein: 4.3,
          iron: "8%"
        },
        {
          name: "Gingerbread",
          calories: 356,
          fat: 16.0,
          carbs: 49,
          protein: 3.9,
          iron: "16%"
        },
        {
          name: "Jelly bean",
          calories: 375,
          fat: 0.0,
          carbs: 94,
          protein: 0.0,
          iron: "0%"
        },
        {
          name: "Lollipop",
          calories: 392,
          fat: 0.2,
          carbs: 98,
          protein: 0,
          iron: "2%"
        },
        {
          name: "Honeycomb",
          calories: 408,
          fat: 3.2,
          carbs: 87,
          protein: 6.5,
          iron: "45%"
        },
        {
          name: "Donut",
          calories: 452,
          fat: 25.0,
          carbs: 51,
          protein: 4.9,
          iron: "22%"
        },
        {
          name: "KitKat",
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          iron: "6%"
        }
      ],
      records: {},
      initialRecord: {
        pathological: undefined,
        personalMedicalHistory: undefined,
        familyMedicalHistory: undefined,
        bodyInspection: undefined,
        bloodVessel: undefined,
        temperature: undefined,
        bloodPressure: undefined,
        heartbeat: undefined,
        summary: undefined,
        partsInspection: undefined,
        hospitalize: undefined,
        facultyTreatment: undefined,
        medicines: [],
        notes: undefined,
        reExaminationDate: undefined,
        files: []
      }
    };
  },
  created() {
    this.getRecord();
  },
  methods: {
    async getRecord() {
      const token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = { Authorization: `Bearer ${token}` };
      await axios.get(`${url}/api/doctor/schedules/date`).then(res => {
        this.records = res.data.results;
      });
    }
  }
};
</script>
<style lang="scss" scoped>
* {
  font-family: "Quicksand", sans-serif;
}
</style>
