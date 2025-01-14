<template>
  <div class="flex flex-col items-start">
    <span :class="{ 'self-end': own }" class="text-sm">{{ username }}</span>
    <div class="relative flex items-center gap-1"></div>
    <div
      class="relative bg-accent-main rounded-xl px-5 pt-4 py-6 w-full break-words"
      :class="{ 'bg-message-bg': !own }"
    >
      <p>
        {{ message?.content }}
      </p>
      <span
        class="absolute text-xs text-grey-main bottom-1"
        :class="{ 'left-2': own, 'right-2': !own }"
        >{{ formatDate(message?.createdAt) }}</span
      >
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUser } from '@/entities/user'
import { onMounted } from 'vue'
import { formatDate } from '@/shared/utils/FormatDate'

const props = defineProps({
  message: Object,
  own: Boolean,
  username: String
})
</script>

<style></style>
