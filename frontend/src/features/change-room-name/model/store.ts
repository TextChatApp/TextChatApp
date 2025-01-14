import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'
import { joinServerQuery } from '@/entities/server'
import { useNotification } from '@kyvg/vue3-notification'
import { AxiosError } from 'axios'
import { useServerStore } from '@/entities/server'
import { storeToRefs } from 'pinia'
import { updateRoomNameQuery } from '@/entities/server'

const serverStore = useServerStore()
const { servers } = storeToRefs(serverStore)

export const useChangeNameStore = defineStore('changeNameStore', () => {
  const notification = useNotification()
  const dataStore = reactive({
    name: ''
  })
  const loading = ref(false)
  const errors = ref<any>(null)

  const changeName = async (roomId: any, serverId: any) => {
    const userToken = localStorage.getItem('token')

    try {
      loading.value = true
      if (dataStore.name) {
        const { data } = await updateRoomNameQuery(roomId, dataStore.name)
        notification.notify({
          title: 'Success',
          type: 'success',
          text: 'Success join'
        })
        dataStore.name = ''
        await serverStore.getRoomInfo(roomId, serverId)
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

  return { dataStore, changeName, loading, errors }
})
