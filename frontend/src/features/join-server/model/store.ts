import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'
import { joinServerQuery } from '@/entities/server'
import { useNotification } from '@kyvg/vue3-notification'
import { AxiosError } from 'axios'
import { useServerStore } from '@/entities/server'
import { storeToRefs } from 'pinia'

const serverStore = useServerStore()
const { servers } = storeToRefs(serverStore)

export const useJoinServerStore = defineStore('joinServerStore', () => {
  const notification = useNotification()
  const dataStore = reactive({
    code: ''
  })
  const loading = ref(false)
  const errors = ref<any>(null)

  const joinServer = async () => {
    const userToken = localStorage.getItem('token')

    try {
      loading.value = true
      if (dataStore.code) {
        const { data } = await joinServerQuery(dataStore.code, userToken)
        notification.notify({
          title: 'Success',
          type: 'success',
          text: 'Success join'
        })
        dataStore.code = ''
        await serverStore.getMyServers()
      }
    } catch (err) {
      if (err instanceof AxiosError) {
        errors.value = err.status
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

  return { dataStore, joinServer, loading, errors }
})
