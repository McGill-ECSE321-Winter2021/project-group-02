import VueRouter from "vue-router";
import Mainpage from "./pages/Mainpage.vue";

const routes = [{ path: "/", name: "Mainpage", component: Mainpage }];

const router = new VueRouter({
  mode: "history",
  routes,
});

export default router;
