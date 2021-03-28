<template>
  <div class="app">
    <transition name="fade" mode="out-in" style="{background-color: teal}">
      <router-view />
    </transition>
  </div>
</template>

<script>
export default {
  name: "App",
  props: {
    msg: String,
  },
  data() {
    return {};
  },
  methods: {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
*,
body,
html {
  padding: 0;
  margin: 0;
  user-select: none;
}
</style>
