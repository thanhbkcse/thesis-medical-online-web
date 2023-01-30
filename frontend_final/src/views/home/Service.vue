<style lang="sass" scoped>

.v-card.on-hover.theme--dark
  background-color: rgba(#FFF, 0.8)
  >.v-card__text
    color: #000
</style>

<template>
  <div class="py-16" style="background-color: white">
    <v-row justify="center" class="py-6">
      <v-col cols="10">
        <v-row>
          <v-col cols="4">
            <v-hover v-slot="{ hover }">
              <v-card
                width="400px"
                height="600px"
                class="mx-auto"
                :elevation="hover ? 16 : 2"
                :class="{ 'on-hover': hover }"
              >
                <v-img
                  class="white--text align-center"
                  height="100%"
                  src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                  gradient="to top right, rgba(100,115,201,.53), rgba(25,32,72,.9)"
                >
                  <v-card-title
                    class="justify-center display-3 font-weight-bold"
                    >Dịch vụ</v-card-title
                  >
                  <v-card-text>
                    <p class="text-center display-1 font-weight-medium mb-2">
                      Lựa chọn dịch vụ hàng đầu
                    </p>
                    <p class="text-center display-1 font-weight-medium mb-2">
                      Với đội ngũ bác sĩ chuyên nghiệp
                    </p>
                    <p class="text-center display-1 font-weight-medium mb-2">
                      Luôn lắng nghe và phát triển
                    </p>
                  </v-card-text>
                </v-img>
              </v-card>
            </v-hover>
          </v-col>
          <v-col cols="8">
            <v-row>
              <v-col cols="4" v-for="(item, i) in lstCards" :key="i">
                <v-hover v-slot="{ hover }">
                  <v-card
                    :elevation="hover ? 16 : 1"
                    :class="{ 'on-hover': hover }"
                    width="300px"
                    height="300px"
                    class="mx-auto my-auto rounded-xl"
                    :color="item.color"
                  >
                    <v-card-text class="px-6">
                      <v-img
                        height="100px"
                        width="100px"
                        v-bind:src="item.icon"
                      >
                      </v-img>
                      <p
                        class="display-1 py-4 my-0 font-weight-black"
                        style="color: #046792"
                      >
                        {{ item.title }}
                      </p>
                      <p>
                        <v-chip
                          v-for="(subchip, j) in item.subChips"
                          :key="j"
                          class="mr-1 font-weight-bold"
                          color="white"
                          text-color="blue-grey"
                          >{{ subchip }}</v-chip
                        >
                      </p>
                      <v-btn
                        rounded
                        color="#f8ad9d82"
                        dark
                        width="100%"
                        large
                        class="font-weight-bold"
                        plain
                        elevation="1"
                        style="color: #6c584cc4"
                        @click="GoToSerive(item.linkDetails)"
                      >
                        Tìm hiểu thêm
                      </v-btn>
                    </v-card-text>
                  </v-card>
                </v-hover>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      lstCards: [
        {
          icon: `${require(`@/assets/img/service/timetable.png`)}`,
          title: "Đặt lịch khám theo ngày",
          subChips: ["Tiện lợi", "Nhanh chóng"],
          linkDetails: "/home/user/create-appoinment",
          color: "#fff4ee"
        },
        {
          icon: `${require(`@/assets/img/service/doctor.png`)}`,
          title: "Đặt lịch khám theo bác sĩ",
          subChips: ["Phù hợp", "Tin tưởng"],
          linkDetails: "",
          color: "#fff4ee"
        },
        {
          icon: `${require(`@/assets/img/service/health-check.png`)}`,
          title: "Lịch sử đặt khám",
          subChips: ["Dễ quản lý", "Tiện lợi"],
          linkDetails: "",
          color: "#fff4ee"
        },
        {
          icon: `${require(`@/assets/img/service/online-class.png`)}`,
          title: "Khám bệnh trực tuyến",
          subChips: ["Trực tiếp", "Hiệu quả"],
          linkDetails: "",
          color: "#fff4ee"
        },
        {
          icon: `${require(`@/assets/img/service/event.png`)}`,
          title: "Theo dõi bệnh án",
          subChips: ["Tiến trình", "Lịch sử"],
          linkDetails: "",
          color: "#fff4ee"
        },
        {
          icon: `${require(`@/assets/img/service/book-stack.png`)}`,
          title: "Tìm hiểu về bệnh",
          subChips: ["Bài viết", "Chuẩn đoán"],
          linkDetails: "",
          color: "#fff4ee"
        }
      ]
    };
  },
  methods: {
    GoToSerive(link_name) {
      this.$router.push({ name: "Đặt lịch khám theo ngày" });
    }
  }
};
</script>
