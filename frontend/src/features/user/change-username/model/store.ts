import { defineStore, storeToRefs } from 'pinia'
import { reactive, ref } from 'vue'
import { serverCreate } from '@/entities/server'
import { useNotification } from '@kyvg/vue3-notification'
import { AxiosError } from 'axios'
import { changeUserInfoQuery } from '@/entities/user'
import { useUser } from '@/entities/user'

const userStore = useUser()
const { userToken } = storeToRefs(userStore)

export const useChangeUser = defineStore('changeUserStore', () => {
  const notification = useNotification()
  const loading = ref(false)

  const changeProfile = async (userData: any) => {
    try {
      loading.value = true
      if (userData) {
        const { data } = await changeUserInfoQuery(userData, userToken.value)
        userStore.setNewInfo(data.user)
        notification.notify({
          title: 'Success',
          type: 'success',
          text: 'User info changed'
        })
      }
    } catch (err) {
      if (err instanceof AxiosError) {
        notification.notify({
          title: 'Error',
          type: 'error',
          text: err?.response?.data
        })
      } else {
        notification.notify({
          title: 'Error',
          type: 'error',
          text: 'An unexpected error occurred' // или можно логировать ошибку
        })
      }
    } finally {
      loading.value = false
    }
  }

  return { changeProfile, loading }
})
