import { defineStore } from 'pinia'
import { ref } from 'vue'
import { type IChat } from './type'
import { getMyChats } from '../api'

export const useChatStore = defineStore('chatsStore', () => {
  const myChats = ref<IChat[]>()

  const lastUsedChat = ref()

  const setLastUsedChat = () => {
    let chatId = localStorage.getItem('lastUsedChatId')
    if (localStorage.getItem('lastUsedChatId')) {
      lastUsedChat.value = chatId
    } else if (myChats.value) {
      lastUsedChat.value = myChats.value[0]?.id
    }
  }

  const getPrivateChats = async () => {
    try {
      const { data } = await getMyChats(localStorage.getItem('token') || "'")
      myChats.value = data
    } catch (err) {
      console.log(err)
    }
  }

  return {
    myChats,
    getPrivateChats
  }
})
