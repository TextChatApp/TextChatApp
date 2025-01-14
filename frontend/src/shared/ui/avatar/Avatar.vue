<template>
  <img
    :src="url ? imgSrc : defaultAvatar"
    :width="size"
    :height="size"
    alt=""
    class="rounded-full"
  />
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import defaultAvatar from '@/shared/assets/image/default-avatar-accent.png'
const imgSrc = ref('')

const props = defineProps({
  url: String,
  size: Number
})

watch(
  () => props.url,
  (newUrl) => {
    imgSrc.value = `http://localhost:8080/${newUrl}` // Используем путь, возвращаемый сервером
  },
  { immediate: true }
)
</script>

<style></style>
