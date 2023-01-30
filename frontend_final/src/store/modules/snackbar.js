const state = {
  snackbar: { showing: false, text: "", color: "" }
};

const mutations = {
  setSnackbar: (state, data) => {
    state.snackbar.showing = data.showing;
    state.snackbar.text = data.text;
    state.snackbar.color = data.color;
  }
};

const actions = {
  // Du lieu truyen vao la params
  // Return goi ham schedule_list ben service
  // Goi API va lay cai body ra
  // cai data trong then chinh la cai body
  // goi ham commit chinh la mutation

  set_snackbar({ commit }, params) {
    switch (params.type) {
      case "success":
        commit("setSnackbar", {
          showing: true,
          text: params.text,
          color: "green"
        });
        break;
      case "warn":
        commit("setSnackbar", {
          showing: true,
          text: params.text,
          color: "#F79009"
        });
        break;
      case "error":
        commit("setSnackbar", {
          showing: true,
          text: params.text,
          color: "#F04438"
        });
        break;
      default:
        break;
    }
  }
};
export default {
  namespaced: true,
  state,
  mutations,
  actions
};
