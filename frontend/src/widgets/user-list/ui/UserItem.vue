<template>
  <div class="flex items-center gap-3">
    <div class="flex">
      <img :src="UserIcon" alt="" />
      <span class="text-xl min-w-24 font-bold">{{ user?.username }}</span>
    </div>
    <button
      @click="startChat()"
      class="px-5 py-2 bg-main rounded-xl transition-all hover:scale-105"
    >
      Начать общение
    </button>
  </div>
</template>

<script setup lang="ts">
import { type User } from '@/entities/user'
import UserIcon from '@/shared/assets/image/default-avatar.png'
import { startPrivateChat } from '@/entities/chat'
import { useRouter } from 'vue-router'
import { useUser } from '@/entities/user'
import { storeToRefs } from 'pinia'

const userStore = useUser()
const { getUserId } = storeToRefs(userStore)

const router = useRouter()

const startChat = async () => {
  try {
    if (props.user && getUserId.value) {
      const { data } = await startPrivateChat(getUserId.value, props.user?.id)
      console.log(data)
    }
  } catch (err) {
    console.log(err)
  }
}

const props = defineProps({
  user: Object
})
</script>

<style></style>
