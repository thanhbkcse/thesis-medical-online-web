<template>
  <div>
    <v-card width="922px" elevation="1" class="pb-8">
      <v-expand-transition>
        <profile-list
          v-if="visible_profile_list"
          v-on:OpenCreateProfile="createProfile"
          v-on:OpenEditProfile="editProfile"
        ></profile-list>
      </v-expand-transition>
      <add-profile
        v-if="visible_add_profile"
        v-on:addProfileClose="closeAddProfile"
      ></add-profile>
      <edit-profile
        v-if="visible_edit_profile"
        v-on:editProfileClose="closeEditProfile"
        :profile="edit_profile"
      ></edit-profile>
    </v-card>
  </div>
</template>

<script>
import ProfileList from "./component/profileList.vue";
import AddProfile from "./component/addProfile.vue";
import EditProfile from "./component/editProfile.vue";
export default {
  components: {
    ProfileList,
    AddProfile,
    EditProfile
  },
  data() {
    return {
      visible_profile_list: true,
      visible_add_profile: false,
      visible_edit_profile: false,
      edit_profile: null
    };
  },

  methods: {
    createProfile() {
      this.visible_profile_list = false;
      this.visible_add_profile = true;
    },

    closeAddProfile() {
      this.visible_profile_list = true;
      this.visible_add_profile = false;
    },

    editProfile(profile) {
      this.visible_profile_list = false;
      this.visible_edit_profile = true;
      this.edit_profile = profile;
    },

    closeEditProfile() {
      this.visible_profile_list = true;
      this.visible_edit_profile = false;
    }
  }
};
</script>
