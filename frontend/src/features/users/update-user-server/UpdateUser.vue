<template>
  <input
    type="text"
    class="bg-transparent outline-none border-2 border-accent-main rounded-md placeholder:text-xs"
    placeholder="Change role"
    v-model="newRole"
    @keydown.enter="update()"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useServerStore } from '@/entities/server'
import { updateUserQuery } from '@/entities/server/api'

const newRole = ref()
const serverStore = useServerStore()

const props = defineProps({
  serverId: Number,
  userId: Number
})

const update = async () => {
  try {
    const { data } = await updateUserQuery(props.serverId, props.userId, newRole.value)
    serverStore.setNewRoleMember(props.userId, newRole.value)
    newRole.value = ''
  } catch (err) {
    err
  }
}
</script>

<style></style>
