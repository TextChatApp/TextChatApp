<template>
  <svg
    :width="size"
    :height="size"
    viewBox="0 0 25 25"
    fill="none"
    class="transition-all"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path d="M16.5 15V19.5H5.5V5.5H16.5V10M10 12.5H22.5" :stroke="color" stroke-width="1.2" />
    <path d="M20 10L22.5 12.5L20 15" :stroke="color" stroke-width="1.2" />
  </svg>
</template>

<script setup lang="ts">
defineProps({
  color: String,
  size: Number
})
</script>

<style scoped>
svg:hover {
  transform: scale(1.2);
}
</style>
