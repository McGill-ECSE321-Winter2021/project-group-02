import Vue from "vue";
import Mainpage from "./pages/Mainpage.vue";
import router from "./routes";

Vue.config.productionTip = false;

new Vue({
  router,
  render: (h) => h(Mainpage),
}).$mount("#app");
