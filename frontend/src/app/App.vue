<template>
  <div class="app-continer">
    <router-view></router-view>
    <notifications classes="my-notification" />
  </div>
</template>

<script setup lang="ts">
import axios from 'axios'
import { onMounted } from 'vue'

// const getData = async () => {
//   try {
//     const { data } = await axios.get('http://localhost:8080/api/chats')
//     console.log(data)
//   } catch (err) {
//     console.log(err)
//   }
// }

// onMounted(async () => {
//   await getData()
// })
</script>

<style scoped>
.app-continer {
  height: 100vh;
}
</style>
