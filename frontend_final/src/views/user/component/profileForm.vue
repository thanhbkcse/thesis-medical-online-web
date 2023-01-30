<template>
  <div>
    <!-- avarta -->
    <v-card width="100%" class="d-flex flex-row" height="236px" flat>
      <v-card width="50%" flat>
        <p class="font-weight-medium">Hình đại diện</p>
        <v-card width="278px" flat>
          <p class="text-body-2 font-weight-medium" style="color: #667085">
            Chọn hình đại diện mới và thả vào khung đăng ảnh để thay đổi ảnh đại
            diện
          </p>
        </v-card>
        <div><p class="font-weight-medium">Mã hồ sơ</p></div>
        <v-card width="278px" flat>
          <p class="text-body-2 font-weight-medium" style="color: #667085">
            {{ profile.profileNumber }}
          </p>
        </v-card>
      </v-card>
      <v-card
        width="50%"
        flat
        class="d-flex flex-row align-center"
        max-width="400px"
        max-height="172px"
      >
        <v-avatar size="64">
          <v-img contain :src="avt_img" ref="avt" />
        </v-avatar>
        <v-card
          class="d-flex align-center justify-center ml-8"
          width="100%"
          color="#FCFCFD"
          height="100%"
        >
          <div class="d-flex flex-column">
            <div class="d-flex justify-center">
              <v-card
                class="d-flex align-center justify-center pa-2"
                rounded="circle"
                color="#EEF2F6"
              >
                <v-icon color="#537DA5" large @click="$refs.file.click()"
                  >mdi-cloud-upload-outline
                </v-icon>
                <input
                  :disabled="disableEdit"
                  type="file"
                  ref="file"
                  accept="image/*"
                  style="display: none"
                  @change="previewFiles"
                />
              </v-card>
            </div>
            <p class="d-flex font-weight-medium">
              Click to upload or drag and drop
            </p>
            <p class="d-flex text-body-2" style="color: #667085">
              SVG, PNG, JPG or GIF (max 800x400px)
            </p>
          </div>
        </v-card>
      </v-card>
    </v-card>
    <!-- form -->
    <div class="d-flex flex-column">
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <!-- header -->
      <v-card height="112px" elevation="0">
        <div class="mt-6">
          <p class="font-weight-bold" style="font-size: 20px">
            Thông tin bệnh nhân
          </p>
          <p style="color: #667085">
            Vui lòng cập nhật thông tin của bệnh nhân.
          </p>
        </div>
      </v-card>
      <!-- name -->
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Họ và tên lót <span style="color: red">*</span>
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.lastName"
                placeholder="VD: Nguyễn Duy"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Tên <span style="color: red">*</span>
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.firstName"
                placeholder="VD: Thanh"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
        </div>
      </v-card>

      <!-- phone, email -->
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Số điện thoại <span style="color: red">*</span>
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.phone"
                placeholder="Số điện thoại"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">Email</p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                placeholder="VD thanhbkcse@gmail.com"
                :disabled="disableEdit"
                v-model="profile.email"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
        </div>
      </v-card>

      <!-- birthday -->
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-column">
          <p class="mb-2 font-weight-medium text-body-2">
            Ngày sinh <span style="color: red">*</span>
          </p>
          <div class="d-flex flex-row justify-space-between">
            <v-card
              width="270px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-select
                :disabled="disableEdit"
                v-model="profile.dob.date"
                :items="days.date"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
              ></v-select>
            </v-card>
            <v-card
              width="270px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-select
                :disabled="disableEdit"
                v-model="profile.dob.month"
                :items="days.month"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
              ></v-select>
            </v-card>
            <v-card
              width="270px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-select
                :disabled="disableEdit"
                v-model="profile.dob.year"
                :items="days.year"
                solo
                flat
                dense
                menu-props="auto"
                append-icon="mdi-chevron-down"
              ></v-select>
            </v-card>
          </div>
        </div>
      </v-card>

      <!-- gender -->
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Giới tính <span style="color: red">*</span>
            </p>
            <v-radio-group :disabled="disableEdit" row v-model="profile.gender">
              <v-radio
                label="Nam"
                value="MALE"
                color="#537DA5"
                class="mr-6"
              ></v-radio>
              <v-radio label="Nữ" value="FEMALE" color="#537DA5"></v-radio>
            </v-radio-group>
          </div>
        </div>
      </v-card>

      <!-- national, identify -->
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">Quốc gia</p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                v-model="profile.country"
                placeholder="Việt Nam"
                solo
                flat
                dense
                class="text-body-1"
                disabled
              ></v-text-field>
            </v-card>
          </div>
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Số CMND/Passport <span style="color: red">*</span>
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.identityCard"
                placeholder="07720xxxxx"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
        </div>
      </v-card>

      <!-- ethnic, job -->
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Dân tộc <span style="color: red">*</span>
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.folk"
                placeholder="VD: Kinh"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">Nghề nghiệp</p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.job"
                placeholder="VD: Sinh viên"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
        </div>
      </v-card>

      <!-- city, district -->
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Tỉnh/Thành phố <span style="color: red">*</span>
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-select
                :disabled="disableEdit"
                :items="province_list"
                v-model="profile.province"
                item-text="text"
                item-value="text"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
              ></v-select>
            </v-card>
          </div>
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Quận/Huyện <span style="color: red">*</span>
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-select
                :disabled="disableEdit"
                :items="town_list"
                v-model="profile.district"
                label=""
                item-text="text"
                item-value="text"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
              ></v-select>
            </v-card>
          </div>
        </div>
      </v-card>

      <!-- sub-district, street, number -->
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Xã/Phường <span style="color: red">*</span>
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-select
                :disabled="disableEdit"
                :items="commune_list"
                v-model="profile.ward"
                item-text="text"
                item-value="text"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
              ></v-select>
            </v-card>
          </div>
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Số nhà và tên đường
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.address"
                placeholder=""
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
        </div>
      </v-card>

      <!-- sub-district, street, number -->
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Quan hệ với tài khoản <span style="color: red">*</span>
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-select
                :items="relationship"
                v-model="profile.relationship"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
              ></v-select>
            </v-card>
          </div>
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Mã số Bảo hiểm y tế
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.healthInsurance"
                placeholder=""
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
        </div>
      </v-card>

      <!-- a relative -->
      <v-card height="112px" elevation="0">
        <div class="mt-6">
          <p class="font-weight-bold" style="font-size: 20px">
            Thông tin nhân thân
          </p>
          <p style="color: #667085">
            Vui lòng cập nhật thông tin của thân nhân.
          </p>
        </div>
      </v-card>
      <!-- name, relationship -->
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Họ và tên
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.guardian"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">
              Quan hệ với bệnh nhân
            </p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-select
                :disabled="disableEdit"
                v-model="profile.relationshipWithPatient"
                :items="relationship"
                solo
                flat
                dense
                append-icon="mdi-chevron-down"
                menu-props="auto"
              ></v-select>
            </v-card>
          </div>
        </div>
      </v-card>

      <!-- phone, email -->
      <v-divider
        style="border-color: rgba(16, 24, 40, 0.03) !important"
      ></v-divider>
      <v-card height="140px" elevation="0">
        <div class="mt-6 d-flex flex-row justify-space-between">
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">Số điện thoại</p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.guardianPhone"
                placeholder="Số điện thoại"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
          <div class="d-flex flex-column">
            <p class="mb-2 font-weight-medium text-body-2">CMND</p>
            <v-card
              width="417px"
              height="48px"
              class="d-flex mb-5"
              elevation="0"
              style="padding-top: 1px"
              outlined
            >
              <v-text-field
                :disabled="disableEdit"
                v-model="profile.guardianIdentityCard"
                placeholder="VD: 07720xxxxxx"
                solo
                flat
                dense
                class="text-body-1"
              ></v-text-field>
            </v-card>
          </div>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script>
