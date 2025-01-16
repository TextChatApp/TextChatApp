<template>
  <div
    v-for="message in messages"
    class="mb-4 message"
    :class="{ own: isPrivate ? isOwnPrivate(message) : isOwn(message) }"
  >
    <MessageBlock
      :avatar="getAvatar(message)"
      :message="message"
      :username="getUsername(message)"
      :own="isPrivate ? isOwnPrivate(message) : isOwn(message)"
    ></MessageBlock>
  </div>
</template>

<script setup lang="ts">
import MessageBlock from '@/entities/message/ui'
import { useUser, type User } from '@/entities/user'
import { storeToRefs } from 'pinia'
import { getChatInfo } from '@/entities/chat'
import { ref } from 'vue'
import Avatar from '@/shared/ui/avatar'

const userStore = useUser()

const { getUserId, userInfo, users } = storeToRefs(userStore)

interface Props {
  messages: object[]
  isPrivate: boolean
}

const props = defineProps<Props>()

interface MessagePrivate {
  id?: number
  sender?: User
}

interface MessageServer {
  id?: number
  userId?: number
}

const isOwnPrivate = (message: MessagePrivate) => {
  if (userInfo.value && message.sender) return message.sender.id == userInfo.value.id ? true : false
}

const isOwn = (message: MessageServer) => {
  if (message) {
    return message?.userId == getUserId.value ? true : false
  }
}

const getUsername = (message: any): string => {
  if (props.isPrivate) {
    return message?.sender?.username
  } else {
    const user = users.value.find((item) => {
      return item.id == message.userId
    })
    return user ? user.username : 'uncknown'
  }
}

const getAvatar = (message: any): string | undefined => {
  if (props.isPrivate) {
    return message?.sender?.avatar
  } else {
    const user = users.value.find((item) => {
      return item.id == message.userId
    })
    return user ? user.avatar : undefined
  }
}
</script>

<style scoped>
.message {
  max-width: 50%;
  align-self: start;
}

.own {
  align-self: end;
}
</style>
