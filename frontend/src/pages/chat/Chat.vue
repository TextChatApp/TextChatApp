<template>
  <div class="w-full bg-chats-bg h-16 p-3" v-if="!isPrivate && infoRoom">
    <ServerHeader :info="infoRoom?.room" :members="membersServer"></ServerHeader>
  </div>
  <div
    ref="chat"
    class="chat overflow-y-auto chat-wrapper px-7 pt-5 xl:px-16 flex flex-col-reverse items-end"
    :class="{ 'pb-24': !isPrivate, 'pb-5': isPrivate }"
  >
    <MessageList
      v-if="chatInfo || infoRoom"
      :isPrivate="isPrivate"
      :messages="isPrivate ? state.privateMessages : state.roomMessages"
    ></MessageList>
  </div>
  <div class="absolute left-0 bottom-78 sm:bottom-0 w-full bg-input-bg px-8 py-5 z-10">
    <MessageInput @send-message="sendMessageFromUser" v-model="message"></MessageInput>
  </div>
</template>

<script setup lang="ts">
import MessageInput from '@/features/message/message-input'
import { MessageList } from '@/widgets/chat'
import { onBeforeMount, onMounted, onUnmounted, ref, watch, nextTick, provide } from 'vue'
import { useSocketStore } from '@/shared/api'
import { useUser, type User } from '@/entities/user'
import { storeToRefs } from 'pinia'

import { getChatInfo } from '@/entities/chat'
import { roomInfo, serverUsers, useServerStore } from '@/entities/server'

import { serverInfoQuery } from '@/entities/server'
import { computed } from '@vue/reactivity'
import { useRoute } from 'vue-router'
import { ServerHeader } from '@/widgets/server-header'
import { useRouter } from 'vue-router'

const userStore = useUser()
const serverStore = useServerStore()

const socketStore = useSocketStore()

const { getUserId, userInfo } = storeToRefs(userStore)
const { servers, infoRoom, membersServer } = storeToRefs(serverStore)

const { state } = storeToRefs(socketStore)

const isPrivate = computed(() => {
  return route.name == 'chat' ? true : false
})

const isUserAdmin = computed(() => {
  if (membersServer.value) {
    const member = membersServer.value.find((member: any) => {
      return member.id == getUserId.value
    })
    return member.role == 'admin' ? true : false
  }
})

const route = useRoute()
const router = useRouter()

const token = localStorage.getItem('token')

const props = defineProps({
  id: String,
  serverId: String
})

const getServerId = computed(() => Number(props.serverId))

const getServerCode = computed(() => {
  if (servers.value) {
    const currentServer = servers.value.find((server: any) => props.serverId == server.id)
    return currentServer?.inviteCode
  }
})

provide('isUserAdmin', isUserAdmin)
provide('serverId', getServerId)
provide('serverCode', getServerCode)

const message = ref<string>('')

const chat = ref()
const chatInfo = ref()

const scrollToBottom = () => {
  if (chat.value) {
    chat.value.scrollTop = chat.value.scrollHeight
  }
}

const getReceiverId = computed(() => {
  if (chatInfo.value) {
    const userOneId = chatInfo.value.userOne.id
    const userTwoId = chatInfo.value.userTwo.id
    return userOneId !== getUserId.value ? userOneId : userTwoId
  }
})

const currentChatInfo = computed(() => {
  return chatInfo.value ? chatInfo.value.room : null
})

const sendMessageFromUser = () => {
  if (isPrivate.value) {
    try {
      if (getUserId.value) {
        socketStore.sendPrivateMessage(message.value, getUserId.value, getReceiverId.value)
        message.value = ''
      }
    } catch (err) {
      console.log(err)
    }
  } else {
    try {
      if (getUserId.value) {
        socketStore.sendRoomMessage(message.value, getUserId.value, props.id)
        message.value = ''
      }
    } catch (err) {
      console.log(err)
    }
  }
}

const getCurrentChatInfo = async () => {
  if (isPrivate.value) {
    try {
      const { data } = await getChatInfo(props.id, token)
      chatInfo.value = data
      state.value.privateMessages = chatInfo.value.messages.reverse()
    } catch (err) {
      console.log(err)
    }
  } else {
    socketStore.subscribeToRoom(props.id)
    try {
      await serverStore.getRoomInfo(props.id, props.serverId)
      state.value.roomMessages = infoRoom.value.messages.reverse()
    } catch (err) {
      router.push('/')
    }
  }
}

onMounted(async () => {
  if (isPrivate.value) {
    await getCurrentChatInfo()
    socketStore.subscribeToUser()
    socketStore.subscribeToPrivateMessages()
  } else {
    await getCurrentChatInfo()
    socketStore.subscribeToRoom(props.id)
  }
  // connect()
  scrollToBottom()
})

watch(
  state.value.privateMessages,
  () => {
    nextTick(() => {
      scrollToBottom() // Прокручиваем вниз каждый раз после изменения сообщений
    })
  },
  { immediate: true, deep: true } // Прокрутить вниз при первом рендере
)

watch(
  () => props.id,
  async (newId, oldId) => {
    if (newId !== oldId) {
      message.value = ''
      await getCurrentChatInfo()
      scrollToBottom()
    }
  }
)
</script>

<style scoped>
.chat {
  background-color: #131517;
  height: calc(100% - 88px);
}

@media (max-width: 650px) {
  .chat {
    height: calc(100% - 166px);
  }
}
.input {
  bottom: 78px;
}
.message {
  max-width: 50%;
  align-self: start;
}

.own {
  align-self: end;
}
.message-list {
  flex: 1 1 auto; /* Гибкость для сжатия и расширения */
  max-width: 100%; /* Ограничивает ширину родителя */
  width: auto; /* Убирает фиксированную ширину */
  box-sizing: border-box; /* Включает padding в расчет ширины */
}
</style>
