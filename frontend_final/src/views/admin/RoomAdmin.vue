<template>
  <div class="content">
    <v-card>
      <v-data-table
        v-model="selected"
        show-select
        :headers="headers"
        :items="desserts"
        item-key="id"
        sort-by="name"
        class="elevation-1"
        :search="search"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-btn
              color="primary"
              dark
              class="mb-2"
              @click="dialoggetnew = true"
            >
              Tạo phòng
            </v-btn>
            <v-spacer></v-spacer>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Tìm kiếm"
              single-line
              hide-details
            ></v-text-field>
            <v-spacer></v-spacer>
            <div v-if="selected.length == 1">
              <v-btn
                tile
                color="success"
                style="margin-right: 20px"
                @click="editItem"
              >
                <v-icon left> mdi-pencil </v-icon>
                Chỉnh sửa
              </v-btn>
            </div>
            <div v-else>
              <v-btn tile color="success" style="margin-right: 20px" disabled>
                <v-icon left> mdi-pencil </v-icon>
                Chỉnh sửa
              </v-btn>
            </div>
            <div v-if="selected.length > 0">
              <v-btn tile color="error" @click="dialogDelete = true">
                <v-icon left> mdi-delete</v-icon>
                Xóa
              </v-btn>
            </div>
            <div v-else>
              <v-btn tile color="error" disabled>
                <v-icon left> mdi-delete</v-icon>
                Xóa
              </v-btn>
            </div>
            <v-dialog v-model="dialoggetnew" max-width="600px" persistent>
              <v-card>
                <v-card-title>
                  <span class="text-h5">Tạo phòng mới</span>
                </v-card-title>

                <v-card-text>
                  <v-container>
                    <v-form ref="form">
                      <v-row>
                        <v-col cols="12">
                          <v-text-field
                            label="Tên phòng"
                            v-model="getnew.name"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12">
                          <v-text-field
                            label="Link"
                            v-model="getnew.link"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                    </v-form>
                  </v-container>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="closegetnew">
                    Hủy
                  </v-btn>
                  <v-btn color="blue darken-1" text @click="save"> Lưu </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
      </v-data-table>

      <v-dialog v-model="dialogDelete" max-width="600px" persistent>
        <v-card>
          <v-card-title class="text-h5"> Bạn có muốn xóa? <br /> </v-card-title>

          <v-card-text>
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left">STT</th>
                    <th class="text-left">Tên phòng</th>
                    <th class="text-left">Link</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(roomItem, k) in selected" :key="k">
                    <td>{{ k + 1 }}</td>
                    <td>{{ roomItem.name }}</td>
                    <td>{{ roomItem.link }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="closeDelete">Hủy</v-btn>
            <v-btn color="blue darken-1" text @click="deleteItemConfirm"
              >Xóa</v-btn
            >
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="dialogedit" max-width="600px" persistent>
        <v-card>
          <v-card-title>
            <span class="text-h5">Chỉnh sửa thông tin</span>
          </v-card-title>

          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="editedItem.name"
                    label="Tên phòng"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    label="Link"
                    v-model="editedItem.link"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="close"> Hủy </v-btn>
            <v-btn color="blue darken-1" text @click="saveedit"> Lưu </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </div>
</template>

<script>
export default {
  data: () => ({
    search: "",
    selected: [],

    dialoggetnew: false,
    dialogDelete: false,
    dialogedit: false,

    headers: [
      {
        text: "Tên phòng",
        align: "start",
        sortable: false,
        value: "name",
      },
      { text: "Link", value: "link", sortable: false },
    ],
    desserts: [
      //   {
      //     id: 1,
      //     name: "H2",
      //     link: "https://meet.google.com/yuy-wjaa-rcq",
      //   },
      //   {
      //     id: 2,
      //     name: "H3",
      //     link: "https://meet.google.com/syw-prhp-umr",
      //   },
      //   {
      //     id: 3,
      //     name: "H4",
      //     link: "https://meet.google.com/cdh-bbxe-hsb",
      //   },
    ],

    editedItem: {
      id: null,
      name: null,
      link: null,
    },

    getnew: {
      name: null,
      link: null,
    },
  }),

  watch: {},
  created() {},
  methods: {
    editItem() {
      this.dialogedit = true;
      this.editedItem = JSON.parse(JSON.stringify(this.selected[0]));
    },

    deleteItemConfirm() {},
    close() {
      this.dialogedit = false;
    },
    closegetnew() {
      this.$refs.form.reset();
      this.dialoggetnew = false;
    },
    closeDelete() {
      this.dialogDelete = false;
    },
    async save() {
      const params = {
        token: this.$store.getters["auth/access_token"],
        data: this.getnew,
      };
      await this.$store.dispatch("rooms/add_room", params);
      this.$refs.form.reset();
      this.getrooms();
    },

    async getrooms() {
      const params = {
        token: this.$store.getters["auth/access_token"],
      };
      await this.$store.dispatch("rooms/get_room", params);
      this.desserts = this.$store.getters["rooms/list_rooms"];
      console.log(this.desserts);
    },
    saveedit() {},
  },
  //Cần lấy trước khi lên vỉew
  created() {
    this.getrooms();
  },
};
</script>
