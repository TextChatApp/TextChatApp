import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'
import { serverCreate } from '@/entities/server'
import { useNotification } from '@kyvg/vue3-notification'
import { AxiosError } from 'axios'

export const useServerCreatingStore = defineStore('serverCreatingStore', () => {
  const notification = useNotification()
  const serverData = reactive({
    name: '',
    description: ''
  })
  const loading = ref(false)

  const createServer = async () => {
    const userToken = localStorage.getItem('token')

    try {
      loading.value = true
      if (serverData) {
        const { data } = await serverCreate(userToken, serverData)
        notification.notify({
          title: 'Success',
          type: 'success',
          text: 'Server Created'
        })
        serverData.name = ''
        serverData.description = ''
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

  return { serverData, createServer, loading }
})
