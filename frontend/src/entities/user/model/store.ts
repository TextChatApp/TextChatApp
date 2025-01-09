import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { type User } from './types'
import { getAllUsers } from '../api'

export const useUser = defineStore('user', () => {
  const userInfo = ref<User | null>(JSON.parse(localStorage.getItem('userInfo') || '""') || null)
  const users = ref<User[]>([])

  const setUserInfo = (data: User) => {
    userInfo.value = data
    localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
  }

  const getUserId = computed(() => userInfo.value?.id)

  const getUsers = async () => {
    try {
      const { data } = await getAllUsers()
      users.value = data
    } catch (err) {
      console.log(err)
    }
  }

  const logout = () => {
    userInfo.value = null
    localStorage.removeItem('userInfo')
  }

  const searchUsersByNickname = (username: string) => {
    if (!username) return users.value
    return users.value.filter((user) =>
      user.username.toLowerCase().includes(username.toLowerCase())
    )
  }

  return {
    userInfo,
    users,
    setUserInfo,
    getUsers,
    getUserId,
    searchUsersByNickname,
    logout
  }
})
