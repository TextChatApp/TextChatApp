<template>
  <div class="flex gap-11 justify-center items-center sm:flex-col">
    <div class="flex justify-center items-center icon" :class="[{ active: isActive('chats') }]">
      <router-link :to="'/'"><IconHome :isActive="isActive('chats')"></IconHome></router-link>
    </div>
    <div class="flex justify-center items-center icon" :class="[{ active: isActive('settings') }]">
      <router-link :to="'#'"><IconSettings></IconSettings></router-link>
    </div>
    <div class="flex justify-center items-center icon" :class="[{ active: isActive('chat') }]">
      <router-link :to="'/chat/1'"
        ><IconChats :isActive="isActive('chat')"></IconChats
      ></router-link>
    </div>
    <div class="flex justify-center items-center icon" :class="[{ active: isActive('users') }]">
      <router-link to="/users"><IconUsers :isActive="isActive('users')"></IconUsers></router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { IconHome, IconChats, IconSettings, IconUsers } from '@/shared/ui/icons/menu'
import { onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { useRoute } from 'vue-router'

const router = useRoute()

const isActive = (name: string) => {
  return router.name === name
}
</script>

<style scoped>
.icon {
  width: 46px;
  height: 46px;
}
.active {
  background-color: #5051f9;
  border-radius: 14px;
}
</style>
