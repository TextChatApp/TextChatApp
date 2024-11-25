<template>
  <div class="layout">
    <aside>
      <Menu></Menu>
    </aside>
    <Header></Header>
    <div class="content md:flex sm:pl-24">
      <Chats
        v-if="!isMobile"
        class="hidden md:block chats flex-auto container px-7 py-5 bg-chats-bg h-full w-full"
      ></Chats>
      <main class="content-main w-full px-7 py-5">
        <router-view v-if="isMobile" name="mobile"></router-view>
        <router-view v-if="!isMobile" name="desktop"></router-view>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import Menu from '@/widgets/menu'
import Header from '@/widgets/header'
import Chats from '@/widgets/chats'
import { onBeforeMount, ref } from 'vue'
import { useRoute } from 'vue-router'

const isMobile = ref(false)

const route = useRoute()

const checkDevice = () => {
  isMobile.value = window.innerWidth <= 768
}

onBeforeMount(() => {
  isMobile.value = window.innerWidth <= 768
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
