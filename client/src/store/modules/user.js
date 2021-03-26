const state = {
  user: -1,
};

const getters = {
  getUser: (state) => state.user,
};

const actions = {
  setUser({ commit }, user) {
    commit("setUser", user);
  },
};

const mutations = {
  setUser: (state, user) => {
    state.user = user;
  },
};

export default { state, getters, actions, mutations };
