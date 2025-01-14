<template>
  <button @click="deleteUserFromServer()" class="text-red-400">Delete</button>
</template>

<script setup lang="ts">
import { deleteUserQuery } from '@/entities/server'
import { ref } from 'vue'
import { useServerStore } from '@/entities/server'
import { updateUserQuery } from '@/entities/server/api'

const serverStore = useServerStore()

const props = defineProps({
  serverId: Number,
  userId: Number
})

const deleteUserFromServer = async () => {
  try {
    const { data } = await deleteUserQuery(props.serverId, props.userId)
    serverStore.deleteMemberFromServer(props.userId)
  } catch (err) {
    console.log(err)
  }
}
</script>

<style></style>
