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

const socketStore = useSocketStore()

onMounted(() => {
  socketStore.connect('ws://localhost:8080/ws')
})
</script>

<style scoped>
.app-continer {
  height: 100vh;
}
</style>
