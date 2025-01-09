<template>
  <div class="bg-accent-main rounded-xl px-5 py-3 w-full break-words">
    <p>
      {{ message?.content }}
    </p>
  </div>
</template>

<script setup lang="ts">
import { useUser } from '@/entities/user'
import { onMounted } from 'vue'

const props = defineProps({
  message: Object
})
</script>

<style></style>