const url = process.env.VUE_APP_ROOT_API;

export default {
  props: {
    type: {
      type: Number,
      default: 0 // 0 is create, 1 is edit
    },

    edit_profile: {
      type: Object,
      default: null
    }
  },

  data() {
    return {
      avt_img: require("@/assets/img/user/profile/avatar1.svg"),
      days: {
        date: [],
        month: [],
        year: []
      },
      relationship: [
        "Ba",
        "Mẹ",
        "Ông",
        "Bà",
        "Anh",
        "Chị",
        "Em",
        "Con",
        "Vợ",
        "Chồng",
        "Chủ tài khoản",
        "Khác"
      ],

      province_list: [],

      town_list: [],
      commune_list: [],
      profile: {
        id: "",
        profileNumber: "",
        firstName: "",
        lastName: "",
        country: "Việt Nam",
        province: "",
        district: "",
        ward: "",
        addressId: "",
        address: "",
        phone: "",
        email: "",
        dob: {
          date: "01",
          month: "01",
          year: "2000"
        },
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
        isContactProfile: false
      }
    };
  },

  async created() {
    //create day
    this.days.month = Array.from({ length: 12 }, (_, i) =>
      i + 1 > 9 ? String(i + 1) : "0" + String(i + 1)
    );
    this.days.date = Array.from({ length: 31 }, (_, i) =>
      i + 1 > 9 ? String(i + 1) : "0" + String(i + 1)
    );
    let year = Array.from({ length: 100 }, (_, i) => String(i + 1923));
    this.days.year = year.reverse();
    this.setDataForm();
    await this.getProvines();
    await this.getDistricts();
    await this.getWards();
  },
  watch: {
    "profile.province": {
      handler() {
        this.getDistricts();
      }
    },
    "profile.district": {
      handler() {
        this.getWards();
      }
    }
  },
  mounted() {
    // Emits on mount
    this.emitInterface();
  },
  computed: {
    disableEdit() {
      return this.profile.isContactProfile;
    }
  },
  methods: {
    async getProvines() {
      const res = await this.axios.get(
        `https://vn-public-apis.fpo.vn/provinces/getAll?limit=-1`
      );
      let provinces = res.data?.data?.data;
      this.province_list = [];
      for (let p of provinces) {
        this.province_list.push({ text: p.name, value: p.code });
      }
    },
    async getDistricts() {
      let province = (this.province_list || []).find(
        p => p.text == this.profile.province
      );
      const params = {
        limit: -1,
        provinceCode: province?.value
      };
      const res = await this.axios.get(
        `https://vn-public-apis.fpo.vn/districts/getByProvince`,
        { params: params }
      );
      let districts = res.data.data.data || [];
      this.town_list = [];
      for (let p of districts) {
        this.town_list.push({ text: p.name, value: p.code });
      }
    },
    async getWards() {
      let district = (this.town_list || []).find(
        p => p.text == this.profile.district
      );
      const params = {
        limit: -1,
        districtCode: district?.value
      };
      const res = await this.axios.get(
        `https://vn-public-apis.fpo.vn/wards/getByDistrict`,
        { params: params }
      );
      let wards = res.data.data.data || [];
      this.commune_list = [];
      for (let p of wards) {
        this.commune_list.push({ text: p.name, value: p.code });
      }
    },
    previewFiles(event) {
      let file_1 = event.target.files[0];
      let reader = new FileReader();
      reader.addEventListener(
        "load",
        () => {
          // convert image file to base64 string
          this.avt_img = reader.result;
        },
        false
      );
      if (file_1) {
        reader.readAsDataURL(file_1);
      } else {
        this.avt_img = require("@/assets/img/user/profile/avatar1.svg");
      }
    },

    setDataForm() {
      if (this.type != 0 && this.edit_profile != null) {
        this.profile.id = this.edit_profile.id;
        this.profile.profileNumber = this.edit_profile.profileNumber;
        this.profile.lastName = this.edit_profile.lastName;
        this.profile.firstName = this.edit_profile.firstName;

        if (this.edit_profile.address != null) {
          this.profile.addressId = this.edit_profile.address.id;
          this.profile.country = this.edit_profile.address.country;
          this.profile.province = this.edit_profile.address.province;
          this.profile.district = this.edit_profile.address.district;
          this.profile.ward = this.edit_profile.address.ward;
          this.profile.address = this.edit_profile.address.address;
        }

        this.profile.phone = this.edit_profile.phone;
        this.profile.email = this.edit_profile.email;
        this.profile.job = this.edit_profile.job;
        this.profile.identityCard = this.edit_profile.identityCard;
        this.profile.healthInsurance = this.edit_profile.healthInsurance;
        this.profile.folk = this.edit_profile.folk;
        this.profile.gender = this.edit_profile.gender;
        this.profile.guardian = this.edit_profile.guardian;
        this.profile.guardianIdentityCard = this.edit_profile.guardianIdentityCard;
        this.profile.guardianPhone = this.edit_profile.guardianPhone;
        this.profile.relationship = this.edit_profile.relationship;
        this.profile.relationshipWithPatient = this.edit_profile.relationshipWithPatient;
        this.profile.isContactProfile = this.editProfile.isContactProfile;
        let date = new Date(this.edit_profile.dob);
        this.profile.dob.year = String(date.getFullYear());
        this.profile.dob.month =
          date.getMonth() > 9
            ? String(date.getMonth())
            : "0" + String(date.getMonth());
        this.profile.dob.date =
          date.getDate() > 9
            ? String(date.getDate())
            : "0" + String(date.getDate());
      }
    },
    formatPhone(phoneInput) {
      let phone = this._.cloneDeep(phoneInput);
      if (phone.length > 1) {
        phone = phone.split("");
        phone[0] = "+84";
        phone = phone.join("");
      }
      return phone;
    },
    async addNewProfile() {
      let phone = this.formatPhone(this.profile.phone);
      let data = {
        firstName: "",
        lastName: "",
        address: {
          country: "",
          province: "",
          district: "",
          ward: "",
          address: ""
        },
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
        relationshipWithPatient: ""
      };
      //process data
      data.lastName = this.profile.lastName;
      data.firstName = this.profile.firstName;
      data.address.country = this.profile.country;
      data.address.province = this.profile.province;
      data.address.district = this.profile.district;
      data.address.ward = this.profile.ward;
      data.address.address = this.profile.address;
      data.dob =
        this.profile.dob.year +
        "-" +
        this.profile.dob.month +
        "-" +
        this.profile.dob.date;
      data.phone = phone;
      data.email = this.profile.email;
      data.job = this.profile.job;
      data.identityCard = this.profile.identityCard;
      data.healthInsurance = this.profile.healthInsurance;
      data.folk = this.profile.folk;
      data.gender = this.profile.gender;
      data.guardian = this.profile.guardian;
      data.guardianPhone = this.profile.guardianPhone;
      data.guardianIdentityCard = this.profile.guardianIdentityCard;
      data.relationship = this.profile.relationship;
      data.relationshipWithPatient = this.profile.relationship;

      let token = this.$store.getters["auth/access_token"];
      const param = {
        token: token,
        data: data
      };
      await this.$store.dispatch("profile/add_new_profile", param);
    },

    async editProfile() {
      let data = {
        id: "",
        firstName: "",
        lastName: "",
        address: {
          id: "",
          country: "",
          province: "",
          district: "",
          ward: "",
          address: ""
        },
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
        relationshipWithPatient: ""
      };
      //process data
      data.id = this.profile.id;
      data.lastName = this.profile.lastName;
      data.firstName = this.profile.firstName;
      data.address.id = this.profile.addressId;
      data.address.country = this.profile.country;
      data.address.province = this.profile.province;
      data.address.district = this.profile.district;
      data.address.ward = this.profile.ward;
      data.address.address = this.profile.address;
      data.dob =
        this.profile.dob.year +
        "-" +
        this.profile.dob.month +
        "-" +
        this.profile.dob.date;
      data.phone = this.profile.phone;
      data.email = this.profile.email;
      data.job = this.profile.job;
      data.identityCard = this.profile.identityCard;
      data.healthInsurance = this.profile.healthInsurance;
      data.folk = this.profile.folk;
      data.gender = this.profile.gender;
      data.guardian = this.profile.guardian;
      data.guardianPhone = this.profile.guardianPhone;
      data.guardianIdentityCard = this.profile.guardianIdentityCard;
      data.relationship = this.profile.relationship;
      data.relationshipWithPatient = this.profile.relationship;

      let token = this.$store.getters["auth/access_token"];
      const param = {
        token: token,
        data: data
      };
      await this.$store.dispatch("profile/edit_profile", param);
    },

    /**
     * Emitting an interface with callable methods from outside
     */
    emitInterface() {
      this.$emit("interface", {
        addNewProfile: () => this.addNewProfile(),
        editProfile: () => this.editProfile()
      });
    }
  }
};
</script>
