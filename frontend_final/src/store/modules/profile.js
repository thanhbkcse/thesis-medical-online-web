import service from "@/store/services/profile";

const state = {
  profile_list: []
};

const mutations = {
  SET_PROFILE_LIST: (state, list) => {
    state.profile_list = list;
  }
};

const actions = {
  profile_list({ commit }, params) {
    return service.profile_list(params).then(({ data }) => {
      if (data.code == 200) {
        commit("SET_PROFILE_LIST", data.results);
      } else {
        console.log("error");
      }
    });
  },
  add_new_profile({ commit }, params) {
    return service.add_new_profile(params).then(({ data }) => {
      console.log("add new profile success");
    });
  },
  remove_profile({ commit }, params) {
    return service.remove_profile(params).then(({ data }) => {
      console.log("remove profile success");
    });
  },

  edit_profile({ commit }, params) {
    return service.edit_profile(params).then(({ data }) => {
      console.log("add new profile success");
    });
  }
};

const getters = {
  profile_list: state => state.profile_list
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
};
