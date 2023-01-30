<style lang="scss" scoped>
* {
  font-family: "Quicksand", sans-serif;
}
</style>
<template>
  <div class="content">
    <v-card>
      <v-card-title>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Tìm kiếm..."
          single-line
          hide-details
          class="my-auto"
        ></v-text-field>
        <v-spacer></v-spacer>
        <p class=" display-1 mr-10 my-auto black--text font-italic">
          Ngày {{ currentDate }}
        </p>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="lstAppStatus"
        :search="search"
        @click:row="selectedRow"
      >
        <template v-slot:item.status="{ item }">
          <div v-if="item.status == 'Đang chờ'">
            <p class="my-auto warning--text">
              <v-icon small class="mr-2" :color="getColor(item.status)" dark>
                mdi-checkbox-blank-circle
              </v-icon>
              {{ item.status }}
            </p>
          </div>
          <div v-if="item.status == 'Đang khám'">
            <p class="my-auto success--text">
              <v-icon small class="mr-2" :color="getColor(item.status)" dark>
                mdi-checkbox-blank-circle
              </v-icon>
              {{ item.status }}
            </p>
          </div>
          <div v-if="item.status == 'Hoàn tất'">
            <p class="my-auto info--text">
              <v-icon small class="mr-2" :color="getColor(item.status)" dark>
                mdi-checkbox-blank-circle
              </v-icon>
              {{ item.status }}
            </p>
          </div>
          <div v-if="item.status == 'Bị hủy'">
            <p class="my-auto error--text">
              <v-icon small class="mr-2" :color="getColor(item.status)" dark>
                mdi-checkbox-blank-circle
              </v-icon>
              {{ item.status }}
            </p>
          </div>
        </template>
      </v-data-table>
    </v-card>

    <v-dialog
      v-model="dialogViewSelected"
      persistent
      max-width="900px"
      scrollable
      readonly
    >
      <v-card>
        <v-card-title
          style="justify-content: space-between"
          class="green darken-2"
        >
          <p class="display-1 font-weight-bold my-auto white--text">
            TRẠNG THÁI CUỘC HẸN
          </p>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                icon
                @click="closeDialogViewSelected()"
                class="white--text"
                v-bind="attrs"
                v-on="on"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </template>
            <span>Đóng</span>
          </v-tooltip>
        </v-card-title>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col class=" headline font-weight-medium" cols="3">
                Ngày {{ currentDate }}
              </v-col>
              <v-col class=" headline font-weight-regular font-italic" cols="3">
                : {{ currentDate }}
              </v-col>
              <v-col class=" headline font-weight-medium" cols="3">
                Thời gian
              </v-col>
              <v-col class=" headline font-weight-regular font-italic" cols="3">
                :{{ selected.time }}
              </v-col>
            </v-row>
            <v-row>
              <v-col class=" headline font-weight-medium" cols="3">
                ID bệnh nhân
              </v-col>
              <v-col class=" headline font-weight-regular font-italic" cols="3">
                : {{ selected.idPatient }}
              </v-col>
              <v-col class=" headline font-weight-medium" cols="3">
                Tên bệnh nhân
              </v-col>
              <v-col class=" headline font-weight-regular font-italic" cols="3">
                : {{ selected.namePatient }}
              </v-col>
            </v-row>
            <v-row>
              <v-col class=" headline font-weight-medium" cols="3">
                Tên bác sĩ
              </v-col>
              <v-col class=" headline font-weight-regular font-italic" cols="3">
                : {{ selected.nameDoctor }}
              </v-col>
              <v-col class=" headline font-weight-medium" cols="3">
                Chuyên khoa
              </v-col>
              <v-col class=" headline font-weight-regular font-italic" cols="3">
                : {{ selected.specialty }}
              </v-col>
            </v-row>
            <v-row>
              <v-col class=" headline font-weight-medium" cols="3">
                Phòng
              </v-col>
              <v-col class=" headline font-weight-regular font-italic" cols="3">
                : {{ selected.room }}
              </v-col>
              <v-col class=" headline font-weight-medium" cols="3">
                Trạng thái
              </v-col>
              <v-col class=" headline font-weight-regular font-italic" cols="3">
                <div v-if="selected.status == 'Đang chờ'">
                  <p class="my-auto warning--text">:{{ selected.status }}</p>
                </div>
                <div v-if="selected.status == 'Đang khám'">
                  <p class="my-auto success--text">:{{ selected.status }}</p>
                </div>
                <div v-if="selected.status == 'Hoàn tất'">
                  <p class="my-auto info--text">:{{ selected.status }}</p>
                </div>
                <div v-if="selected.status == 'Bị hủy'">
                  <p class="my-auto error--text">:{{ selected.status }}</p>
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            small
            color="grey darken-2"
            dark
            @click="closeDialogViewSelected"
            class="mr-2 px-5 py-4"
          >
            Đóng
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentDate: "2022-04-20",
      search: "",
      headers: [
        {
          text: "ID Bệnh nhân",
          align: "start",
          sortable: false,
          value: "idPatient",
          class: "font-weight-bold  green darken-2 white--text"
        },
        {
          text: "Thời gian",
          value: "time",
          sortable: true,
          class: "font-weight-bold  green darken-1 white--text"
        },
        {
          text: "Tên bệnh nhân",
          value: "namePatient",
          sortable: false,
          class: "font-weight-bold  green darken-2 white--text"
        },
        {
          text: "Tên bác sĩ khám",
          value: "nameDoctor",
          sortable: false,
          class: "font-weight-bold  green darken-1 white--text"
        },
        {
          text: "Khoa khám",
          value: "specialty",
          sortable: false,
          class: "font-weight-bold  green darken-2 white--text"
        },
        {
          text: "Trạng thái",
          value: "status",
          sortable: false,
          class: "font-weight-bold  green darken-1 white--text"
        },
        {
          text: "Phòng",
          value: "room",
          sortable: false,
          class: "font-weight-bold  green darken-2 white--text"
        }
      ],
      lstAppStatus: [
        {
          idPatient: "1",
          time: "9:00-10:00",
          namePatient: "Nguyen Ngoc Tan",
          nameDoctor: "Doctor Van A",
          status: "Đang chờ",
          specialty: "Khoa Nhi",
          room: "H1"
        },
        {
          idPatient: "2",
          time: "10:00-11:00",
          namePatient: "Nguyen Ngoc Son",
          nameDoctor: "Doctor Van B",
          status: "Đang khám",
          specialty: "Khoa Nhi",
          room: "H2"
        },
        {
          idPatient: "2",
          time: "11:00-12:00",
          namePatient: "Nguyen Ngoc Son",
          nameDoctor: "Doctor Van B",
          status: "Hoàn tất",
          specialty: "Khoa Nhi",
          room: "H2"
        },
        {
          idPatient: "2",
          time: "12:00-13:00",
          namePatient: "Nguyen Ngoc Son",
          nameDoctor: "Doctor Van B",
          status: "Bị hủy",
          specialty: "Khoa Nhi",
          room: "H2"
        }
      ],
      selected: {},
      dialogViewSelected: false
    };
  },
  methods: {
    getColor(status) {
      if (status == "Đang chờ") return "warning";
      else if (status == "Đang khám") return "success";
      else if (status == "Hoàn tất") return "info";
      else return "error";
    },
    selectedRow(row) {
      this.selected = row;
      this.dialogViewSelected = true;
    },
    closeDialogViewSelected() {
      this.dialogViewSelected = false;
    }
  }
};
</script>
