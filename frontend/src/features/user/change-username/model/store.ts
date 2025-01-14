import { defineStore, storeToRefs } from 'pinia'
import { reactive, ref } from 'vue'
import { serverCreate } from '@/entities/server'
import { useNotification } from '@kyvg/vue3-notification'
import { AxiosError } from 'axios'
import { changeUserInfoQuery } from '@/entities/user'
import { useUser } from '@/entities/user'

const userStore = useUser()

export const useChangeUser = defineStore('changeUserStore', () => {
  const notification = useNotification()
  const loading = ref(false)

  const changeProfile = async (userData: any) => {
    console.log(userData)
    try {
      loading.value = true
      if (userData) {
        console.log(userData)
        const { data } = await changeUserInfoQuery(userData)
        userStore.setNewInfo(data)
        notification.notify({
          title: 'Success',
          type: 'success',
          text: 'Server Created'
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
