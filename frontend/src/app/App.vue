<template>
  <div class="app-continer">
    <router-view></router-view>
    <notifications classes="my-notification" />
  </div>
</template>

<script setup lang="ts">
import axios from 'axios'
import { onMounted, onUnmounted, watch } from 'vue'
// import useWebSocket from '@/shared/api/socket'
import { useSocketStore } from '@/shared/api/socket'
import { useUser } from '@/entities/user'
import { storeToRefs } from 'pinia'

const socketStore = useSocketStore()
const userStore = useUser()

const { userToken } = storeToRefs(userStore)

onMounted(() => {
  socketStore.connect('ws://localhost:8080/ws', userToken.value)
  userStore.getUsers()
})

onUnmounted(() => {
  socketStore.disconnect()
})
</script>

<style scoped>
.app-continer {
  height: 100vh;
}
</style>
