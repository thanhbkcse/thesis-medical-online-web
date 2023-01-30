import service from "@/store/services/record";

const state = {};

const mutations = {};

const actions = {
  create_record({ commit }, params) {
    return service.create_record(params).then(({ data }) => {
     if(data.code == 200){
      this.$store.dispatch("snackbar/set_snackbar", {
        text: "Khám thành công",
        type: "success"
       });
     }
     else
     {
      this.$store.dispatch("snackbar/set_snackbar", {
        text: "Khám không thành công",
        type: "error"
       });
     }
    });
  } 
};

const getters = {};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
};
