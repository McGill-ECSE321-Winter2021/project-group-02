import Vue from "vue";
import Router from "vue-router";
import Mainpage from "./pages/Mainpage.vue";
import Dashboard from "./pages/Dashboard.vue";
import MyAccount from "./pages/MyAccount.vue";

Vue.use(Router);

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "Mainpage",
      component: Mainpage,
    },
    {
      path: "/dashboard",
      name: "Dashboard",
      component: Dashboard,
    },
    {
      path: "/myaccount",
      name: "MyAccount",
      component: MyAccount,
    },
  ],
});
