<template>
  <div
    ref="chat"
    class="chat overflow-y-auto chat-wrapper px-7 py-5 xl:px-16 flex flex-col-reverse items-end"
  >
    <div v-for="message in messages" class="mb-2 message" :class="{ own: isOwn(message) }">
      <MessageBlock :message="message"></MessageBlock>
    </div>
  </div>
  <div class="absolute left-0 bottom-78 sm:bottom-0 w-full bg-input-bg px-8 py-5 z-10">
    <MessageInput @send-message="sendMessageFromUser" v-model="message"></MessageInput>
  </div>
</template>

<script setup lang="ts">
import MessageInput from '@/features/message/message-input'
import MessageBlock from '@/entities/message/ui'
import { onBeforeMount, onMounted, onUnmounted, ref, watch, nextTick } from 'vue'
import { useSocketStore } from '@/shared/api'
// import useWebSocket from '@/shared/api/socket'
import { useUser, type User } from '@/entities/user'
import { storeToRefs } from 'pinia'
import { getChatInfo } from '@/entities/chat'
import { computed } from '@vue/reactivity'

const userStore = useUser()
const socketStore = useSocketStore()

const { getUserId, userInfo } = storeToRefs(userStore)
const { messages } = storeToRefs(socketStore)

const props = defineProps({
  id: String
})

interface Message {
  id?: number
  sender?: User
}

const message = ref<string>('')

const chat = ref()
const chatInfo = ref()

const scrollToBottom = () => {
  if (chat.value) {
    chat.value.scrollTop = chat.value.scrollHeight
  }
}

const isOwn = (message: Message) => {
  if (userInfo.value && message.sender) return message.sender.id == userInfo.value.id ? true : false
}

// const { connect, sendMessage, disconnect, messages, isConnected } =
//   useWebSocket('ws://localhost:8080/ws')

const getReceiverId = computed(() => {
  if (chatInfo.value) {
    const userOneId = chatInfo.value.userOne.id
    const userTwoId = chatInfo.value.userTwo.id
    return userOneId !== getUserId.value ? userOneId : userTwoId
  }
})

const sendMessageFromUser = () => {
  try {
    if (getUserId.value) {
      socketStore.sendMessage(message.value, getUserId.value, getReceiverId.value)
      message.value = ''
    }
  } catch (err) {
    console.log(err)
  }
}

const getCurrentChatInfo = async () => {
  try {
    const { data } = await getChatInfo(props.id)
    chatInfo.value = data
    messages.value = chatInfo.value.messages.reverse()
  } catch (err) {
    console.log(err)
  }
}

onMounted(async () => {
  await getCurrentChatInfo()
  // connect()
  scrollToBottom()
})

// onUnmounted(() => {
//   disconnect()
// })

watch(
  messages,
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
</style>
