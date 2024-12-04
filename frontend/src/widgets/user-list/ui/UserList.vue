<template>
  <div class="p-10 bg-accent-main rounded-xl">
    <div class="flex flex-col justify-center items-start">
      <UserItem v-for="user in users" :user="user"></UserItem>
    </div>
  </div>
</template>

<script setup lang="ts">
import { type User } from '@/entities/user'
import UserItem from './UserItem.vue'

const props = defineProps<{
  users: User
}>()
</script>

<style></style>
