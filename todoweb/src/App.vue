<template>
  <div id="app">
    <ToDoApp />
  </div>
</template>

<script>
import ToDoApp from './components/ToDoApp.vue'

export default {
  components: {
    ToDoApp
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  margin-top: 60px;
}
</style>
