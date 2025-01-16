<template>
  <button
    class="text-sm bg-red-500 py-1 px-3 rounded-md hover:opacity-80 transition-all truncate"
    @click="deleteRoom()"
  >
    Delete
  </button>
</template>

<script setup lang="ts">
import { deleteRoomQuery } from '@/entities/server'
import { useUser } from '@/entities/user'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { useServerStore } from '@/entities/server'

const serverStore = useServerStore()
const { servers } = storeToRefs(serverStore)

const userStore = useUser()
const router = useRouter()

const { getUserId } = storeToRefs(userStore)

const props = defineProps({
  roomId: Number,
  serverId: Number
})

const deleteRoom = async () => {
  try {
    const { data } = await deleteRoomQuery(props.roomId)
    router.push('/')
  } catch (err) {
    console.log(err)
  }
}
</script>

<style scoped>
div:hover {
  transform: scale(1.05);
}
</style>
