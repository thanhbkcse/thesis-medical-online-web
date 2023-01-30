<template>
    <div class="content">
        <v-carousel height="200" class="mb-8" hide-delimiters show-arrows-on-hover>
          <template v-for="(item, index) in card_list_2">
            <v-carousel-item 
              v-if="(index + 1) % columns === 1 || columns === 1"
              :key="index"
            >
              <v-row justify="center">
                <template v-for="(n, i) in columns">
                  <template v-if="+index + i < card_list_2.length">
                    <v-col :key="i" justify="center" align="center" cols = 12/columns>
                      <v-card
                        class="mx-2"
                        :key="index"
                        width="200px"
                        height="134px"
                        hover="true"
                        style="background-color: #e6f0f5"
                        @click="getlink(+index + i)"
                      >
                        <v-row justify="center" class="mt-3">
                          <v-avatar height="48px" width="48px" color="white" class="mt-3">
                            <img :src="card_list_2[+index + i].image" alt="" />
                          </v-avatar>
                        </v-row>
                        <br />
                        <v-row justify="center" class="mt-2">
                          <strong
                            class="font-weight-bold ml-2"
                            style="color: #046792; font-size: 16px"
                            >{{ card_list_2[+index + i].label }}</strong
                          >
                        </v-row>
                      </v-card>
                    </v-col>
                  </template>
                </template>
              </v-row>
            </v-carousel-item>
          </template>
        </v-carousel>
    </div>
</template>

<script>
export default {
    data: () => ({
    card_list_2: [
      {
        image: require("@/assets/img/home/home_icon1.png"),
        label: "Chẩn đoán hình ảnh",
        name: "Chẩn đoán hình ảnh",
      },
      {
        image: require("@/assets/img/home/home_icon2.png"),
        label: "Cơ - Xương - Khớp",
        name: "Cơ - Xương - Khớp",
      },
      {
        image: require("@/assets/img/home/home_icon3.png"),
        label: "Nhi khoa",
        name: "Nhi khoa",
      },
      {
        image: require("@/assets/img/home/home_icon4.png"),
        label: "Sản - Phụ Khoa",
         name: "Sản - Phụ khoa",
      },
      {
        image: require("@/assets/img/home/home_icon5.png"),
        label: "Tai - Mũi - Họng",
        name: "Tai - Mũi - Họng",
      },
      {
        image: require("@/assets/img/home/home_icon6.png"),
        label: "Tim mạch",
        name: "Tim mạch",
      },
      {
        image: require("@/assets/img/home/home_icon7.png"),
        label: "Hô hấp",
        name: "Hô hấp và miễn dịch dị ứng lâm sàng",
      },
      {
        image: require("@/assets/img/home/home_icon8.png"),
        label: "Nội soi",
        name: "Nội soi",
      },
      {
        image: require("@/assets/img/home/home_icon9.png"),
        label: "Tiêu hóa gan mật",
        name: "Tiêu hóa gan mật",
      },
      {
        image: require("@/assets/img/home/home_icon10.png"),
        label: "Xét nghiệm",
        name: "Xét nghiệm",
      },
      {
        image: require("@/assets/img/home/home_icon11.png"),
        label: "Khoa ngoại",
        name: "Khoa ngoại",
      },
      {
        image: require("@/assets/img/home/home_icon12.png"),
        label: "Khoa tâm thần",
        name: "Tâm thần kinh",
      },
    ],
    }),
    methods:{
        getlink(index){
            this.$router.push({ name: this.card_list_2[index].name });
        },
        getpage(name){
            this.$router.push({ name: name });
        },
    },
    computed: {
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
  },
}
</script>