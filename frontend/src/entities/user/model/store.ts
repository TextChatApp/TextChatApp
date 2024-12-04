import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { type User } from './types'

export const useUser = defineStore('user', () => {
  const userInfo = ref<User | null>(JSON.parse(localStorage.getItem('userInfo') || '""') || null)

  const setUserInfo = (data: User) => {
    userInfo.value = data
    localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
  }

  const getUserId = computed(() => userInfo.value?.id)

  const logout = () => {
    userInfo.value = null
    localStorage.removeItem('userInfo')
  }

  return {
    userInfo,
    setUserInfo,
    getUserId,
    logout
  }
})
