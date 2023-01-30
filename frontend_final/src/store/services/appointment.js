import axios from "axios";

const url = process.env.VUE_APP_ROOT_API;
function speciality_list(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios.get(`${url}/api/specialties`, {}).then(response => {
    return {
      data: response.data
    };
  });
}
// user work
function getDoctorList_byDateAndSpeciality(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios
    .get(`${url}/api/schedules/doctors`, { params: params.data })
    .then(response => {
      return {
        data: response.data
      };
    });
}

function createAppointment(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios.post(`${url}/api/appointments`, params.data).then(response => {
    return {
      data: response.data
    };
  });
}

function get_appointment_by_user(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios
    .get(`${url}/api/appointments/user`, {
      params: {
        size: params.size,
        page: params.page
      }
    })
    .then(response => {
      return {
        data: response.data
      };
    });
}

function get_doctor_schedule_by_date(params) {
  return axios
    .get(`${url}/api/schedules`, { params: params })
    .then(response => {
      return {
        data: response.data
      };
    });
}

//doctor work
function get_appointment_by_doctor(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios
    .get(`${url}/api/appointments/doctor`, { params: params.data })
    .then(response => {
      return {
        data: response.data
      };
    });
}

export default {
  speciality_list,
  //user work
  getDoctorList_byDateAndSpeciality,
  createAppointment,
  get_appointment_by_user,
  get_doctor_schedule_by_date,
  //doctor work
  get_appointment_by_doctor
};
