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
            Chỉnh sửa hồ sơ
          </p>
          <p style="color: #667085" class="text-body-1">
            Vui lòng cập nhật những thông tin ở phía dưới.
          </p>
        </div>
      </div>
      <div class="d-flex flex-row align-center mr-8">
        <v-btn
          class="mr-6 btn-not-transform text-body-1 font-weight-medium"
          elevation="0"
          outlined
          color="#667085"
          text
          @click="cancleEditProfile"
          >Hủy bỏ</v-btn
        >
        <v-btn
          color="#537DA5"
          class="white--text btn-not-transform text-body-1"
          elevation="0"
          @click="editProfile"
          >Lưu chỉnh sửa</v-btn
        >
      </div>
    </v-card>
    <v-divider
      style="border-color: rgba(16, 24, 40, 0.03) !important"
    ></v-divider>
    <profile-form
      class="ma-6"
      v-on:interface="getChildInterface"
      :type="1"
      :edit_profile="profile"
    ></profile-form>
  </div>
</template>

<script>
import ProfileForm from "./profileForm.vue";
export default {
  props: {
    profile: Object
  },
  components: {
    ProfileForm
  },
  methods: {
    cancleEditProfile() {
      this.$emit("editProfileClose");
    },
    getChildInterface(childInterface) {
      this.$options.childInterface = childInterface;
    },
    async editProfile() {
      await this.$options.childInterface.editProfile();
      this.$emit("editProfileClose");
    }
  }
};
</script>
<style scoped>
.btn-not-transform {
  text-transform: none;
}
</style>
