<template>
  <v-card class="d-flex flex-column pa-8">
    <!-- header -->
    <v-divider></v-divider>
    <v-card elevation="0 pa-5">
      <p class="font-weight-bold ma-0" style="font-size: 24px">
        Quản lý bệnh viện
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
        <v-btn icon @click="open_add_dialog()" class="mr-3">
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
        :items="hospital_list"
        :items-per-page="5"
        class="elevation-0"
        show-select
        :footer-props="{ itemsPerPageText: 'Số hàng' }"
      >
        <template v-slot:[`item.image`]="{ item }">
          <div class="d-flex justify-center">
            <v-card width="100" height="60" class="ma-2" elevation="0">
              <v-img :src="getImgOfHospital(item)"></v-img>
            </v-card>
          </div>
        </template>
        <template v-slot:[`item.address`]="{ item }">
          <div class="d-flex flex-row align-center">
            <p class="ma-0">
              {{ get_hospital_address(item) }}
            </p>
          </div>
        </template>
        <template v-slot:[`footer.page-text`]="items">
          {{ items.pageStart }} - {{ items.pageStop }} của
          {{ items.itemsLength }}
        </template>
      </v-data-table>
    </v-card>
    <!-- some dialog here -->
    <!-- add/edit dialog -->
    <v-dialog width="1000" v-model="submit_dialog">
      <v-card class="pa-5 d-flex flex-column">
        <!-- header -->
        <p v-if="is_add_dialog" class="font-weight-bold text-h6 mb-5">Thêm</p>
        <p v-else class="font-weight-bold text-h6 mb-5">Chỉnh sửa</p>

        <!-- list field item -->
        <v-card class="d-flex flex-wrap justify-space-between" elevation="0">
          <!-- field -->
          <v-card class="d-flex flex-column mb-5" width="30%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Tên Bệnh viện</p>
            <v-card outlined width="100%" elevation="0">
              <v-text-field
                :spellcheck="false"
                flat
                solo
                hide-details=""
                v-model="hospital_form.name"
              ></v-text-field>
            </v-card>
          </v-card>
          <!-- empty field -->
          <v-card elevation="0" width="50%"></v-card>
          <!-- filed image -->
          <v-card
            width="50%"
            flat
            class="d-flex flex-row align-center mb-5"
            max-width="400px"
            max-height="172px"
          >
            <!-- <v-avatar size="64">
              <v-img contain :src="avt_img" ref="avt" />
            </v-avatar> -->
            <v-card class="ma-2" elevation="0">
              <v-img
                width="100"
                height="60"
                :src="getImgOfHospital(hospital_form)"
                ref="avt"
                contain
              ></v-img>
            </v-card>
            <v-card
              class="d-flex align-center justify-center ml-8 pa-2"
              width="100%"
              color="#FCFCFD"
              height="100%"
              elevation="0"
            >
              <div class="d-flex flex-column">
                <div class="d-flex justify-center">
                  <v-card
                    class="d-flex align-center justify-center pa-2"
                    rounded="circle"
                    color="#EEF2F6"
                  >
                    <v-icon
                      color="#537DA5"
                      large
                      @click="$refs.avt_file.click()"
                      >mdi-cloud-upload-outline
                    </v-icon>
                    <input
                      type="file"
                      ref="avt_file"
                      accept="image/*"
                      style="display: none"
                      @change="previewAvatarFiles"
                    />
                  </v-card>
                </div>
                <p class="d-flex font-weight-medium justify-center">
                  Nhấn để tải ảnh đại diện
                </p>
              </div>
            </v-card>
          </v-card>

          <!-- filed image -->
          <v-card
            width="50%"
            flat
            class="d-flex flex-row align-center mb-5"
            max-width="400px"
            max-height="172px"
          >
            <!-- <v-avatar size="64">
              <v-img contain :src="avt_img" ref="avt" />
            </v-avatar> -->
            <v-card class="ma-2" elevation="0">
              <v-img
                width="100"
                height="60"
                :src="getMapOfHospital(hospital_form)"
                ref="avt"
                contain
              ></v-img>
            </v-card>
            <v-card
              class="d-flex align-center justify-center ml-8 pa-2"
              width="100%"
              color="#FCFCFD"
              height="100%"
              elevation="0"
            >
              <div class="d-flex flex-column">
                <div class="d-flex justify-center">
                  <v-card
                    class="d-flex align-center justify-center pa-2"
                    rounded="circle"
                    color="#EEF2F6"
                  >
                    <v-icon
                      color="#537DA5"
                      large
                      @click="$refs.map_file.click()"
                      >mdi-cloud-upload-outline
                    </v-icon>
                    <input
                      type="file"
                      ref="map_file"
                      accept="image/*"
                      style="display: none"
                      @change="previewMapFiles"
                    />
                  </v-card>
                </div>
                <p class="d-flex font-weight-medium justify-center">
                  Nhấn để tải ảnh minimap
                </p>
              </div>
            </v-card>
          </v-card>

          <!-- field -->
          <v-card class="d-flex flex-column mb-3" width="100%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Thông tin</p>
            <v-card outlined width="100%">
              <v-textarea
                :spellcheck="false"
                flat
                solo
                hide-details=""
                v-model="hospital_form.info"
              ></v-textarea>
            </v-card>
          </v-card>

          <!-- field -->
          <v-card class="d-flex flex-column mb-5" width="30%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Quốc gia</p>
            <v-card outlined width="100%" elevation="0">
              <v-text-field
                flat
                solo
                hide-details=""
                v-model="hospital_form.address.country"
              ></v-text-field>
            </v-card>
          </v-card>

          <!-- field -->
          <v-card class="d-flex flex-column mb-5" width="30%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Tỉnh/Thành phố</p>
            <v-card outlined width="100%" elevation="0">
              <v-combobox
                :items="province_list_show"
                v-model="hospital_form.address.province"
                item-text="text"
                item-value="text"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
                hide-details=""
                :spellcheck="false"
              ></v-combobox>
            </v-card>
          </v-card>

          <!-- field -->
          <v-card class="d-flex flex-column mb-5" width="30%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Quận/Huyện</p>
            <v-card outlined width="100%" elevation="0">
              <v-combobox
                :items="town_list_show"
                v-model="hospital_form.address.district"
                label=""
                item-text="text"
                item-value="value"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
                hide-details=""
              ></v-combobox>
            </v-card>
          </v-card>

          <!-- field -->
          <v-card class="d-flex flex-column mb-5" width="30%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">Xã/Phường</p>
            <v-card outlined width="100%" elevation="0">
              <v-combobox
                :items="commune_list_show"
                v-model="hospital_form.address.ward"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
                hide-details=""
              ></v-combobox>
            </v-card>
          </v-card>

          <!-- field -->
          <v-card class="d-flex flex-column mb-5" width="30%" elevation="0">
            <!-- label -->
            <p class="mb-2 font-weight-medium text-body-2">
              Số nhà và tên đường
            </p>
            <v-card outlined width="100%" elevation="0">
              <v-text-field
                flat
                solo
                hide-details=""
                v-model="hospital_form.address.address"
              ></v-text-field>
            </v-card>
          </v-card>
          <!-- empty -->
          <v-card
            class="d-flex flex-column mb-5"
            width="30%"
            elevation="0"
          ></v-card>
        </v-card>

        <p class="font-weight-bold">Lịch làm việc</p>
        <!-- monday -->
        <div class="d-flex flex-column">
          <p class="mb-0 mb-3 font-weight-medium text-body-2">
            Thứ hai:
          </p>
          <div class="d-flex flex-row mb-3">
            <!-- work time -->
            <div class="d-flex flex-column mr-2">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ làm việc:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.mondayTimeStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.mondayTimeEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>

            <!-- break time -->
            <div class="d-flex flex-column">
              <p class="ma-0  text-body-2" style="color: #667085">
                Giờ giải lao:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.mondayTimeBreakStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.mondayTimeBreakEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>
          </div>
        </div>

        <!-- Tuesday -->
        <div class="d-flex flex-column">
          <p class="mb-0 mb-3 font-weight-medium text-body-2">Thứ ba:</p>
          <div class="d-flex flex-row justify-space-around mb-3">
            <!-- work time -->
            <div class="d-flex flex-column mr-2">
              <p class="ma-0  text-body-2" style="color: #667085">
                Giờ làm việc:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.tuesdayTimeStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.tuesdayTimeEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>

            <!-- break time -->
            <div class="d-flex flex-column">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ giải lao:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.tuesdayTimeBreakStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.tuesdayTimeBreakEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>
          </div>
        </div>

        <!-- wednesday -->
        <div class="d-flex flex-column">
          <p class="mb-0 mb-3 font-weight-medium text-body-2">Thứ tư:</p>
          <div class="d-flex flex-row mb-3">
            <!-- work time -->
            <div class="d-flex flex-column mr-2">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ làm việc:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.wednesdayTimeStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.wednesdayTimeEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>

            <!-- break time -->
            <div class="d-flex flex-column">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ giải lao:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.wednesdayTimeBreakStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.wednesdayTimeBreakEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>
          </div>
        </div>

        <!-- thursday -->
        <div class="d-flex flex-column">
          <p class="mb-0 mb-3 font-weight-medium text-body-2">Thứ năm:</p>
          <div class="d-flex flex-row mb-3">
            <!-- work time -->
            <div class="d-flex flex-column mr-2">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ làm việc:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.thursdayTimeStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.thursdayTimeEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>

            <!-- break time -->
            <div class="d-flex flex-column">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ giải lao:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.thursdayTimeBreakStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.thursdayTimeBreakEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>
          </div>
        </div>

        <!-- friday -->
        <div class="d-flex flex-column">
          <p class="mb-0 mb-3 font-weight-medium text-body-2">Thứ sáu:</p>
          <div class="d-flex flex-row mb-3">
            <!-- work time -->
            <div class="d-flex flex-column mr-2">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ làm việc:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.fridayTimeStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.fridayTimeEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>

            <!-- break time -->
            <div class="d-flex flex-column">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ giải lao:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.fridayTimeBreakStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.fridayTimeBreakEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>
          </div>
        </div>

        <!-- saturday -->
        <div class="d-flex flex-column">
          <p class="mb-0 mb-3 font-weight-medium text-body-2">Thứ bảy:</p>
          <div class="d-flex flex-row mb-3">
            <!-- work time -->
            <div class="d-flex flex-column mr-2">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ làm việc:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.saturdayTimeStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.saturdayTimeEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>

            <!-- break time -->
            <div class="d-flex flex-column">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ giải lao:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.saturdayTimeBreakStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.saturdayTimeBreakEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>
          </div>
        </div>

        <!-- sunday -->
        <div class="d-flex flex-column">
          <p class="mb-0 mb-3 font-weight-medium text-body-2">Chủ nhật:</p>
          <div class="d-flex flex-row mb-3">
            <!-- work time -->
            <div class="d-flex flex-column mr-2">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ làm việc:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.sundayTimeStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.sundayTimeEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>

            <!-- break time -->
            <div class="d-flex flex-column">
              <p class="ma-0 text-body-2" style="color: #667085">
                Giờ giải lao:
              </p>
              <div class="d-flex flex-row align-center">
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.sundayTimeBreakStart"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
                <p class="mb-0 mx-5 font-weight-medium text-body-2">-</p>
                <v-card outlined elevation="0">
                  <v-combobox
                    v-model="hospital_form.hospitalHour.sundayTimeBreakEnd"
                    flat
                    solo
                    hide-details=""
                    dense
                    :items="time_frame"
                  ></v-combobox>
                </v-card>
              </div>
            </div>
          </div>
        </div>

        <!-- submit/cancle -->
        <v-card
          width="100%"
          class="d-flex flex-row justify-space-between mt-16"
          elevation="0"
        >
          <v-btn
            elevation="0"
            outlined
            color="#D0D5DD"
            @click="closeForm()"
            class="btn"
            ><p class="ma-0 font-weight-bold" style="color:#667085">
              Hủy
            </p></v-btn
          >
          <v-btn
            v-if="is_add_dialog"
            class="btn white--text"
            color="#537DA5"
            elevation="0"
            @click="createHospital()"
            >Thêm mới</v-btn
          >
          <v-btn
            v-else
            class="btn white--text"
            color="#537DA5"
            elevation="0"
            @click="editHospital()"
            >Lưu chỉnh sửa</v-btn
          >
        </v-card>
      </v-card>
    </v-dialog>

    <!-- remove  dialog -->
    <v-dialog v-model="remove_dialog" width="408px">
      <v-card height="192px" class="d-flex flex-column">
        <div class="d-flex flex-column mt-8 ml-8">
          <p style="font-size: 24px" class="font-weight-bold">
            Xác nhận xoá bệnh viện
          </p>
          <p style="color: #667085">
            Bạn có chắc chắn muốn xoá bệnh viện này không?
          </p>
        </div>
        <div class="d-flex flex-row justify-space-between ml-8 mr-8">
          <v-btn
            class="btn font-weight-medium"
            width="160px"
            outlined
            color="#667085"
            text
            @click="remove_dialog = false"
            >Huỷ bỏ</v-btn
          >
          <v-btn
            class="btn white--text font-weight-medium"
            width="160px"
            color="#F04438"
            elevation="0"
            @click="onDeleteSubmit()"
            >Xác nhận xóa</v-btn
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
      //show dialog
      submit_dialog: false,
      is_add_dialog: true,
      remove_dialog: false,
      //table
      headers: [
        {
          text: "Ảnh đại diện",
          value: "image",
          sortable: false,
          align: "center"
        },
        {
          text: "Tên",
          align: "start",
          sortable: false,
          value: "name"
        },

        { text: "Địa chỉ", value: "address", sortable: false },
        {
          text: "Số lượt đăng ký",
          value: "registrationNumber",
          align: "center"
        },
        {
          text: "Đánh giá",
          value: "favorite",
          align: "center"
        }
      ],
      selected: [],

      hospital_list: [],

      time_frame: [
        "7:00",
        "8:00",
        "9:00",
        "10:00",
        "11:00",
        "12:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "17:00",
        "18:00"
      ],
      province_list: [],
      province_list_show: [],
      town_list: [],
      town_list_show: [],
      commune_list: [],
      commune_list_show: [],
      hospital_form: {
        id: "",
        name: "",
        address: {
          id: "",
          country: "Việt Nam",
          province: "",
          district: "",
          ward: "",
          address: ""
        },
        info: "",
        mapImageUrl: null,
        imageUrl: null,
        hospitalHour: {
          id: "",
          mondayTimeStart: "",
          mondayTimeEnd: "",
          mondayTimeBreakStart: "",
          mondayTimeBreakEnd: "",
          tuesdayTimeStart: "",
          tuesdayTimeEnd: "",
          tuesdayTimeBreakStart: "",
          tuesdayTimeBreakEnd: "",
          wednesdayTimeStart: "",
          wednesdayTimeEnd: "",
          wednesdayTimeBreakStart: "",
          wednesdayTimeBreakEnd: "",
          thursdayTimeStart: "",
          thursdayTimeEnd: "",
          thursdayTimeBreakStart: "",
          thursdayTimeBreakEnd: "",
          fridayTimeStart: "",
          fridayTimeEnd: "",
          fridayTimeBreakStart: "",
          fridayTimeBreakEnd: "",
          saturdayTimeStart: "",
          saturdayTimeEnd: "",
          saturdayTimeBreakStart: "",
          saturdayTimeBreakEnd: "",
          sundayTimeStart: "",
          sundayTimeEnd: "",
          sundayTimeBreakStart: "",
          sundayTimeBreakEnd: ""
        },
        is_avt_change: false,
        is_map_change: false,
        avatar_img: null,
        minimap_img: null,
        registrationNumber: 0,
        favorite: 0.0,
        isActive: true
      }
    };
  },

  created() {
    this.get_hospital_list();
    this.getProvines();
  },
  watch: {
    "hospital_form.address.province": {
      handler() {
        this.getDistricts();
      }
    },
    "hospital_form.address.district": {
      handler() {
        this.getWards();
      }
    }
  },
  methods: {
    open_add_dialog() {
      this.is_add_dialog = true;
      this.refreshForm();
      this.submit_dialog = true;
    },

    open_edit_dialog() {
      this.is_add_dialog = false;
      this.hospital_form.id = this.selected[0].id;
      this.hospital_form.name = this.selected[0].name;
      this.hospital_form.info = this.selected[0].info;
      this.hospital_form.imageUrl = this.selected[0].imageUrl;
      this.hospital_form.mapImageUrl = this.selected[0].mapImageUrl;
      this.hospital_form.address.id = this.selected[0].address.id;
      this.hospital_form.address.country = this.selected[0].address.country;
      this.hospital_form.address.province = this.selected[0].address.province;
      this.hospital_form.address.district = this.selected[0].address.district;
      this.hospital_form.address.ward = this.selected[0].address.ward;
      this.hospital_form.address.address = this.selected[0].address.address;
      this.hospital_form.hospitalHour.id = this.selected[0].hospitalHour.id;
      this.hospital_form.hospitalHour.mondayTimeStart = this.getTimeStart(
        this.selected[0].hospitalHour.mondayTime
      );
      this.hospital_form.hospitalHour.mondayTimeEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.mondayTime
      );
      this.hospital_form.hospitalHour.mondayTimeBreakStart = this.getTimeStart(
        this.selected[0].hospitalHour.mondayTimeBreak
      );
      this.hospital_form.hospitalHour.mondayTimeBreakEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.mondayTimeBreak
      );
      this.hospital_form.hospitalHour.tuesdayTimeStart = this.getTimeStart(
        this.selected[0].hospitalHour.tuesdayTime
      );
      this.hospital_form.hospitalHour.tuesdayTimeEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.tuesdayTime
      );
      this.hospital_form.hospitalHour.tuesdayTimeBreakStart = this.getTimeStart(
        this.selected[0].hospitalHour.tuesdayTimeBreak
      );
      this.hospital_form.hospitalHour.tuesdayTimeBreakEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.tuesdayTimeBreak
      );
      this.hospital_form.hospitalHour.wednesdayTimeStart = this.getTimeStart(
        this.selected[0].hospitalHour.wednesdayTime
      );
      this.hospital_form.hospitalHour.wednesdayTimeEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.wednesdayTime
      );
      this.hospital_form.hospitalHour.wednesdayTimeBreakStart = this.getTimeStart(
        this.selected[0].hospitalHour.wednesdayTimeBreak
      );
      this.hospital_form.hospitalHour.wednesdayTimeBreakEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.wednesdayTimeBreak
      );
      this.hospital_form.hospitalHour.thursdayTimeStart = this.getTimeStart(
        this.selected[0].hospitalHour.thursdayTime
      );
      this.hospital_form.hospitalHour.thursdayTimeEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.thursdayTime
      );
      this.hospital_form.hospitalHour.thursdayTimeBreakStart = this.getTimeStart(
        this.selected[0].hospitalHour.thursdayTimeBreak
      );
      this.hospital_form.hospitalHour.thursdayTimeBreakEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.thursdayTimeBreak
      );
      this.hospital_form.hospitalHour.fridayTimeStart = this.getTimeStart(
        this.selected[0].hospitalHour.fridayTime
      );
      this.hospital_form.hospitalHour.fridayTimeEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.fridayTime
      );
      this.hospital_form.hospitalHour.fridayTimeBreakStart = this.getTimeStart(
        this.selected[0].hospitalHour.fridayTimeBreak
      );
      this.hospital_form.hospitalHour.fridayTimeBreakEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.fridayTimeBreak
      );
      this.hospital_form.hospitalHour.saturdayTimeStart = this.getTimeStart(
        this.selected[0].hospitalHour.saturdayTime
      );
      this.hospital_form.hospitalHour.saturdayTimeEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.saturdayTime
      );
      this.hospital_form.hospitalHour.saturdayTimeBreakStart = this.getTimeStart(
        this.selected[0].hospitalHour.saturdayTimeBreak
      );
      this.hospital_form.hospitalHour.saturdayTimeBreakEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.saturdayTimeBreak
      );
      this.hospital_form.hospitalHour.sundayTimeStart = this.getTimeStart(
        this.selected[0].hospitalHour.sundayTime
      );
      this.hospital_form.hospitalHour.sundayTimeEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.sundayTime
      );
      this.hospital_form.hospitalHour.sundayTimeBreakStart = this.getTimeStart(
        this.selected[0].hospitalHour.sundayTimeBreak
      );
      this.hospital_form.hospitalHour.sundayTimeBreakEnd = this.getTimeEnd(
        this.selected[0].hospitalHour.sundayTimeBreak
      );
      this.hospital_form.registrationNumber = this.selected[0].registrationNumber;
      this.hospital_form.favorite = this.selected[0].favorite;
      this.hospital_form.isActive = this.selected[0].isActive;
      this.submit_dialog = true;
    },

    async get_hospital_list() {
      await axios.get(`${url}/api/hospitals`).then(res => {
        this.hospital_list = res.data.results;
      });
    },

    get_hospital_address(hospital) {
      if (!hospital?.address) return "";
      let address_str = "";
      if (hospital.address.address != null) {
        if (address_str.length == 0) {
          address_str = hospital.address.address;
        }
      }
      if (hospital.address.ward != null) {
        address_str =
          address_str.length == 0
            ? hospital.address.ward
            : address_str + ", " + hospital.address.ward;
      }
      if (hospital.address.district != null) {
        address_str =
          address_str.length == 0
            ? hospital.address.district
            : address_str + ", " + hospital.address.district;
      }
      if (hospital.address.province != null) {
        address_str =
          address_str.length == 0
            ? hospital.address.province
            : address_str + ", " + hospital.address.province;
      }

      if (hospital.address.country != null) {
        address_str =
          address_str.length == 0
            ? hospital.address.country
            : address_str + ", " + hospital.address.country;
      }
      return address_str;
    },
    getImgOfHospital(hospital) {
      if (hospital.imageUrl != null) {
        return hospital.imageUrl;
      } else {
        return require("@/assets/img/home/hospital_avt.png");
      }
    },

    getMapOfHospital(hospital) {
      if (hospital.mapImageUrl != null) {
        return hospital.mapImageUrl;
      } else {
        return require("@/assets/img/home/hospital_avt.png");
      }
    },

    previewAvatarFiles(event) {
      let file_1 = event.target.files[0];
      let reader = new FileReader();
      reader.addEventListener(
        "load",
        () => {
          // convert image file to base64 string
          this.hospital_form.imageUrl = reader.result;
        },
        false
      );
      if (file_1) {
        reader.readAsDataURL(file_1);
        this.avatar_img = file_1;
      } else {
        this.hospital_form.imageUrl = require("@/assets/img/user/profile/avatar1.svg");
      }
    },

    previewMapFiles(event) {
      let file_1 = event.target.files[0];
      let reader = new FileReader();
      reader.addEventListener(
        "load",
        () => {
          // convert image file to base64 string
          this.hospital_form.mapImageUrl = reader.result;
        },
        false
      );
      if (file_1) {
        reader.readAsDataURL(file_1);
        this.mapImageUrl = file_1;
      } else {
        this.hospital_form.mapImageUrl = require("@/assets/img/user/profile/avatar1.svg");
      }
    },

    getTimeStart(timeFrame) {
      let arr = timeFrame.split(" - ");
      if (arr.length != 2) return "";
      return arr[0];
    },
    getTimeEnd(timeFrame) {
      let arr = timeFrame.split(" - ");
      if (arr.length != 2) return "";
      return arr[1];
    },
    refreshForm() {
      this.hospital_form.id = "";
      this.hospital_form.name = "";
      this.hospital_form.info = "";
      this.hospital_form.imageUrl = null;
      this.hospital_form.mapImageUrl = null;
      this.hospital_form.address.id = "";
      this.hospital_form.address.country = "";
      this.hospital_form.address.province = "";
      this.hospital_form.address.district = "";
      this.hospital_form.address.ward = "";
      this.hospital_form.address.address = "";
      this.hospital_form.hospitalHour.id = "";
      this.hospital_form.hospitalHour.mondayTimeStart = "";
      this.hospital_form.hospitalHour.mondayTimeEnd = "";
      this.hospital_form.hospitalHour.mondayTimeBreakStart = "";
      this.hospital_form.hospitalHour.mondayTimeBreakEnd = "";
      this.hospital_form.hospitalHour.tuesdayTimeStart = "";
      this.hospital_form.hospitalHour.tuesdayTimeEnd = "";
      this.hospital_form.hospitalHour.tuesdayTimeBreakStart = "";
      this.hospital_form.hospitalHour.tuesdayTimeBreakEnd = "";
      this.hospital_form.hospitalHour.wednesdayTimeStart = "";
      this.hospital_form.hospitalHour.wednesdayTimeEnd = "";
      this.hospital_form.hospitalHour.wednesdayTimeBreakStart = "";
      this.hospital_form.hospitalHour.wednesdayTimeBreakEnd = "";
      this.hospital_form.hospitalHour.thursdayTimeStart = "";
      this.hospital_form.hospitalHour.thursdayTimeEnd = "";
      this.hospital_form.hospitalHour.thursdayTimeBreakStart = "";
      this.hospital_form.hospitalHour.thursdayTimeBreakEnd = "";
      this.hospital_form.hospitalHour.fridayTimeStart = "";
      this.hospital_form.hospitalHour.fridayTimeEnd = "";
      this.hospital_form.hospitalHour.fridayTimeBreakStart = "";
      this.hospital_form.hospitalHour.fridayTimeBreakEnd = "";
      this.hospital_form.hospitalHour.saturdayTimeStart = "";
      this.hospital_form.hospitalHour.saturdayTimeEnd = "";
      this.hospital_form.hospitalHour.saturdayTimeBreakStart = "";
      this.hospital_form.hospitalHour.saturdayTimeBreakEnd = "";
      this.hospital_form.hospitalHour.sundayTimeStart = "";
      this.hospital_form.hospitalHour.sundayTimeEnd = "";
      this.hospital_form.hospitalHour.sundayTimeBreakStart = "";
      this.hospital_form.hospitalHour.sundayTimeBreakEnd = "";
      this.hospital_form.registrationNumber = 0;
      this.hospital_form.favorite = 0.0;
      this.hospital_form.isActive = true;
      this.avatar_img = null;
      this.minimap_img = null;
    },

    closeForm() {
      this.refreshForm();
      this.submit_dialog = false;
    },

    async createHospital() {
      let imageUrl = this.hospital_form.imageUrl;
      let mapImageUrl = this.hospital_form.mapImageUrl;
      if (this.avatar_img != null) {
        imageUrl = await this.post_file(this.avatar_img);
      }

      if (this.minimap_img != null) {
        mapImageUrl = await this.post_file(this.minimap_img);
      }

      let token = this.$store.getters["auth/access_token"];
      const data = {
        name: this.hospital_form.name,
        address: {
          country: this.hospital_form.address.country,
          province: this.hospital_form.address.province,
          district: this.hospital_form.address.district,
          ward: this.hospital_form.address.ward,
          address: this.hospital_form.address.address
        },
        info: this.hospital_form.info,
        mapImageUrl: mapImageUrl,
        imageUrl: imageUrl,
        hospitalHour: {
          mondayTime:
            this.hospital_form.hospitalHour.mondayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.mondayTimeEnd,
          mondayTimeBreak:
            this.hospital_form.hospitalHour.mondayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.mondayTimeBreakEnd,
          tuesdayTime:
            this.hospital_form.hospitalHour.tuesdayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.tuesdayTimeEnd,
          tuesdayTimeBreak:
            this.hospital_form.hospitalHour.tuesdayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.tuesdayTimeBreakEnd,
          wednesdayTime:
            this.hospital_form.hospitalHour.wednesdayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.wednesdayTimeEnd,
          wednesdayTimeBreak:
            this.hospital_form.hospitalHour.wednesdayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.wednesdayTimeBreakEnd,
          thursdayTime:
            this.hospital_form.hospitalHour.thursdayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.thursdayTimeEnd,
          thursdayTimeBreak:
            this.hospital_form.hospitalHour.thursdayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.thursdayTimeBreakEnd,
          fridayTime:
            this.hospital_form.hospitalHour.fridayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.fridayTimeEnd,
          fridayTimeBreak:
            this.hospital_form.hospitalHour.fridayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.fridayTimeBreakEnd,
          saturdayTime:
            this.hospital_form.hospitalHour.saturdayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.saturdayTimeEnd,
          saturdayTimeBreak:
            this.hospital_form.hospitalHour.saturdayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.saturdayTimeBreakEnd,
          sundayTime:
            this.hospital_form.hospitalHour.sundayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.sundayTimeEnd,
          sundayTimeBreak:
            this.hospital_form.hospitalHour.sundayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.sundayTimeBreakEnd
        }
      };

      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };

      await axios.post(`${url}/api/hospitals`, data).then(response => {
        if (response.data.code === 200) {
          console.log("Tạo lịch bệnh viện");
        }
      });
      this.get_hospital_list();

      this.submit_dialog = false;
    },

    async editHospital() {
      let imageUrl = this.hospital_form.imageUrl;
      let mapImageUrl = this.hospital_form.mapImageUrl;
      if (this.avatar_img != null) {
        imageUrl = await this.post_file(this.avatar_img);
      }

      if (this.minimap_img != null) {
        mapImageUrl = await this.post_file(this.minimap_img);
      }
      let token = this.$store.getters["auth/access_token"];
      const data = {
        id: this.hospital_form.id,
        name: this.hospital_form.name,
        address: {
          id: this.hospital_form.address.id,
          country: this.hospital_form.address.country,
          province: this.hospital_form.address.province,
          district: this.hospital_form.address.district,
          ward: this.hospital_form.address.ward,
          address: this.hospital_form.address.address
        },
        info: this.hospital_form.info,
        mapImageUrl: mapImageUrl,
        imageUrl: imageUrl,
        hospitalHour: {
          id: this.hospital_form.hospitalHour.id,
          mondayTime:
            this.hospital_form.hospitalHour.mondayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.mondayTimeEnd,
          mondayTimeBreak:
            this.hospital_form.hospitalHour.mondayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.mondayTimeBreakEnd,
          tuesdayTime:
            this.hospital_form.hospitalHour.tuesdayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.tuesdayTimeEnd,
          tuesdayTimeBreak:
            this.hospital_form.hospitalHour.tuesdayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.tuesdayTimeBreakEnd,
          wednesdayTime:
            this.hospital_form.hospitalHour.wednesdayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.wednesdayTimeEnd,
          wednesdayTimeBreak:
            this.hospital_form.hospitalHour.wednesdayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.wednesdayTimeBreakEnd,
          thursdayTime:
            this.hospital_form.hospitalHour.thursdayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.thursdayTimeEnd,
          thursdayTimeBreak:
            this.hospital_form.hospitalHour.thursdayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.thursdayTimeBreakEnd,
          fridayTime:
            this.hospital_form.hospitalHour.fridayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.fridayTimeEnd,
          fridayTimeBreak:
            this.hospital_form.hospitalHour.fridayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.fridayTimeBreakEnd,
          saturdayTime:
            this.hospital_form.hospitalHour.saturdayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.saturdayTimeEnd,
          saturdayTimeBreak:
            this.hospital_form.hospitalHour.saturdayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.saturdayTimeBreakEnd,
          sundayTime:
            this.hospital_form.hospitalHour.sundayTimeStart +
            " - " +
            this.hospital_form.hospitalHour.sundayTimeEnd,
          sundayTimeBreak:
            this.hospital_form.hospitalHour.sundayTimeBreakStart +
            " - " +
            this.hospital_form.hospitalHour.sundayTimeBreakEnd
        },
        registrationNumber: this.hospital_form.registrationNumber,
        favorite: this.hospital_form.favorite,
        isActive: this.hospital_form.isActive
      };

      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };

      await axios.put(`${url}/api/hospitals`, data).then(response => {
        if (response.data.code === 200) {
          console.log("Chỉnh sửa bệnh viện thành công");
        }
      });
      this.get_hospital_list();

      this.submit_dialog = false;
    },

    async getProvines() {
      const res = await this.axios.get(
        `https://vn-public-apis.fpo.vn/provinces/getAll?limit=-1`
      );
      let provinces = res.data?.data?.data;
      for (let p of provinces) {
        this.province_list.push({ text: p.name, value: p.code });
        this.province_list_show.push(p.name);
      }
    },
    async getDistricts() {
      let province = (this.province_list || []).find(
        p => p.text == this.hospital_form.address.province
      );

      const params = {
        limit: -1,
        provinceCode: province.value
      };
      const res = await this.axios.get(
        `https://vn-public-apis.fpo.vn/districts/getByProvince`,
        { params: params }
      );
      this.town_list = [];
      this.town_list_show = [];
      let districts = res.data.data.data || [];
      for (let p of districts) {
        this.town_list.push({ text: p.name, value: p.code });
        this.town_list_show.push(p.name);
      }
    },
    async getWards() {
      let district = (this.town_list || []).find(
        p => p.text == this.hospital_form.address.district
      );
      const params = {
        limit: -1,
        districtCode: district.value
      };
      const res = await this.axios.get(
        `https://vn-public-apis.fpo.vn/wards/getByDistrict`,
        { params: params }
      );
      this.commune_list = [];
      this.commune_list_show = [];
      let wards = res.data.data.data || [];
      for (let p of wards) {
        this.commune_list.push({ text: p.name, value: p.code });
        this.commune_list_show.push(p.name);
      }
    },

    async post_file(file) {
      let form_data = new FormData();
      form_data.append("file", file);
      let token = this.$store.getters["auth/access_token"];
      let result = null;
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
            result = response.data.results;
          }
        });
      return result;
    },

    async deleteHospital(hospital_id) {
      let token = this.$store.getters["auth/access_token"];
      axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const data = {
        hospitalId: hospital_id
      };
      await axios
        .delete(`${url}/api/hospitals`, { params: data })
        .then(response => {
          if (response.data.code === 200) {
            console.log("Xóa bệnh viện");
          }
        });
    },

    async onDeleteSubmit() {
      await Promise.all(
        this.selected.map(async hospital => {
          await this.deleteHospital(hospital.id);
        })
      );

      this.get_hospital_list();
      this.remove_dialog = false;
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
</style>
