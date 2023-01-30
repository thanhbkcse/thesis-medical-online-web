import axios from "axios";

const url = "http://13.40.75.147:8080";

function schedule_list(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios.get(`${url}/api/doctor/schedules`, {}).then((response) => {
    return {
      data: response.data,
    };
  });
}

export default {
  schedule_list,
};
