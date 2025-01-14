<template>
  <div class="flex items-center justify-between w-full">
    <div class="flex gap-x-4 w-full sm:w-auto">
      <div class="flex-1 min-w-0">
        <span class="block font-bold text-base truncate">{{ room.name }}</span>
      </div>
    </div>
    <div class="flex flex-col items-end text-right">
      <!-- <span class="block font-bold text-xs text-grey-main truncate">4:30 PM</span> -->
      <!-- <span class="block font-bold text-xs text-grey-main truncate">dsdsd</span> -->
    </div>
  </div>
</template>

<script setup lang="ts">
import user from '@/shared/assets/image/default-avatar-accent.png'

import { storeToRefs } from 'pinia'
import { computed } from 'vue'

interface Room {
  id?: number
  name?: string
  description?: string
  createdAt?: string
}

const props = defineProps<{
  room: Room
}>()
</script>

<style scoped></style>
