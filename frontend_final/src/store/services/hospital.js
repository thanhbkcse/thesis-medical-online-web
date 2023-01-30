import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;

function read_all_hospital(params) {
  return axios.post(`${url}/api/hospitals/search`, params).then(response => {
    return {
      data: response.data
    };
  });
}

function get_doctor_by_hospital(params) {
  return axios
    .get(`${url}/api/doctors/hospital`, { params: params })
    .then(response => {
      return {
        data: response.data
      };
    });
}

export default {
  read_all_hospital,
  get_doctor_by_hospital
};
