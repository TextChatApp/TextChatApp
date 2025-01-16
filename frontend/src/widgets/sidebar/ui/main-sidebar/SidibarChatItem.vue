<template>
  <div class="flex items-center justify-between w-full">
    <div class="flex gap-x-4 w-full sm:w-auto">
      <div>
        <Avatar :url="partner?.avatar" :size="50"></Avatar>
        <!-- <img :src="user" alt="" width="50" height="50" /> -->
      </div>
      <div class="flex-1 min-w-0">
        <span class="block font-bold text-base truncate">{{ chatName }}</span>
        <p
          class="block text-sm truncate font-bold text-grey-main"
          :class="{ 'text-green-500': getOnlineStatus == 'online' }"
        >
          {{ getOnlineStatus }}
        </p>
      </div>
    </div>
    <div class="flex flex-col items-end text-right">
      <span class="block font-bold text-xs text-grey-main truncate">4:30 PM</span>
      <!-- <span class="block font-bold text-xs text-grey-main truncate">dsdsd</span> -->
    </div>
  </div>
</template>

<script setup lang="ts">
import user from '@/shared/assets/image/default-avatar-accent.png'
import Avatar from '@/shared/ui/avatar'
import { useUser } from '@/entities/user'
import { type User } from '@/entities/user'
import { storeToRefs } from 'pinia'
import { computed, watch } from 'vue'
import { useSocketStore } from '@/shared/api'

const userStore = useUser()
const { getUserId, users } = storeToRefs(userStore)

interface PrivateChat {
  id?: number
  userOne?: User
  userTwo?: User
}

const props = defineProps<{
  chat: PrivateChat
}>()

const chatName = computed(() =>
  props.chat.userOne?.id == getUserId.value
    ? props.chat.userTwo?.username
    : props.chat.userOne?.username
)

const partner = computed(() => {
  return props.chat.userOne?.id == getUserId.value ? props.chat.userTwo : props.chat.userOne
})

const getOnlineStatus = computed(() => {
  const findUser = users.value.find((item) => item.id == partner.value?.id)
  if (findUser) {
    return findUser.status
  }
})

watch(
  () => users.value,
  (newUsers) => {
    console.log('Users updated:', newUsers)
  },
  { deep: true } // Глубокое наблюдение за изменениями
)
</script>

<style></style>
