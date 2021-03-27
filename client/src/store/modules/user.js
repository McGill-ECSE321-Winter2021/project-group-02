const state = {
  user: -1,
  userType: "",
  technicianSchedule: [],
};

const getters = {
  getUser: (state) => {
    return {
      user: state.user,
      userType: state.userType,
    };
  },
};

const actions = {
  setUser({ commit }, { user, userType }) {
    commit("setUser", { user, userType });
  },
};

const mutations = {
  setUser: (state, { user, userType }) => {
    state.user = user;
    state.userType = userType;
  },
};

export default { state, getters, actions, mutations };
