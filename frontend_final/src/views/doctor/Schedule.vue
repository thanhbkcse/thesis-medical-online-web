<template>
  <div>
    <v-row
      v-if="
        schedules.length &&
        Object.values(appointmentInSchedule || {}).some((prop) => prop)
      "
      class="fill-height"
    >
      <v-col>
        <v-sheet height="64">
          <v-toolbar flat>
            <v-btn
              outlined
              class="mr-4"
              color="grey darken-2"
              @click="setToday"
            >
              Hôm nay
            </v-btn>
            <v-btn fab text small color="grey darken-2" @click="prev">
              <v-icon small> mdi-chevron-left </v-icon>
            </v-btn>
            <v-btn fab text small color="grey darken-2" @click="next">
              <v-icon small> mdi-chevron-right </v-icon>
            </v-btn>
            <v-toolbar-title v-if="$refs.calendar">
              {{ $refs.calendar.title }}
            </v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
        </v-sheet>
        <v-sheet height="600">
          <v-calendar
            ref="calendar"
            v-model="focus"
            color="primary"
            :events="events"
            :event-color="getEventColor"
            :type="type"
            @click:event="showEvent"
            @click:date="getAppointmentList"
            @change="updateRange"
          ></v-calendar>
          <v-menu
            v-model="selectedOpen"
            :close-on-content-click="false"
            :activator="selectedElement"
            offset-x
          >
            <v-card color="grey lighten-4" min-width="350px" flat>
              <v-toolbar :color="selectedEvent.color" dark>
                <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn icon>
                  <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
              </v-toolbar>
              <v-card-text>
                <span v-html="selectedEvent.details"></span>
                <a
                  style="text-decoration: none"
                  v-if="selectedEvent.link"
                  :href="selectedEvent.link"
                  target="_blank"
                  >Vào phòng google meet</a
                >
                <div v-if="selectedEvent.time">
                  <ul v-for="(item, i) in selectedEvent.time" :key="i">
                    <li>{{ item }}</li>
                  </ul>
                </div>
              </v-card-text>
              <v-card-actions> </v-card-actions>
            </v-card>
          </v-menu>
        </v-sheet>
      </v-col>
    </v-row>
    <v-row class="ma-3">
      <v-btn color="#537da5" class="white--text" tile @click="openAllPanel()"
        >Mở tất cả</v-btn
      >
      <v-btn
        class="ml-2 white--text"
        color="#537da5"
        tile
        @click="closeAllPanel()"
        >Đóng tất cả</v-btn
      >
      <v-btn class="ml-2 white--text" color="#537da5" tile>Chuyển lịch</v-btn>
    </v-row>
    <v-row class="ma-3">
      <v-expansion-panels class="mt-3" v-model="panel" multiple>
        <v-expansion-panel v-for="(item, i) in appointmentList" :key="i">
          <v-expansion-panel-header>{{ item.time }} </v-expansion-panel-header>
          <v-expansion-panel-content
            v-for="(appointment, j) in item.appointments"
            :key="j"
          >
            <v-container>
              <v-row>
                <v-col cols="2" class="align-self-center"
                  ><span class="tag mr-2">STT:</span>
                  {{ appointment.stt }}</v-col
                >
                <v-col cols="4" class="align-self-center"
                  ><span class="tag mr-2">Họ tên:</span>
                  {{ _.get(appointment, "profile.name") }}
                </v-col>
                <v-col cols="2" class="align-self-center"
                  ><span class="tag mr-2">Phòng:</span>
                  {{ _.get(appointment, "room.name") }}
                </v-col>
                <v-col cols="3" class="d-flex">
                  <v-btn
                    color="success"
                    @click="openAppointmentDetail(appointment)"
                  >
                    Chi tiết
                  </v-btn>
                  <v-btn
                    class="ml-2"
                    color="primary"
                    @click="openDialogCreateRecord(appointment)"
                  >
                    Tạo bệnh án
                  </v-btn>
                </v-col>
              </v-row>
            </v-container>
            <v-divider></v-divider>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-row>
    <v-dialog v-model="dialog" scrollable max-width="800px" persistent>
      <v-card>
        <v-card-title style="justify-content: space-between">
          <h2>Thông tin bệnh nhân</h2>
          <v-btn icon @click="closeDialogDetail()">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-divider></v-divider>
        <v-card-text>
          <v-row>
            <v-col cols="6">
              <v-card flat>
                <v-card-text>
                  <div class="row">
                    <span class="col-4">Họ và tên:</span>
                    <strong class="col-8"
                      ><span>
                        {{ _.get(appointment, "profile.name") }}</span
                      ></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-4">Giới tính:</span>
                    <strong class="col-8"
                      ><span>{{
                        _.get(appointment, "profile.gender")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-4">Ngày sinh:</span>
                    <strong class="col-8"
                      ><span>{{
                        _.get(appointment, "profile.dob")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-4">Số điện thoại:</span>
                    <strong class="col-8"
                      ><span>{{
                        _.get(appointment, "profile.phoneNumber")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-4">Địa chỉ:</span>
                    <strong class="col-8"
                      ><span>{{
                        _.get(appointment, "profile.address")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-4">Nghề nghiệp:</span>
                    <strong class="col-8"
                      ><span>{{
                        _.get(appointment, "profile.job")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-4">CCCD:</span>
                    <strong class="col-8"
                      ><span>{{
                        _.get(appointment, "profile.identityCard")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-4">Bảo hiểm y tế:</span>
                    <strong class="col-8"
                      ><span>{{
                        _.get(appointment, "profile.healthInsurance")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-4">Dân tộc:</span>
                    <strong class="col-8"
                      ><span>{{
                        _.get(appointment, "profile.folk")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-4">Người bảo hộ:</span>
                    <strong class="col-8"
                      ><span>{{
                        _.get(appointment, "profile.protector")
                      }}</span></strong
                    >
                  </div>
                </v-card-text>
              </v-card>
            </v-col>
            <v-col cols="6">
              <v-card flat>
                <v-card-text>
                  <div class="row">
                    <span class="col-5">Mã bệnh nhân:</span>
                    <strong class="col-7"
                      ><span>
                        {{ _.get(appointment, "profile.profileNumber") }}</span
                      ></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-5">Ngày khám:</span>
                    <strong class="col-7"
                      ><span> {{ _.get(appointment, "date") }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-5">Số thứ tự:</span>
                    <strong class="col-7"
                      ><span>{{ _.get(appointment, "stt") }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-5">Phòng:</span>
                    <strong class="col-7"
                      ><span>{{
                        _.get(appointment, "room.name")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-5">Link phòng:</span>
                    <strong class="col-7"
                      ><span>{{
                        _.get(appointment, "room.link") || "--"
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-5">Triệu chứng:</span>
                    <strong class="col-7"
                      ><span>{{ _.get(appointment, "symptom") }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-5">Mô tả chi tiết:</span>
                    <strong class="col-7"
                      ><span>{{
                        _.get(appointment, "description")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-5">Thời gian diễn ra:</span>
                    <strong class="col-7"
                      ><span>{{
                        _.get(appointment, "timeSituation")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-5">Tự điều trị:</span>
                    <strong class="col-7"
                      ><span>{{
                        _.get(appointment, "selfTreatment")
                      }}</span></strong
                    >
                  </div>
                  <div class="row">
                    <span class="col-5">Ngày tạo:</span>
                    <strong class="col-7"
                      ><span>{{
                        dateFormat(_.get(appointment, "createdDate"))
                      }}</span></strong
                    >
                  </div>
                </v-card-text>
              </v-card>
            </v-col>
          </v-row>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions style="flex-direction: row-reverse">
          <v-btn depressed color="error" @click="closeDialogDetail()">
            Đóng
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-bottom-sheet
      v-click-outside
      v-model="dialogRecord"
      scrollable
      persistent
    >
      <v-form ref="form">
        <v-card height="80vh">
          <v-card-title style="justify-content: space-between">
            <h2>Tạo Bệnh Án</h2>
            <div>
              <v-btn
                outlined
                tile
                color="primary"
                class="mr-2"
                @click="backStep()"
                :disabled="stepper === 1"
                >Quay lại</v-btn
              >
              <v-btn
                :disabled="stepper === 3"
                tile
                color="primary"
                class="mr-2"
                @click="nextStep()"
                >Tiếp tục</v-btn
              >
              <v-btn
                :disabled="stepper !== 3"
                tile
                color="primary"
                @click="saveRecord"
                :loading="loading"
              >
                Lưu bệnh án
              </v-btn>
              <v-btn icon @click="closeForm()">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </div>
          </v-card-title>
          <v-card-text class="body-content">
            <v-stepper class="mt-2" v-model="stepper">
              <v-stepper-header>
                <v-stepper-step :complete="stepper > 1" step="1"
                  >Thông tin bệnh nhân
                </v-stepper-step>

                <v-divider></v-divider>

                <v-stepper-step :complete="stepper > 2" step="2">
                  Chuẩn đoán bệnh
                </v-stepper-step>

                <v-divider></v-divider>

                <v-stepper-step step="3"> Kê đơn </v-stepper-step>
              </v-stepper-header>

              <v-stepper-items>
                <v-stepper-content flat step="1">
                  <v-card flat>
                    <v-card-text height="70%">
                      <v-row>
                        <v-col cols="3">
                          <v-card flat>
                            <v-card-title primary-title>
                              Thông tin lịch hẹn
                            </v-card-title>
                            <v-card-text>
                              <div class="row">
                                <span class="col-5">Mã bệnh nhân:</span>
                                <strong class="col-7"
                                  ><span>
                                    {{
                                      _.get(
                                        appointment,
                                        "profile.profileNumber"
                                      )
                                    }}</span
                                  ></strong
                                >
                              </div>
                              <div class="row">
                                <span class="col-5">Ngày khám:</span>
                                <strong class="col-7"
                                  ><span>
                                    {{ _.get(appointment, "date") }}</span
                                  ></strong
                                >
                              </div>
                              <div class="row">
                                <span class="col-5">Số thứ tự:</span>
                                <strong class="col-7"
                                  ><span>{{
                                    _.get(appointment, "stt")
                                  }}</span></strong
                                >
                              </div>
                              <div class="row">
                                <span class="col-5">Phòng:</span>
                                <strong class="col-7"
                                  ><span>{{
                                    _.get(appointment, "room.name")
                                  }}</span></strong
                                >
                              </div>
                              <div class="row">
                                <span class="col-5">Link phòng:</span>
                                <strong class="col-7"
                                  ><span>{{
                                    _.get(appointment, "room.link") || "--"
                                  }}</span></strong
                                >
                              </div>
                              <div class="row">
                                <span class="col-5">Ngày tạo:</span>
                                <strong class="col-7"
                                  ><span>{{
                                    dateFormat(
                                      _.get(appointment, "createdDate")
                                    ) || "--"
                                  }}</span></strong
                                >
                              </div>
                            </v-card-text>
                          </v-card>
                        </v-col>
                        <v-col cols="9">
                          <v-card class="mb-6" flat>
                            <v-card-title>Thông tin cá nhân</v-card-title>
                            <v-card-text>
                              <v-row>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.profile.name"
                                    label="Họ và tên"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.profile.gender"
                                    label="Giới tính"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.profile.dob"
                                    label="Ngày sinh"
                                    append-icon="mdi-calendar"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.profile.phoneNumber"
                                    label="Số điện thoại"
                                    append-icon="mdi-cellphone"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.profile.address"
                                    label="Địa chỉ"
                                    append-icon="mdi-domain"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.profile.job"
                                    label="Nghề nghiệp"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.profile.identityCard"
                                    label="CCCD"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="
                                      appointment.profile.healthInsurance
                                    "
                                    label="Bảo hiểm y tế"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.profile.folk"
                                    label="Dân tộc"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.profile.protector"
                                    label="Người bảo hộ"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="3"> </v-col>
                                <v-col cols="3"> </v-col>
                              </v-row>
                            </v-card-text>
                          </v-card>
                          <v-card flat>
                            <v-card-title>Triệu chứng</v-card-title>
                            <v-card-text>
                              <v-row>
                                <v-col cols="3">
                                  <v-textarea
                                    outline
                                    rows="1"
                                    v-model="appointment.symptom"
                                    label="Triệu chứng"
                                    readonly
                                  ></v-textarea>
                                </v-col>
                                <v-col cols="3">
                                  <v-textarea
                                    outline
                                    rows="1"
                                    v-model="appointment.description"
                                    label="Mô tả chi tiết"
                                    readonly
                                  ></v-textarea>
                                </v-col>
                                <v-col cols="3">
                                  <v-text-field
                                    v-model="appointment.timeSituation"
                                    label="Thời gian diễn ra"
                                    append-icon="mdi-calendar"
                                    readonly
                                  ></v-text-field>
                                </v-col>
                                <v-col cols="3">
                                  <v-checkbox
                                    v-model="appointment.selfTreatment"
                                    :label="'Đã tự điều trị'"
                                    readonly
                                  ></v-checkbox>
                                </v-col>
                              </v-row>
                            </v-card-text>
                          </v-card>
                        </v-col>
                      </v-row>
                    </v-card-text>
                  </v-card>
                </v-stepper-content>

                <v-stepper-content flat step="2">
                  <v-card flat class="mb-12">
                    <v-card-title></v-card-title>
                    <v-card-text>
                      <v-row>
                        <v-col cols="3">
                          <v-textarea
                            v-model="record.pathological"
                            label="Quá trình bệnh lí"
                            rows="2"
                          ></v-textarea>
                        </v-col>
                        <v-col cols="3">
                          <v-textarea
                            v-model="record.personalMedicalHistory"
                            label="Tiền sử bệnh bản thân"
                            rows="2"
                          ></v-textarea>
                        </v-col>
                        <v-col cols="3">
                          <v-textarea
                            v-model="record.familyMedicalHistory"
                            label="Tiền sử bệnh gia đình"
                            rows="2"
                          ></v-textarea>
                        </v-col>
                        <v-col cols="3">
                          <v-textarea
                            v-model="record.bodyInspection"
                            label="Khám xét toàn thân"
                            rows="2"
                          ></v-textarea>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="3">
                          <v-text-field
                            v-model="record.bloodVessel"
                            label="Mạch (lần/phút)"
                            placeholder="Mạch"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="3">
                          <v-text-field
                            v-model="record.temperature"
                            label="Nhiệt độ (độ C)"
                            placeholder="Nhiệt độ"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="3">
                          <v-text-field
                            v-model="record.bloodPressure"
                            label="Huyết áp (mmHg)"
                            placeholder="Huyết áp"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="3">
                          <v-text-field
                            v-model="record.heartbeat"
                            label="Nhịp tim (lần/phút)"
                            placeholder="Nhịp tim"
                          ></v-text-field>
                        </v-col>
                      </v-row>

                      <v-row>
                        <v-col cols="3">
                          <v-textarea
                            v-model="record.partsInspection"
                            label="Khám xét các bộ phận"
                            rows="2"
                          ></v-textarea>
                        </v-col>
                        <v-col cols="3">
                          <v-textarea
                            v-model="record.summary"
                            label="Tóm tắt kết quả lâm sàng"
                            rows="2"
                          ></v-textarea>
                        </v-col>
                        <v-col cols="3"> </v-col>
                        <v-col cols="3"> </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="3">
                          <v-checkbox
                            v-model="record.hospitalize"
                            label="Yêu cầu nhập viện"
                          ></v-checkbox>
                        </v-col>
                        <v-col cols="3">
                          <v-select
                            :disabled="!record.hospitalize"
                            v-model="record.facultyTreatment"
                            label="Khoa điều trị"
                            :items="specialties"
                          ></v-select>
                        </v-col>
                        <v-col cols="3"> </v-col>
                        <v-col cols="3"> </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="6">
                          <v-file-input
                            v-model="files"
                            color="primary lighten-1"
                            counter
                            label=""
                            multiple
                            placeholder="File đính kèm"
                            prepend-icon="mdi-paperclip"
                            height="40"
                            outlined
                            :show-size="1000"
                          >
                            <template v-slot:selection="{ index, text }">
                              <v-chip
                                v-if="index < 2"
                                color="primary lighten-1"
                                dark
                                label
                                small
                              >
                                {{ text }}
                              </v-chip>

                              <span
                                v-else-if="index === 2"
                                class="text-overline grey--text text--darken-3 mx-2"
                              >
                                +{{ files.length - 2 }} File(s)
                              </span>
                            </template>
                          </v-file-input>
                        </v-col>
                        <v-col cols="6"> </v-col>
                      </v-row>
                    </v-card-text>
                  </v-card>
                </v-stepper-content>

                <v-stepper-content step="3">
                  <v-card height="80vh" flat class="mb-12">
                    <v-card-text>
                      <v-btn
                        class="mb-3"
                        color="primary"
                        @click="addMedicine"
                        :disabled="panelMedicine.length > 0"
                        ><v-icon right dark class="mr-1">
                          mdi-plus-circle-outline </v-icon
                        >Thêm đơn thuốc</v-btn
                      >
                      <v-expansion-panels
                        class="mt-3"
                        v-model="panelMedicine"
                        multiple
                      >
                        <v-expansion-panel
                          v-for="(medicine, index) in medicineList"
                          :key="index"
                          class="mt-3"
                        >
                          <v-expansion-panel-header>
                            {{ _.get(medicine, "name") }}
                          </v-expansion-panel-header>
                          <v-expansion-panel-content>
                            <v-form ref="form" v-model="valid" lazy-validation>
                              <v-row>
                                <v-col cols="3"
                                  ><v-select
                                    :items="medicineNameList"
                                    v-model="medicine.name"
                                    label="Tên thuốc"
                                    placeholder="Chọn tên thuốc"
                                    :rules="[
                                      (v) => !!v || 'Tên thuốc là bắt buộc!',
                                    ]"
                                  ></v-select
                                ></v-col>
                                <v-col cols="3"
                                  ><v-text-field
                                    v-model="medicine.number"
                                    label="Số lượng"
                                    placeholder="Nhập số lượng"
                                    type="number"
                                  ></v-text-field
                                ></v-col>
                                <v-col cols="3"
                                  ><v-select
                                    :items="medicineTypeList"
                                    v-model="medicine.type"
                                    label="Loại"
                                  ></v-select
                                ></v-col>
                                <v-col cols="3"
                                  ><v-text-field
                                    v-model="medicine.use"
                                    label="Cách sử dụng"
                                    placeholder="Nhập cách sử dụng"
                                  ></v-text-field
                                ></v-col>
                              </v-row>
                              <v-row class="justify-end">
                                <v-btn
                                  tile
                                  color="error"
                                  outlined
                                  @click="removeMedicine(index)"
                                  >Xóa</v-btn
                                >
                                <v-btn
                                  class="ml-2"
                                  tile
                                  color="primary"
                                  :disabled="!valid"
                                  @click="saveMedicine(index)"
                                  >Lưu</v-btn
                                >
                              </v-row>
                            </v-form>
                          </v-expansion-panel-content>
                        </v-expansion-panel>
                      </v-expansion-panels>
                      <v-row>
                        <v-col cols="6">
                          <v-textarea
                            v-model="record.notes"
                            clearable
                            clear-icon="mdi-close-circle"
                            label="Lưu ý"
                            rows="2"
                          ></v-textarea>
                        </v-col>
                        <v-col cols="6"> </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="3">
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
                                readonly
                                v-bind="attrs"
                                v-on="on"
                              ></v-text-field>
                            </template>
                            <v-date-picker
                              v-model="record.reExaminationDate"
                              @input="menu2 = false"
                            ></v-date-picker>
                          </v-menu>
                        </v-col>
                      </v-row>
                    </v-card-text>
                  </v-card>
                </v-stepper-content>
              </v-stepper-items>
            </v-stepper>
          </v-card-text>
        </v-card>
      </v-form>
    </v-bottom-sheet>
    <!-- <v-row>

      <v-col cols="8" md="8" sm="12" xs="12">       
        
        
      </v-col>
    </v-row> -->
  </div>
</template>
<script>
import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;
export default {
  data: () => ({
    focus: "",
    type: "month",
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
    schedules: [],
    appointmentList: [],
    appointment: {},
    initialAppointment: {
      id: undefined,
      stt: undefined,
      room: {
        id: undefined,
        name: undefined,
        link: undefined,
      },
      profile: {
        id: undefined,
        profileNumber: undefined,
        name: undefined,
        address: undefined,
        phoneNumber: undefined,
        dob: undefined,
        job: undefined,
        identityCard: undefined,
        healthInsurance: undefined,
        folk: undefined,
        gender: undefined,
        protector: undefined,
      },
      doctor: {
        id: undefined,
        name: undefined,
        specialty: undefined,
        level: undefined,
      },
      date: undefined,
      time: undefined,
      symptom: undefined,
      description: undefined,
      timeSituation: undefined,
      selfTreatment: undefined,
      files: [],
      status: undefined,
      createdDate: undefined,
    },
    panel: [],
    panelMedicine: [],
    stepper: 1,
    dialog: false,
    dialogRecord: false,
    record: {},
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
      files: [],
    },
    medicine: {
      name: undefined,
      number: undefined,
      type: undefined,
      use: undefined,
    },
    medicineList: [],
    specialties: [],
    medicineNameList: ["Medicine A", "Medicine B", "Medicine C"],
    medicineTypeList: ["Viên", "Vỉ", "Hộp"],
    files: [],
    loading: false,
    valid: true,
    menu2: false,
    appointmentInSchedule: {},
  }),
  created() {
    var date = new Date();
    var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
    var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    firstDay = firstDay.toISOString().split("T")[0];
    lastDay = lastDay.toISOString().split("T")[0];
    this.getSchedules(firstDay, lastDay);
    this.getAppointmentInSchedule(firstDay, lastDay);
  },
  mounted() {
    this.appointment = JSON.parse(JSON.stringify(this.initialAppointment));
    this.record = JSON.parse(JSON.stringify(this.initialRecord));
    this.getSpecialties();
    //this.$refs.calendar.checkChange();
  },
  methods: {
    dateFormat(date) {
      var dateTest = new Date();
      return dateTest.toISOString().split("T")[0];
    },
    async getSchedules(dateStart, dateEnd) {
      const token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = { Authorization: `Bearer ${token}` };
      await axios
        .get(`${url}/api/doctor/schedules/date`, {
          params: {
            dateStart: dateStart,
            dateEnd: dateEnd,
          },
        })
        .then((res) => {
          this.schedules = res.data.results;
        });
    },
    async getAppointmentInSchedule(dateStart, dateEnd) {
      const token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = { Authorization: `Bearer ${token}` };
      await axios
        .get(`${url}/api/doctor/appointments/schedule`, {
          params: {
            dateStart: dateStart,
            dateEnd: dateEnd,
          },
        })
        .then((res) => {
          const results = res.data.results;
          this.appointmentInSchedule = this._.groupBy(
            results,
            ({ date }) => date
          );
        });
    },
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.focus = "";
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        requestAnimationFrame(() =>
          requestAnimationFrame(() => (this.selectedOpen = true))
        );
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        requestAnimationFrame(() => requestAnimationFrame(() => open()));
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    updateRange({ start, end }) {
      const events = [];
      for (let i = 0; i < this.schedules.length; i++) {
        events.push({
          name: `Phòng ${this.schedules[i].room.name}`,
          start: this.schedules[i].date,
          end: this.schedules[i].date,
          color: "blue",
          timed: false,
          link: this.schedules[i].room.link || "#",
        });
        events.push({
          name: `Thời gian`,
          start: this.schedules[i].date,
          end: this.schedules[i].date,
          color: "green",
          timed: false,
          time: this.schedules[i].times,
        });
        // if (this.appointmentInSchedule[this.schedules[i].date].length) {
        //   let number = this.appointmentInSchedule[this.schedules[i].date]
        //     .length;
        //   events.push({
        //     name: `${number} Lịch hẹn`,
        //     start: this.schedules[i].date,
        //     end: this.schedules[i].date,
        //     color: "orange",
        //     timed: false
        //   });
        // }
      }
      this.events = events;
    },
    nextStep() {
      this.stepper++;
    },
    backStep() {
      if (this.stepper == 1) return;
      this.stepper--;
    },
    openAllPanel() {
      this.panel = [...Array(this.appointmentList.length).keys()].map(
        (k, i) => i
      );
    },
    closeAllPanel() {
      this.panel = [];
    },
    closeForm() {
      this.dialogRecord = false;
      this.appointment = JSON.parse(JSON.stringify(this.initialAppointment));
      this.record = JSON.parse(JSON.stringify(this.initialRecord));
      this.stepper = 1;
      this.medicineList = [];
    },

    openAppointmentDetail(appointment) {
      this.dialog = true;
      Object.assign(this.appointment, appointment);
    },

    openDialogCreateRecord(appointment) {
      this.dialogRecord = true;
      Object.assign(this.appointment, appointment);
    },

    async getAppointmentList({ date }) {
      const params = {
        token: this.$store.getters["auth/access_token"],
        data: {
          date: date,
        },
      };
      await this.$store.dispatch(
        "appointment/getAppointment_byDate_Doctor",
        params
      );
      this.appointmentList =
        this.$store.getters["appointment/doctor_appointment_list"];
    },
    closeDialogDetail() {
      this.dialog = false;
      this.appointment = JSON.parse(JSON.stringify(this.initialAppointment));
    },
    async getSpecialties() {
      axios.get(`${url}/api/specialties`).then((res) => {
        this.specialties = res.data.results;
      });
    },
    addMedicine() {
      if (!this.panelMedicine.length && !this.medicineList.length) {
        this.panelMedicine = [0];
      } else {
        console.log(this.medicineList.length);
        this.panelMedicine = [this.medicineList.length];
      }
      let medicine = { ...this.medicine };
      this.medicineList.push(medicine);
    },
    saveMedicine(index) {
      if (!this.$refs[`form`][index].validate()) {
        return;
      }
      this.record.medicines = [...this.medicineList];
      this.panelMedicine = [];
    },
    removeMedicine(index) {
      this.medicineList.splice(index, 1);
      this.panelMedicine = [];
    },
    async saveRecord() {
      const formData = new FormData();

      formData.append("appointmentId", this.appointment.id);
      formData.append("pathological", this.record.pathological);
      formData.append(
        "personalMedicalHistory",
        this.record.personalMedicalHistory
      );
      formData.append("familyMedicalHistory", this.record.familyMedicalHistory);
      formData.append("bodyInspection", this.record.bodyInspection);
      formData.append("bloodVessel", this.record.bloodVessel);
      formData.append("temperature", this.record.temperature);
      formData.append("bloodPressure", this.record.bloodPressure);
      formData.append("heartbeat", this.record.heartbeat);
      formData.append("summary", this.record.summary);
      formData.append("partsInspection", this.record.partsInspection);
      formData.append("hospitalize", "True");
      formData.append("facultyTreatment", this.record.facultyTreatment);
      formData.append("notes", this.record.notes);
      formData.append("reExaminationDate", this.record.reExaminationDate);
      const medicineList = JSON.stringify(this.medicineList);
      formData.append("medicines", medicineList);
      this.files.forEach((file) => {
        formData.append("files", file);
      });
      this.loading = true;
      const params = {
        token: this.$store.getters["auth/access_token"],
        data: formData,
      };
      await this.$store
        .dispatch("record/create_record", params)
        .then(() => {
          this.closeForm();
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>
<style lang="scss" scoped>
.tag {
  font-weight: 400;
}
.body-content {
  height: calc(80vh - 64px) !important;
}
</style>
