import axios from "axios";
const url = process.env.VUE_APP_ROOT_API;

function get_store_medicine_by_hospital(params) {
    axios.defaults.headers.common = { Authorization: `Bearer ${params.token}` };
    return axios
      .get(`${url}/api/store-medicines/hospital`, { params: params.data })
      .then(response => {
        return {
          data: response.data
        };
    });
}

export default {
    get_store_medicine_by_hospital
};