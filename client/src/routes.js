import Vue from "vue";
import Router from "vue-router";
import Mainpage from "./pages/Mainpage.vue";
import Dashboard from "./pages/Dashboard.vue";
import MyAccount from "./pages/MyAccount.vue";
import ViewTechnicianSchedule from "./pages/ViewTechnicianSchedule.vue";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
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
    {
      path: "/viewtechnicianschedule",
      name: "ViewTechnicianSchedule",
      component: ViewTechnicianSchedule,
    },
  ],
});
