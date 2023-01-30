import service from "@/store/services/hospital";

const state = {
  hospital_all_data: [],
  hospital_selected: {},
  doctor_by_hospital: []
};

const mutations = {
  SET_HOSPITAL_ALL_DATA: (state, hospital_all_data) => {
    state.hospital_all_data = hospital_all_data;
  },
  SET_HOSPITAL_SELECTED: (state, hospital_selected) => {
    state.hospital_selected = hospital_selected;
  },
  SET_DOCTOR_BY_HOSPITAL: (state, doctor_by_hospital) => {
    state.doctor_by_hospital = doctor_by_hospital;
  }
};

const actions = {
  read_all_hospital({ commit }, params) {
    return service.read_all_hospital(params).then(({ data }) => {
      if (data.results != null) {
        commit("SET_HOSPITAL_ALL_DATA", data.results);
      }
      return data;
    });
  },

  set_hospital_select_info({ commit }, param) {
    commit("SET_HOSPITAL_SELECTED", param);
  },

  get_doctor_by_hospital({ commit }, param) {
    return service.get_doctor_by_hospital(param).then(({ data }) => {
      if (data.results != null) {
        commit("SET_DOCTOR_BY_HOSPITAL", data.results);
      }
      return data;
    });
  }
};

const getters = {
  hospital_all_data: state => state.hospital_all_data,
  hospital_selected: state => state.hospital_selected,
  doctor_by_hospital: state => state.doctor_by_hospital
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
};
