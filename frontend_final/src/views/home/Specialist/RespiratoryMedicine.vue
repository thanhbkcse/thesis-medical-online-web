<template>
  <div class="content">
    <v-footer color="#DCF5FF" height="auto" :padless="padless" class="mb-10">
      <v-col cols="3">icon</v-col>
      <v-col cols="5">
        <v-row justify="center" no-gutters>
          <v-btn
            v-for="link in links"
            :key="link.label"
            color="#046792"
            text
            rounded
            class="my-2"
            @click="getpage(link.name)"
          >
            {{ link.label }}
          </v-btn>
        </v-row></v-col
      >
      <v-col cols="4">
        <v-row justify="end" no-gutters>
          <v-btn
            color="#046792"
            rounded
            class="my-2 white--text"
            @click="login"
          >
            Đăng nhập
          </v-btn>
          <v-btn
            color="#046792"
            rounded
            class="my-2 white--text"
            @click="signup"
          >
            Đăng ký
          </v-btn>
        </v-row></v-col
      >
    </v-footer>
    <div class="contentpage">
      <v-breadcrumbs :items="items"></v-breadcrumbs>
      <br>

      <p class="text-center display-1 font-weight-bold blue--text">
HÔ HẤP VÀ DỊ ỨNG MIỄN DỊCH LÂM SÀNG
      </p>

      <br>

      <p class="text-left title font-weight-bold red--text mb-6">
        I. Giới thiệu chung
      </p>

      <p class="text-justify body-1">
    Đơn vị Hô Hấp và Dị ứng miễn dịch lâm sàng của Phòng khám  đứng đầu là  PGS.TS.BS Lê Thị Tuyết Lan cùng nhiều chuyên gia đầu nghành trong lĩnh vực hô hấp, đã được đào tạo trong và ngoài nước.
      </p>

       <p class="text-justify body-1">Bệnh lý  về Hô hấp: hen, bệnh phổi tắc nghẽn mạn tính (COPD), ho kéo dài, viêm hô hấp, khó thở không rõ nguyên nhân, ngáy và ngưng thở lúc ngủ, tầm soát ung thư phổi, viêm phổi, cai thuốc lá…</p>
       

       
     

      <p class="text-left title font-weight-bold red--text mb-6">II. CÁC PHƯƠNG PHÁP THĂM DÒ CHỨC NĂNG HÔ HẤP ĐƯỢC TRIỂN KHAI TẠI PHÒNG KHÁM:</p>

      <p class="text-justify body-1">
     ĐO HÔ HẤP KÝ: Chẩn đoán và theo dõi bệnh hen, bệnh phổi tắc nghẽn mạn tính (COPD), xác định nguyên nhân của các triệu chứng hô hấp như ho, khó thở, khò khè… Hô hấp ký với nghiệm pháp dùng thuốc dãn phế quản để xác định hồi phục phế quản.
      </p>

       <p class="text-justify body-1">
    Đo thể tích phổi và chẩn đoán các trường hợp tắc nghẽn đường dẫn khí bằng Phế thân ký. Phối hợp hô hấp ký và phế thân ký, đo khuếch tán qua màng phế nang mao mạch với CO (DLCO) giúp định hướng rõ hơn các nhóm nguyên nhân gây triệu chứng hô hấp.
      </p>

       <p class="text-justify body-1">
    DAO DỘNG XUNG KÝ (IOS): dao động xung ký là 1 phương pháp thăm dò chức năng hô hấp dễ thực hiện nên được ứng dụng rộng rãi cho trẻ em từ 2 tuổi trở lên và ở người lớn có chống chỉ định với hô hấp ký. Người thực hiện dao động xung ký chỉ cần thở bình thường để có kết quả.
      </p>

       <p class="text-justify body-1">
  Được khuyến cáo để chẩn đoán viêm đường dẫn tăng bạch cầu ái toan và có thể giúp xác định các đối tượng đáp ứng với điều trị corticosteroid khi có triệu chứng hô hấp mạn tính do viêm đường dẫn khí. FeNO còn có thể là một bằng chứng giúp chẩn đoán hen và FeNO còn có thể là một chỉ dấu của tăng đáp ứng đường dẫn khí.
      </p>
      

      <p class="text-justify body-1">
    Giúp phát hiện các bệnh tim mạch, hô hấp tiềm ẩn không được phát hiện khi thăm dò lúc nghỉ, đưa ra thông tin để tiên lượng và phục hồi chức năng đối với các đối tượng có bệnh lý, hay phân loại mức độ thể chất và xây dựng bài tập luyện với người khỏe mạnh và vận động viên. CPET còn có vai trò to lớn trong đánh giá tiền phẫu, đặc biệt các trường hợp mổ cắt phổi. Đây là một trong những điểm mạnh của Phòng khám, khi là đơn vị duy nhất triển khai nghiệm pháp trên lâm sàng cho bệnh nhân.
      </p>

       
      
      


