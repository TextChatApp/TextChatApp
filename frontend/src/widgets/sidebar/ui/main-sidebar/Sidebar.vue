<template>
  <div class="overflow-y-auto w-full h-full md:w-72 px-7 py-5 bg-chats-bg">
    <h2 class="text-2xl sm:text-3xl font-bold mb-6">Messages</h2>
    <div class="mb-10 w-full"><ChatsInput></ChatsInput></div>
    <div class="w-full">
      <div class="flex items-center gap-2 mb-4">
        <img :src="chatsIcon" alt="" />
        <h3>Personal chats</h3>
      </div>
      <div class="flex flex-col gap-7 items-center justify-center w-full mb-3">
        <!-- cards -->
        <router-link v-for="item in items" :key="item.id" :to="'/chat/2'" class="w-full">
          <SidibarItem></SidibarItem>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ChatsInput from '@/shared/ui/inputs/ChatsInput'
import chatsIcon from '@/shared/assets/image/chats.png'
import user from '@/shared/assets/image/user.png'

import SidibarItem from './SidibarItem.vue'

//запрос на получение чатов из энтити
const items = [
  {
    id: 1,
    name: 'Design Team',
    desc: 'Hello everyone'
  },
  {
    id: 2,
    name: 'Design Team',
    desc: 'Hello everyone'
  },
  {
    id: 3,
    name: 'Design Team',
    desc: 'Hello everyone'
  },
  {
    id: 4,
    name: 'Design Team',
    desc: 'Hello everyone'
  }
]
</script>

<style scoped></style>
