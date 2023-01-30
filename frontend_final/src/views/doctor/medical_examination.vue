<template>
  <div>
    <v-card class="fill-height d-flex flex-column pa-8">
      <v-footer color="#3C5E7E" padless class="d-flex justify-center pa-5">
        <p class="ma-0 white--text" style="font-size: 20px">
          Danh sách khám
        </p>
      </v-footer>
      <v-data-table
        v-model="selected"
        :headers="headers_appointment"
        :items="list_appointment"
        :single-select="true"
        item-key="id"
        show-select
        class="elevation-1"
        checkbox-color="#3C5E7E"
        :search="search"
        :loading="loading"
        :no-data-text="'Không có danh sách khám'"
        :footer-props="{
          'items-per-page-options': [10, 20, 50]
        }"
        @pagination="pagination = $event"
        :server-items-length="totalPages"
        @update:page="updatePage()"
      >
        <template v-slot:top>
          <div class="pa-6 d-flex flex-row align-center">
            <v-card outlined width="50%">
              <v-text-field
                v-model="search"
                label="Tìm kiếm"
                prepend-inner-icon="mdi-magnify"
                solo
                flat
                hide-details=""
              ></v-text-field>
            </v-card>
            <v-spacer></v-spacer>
            <v-btn
              class="mr-3 white--text btn font-weight-medium text-body-1"
              elevation="0"
              color="#476D92"
              :disabled="_.isEmpty(selected)"
              @click.stop="examinate()"
            >
              <v-icon medium class="mr-2">mdi-medication</v-icon>
              Khám</v-btn
            >
            <v-menu :close-on-content-click="false">
              <template v-slot:activator="{ on, attrs }">
                <v-icon v-bind="attrs" v-on="on" medium class="mr-2"
                  >mdi-filter-variant</v-icon
                >
              </template>
              <v-card width="300px">
                <div class="d-flex justify-center" style="color: #537DA5">
                  <p class="font-weight-bold mt-3">Lọc Kết Quả</p>
                </div>
                <v-divider></v-divider>
                <v-list>
                  <v-list-item class="mt-3">
                    <v-autocomplete
                      prepend-inner-icon="mdi-map-marker"
                      item-text="text"
                      item-value="text"
                      label="Địa Điểm"
                      clearable
                      dense
                      outlined
                      :menu-props="{ offsetY: true }"
                      placeholder="Tìm địa điểm"
                    ></v-autocomplete>
                  </v-list-item>
                  <div class="d-flex justify-center mt-3">
                    <v-btn
                      color="#537DA5"
                      elevation="0"
                      class="white--text btn font-weight-medium text-body-1"
                      width="90%"
                      style="margin: 0 auto"
                      @click="clearFilters()"
                    >
                      Bỏ lọc
                    </v-btn>
                  </div>
                </v-list>
              </v-card>
            </v-menu>
          </div>
        </template>
        <template v-slot:[`item.profile.fullName`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">
              {{ item.profile.lastName }} {{ item.profile.firstName }}
            </p>
          </div>
        </template>
        <template v-slot:[`item.status`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p
              class="ma-0 font-weight-medium text-body-2"
              style="color: #667085"
              v-if="item.status == 'PENDING'"
            >
              <v-chip text-color="white" color="orange" small>
                Chờ duyệt
              </v-chip>
            </p>
            <p
              v-else-if="item.status == 'CANCEL'"
              class="ma-0 font-weight-medium text-body-2"
              style="color: red"
            >
              <v-chip small color="red" text-color="white">
                Đã hủy
              </v-chip>
            </p>
            <p
              class="ma-0 font-weight-medium text-body-2"
              style="color: #667085"
              v-else-if="item.status === 'PROCESS'"
            >
              <v-chip color="primary" small>
                Chưa tiến hành
              </v-chip>
            </p>

            <p
              class="ma-0 font-weight-medium text-body-2"
              style="color: #12B76A"
              v-else-if="item.status === 'COMPLETE'"
            >
              <v-chip small color="green" text-color="white">
                Đã hoàn tất
              </v-chip>
            </p>
          </div>
        </template>
        <template v-slot:[`item.symptom`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p v-if="!item.symptom.length" class="ma-0">
              {{ item.symptom | empty }}
            </p>
            <p class="d-flex align-center ma-0" v-else>
              <v-menu :close-on-content-click="false">
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                    v-bind="attrs"
                    v-on="on"
                    class="d-flex align-center"
                    meidum
                    color="#537da5"
                    >mdi-comment-text-outline</v-icon
                  >
                </template>
                <v-card max-width="300px">
                  <v-list>
                    <v-list-tile-content>
                      <p class="pa-3">{{ item.symptom }}</p>
                    </v-list-tile-content>
                  </v-list>
                </v-card>
              </v-menu>
            </p>
          </div>
        </template>
        <template v-slot:[`item.profile.dob`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">{{ convert_date(item.profile.dob) }}</p>
          </div>
        </template>
        <template v-slot:[`item.profile.phone`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">{{ item.profile.phone | phone }}</p>
          </div>
        </template>
        <template v-slot:[`item.date`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">{{ convert_date(item.date) }}</p>
          </div>
        </template>
        <template v-slot:[`item.room`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">{{ item.room.name }}</p>
            <v-icon
              v-if="item.type === 'ONLINE'"
              class="ml-2"
              @click="go_to_room(item.room.link)"
              >mdi-video-outline</v-icon
            >
          </div>
        </template>
      </v-data-table>
      <v-dialog v-model="exam_confirm_dialog" width="408px">
        <v-card height="192px" class="d-flex flex-column">
          <div class="d-flex flex-column mt-8 ml-8">
            <p style="font-size: 24px" class="font-weight-bold">
              Xác nhận hoàn tất khám
            </p>
            <p style="color: #667085">
              Bạn có muốn hoàn tất khám hồ sơ này không?
            </p>
          </div>
          <div class="d-flex flex-row justify-space-between ml-8 mr-8">
            <v-btn
              class="btn font-weight-medium"
              width="160px"
              outlined
              color="primary"
              @click="exam_confirm_dialog = false"
              :disabled="loadingConfirm"
              >Huỷ bỏ</v-btn
            >
            <v-btn
              class="btn white--text font-weight-medium"
              width="160px"
              color="#537DA5"
              elevation="0"
              @click="create_record"
              :loading="loadingConfirm"
              >Xác nhận</v-btn
            >
          </div>
        </v-card>
      </v-dialog>
      <v-dialog fullscreen v-model="exam_dialog" v-if="exam_dialog">
        <v-card>
          <v-footer
            color="#314E6A"
            class="white--text d-flex flex-row"
            height="50px"
            tile
          >
            <p class="ma-0">Tiến Hành Khám Bệnh</p>
            <v-spacer></v-spacer>
            <v-btn
              color="white"
              class="mr-5"
              elevation="0"
              outlined
              @click.stop="stop_examination()"
            >
              Hủy
            </v-btn>
            <v-btn
              color="white"
              elevation="0"
              outlined
              @click.stop="exam_confirm_dialog = true"
            >
              Lưu
            </v-btn>
          </v-footer>
          <v-card elevation="0">
            <v-tabs color="#314E6A">
              <v-tab style="text-transform: none;"> Thông Tin Khám </v-tab>
              <v-tab style="text-transform: none;"> Lịch Sử Khám </v-tab>
              <v-tab style="text-transform: none;"> Khám Bệnh</v-tab>
              <v-tab style="text-transform: none;">Kê Đơn</v-tab>
              <!-- profile -->
              <v-tab-item>
                <v-card flat class="d-flex flex-column pa-6">
                  <p class="font-weight-bold text-body-1">
                    Thông tin bệnh nhân
                  </p>
                  <!-- last name, first name, dob -->
                  <div class="d-flex flex-row justify-space-between">
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <!-- <p class="text-body-2 ma-0 font-weight-medium">
                        Họ và tên lót:
                      </p> -->
                      <v-text-field
                        label="Họ và tên lót"
                        class="text-body-2"
                        v-model="selected_appointment.profile.lastName"
                        readonly
                        outlined
                        background-color="#EEF2F6"
                      ></v-text-field>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <!-- <p class="text-body-2 ma-0 font-weight-medium">Tên:</p> -->
                      <v-text-field
                        label="Tên"
                        class="text-body-2"
                        v-model="selected_appointment.profile.firstName"
                        readonly
                        outlined
                        background-color="#EEF2F6"
                      ></v-text-field>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <!-- <p class="text-body-2 ma-0 font-weight-medium">
                        Ngày sinh
                      </p> -->
                      <v-text-field
                        label="Ngày sinh"
                        class="text-body-2"
                        v-model="selected_appointment.profile.dob"
                        readonly
                        outlined
                        background-color="#EEF2F6"
                      ></v-text-field>
                    </v-card>
                  </div>

                  <!-- job, gender, phone -->
                  <div class="d-flex flex-row justify-space-between">
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <!-- <p class="text-body-2 ma-0 font-weight-medium">
                        Nghề nghiệp:
                      </p> -->
                      <v-text-field
                        label="Nghề nghiệp"
                        class="text-body-2"
                        v-model="selected_appointment.profile.job"
                        readonly
                        outlined
                        background-color="#EEF2F6"
                      ></v-text-field>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <!-- <p class="text-body-2 ma-0 font-weight-medium">
                        Giới tính:
                      </p> -->
                      <v-text-field
                        label="Giới tính"
                        class="text-body-2"
                        v-model="selected_appointment.profile.gender"
                        readonly
                        outlined
                        background-color="#EEF2F6"
                      ></v-text-field>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <!-- <p class="text-body-2 ma-0 font-weight-medium">
                        Số điện thoại
                      </p> -->
                      <v-text-field
                        label="Số điện thoại"
                        class="text-body-2"
                        v-model="selected_appointment.profile.phone"
                        readonly
                        outlined
                        background-color="#EEF2F6"
                      ></v-text-field>
                    </v-card>
                  </div>

                  <!-- email, address -->
                  <div class="d-flex flex-row justify-space-between">
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <!-- <p class="text-body-2 ma-0 font-weight-medium">Email:</p> -->
                      <v-text-field
                        label="Email"
                        class="text-body-2"
                        v-model="selected_appointment.profile.email"
                        readonly
                        outlined
                        background-color="#EEF2F6"
                      ></v-text-field>
                    </v-card>
                    <v-card
                      width="65%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <!-- <p class="text-body-2 ma-0 font-weight-medium">
                        Địa chỉ:
                      </p> -->
                      <v-text-field
                        label="Địa chỉ"
                        class="text-body-2"
                        v-model="selected_appointment.profile.address"
                        readonly
                        outlined
                        background-color="#EEF2F6"
                      ></v-text-field>
                    </v-card>
                  </div>

                  <!-- appointment profile -->
                  <p class="font-weight-bold text-body-1">
                    Thông tin triệu chứng
                  </p>
                  <!--  -->
                  <div class="d-flex flex-row justify-space-between">
                    <v-card
                      width="65%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <!-- <p class="text-body-2 ma-0 font-weight-medium">
                        Triệu chứng:
                      </p> -->
                      <v-textarea
                        label="Triệu chứng"
                        class="text-body-2"
                        v-model="selected_appointment.symptom"
                        readonly
                        outlined
                        background-color="#EEF2F6"
                      ></v-textarea>
                    </v-card>
                  </div>
                  <p class="font-weight-bold text-body-1 ma-0">
                    Kết quả xét nghiệm
                  </p>
                  <div class="d-flex flex-wrap">
                    <v-card
                      elevation="0"
                      class="d-flex flex-row pa-2 mr-2"
                      color="#EEF2F6"
                      v-for="file in selected_appointment.files"
                      :key="file.id"
                    >
                      <div class="mr-5">
                        <p class="ma-0 text-body-2 font-weight-medium">
                          {{ get_text_of_type_file(file) }}
                        </p>
                        <p class="ma-0 text-body-2">
                          {{ get_name_of_file(file) }}
                        </p>
                      </div>
                      <v-icon
                        class="mr-2"
                        @click="view_file(file)"
                        v-if="is_img_file(file)"
                        >mdi-eye-outline</v-icon
                      >
                      <v-icon>mdi-download-outline</v-icon>
                    </v-card>
                  </div>
                </v-card>
              </v-tab-item>
              <!-- history -->
              <v-tab-item>
                <health-records
                  isHistory
                  :profileHistory="selected_appointment.profile"
                />
              </v-tab-item>
              <!-- examination -->
              <v-tab-item>
                <v-card flat class="pa-6 d-flex flex-column">
                  <div>
                    <h4>Khám sơ bộ</h4>
                  </div>
                  <div class="d-flex flex-row mt-5 justify-space-between">
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <v-text-field
                        v-model="record.height"
                        label="Chiều cao (cm)"
                        placeholder="Vui lòng nhập chiều cao"
                        background-color="#EEF2F6"
                        outlined
                      ></v-text-field>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <v-text-field
                        label="Cân nặng (kg)"
                        v-model="record.weight"
                        placeholder="Vui lòng nhập cân nặng"
                        background-color="#EEF2F6"
                        outlined
                      ></v-text-field>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <v-text-field
                        label="Mạch (lần/phút)"
                        v-model="record.bloodVessel"
                        placeholder="Vui lòng nhập mạch"
                        background-color="#EEF2F6"
                        outlined
                      ></v-text-field>
                    </v-card>
                  </div>
                  <div class="d-flex flex-row mt-5 justify-space-between">
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <v-text-field
                        label="Nhiệt độ (độ C)"
                        v-model="record.temperature"
                        placeholder="Vui lòng nhập nhiệt độ"
                        background-color="#EEF2F6"
                        outlined
                      ></v-text-field>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <v-text-field
                        label="Huyết áp (mmHg)"
                        v-model="record.bloodPressure"
                        placeholder="Vui lòng nhập huyết áp"
                        background-color="#EEF2F6"
                        outlined
                      ></v-text-field>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <v-text-field
                        label="Nhịp tim (lần/phút)"
                        v-model="record.heartbeat"
                        placeholder="Vui lòng nhập nhịp tim"
                        background-color="#EEF2F6"
                        outlined
                      ></v-text-field>
                    </v-card>
                  </div>
                  <div class="mb-5">
                    <h4>Tóm tắt kết quả lâm sàng</h4>
                  </div>
                  <div class="d-flex flex-row justify-space-between">
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <v-textarea
                        label="Chuẩn đoán"
                        v-model="record.diagnose"
                        background-color="#EEF2F6"
                        outlined
                        placeholder="Vui lòng nhập chuẩn đoán"
                      ></v-textarea>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <v-textarea
                        label="Chỉ định"
                        background-color="#EEF2F6"
                        outlined
                        placeholder="Vui lòng nhập chỉ định"
                        v-model="record.prescribe"
                      ></v-textarea>
                    </v-card>
                    <v-card
                      width="30%"
                      elevation="0"
                      flat
                      class="d-flex flex-column"
                    >
                      <v-textarea
                        label="Ghi chú"
                        placeholder="Vui lòng nhập ghi chú"
                        background-color="#EEF2F6"
                        outlined
                        v-model="record.note"
                      ></v-textarea>
                    </v-card>
                  </div>
                  <div class="d-flex flex-row mt-2">
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column"
                    >
                      <v-checkbox
                        class="mb-0"
                        v-model="record.hospitalize"
                        :label="'Yêu cầu nhập viện'"
                      ></v-checkbox>
                    </v-card>
                  </div>
                  <v-card
                    width="30%"
                    elevation="0"
                    class="d-flex flex-column mt-3"
                  >
                    <v-menu
                      v-model="menu2"
                      :close-on-content-click="false"
                      :nudge-right="40"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="record.reExaminationDate"
                          label="Ngày tái khám"
                          append-icon="mdi-calendar"
                          v-bind="attrs"
                          v-on="on"
                          outlined
                          background-color="#EEF2F6"
                          clearable
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="record.reExaminationDate"
                        @input="menu2 = false"
                        locale="vi"
                        no-title
                      ></v-date-picker>
                    </v-menu>
                  </v-card>
                  <div class="d-flex flex-row mt-5 justify-space-between">
                    <v-card
                      width="45%"
                      class="d-flex flex-column"
                      elevation="0"
                    >
                      <!-- test results type -->
                      <div
                        class="d-flex flex-row justify-space-between align-center"
                      >
                        <p class="font-weight-bold text-body-1 ma-0">
                          Kết quả xét nghiệm
                        </p>
                      </div>
                      <!-- add test result  -->
                      <v-card
                        color="#EEF2F6"
                        outlined
                        class="my-5 pa-2 d-flex flex-row"
                        style="border: 0.5px solid #757575"
                      >
                        <v-file-input
                          v-model="test_file"
                          placeholder="Vui lòng chọn hình ảnh"
                          solo
                          dense
                          prepend-inner-icon="mdi-link-variant"
                          prepend-icon=""
                          hide-details=""
                          flat
                          multiple
                          small-chips
                          background-color="#EEF2F6"
                        ></v-file-input>
                        <v-btn
                          class="btn white--text font-weight-medium text-body-2"
                          color="#537DA5"
                          elevation="0"
                          @click="addTestFile"
                          >Tải lên</v-btn
                        >
                      </v-card>
                      <!-- add test result  -->
                      <div
                        class="d-flex flex-wrap justify-space-between align-center mb-5"
                        v-for="(image, index) in test_add_list"
                        :key="index"
                      >
                        <v-card width="221" outlined class="pa-1">
                          <v-combobox
                            spellcheck="false"
                            v-model="image.type"
                            :items="test_type"
                            item-text="name"
                            solo
                            dense
                            flat
                            append-icon="mdi-chevron-down"
                            item-color="light-blue darken-4"
                            placeholder="Chọn loại xét nghiệm"
                            hide-details=""
                          ></v-combobox>
                        </v-card>
                        <v-card
                          min-width="50%"
                          outlined
                          class="pa-3 d-flex flex-row"
                        >
                          <v-icon class="mr-2">mdi-image-outline</v-icon>
                          <v-card min-width="50%" elevation="0">
                            <p class="ma-0" style="">
                              {{ image.file_name }}
                            </p>
                          </v-card>

                          <v-spacer></v-spacer>
                          <v-icon @click="removeTestFile(index)"
                            >mdi-close</v-icon
                          >
                        </v-card>
                      </div>
                    </v-card>
                  </div>
                  <div class="d-flex flex-row mt-5 justify-space-between">
                    <v-card
                      width="45%"
                      class="d-flex flex-column"
                      elevation="0"
                    >
                      <!-- Image analysation type -->
                      <div
                        class="d-flex flex-row justify-space-between align-center"
                      >
                        <p class="font-weight-bold text-body-1 ma-0">
                          Chuẩn đoán hình ảnh
                        </p>
                      </div>
                      <!-- add test result  -->
                      <v-card
                        color="#EEF2F6"
                        outlined
                        style="border: 0.5px solid #757575"
                        class="my-5 pa-2 d-flex flex-row"
                      >
                        <v-file-input
                          placeholder="Vui lòng chọn hình ảnh"
                          solo
                          dense
                          prepend-inner-icon="mdi-link-variant"
                          prepend-icon=""
                          hide-details=""
                          flat
                          v-model="image_file"
                          multiple
                          small-chips
                          background-color="#EEF2F6"
                        ></v-file-input>
                        <v-btn
                          class="btn white--text font-weight-medium text-body-2"
                          color="#537DA5"
                          elevation="0"
                          @click="addImageAnalystFile"
                          >Tải lên</v-btn
                        >
                      </v-card>
                      <!-- add image analyst  -->

                      <!-- list add image analyst-->
                      <div
                        class="d-flex flex-wrap justify-space-between align-center mb-5"
                        v-for="(image, index) in image_analyst_list"
                        :key="index + 3"
                      >
                        <v-card width="221" outlined class="pa-1">
                          <v-combobox
                            spellcheck="false"
                            v-model="image.type"
                            :items="image_analyst_type"
                            item-text="name"
                            item-value="key"
                            solo
                            dense
                            flat
                            append-icon="mdi-chevron-down"
                            item-color="light-blue darken-4"
                            placeholder="Chọn loại hình ảnh"
                            hide-details=""
                          ></v-combobox>
                        </v-card>
                        <v-card
                          min-width="50%"
                          outlined
                          class="pa-3 d-flex flex-row"
                        >
                          <v-icon class="mr-2">mdi-image-outline</v-icon>
                          <v-card min-width="50%" elevation="0">
                            <p class="ma-0" style="">
                              {{ image.file_name }}
                            </p>
                          </v-card>

                          <v-spacer></v-spacer>
                          <v-icon @click="removeImageFile(index)"
                            >mdi-close</v-icon
                          >
                        </v-card>
                      </div>
                    </v-card>
                  </div>
                </v-card>
              </v-tab-item>
              <!-- međicine -->
              <v-tab-item>
                <v-card flat class="pa-6 d-flex flex-column">
                  <p class="font-weight-bold">
                    Kê đơn
                  </p>
                  <div class="d-flex flex-row">
                    <!-- from date -->
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column mt-3 mr-12"
                    >
                      <v-menu
                        v-model="from_date_menu"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="from_date"
                            label="Ngày bắt đầu"
                            append-icon="mdi-calendar"
                            v-bind="attrs"
                            v-on="on"
                            outlined
                            background-color="#EEF2F6"
                            clearable
                          ></v-text-field>
                        </template>
                        <v-date-picker
                          v-model="from_date"
                          @input="from_date_menu = false"
                          locale="vi"
                          no-title
                        ></v-date-picker>
                      </v-menu>
                    </v-card>

                    <!-- to date -->
                    <v-card
                      width="30%"
                      elevation="0"
                      class="d-flex flex-column mt-3"
                    >
                      <v-menu
                        v-model="to_date_menu"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="to_date"
                            label="Ngày kết thúc"
                            append-icon="mdi-calendar"
                            v-bind="attrs"
                            v-on="on"
                            outlined
                            background-color="#EEF2F6"
                            clearable
                          ></v-text-field>
                        </template>
                        <v-date-picker
                          v-model="to_date"
                          @input="to_date_menu = false"
                          locale="vi"
                          no-title
                        ></v-date-picker>
                      </v-menu>
                    </v-card>
                  </div>
                  <div class="d-flex flex-column">
                    <v-card
                      class="pa-6"
                      color="#EEF2F6"
                      width="80%"
                      elevation="0"
                      style="border: 0.5px solid #757575"
                      outlined
                    >
                      <div class="d-flex flex-row pb-6">
                        <v-spacer></v-spacer>
                        <v-btn
                          class="btn mr-3 white--text text-body-1"
                          color="#537DA5"
                          elevation="0"
                          :disabled="!from_date || !to_date"
                          @click.stop="open_add_medicine_dialog"
                          ><v-icon medium class="mr-1">mdi-plus</v-icon
                          >Thêm</v-btn
                        >
                        <v-btn
                          class="btn mr-3 white--text text-body-1"
                          color="#667085"
                          elevation="0"
                          :disabled="
                            !from_date ||
                              !to_date ||
                              _.isEmpty(selectedMedicine)
                          "
                          @click.stop="openEditMedicine()"
                          ><v-icon medium class="mr-1">mdi-pencil</v-icon>Chỉnh
                          sửa</v-btn
                        >
                        <v-btn
                          class="btn white--text text-body-1"
                          color="#F04438"
                          elevation="0"
                          :disabled="
                            !from_date ||
                              !to_date ||
                              _.isEmpty(selectedMedicine)
                          "
                          @click="remove_medicine_to_prescriptions"
                          ><v-icon medium class="mr-1">mdi-delete</v-icon
                          >Xóa</v-btn
                        >
                      </div>
                      <v-data-table
                        :headers="medicineHeaders"
                        :items="indexMedicines"
                        class="elevation-1"
                        single-select
                        show-select
                        v-model="selectedMedicine"
                        item-key="id"
                        checkbox-color="#3C5E7E"
                        hide-default-footer
                        no-data-text="Đơn thuốc trống"
                        :key="tableMedicineKey"
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
                              {{ item.data.name }}
                            </p>
                          </div>
                        </template>
                      </v-data-table>
                    </v-card>
                  </div>

                  <v-dialog v-model="medicine_dialog" width="500">
                    <v-toolbar color="#537DA5" height="64" class="white--text">
                      Thêm thuốc
                    </v-toolbar>
                    <v-card class="d-flex flex-column pa-8" tile>
                      <v-card elevation="0" class="d-flex flex-column mb-5">
                        <p class="text-body-2 ma-0 font-weight-medium">
                          Tên thuốc:
                        </p>

                        <v-combobox
                          spellcheck="false"
                          v-model="medicine.data"
                          :items="store_medicine_list"
                          item-text="name"
                          solo
                          flat
                          append-icon="mdi-chevron-down"
                          item-color="light-blue darken-4"
                          placeholder="Chọn thuốc"
                          hide-details=""
                          background-color="#EEF2F6"
                        ></v-combobox>
                      </v-card>
                      <v-card elevation="0" class="d-flex flex-column">
                        <p class="text-body-2 ma-0 font-weight-medium">
                          Đơn vị uống:
                        </p>
                        <v-text-field
                          solo
                          flat
                          background-color="#EEF2F6"
                          v-model="medicine.unit"
                        ></v-text-field>
                      </v-card>
                      <v-card elevation="0" class="d-flex flex-column">
                        <p class="text-body-2 ma-0 font-weight-medium">
                          Sáng uống:
                        </p>
                        <v-text-field
                          type="number"
                          solo
                          flat
                          background-color="#EEF2F6"
                          v-model="medicine.morning"
                        ></v-text-field>
                      </v-card>
                      <v-card elevation="0" class="d-flex flex-column">
                        <p class="text-body-2 ma-0 font-weight-medium">
                          Chiều uống:
                        </p>
                        <v-text-field
                          type="number"
                          solo
                          flat
                          v-model="medicine.afternoon"
                          background-color="#EEF2F6"
                        ></v-text-field>
                      </v-card>
                      <v-card elevation="0" class="d-flex flex-column">
                        <p class="text-body-2 ma-0 font-weight-medium">
                          Tối uống:
                        </p>
                        <v-text-field
                          type="number"
                          solo
                          flat
                          background-color="#EEF2F6"
                          v-model="medicine.evening"
                        ></v-text-field>
                      </v-card>
                      <v-card class="d-flex" elevation="0">
                        <v-spacer></v-spacer>
                        <v-btn
                          class="btn mr-5 white--text"
                          color="primary"
                          outlined
                          elevation="0"
                          @click.stop="medicine_dialog = false"
                          >Hủy</v-btn
                        >

                        <v-btn
                          v-if="medicine_dialog_type == 1"
                          color="#537DA5"
                          class="white--text btn font-weight-medium"
                          elevation="0"
                          :disabled="
                            medicine.evening == 0 &&
                              medicine.morning == 0 &&
                              medicine.afternoon == 0
                          "
                          @click="add_medicine_to_prescriptions"
                          >Thêm</v-btn
                        >
                        <v-btn
                          v-if="medicine_dialog_type == 2"
                          color="#6D91B3"
                          class="white--text btn font-weight-medium"
                          elevation="0"
                          :disabled="_.isEmpty(selectedMedicine)"
                          @click="closeEditMedicine()"
                          >Lưu</v-btn
                        >
                      </v-card>
                    </v-card>
                  </v-dialog>
                </v-card>
              </v-tab-item>
            </v-tabs>
          </v-card>
        </v-card>
      </v-dialog>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;
import HealthRecords from "../user/health_records.vue";

export default {
  components: {
    HealthRecords
  },
  async mounted() {
    await this.get_appointment();
    this.fetchMedicines();
  },

  data() {
    return {
      search: "",
      selected: [],
      tableMedicineKey: 0,
      headers_appointment: [
        {
          text: "Họ và tên",
          align: "start",
          sortable: false,
          value: "profile.fullName",
          filterable: true
        },
        { text: "Tình trạng", value: "status", sortable: false },
        { text: "Triệu chứng", value: "symptom", sortable: false },
        { text: "Ngày sinh", value: "profile.dob", sortable: false },
        { text: "Số điện thoại", value: "profile.phone", sortable: false },
        { text: "Ngày khám", value: "date", sortable: false },
        { text: "Khung giờ khám", value: "time", sortable: false },
        { text: "Phòng", value: "room", sortable: false }
      ],
      loading: false,
      loadingConfirm: false,
      list_appointment: [],
      selected_appointment: {
        id: "",
        code: 0,
        room: {
          id: "",
          name: "",
          link: null
        },
        profile: {
          id: "",
          profileNumber: 0,
          firstName: "",
          lastName: "",
          address: "",
          phone: "",
          email: "",
          dob: "",
          job: "",
          identityCard: "",
          healthInsurance: "",
          folk: "",
          gender: "",
          guardian: "",
          guardianPhone: "",
          guardianIdentityCard: "",
          relationship: "",
          relationshipWithPatient: "",
          imageUrl: null
        },
        doctor: {
          id: "",
          name: "",
          email: "",
          specialty: "",
          hospital: {
            id: "",
            name: "",
            address: {
              id: "",
              country: "",
              province: "",
              district: "",
              ward: "",
              address: ""
            },
            info: "",
            registrationNumber: 0,
            favorite: 0.0,
            mapImageUrl: null,
            imageUrl: "",
            isActive: true,
            hospitalHour: {
              id: "",
              mondayTime: "",
              mondayTimeBreak: "",
              tuesdayTime: "",
              tuesdayTimeBreak: "",
              wednesdayTime: "",
              wednesdayTimeBreak: "",
              thursdayTime: "",
              thursdayTimeBreak: "",
              fridayTime: "",
              fridayTimeBreak: "",
              saturdayTime: "",
              saturdayTimeBreak: "",
              sundayTime: "",
              sundayTimeBreak: ""
            }
          },
          level: "",
          bio: "",
          registrationNumber: 0,
          price: "",
          imageUrl: null,
          favorite: 0
        },
        service: null,
        date: "",
        time: "",
        symptom: "",
        files: [
          {
            id: "",
            imageUrl: "",
            type: ""
          }
        ],
        status: "",
        type: "",
        isPaid: true,
        fee: "",
        category: ""
      },
      all_appointment_list: [],
      exam_dialog: false,
      from_date_menu: false,
      from_date: "",
      to_date_menu: false,
      to_date: "",

      medicineHeaders: [
        {
          text: "Tên",
          align: "start",
          sortable: false,
          value: "name"
        },
        { text: "Liều", value: "use", sortable: false },
        { text: "Đơn vị uống", value: "unit", sortable: false },
        { text: "Số lượng", value: "mount", sortable: false }
      ],
      prescriptions: [],
      medicine_dialog: false,
      medicine_dialog_type: 1,
      store_medicine_list: [],
      medicine: {
        data: {
          name: "",
          id: ""
        },
        unit: "",
        morning: 0,
        afternoon: 0,
        evening: 0
      },
      selectedMedicine: [],
      test_type: [
        { name: "Xét nghiệm máu", key: "BLOOD_TEST" },
        { name: "Xét nghiệm nước tiểu", key: "URINE_TEST" },
        { name: "Xét nghiệm khác", key: "DIFFERENT_TEST" }
      ],
      test_select: "",
      test_file: [],
      test_add_list: [],
      image_select: "",
      image_analyst_type: [
        { name: "CT", key: "CT_SCAN" },
        { name: "X-quang", key: "X_RAY " },
        { name: "PET", key: "PET_SCAN" },
        { name: "Siêu âm", key: "SUPERSONIC" },
        { name: "MRI", key: "MRI" },
        { name: "Hình ảnh khác", key: "DIFFERENT_IMAGE" }
      ],
      image_analyst_list: [],
      image_file: [],
      submit_file_list: [],
      menu2: false,
      record: {
        diagnose: "",
        prescribe: "",
        note: "",
        reExaminationDate: "",
        height: "",
        weight: "",
        bloodVessel: "",
        temperature: "",
        bloodPressure: "",
        heartbeat: "",
        hospitalize: false
      },
      exam_confirm_dialog: false,
      image_file_accept: [
        "wmf",
        "webp",
        "tga",
        "emf",
        "ai",
        "tif",
        "bmp",
        "gif",
        "jpg",
        "ps",
        "jpeg",
        "ufo",
        "pdf",
        "eps",
        "svg",
        "png",
        "psd"
      ],
      params: {
        filters: [],
        sorts: [],
        page: 0,
        size: 1
      },
      totalPages: 0,
      pagination: {
        itemsPerPage: 10,
        page: 1
      }
    };
  },
  computed: {
    doctorId() {
      return this.$store.getters["auth/userId"];
    },
    indexMedicines() {
      return this.prescriptions.map((item, index) => ({
        id: index,
        ...item
      }));
    }
  },
  watch: {
    "pagination.page": {
      handler(val, newVal) {
        if (val != newVal) this.get_appointment();
      },
      deep: true
    },
    "pagination.itemsPerPage": {
      handler(val, newVal) {
        if (val != newVal) this.get_appointment();
      },
      deep: true
    }
  },
  methods: {
    examinate() {
      const data = this.selected[0];
      this.selected_appointment = JSON.parse(JSON.stringify(data));
      this.selected_appointment.profile.dob = this.convert_date(
        data.profile.dob
      );
      switch (data.profile.gender) {
        case "MALE":
          this.selected_appointment.profile.gender = "Nam";
          break;
        case "FEMALE":
          this.selected_appointment.profile.gender = "Nữ";
          break;
        default:
          this.selected_appointment.profile.gender = "Khác";
        // code block
      }

      this.selected_appointment.profile.address = this.getAddress(data.profile);
      this.exam_dialog = true;
    },
    open_add_medicine_dialog() {
      this.medicine_dialog_type = 1;
      this.medicine = {
        data: "",
        unit: "Viên",
        count: 0,
        morning: 0,
        afternoon: 0,
        evening: 0
      };
      this.medicine_dialog = true;
    },
    openEditMedicine() {
      this.medicine_dialog_type = 2;
      this.medicine = this._.cloneDeep(this.selectedMedicine[0]);
      this.medicine_dialog = true;
    },
    async fetchMedicines() {
      let token = this.$store.getters["auth/access_token"];

      const params = {
        token: token,
        data: {
          hospitalId: this.list_appointment[0].doctor.hospital.id
        }
      };
      await this.$store.dispatch(
        "medicine/get_store_medicine_by_hospital",
        params
      );
      this.store_medicine_list = this.$store.getters[
        "medicine/store_medicine_by_hospital"
      ];
    },
    countMedicine(item) {
      const oneDay = 24 * 60 * 60 * 1000;
      const fromDate = new Date(this.from_date);
      const toDate = new Date(this.to_date);
      const diffDays = Math.round(Math.abs((fromDate - toDate) / oneDay));
      return (
        (Number(item.morning) + Number(item.afternoon) + Number(item.evening)) *
        diffDays
      );
    },
    convert_to_use(item) {
      let use = "";

      if (item.morning != 0) {
        if (use.length == 0) {
          use = use + "Sáng: " + item.morning;
        } else {
          use = use + ", Sáng: " + item.morning;
        }
      }

      if (item.afternoon != 0) {
        if (use.length == 0) {
          use = use + "Chiều: " + item.afternoon;
        } else {
          use = use + ", Chiều: " + item.afternoon;
        }
      }
      if (item.evening != 0) {
        if (use.length == 0) {
          use = use + "Tối: " + item.evening;
        } else {
          use = use + ", Tối: " + item.evening;
        }
      }
      return use;
    },
    add_medicine_to_prescriptions() {
      this.prescriptions.push({
        data: this.medicine.data,
        unit: this.medicine.unit,
        morning: this.medicine.morning,
        afternoon: this.medicine.afternoon,
        evening: this.medicine.evening
      });
      this.medicine_dialog = false;
    },
    closeEditMedicine() {
      let index = this.indexMedicines.indexOf(this.selectedMedicine[0]);
      this.indexMedicines[index] = this._.cloneDeep(this.medicine);
      this.tableMedicineKey++;
      this.medicine_dialog = false;
    },
    remove_medicine_to_prescriptions() {
      let index = this.prescriptions.indexOf(this.selectedMedicine[0]);

      this.prescriptions.splice(index, 1);
      this.medicine_dialog = false;
    },

    removeTestFile(index) {
      this.test_add_list.splice(index, 1);
    },

    removeImageFile(index) {
      this.image_analyst_list.splice(index, 1);
    },

    addTestFile() {
      this.test_file.forEach(file => {
        this.test_add_list.push({
          type: { key: "", name: "" },
          file_name: file.name,
          data: file
        });
      });
    },

    addImageAnalystFile() {
      this.image_file.forEach(file => {
        this.image_analyst_list.push({
          type: { key: "", name: "" },
          file_name: file.name,
          data: file
        });
      });
    },

    async get_appointment() {
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const cancelToken = axios.CancelToken.source().token;
      let params = this._.cloneDeep(this.params);
      params.page = this.pagination.page - 1;
      params.size = this.pagination.itemsPerPage;

      // params.filters.push({
      //   key: "date",
      //   operator: "EQUAL_DATE",
      //   field_type: "STRING",
      //   value: "2022-12-19"
      // });
      params.filters.push({
        key: "doctor.id",
        operator: "EQUAL_NESTED",
        field_type: "STRING",
        value: this.doctorId
      });
      params.filters.push({
        key: "status",
        operator: "IN",
        field_type: "STATUS",
        values: ["PENDING", "PROCESS"]
      });
      this.loading = true;
      await axios
        .post(`${url}/api/doctor/appointments/search`, params, {
          cancelToken
        })
        .then(res => {
          this.list_appointment = res.data.results;
          this.totalPages = res.data?.meta?.totalPages;
        })
        .finally(() => {
          this.loading = false;
        });
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

    getAddress(profile) {
      if (profile.address == null) return "";
      return (
        profile.address.address +
        ", " +
        profile.address.ward +
        ", " +
        profile.address.district +
        ", " +
        profile.address.province
      );
    },

    async create_record() {
      this.loadingConfirm = true;
      let post_file_list = this.test_add_list.concat(this.image_analyst_list);
      await Promise.all(
        post_file_list.map(async file => {
          await this.post_file(file.data, file.type);
        })
      );
      let token = this.$store.getters["auth/access_token"];

      let medicine_list_submit = [];
      this.indexMedicines.forEach(medicine => {
        medicine_list_submit.push({
          storeMedicineId: medicine.data.id,
          unit: medicine.unit,
          numMedicineInMorning: medicine.morning,
          numMedicineInAfternoon: medicine.afternoon,
          numMedicineInEvening: medicine.evening,
          dateStart: this.from_date,
          dateEnd: this.to_date
        });
      });
      const params = {
        token: token,
        data: {
          appointmentId: this.selected_appointment.id,
          diagnose: this.record.diagnose,
          prescribe: this.record.prescribe,
          note: this.record.note,
          reExaminationDate: this.record.reExaminationDate,
          height: this.record.height,
          weight: this.record.weight,
          bloodVessel: this.record.bloodVessel,
          temperature: this.record.temperature,
          bloodPressure: this.record.bloodPressure,
          heartbeat: this.record.heartbeat,
          hospitalize: this.record.hospitalize,
          medicines: medicine_list_submit,
          files: this.submit_file_list
        }
      };
      this.$store.dispatch("record/create_record", params);
      this.loadingConfirm = false;
      this.refresh_exam_data();
      this.exam_confirm_dialog = false;
      this.exam_dialog = false;
      this.get_appointment();
    },

    async post_file(file, type) {
      let form_data = new FormData();
      form_data.append("file", file);
      let token = this.$store.getters["auth/access_token"];

      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      await axios
        .post(`${url}/api/files/upload`, form_data, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(response => {
          if (response.data.code === 200) {
            this.submit_file_list.push({
              imageUrl: response.data.results,
              type: type.key
            });
          }
        });
    },

    refresh_exam_data() {
      this.from_date = "";
      this.to_date = "";
      this.record.diagnose = "";
      this.record.prescribe = "";
      this.record.note = "";
      this.record.bloodVessel = "";
      this.record.bloodPressure = "";
      this.record.height = "";
      this.record.weight = "";
      this.record.temperature = "";
      this.record.hospitalize = false;
      this.record.reExaminationDate = "";
      this.record.heartbeat = "";
      this.prescriptions = [];
      this.indexMedicines = [];
    },

    stop_examination() {
      this.refresh_exam_data();
      this.exam_dialog = false;
    },

    get_text_of_type_file(file) {
      let type_name = "";
      this.test_type.forEach(file_type => {
        if (file_type.key === file.type) type_name = file_type.name;
      });
      this.image_analyst_type.forEach(file_type => {
        if (file_type.key === file.type) type_name = file_type.name;
      });
      return type_name;
    },

    get_name_of_file(file) {
      let temp_array = file.imageUrl.split("/");
      return temp_array[temp_array.length - 1];
    },
    view_file(file) {
      window.open(file.imageUrl);
    },
    is_img_file(file) {
      let temp_array = file.imageUrl.split("/");
      let name_file = temp_array[temp_array.length - 1];
      let temp_array_1 = name_file.split(".");
      let result = false;
      if (this.image_file_accept.includes(temp_array_1[1])) result = true;
      return result;
    },
    down_load_file() {},

    go_to_room(link) {
      window.open(link);
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
.v-data-table > .v-data-table__wrapper > table > tbody > tr > th,
.v-data-table > .v-data-table__wrapper > table > thead > tr > th,
.v-data-table > .v-data-table__wrapper > table > tfoot > tr > th {
  font-size: 20px !important;
}
</style>