<br>

      <p class="text-center display-1 font-weight-bold blue--text">
      Các chuyên khoa khác
    </p>

    <br>
    <br>

  

    <v-carousel height="200">
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
                    <br>
                    <v-row justify="center" class="mt-2">
                      <strong
                        class="font-weight-regular ml-2"
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

    <v-footer class="mt-15">
      <v-row justify="center" class="mt-9 mb-9">
        <v-col cols="2 mr-5">
          <v-row justify="center "
            ><p class="body-1 font-weight-bold blue--text">Về trang web</p>
          </v-row>
          <v-row justify="center"
            ><p class="text-justify body-1">
              Được xây dựng bằng VueJs và tạo ra từ chính công sức của tập thể.
            </p></v-row
          >
        </v-col>

        <v-col cols="2">
          <v-row justify="center"
            ><p class="body-1 font-weight-bold blue--text">Ngôn ngữ sử dụng</p>
          </v-row>
          <v-row justify="center"><p class="text-justify body-1">Vue</p></v-row>
          <v-row justify="center"
            ><p class="text-justify body-1">HTML</p></v-row
          >
          <v-row justify="center"><p class="text-justify body-1">CSS</p></v-row>
          <v-row justify="center"><p class="text-justify body-1">Js</p></v-row>
        </v-col>
        <v-col cols="2">
          <v-row justify="center"
            ><p class="body-1 font-weight-bold blue--text">Đối tượng sử dụng</p>
          </v-row>
          <v-row justify="center"
            ><p class="text-justify body-1">Bệnh nhân</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">Quản lí</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">Bác sĩ</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">Nhân viên</p></v-row
          >
        </v-col>
        <v-col cols="2">
          <v-row justify="center "
            ><p class="body-1 font-weight-bold blue--text">Liên hệ</p>
          </v-row>
          <v-row justify="center"
            ><p class="text-justify body-1">Email: t3@gmail.com</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">SĐT: 0123456798</p></v-row
          >
          <v-row justify="center"
            ><p class="text-justify body-1">
              Địa chỉ: 22 abc Dĩ An Bình Dương
            </p></v-row
          >
        </v-col>
      </v-row>
    </v-footer>

    
  </div>
</template>

<script>
export default {
  data: () => ({
   links:[
      {
        label: "Trang chủ",
        name: "home"
      },
      {
        label: "Giới thiệu",
        name: "Giới thiệu",
      },
        {
        label: "Quy trình",
        name: "Quy trình",
      },
      {
        label: "Dịch vụ",
        name: "Dịch vụ",
      },
        {
        label: "Liên hệ",
        name: "Liên hệ",
      },
],
    items: [
      {
        text: "Home",
        disabled: false,
        href: "../",
      },
      {
        text: "Chuyên khoa",
        disabled: false,
        href: "./",
      },
      {
        text: "Hô hấp",
        disabled: true,
      },
    ],

 
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
  methods: {
    login() {
      this.$router.push({ name: "Đăng nhập" });
    },
    signup() {
      this.$router.push({ name: "Đăng ký" });
    },
    getlink(index){
   
      this.$router.push({ name: this.card_list_2[index].name });
    },
    getpage(name){
       this.$router.push({ name: name });
    }
},
  computed: {
    columns() {
      if (this.$vuetify.breakpoint.xl) {
        return 6;
      }

      if (this.$vuetify.breakpoint.lg) {
        return 4;
      }

      if (this.$vuetify.breakpoint.md) {
        return 3;
      }

      return 1;
    },
  },
};
</script>

<style scoped>
.contentpage {
  width: 80%;
  margin-left: auto;
  margin-right: auto;
}
</style>
