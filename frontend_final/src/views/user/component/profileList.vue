<template>
  <div>
    <!-- header section -->
    <v-card
      width="100%"
      height="132px"
      elevation="0"
      class="d-flex justify-space-between"
    >
      <div class="d-flex align-center ml-8">
        <div class="d-flex flex-column">
          <p class="font-weight-bold mb-3" style="font-size: 24px">
            Hồ sơ bệnh nhân
          </p>
          <p style="color: #667085" class="text-body-1">
            Chọn và cài đặt hồ sơ mà bạn muốn theo dõi sức khoẻ
          </p>
        </div>
      </div>
      <div class="d-flex align-center mr-8">
        <v-btn
          color="#537DA5"
          class="white--text btn-not-transform text-body-2"
          @click="createProfile"
          ><v-icon left>mdi-account-plus-outline</v-icon> Thêm mới hồ sơ</v-btn
        >
      </div>
    </v-card>
    <v-divider
      style="border-color: rgba(16, 24, 40, 0.03) !important"
    ></v-divider>
    <!-- remove profile dialog -->
    <v-dialog v-model="dialog" width="408px">
      <v-card height="192px" class="d-flex flex-column">
        <div class="d-flex flex-column mt-8 ml-8">
          <p style="font-size: 24px" class="font-weight-bold">
            Xác nhận xoá hồ sơ
          </p>
          <p style="color: #667085">
            Bạn có chắc chắn muốn xoá hồ sơ này không?
          </p>
        </div>
        <div class="d-flex flex-row justify-space-between ml-8 mr-8">
          <v-btn
            class="btn-not-transform font-weight-medium"
            width="160px"
            outlined
            color="#667085"
            text
            @click="cancle_remove()"
            >Huỷ bỏ</v-btn
          >
          <v-btn
            class="btn-not-transform white--text font-weight-medium"
            width="160px"
            color="#F04438"
            elevation="0"
            @click="remove_submit()"
            >Xoá hồ sơ</v-btn
          >
        </div>
      </v-card>
    </v-dialog>
    <!-- list profile -->
    <div class="d-flex justify-center mt-8">
      <v-progress-circular
        v-if="loading"
        indeterminate
        color="primary"
      ></v-progress-circular>

      <v-card v-else width="858px" elevation="0">
        <v-expansion-panels accordion tile flat>
          <v-expansion-panel
            v-for="profile in profile_list"
            :key="profile.id"
            class="mb-4 rounded-lg"
            style="border: #f2f4f7 1px solid"
          >
            <v-expansion-panel-header>
              <v-card class="d-flex flex-row align-center" elevation="0">
                <v-avatar size="40">
                  <img :src="getImgOfProfile(profile)" />
                </v-avatar>
                <div class="d-flex flex-column ml-3">
                  <p class="ma-0 font-weight-bold text-body-1">
                    {{ profile.lastName }} {{ profile.firstName }}
                  </p>
                  <p
                    class="ma-0 font-weight-normal text-body-2"
                    style="color: #667085"
                  >
                    {{ profile.relationship }}
                  </p>
                </div>
              </v-card>
              <!-- button control -->
              <div class="d-flex justify-end">
                <v-btn
                  fab
                  dark
                  small
                  elevation="0"
                  color="#EEF2F6"
                  width="32px"
                  height="32px"
                  class="d-flex justify-center mr-5"
                  @click="editProfile(profile)"
                >
                  <v-icon small color="#537DA5"> mdi-pencil-outline </v-icon>
                </v-btn>
                <v-btn
                  fab
                  dark
                  small
                  elevation="0"
                  color="#FEF3F2"
                  width="32px"
                  height="32px"
                  class="d-flex justify-center mr-5"
                  @click.stop="notify_remove_profile(profile)"
                >
                  <v-icon small color="#F04438">
                    mdi-account-remove-outline
                  </v-icon>
                </v-btn>
              </div>
              <template v-slot:actions>
                <v-card
                  elevation="0"
                  rounded="circle"
                  color="#FCFCFD"
                  width="32px"
                  height="32px"
                  class="d-flex justify-center"
                >
                  <v-icon small> mdi-chevron-down </v-icon>
                </v-card>
              </template>
            </v-expansion-panel-header>

            <v-expansion-panel-content class="pa-0">
              <!-- user information -->
              <div class="d-flex flex-column">
                <!-- email -->
                <v-divider style="border-color: #f2f4f7 !important"></v-divider>
                <v-card
                  class="d-flex flec-row justify-space-between align-center"
                  elevation="0"
                  height="72px"
                >
                  <p class="ma-0 font-weight-medium" style="color: #667085">
                    Email
                  </p>
                  <p class="ma-0 font-weight-medium">
                    {{ profile.email }}
                  </p>
                </v-card>
                <!-- phone -->
                <v-divider style="border-color: #f2f4f7 !important"></v-divider>
                <v-card
                  class="d-flex flec-row justify-space-between align-center"
                  elevation="0"
                  height="72px"
                >
                  <p class="ma-0 font-weight-medium" style="color: #667085">
                    Số điện thoại
                  </p>
                  <p class="ma-0 font-weight-medium">
                    {{ profile.phone }}
                  </p>
                </v-card>
                <!-- address -->
                <v-divider style="border-color: #f2f4f7 !important"></v-divider>
                <v-card
                  class="d-flex flec-row justify-space-between align-center"
                  elevation="0"
                  height="72px"
                >
                  <p class="ma-0 font-weight-medium" style="color: #667085">
                    Địa chỉ
                  </p>
                  <p class="ma-0 font-weight-medium">
                    {{ getAddress(profile) }}
                  </p>
                </v-card>
                <!-- gender -->
                <v-divider style="border-color: #f2f4f7 !important"></v-divider>
                <v-card
                  class="d-flex flec-row justify-space-between align-center"
                  elevation="0"
                  height="72px"
                >
                  <p class="ma-0 font-weight-medium" style="color: #667085">
                    Giới tính
                  </p>
                  <p class="ma-0 font-weight-medium">
                    {{ getGender(profile.gender) }}
                  </p>
                </v-card>
                <!-- birthday -->
                <v-divider style="border-color: #f2f4f7 !important"></v-divider>
                <v-card
                  class="d-flex flec-row justify-space-between align-center"
                  elevation="0"
                  height="72px"
                >
                  <p class="ma-0 font-weight-medium" style="color: #667085">
                    Ngày sinh
                  </p>
                  <p class="ma-0 font-weight-medium">
                    {{ getDate(profile.dob) }}
                  </p>
                </v-card>
                <!-- identify -->
                <v-divider style="border-color: #f2f4f7 !important"></v-divider>
                <v-card
                  class="d-flex flec-row justify-space-between align-center"
                  elevation="0"
                  height="72px"
                >
                  <p class="ma-0 font-weight-medium" style="color: #667085">
                    Số CMND/CCCD
                  </p>
                  <p class="ma-0 font-weight-medium">
                    {{ profile.identityCard }}
                  </p>
                </v-card>
                <!-- job -->
                <v-divider style="border-color: #f2f4f7 !important"></v-divider>
                <v-card
                  class="d-flex flec-row justify-space-between align-center"
                  elevation="0"
                  height="72px"
                >
                  <p class="ma-0 font-weight-medium" style="color: #667085">
                    Nghề nghiệp
                  </p>
                  <p class="ma-0 font-weight-medium">{{ profile.job }}</p>
                </v-card>
                <!-- country -->
                <v-divider style="border-color: #f2f4f7 !important"></v-divider>
                <v-card
                  class="d-flex flec-row justify-space-between align-center"
                  elevation="0"
                  height="72px"
                >
                  <p class="ma-0 font-weight-medium" style="color: #667085">
                    Quốc gia
                  </p>
                  <p
                    class="ma-0 font-weight-medium"
                    v-if="profile.address != null"
                  >
                    {{ profile.address.country }}
                  </p>
                </v-card>
                <!-- ethnic -->
                <v-divider style="border-color: #f2f4f7 !important"></v-divider>
                <v-card
                  class="d-flex flec-row justify-space-between align-center"
                  elevation="0"
                  height="72px"
                >
                  <p class="ma-0 font-weight-medium" style="color: #667085">
                    Dân tộc
                  </p>
                  <p class="ma-0 font-weight-medium">{{ profile.folk }}</p>
                </v-card>
              </div>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
      profile_remove: null,
      profile_list: [],
      loading: false
    };
  },

  async mounted() {
     if (process.env.VUE_APP_LOGIN_DEV === "TRUE") {
        this.logindev();
     }
     else{
       await this.getProfileList();
     }
  },
  methods: {
    createProfile() {
      this.$emit("OpenCreateProfile");
    },

    editProfile(profile) {
      this.$emit("OpenEditProfile", profile);
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
    getImgOfProfile(profile) {
      if (profile.imageUrl != null) {
        return profile.imageUrl;
      } else {
        return require("@/assets/img/home/appbar/avatar.png");
      }
    },
    getDate(date_string) {
      let date = new Date(date_string);
      return date.toLocaleDateString();
    },

    getGender(string_gender) {
      return string_gender === "MALE" ? "Nam" : "Nữ";
    },

    notify_remove_profile(profile) {
      this.dialog = true;
      this.profile_remove = profile;
    },

    cancle_remove() {
      this.profile_remove = null;
      this.dialog = false;
    },

    async remove_submit() {
      let token = this.$store.getters["auth/access_token"];
      const param = {
        token: token,
        data: this.profile_remove.id
      };
      await this.$store.dispatch("profile/remove_profile", param);
      this.getProfileList();
      this.dialog = false;
    },

    async getProfileList() {
      this.loading = true;
      let token = this.$store.getters["auth/access_token"];
      const param = {
        token: token
      };
      await this.$store.dispatch("profile/profile_list", param);
      this.loading = false;
      this.profile_list = this.$store.getters["profile/profile_list"];
    },

    logindev(){
      this.profile_list = [
        {
          "id": "1",
          "profileNumber": "1025066422997405696",
          "firstName": "Thanh",
          "lastName": "Nguyễn Duy",
          "address": {
              "id": "db89ce01-084a-4ad7-80ef-1e9e23a60723",
              "country": "Việt Nam",
              "province": "Thành phố Hồ Chí Minh",
              "district": "Quận 3",
              "ward": "Phường 1",
              "address": "số 8, đường Kha Vạn Cân"
          },
          "phone": "09672367237",
          "email": "thanhbkcse@gmail.com",
          "dob": "2000-03-01T00:00:00.000+00:00",
          "job": "Sinh viên",
          "identityCard": "077123123000",
          "healthInsurance": "0123712837128",
          "folk": "Kinh",
          "gender": "MALE",
          "guardian": "Nguyễn Văn A",
          "guardianPhone": "0129301293",
          "guardianIdentityCard": "123123123123",
          "relationship": "Anh",
          "relationshipWithPatient": "Anh"
        },
        {
          "id": "2",
          "profileNumber": "1025066422997405696",
          "firstName": "Thọ",
          "lastName": "Hà Ngọc",
          "address": {
              "id": "db89ce01-084a-4ad7-80ef-1e9e23a60723",
              "country": "Việt Nam",
              "province": "Thành phố Hồ Chí Minh",
              "district": "Quận 3",
              "ward": "Phường 1",
              "address": "số 8, đường Kha Vạn Cân"
          },
          "phone": "09672367237",
          "email": "thanhbkcse@gmail.com",
          "dob": "2000-03-01T00:00:00.000+00:00",
          "job": "Sinh viên",
          "identityCard": "077123123000",
          "healthInsurance": "0123712837128",
          "folk": "Kinh",
          "gender": "MALE",
          "guardian": "Nguyễn Văn A",
          "guardianPhone": "0129301293",
          "guardianIdentityCard": "123123123123",
          "relationship": "Anh",
          "relationshipWithPatient": "Anh"
        },
        {
          "id": "3",
          "profileNumber": "1025066422997405696",
          "firstName": "Thảo",
          "lastName": "Đinh Thị",
          "address": {
              "id": "db89ce01-084a-4ad7-80ef-1e9e23a60723",
              "country": "Việt Nam",
              "province": "Thành phố Hồ Chí Minh",
              "district": "Quận 3",
              "ward": "Phường 1",
              "address": "số 8, đường Kha Vạn Cân"
          },
          "phone": "09672367237",
          "email": "thanhbkcse@gmail.com",
          "dob": "2000-03-01T00:00:00.000+00:00",
          "job": "Sinh viên",
          "identityCard": "077123123000",
          "healthInsurance": "0123712837128",
          "folk": "Kinh",
          "gender": "MALE",
          "guardian": "Nguyễn Văn A",
          "guardianPhone": "0129301293",
          "guardianIdentityCard": "123123123123",
          "relationship": "Anh",
          "relationshipWithPatient": "Anh"
        },
      ];

      this.loading = false;
    }
  }
};
</script>

<style scoped>
.btn-not-transform {
  text-transform: none;
}
</style>
