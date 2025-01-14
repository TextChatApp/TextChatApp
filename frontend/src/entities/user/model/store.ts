import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { type User } from './types'
import { getAllUsers } from '../api'

export const useUser = defineStore('user', () => {
  const userInfo = ref<User | null>(JSON.parse(localStorage.getItem('userInfo') || '""') || null)
  const userToken = ref(localStorage.getItem('token') || '""' || null)
  const users = ref<User[]>([])

  const setUserInfo = (user: any, token: string) => {
    userInfo.value = user
    userToken.value = token
    localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
    localStorage.setItem('token', userToken.value)
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
    localStorage.removeItem('token')
  }

  const setNewInfo = (user: any) => {
    delete user.password
    userInfo.value = user
    localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
  }

  const searchUsersByNickname = (username: string) => {
    if (!username) return users.value
    return users.value.filter((user) =>
      user.username.toLowerCase().includes(username.toLowerCase())
    )
  }

  return {
    userInfo,
    userToken,
    users,
    setUserInfo,
    getUsers,
    getUserId,
    searchUsersByNickname,
    logout,
    setNewInfo
  }
})
