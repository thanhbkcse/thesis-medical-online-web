<template>
  <v-row>
    <v-col cols="12"
      ><div class="d-flex">
        <v-avatar size="40">
          <img src="@/assets/img/home/appbar/avatar.png" />
        </v-avatar>
        <div class=" ml-6" style="flex: auto">
          <v-card
            width="417px"
            height="48px"
            class="d-flex mb-5"
            elevation="0"
            style="padding-top: 1px"
            outlined
          >
            <v-text-field
              v-model="questionModel.name"
              placeholder="Nhập tên của bạn."
              solo
              flat
              dense
              class="text-body-1"
            ></v-text-field>
          </v-card>
          <v-card outlined class="mb-3">
            <v-textarea
              v-model="questionModel.question"
              spellcheck="false"
              solo
              height="148"
              flat
              hide-details
              placeholder="Vui lòng nhập câu hỏi của bạn..."
            >
            </v-textarea>
          </v-card>

          <v-btn
            width="110"
            height="44"
            class="white--text btn text-body-2"
            elevation="0"
            color="#537DA5"
            @click="createQuestion()"
          >
            Gửi câu hỏi
          </v-btn>
        </div>
      </div>
    </v-col>
    <v-col cols="12">
      <div class="d-flex" v-for="(data, i) in listQuestion" :key="i">
        <v-avatar size="40">
          <img src="@/assets/img/home/appbar/avatar.png" />
        </v-avatar>
        <div class="ml-6" style="flex: auto">
          <p class="mb-2 font-weight-bold" style="font-size: 20px">
            {{ data.name }}
          </p>
          <p style="color: grey; font-size: 85% !important">
            {{ getDate(data.date) }}
          </p>
          <p class="text-body-1" style="margin-bottom: 0px">
            {{ data.question }}
          </p>
          <!-- already reply -->
          <v-btn
            v-if="data.answers.length && !alreadyReply"
            color="#537DA5"
            style="text-transform: none; font-size: 85%; margin-top: -1px"
            text
            @click="openAlreadyReply(i)"
            >{{ data.answers.length }} Trả lời</v-btn
          >
          <div v-if="alreadyReply && alreadyReplyId == i">
            <v-divider class="mt-3"></v-divider>
            <div class="d-flex" v-for="(dataReply, j) in data.answers" :key="j">
              <v-icon medium>
                mdi-reply
              </v-icon>
              <div class="d-flex mt-3 ml-6" style="flex: auto">
                <v-avatar size="40">
                  <img src="@/assets/img/home/appbar/avatar.png" />
                </v-avatar>
                <div class="ml-6" style="flex: auto">
                  <p class="mb-2 font-weight-bold" style="font-size: 20px">
                    {{ getName(data) }}
                  </p>
                  <p style="color: grey; font-size: 85% !important">
                    {{ getDate(data.date) }}
                  </p>
                  <p class="text-body-1 mt-3">
                    {{ dataReply.answer }}
                  </p>
                  <div v-if="j == data.answers.length - 1">
                    <v-card outlined class="mb-3">
                      <v-textarea
                        v-model="answerModel.answer"
                        spellcheck="false"
                        solo
                        height="148"
                        flat
                        hide-details
                        placeholder="Vui lòng nhập câu trả lời của bạn..."
                      >
                      </v-textarea>
                    </v-card>
                    <v-btn
                      height="44"
                      class="white--text btn text-body-2"
                      elevation="0"
                      outlined
                      color="indigo"
                      @click="closeAlreadyReply()"
                    >
                      Đóng
                    </v-btn>
                    <v-btn
                      height="44"
                      class="white--text btn text-body-2 ml-3"
                      elevation="0"
                      color="#537DA5"
                      @click="createAnswer(data.id, true, i)"
                    >
                      Trả lời
                    </v-btn>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- reply -->
          <v-btn
            v-if="!reply && !data.answers.length"
            color="#537DA5"
            style="text-transform: none; font-size: 85%; margin-top: -1px"
            text
            @click="openReply(i)"
            >Trả lời</v-btn
          >

          <div v-if="reply && replyId == i" class="mb-3">
            <v-divider class="mt-3"></v-divider>
            <div class="d-flex mt-3">
              <v-icon medium>
                mdi-reply
              </v-icon>
              <div class=" ml-6" style="flex: auto">
                <v-card outlined class="mb-3">
                  <v-textarea
                    v-model="answerModel.answer"
                    spellcheck="false"
                    solo
                    height="148"
                    flat
                    hide-details
                    placeholder="Vui lòng nhập câu trả lời của bạn..."
                  >
                  </v-textarea>
                </v-card>
                <v-btn
                  height="44"
                  class="white--text btn text-body-2"
                  elevation="0"
                  outlined
                  color="indigo"
                  @click="closeReply()"
                >
                  Hủy
                </v-btn>
                <v-btn
                  height="44"
                  class="white--text btn text-body-2 ml-3"
                  elevation="0"
                  color="#537DA5"
                  @click="createAnswer(data.id, false, null)"
                >
                  Trả lời
                </v-btn>
              </div>
            </div>
          </div>
        </div>
      </div>
    </v-col>
    <!-- page -->
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
      questionModel: {
        question: "",
        name: "",
        username: this.username,
        objectId: this.object?.id
      },
      answerModel: {
        answer: "",
        username: this.username,
        questionId: null
      },
      listQuestion: [],
      params: {
        objectId: null,
        page: 0,
        size: 5
      },
      page: 1,
      totalPages: 0,
      reply: false,
      replyId: null,
      alreadyReply: false,
      alreadyReplyId: null
    };
  },
  async created() {
    await this.getListQuestion();
  },
  computed: {
    isDoctor() {
      return this.$store.getters["auth/isDoctor"];
    },
    isUser() {
      return this.$store.getters["auth/isUser"];
    }
  },
  watch: {
    page: {
      handler() {
        this.getListQuestion();
      }
    }
  },
  methods: {
    async getListQuestion() {
      const url = process.env.VUE_APP_ROOT_API;
      this.params.objectId = this.object?.id;
      this.params.page = this.page - 1;
      const res = await this.axios.get(`${url}/api/questions`, {
        params: this.params
      });
      this.listQuestion = res.data.results;
      this.totalPages = res.data?.meta?.totalPages;
      this.$emit("setNumQuestion", res.data?.meta?.totalElements);
    },
    async createQuestion() {
      const url = process.env.VUE_APP_ROOT_API;
      let token = this.$store.getters["auth/access_token"];
      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      const questionModel = this._.cloneDeep(this.questionModel);
      await this.axios
        .post(`${url}/api/questions`, questionModel)
        .then(response => {
          this.questionModel.name = "";
          this.questionModel.question = "";
          this.$store.dispatch("snackbar/set_snackbar", {
            text: "Gửi câu hỏi thành công.",
            type: "success"
          });
          this.$emit("reloadQuestion");
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    async createAnswer(questionId, already, index) {
      const url = process.env.VUE_APP_ROOT_API;
      let token = this.$store.getters["auth/access_token"];
      this.axios.defaults.headers.common = {
        Authorization: `Bearer ${token}`
      };
      this.answerModel.questionId = questionId;
      const answerModel = this._.cloneDeep(this.answerModel);
      await this.axios
        .post(`${url}/api/questions/reply`, answerModel)
        .then(response => {
          this.answerModel.answer = "";
          this.answerModel.questionId = null;
          this.$store.dispatch("snackbar/set_snackbar", {
            text: "Trả lời thành công.",
            type: "success"
          });
          if (already) {
            let newAnswer = response.data.results;
            this.listQuestion[index].answers.push(newAnswer);
          } else this.$emit("reloadQuestion");
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
    getName(data) {
      if (this.isMyQuestion(data)) return data.name;
      else return this.username;
    },
    openReply(index) {
      this.reply = true;
      this.replyId = index;
    },
    closeReply() {
      this.reply = false;
      this.questionModel.name = "";
      this.questionModel.question = "";
    },
    openAlreadyReply(index) {
      this.alreadyReply = true;
      this.alreadyReplyId = index;
    },
    closeAlreadyReply() {
      this.alreadyReply = false;
    },
    isMyQuestion(data) {
      return data.user?.username === this.username;
    }
  },
  props: ["object", "username"]
};
</script>

<style scoped>
.btn {
  text-transform: none;
}
</style>
