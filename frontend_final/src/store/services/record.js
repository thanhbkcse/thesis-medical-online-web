import axios from "axios";

const url = process.env.VUE_APP_ROOT_API;

function create_record(params) {
  axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
  return axios
    .post(`${url}/api/records`, params.data)
    .then((response) => {
      return {
        data: response.data,
      };
    });
}
export default {
  create_record,
};
