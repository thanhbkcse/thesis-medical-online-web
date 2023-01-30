import service from "@/store/services/medicine";

const state = {
  store_medicine_by_hospital: []
};

const mutations = {
  SET_STORE_MEDICINE_BY_HOSPITAL: (state, store_medicine_by_hospital) => {
    state.store_medicine_by_hospital = store_medicine_by_hospital;
  }
};

const actions = {
    get_store_medicine_by_hospital({ commit }, param) {
     return service.get_store_medicine_by_hospital(param).then(({ data }) => {
      if (data.code == 200) {
        commit("SET_STORE_MEDICINE_BY_HOSPITAL", data.results);
      }
      else{
        commit("SET_STORE_MEDICINE_BY_HOSPITAL", []);
      }
      return data;
    });
  }
};

const getters = {
  store_medicine_by_hospital: state => state.store_medicine_by_hospital
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
};
