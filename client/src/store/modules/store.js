const state = {
  user: -1,
  userType: "",
  apptIdToModify : -1,
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
  setApptIdToModify({ commit }, apptIdToModify) {
    commit("setApptIdToModify", apptIdToModify);
  },
};

const mutations = {
  setUser: (state, { user, userType }) => {
    state.user = user;
    state.userType = userType;
  },
  setApptIdToModify: (state, apptIdToModify) => {
    state.apptIdToModify = apptIdToModify;
  },
};

export default { state, getters, actions, mutations };
