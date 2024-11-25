<template>
  <button
    :disabled="props.disabled"
    class="w-full md:w-1/2 px-10 py-2 text-center bg-accent-main rounded-xl text-white transition-all hover:opacity-90 focus:outline-accent-blue active:translate-y-1"
  >
    {{ text }}
  </button>
</template>

<script setup lang="ts">
interface Props {
  text: string
  disabled?: boolean
}

const props = defineProps<Props>()
</script>

<style scoped></style>
