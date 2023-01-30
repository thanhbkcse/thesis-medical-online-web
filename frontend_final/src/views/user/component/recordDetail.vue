<template>
  <div>
    <div>
      <p class="font-weight-bold mb-3" style="font-size: 24px">
        Thông tin bệnh án
      </p>
      <p style="color: #667085" class="text-body-1">
        Chi tiết thông tin bệnh án của bạn
      </p>
    </div>
    <p class="font-weight-bold">Khám sơ bộ</p>
    <v-card
      class="mb-5 d-flex pa-6"
      style="border: #D0D5DD solid 1px"
      elevation="0"
    >
      <div style="width: 50%">
        <v-card class="d-flex flex-row mb-3" elevation="0">
          <!-- label -->
          <v-card width="50%" elevation="0"
            ><p
              class="ma-0 text-body-1 font-weight-regular"
              style="color: #667085"
            >
              Chiều cao (cm)
            </p></v-card
          >
          <p class="ma-0 font-weight-medium">
            {{ record.height | empty }}
          </p>
        </v-card>
        <v-card class="d-flex flex-row mb-3" elevation="0">
          <!-- label -->
          <v-card width="50%" elevation="0"
            ><p
              class="ma-0 text-body-1 font-weight-regular"
              style="color: #667085"
            >
              Cân nặng (kg)
            </p></v-card
          >
          <p class="ma-0 font-weight-medium">
            {{ record.weight | empty }}
          </p>
        </v-card>
        <v-card class="d-flex flex-row mb-3" elevation="0">
          <!-- label -->
          <v-card width="50%" elevation="0"
            ><p
              class="ma-0 text-body-1 font-weight-regular"
              style="color: #667085"
            >
              Mạch (lần/phút)
            </p></v-card
          >
          <p class="ma-0 font-weight-medium">
            {{ record.bloodVessel | empty }}
          </p>
        </v-card>
      </div>

      <div style="width: 50%">
        <v-card class="d-flex flex-row mb-3" elevation="0">
          <!-- label -->
          <v-card width="50%" elevation="0"
            ><p
              class="ma-0 text-body-1 font-weight-regular"
              style="color: #667085"
            >
              Nhiệt độ (độ C)
            </p></v-card
          >
          <p class="ma-0 font-weight-medium">
            {{ record.temperature | empty }}
          </p>
        </v-card>
        <v-card class="d-flex flex-row mb-3" elevation="0">
          <!-- label -->
          <v-card width="50%" elevation="0"
            ><p
              class="ma-0 text-body-1 font-weight-regular"
              style="color: #667085"
            >
              Huyết áp (mmHg)
            </p></v-card
          >
          <p class="ma-0 font-weight-medium">
            {{ record.bloodPressure | empty }}
          </p>
        </v-card>
        <v-card class="d-flex flex-row mb-3" elevation="0">
          <!-- label -->
          <v-card width="50%" elevation="0"
            ><p
              class="ma-0 text-body-1 font-weight-regular"
              style="color: #667085"
            >
              Nhịp tim (lần/phút)
            </p></v-card
          >
          <p class="ma-0 font-weight-medium">
            {{ record.heartbeat | empty }}
          </p>
        </v-card>
      </div>
    </v-card>
    <p class="font-weight-bold">Tóm tắt kết quả lâm sàng</p>
    <v-card
      class="mb-5 d-flex flex-column justify-center pa-6"
      style="border: #D0D5DD solid 1px"
      elevation="0"
    >
      <v-card class="d-flex flex-row mb-3" elevation="0">
        <!-- label -->
        <v-card width="30%" elevation="0"
          ><p
            class="ma-0 text-body-1 font-weight-regular"
            style="color: #667085"
          >
            Chuẩn đoán
          </p></v-card
        >
        <p class="ma-0 font-weight-medium">
          {{ record.diagnose | empty }}
        </p>
      </v-card>
      <v-card class="d-flex flex-row mb-3" elevation="0">
        <!-- label -->
        <v-card width="30%" elevation="0"
          ><p
            class="ma-0 text-body-1 font-weight-regular"
            style="color: #667085"
          >
            Chỉ định
          </p></v-card
        >
        <p class="ma-0 font-weight-medium">
          {{ record.prescribe | empty }}
        </p>
      </v-card>
      <v-card class="d-flex flex-row mb-3" elevation="0">
        <!-- label -->
        <v-card width="30%" elevation="0"
          ><p
            class="ma-0 text-body-1 font-weight-regular"
            style="color: #667085"
          >
            Ghi chú
          </p></v-card
        >
        <p class="ma-0 font-weight-medium">
          {{ record.note | empty }}
        </p>
      </v-card>
      <v-card class="d-flex flex-row mb-3" elevation="0">
        <!-- label -->
        <v-card width="30%" elevation="0"
          ><p
            class="ma-0 text-body-1 font-weight-regular"
            style="color: #667085"
          >
            Ngày tái khám
          </p></v-card
        >
        <p class="ma-0 font-weight-medium">
          {{
            record.reExaminationDate
              ? convert_date(record.reExaminationDate)
              : "" | empty
          }}
        </p>
      </v-card>
      <v-card class="d-flex flex-row mb-3" elevation="0">
        <!-- label -->
        <v-card width="30%" elevation="0"
          ><p
            class="ma-0 text-body-1 font-weight-regular"
            style="color: #667085"
          >
            Yêu cầu nhập viện
          </p></v-card
        >
        <p class="ma-0 font-weight-medium">
          {{ record.hospitalize ? "Có" : "Không" }}
        </p>
      </v-card>
    </v-card>
    <p class="font-weight-bold">Đơn thuốc</p>
    <v-card class="d-flex flex-column mb-6" outlined>
      <!-- medicine list -->
      <v-data-table
        :headers="medicineHeaders"
        :items="record.medicines"
        checkbox-color="#3C5E7E"
        hide-default-footer
        no-data-text="Đơn thuốc trống"
      >
        <template v-slot:[`item.use`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">
              {{ convert_to_use(item) }}
            </p>
          </div>
        </template>
        <template v-slot:[`item.mount`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">
              {{ countMedicine(item) }}
            </p>
          </div>
        </template>
        <template v-slot:[`item.name`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">
              {{ item.storeMedicine.name }}
            </p>
          </div>
        </template>
      </v-data-table>
    </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      medicineHeaders: [
        {
          text: "Tên",
          align: "start",
          sortable: false,
          value: "storeMedicine.name",
          class: "text-body-2 font-weight-medium"
        },
        {
          text: "Liều",
          value: "use",
          sortable: false,
          class: "text-body-2 font-weight-medium"
        },
        {
          text: "Đơn vị uống",
          value: "unit",
          sortable: false,
          class: "text-body-2 font-weight-medium"
        },
        {
          text: "Số lượng",
          value: "mount",
          sortable: false,
          class: "text-body-2 font-weight-medium"
        }
      ]
    };
  },
  methods: {
    formatDate(date_string) {
      let date = new Date(date_string);
      let day = date.getDate();
      let month = date.getMonth();
      let year = date.getFullYear();
      return `${day} tháng ${month}, ${year}`;
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
    convert_to_use(item) {
      let use = "";

      if (item.numMedicineInMorning != 0) {
        if (use.length == 0) {
          use = use + "Sáng: " + item.numMedicineInMorning;
        } else {
          use = use + ", Sáng: " + item.numMedicineInMorning;
        }
      }

      if (item.numMedicineInAfternoon != 0) {
        if (use.length == 0) {
          use = use + "Chiều: " + item.numMedicineInAfternoon;
        } else {
          use = use + ", Chiều: " + item.numMedicineInAfternoon;
        }
      }
      if (item.numMedicineInEvening != 0) {
        if (use.length == 0) {
          use = use + "Tối: " + item.numMedicineInEvening;
        } else {
          use = use + ", Tối: " + item.numMedicineInEvening;
        }
      }
      return use;
    },
    countMedicine(item) {
      const oneDay = 24 * 60 * 60 * 1000;
      const fromDate = new Date(item.dateStart);
      const toDate = new Date(item.dateEnd);
      const diffDays = Math.round(Math.abs((fromDate - toDate) / oneDay));
      return (
        (Number(item.numMedicineInMorning) +
          Number(item.numMedicineInAfternoon) +
          Number(item.numMedicineInEvening)) *
        diffDays
      );
    }
  },
  props: ["record"]
};
</script>

<style></style>
