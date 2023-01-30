<template>
  <div class="content">
    <v-app>
      <v-card>
        <v-toolbar flat color="primary" dark>
          <v-toolbar-title>Danh sách thông báo</v-toolbar-title>
        </v-toolbar>
        <v-tabs vertical>
          <v-tab>
            <v-icon left> mark_as_unread </v-icon>
            Thông báo đến
          </v-tab>
          <v-tab>
            <v-icon left> send </v-icon>
            Thông báo gửi
          </v-tab>

          <v-tab-item>
            <v-card flat>
              <v-card-text>
                <!-- Start Thông báo đến -->
                <!-- Start Thông báo đến -->
                <!-- Start Thông báo đến -->

                <v-app id="inspire">
                  <v-data-table
                    :headers="headers"
                    :items="desserts"
                    :search="search"
                    :sort-by.sync="sortBy"
                    :sort-desc.sync="sortDesc"
                    class="elevation-1"
                  >
                    <template v-slot:top>
                      <v-toolbar flat>
                        <v-toolbar-title></v-toolbar-title>
                        <v-divider class="mx-4" inset vertical></v-divider>
                        <v-dialog v-model="dialog" max-width="500px">
                          <template v-slot:activator="{ on, attrs }">
                            <v-btn
                              color="primary"
                              dark
                              class="mb-2"
                              v-bind="attrs"
                              v-on="on"
                            >
                              Tạo thông báo mới
                            </v-btn>
                          </template>

                          <v-card>
                            <v-card-title>
                              <span class="text-h5">{{ formTitle }}</span>
                            </v-card-title>

                            <v-card-text>
                              <v-container>
                                <v-row>
                                  <v-col cols="12" sm="6" md="4">
                                    <v-text-field
                                      v-model="editedItem.name"
                                      label="Người gửi"
                                    ></v-text-field>
                                  </v-col>
                                  <v-col cols="12" sm="6" md="4">
                                    <v-text-field
                                      v-model="editedItem.tieude"
                                      label="Chủ đề"
                                    ></v-text-field>
                                  </v-col>
                                  <v-col cols="12" sm="6" md="4">
                                    <v-text-field
                                      label="Nội dung"
                                    ></v-text-field>
                                  </v-col>
                                  <v-col cols="12" sm="6" md="4">
                                    <v-text-field
                                      v-model="editedItem.thoigian"
                                      label="Thời gian"
                                    ></v-text-field>
                                  </v-col>
                                </v-row>
                              </v-container>
                            </v-card-text>

                            <v-card-actions>
                              <v-spacer></v-spacer>
                              <v-btn color="blue darken-1" text @click="close">
                                Cancel
                              </v-btn>
                              <v-btn color="blue darken-1" text @click="save">
                                Save
                              </v-btn>
                            </v-card-actions>
                          </v-card>
                        </v-dialog>
                        <v-spacer></v-spacer>
                        <v-text-field
                          v-model="search"
                          append-icon="mdi-magnify"
                          label="Tìm kiếm"
                          single-line
                          hide-details
                        ></v-text-field>

                        <v-dialog v-model="dialogDelete" max-width="500px">
                          <v-card>
                            <v-card-title class="text-h5"
                              >Are you sure you want to delete this
                              item?</v-card-title
                            >
                            <v-card-actions>
                              <v-spacer></v-spacer>
                              <v-btn
                                color="blue darken-1"
                                text
                                @click="closeDelete"
                                >Cancel</v-btn
                              >
                              <v-btn
                                color="blue darken-1"
                                text
                                @click="deleteItemConfirm"
                                >OK</v-btn
                              >
                              <v-spacer></v-spacer>
                            </v-card-actions>
                          </v-card>
                        </v-dialog>
                      </v-toolbar>
                    </template>
                    <template v-slot:[`item.actions`]="{ item }">
                      <v-icon small class="mr-2" @click="editItem(item)">
                        visibility
                      </v-icon>
                      <v-icon small @click="deleteItem(item)">
                        mdi-delete
                      </v-icon>
                    </template>
                    <template v-slot:no-data>
                      <v-btn color="primary" @click="initialize"> Reset </v-btn>
                    </template>
                  </v-data-table>
                </v-app>
                <!-- End Thông báo đến -->
                <!-- End Thông báo đến -->
                <!-- End Thông báo đến -->
              </v-card-text>
            </v-card>
          </v-tab-item>
          <v-tab-item>
            <v-card flat>
              <v-card-text> Nội dung </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </v-card>
    </v-app>
  </div>
</template>

<script>
export default {
  data: () => ({
    sortBy: "thoigian",
    sortDesc: true,
    search: "",
    dialog: false,
    dialogDelete: false,
    headers: [
      {
        text: "Người gửi",
        align: "start",
        value: "name",
      },
      { text: "Chủ đề", value: "tieude" },
      { text: "Thời gian", value: "thoigian" },
      { text: "Actions", value: "actions", sortable: false },
    ],
    desserts: [],
    editedIndex: -1,
    editedItem: {
      name: "",
      tieude: "",
      thoigian: "",
    },
    defaultItem: {
      name: "",
      tieude: "",
      thoigian: "",
    },
  }),
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "Thông báo mới" : "Xem thông báo";
    },
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },
  created() {
    this.initialize();
  },
  methods: {
    initialize() {
      this.desserts = [
        {
          name: "Nguyễn Văn A",
          tieude: "Thay đổi lịch làm việc 1",
          thoigian: "2022-03-16",
        },
        {
          name: "Nguyễn Văn B",
          tieude: "Thay đổi lịch làm việc 2",
          thoigian: "2022-03-15",
        },
        {
          name: "Nguyễn Văn C",
          tieude: "Thay đổi lịch làm việc 3",
          thoigian: "2022-03-14",
        },
        {
          name: "Nguyễn Văn D",
          tieude: "Thay đổi lịch làm việc 4",
          thoigian: "2022-03-13",
        },
        {
          name: "Nguyễn Văn E",
          tieude: "Thay đổi lịch làm việc 5",
          thoigian: "2022-03-12",
        },
        {
          name: "Nguyễn Văn F",
          tieude: "Thay đổi lịch làm việc 6",
          thoigian: "2022-03-11",
        },
        {
          name: "Nguyễn Văn A",
          tieude: "Thay đổi lịch làm việc 7",
          thoigian: "2022-03-10",
        },
        {
          name: "Nguyễn Văn A",
          tieude: "Thay đổi lịch làm việc 8",
          thoigian: "2022-03-17",
        },
        {
          name: "Nguyễn Văn A",
          tieude: "Thay đổi lịch làm việc 9",
          thoigian: "2022-03-17",
        },
        {
          name: "Nguyễn Văn A",
          tieude: "Thay đổi lịch làm việc 10",
          thoigian: "2022-03-17",
        },
      ];
    },
    editItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },
    deleteItemConfirm() {
      this.desserts.splice(this.editedIndex, 1);
      this.closeDelete();
    },
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.desserts[this.editedIndex], this.editedItem);
      } else {
        this.desserts.push(this.editedItem);
      }
      this.close();
    },
  },
};
</script>
