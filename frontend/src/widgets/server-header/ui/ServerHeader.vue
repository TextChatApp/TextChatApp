<template>
  <div class="flex">
    <div class="text-xl">#{{ info?.name }}</div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { serverUsers } from '@/entities/server'

interface Info {
  info: { id?: number; name?: string }
  members: [{ id?: number; username?: string; email?: string; status?: string }]
}

const props = defineProps<Info>()
</script>

<style></style>
