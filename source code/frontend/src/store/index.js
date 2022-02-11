import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    fileItems: [],
    fat: [],
    info: null
  },
  mutations: {
    updateFileItems(state, payload) {
      state.fileItems = [payload];
    },
    updateFat(state, payload) {
      state.fat = payload.fat;
    },
    updateInfo(state, payload) {
      state.info = payload;
    }
  },
  actions: {
    updateFileItems(context, payload) {
      context.commit("updateFileItems", payload);
      fetch("http://localhost:7000/file/disk", {
        method: "GET",
        mode: "cors",
      })
        .then((response) => response.json())
        .then((json) => {
          if (json.error) console.log(json.error);
          else context.commit("updateFat", { fat: json });
        })
        .catch((err) => console.error(err));
    },
  },
  modules: {},
});
