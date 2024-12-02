import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { type User } from './types'

export const useUser = defineStore('user', () => {
  const userInfo = ref<User | null>(null)

  const setUserInfo = (data: User) => {
    userInfo.value = data
  }

  return {
    userInfo,
    setUserInfo
  }
})
