<template>
  <v-responsive aspect-ratio="18:1">
    <v-app-bar
      id="default-app-bar"
      color="white"
      height="80px"
      app
      elevation="1"
    >
      <v-row justify="space-around" class="hidden-sm-and-down" align="center">
        <div class="d-flex flex-row align-center">
          <v-avatar class="mr-2">
            <v-img src="@/assets/img/home/appbar/logo.png"></v-img>
          </v-avatar>
          <v-card class="font-weight-medium text-body-1 mr-lg-12" elevation="0">
            Blouse Care
          </v-card>
          <v-btn
            color="#667085"
            elevation="0"
            class="font-weight-medium text-body-1 btn-not-hover btn-not-transform"
            text
            @click="onHomeClick"
            >Trang chủ</v-btn
          >
          <v-menu
            offset-y
            open-on-hover
            content-class="elevation-1 overflow-hidden"
            style="overflow: hidden !important"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="#667085"
                elevation="0"
                class="font-weight-medium text-body-1 btn-not-hover btn-not-transform"
                text
                v-bind="attrs"
                v-on="on"
              >
                Đặt lịch <v-icon right>mdi-chevron-down</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item v-for="(item, i) in appointment_list" :key="i" link>
                <v-list-item-content
                  style="height: 92px; width: 296px"
                  @click="onAppointmentServiceClick(item)"
                >
                  <v-col cols="12" class="pa-0">
                    <v-row class="ma-0"
                      ><v-col cols="2" class="pb-0"
                        ><v-img :src="item.icon"></v-img></v-col
                      ><v-col cols="10" class="pl-0"
                        ><p class="font-weight-medium text-body-1 ma-0">
                          {{ item.title }}
                        </p>
                        <p class="text-body-2" style="color: #667085">
                          {{ item.content }}
                        </p></v-col
                      ></v-row
                    ></v-col
                  >
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-menu>
          <v-menu
            nudge-left="220"
            offset-y
            open-on-hover
            content-class="elevation-1 overflow-hidden"
            style="overflow: hidden !important"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="#667085"
                elevation="0"
                class="font-weight-medium text-body-1 btn-not-hover btn-not-transform"
                text
                v-bind="attrs"
                v-on="on"
              >
                Chuyên khoa <v-icon right>mdi-chevron-down</v-icon>
              </v-btn>
            </template>
            <div class="d-flex flex-row">
              <v-list>
                <v-list-item v-for="(item, i) in specialists_1" :key="i" link>
                  <v-list-item-content
                    style="height: 92px; width: 296px"
                    @click="onAppointmentServiceClick(item)"
                  >
                    <v-col cols="12" class="pa-0">
                      <v-row class="ma-0"
                        ><v-col cols="2" class="pb-0"
                          ><v-img :src="item.icon"></v-img></v-col
                        ><v-col cols="10" class="pl-0"
                          ><p class="font-weight-medium text-body-1 ma-0">
                            {{ item.title }}
                          </p>
                          <p class="text-body-2" style="color: #667085">
                            {{ item.content }}
                          </p></v-col
                        ></v-row
                      ></v-col
                    >
                  </v-list-item-content>
                </v-list-item>
              </v-list>
              <v-list>
                <v-list-item v-for="(item, i) in specialists_2" :key="i" link>
                  <v-list-item-content
                    style="height: 92px; width: 296px"
                    @click="onAppointmentServiceClick(item)"
                  >
                    <v-col cols="12" class="pa-0">
                      <v-row class="ma-0"
                        ><v-col cols="2" class="pb-0"
                          ><v-img :src="item.icon"></v-img></v-col
                        ><v-col cols="10" class="pl-0"
                          ><p class="font-weight-medium text-body-1 ma-0">
                            {{ item.title }}
                          </p>
                          <p class="text-body-2" style="color: #667085">
                            {{ item.content }}
                          </p></v-col
                        ></v-row
                      ></v-col
                    >
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </div>
          </v-menu>

          <v-btn
            color="#667085"
            elevation="0"
            class="font-weight-medium text-body-1 btn-not-hover btn-not-transform"
            text
            >Cộng đồng</v-btn
          >
          <v-btn
            color="#667085"
            elevation="0"
            class="font-weight-medium text-body-1 btn-not-hover btn-not-transform"
            text
            >Cẩm nang</v-btn
          >
        </div>
        <div class="d-flex flex-row align-center">
          <!-- Login Section -->
          <div v-show="!is_login">
            <v-dialog v-model="login_dialog" width="800px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  color="#667085"
                  elevation="0"
                  class="mr-3 font-weight-medium text-body-1 btn-not-hover btn-not-transform"
                  text
                  v-on="on"
                  v-bind="attrs"
                  >Đăng nhập</v-btn
                >
              </template>
              <v-card class="d-flex flex-row" height="610px">
                <v-card width="50%" elevation="0" class="d-flex flex-column">
                  <p
                    class="mt-16 ml-8 font-weight-bold"
                    :style="{ 'font-size': 32 + 'px' }"
                  >
                    Đăng nhập
                  </p>
                  <p
                    class="ml-8 font-weight-normal"
                    :style="{ 'font-size': 16 + 'px', color: '#667085' }"
                  >
                    Vui lòng nhập các thông tin phía dưới để đăng nhập.
                  </p>
                  <!-- input -->
                  <v-card
                    class="mt-12 ml-8 d-flex flex-column"
                    color="white"
                    elevation="0"
                  >
                    <v-card
                      width="320px"
                      height="44px"
                      class="d-flex mb-5"
                      elevation="0"
                      outlined
                    >
                      <v-text-field
                        v-model="user.username"
                        placeholder="Số điện thoại/Tên đăng nhập"
                        solo
                        flat
                        dense
                        class="text-body-2"
                      ></v-text-field
                    ></v-card>

                    <v-card width="320px" height="44px" elevation="0" outlined>
                      <v-text-field
                        v-model="user.password"
                        placeholder="Mật khẩu"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        solo
                        flat
                        dense
                        :type="show1 ? 'text' : 'password'"
                        @click:append="show1 = !show1"
                        class="text-body-2"
                      ></v-text-field>
                    </v-card>
                  </v-card>
                  <v-card width="320px" elevation="0" class="ml-8">
                    <a
                      class="d-flex font-weight-medium justify-end my-4 text-body-2"
                      style="color: #537da5"
                    >
                      Quên mật khẩu?
                    </a>
                  </v-card>
                  <v-btn
                    width="320px"
                    height="48px"
                    class="ml-8 white--text btn-not-hover btn-not-transform text-body-1"
                    color="#537DA5"
                    elevation="0"
                    @click="onLoginSubmmit"
                    >Đăng nhập</v-btn
                  >
                  <v-card
                    class="mt-8 d-flex flex-row align-center ml-8"
                    elevation="0"
                    width="320px"
                  >
                    <v-divider></v-divider>
                    <p class="ma-0 mx-3 text-body-2" style="color: #667085">
                      hoặc đăng nhập với
                    </p>
                    <v-divider></v-divider>
                  </v-card>
                  <v-card class="mt-8 ml-8 d-flex flex-row" elevation="0">
                    <v-btn
                      width="151.5px"
                      height="44px"
                      elevation="0"
                      color="#D0D5DD"
                      outlined
                      text
                      ><v-img
                        src="@/assets/img/home/logo-google.svg"
                        height="24px"
                        width="24px"
                        contain
                      ></v-img>
                      <v-card
                        class="ma-0 btn-not-hover btn-not-transform font-weight-medium"
                        style="color: #667085"
                        elevation="0"
                        width="60%"
                      >
                        Google
                      </v-card></v-btn
                    >
                    <v-btn
                      width="151.5px"
                      height="44px"
                      elevation="0"
                      color="#1674EA"
                      class="ml-4"
                      ><v-img
                        src="@/assets/img/home/logo-facebook.svg"
                        height="24px"
                        width="24px"
                        contain
                      ></v-img>
                      <p
                        class="font-weight-medium white--text ma-0 btn-not-hover btn-not-transform"
                      >
                        Facebook
                      </p></v-btn
                    >
                  </v-card>
                </v-card>
                <v-img
                  src="@/assets/img/home/login_image.png"
                  width="50%"
                ></v-img>
              </v-card>
            </v-dialog>
          </div>

          <!-- Sign up Section -->
          <div v-show="!is_login">
            <v-dialog v-model="sign_up_dialog" width="800px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  color="#537DA5"
                  elevation="0"
                  class="text-body-1 btn-not-hover btn-not-transform white--text"
                  v-on="on"
                  v-bind="attrs"
                  >Đăng ký</v-btn
                >
              </template>
              <v-card class="d-flex flex-row" height="654px">
                <v-card width="50%" elevation="0" class="d-flex flex-column">
                  <p
                    class="mt-16 ml-8 font-weight-bold"
                    :style="{ 'font-size': 32 + 'px' }"
                  >
                    Đăng ký
                  </p>
                  <p
                    class="ml-8 font-weight-normal"
                    :style="{ 'font-size': 16 + 'px', color: '#667085' }"
                  >
                    Vui lòng nhập đầy đủ thông tin phía dưới để tiến hành đăng
                    kí.
                  </p>
                  <!-- input -->
                  <v-card
                    class="mt-8 ml-8 mb-5 d-flex flex-column"
                    color="white"
                    elevation="0"
                  >
                    <v-card
                      width="320px"
                      height="44px"
                      class="d-flex mb-5"
                      elevation="0"
                      outlined
                    >
                      <v-text-field
                        v-model="sign_up_form.username"
                        placeholder="Tên đăng nhập"
                        solo
                        flat
                        dense
                        class="text-body-2"
                      ></v-text-field
                    ></v-card>
                    <v-card
                      width="320px"
                      height="44px"
                      class="d-flex mb-5"
                      elevation="0"
                      outlined
                    >
                      <v-text-field
                        v-model="sign_up_form.phone"
                        placeholder="Số điện thoại"
                        solo
                        flat
                        dense
                        class="text-body-2"
                      ></v-text-field
                    ></v-card>

                    <v-card width="320px" height="44px" elevation="0" outlined>
                      <v-text-field
                        v-model="sign_up_form.password"
                        placeholder="Mật khẩu"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        solo
                        flat
                        dense
                        :type="show1 ? 'text' : 'password'"
                        @click:append="show1 = !show1"
                        class="text-body-2"
                      ></v-text-field>
                    </v-card>
                  </v-card>
                  <v-btn
                    width="320px"
                    height="48px"
                    class="ml-8 white--text btn-not-hover btn-not-transform text-body-1"
                    color="#537DA5"
                    elevation="0"
                    @click="onSignupSubmit"
                    >Đăng kí</v-btn
                  >
                  <v-card
                    class="mt-8 d-flex flex-row align-center ml-8"
                    elevation="0"
                    width="320px"
                  >
                    <v-divider></v-divider>
                    <p class="ma-0 mx-3 text-body-2" style="color: #667085">
                      hoặc đăng kí với
                    </p>
                    <v-divider></v-divider>
                  </v-card>
                  <v-card class="mt-8 ml-8 d-flex flex-row" elevation="0">
                    <v-btn
                      width="151.5px"
                      height="44px"
                      elevation="0"
                      color="#D0D5DD"
                      outlined
                      text
                      ><v-img
                        src="@/assets/img/home/logo-google.svg"
                        height="24px"
                        width="24px"
                        contain
                      ></v-img>
                      <v-card
                        class="ma-0 btn-not-hover btn-not-transform font-weight-medium"
                        style="color: #667085"
                        elevation="0"
                        width="60%"
                      >
                        Google
                      </v-card></v-btn
                    >
                    <v-btn
                      width="151.5px"
                      height="44px"
                      elevation="0"
                      color="#1674EA"
                      class="ml-4"
                      ><v-img
                        src="@/assets/img/home/logo-facebook.svg"
                        height="24px"
                        width="24px"
                        contain
                      ></v-img>
                      <p
                        class="font-weight-medium white--text ma-0 btn-not-hover btn-not-transform"
                      >
                        Facebook
                      </p></v-btn
                    >
                  </v-card>
                </v-card>
                <v-img
                  src="@/assets/img/home/signup_image.png"
                  width="416px"
                  contain
                ></v-img>
              </v-card>
            </v-dialog>
          </div>
          <!-- show when logging success -->
          <div v-if="is_login">
            <div class="d-flex flex-row">
              <!-- notification -->
              <v-card
                rounded="circle"
                width="40px"
                height="40px"
                color="#FCFCFD"
                class="d-flex justify-center align-center mr-6"
                elevation="0"
              >
                <v-menu
                  offset-y
                  left
                  content-class="elevation-1 overflow-hidden"
                  style="overflow: hidden !important"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      class="btn-not-hover"
                      :ripple="false"
                      v-bind="attrs"
                      v-on="on"
                    >
                      <v-badge
                        :content="notificationValue"
                        :value="notificationValue"
                        color="red"
                        overlap
                      >
                        <v-icon> mdi-bell-outline </v-icon>
                      </v-badge>
                    </v-btn>
                  </template>
                  <v-card class="mx-auto" width="260px" tile>
                    <v-list v-if="messages.length">
                      <div v-for="(item, i) in messages" :key="i">
                        <v-list-item @click="goToNotification(item)">
                          <div class="d-flex flex-row">
                            <v-list-item-content>
                              <h4>{{ _.get(item, "title") }}</h4>
                              <p class="d-flex align-end pa-0 ma-0 text-body-2">
                                {{ getText(item) }}
                              </p>
                              <p
                                class="d-flex align-end pa-0 ma-0 text-body-2"
                                style="color: grey; font-size: 75% !important"
                              >
                                {{ timeSince(item.time) }}
                              </p>
                            </v-list-item-content>
                            <div class="d-flex align-center" v-if="!item.read">
                              <v-icon color="blue"> mdi-circle-medium </v-icon>
                            </div>
                          </div>
                        </v-list-item>
                      </div>
                    </v-list>
                    <v-list v-else>
                      <v-list-item class="d-flex justify-center"
                        ><p class="mt-3" style="color: grey">
                          không có thông báo
                        </p></v-list-item
                      >
                    </v-list>
                  </v-card>
                </v-menu>
              </v-card>

              <!-- dropdown user menu  -->
              <v-card
                rounded="circle"
                width="40px"
                height="40px"
                color="#FCFCFD"
                class="d-flex justify-center align-center"
                elevation="0"
              >
                <v-menu
                  offset-y
                  left
                  content-class="elevation-1 overflow-hidden"
                  style="overflow: hidden !important"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      class="btn-not-hover"
                      :ripple="false"
                      v-bind="attrs"
                      v-on="on"
                    >
                      <v-avatar size="40">
                        <img src="@/assets/img/home/appbar/avatar.png" />
                      </v-avatar>
                    </v-btn>
                  </template>
                  <v-card class="mx-auto" width="240px" tile>
                    <v-list>
                      <v-list-item
                        v-for="(item, i) in function_menu"
                        :key="i"
                        @click="onMenuClick(item)"
                      >
                        <div class="d-flex flex-row">
                          <v-list-item-icon class="mr-3">
                            <v-img
                              :src="item.icon"
                              height="20px"
                              width="20px"
                              contain
                              content-class
                              class="d-flex align-end justify-start"
                            ></v-img>
                          </v-list-item-icon>
                          <v-list-item-content>
                            <p
                              class="d-flex align-end pa-0 ma-0 text-body-2"
                              :style="{ color: item.color }"
                            >
                              {{ item.content }}
                            </p>
                          </v-list-item-content>
                        </div>
                      </v-list-item>
                    </v-list>
                  </v-card>
                </v-menu>
              </v-card>
            </div>
          </div>
        </div>
      </v-row>

      <!-- app bar nav icon -->
      <v-spacer class="hidden-md-and-up"></v-spacer>
      <v-menu
        bottom
        left
        v-model="nav_menu_icon_show"
        :close-on-click="false"
        :close-on-content-click="false"
      >
        <template v-slot:activator="{}">
          <v-app-bar-nav-icon
            @click="nav_menu_icon_show = true"
            class="hidden-md-and-up"
          />
        </template>
        <v-row class="hidden-md-and-up overflow-hidden ma-0 pa-0">
          <v-card
            width="500"
            height="100%"
            class="hidden-md-and-up d-flex flex-column  overflow-hidden"
          >
            <v-card
              height="72"
              class="d-flex flex-row align-center pa-2"
              elevation="0"
            >
              <v-avatar class="mr-2">
                <v-img src="@/assets/img/home/appbar/logo.png"></v-img>
              </v-avatar>
              <v-card
                class="font-weight-medium text-body-1 mr-lg-12"
                elevation="0"
              >
                Blouse Care
              </v-card>
              <v-spacer></v-spacer>
              <v-icon class="mr-1" @click="nav_menu_icon_show = false"
                >mdi-close</v-icon
              >
            </v-card>
            <v-divider style="border-color: #f2f4f7 !important"></v-divider>
            <v-card class="pa-2" elevation="0" v-if="is_login">
              <!-- dropdown user menu  -->
              <v-card
                rounded="circle"
                width="40px"
                height="40px"
                color="#FCFCFD"
                class="d-flex justify-center align-center"
                elevation="0"
              >
                <v-menu
                  offset-y
                  right
                  content-class="elevation-1 overflow-hidden"
                  style="overflow: hidden !important"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      class="btn-not-hover"
                      :ripple="false"
                      v-bind="attrs"
                      v-on="on"
                    >
                      <v-avatar size="40">
                        <img src="@/assets/img/home/appbar/avatar.png" />
                      </v-avatar>
                    </v-btn>
                  </template>
                  <v-card class="mx-auto hidden-md-and-up" width="240px" tile>
                    <v-list>
                      <v-list-item
                        v-for="(item, i) in function_menu"
                        :key="i"
                        @click="onMenuClick(item)"
                      >
                        <div class="d-flex flex-row">
                          <v-list-item-icon class="mr-3">
                            <v-img
                              :src="item.icon"
                              height="20px"
                              width="20px"
                              contain
                              content-class
                              class="d-flex align-end justify-start"
                            ></v-img>
                          </v-list-item-icon>
                          <v-list-item-content>
                            <p
                              class="d-flex align-end pa-0 ma-0 text-body-2"
                              :style="{ color: item.color }"
                            >
                              {{ item.content }}
                            </p>
                          </v-list-item-content>
                        </div>
                      </v-list-item>
                    </v-list>
                  </v-card>
                </v-menu>
              </v-card>
            </v-card>
            <v-divider
              style="border-color: #f2f4f7 !important"
              v-if="is_login"
            ></v-divider>

            <!-- operation list -->
            <v-list>
              <v-list-item @click="onHomeClick" color="#667085">
                <v-list-item-title
                  ><p
                    class="font-weight-medium text-body-1 ma-0"
                    style="color:#667085"
                  >
                    Trang chủ
                  </p>
                </v-list-item-title>
              </v-list-item>

              <v-list-group color="#667085">
                <template v-slot:activator>
                  <v-list-item-content>
                    <v-list-item-title
                      ><p
                        class="font-weight-medium text-body-1 ma-0"
                        style="color:#667085"
                      >
                        Đặt lịch
                      </p></v-list-item-title
                    >
                  </v-list-item-content>
                </template>
                <v-list-item
                  v-for="(item, i) in appointment_list"
                  :key="i"
                  link
                >
                  <v-list-item-content
                    style="height: 92px; width: 296px"
                    @click="onAppointmentServiceClick(item)"
                  >
                    <v-col cols="12" class="pa-0">
                      <v-row class="ma-0"
                        ><v-col cols="2" class="pb-0 pt-4"
                          ><v-img
                            :src="item.icon"
                            height="24"
                            width="24"
                          ></v-img></v-col
                        ><v-col cols="10" class="pl-0"
                          ><p class="font-weight-medium text-body-1 ma-0">
                            {{ item.title }}
                          </p>
                          <p class="text-body-2" style="color: #667085">
                            {{ item.content }}
                          </p></v-col
                        ></v-row
                      ></v-col
                    >
                  </v-list-item-content>
                </v-list-item>
              </v-list-group>
              <v-list-item>
                <v-list-item-title
                  ><p
                    class="font-weight-medium text-body-1 ma-0"
                    style="color:#667085"
                  >
                    Chuyên khoa
                  </p></v-list-item-title
                >
              </v-list-item>
              <v-list-item>
                <v-list-item-title>
                  <p
                    class="font-weight-medium text-body-1 ma-0"
                    style="color:#667085"
                  >
                    Cộng đồng
                  </p></v-list-item-title
                >
              </v-list-item>
              <v-list-item>
                <v-list-item-title
                  ><p
                    class="font-weight-medium text-body-1 ma-0"
                    style="color:#667085"
                  >
                    Cẩm nang
                  </p></v-list-item-title
                >
              </v-list-item>
              <v-list-item>
                <v-btn
                  elevation="0"
                  width="100%"
                  color="#537DA5"
                  class="white--text btn-not-transform"
                  @click="login_dialog = true"
                  ><p class="ma-0 text-body-1">Đăng nhập</p></v-btn
                >
              </v-list-item>
              <v-list-item>
                <v-btn
                  elevation="0"
                  width="100%"
                  color="#D4DFE9"
                  class="btn-not-transform"
                  @click="sign_up_dialog = true"
                  ><p class="ma-0 text-body-1" style="color:#537DA5">
                    Đăng ký
                  </p></v-btn
                >
              </v-list-item>
            </v-list>
          </v-card>
        </v-row>
      </v-menu>

      <!-- OTP -->
      <v-dialog v-model="opt_dialog" width="376px">
        <v-card
          height="678"
          elevation="0"
          class="d-flex flex-column align-center justify-center"
        >
          <v-card max-height="176" max-width="184" elevation="0">
            <v-img src="@/assets/img/home/appbar/otp_image.svg" contain></v-img>
          </v-card>
          <p class="font-weight-bold" style="font-size: 32px">Xác nhận OTP</p>
          <p class="font-weight-medium text-body-2 ma-0" style="color: #667085">
            Mã xác thực vừa được gửi tới số điện thoại:
          </p>
          <p class="font-weight-bold text-body-2">0962530448</p>
          <v-card max-width="80%" elevation="0">
            <v-otp-input length="6" type="number" v-model="otp"></v-otp-input>
          </v-card>
          <v-card width="245" elevation="0">
            <p class="text-center text-body-2" style="color: #667085">
              Nếu chưa nhận được mã xác thực, bấm Gửi lại mã sau:
            </p>
          </v-card>
          <v-btn
            elevation="0"
            color="#D0D5DD"
            class="btn-not-transform btn-not-hover text-body-2"
            text
            >Gửi lại mã</v-btn
          >
          <p style="color: #537da5" class="text-body-1 font-weight-medium">
            02:00
          </p>
          <v-btn
            elevation="0"
            color="#537DA5"
            class="btn-not-transform btn-not-hover text-body-1 white--text"
            width="320"
            height="48"
            @click="onOtpSubmit"
            >Xác nhận</v-btn
          >
        </v-card>
      </v-dialog>
    </v-app-bar>
  </v-responsive>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
