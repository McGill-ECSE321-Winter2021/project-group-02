<template>
  <div class="view-notifications">

      <ul id="example-1">
  <li v-for="notification in notifications" :key="notification.message">
    {{ notification.message }}
  </li>
    </ul>



  </div>
</template>


<script>
	import axios from "axios";
	import proxy from "../constants.js";

export default {
    name: "ViewNotifications",
    props: {
    msg: String,
    },
  components: {},
  data() {
    return {
      notifications: [],
    };
  },
    methods: {},
  async mounted() {
    try {
      
      let notificationResponse = axios.get(
        proxy.proxy`api/appointment/notifications/${this.$store.state.user}`
      );
      if (notificationResponse.status !== 200) return;
      this.notifications = notificationResponse.data;
    } catch (error) {
      console.log(`${error}`);
    }
  },
};
</script>