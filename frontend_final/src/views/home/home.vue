<template>
  <v-app>
    <div>
      <!-- section 1 -->
      <v-card
        width="100%"
        class="d-flex flex-column flex-lg-row justify-space-around align-center"
        min-height="500px"
        color="#EEF2F6"
      >
        <v-card
          color="#EEF2F6"
          max-width="564px"
          elevation="0"
          class="d-flex justify-sm-center"
        >
          <div class="d-flex flex-column pa-12">
            <v-card
              max-width="564px"
              min-height="96px"
              color="#EEF2F6"
              elevation="0"
              ><p
                class="font-weight-bold ma-0 d-flex justify-lg-start justify-center"
                :style="{ color: '#537DA5', fontSize: section1_title + 'px' }"
              >
                Blouse Care | Ứng dụng
              </p>
              <p
                class="font-weight-bold d-flex justify-lg-start justify-center"
                :style="{ color: '#537DA5', fontSize: section1_title + 'px' }"
              >
                đặt lịch khám online hàng đầu
              </p>
            </v-card>
            <div class="d-flex flex-column">
              <v-card
                max-width="564px"
                min-height="96px"
                color="#EEF2F6"
                elevation="0"
                class="d-flex flex-column"
                ><p
                  class="font-weight-medium text-center text-lg-start"
                  style="color: #667085"
                >
                  Người tiêu dùng thông thái, hãy khám bệnh thông minh. Bạn là
                  người tiêu dùng thông thái, bạn yêu gia đình hơn chính bản
                  thân mình. Hãy cùng Blouse Care chăm sóc sức khỏe cho bản thân
                  và gia đình để mỗi ngày bên nhau là một ngày ý nghĩa.
                </p></v-card
              >
            </div>

            <!-- fast book appointment -->
            <div class="d-flex justify-center justify-lg-start">
              <v-dialog v-model="fast_book_dialog" width="640px">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    height="48px"
                    width="155px"
                    color="#537DA5"
                    class="white--text text-body-1"
                    style="text-transform: none"
                    v-on="on"
                    v-bind="attrs"
                    >Đặt khám ngay</v-btn
                  >
                </template>
                <v-card class="d-flex flex-column">
                  <v-card
                    class="d-flex justify-end mt-10"
                    elevation="0"
                    width="586px"
                  >
                    <v-icon color="#101828" @click="fast_book_dialog = false"
                      >mdi-close</v-icon
                    >
                  </v-card>
                  <v-card class="d-flex flex-column" elevation="0" width="100%">
                    <div class="d-flex justify-center">
                      <v-img
                        height="107px"
                        width="177px"
                        contain
                        src="@/assets/img/home/fast_book.svg"
                      ></v-img>
                    </div>
                  </v-card>
                  <p
                    class="d-flex justify-center font-weight-bold"
                    style="font-size: 32px"
                  >
                    Đặt khám nhanh
                  </p>
                  <p class="d-flex justify-center">
                    Vui lòng để lại thông tin, chúng tôi sẽ liên hệ lại với bạn
                    để tư vấn.
                  </p>
                  <!-- input -->
                  <v-card
                    class="d-flex justify-center"
                    height="328px"
                    elevation="0"
                  >
                    <v-card width="592px" elevation="0">
                      <div class="d-flex flex-column">
                        <div class="d-flex flex-row">
                          <div class="d-flex flex-column">
                            <label for="" class="font-weight-bold text-body-2"
                              >Họ và tên</label
                            >
                            <v-card
                              width="288px"
                              height="44px"
                              class="d-flex mb-5"
                              elevation="0"
                              outlined
                            >
                              <v-text-field
                                placeholder="VD: Nguyen Xuan H"
                                solo
                                flat
                                dense
                                class="text-body-2"
                              ></v-text-field
                            ></v-card>
                          </div>
                          <div class="d-flex flex-column ml-4">
                            <label for="" class="font-weight-bold text-body-2"
                              >Số điện thoại</label
                            >
                            <v-card
                              width="288px"
                              height="44px"
                              class="d-flex mb-5"
                              elevation="0"
                              outlined
                            >
                              <v-text-field
                                placeholder="VD: 012345689"
                                solo
                                flat
                                dense
                                class="text-body-2"
                              ></v-text-field
                            ></v-card>
                          </div>
                        </div>
                        <div class="d-flex flex-column">
                          <label for="" class="font-weight-bold text-body-2"
                            >Nhu cầu khám</label
                          >
                          <v-card
                            width="592px"
                            height="128px"
                            class="d-flex mb-5"
                            elevation="0"
                            outlined
                          >
                            <v-textarea
                              solo
                              placeholder="Vui lòng nhập mô tả về nhu cầu khám"
                              class="text-body-2"
                              dense
                              flat
                              height="120px"
                            ></v-textarea
                          ></v-card>
                        </div>
                        <div class="d-flex flex-row">
                          <v-card class="d-flex align-center" elevation="0">
                            <v-img
                              sizes="24px"
                              src="@/assets/img/home/phone_icon.svg"
                              contain
                            ></v-img>
                            <p class="ma-0 ml-3 font-weight-medium text-body-2">
                              Liên hệ Hotline:
                            </p>
                            <p
                              class="ma-0 ml-2 font-weight-medium text-body-2"
                              style="color: #537da5"
                            >
                              190017473
                            </p>
                          </v-card>

                          <v-card class="ml-auto"
                            ><v-btn
                              class="d-flex align-center btn"
                              color="#537DA5"
                              ><v-img
                                src="@/assets/img/home/send_mail_icon.svg"
                                sizes="20px"
                                class="mr-3"
                              ></v-img>
                              <p class="ma-0 white--text">Gửi yêu cầu</p></v-btn
                            ></v-card
                          >
                        </div>
                      </div>
                    </v-card>
                  </v-card>
                </v-card>
              </v-dialog>
            </div>
          </div>
        </v-card>
        <!-- <v-card height="500px">a</v-card> -->
        <v-card
          class="d-flex flex-row align-end"
          :height="$vuetify.breakpoint.mdAndDown ? '250px' : '500'"
          elevation="0"
          color="#EEF2F6"
        >
          <v-card
            elevation="0"
            color="#EEF2F6"
            :width="$vuetify.breakpoint.mdAndDown ? '300px' : '600px'"
          >
            <v-img src="@/assets/img/home/section1-doctor.svg"></v-img>
          </v-card>
        </v-card>
      </v-card>

      <!-- section 2 -->
      <v-layout>
        <v-card
          class="d-flex flex-column justify-center"
          width="100%"
          height="296px"
        >
          <p
            class="d-flex justify-center font-weight-bold mb-0"
            style="font-size: 40px"
          >
            Tra cứu nhanh thông tin sức khỏe
          </p>
          <!---->
          <v-card elevation="0" class="d-flex justify-center">
            <v-card
              elevation="0"
              class="d-flex align-center mt-8"
              width="60%"
              height="76px"
              outlined
            >
              <v-menu
                bottom
                :offset-y="true"
                v-model="show_search_menu"
                allow-overflow
                content-class="menu-content"
              >
                <template v-slot:activator="{}">
                  <v-card
                    class="d-flex flex-row align-center"
                    width="100%"
                    elevation="0"
                  >
                    <v-text-field
                      v-model="search"
                      full-width
                      solo
                      flat
                      placeholder="Tìm kiếm triệu chứng, bệnh viện, phòng khám, bác sĩ, ..."
                      hide-details=""
                    ></v-text-field>

                    <v-divider class="mx-2" inset vertical></v-divider>
                    <v-menu bottom :offset-y="true">
                      <template v-slot:activator="{ attrs, on }">
                        <v-btn
                          color="#667085"
                          elevation="0"
                          class="font-weight-medium text-body-1 btn"
                          text
                          v-bind="attrs"
                          v-on="on"
                          width="135px"
                          height="44px"
                        >
                          Địa điểm <v-icon right>mdi-chevron-down</v-icon>
                        </v-btn>
                      </template>
                    </v-menu>
                    <v-btn
                      color="#537DA5"
                      elevation="0"
                      class="white--text btn font-weight-medium text-body-1 mr-3"
                      @click="show_search_menu = true"
                      >Tìm kiếm
                      <v-img
                        class="ml-3"
                        src="@/assets/img/home/search_icon.svg"
                      ></v-img
                    ></v-btn>
                  </v-card>
                </template>
                <!-- search result -->
                <v-card class="d-flex flex-column pa-3" width="100%">
                  <div class="d-flex flex-column" v-if="!showSearchResults">
                    <v-card
                      width="100%"
                      class="d-flex justify-center"
                      elevation="0"
                      style="background: none"
                    >
                      <v-img
                        class="d-flex"
                        src="@/assets/img/user/profile/lookingNotFound.png"
                        width="183px"
                        height="186px"
                        contain
                      ></v-img>
                    </v-card>
                    <p
                      class="font-weight-bold ml-8 d-flex justify-center"
                      style="font-size: 20px"
                    >
                      Không có kết quả tìm kiếm
                    </p>
                    <p
                      class="ml-8 d-flex justify-center"
                      style="color: #667085"
                    >
                      Xin lỗi chúng tôi không tìm thấy kết quả phù hợp với bạn.
                    </p>
                  </div>
                  <!-- hospital -->
                  <v-card
                    v-if="hospitals.length"
                    class="d-flex flex-column"
                    elevation="0"
                    width="100%"
                  >
                    <v-card
                      class="d-flex flex-row justify-center align-center"
                      elevation="0"
                    >
                      <v-card width="500" elevation="0">
                        <p
                          class="font-weight-bold"
                          style="color: #667085; font-size:20px; margin: 0"
                        >
                          Cơ sở y tế
                        </p>
                      </v-card>
                      <v-card
                        width="500"
                        elevation="0"
                        class="d-flex justify-end"
                      >
                        <v-btn
                          text
                          class="btn text-body-2"
                          color="#537DA5"
                          @click="$router.push('/hospital-appointment/')"
                          >Xem thêm<v-icon class="ml-2" small color="#537da5"
                            >mdi-arrow-right</v-icon
                          ></v-btn
                        >
                      </v-card>
                    </v-card>
                    <!-- <p class="font-weight-bold">Cơ sở y tế</p> -->
                    <div class="d-flex justify-center">
                      <v-card
                        width="1000"
                        class="d-flex flex-wrap"
                        elevation="0"
                      >
                        <v-card
                          v-for="(hospital, iHospital) in hospitals"
                          :key="iHospital"
                          width="500"
                          height="150"
                          class="d-flex flex-row pa-3 align-center"
                          elevation="0"
                        >
                          <v-card>
                            <v-img
                              style="width: 86px"
                              :src="getImgOfHospital(hospital)"
                            ></v-img>
                          </v-card>
                          <div class="ml-2">
                            <p class="ma-0 font-weight-medium">
                              {{ hospital.name }}
                            </p>
                            <div class="d-flex">
                              <v-icon
                                class="align-self-start mr-2"
                                medium
                                color="#537da5"
                                >mdi-map-marker-outline</v-icon
                              >
                              <p class="text-body-2 mb-3">
                                {{ get_hospital_address(hospital) }}
                              </p>
                            </div>
                          </div>
                        </v-card>
                      </v-card>
                    </div>
                  </v-card>
                  <v-card class="d-flex flex-row justify-center" elevation="0">
                    <v-card
                      class="d-flex flex-column"
                      width="500"
                      elevation="0"
                      v-if="doctors.length"
                    >
                      <div
                        class="d-flex flex-row justify-space-between align-center"
                      >
                        <p
                          class="font-weight-bold"
                          style="color: #667085; font-size:20px; margin: 0"
                        >
                          Bác sĩ
                        </p>
                        <v-btn
                          text
                          class="btn text-body-2"
                          color="#537DA5"
                          @click="$router.push('/doctor-appointment/')"
                          >Xem thêm<v-icon class="ml-2" small color="#537da5"
                            >mdi-arrow-right</v-icon
                          ></v-btn
                        >
                      </div>

                      <v-card
                        v-for="(doctor, iDoctor) in doctors"
                        :key="iDoctor"
                        class="pa-3 mb-3 d-flex flex-row align-center"
                        width="100%"
                        elevation="0"
                      >
                        <v-avatar size="86" class="align-self-start">
                          <v-img :src="getImgOfDoctor(doctor)"></v-img>
                        </v-avatar>

                        <div class="ml-2">
                          <p class="ma-0 font-weight-medium">
                            {{ doctor.level }}. {{ doctor.name }}
                          </p>
                          <div class="d-flex">
                            <v-icon small color="#537da5">mdi-domain</v-icon>
                            <p
                              class="ma-0  text-body-2 ml-2"
                              style="color: #667085"
                            >
                              {{ doctor.hospital.name }}
                            </p>
                          </div>
                          <v-card
                            class="py-1 px-2 mr-2 font-weight-medium text-body-2 d-flex justify-center"
                            elevation="0"
                            color="#F9FAFB"
                            dark
                            style="border: 1px solid #d0d5dd; color: #667085; width: fit-content"
                          >
                            {{ doctor.specialty }}</v-card
                          >
                          <div class="d-flex flex-row align-center mt-1">
                            <p style="font-size: 90%">Giá khám:</p>
                            <p
                              class="ml-3 font-weight-bold"
                              style="color: #537da5"
                            >
                              {{ doctor.price }} đ
                            </p>
                          </div>
                        </div>
                      </v-card>
                    </v-card>
                    <v-card
                      class="d-flex flex-column"
                      width="500"
                      elevation="0"
                      v-if="services.length"
                    >
                      <div
                        class="d-flex flex-row justify-space-between align-center"
                      >
                        <p
                          class="font-weight-bold"
                          style="color: #667085; font-size:20px; margin: 0"
                        >
                          Dịch vụ
                        </p>
                        <v-btn
                          text
                          class="btn text-body-2"
                          color="#537DA5"
                          @click="$router.push('/service-appointment/')"
                          >Xem thêm<v-icon class="ml-2" small color="#537da5"
                            >mdi-arrow-right</v-icon
                          ></v-btn
                        >
                      </div>
                      <v-card
                        v-for="(service, iService) in services"
                        :key="iService"
                        class="pa-3 mb-3 d-flex flex-row align-center"
                        width="100%"
                        elevation="0"
                      >
                        <v-card>
                          <v-img
                            style="width: 86px"
                            :src="getImgOfService(service)"
                          ></v-img>
                        </v-card>
                        <div class="ml-2">
                          <p class="ma-0 font-weight-medium">
                            {{ service.name }}
                          </p>
                          <div class="d-flex">
                            <v-icon small color="#537da5">mdi-domain</v-icon>
                            <p
                              class="ma-0  text-body-2 ml-2"
                              style="color: #667085"
                            >
                              {{ service.hospital.name }}
                            </p>
                          </div>
                          <p
                            class="font-weight-bold text-body-2"
                            style="color: #537da5"
                          >
                            {{ service.price }} đ
                          </p>
                        </div>
                      </v-card>
                    </v-card>
                  </v-card>
                </v-card>
              </v-menu>
            </v-card>
          </v-card>
          <!-- search loading -->
          <v-card width="100%" class="d-flex justify-center" elevation="0">
            <v-card
              :loading="false"
              width="60%"
              height="3"
              elevation="0"
            ></v-card>
          </v-card>

          <!---->
          <v-card elevation="0" class="d-flex justify-center">
            <v-card
              elevation="0"
              class="d-flex align-center mt-5"
              width="720px"
            >
              <p class="ma-0 mr-4 font-weight-medium text-body-1">
                Tìm kiếm phổ biến:
              </p>
              <v-card
                v-for="(item, i) in poular_search_list"
                :key="i"
                class="py-1 px-2 mr-2 font-weight-medium text-body-2"
                elevation="0"
                color="#F9FAFB"
                dark
                style="border: 1px solid #d0d5dd; color: #667085"
                >{{ item }}</v-card
              >
            </v-card>
          </v-card>
        </v-card>
      </v-layout>

      <!-- section 3 -->
      <v-layout>
        <v-card
          class="d-flex flex-column"
          width="100%"
          height="441px"
          color="#FCFCFD"
          elevation="0"
        >
          <v-card>
            <v-card class="d-flex justify-center" color="#FCFCFD" elevation="0">
              <v-card
                class="d-flex justify-space-between mt-6"
                width="65%"
                color="#FCFCFD"
                elevation="0"
              >
                <p class="font-weight-bold" :style="{ 'font-size': 24 + 'px' }">
                  Dịch vụ nổi bật
                </p>
                <v-btn
                  class="btn font-weight-medium text-body-1"
                  elevation="0"
                  color="#FCFCFD"
                  :style="{ color: '#667085' }"
                  @click="$router.push('/service-appointment/')"
                  >Xem tất cả
                  <v-icon class="ml-2" small color="#537da5"
                    >mdi-arrow-right</v-icon
                  ></v-btn
                >
              </v-card>
            </v-card>
          </v-card>
          <v-card elevation="0" color="#FCFCFD">
            <v-card class="d-flex justify-center" elevation="0" color="#FCFCFD">
              <div class="d-flex flex-row">
                <v-card
                  class="mr-8"
                  v-for="(item, i) in service_list"
                  :key="i"
                  width="320px"
                  height="290px"
                >
                  <div class="d-flex flex-column mx-3 mt-3">
                    <v-img :src="item.img" width="296px" height="145px"></v-img>
                    <p class="font-weight-medium text-body-2 mt-3">
                      {{ item.title }}
                    </p>
                    <p
                      class="font-weight-medium text-body-2"
                      style="color: #667085"
                    >
                      {{ item.place }}
                    </p>
                    <p
                      class="font-weight-bold text-body-2"
                      style="color: #537da5"
                    >
                      {{ item.price }}
                    </p>
                  </div>
                </v-card>
              </div>
            </v-card>
          </v-card>
        </v-card>
      </v-layout>

      <!-- section 4 -->
      <v-layout>
        <v-card
          height="336px"
          class="d-flex flex-column"
          width="100%"
          elevation="0"
        >
          <p
            class="d-flex justify-center mt-16 text-body-1 font-weight-bold"
            style="color: #537da5"
          >
            Dịch vụ
          </p>
          <p
            class="d-flex justify-center font-weight-bold mb-0"
            style="font-size: 40px"
          >
            Trải nghiệm khám bệnh hiện đại
          </p>
          <p
            class="d-flex justify-center font-weight-bold"
            style="font-size: 40px"
          >
            cùng Blouse Care
          </p>
        </v-card>
      </v-layout>

      <!-- section 5 -->
      <v-layout>
        <v-card
          class="d-flex justify-center"
          width="100%"
          height="312px"
          elevation="0"
        >
          <v-card
            class="d-flex flex-column mr-8"
            v-for="(item, index) in intro_list"
            :key="index"
            height="228px"
            width="280px"
            elevation="0"
          >
            <div class="d-flex justify-center mb-3">
              <v-card
                width="56px"
                height="56px"
                class="d-flex align-center"
                color="#FCFCFD"
                elevation="0"
              >
                <v-img
                  :src="item.img"
                  width="33px"
                  height="22px"
                  contain
                ></v-img>
              </v-card>
            </div>
            <v-card class="d-flex flex-column" height="140px" elevation="0">
              <p
                class="d-flex justify-center text-center text-h6 font-weight-bold"
              >
                {{ item.title }}
              </p>
              <p
                class="d-flex justify-center text-center font-weight-regular"
                style="color: #667085"
              >
                {{ item.content }}
              </p>
            </v-card>
            <v-btn
              elevation="0"
              color="#537DA5"
              text
              class="btn font-weight-medium text-body-1"
              >Khám phá ngay</v-btn
            >
          </v-card>
        </v-card>
      </v-layout>

      <!-- section 6 -->
      <v-layout>
        <v-card
          height="600px"
          width="100%"
          elevation="0"
          color="#FCFCFD"
          class="d-flex flex-column"
        >
          <!-- Container -->
          <v-card elevation="0" color="#FCFCFD" class="d-flex flex-column">
            <p
              class="d-flex justify-center mt-16 font-weight-bold text-body-1"
              style="color: #537da5"
            >
              Chuyên khoa
            </p>
            <p
              class="d-flex justify-center font-weight-bold"
              style="font-size: 40px"
            >
              Một số chuyên khoa nổi bật
            </p>
            <div class="d-flex justify-center">
              <v-card elevation="0" color="#FCFCFD" width="717px">
                <p
                  class="d-flex justify-center font-weight-normal text-body-1 text-center"
                  style="color: #667085"
                >
                  Blouse Care - Nền tảng đặt lịch khám online hàng đầu với các
                  bệnh viện, phòng khám uy tín trên cả nước: Hà Nội, Hồ Chí
                  Minh, Đà Nẵng, Cần Thơ,… chọn dịch vụ khám sản phụ khoa, khám
                  tiêu hóa, khám cơ xương khớp, khám sức khỏe tổng quát, khám
                  nhi.
                </p>
              </v-card>
            </div>
          </v-card>
          <!-- List Member -->
          <v-card
            elevation="0"
            color="#FCFCFD"
            class="d-flex justify-center mt-16"
          >
            <v-card
              width="183px"
              height="148px"
              class="d-flex justify-center align-center mr-6"
              v-for="(item, i) in special_list"
              :key="i"
              elevation="0"
              link
            >
              <div class="d-flex flex-column">
                <div class="d-flex justify-center">
                  <v-card
                    width="56px"
                    height="56px"
                    class="d-flex align-center"
                    color="#EEF2F6"
                    elevation="0"
                    rounded="circle"
                  >
                    <v-img
                      :src="item.img"
                      width="33px"
                      height="22px"
                      contain
                    ></v-img>
                  </v-card>
                </div>
                <p class="d-flex justify-center font-weight-medium mt-5">
                  {{ item.title }}
                </p>
              </div>
            </v-card>
          </v-card>
          <!-- Button view all -->
          <div class="d-flex justify-center mt-6">
            <v-btn
              width="122px"
              height="48px"
              class="white--text btn text-body-1 rounded-lg"
              elevation="0"
              color="#537DA5"
              >Xem tất cả
            </v-btn>
          </div>
        </v-card>
      </v-layout>
    </div>
  </v-app>
