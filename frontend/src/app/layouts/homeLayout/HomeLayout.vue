<template>
  <div class="layout">
    <aside>
      <Menu></Menu>
    </aside>
    <Header></Header>
    <div class="content md:flex sm:pl-24">
      <Chats
        v-if="!isMobile"
        class="hidden md:block shrink-0 grow-0 px-7 py-5 bg-chats-bg h-full w-full"
      ></Chats>
      <main class="relative content-main w-full h-full">
        <router-view v-if="isMobile" name="mobile"></router-view>
        <router-view v-if="!isMobile" name="desktop"></router-view>
      </main>
      <!-- подумать -->
    </div>
  </div>
</template>

<script setup lang="ts">
import Menu from '@/widgets/menu'
import Header from '@/widgets/header'
import Chats from '@/widgets/sidebar'
import { ServerHeader } from '@/widgets/server-header'
import { onBeforeMount, ref } from 'vue'
import { useRoute } from 'vue-router'

const isMobile = ref(false)
const visibleRightAside = ref(false)

const route = useRoute()

const checkDevice = () => {
  isMobile.value = window.innerWidth <= 768
  visibleRightAside.value = window.innerWidth >= 1000
}

onBeforeMount(() => {
  checkDevice()
  window.addEventListener('resize', checkDevice)
})
</script>

<style scoped>
.content {
  height: calc(100vh - 80px);
}
.content-main {
  background-color: #131517;
  height: calc(100vh - 80px);
  flex: 1 1 75%;
}
</style>
