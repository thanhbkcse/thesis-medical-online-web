<template>
  <v-container fluid class="pa-0">
    <div>
      <v-card
        width="100%"
        min-height="1000px"
        color="#fcfcfd"
        class="d-flex justify-center py-12"
      >
        <div class="mr-12">
          <v-card class="pb-4" elevation="1">
            <v-list nav>
              <v-list-item-group
                v-model="selectedItem"
                active-class="bg-active"
                class="bgWhite"
              >
                <v-list-item
                  v-for="(item, i) in items"
                  :key="i"
                  class="mt-4 mx-5"
                  :to="item.link"
                >
                  <v-list-item-icon class="mr-3">
                    <v-icon v-text="item.icon"></v-icon>
                  </v-list-item-icon>
                  <v-list-item-content class="pr-6 ma-0">
                    <v-list-item-title
                      v-text="item.text"
                      class="font-weight-medium text-body-1"
                    ></v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-card>
        </div>
        <v-scroll-y-transition appear>
          <router-view :key="$route.path"
        /></v-scroll-y-transition>
      </v-card>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "DefaultView",
  data() {
    return {
      selectedItem: 1,
      items: [
        {
          text: "Hồ sơ bệnh nhân",
          icon: "far fa-address-book",
          link: "/home/user/profile"
        },
        {
          text: "Phiếu khám bệnh",
          icon: "fa fa-file-medical",
          link: "/home/user/appointment-history"
        },
        {
          text: "Hồ sơ sức khỏe",
          icon: "mdi-file-document-multiple-outline",
          link: "/home/user/health-records"
        },
        {
          text: "Lịch sử giao dịch",
          icon: "mdi-swap-horizontal",
          link: "/home/user/transaction-history"
        },
        {
          text: "Câu hỏi của bạn",
          icon: "mdi-help-circle-outline",
          link: "/home/user/your-question"
        },
        {
          text: "Thông báo",
          icon: "mdi-bell-outline",
          link: "/home/user/notify"
        }
      ]
    };
  }
};
</script>
<style scoped>
.bg-active {
  color: #537da5 !important;
}
</style>
