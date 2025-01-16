<template>
  <div v-for="member in members" class="flex justify-between items-center flex-wrap">
    <div class="mr-4 max-w-32">
      <div class="font-bold text-xl mr-1 truncate">{{ member.username }}</div>
      <span>({{ member.role }})</span>
    </div>
    <div class="flex gap-4 text-sm" v-if="isUserAdmin">
      <DeleteUser :userId="member.id" :server-id="serverId"></DeleteUser>
      <UpdateUser :user-id="member.id" :server-id="serverId"></UpdateUser>
    </div>
  </div>
</template>

<script setup lang="ts">
import DeleteUser from '@/features/users/delete-from-server-user'
import UpdateUser from '@/features/users/update-user-server'
import { inject } from 'vue'

interface Props {
  members: [{ id?: number; username?: string; email?: string; status?: string; role?: string }]
}

const props = defineProps<Props>()

const isUserAdmin = inject('isUserAdmin')
const serverId: any = inject('serverId')
</script>

<style></style>