</template>

<script>
const url = process.env.VUE_APP_ROOT_API;
export default {
  data: () => ({
    search: "",
    show_search_menu: false,
    offset: true,
    items: [
      { title: "Click Me" },
      { title: "Click Me" },
      { title: "Click Me" },
      { title: "Click Me 2" }
    ],
    fast_book_dialog: false,
    card_list_2: [
      {
        image: require("@/assets/img/home/home_icon1.png"),
        label: "Chẩn đoán hình ảnh",
        name: "Chẩn đoán hình ảnh"
      },
      {
        image: require("@/assets/img/home/home_icon2.png"),
        label: "Cơ - Xương - Khớp",
        name: "Cơ - Xương - Khớp"
      },
      {
        image: require("@/assets/img/home/home_icon3.png"),
        label: "Nhi khoa",
        name: "Nhi khoa"
      },
      {
        image: require("@/assets/img/home/home_icon4.png"),
        label: "Sản - Phụ Khoa",
        name: "Sản - Phụ khoa"
      },
      {
        image: require("@/assets/img/home/home_icon5.png"),
        label: "Tai - Mũi - Họng",
        name: "Tai - Mũi - Họng"
      },
      {
        image: require("@/assets/img/home/home_icon6.png"),
        label: "Tim mạch",
        name: "Tim mạch"
      },
      {
        image: require("@/assets/img/home/home_icon7.png"),
        label: "Hô hấp",
        name: "Hô hấp và miễn dịch dị ứng lâm sàng"
      },
      {
        image: require("@/assets/img/home/home_icon8.png"),
        label: "Nội soi",
        name: "Nội soi"
      },
      {
        image: require("@/assets/img/home/home_icon9.png"),
        label: "Tiêu hóa gan mật",
        name: "Tiêu hóa gan mật"
      },
      {
        image: require("@/assets/img/home/home_icon10.png"),
        label: "Xét nghiệm",
        name: "Xét nghiệm"
      },
      {
        image: require("@/assets/img/home/home_icon11.png"),
        label: "Khoa ngoại",
        name: "Khoa ngoại"
      },
      {
        image: require("@/assets/img/home/home_icon12.png"),
        label: "Khoa tâm thần",
        name: "Tâm thần kinh"
      }
    ],

    poular_search_list: [
      "Khám tai mũi họng",
      "Khám sản phụ khoa",
      "Nội soi tiêu hóa",
      "Siêu âm 2D"
    ],
    service_list: [
      {
        img: require("@/assets/img/home/service5.jpeg"),
        title: "Gói Khám Gan nhiễm mỡ - Tổ hợp y tế MEDIPLUS",
        place: "Tổ Hợp Phòng Khám MEDIPLUS",
        price: "2.480.000 đ"
      },
      {
        img: require("@/assets/img/home/service6.png"),
        title:
          "Gói khám tổng quát hệ tiêu hoá, gan mật tiêu chuẩn - Tổ hợp y tế MEDIPLUS",
        place: "Tổ Hợp Phòng Khám MEDIPLUS",
        price: "2.220.000 đ"
      },
      {
        img: require("@/assets/img/home/service7.jpeg"),
        title: "Gói Xét nghiệm tổng quát cho Nam - TTXN LABHOUSE",
        place: "Tổ Hợp Phòng Khám MEDIPLUS",
        price: "1.335.000 đ"
      },
      {
        img: require("@/assets/img/home/service8.png"),
        title:
          "Test nhanh Cúm A,B cho người Nước ngoài tại Bệnh viện Đa khoa Bảo Sơn",
        place: "Tổ Hợp Phòng Khám MEDIPLUS",
        price: "2.480.000 đ"
      }
    ],
    intro_list: [
      {
        img: require("@/assets/img/home/service_intro1.svg"),
        title: "Đặt khám từ xa",
        content:
          "Bác sĩ của Blouse Care luôn sẵn sàng tư vấn và chăm sóc sức khỏe cho bạn mọi lúc mọi nơi qua video & chat."
      },
      {
        img: require("@/assets/img/home/service_intro2.svg"),
        title: "Đội ngũ chuyên gia và cơ sở y tế hàng đầu",
        content:
          "Dễ dàng kết nối với các bác sĩ ưu tú, tận tâm, có chuyên môn cao."
      },
      {
        img: require("@/assets/img/home/service_intro3.svg"),
        title: "Tiếp đón ưu tiên với nhiều quyền lợi đặc biệt",
        content:
          "Đặt khám hẹn trước tại các bệnh viện trung ương, phòng khám hàng đầu."
      },
      {
        img: require("@/assets/img/home/service_intro4.svg"),
        title: "Giá khám bằng với giá tại cơ sở y tế",
        content:
          "Không những vậy còn giúp bạn tiết kiệm thời gian và các chi phí khác."
      }
    ],
    special_list: [
      {
        img: require("@/assets/img/home/ThanKinh.svg"),
        title: "Thần kinh"
      },
      {
        img: require("@/assets/img/home/Nhi.svg"),
        title: "Nhi khoa"
      },
      {
        img: require("@/assets/img/home/CoXuongKhop.svg"),
        title: "Cơ - Xương - Khớp"
      },
      {
        img: require("@/assets/img/home/San.svg"),
        title: "Sản - Phụ khoa"
      },
      {
        img: require("@/assets/img/home/TaiMuiHong.svg"),
        title: "Tai - Mũi - Họng"
      },
      {
        img: require("@/assets/img/home/Tim.svg"),
        title: "Tim mạch"
      }
    ],
    hospitals: [],
    doctors: [],
    services: []
  }),
  watch: {
    search: {
      handler: _.debounce(function() {
        this.getSearchResults();
      }, 400)
    }
  },
  methods: {
    async getSearchResults() {
      const params = {
        value: this.search
      };
      const res = await this.axios.post(`${url}/api/generic-search`, params);
      this.services = res.data.results.services;
      this.doctors = res.data.results.doctors;
      this.hospitals = res.data.results.hospitals;
      this.show_search_menu = true;
    },
    login() {
      this.$router.push({ name: "Đăng nhập" });
    },
    signup() {
      this.$router.push({ name: "Đăng ký" });
    },
    getlink(index) {
      this.$router.push({ name: this.card_list_2[index].name });
    },
    getpage(name) {
      this.$router.push({ name: name });
    },
    get_hospital_address(hospital) {
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
    getImgOfService(service) {
      if (service.imageUrl != null) {
        return service.imageUrl;
      } else {
        return require("@/assets/img/home/service_avt.png");
      }
    },
    getImgOfDoctor(doctor) {
      if (doctor.imageUrl != null) {
        return doctor.imageUrl;
      } else {
        return require("@/assets/img/user/profile/avatar1.svg");
      }
    }
  },
  computed: {
    showSearchResults() {
      return (
        this.doctors.length || this.hospitals.length || this.services.length
      );
    },
    columns() {
      if (this.$vuetify.breakpoint.xl) {
        return 6;
      }

      if (this.$vuetify.breakpoint.lg) {
        return 6;
      }

      if (this.$vuetify.breakpoint.md) {
        return 4;
      }
      if (this.$vuetify.breakpoint.sm) {
        return 2;
      }

      return 1;
    },
    section1_title() {
      if (this.$vuetify.breakpoint.mdAndDown) {
        return 24;
      }
      return 40;
    }
  }
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
.menu-content {
  margin-top: 20px;
}
</style>
