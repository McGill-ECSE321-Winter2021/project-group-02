import Vue from "vue";
import Router from "vue-router";
import Mainpage from "./pages/Mainpage.vue";
import Dashboard from "./pages/Dashboard.vue";
import MyAccount from "./pages/MyAccount.vue";
import ViewTechnicianSchedule from "./pages/ViewTechnicianSchedule.vue";
import ModifyAppointment from "./pages/ModifyAppointment.vue";
import AssignTechnicianSchedule from "./pages/AssignTechnicianSchedule.vue";
import CreateAccount from "./pages/CreateAccount.vue";
import BookAppointment from "./pages/BookAppointment";
import PayForAppointment from "./pages/PayForAppointment.vue";
import WorkspacePage from "./pages/WorkspacePage";
import ViewNotifications from "./pages/ViewNotifications.vue";
import ViewBookedAppointments from "./pages/ViewBookedAppointments.vue";
import ViewBookedAppointmentsAssistant from "./pages/ViewBookedAppointmentsAssistant.vue"

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
    {
      path: "/modifyappointment",
      name: "modifyAppointment",
      component: ModifyAppointment,
    },
    {
      path: "/assigntechnicianschedule",
      name: "AssignTechnicianSchedule",
      component: AssignTechnicianSchedule,
    },
    {
      path: "/admincreateaccount",
      name: "CreateAccount",
      component: CreateAccount,
    },
    {
      path: "/bookappointment",
      name: "BookAppointment",
      component: BookAppointment,
    },
    {
      path: "/pay",
      name: "PayForAppointment",
      component: PayForAppointment,
    },
    {
      path: "/workspace",
      name: "WorkspacePage",
      component: WorkspacePage,
    },
    {
      path: "/viewnotifications",
      name: "ViewNotifications",
      component: ViewNotifications,
    },
    {
      path: "/viewbookedappointments",
      name: "ViewBookedAppointments",
      component: ViewBookedAppointments,
    },
    {
      path: "/viewbookedappointmentsassistant",
      name: "ViewBookedAppointmentsAssistant",
      component: ViewBookedAppointmentsAssistant,
    },
  ],
});
