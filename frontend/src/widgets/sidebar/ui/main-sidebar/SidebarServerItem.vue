<template>
  <div class="flex items-center justify-between w-full">
    <div class="flex gap-x-4 w-full sm:w-auto">
      <div>
        <img :src="user" alt="" width="50" height="50" />
      </div>
      <div class="flex-1 min-w-0">
        <span class="block font-bold text-base truncate">{{ props.server?.name }}</span>
        <p class="block text-sm truncate">{{ props.server.description }}</p>
      </div>
    </div>
    <div class="flex flex-col items-end text-right">
      <span class="block font-bold text-xs text-grey-main truncate">4:30 PM</span>
      <!-- <span class="block font-bold text-xs text-grey-main truncate">dsdsd</span> -->
    </div>
  </div>
</template>

<script setup lang="ts">
import user from '@/shared/assets/image/default-avatar-accent.png'

import { storeToRefs } from 'pinia'
import { computed } from 'vue'

interface Server {
  id?: number
  name?: string
  description?: string
  createdAt?: string
}

const props = defineProps<{
  server: Server
}>()
</script>

<style></style>
