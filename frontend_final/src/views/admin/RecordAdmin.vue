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
            <v-btn color="primary" dark class="mb-2" @click="opennewRecord">
              Thêm bệnh án
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

            <v-dialog v-model="dialogNewrecord" max-width="1200px" persistent>
              <v-card>
                <v-card-title style="justify-content: space-between">
                  <span class="text-h5">{{ nameTitle }}</span>
                  <v-btn
                    color="#333"
                    small
                    style="color: #ffffff; padding: 0px"
                    @click="closegetnew"
                  >
                    Đóng
                  </v-btn>
                </v-card-title>
                <!-- form bệnh án ở đây ----------------------------------------------------------- -->
                <!-- form bệnh án ở đây ----------------------------------------------------------- -->
                <v-form ref="form">
                  <v-card-text>
                    <div>
                      <p
                        style="
                          display: flex;
                          width: 30%;
                          margin: 0px;
                          float: left;
                        "
                      >
                        <b style="margin-top: 20px; margin-right: 20px"
                          >BÁC SĨ: </b
                        ><v-text-field
                          style="width: 50%; margin: 0px"
                          v-model="record.appointment.doctor.name"
                        ></v-text-field>
                      </p>
                      <p
                        style="
                          display: flex;
                          width: 35%;
                          margin: 0px;
                          float: left;
                        "
                      >
                        <b style="margin-top: 20px; margin-right: 20px"
                          >NGÀY KHÁM:
                        </b>
                        <v-menu
                          v-model="menu12"
                          :close-on-content-click="false"
                          :nudge-right="40"
                          transition="scale-transition"
                          offset-y
                          min-width="auto"
                        >
                          <template v-slot:activator="{ on, attrs }">
                            <v-text-field
                              v-model="record.appointment.date"
                              prepend-icon="mdi-calendar"
                              readonly
                              v-bind="attrs"
                              v-on="on"
                            ></v-text-field>
                          </template>
                          <v-date-picker
                            v-model="record.appointment.date"
                            @input="menu12 = false"
                          ></v-date-picker>
                        </v-menu>
                      </p>
                      <p style="display: flex; width: 35%; margin: 0px">
                        <b style="margin-top: 20px; margin-right: 20px"
                          >THỜI GIAN:
                        </b>
                        <v-autocomplete
                          style="margin-top: 10px"
                          v-model="record.appointment.time"
                          :items="timeselect"
                          dense
                          filled
                        ></v-autocomplete>
                      </p>
                    </div>
                  </v-card-text>

                  <v-stepper non-linear v-model="e1">
                    <v-stepper-header>
                      <v-stepper-step editable step="1"
                        >Thông tin bệnh nhân
                      </v-stepper-step>

                      <v-divider></v-divider>

                      <v-stepper-step editable step="2">
                        Chuẩn đoán bệnh
                      </v-stepper-step>

                      <v-divider></v-divider>

                      <v-stepper-step step="3" editable>
                        Kê đơn
                      </v-stepper-step>
                    </v-stepper-header>

                    <v-stepper-items>
                      <v-stepper-content :key="`${1}-content`" :step="1">
                        <v-card class="mb-12" color="white" height="auto">
                          <div class="gop">
                            <div class="gop0">
                              <div class="gop1">
                                <div class="textform">
                                  <p>1. Họ và tên:</p>

                                  <v-text-field
                                    v-model="record.appointment.profile.name"
                                  ></v-text-field>
                                </div>
                                <div class="textform">
                                  <p>2. Năm sinh:</p>
                                  <div class="namsinh">
                                    <v-menu
                                      v-model="menu11"
                                      :close-on-content-click="false"
                                      :nudge-right="40"
                                      transition="scale-transition"
                                      offset-y
                                      min-width="auto"
                                    >
                                      <template
                                        v-slot:activator="{ on, attrs }"
                                      >
                                        <v-text-field
                                          v-model="
                                            record.appointment.profile.dob
                                          "
                                          prepend-icon="mdi-calendar"
                                          readonly
                                          v-bind="attrs"
                                          v-on="on"
                                        ></v-text-field>
                                      </template>
                                      <v-date-picker
                                        @input="menu11 = false"
                                        v-model="record.appointment.profile.dob"
                                      ></v-date-picker>
                                    </v-menu>
                                  </div>
                                </div>
                                <div class="textform">
                                  <p>3. Giới tính:</p>
                                  <div class="gioitinh">
                                    <v-radio-group
                                      row
                                      v-model="
                                        record.appointment.profile.gender
                                      "
                                    >
                                      <v-radio
                                        label="Nam"
                                        value="Nam"
                                      ></v-radio>
                                      <v-radio label="Nữ" value="Nữ"></v-radio>
                                    </v-radio-group>
                                  </div>
                                </div>
                                <div class="textform">
                                  <p>4. Dân tộc:</p>
                                  <div class="autocom">
                                    <v-toolbar color="white">
                                      <v-autocomplete
                                        v-model="
                                          record.appointment.profile.folk
                                        "
                                        :items="states"
                                        :search-input.sync="search1"
                                        cache-items
                                        class="mx-4"
                                        flat
                                        hide-no-data
                                        hide-details
                                        label="Dân tộc..."
                                        solo-inverted
                                      ></v-autocomplete>
                                    </v-toolbar>
                                  </div>
                                </div>
                                <div class="textform">
                                  <p>5. Mã số BHYT(Nếu có):</p>

                                  <v-text-field
                                    v-model="
                                      record.appointment.profile.healthInsurance
                                    "
                                  ></v-text-field>
                                </div>
                                <div class="textform">
                                  <p>6. Nghề nghiệp</p>

                                  <v-text-field
                                    v-model="record.appointment.profile.job"
                                  ></v-text-field>
                                </div>

                                <div class="textform">
                                  <p>7. Địa chỉ:</p>

                                  <v-text-field
                                    v-model="record.appointment.profile.address"
                                  ></v-text-field>
                                </div>

                                <div class="textform">
                                  <p>8. Tên người giám hộ(Nếu có)</p>

                                  <v-text-field
                                    v-model="
                                      record.appointment.profile.protector
                                    "
                                  ></v-text-field>
                                </div>
                                <div class="textform">
                                  <p>9. SĐT Liên lạc:</p>

                                  <v-text-field
                                    v-model="
                                      record.appointment.profile.phoneNumber
                                    "
                                  ></v-text-field>
                                </div>

                                <div class="textform">
                                  <p>10. CMND:</p>

                                  <v-text-field
                                    v-model="
                                      record.appointment.profile.identityCard
                                    "
                                  ></v-text-field>
                                </div>
                              </div>
                            </div>
                          </div>
                        </v-card>
                        <v-btn color="primary" @click="nextStep(1)">
                          Tiếp tục
                        </v-btn>
                      </v-stepper-content>

                      <v-stepper-content :key="`${2}-content`" :step="2">
                        <v-card class="mb-12" color="white" height="auto">
                          <div class="page2_0">
                            <div class="page2_1">
                              <div class="page2_2">
                                <div class="textpage2">
                                  <p>1. Quá trình bệnh lí:</p>
                                  <v-container fluid>
                                    <v-textarea
                                      v-model="record.pathological"
                                      clearable
                                      clear-icon="mdi-close-circle"
                                      label="Biểu hiện của bệnh"
                                      background-color="grey lighten-2"
                                      color="cyan"
                                      rows="1"
                                      auto-grow
                                      style="
                                        border-radius: 5px;
                                        padding: 0px 0px;
                                      "
                                    ></v-textarea>
                                  </v-container>
                                </div>

                                <div class="textpage2">
                                  <p>2. Tiền sử bệnh bản thân:</p>
                                  <v-container fluid>
                                    <v-textarea
                                      v-model="record.personalMedicalHistory"
                                      clearable
                                      clear-icon="mdi-close-circle"
                                      label="Các bệnh đã mắc"
                                      background-color="grey lighten-2"
                                      color="cyan"
                                      auto-grow
                                      rows="1"
                                      style="
                                        border-radius: 5px;
                                        padding: 0px 0px;
                                      "
                                    ></v-textarea>
                                  </v-container>
                                </div>
                                <div class="textpage2">
                                  <p>3. Tiền sử bệnh gia đình:</p>
                                  <v-container fluid>
                                    <v-textarea
                                      v-model="record.familyMedicalHistory"
                                      clearable
                                      clear-icon="mdi-close-circle"
                                      label="Tiền sử bệnh gia đình"
                                      background-color="grey lighten-2"
                                      color="cyan"
                                      auto-grow
                                      rows="1"
                                      style="
                                        border-radius: 5px;
                                        padding: 0px 0px;
                                      "
                                    ></v-textarea>
                                  </v-container>
                                </div>
                                <div class="textpage2">
                                  <p>4. Khám xét toàn thân:</p>
                                  <v-container fluid>
                                    <v-textarea
                                      v-model="record.bodyInspection"
                                      clearable
                                      clear-icon="mdi-close-circle"
                                      label="Kết quả khám toàn thân"
                                      background-color="grey lighten-2"
                                      color="cyan"
                                      auto-grow
                                      rows="1"
                                      style="
                                        border-radius: 5px;
                                        padding: 0px 0px;
                                      "
                                    ></v-textarea>
                                  </v-container>
                                </div>

                                <div
                                  class="ketquatongquat"
                                  style="
                                    display: flex;
                                    width: 90%;
                                    margin-left: 5%;
                                  "
                                >
                                  <div
                                    class="inline"
                                    style="display: flex; width: 25%"
                                  >
                                    <p
                                      style="
                                        width: 50px !important;
                                        margin-top: 10px;
                                      "
                                    >
                                      Mạch:
                                    </p>
                                    <v-text-field
                                      v-model="record.bloodVessel"
                                      style="width: 20%; padding: 0px"
                                    ></v-text-field>
                                    <p
                                      style="
                                        width: 60px !important;
                                        margin-top: 10px;
                                      "
                                    >
                                      lần/phút
                                    </p>
                                  </div>

                                  <div
                                    class="inline"
                                    style="display: flex; width: 25%"
                                  >
                                    <p
                                      style="
                                        width: 50px !important;
                                        margin-top: 10px;
                                      "
                                    >
                                      Nhiệt độ:
                                    </p>
                                    <v-text-field
                                      v-model="record.temperature"
                                      style="width: 20%; padding: 0px"
                                    ></v-text-field>
                                    <p
                                      style="
                                        width: 60px !important;
                                        margin-top: 10px;
                                      "
                                    >
                                      độ C
                                    </p>
                                  </div>

                                  <div
                                    class="inline"
                                    style="display: flex; width: 25%"
                                  >
                                    <p
                                      style="
                                        width: 50px !important;
                                        margin-top: 10px;
                                      "
                                    >
                                      Huyết áp:
                                    </p>
                                    <v-text-field
                                      v-model="record.bloodPressure"
                                      style="width: 20%; padding: 0px"
                                    ></v-text-field>
                                    <p
                                      style="
                                        width: 60px !important;
                                        margin-top: 10px;
                                      "
                                    >
                                      mmHg
                                    </p>
                                  </div>

                                  <div
                                    class="inline"
                                    style="display: flex; width: 25%"
                                  >
                                    <p
                                      style="
                                        width: 50px !important;
                                        margin-top: 10px;
                                      "
                                    >
                                      Nhịp tim:
                                    </p>
                                    <v-text-field
                                      v-model="record.heartbeat"
                                      style="width: 20%; padding: 0px"
                                    ></v-text-field>
                                    <p
                                      style="
                                        width: 60px !important;
                                        margin-top: 10px;
                                      "
                                    >
                                      lần/phút
                                    </p>
                                  </div>
                                </div>

                                <div class="textpage2">
                                  <p>5. Khám xét các bộ phận</p>
                                  <v-container fluid>
                                    <v-textarea
                                      v-model="record.partsInspection"
                                      clearable
                                      clear-icon="mdi-close-circle"
                                      label="Kết quả khám bộ phận"
                                      background-color="grey lighten-2"
                                      color="cyan"
                                      auto-grow
                                      rows="1"
                                      style="
                                        border-radius: 5px;
                                        padding: 0px 0px;
                                      "
                                    ></v-textarea>
                                  </v-container>
                                </div>

                                <div class="textpage2">
                                  <p>6. Tóm tắt kết quả lâm sàng:</p>
                                  <v-container fluid>
                                    <v-textarea
                                      v-model="record.summary"
                                      clearable
                                      clear-icon="mdi-close-circle"
                                      label="Chuẩn đoán lâm sàng"
                                      background-color="grey lighten-2"
                                      color="cyan"
                                      auto-grow
                                      rows="1"
                                      style="
                                        border-radius: 5px;
                                        padding: 0px 0px;
                                      "
                                    ></v-textarea>
                                  </v-container>
                                </div>

                                <div class="textpage2">
                                  <p>7. Hình ảnh bổ sung:</p>
                                  <v-file-input
                                    v-model="record.files"
                                    label="File input"
                                    filled
                                    multiple
                                    prepend-icon="mdi-camera"
                                  ></v-file-input>
                                </div>
                              </div>
                            </div>
                          </div>
                        </v-card>

                        <v-btn text @click="beforeStep(2)"> Quay lại </v-btn>
                        <v-btn color="primary" @click="nextStep(2)">
                          Tiếp tục
                        </v-btn>
                      </v-stepper-content>

                      <v-stepper-content :key="`${3}-content`" :step="3">
                        <v-card class="mb-12" color="white" height="auto">
                          <div class="page2_0">
                            <div class="page2_1">
                              <div class="page2_2">
                                <div class="textpage2">
                                  <p>CHUẨN ĐOÁN NHẬP VIỆN:</p>
                                  <v-checkbox
                                    v-model="record.hospitalize"
                                    label="Yều cầu nhập viện"
                                  ></v-checkbox>
                                </div>

                                <div class="textpage2">
                                  <p>KHOA ĐIỀU TRỊ:</p>
                                  <v-autocomplete
                                    v-model="record.facultyTreatment"
                                    :items="dskhoa"
                                    dense
                                    filled
                                  ></v-autocomplete>
                                </div>

                                <div class="textpage2">
                                  <p>KÊ ĐƠN THUỐC:</p>
                                  <div class="textpage3">
                                    <div class="textpage4">
                                      <div
                                        class="ketquatongquat"
                                        v-for="(thuoc_1, k) in record.medicines"
                                        :key="k"
                                      >
                                        <p
                                          style="width:100%; text-align: left;:"
                                        >
                                          <i> {{ k + 1 }}</i>
                                        </p>
                                        <v-autocomplete
                                          v-model="thuoc_1.nameMedicine"
                                          :items="dsthuoc"
                                          dense
                                          filled
                                          label="Tên thuốc"
                                        ></v-autocomplete>
                                        <div class="combosoluongloai">
                                          <div class="soluong">
                                            <p
                                              style="
                                                float: left;
                                                margin-top: 15px;
                                                margin-right: 10px;
                                              "
                                            >
                                              Số lượng:
                                            </p>

                                            <v-text-field
                                              v-model="thuoc_1.numberMedicine"
                                              style="width: 30%"
                                            ></v-text-field>
                                          </div>

                                          <div class="loai">
                                            <v-autocomplete
                                              v-model="thuoc_1.typeMedicine"
                                              :items="dsloai"
                                              dense
                                              filled
                                              label="loại"
                                            ></v-autocomplete>
                                          </div>
                                        </div>
                                        <div class="cachdung">
                                          <v-autocomplete
                                            v-model="thuoc_1.useMedicine"
                                            :items="dscachdung"
                                            dense
                                            chips
                                            small-chips
                                            label="Cách dùng"
                                            multiple
                                            solo
                                          ></v-autocomplete>
                                        </div>
                                        <v-btn
                                          block
                                          @click="removedonthuoc(medicines, k)"
                                        >
                                          Xóa
                                        </v-btn>

                                        <v-divider
                                          style="margin-bottom: 20px"
                                        ></v-divider>
                                      </div>
                                    </div>
                                  </div>

                                  <div class="controls">
                                    <v-btn
                                      block
                                      style="width: 100%"
                                      @click="addnewdonthuoc(medicines)"
                                    >
                                      Thêm mới
                                    </v-btn>
                                  </div>
                                  <v-divider
                                    style="margin-bottom: 20px"
                                  ></v-divider>
                                </div>

                                <div class="textpage2">
                                  <p>LƯU Ý:</p>
                                  <v-container fluid>
                                    <v-textarea
                                      v-model="record.notes"
                                      clearable
                                      clear-icon="mdi-close-circle"
                                      background-color="grey lighten-2"
                                      color="cyan"
                                      auto-grow
                                      rows="3"
                                      style="
                                        border-radius: 5px;
                                        padding: 0px 0px;
                                      "
                                    ></v-textarea>
                                  </v-container>
                                </div>

                                <div class="textpage2">
                                  <p>NGÀY TÁI KHÁM:</p>

                                  <v-menu
                                    v-model="menu10"
                                    :close-on-content-click="false"
                                    :nudge-right="40"
                                    transition="scale-transition"
                                    offset-y
                                    min-width="auto"
                                  >
                                    <template v-slot:activator="{ on, attrs }">
                                      <v-text-field
                                        v-model="record.reExaminationDate"
                                        prepend-icon="mdi-calendar"
                                        readonly
                                        v-bind="attrs"
                                        v-on="on"
                                      ></v-text-field>
                                    </template>
                                    <v-date-picker
                                      v-model="record.reExaminationDate"
                                      @input="menu10 = false"
                                    ></v-date-picker>
                                  </v-menu>
                                </div>
                              </div>
                            </div>
                          </div>
                        </v-card>

                        <v-btn text @click="beforeStep(3)"> Quay Lại </v-btn>
                        <v-btn color="primary"> Lưu bệnh án </v-btn>
                      </v-stepper-content>
                    </v-stepper-items>
                  </v-stepper>

                  <!-- form bệnh án ở đây ----------------------------------------------------------- -->
                  <!-- form bệnh án ở đây ----------------------------------------------------------- -->

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="closegetnew">
                      Hủy
                    </v-btn>
                    <v-btn color="blue darken-1" text @click="save">
                      Lưu
                    </v-btn>
                  </v-card-actions>
                </v-form>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>
        <!-- <template v-slot:[`item.actions`]="{ item }">
          <v-icon small class="mr-2" @click="editItem(item)">
            mdi-pencil
          </v-icon>
          <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
        </template> -->
      </v-data-table>

      <v-dialog v-model="dialogDelete" max-width="700px" persistent>
        <v-form>
          <v-card>
            <v-card-title class="text-h5">
              Bạn chắc chắn muốn xóa bệnh án? <br />
            </v-card-title>
            <v-card-text>
              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left">STT</th>
                      <th class="text-left">Họ tên</th>
                      <th class="text-left">Ngày sinh</th>
                      <th class="text-left">Số điện thoại</th>
                      <th class="text-left">Bác sĩ</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(recordItem, k) in selected" :key="k">
                      <td>{{ k + 1 }}</td>
                      <td>{{ recordItem.appointment.profile.name }}</td>
                      <td>{{ recordItem.appointment.profile.dob }}</td>
                      <td>{{ recordItem.appointment.profile.phoneNumber }}</td>
                      <td>{{ recordItem.appointment.doctor.name }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>

              <!-- <v-text-field
                v-model="dlItem.appointment.profile.name"
                label="Họ và tên"
                readonly
              ></v-text-field>
              <v-text-field
                v-model="dlItem.appointment.profile.dob"
                label="Ngày sinh"
                readonly
              ></v-text-field>
              <v-text-field
                v-model="dlItem.appointment.profile.phoneNumber"
                label="Số điện thoại"
                readonly
              ></v-text-field>
                <v-text-field
                v-model="dlItem.appointment.doctor.name"
                label="Tên bác sĩ khám"
                readonly
              ></v-text-field> -->
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
        </v-form>
      </v-dialog>

      <v-dialog v-model="dialogedit" max-width="600px" persistent> </v-dialog>
    </v-card>
  </div>
</template>

<script>
export default {
  data: () => ({
    e1: 1,
    menu11: false,
    menu12: false,

    nulltime: null,
    timeselect: [
      "7:00-8:00",
      "8:00-9:00",
      "9:00-10:00",
      "10:00-11:00",
      "11:00-12:00",
      "12:00-13:00",
      "14:00-15:00",
      "15:00-16:00",
    ],
    dskhoa: [
      "Khoa Nội",
      "Khoa Ngoại",
      "Khoa Phụ Sản",
      "Khoa Nhi",
      "Khoa Truyền Nhiễm",
      "Khoa Cấp Cứu",
      "Khoa Hồi Sức và Chống Độc",
      "Khoa Ung Bướu",
    ],
    states: ["Kinh", "Thái", "Mường", "Dao", "GiaRai"],
    search1: null,
    dsthuoc: ["Thuốc A", "Thuốc B"],
    dsloai: ["Viên", "Vỉ", "Hộp"],
    dscachdung: [
      "Sáng-1v",
      "Sáng-2v",
      "Trưa-1v",
      "Trưa-2v",
      "Tối-1v",
      "Tối-2v",
    ],

    menu10: false,

    menu2: false,
    nameTitle: "",

    search: "",
    selected: [],

    dialogNewrecord: false,
    dialogDelete: false,
    dialogedit: false,
    indexdl: null,
    indexedit: null,
    headers: [
      {
        text: "Tên bệnh nhân",
        align: "start",
        sortable: false,
        value: "appointment.profile.name",
      },
      { text: "ID bệnh án", value: "idrecord", sortable: false },
      {
        text: "Số điện thoại",
        value: "appointment.profile.phoneNumber",
        sortable: false,
      },
      { text: "Tên bác sĩ", value: "appointment.doctor.name", sortable: false },
      { text: "Khoa điều trị", value: "facultyTreatment", sortable: false },
      { text: "Ngày khám", value: "appointment.date", sortable: false },
      { text: "Ngày tái khám", value: "reExaminationDate", sortable: false },
    ],
    desserts: [
      {
        id: 1,
        idrecord: 1,
        appointment: {
          id: 1,
          stt: 1,
          room: "h2-93",
          profile: {
            id: 1,
            profileNumber: 1,
            name: "Nguyễn Ngọc Tân",
            address: "20 lương thạnh",
            phoneNumber: "123456789",
            dob: "2000-06-18",
            job: "sv",
            identityCard: "123456789",
            healthInsurance: "123456",
            folk: "Kinh",
            gender: "Nam",
            protector: "bố mỹ",
          },
          doctor: {
            id: 1,
            name: "Dr.Tan",
            specialty: "Khoa Nhi",
            level: "Đại học",
          },
          date: "2022-04-06",
          time: "9:00-10:00",
          symptom: "dau",
          description: "dauqua",
          timeSituation: "1 ngày",
          selfTreatment: true,
          files: [],
        },
        pathological: "đau 1",
        personalMedicalHistory: "đau 2",
        familyMedicalHistory: "đau 3",
        bodyInspection: "đau 4",
        bloodVessel: "120",
        temperature: "37.5",
        bloodPressure: "100",
        heartbeat: "110",
        summary: "bình thường",
        partsInspection: "phù nề",
        hospitalize: true,
        facultyTreatment: "Khoa Nhi",
        medicines: [
          {
            nameMedicine: "Thuốc A",
            numberMedicine: "2",
            typeMedicine: "Hộp",
            useMedicine: ["Sáng-1v", "Trưa-1v", "Tối-1v"],
          },
          {
            nameMedicine: "Thuốc B",
            numberMedicine: "2",
            typeMedicine: "Hộp",
            useMedicine: ["Sáng-1v", "Trưa-2v", "Tối-1v"],
          },
        ],
        notes: "cẩn thận",
        reExaminationDate: "2022-05-06",
        files: [],
      },

      {
        id: 2,
        idrecord: 2,
        appointment: {
          id: 1,
          stt: 1,
          room: "h10-3",
          profile: {
            id: 1,
            profileNumber: 1,
            name: "Nguyễn thị nụ",
            address: "quảng bình",
            phoneNumber: "987654321",
            dob: "2000-06-18",
            job: "sv",
            identityCard: "123456789",
            healthInsurance: "123456",
            folk: "Kinh",
            gender: "Nữ",
            protector: "bố mỹ",
          },
          doctor: {
            id: 1,
            name: "Dr.Tan",
            specialty: "Khoa Nhi",
            level: "Đại học",
          },
          date: "2022-04-06",
          time: "9:00-10:00",
          symptom: "dau",
          description: "dauqua",
          timeSituation: "1 ngày",
          selfTreatment: true,
          files: [],
        },
        pathological: "đau 1",
        personalMedicalHistory: "đau 2",
        familyMedicalHistory: "đau 3",
        bodyInspection: "đau 4",
        bloodVessel: "120",
        temperature: "37.5",
        bloodPressure: "100",
        heartbeat: "110",
        summary: "bình thường",
        partsInspection: "phù nề",
        hospitalize: true,
        facultyTreatment: "Khoa Nhi",
        medicines: [
          {
            nameMedicine: "Thuốc A",
            numberMedicine: "2",
            typeMedicine: "Hộp",
            useMedicine: ["Sáng-1v", "Trưa-1v", "Tối-1v"],
          },
        ],
        notes: "cẩn thận",
        reExaminationDate: "2022-05-06",
        files: [],
      },
    ],

    // dlItem: {
    //   id: null,
    //   idrecord: null,
    //   appointment: {
    //     id: null,
    //     stt: null,
    //     room: null,
    //     profile: {
    //       id: null,
    //       profileNumber: null,
    //       name: null,
    //       address: null,
    //       phoneNumber: null,
    //       dob: null,
    //       job: null,
    //       identityCard: null,
    //       healthInsurance: null,
    //       folk: null,
    //       gender: null,
    //       protector: null,
    //     },
    //     doctor: {
    //       id: null,
    //       name: null,
    //       specialty: null,
    //       level: null,
    //     },
    //     date: null,
    //     time: null,
    //     symptom: null,
    //     description: null,
    //     timeSituation: null,
    //     selfTreatment: null,
    //     files: [],
    //   },
    //   pathological: null,
    //   personalMedicalHistory: null,
    //   familyMedicalHistory: null,
    //   bodyInspection: null,
    //   bloodVessel: null,
    //   temperature: null,
    //   bloodPressure: null,
    //   heartbeat: null,
    //   summary: null,
    //   partsInspection: null,
    //   hospitalize: null,
    //   facultyTreatment: null,
    //   medicines: [
    //     {
    //       nameMedicine: null,
    //       numberMedicine: null,
    //       typeMedicine: null,
    //       useMedicine: null,
    //     },
    //   ],
    //   notes: null,
    //   reExaminationDate: null,
    //   files: [],
    // },
    record: {
      id: null,
      idrecord: null,
      appointment: {
        id: null,
        stt: null,
        room: null,
        profile: {
          id: null,
          profileNumber: null,
          name: null,
          address: null,
          phoneNumber: null,
          dob: null,
          job: null,
          identityCard: null,
          healthInsurance: null,
          folk: null,
          gender: null,
          protector: null,
        },
        doctor: {
          id: null,
          name: null,
          specialty: null,
          level: null,
        },
        date: null,
        time: null,
        symptom: null,
        description: null,
        timeSituation: null,
        selfTreatment: null,
        files: [],
      },
      pathological: null,
      personalMedicalHistory: null,
      familyMedicalHistory: null,
      bodyInspection: null,
      bloodVessel: null,
      temperature: null,
      bloodPressure: null,
      heartbeat: null,
      summary: null,
      partsInspection: null,
      hospitalize: null,
      facultyTreatment: null,
      medicines: [
        {
          nameMedicine: null,
          numberMedicine: null,
          typeMedicine: null,
          useMedicine: null,
        },
      ],
      notes: null,
      reExaminationDate: null,
      files: [],
    },
  }),

  watch: {},
  created() {},
  methods: {
    editItem() {
      this.nameTitle = "Chỉnh sửa thông tin";
      this.dialogNewrecord = true;

      this.record.id = this.selected[0].id;
      this.record.idrecord = this.selected[0].idrecord;

      this.record.appointment.id = this.selected[0].appointment.id;
      this.record.appointment.stt = this.selected[0].appointment.stt;
      this.record.appointment.room = this.selected[0].appointment.room;

      this.record.appointment.profile.id =
        this.selected[0].appointment.profile.id;
      this.record.appointment.profile.profileNumber =
        this.selected[0].appointment.profile.profileNumber;
      this.record.appointment.profile.name =
        this.selected[0].appointment.profile.name;
      this.record.appointment.profile.address =
        this.selected[0].appointment.profile.address;
      this.record.appointment.profile.phoneNumber =
        this.selected[0].appointment.profile.phoneNumber;
      this.record.appointment.profile.dob =
        this.selected[0].appointment.profile.dob;
      this.record.appointment.profile.job =
        this.selected[0].appointment.profile.job;
      this.record.appointment.profile.identityCard =
        this.selected[0].appointment.profile.identityCard;
      this.record.appointment.profile.healthInsurance =
        this.selected[0].appointment.profile.healthInsurance;
      this.record.appointment.profile.folk =
        this.selected[0].appointment.profile.folk;
      this.record.appointment.profile.gender =
        this.selected[0].appointment.profile.gender;
      this.record.appointment.profile.protector =
        this.selected[0].appointment.profile.protector;

      this.record.appointment.doctor.id =
        this.selected[0].appointment.doctor.id;
      this.record.appointment.doctor.name =
        this.selected[0].appointment.doctor.name;
      this.record.appointment.doctor.specialty =
        this.selected[0].appointment.doctor.specialty;
      this.record.appointment.doctor.level =
        this.selected[0].appointment.doctor.level;

      this.record.appointment.date = this.selected[0].appointment.date;
      this.record.appointment.time = this.selected[0].appointment.time;
      this.record.appointment.symptom = this.selected[0].appointment.symptom;
      this.record.appointment.description =
        this.selected[0].appointment.description;
      this.record.appointment.timeSituation =
        this.selected[0].appointment.timeSituation;
      this.record.appointment.selfTreatment =
        this.selected[0].appointment.selfTreatment;
      this.record.appointment.files = this.selected[0].appointment.files;

      this.record.pathological = this.selected[0].pathological;
      this.record.personalMedicalHistory =
        this.selected[0].personalMedicalHistory;
      this.record.familyMedicalHistory = this.selected[0].familyMedicalHistory;
      this.record.bodyInspection = this.selected[0].bodyInspection;
      this.record.bloodVessel = this.selected[0].bloodVessel;
      this.record.temperature = this.selected[0].temperature;
      this.record.bloodPressure = this.selected[0].bloodPressure;
      this.record.heartbeat = this.selected[0].heartbeat;
      this.record.summary = this.selected[0].summary;
      this.record.partsInspection = this.selected[0].partsInspection;
      this.record.hospitalize = this.selected[0].hospitalize;
      this.record.facultyTreatment = this.selected[0].facultyTreatment;
      this.record.medicines = this.selected[0].medicines;
      this.record.notes = this.selected[0].notes;
      this.record.reExaminationDate = this.selected[0].reExaminationDate;
      this.record.files = this.selected[0].files;
    },
    // deleteItem(item) {
    //   this.dialogDelete = true;
    //   this.indexdl = this.desserts.indexOf(item);

    //    this.dlItem.id = this.desserts[this.indexdl].id;
    //   this.dlItem.idrecord = this.desserts[this.indexdl].idrecord;

    //   this.dlItem.appointment.id = this.desserts[this.indexdl].appointment.id;
    //   this.dlItem.appointment.stt =
    //     this.desserts[this.indexdl].appointment.stt;
    //   this.dlItem.appointment.room =
    //     this.desserts[this.indexdl].appointment.room;

    //   this.dlItem.appointment.profile.id =
    //     this.desserts[this.indexdl].appointment.profile.id;
    //   this.dlItem.appointment.profile.profileNumber =
    //     this.desserts[this.indexdl].appointment.profile.profileNumber;
    //   this.dlItem.appointment.profile.name =
    //     this.desserts[this.indexdl].appointment.profile.name;
    //   this.dlItem.appointment.profile.address =
    //     this.desserts[this.indexdl].appointment.profile.address;
    //   this.dlItem.appointment.profile.phoneNumber =
    //     this.desserts[this.indexdl].appointment.profile.phoneNumber;
    //   this.dlItem.appointment.profile.dob =
    //     this.desserts[this.indexdl].appointment.profile.dob;
    //   this.dlItem.appointment.profile.job =
    //     this.desserts[this.indexdl].appointment.profile.job;
    //   this.dlItem.appointment.profile.identityCard =
    //     this.desserts[this.indexdl].appointment.profile.identityCard;
    //   this.dlItem.appointment.profile.healthInsurance =
    //     this.desserts[this.indexdl].appointment.profile.healthInsurance;
    //   this.dlItem.appointment.profile.folk =
    //     this.desserts[this.indexdl].appointment.profile.folk;
    //   this.dlItem.appointment.profile.gender =
    //     this.desserts[this.indexdl].appointment.profile.gender;
    //   this.dlItem.appointment.profile.protector =
    //     this.desserts[this.indexdl].appointment.profile.protector;

    //   this.dlItem.appointment.doctor.id =
    //     this.desserts[this.indexdl].appointment.doctor.id;
    //   this.dlItem.appointment.doctor.name =
    //     this.desserts[this.indexdl].appointment.doctor.name;
    //   this.dlItem.appointment.doctor.specialty =
    //     this.desserts[this.indexdl].appointment.doctor.specialty;
    //   this.dlItem.appointment.doctor.level =
    //     this.desserts[this.indexdl].appointment.doctor.level;

    //   this.dlItem.appointment.date =
    //     this.desserts[this.indexdl].appointment.date;
    //   this.dlItem.appointment.time =
    //     this.desserts[this.indexdl].appointment.time;
    //   this.dlItem.appointment.symptom =
    //     this.desserts[this.indexdl].appointment.symptom;
    //   this.dlItem.appointment.description =
    //     this.desserts[this.indexdl].appointment.description;
    //   this.dlItem.appointment.timeSituation =
    //     this.desserts[this.indexdl].appointment.timeSituation;
    //   this.dlItem.appointment.selfTreatment =
    //     this.desserts[this.indexdl].appointment.selfTreatment;
    //   this.dlItem.appointment.files =
    //     this.desserts[this.indexdl].appointment.files;

    //   this.dlItem.pathological = this.desserts[this.indexdl].pathological;
    //   this.dlItem.personalMedicalHistory =
    //     this.desserts[this.indexdl].personalMedicalHistory;
    //   this.dlItem.familyMedicalHistory =
    //     this.desserts[this.indexdl].familyMedicalHistory;
    //   this.dlItem.bodyInspection = this.desserts[this.indexdl].bodyInspection;
    //   this.dlItem.bloodVessel = this.desserts[this.indexdl].bloodVessel;
    //   this.dlItem.temperature = this.desserts[this.indexdl].temperature;
    //   this.dlItem.bloodPressure = this.desserts[this.indexdl].bloodPressure;
    //   this.dlItem.heartbeat = this.desserts[this.indexdl].heartbeat;
    //   this.dlItem.summary = this.desserts[this.indexdl].summary;
    //   this.dlItem.partsInspection =
    //     this.desserts[this.indexdl].partsInspection;
    //   this.dlItem.hospitalize = this.desserts[this.indexdl].hospitalize;
    //   this.dlItem.facultyTreatment =
    //     this.desserts[this.indexdl].facultyTreatment;
    //   this.dlItem.medicines = this.desserts[this.indexdl].medicines;
    //   this.dlItem.notes = this.desserts[this.indexdl].notes;
    //   this.dlItem.reExaminationDate =
    //     this.desserts[this.indexdl].reExaminationDate;
    //   this.dlItem.files = this.desserts[this.indexdl].files;
    // },
    deleteItemConfirm() {},

    closegetnew() {
      this.dialogNewrecord = false;
    },
    closeDelete() {
      this.dialogDelete = false;
    },
    save() {},

    opennewRecord() {
      this.nameTitle = "Tạo bệnh án mới";

      this.dialogNewrecord = true;
      this.$refs.form.reset();
    },
    nextStep(n) {
      if (n === this.steps) {
        this.e1 = 3;
      } else {
        this.e1 = n + 1;
      }
    },
    beforeStep(n) {
      if (n === this.steps) {
        this.e1 = n - 1;
      } else {
        this.e1 = 1;
      }
    },
    addnewdonthuoc(medicines) {
      this.record.medicines.push({});
    },
    removedonthuoc(medicines, k) {
      this.record.medicines.splice(k, 1);
    },
  },
};
</script>

<style lang="scss" scoped>
.gop {
  width: 100%;
  background-color: rgba(201, 198, 195, 0.671);
}
.gop0 {
  width: 90%;

  margin-left: auto;
  margin-right: auto;
  background-color: #ffffff;
}
.gop1 {
  width: 100%;
}

.textform {
  width: 70%;
  margin-left: auto;
  margin-right: auto;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  align-content: center;
}
.textform p {
  width: 15%;
  height: 34px;
  font-size: 16px;
  margin: 20px 0px 10px 0px;
  min-width: 250px;
  align-items: center;
}
.textform .inputtext {
  min-width: 300px;
  height: 40px;
}

.textform input {
  background-color: #ffffff;
  color: #000000;
  height: 38px;
  margin: 20px 0px 10px 0px;
  border-radius: 15px;
  width: 100%;
  font-size: 16px;
  padding: 10px 20px;
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
}

.textform input:focus {
  box-shadow: rgb(255, 255, 255) 0px 3px 8px;
  border-style: ridge;
  border-color: rgb(104, 101, 101);
}

.page2_0 {
  width: 100%;
  background-color: rgba(201, 198, 195, 0.671);
}
.page2_1 {
  width: 90%;

  margin-left: auto;
  margin-right: auto;
  background-color: #ffffff;
}

.textpage2 {
  margin: 0px 20px;
  width: auto;
}

.textpage2 p {
  width: auto;
  height: 34px;
  font-size: 16px;
  margin: 0px 0px;

  align-items: center;
}

.loai {
  width: 30%;
  float: left;
  margin-right: 30px;
}

.cachdung {
  width: 90%;
  margin: 10px 20px;
}
</style>
