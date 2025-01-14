<template>
  <div class="app-continer">
    <router-view></router-view>
    <notifications classes="my-notification" />
  </div>
</template>

<script setup lang="ts">
import axios from 'axios'
import { onMounted, watch } from 'vue'
// import useWebSocket from '@/shared/api/socket'
import { useSocketStore } from '@/shared/api/socket'
import { useUser } from '@/entities/user'

const socketStore = useSocketStore()
const userStore = useUser()

onMounted(() => {
  socketStore.connect('ws://localhost:8080/ws')
  userStore.getUsers()
})
</script>

<style scoped>
.app-continer {
  height: 100vh;
}
</style>
