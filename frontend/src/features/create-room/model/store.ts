import { defineStore, storeToRefs } from 'pinia'
import { reactive, ref } from 'vue'
import { roomCreate } from '@/entities/server'
import { useNotification } from '@kyvg/vue3-notification'
import { AxiosError } from 'axios'
import { useServerStore } from '@/entities/server'

const serverStore = useServerStore()
const { servers } = storeToRefs(serverStore)

export const useRoomCreatingStore = defineStore('roomCreatingStore', () => {
  const notification = useNotification()
  const roomData = reactive({
    name: ''
  })
  const loading = ref(false)

  const createRoom = async (serverId: any) => {
    try {
      loading.value = true
      if (roomData) {
        const { data } = await roomCreate(serverId, roomData)
        notification.notify({
          title: 'Success',
          type: 'success',
          text: 'Room created'
        })
        await serverStore.getMyServers()
        roomData.name = ''
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

  return { roomData, createRoom, loading }
})
