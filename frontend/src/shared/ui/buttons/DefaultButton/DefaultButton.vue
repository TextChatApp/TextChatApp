<template>
  <button
    :disabled="props.disabled"
    :class="[{ error: disabled }]"
    class="gap-2 md:w-1/2 px-10 py-2 text-center bg-accent-main rounded-xl text-white transition-all hover:opacity-90 focus:outline-accent-blue active:translate-y-1"
  >
    <div>
      <p v-if="!loading">{{ text }}</p>
      <div><Loader v-if="loading" :width="'20px'" :height="'20px'" :isDark="true"></Loader></div>
    </div>
  </button>
</template>

<script setup lang="ts">
import Loader from '../../loader'

interface Props {
  text: string
  disabled?: boolean
  loading?: boolean
}

const props = defineProps<Props>()
</script>

<style scoped>
.error {
  opacity: 0.2;
  cursor: not-allowed;
  outline: none;
}
</style>
