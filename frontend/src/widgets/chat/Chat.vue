<template>
  <div
    ref="chat"
    class="chat overflow-y-auto chat-wrapper px-7 py-5 xl:px-16 flex flex-col items-end"
  >
    <div v-for="message in 50" class="mb-2 message">
      <MessageBlock></MessageBlock>
    </div>
  </div>
  <div class="absolute left-0 bottom-78 sm:bottom-0 w-full bg-input-bg px-8 py-5 z-10">
    <MessageInput></MessageInput>
  </div>
</template>

<script setup lang="ts">
import MessageInput from '@/features/message/message-input'
import MessageBlock from '@/entities/message/ui'
import { onMounted, ref } from 'vue'

const chat = ref()

const scrollToBottom = () => {
  if (chat.value) {
    chat.value.scrollTop = chat.value.scrollHeight
  }
}

onMounted(() => {
  scrollToBottom()
})
</script>

<style scoped>
.chat {
  background-color: #131517;
}
.input {
  bottom: 78px;
}
.message {
  max-width: 50%;
}

.chat-wrapper {
  height: calc(100% - 88px);
}
</style>