const url = process.env.VUE_APP_ROOT_API;
// Utilities
const ButtonFunctionType = {
  LOG_OUT: 0,
  FUNCTION: 1
};
export default {
  name: "HomeBar",
  computed: {},

  data: () => ({
    show1: false,
    login_dialog: false,
    sign_up_dialog: false,
    opt_dialog: false,
    is_login: false,
    nav_menu_icon_show: false,
    appointment_list: [
      {
        icon: require("@/assets/img/home/appbar/doctor.svg"),
        title: "Bác sĩ",
        content:
          "Đặt lịch khám với bác sĩ chuyên khoa tại bệnh viện hoặc online",
        link_name: "Đặt lịch bác sĩ"
      },
      {
        icon: require("@/assets/img/home/appbar/hospital.svg"),
        title: "Bệnh viện",
        content: "Đặt lịch khám chuyên khoa tại các bệnh viện",
        link_name: "Đặt lịch bệnh viện"
      },
      {
        icon: require("@/assets/img/home/appbar/service.svg"),
        title: "Dịch vụ",
        content: "Các dịch vụ và gói khám tùy chọn theo nhu cầu",
        link_name: "Đặt lịch dịch vụ"
      }
    ],

    specialists_1: [
      {
        icon: require("@/assets/img/home/appbar/ChuanDoanHinhAnh.svg"),
        title: "Chuẩn đoán hình ảnh",
        content: "Khoa cận lâm sàng, thực hiện các kỹ thuật tạo ảnh y học ",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/CoXuongKhop.svg"),
        title: "Cơ Xương Khớp",
        content: "Khám chữa và khắc phục những tổn thương hệ vận động cơ",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/Nhi.svg"),
        title: "Nhi Khoa",
        content: "Khám chữa bệnh nội khoa của trẻ em",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/San.svg"),
        title: "Sản - Phụ Khoa",
        content:
          "Tập trung vào kiểm tra và chăm sóc sức khỏe tình dục và sinh sản",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/TaiMuiHong.svg"),
        title: "Tai - Mũi - Họng",
        content: "Khám, chữa bệnh chuyên sâu về tai, tai thần kinh, nền sọ",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/Tim.svg"),
        title: "Tim mạch",
        content: "Giúp chẩn đoán và điều trị những bệnh lý tim mạch",
        link_name: "Sản - Phụ khoa"
      }
    ],

    specialists_2: [
      {
        icon: require("@/assets/img/home/appbar/Phoi.svg"),
        title: "Hô hấp và miễn dịch dị ứng",
        content: "Khám và điều trị các bệnh về dị ứng, miễn dịch",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/NoiSoi.svg"),
        title: "Nội soi",
        content: "Nội soi cấp cứu bệnh nhân dị vật đường thở, đường ăn",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/Gan.svg"),
        title: "Tiêu hóa gan mật",
        content: "Khám và điều trị bệnh chuyên khoa Tiêu hóa – Gan mật",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/XetNghiem.svg"),
        title: "Xét Nghiệm",
        content:
          "Chức năng tiếp nhận nghiệm mẫu, thực hiện các kỹ thuật xét nghiệm",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/KhoaNgoai.svg"),
        title: "Khoa Ngoại",
        content: "Điều trị những bệnh ngoại khoa bằng phương pháp phẫu thuật",
        link_name: "Sản - Phụ khoa"
      },
      {
        icon: require("@/assets/img/home/appbar/ThanKinh.svg"),
        title: "Tâm thần kinh",
        content: "Chẩn đoán, điều trị và phòng ngừa các rối loạn tâm thần",
        link_name: "Sản - Phụ khoa"
      }
    ],

    function_menu: [
      {
        icon: require("@/assets/img/home/appbar/account_icon.svg"),
        content: "Hồ sơ bệnh nhân",
        color: "#667085",
        type: ButtonFunctionType.FUNCTION,
        link: "/home/user/profile"
      },
      {
        icon: require("@/assets/img/home/appbar/clock_icon.svg"),
        content: "Phiếu khám bệnh",
        color: "#667085",
        type: ButtonFunctionType.FUNCTION,
        link: "/home/user/appointment-history"
      },
      {
        icon: require("@/assets/img/home/appbar/document_icon.svg"),
        content: "Hồ sơ sức khỏe",
        color: "#667085",
        type: ButtonFunctionType.FUNCTION,
        link: "/home/user/health-records"
      },
      {
        icon: require("@/assets/img/home/appbar/connection_icon.svg"),
        content: "Lịch sử giao dịch",
        color: "#667085",
        type: ButtonFunctionType.FUNCTION,
        link: "/home/user/transaction-history"
      },
      {
        icon: require("@/assets/img/home/appbar/help_icon.svg"),
        content: "Câu hỏi của bạn",
        color: "#667085",
        type: ButtonFunctionType.FUNCTION,
        link: "/home/user/your-question"
      },
      {
        icon: require("@/assets/img/home/appbar/bell_menu_icon.svg"),
        content: "Thông báo",
        color: "#667085",
        type: ButtonFunctionType.FUNCTION,
        link: "/home/user/notify"
      },
      {
        icon: require("@/assets/img/home/appbar/logout_icon.svg"),
        content: "Đăng xuất",
        color: "#F04438",
        type: ButtonFunctionType.LOG_OUT,
        link: "/"
      }
    ],

    user: {
      username: "",
      password: ""
    },

    sign_up_form: {
      username: "",
      password: "",
      phone: ""
    },

    otp: "",
    connected: null,
    receivedMessages: [],
    socket: null,
    username: null
  }),

  created() {
    this.is_login = this.$store.getters["auth/isLogin"];
    this.username = this.$store.getters["auth/username"];
  },
  watch: {
    is_login: {
      handler(value) {
        if (value) {
          this.connect();
          this.fetchNotifications();
        } else this.disconnect();
      },
      immediate: true
    }
  },
  computed: {
    notificationValue() {
      return (this.messages || []).filter(m => !m.read).length;
    },
    messages() {
      return (this.receivedMessages || []).slice(-5);
    }
  },
  methods: {
    getText(item) {
      let text = this._.get(item, "text") || "";
      return text.length > 50
        ? `${_.get(item, "text").substring(0, 50)} ...`
        : _.get(item, "text");
    },
    goToNotification(item) {
      let token = this.$store.getters["auth/access_token"];

      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      this.axios.post(`${url}/api/notifications/read/${item.id}`);
      item.read = true;
      this.$router.push("/home/user/notify");
    },
    async fetchNotifications() {
      let token = this.$store.getters["auth/access_token"];

      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const params = {
        size: 5,
        page: 0
      };
      await this.axios
        .get(`${url}/api/notifications`, {
          params: params
        })
        .then(res => {
          this.receivedMessages = res.data?.results;
        });
    },
    connect() {
      this.socket = new SockJS(`${url}/ws`);
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        () => {
          this.connected = true;
          this.stompClient.subscribe(
            `/user/${this.username}/queue/notification`,
            tick => {
              var res = JSON.parse(tick.body);
              this.receivedMessages.unshift(res);
            }
          );
        },
        error => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    login() {
      this.$router.push({ name: "Đăng nhập" });
    },

    signup() {
      this.$router.push({ name: "Đăng ký" });
    },
    async getpage(link) {
      this.$router.push({ name: link.name }).catch(error => {
        if (error == null) {
          return;
        }
        if (error.name != "NavigationDuplicated") {
          throw error;
        }
      });
    },

    async onMenuClick(button) {
      if (button.type == ButtonFunctionType.LOG_OUT) {
        this.$store.dispatch("auth/logout", {});
        this.is_login = false;
      }
      this.$router.push({ path: button.link }).catch(error => {
        if (error == null) {
          return;
        }
        if (error.name != "NavigationDuplicated") {
          throw error;
        }
      });
    },

    async onLoginSubmmit() {
      //check login dev
      if (process.env.VUE_APP_LOGIN_DEV === "TRUE") {
        this.login_dialog = false;
        this.is_login = true;
        const user = {
          username: this.user.username,
          password: this.user.password
        };
        await this.$store.dispatch("auth/login_dev", user);
        if(this.user.username === "user")
        {
          this.$store.dispatch("snackbar/set_snackbar", {
            text: "Đăng nhập thành công",
            type: "success"
          });
        }

        if(this.user.username === "doctor")
        {
          this.$router.push({ name: "Lịch làm việc" });
        }
        if(this.user.username === "superadmin")
        {
          this.$router.push({ name: "Quản lý bệnh viện" });
        }
        if(this.user.username === "hospitaladmin")
        {
          this.$router.push({ name: "Quản lý bác sĩ" });
        }

        return;
      }

      const user = {
        username: this.user.username,
        password: this.user.password
      };
      await this.$store.dispatch("auth/login", user);

      if (this.$store.getters["auth/access_token"] != "") {
        this.login_dialog = false;
        this.is_login = true;
        let type = this.$store.getters["auth/types"];
        if ((type || []).some(t => t.authority === "ROLE_USER")) {
          this.$store.dispatch("snackbar/set_snackbar", {
            text: "Đăng nhập thành công",
            type: "success"
          });
        }
        if ((type || []).some(t => t.authority === "ROLE_DOCTOR")) {
          this.$router.push({ name: "Lịch làm việc" });
        }
        if ((type || []).some(t => t.authority === "ROLE_ADMIN")) {
          this.$router.push({ name: "Quản lý bệnh viện" });
        }
        if ((type || []).some(t => t.authority === "ROLE_HOSPITAL_ADMIN")) {
          this.$router.push({ name: "Quản lý bác sĩ" });
        }
      }
    },

    async onSignupSubmit() {
      const form = {
        username: this.sign_up_form.username,
        password: this.sign_up_form.password,
        phone: "+84" + this.sign_up_form.phone.substring(1),
        role: "ROLE_USER"
      };

      try {
        await this.$store.dispatch("auth/signup", form);
      } catch (error) {
        console.log(error.response); // this is the main part. Use the response property from the error object
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Dăng ký không thành công",
          type: "error"
        });
        return;
      }

      if (this.$store.getters["auth/is_signup_submit_success"]) {
        this.sign_up_dialog = false;
        this.opt_dialog = true;
      } else {
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Dăng ký không thành công",
          type: "error"
        });
      }
    },

    async onOtpSubmit() {
      const param = {
        username: this.sign_up_form.username,
        otp: this.otp
      };

      await this.$store.dispatch("auth/verify_signup", param);
      if (this.$store.getters["auth/is_verify_submit_success"]) {
        this.opt_dialog = false;
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Dăng ký thành công",
          type: "success"
        });
      } else {
        this.$store.dispatch("snackbar/set_snackbar", {
          text: "Dăng ký không thành công",
          type: "error"
        });
      }
    },

    onHomeClick() {
      this.$router.push({ name: "Trang chủ" }).catch(error => {
        if (error == null) {
          return;
        }
        if (error.name != "NavigationDuplicated") {
          throw error;
        }
      });
    },

    onAppointmentServiceClick(item) {
      this.$router.push({ name: item.link_name }).catch(error => {
        if (error == null) {
          return;
        }
        if (error.name != "NavigationDuplicated") {
          throw error;
        }
      });
    },
    timeSince(date) {
      if (typeof date !== "object") {
        date = new Date(date);
      }
      var seconds = Math.floor((new Date() - date) / 1000);

      var interval = seconds / 31536000;

      if (interval > 1) {
        return Math.floor(interval) + " năm trước";
      }
      interval = seconds / 2592000;
      if (interval > 1) {
        return Math.floor(interval) + " tháng trước";
      }
      interval = seconds / 86400;
      if (interval > 1) {
        return Math.floor(interval) + " ngày trước";
      }
      interval = seconds / 3600;
      if (interval > 1) {
        return Math.floor(interval) + " giờ trước";
      }
      interval = seconds / 60;
      if (interval > 1) {
        return Math.floor(interval) + " phút trước";
      }
      return Math.floor(seconds) + " giây trước";
    }
  }
};
</script>

<style scoped>
.btn-not-transform {
  text-transform: none;
}

.btn-not-hover:hover::before {
  opacity: 0 !important;
}
.text-truncated {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
