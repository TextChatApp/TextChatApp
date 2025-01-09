<template>
  <div class="overflow-y-auto w-full h-full md:w-72 px-7 py-5 bg-chats-bg">
    <h2 class="text-2xl sm:text-3xl font-bold mb-6">Messages</h2>
    <div class="mb-10 w-full"><ChatsInput :placeholder="'Search...'"></ChatsInput></div>
    <div class="w-full">
      <div class="flex items-center gap-2 mb-4">
        <img :src="chatsIcon" alt="" />
        <h3>Personal chats</h3>
      </div>
      <div class="flex flex-col gap-5 items-center justify-center w-full mb-3">
        <!-- cards -->
        <router-link
          v-for="chat in chats"
          :to="`/chat/${chat.id}`"
          class="w-full pt-2 pb-2 px-1 transition-all"
          :class="{}"
          :key="chat.id"
        >
          <SidibarItem :chat="chat"></SidibarItem>
        </router-link>
        <Loader v-if="!chats" class="pt-10"></Loader>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ChatsInput from '@/shared/ui/inputs/ChatsInput'
import chatsIcon from '@/shared/assets/image/chats.png'
import Loader from '@/shared/ui/loader/Loader.vue'
import { getAllChats } from '@/entities/chat'
import SearchUsers from '@/features/search-users'
import { useRouter } from 'vue-router'

import SidibarItem from './SidibarItem.vue'
import { onMounted, ref } from 'vue'

const chats = ref()

const router = useRouter()

const getChats = async () => {
  try {
    const { data } = await getAllChats()
    chats.value = data
  } catch (err) {
    console.log(err)
  }
}

//запрос на получение чатов из энтити
onMounted(async () => {
  await getChats()
})
</script>

<style scoped>
.active-chat {
  border-bottom: 3px solid #fff;
  border-radius: 1px;
}
.router-link-active {
  background-color: #131517;
  border-radius: 15px;
}
</style>
