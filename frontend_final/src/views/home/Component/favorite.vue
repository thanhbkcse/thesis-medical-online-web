<template>
  <v-row>
    <v-col cols="12"
      ><div v-if="isLogin" class="d-flex">
        <v-avatar size="40">
          <img src="@/assets/img/home/appbar/avatar.png" />
        </v-avatar>
        <div class=" ml-6" style="flex: auto">
          <v-card outlined class="mb-3">
            <v-textarea
              v-model="favoriteModel.comment"
              spellcheck="false"
              solo
              height="148"
              flat
              hide-details
              placeholder="Vui lòng nhập đánh giá của bạn..."
            >
            </v-textarea>
          </v-card>

          <div class="mb-3">
            <v-rating
              v-model="favoriteModel.favorite"
              icon-label="custom icon label text {0} of {1}"
              color="yellow darken-3"
              background-color="grey darken-1"
            ></v-rating>
          </div>
          <v-btn
            width="110"
            height="44"
            class="white--text btn text-body-2"
            elevation="0"
            color="#537DA5"
            @click="favorite()"
          >
            Gửi đánh giá
          </v-btn>
        </div>
      </div>
    </v-col>
    <v-col cols="12">
      <div class="d-flex" v-for="(data, i) in listFavorite" :key="i">
        <v-avatar size="40">
          <img src="@/assets/img/home/appbar/avatar.png" />
        </v-avatar>
        <div class="ml-6" style="flex: auto">
          <p class="mb-2 font-weight-bold" style="font-size: 20px">
            {{ getName(data.profile) }}
          </p>
          <p style="color: grey; font-size: 85% !important">
            {{ getDate(data.date) }}
          </p>
          <div style="margin-left: -10px; margin-top: -10px;">
            <v-rating
              v-model="data.favorite"
              icon-label="custom icon label text {0} of {1}"
              color="yellow darken-3"
              background-color="grey darken-1"
              readonly
            ></v-rating>
          </div>
          <p class="text-body-1 mb-6">
            {{ data.comment }}
          </p>
        </div>
      </div>
    </v-col>
    <v-col cols="12">
      <template>
        <div class="text-center">
          <v-pagination
            v-if="totalPages != 0"
            v-model="page"
            :length="totalPages"
            :total-visible="7"
          ></v-pagination>
        </div>
      </template>
    </v-col>
  </v-row>
</template>

<script>
export default {
  data() {
    return {
      favoriteModel: {
        favorite: 0,
        comment: "",
        username: this.username,
        objectId: this.object?.id,
        objectType: this.objectType
      },
      listFavorite: [],
      params: {
        objectId: null,
        page: 0,
        size: 5
      },
      page: 1,
      totalPages: 0
    };
  },
  async created() {
    await this.getListFavorite();
  },
  watch: {
    page: {
      handler() {
        this.getListFavorite();
      }
    }
  },
  computed: {
    isLogin() {
      return this.$store.getters["auth/isLogin"];
    }
  },
  methods: {
    async getListFavorite() {
      const url = process.env.VUE_APP_ROOT_API;
      this.params.objectId = this.object?.id;
      this.params.page = this.page - 1;
      const res = await this.axios.get(`${url}/api/favorites`, {
        params: this.params
      });
      this.listFavorite = res.data.results;
      this.totalPages = res.data?.meta?.totalPages;
      this.$emit("setNumFavorite", res.data?.meta?.totalElements);
    },
    async favorite() {
      const url = process.env.VUE_APP_ROOT_API;
      let token = this.$store.getters["auth/access_token"];
      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const favoriteModel = this._.cloneDeep(this.favoriteModel);
      await this.axios
        .post(`${url}/api/favorites`, favoriteModel)
        .then(response => {
          this.favoriteModel.favorite = 0;
          this.favoriteModel.comment = "";
          this.$store.dispatch("snackbar/set_snackbar", {
            text: "Đánh giá thành công.",
            type: "success"
          });
          this.$emit("reloadFavorite");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    getDate(date_string) {
      let date = new Date(date_string);
      let day = date.getDate();
      let month = date.getMonth();
      let year = date.getFullYear();
      return `${day} tháng ${month}, ${year}`;
    },
    getName(profile) {
      if (!profile.lastName && !profile.firstName) return "Tài khoản ẩn danh";
      return `${profile.lastName} ${profile.firstName}`;
    }
  },
  props: ["object", "username", "objectType"]
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
</style>
