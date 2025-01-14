<template>
  <div class="rounded-xl h-full overflow-y-auto">
    <div class="flex gap-8 flex-wrap justify-center items-start">
      <TransitionGroup name="list"
        ><UserItem
          v-for="user in users"
          :key="user.id"
          :user="user"
          class="user-item flex-shrink-0"
      /></TransitionGroup>
    </div>
  </div>
</template>

<script setup lang="ts">
import UserItem from './UserItem.vue'
import SearchUsers from '@/features/search-users'

const props = defineProps({
  users: Object
})
</script>

<style scoped></style>
