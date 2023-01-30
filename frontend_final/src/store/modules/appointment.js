import service from "@/store/services/appointment";

const state = {
  speciality_list: [],
  //user work
  doctors_list: [],
  user_appointment_list: [],
  make_appointment_doctor_select: {},
  make_appointment_doctor_schedule: [],
  make_appointment_set_time_doctor: null,
  make_appointment_service_select:{},
  make_appointment_set_time_service: null,
  //doctor work
  doctor_appointment_list: []
};

const mutations = {
  SET_SPECIALITY_LIST: (state, list) => {
    state.speciality_list = list;
  },
  // user work
  SET_DOCTOR_LIST: (state, list) => {
    state.doctors_list = list;
  },

  SET_USER_APPOINTMENT_LIST: (state, list) => {
    state.user_appointment_list = list;
  },

  SET_MAKE_APPOINTMENT_DOCTOR_SELECT: (
    state,
    make_appointment_doctor_select
  ) => {
    state.make_appointment_doctor_select = make_appointment_doctor_select;
  },

  SET_MAKE_APPOINTMENT_DOCTOR_SCHEDULE: (
    state,
    make_appointment_doctor_schedule
  ) => {
    state.make_appointment_doctor_schedule = make_appointment_doctor_schedule;
  },

  SET_MAKE_APPOINTMENT_SET_TIME_DOCTOR: (
    state,
    make_appointment_set_time_doctor
  ) => {
    state.make_appointment_set_time_doctor = make_appointment_set_time_doctor;
  },

  SET_MAKE_APPOINTMENT_SERVICE_SELECT:(state, make_appointment_service_select)=>{
    state.make_appointment_service_select = make_appointment_service_select
  },

  SET_MAKE_APPOINTMENT_SET_TIME_SERVICE: (
    state,
    make_appointment_set_time_service
  ) => {
    state.make_appointment_set_time_service = make_appointment_set_time_service;
  },

  //doctor work
  SET_DOCTOR_APPOINTMENT_LIST: (state, list) => {
    state.doctor_appointment_list = list;
  }
};

const actions = {
  speciality_list({ commit }, params) {
    return service.speciality_list(params).then(({ data }) => {
      if (data.code == 1) {
        commit("SET_SPECIALITY_LIST", data.results);
      } else {
        console.log("error");
      }
    });
  },
  // user work
  getDoctorList_byDateAndSpeciality({ commit }, params) {
    return service
      .getDoctorList_byDateAndSpeciality(params)
      .then(({ data }) => {
        if (data.code == 1) {
          commit("SET_DOCTOR_LIST", data.results);
        } else {
          console.log("error");
        }
      });
  },

  createAppointment({ commit }, params) {
    return service.createAppointment(params).then(({ data }) => {
      console.log("add new appointment success");
    });
  },

  get_appointment_by_user({ commit }, params) {
    return service.get_appointment_by_user(params).then(({ data }) => {
      if (data.code == 200) {
        commit("SET_USER_APPOINTMENT_LIST", data.results);
        return data;
      } else {
        console.log("error");
      }
    });
  },

  set_doctor_select_to_make_appointment({ commit }, params) {
    commit("SET_MAKE_APPOINTMENT_DOCTOR_SELECT", params);
  },

  get_doctor_schedule({ commit }, params) {
    return service.get_doctor_schedule_by_date(params).then(({ data }) => {
      if (data.code == 200) {
        commit("SET_MAKE_APPOINTMENT_DOCTOR_SCHEDULE", data.results);
      } else {
        console.log("error");
      }
    });
  },

  set_time_to_make_appointment_doctor({ commit }, params) {
    commit("SET_MAKE_APPOINTMENT_SET_TIME_DOCTOR", params);
  },

  set_service_select_to_make_appointment({ commit }, params){
    commit("SET_MAKE_APPOINTMENT_SERVICE_SELECT", params);
  },

  set_time_to_make_appointment_service({ commit }, params) {
    commit("SET_MAKE_APPOINTMENT_SET_TIME_SERVICE", params);
  },

  //doctor work
  get_appointment_by_doctor({ commit }, params) {
    return service.get_appointment_by_doctor(params).then(({ data }) => {
      if (data.code == 200) {
        commit("SET_DOCTOR_APPOINTMENT_LIST", data.results);
      } else {
        console.log("error");
      }
    });
  }
};

const getters = {
  speciality_list: state => state.speciality_list,
  //user work
  doctors_list: state => state.doctors_list,
  user_appointment_list: state => state.user_appointment_list,
  make_appointment_doctor_select: state => state.make_appointment_doctor_select,
  make_appointment_doctor_schedule: state =>
    state.make_appointment_doctor_schedule,
  make_appointment_set_time_doctor: state =>
    state.make_appointment_set_time_doctor,
  make_appointment_service_select: state => state.make_appointment_service_select,
  make_appointment_set_time_service: state => state.make_appointment_set_time_service,
  //doctor work
  doctor_appointment_list: state => state.doctor_appointment_list
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
};
