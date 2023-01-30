<template>
  <div class="content">
    <v-card>
      <v-data-table
        v-model="selected"
        show-select
        :headers="headers"
        :items="desserts"
        item-key="idaccount"
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
              Thêm tài khoản
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
                  <span class="text-h5">Tạo tài khoản mới</span>
                </v-card-title>

                <v-card-text>
                  <v-container>
                    <v-form ref="form">
                      <v-row>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            label="Họ và tên"
                            v-model="getnew.name"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-autocomplete
                            v-model="getnew.position"
                            :items="items"
                            dense
                            filled
                            label="Vai trò"
                          ></v-autocomplete>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            label="Tên tài khoản"
                            v-model="getnew.username"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            v-model="getnew.password"
                            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="show1 ? 'text' : 'password'"
                            name="input-10-1"
                            label="Mật khẩu"
                            @click:append="show1 = !show1"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            label="Email"
                            v-model="getnew.email"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            label="Địa chỉ"
                            v-model="getnew.address"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            label="Số điện thoại"
                            v-model="getnew.phoneNumber"
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
          <v-card-title class="text-h5">
            Bạn có muốn xóa tài khoản? <br />
          </v-card-title>

          <v-card-text>
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left">STT</th>
                    <th class="text-left">Họ và tên</th>
                    <th class="text-left">Tên tài khoản</th>

                    <th class="text-left">Vị trí</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(taikhoan, k) in selected" :key="k">
                    <td>{{ k + 1 }}</td>
                    <td>{{ taikhoan.name }}</td>
                    <td>{{ taikhoan.username }}</td>
                    <td>{{ taikhoan.position }}</td>
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
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    label="Họ và tên"
                    v-model="editedItem.name"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <v-autocomplete
                    v-model="editedItem.position"
                    :items="items"
                    dense
                    filled
                    label="Vai trò"
                  ></v-autocomplete>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    label="Tên tài khoản"
                    v-model="editedItem.username"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    v-model="editedItem.password"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="Mật khẩu"
                    hint="Ít nhất 8 ký tự"
                    counter
                    @click:append="show1 = !show1"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    label="Email"
                    v-model="editedItem.email"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    label="Địa chỉ"
                    v-model="editedItem.address"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    label="Số điện thoại"
                    v-model="editedItem.phoneNumber"
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
    show1: false,
    items: ["Bác sĩ", "Bệnh nhân", "Nhân viên", "Admin"],
    search: "",
    selected: [],

    dialoggetnew: false,
    dialogDelete: false,
    dialogedit: false,
    indexdelete: null,
    indexedit: null,
    headers: [
      {
        text: "Họ và tên",
        align: "start",
        sortable: false,
        value: "name",
      },
      { text: "Vai trò", value: "position", sortable: false },
      { text: "Tên tài khoản", value: "username", sortable: false },
      { text: "Số điện thoại", value: "phoneNumber", sortable: false },
    ],
    desserts: [
      {
        idaccount: 1,
        name: "nguyễn thị ngọc tân",
        position: "Bệnh nhân",
        username: "tankk",
        password: "123456789",
        email: "tan@gmail.com",
        address: "20 Lương thạnh",
        phoneNumber: "0123456798",
      },
      {
        idaccount: 2,
        name: "abc",
        position: "Bác sĩ",
        username: "abc",
        password: "abc",
        email: "abc",
        address: "abc",
        phoneNumber: "abc",
      },
    ],

    editedItem: {
      idaccount: null,
      name: null,
      position: null,
      username: null,
      password: null,
      email: null,
      address: null,
      phoneNumber: null,
    },

    getnew: {
      idaccount: null,
      name: null,
      position: null,
      username: null,
      password: null,
      email: null,
      address: null,
      phoneNumber: null,
    },
  }),

  watch: {},
  created() {},
  methods: {
    editItem() {
      this.dialogedit = true;

      this.editedItem.idaccount = this.selected[0].idaccount;
      this.editedItem.name = this.selected[0].name;
      this.editedItem.position = this.selected[0].position;
      this.editedItem.username = this.selected[0].username;
      this.editedItem.password = this.selected[0].password;
      this.editedItem.email = this.selected[0].email;
      this.editedItem.address = this.selected[0].address;
      this.editedItem.phoneNumber = this.selected[0].phoneNumber;
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
    save() {},
    saveedit() {},
  },
};
</script>
