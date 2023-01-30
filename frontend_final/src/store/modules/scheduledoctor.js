import service from "@/store/services/scheduledoctor";

// state la luu toan cuc
// khoi tao schedule_list rong
const state = {
  schedule_list: [],
};

// luu cai data lay duoc trong cai action vao schedule_list rong tren state
const mutations = {
  saveData: (state, data) => {
    state.schedule_list = data;
  },
};

const actions = {
  // Du lieu truyen vao la params
  // Return goi ham schedule_list ben service
  // Goi API va lay cai body ra
  // cai data trong then chinh la cai body
  // goi ham commit chinh la mutation

  schedule_list({ commit }, params) {
    return service.schedule_list(params).then(({ data }) => {
      commit("saveData", data);
    });
  },
};

// Lay schedule list
const getters = {
  schedule_list: (state) => state.schedule_list,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
