<template>
  <ChatsInput v-model="query" :placeholder="'Find users'"></ChatsInput>
</template>

<script setup lang="ts">
import ChatsInput from '@/shared/ui/inputs/ChatsInput'
import { getAllUsers } from '@/entities/user'
import { useUser } from '@/entities/user'
import { ref, computed, watchEffect } from 'vue'
import { storeToRefs } from 'pinia'

const query = ref('')

const userStore = useUser()
const { users } = storeToRefs(userStore)

const emit = defineEmits(['update:filteredUsers'])

const filteredUsers = computed(() => {
  if (!query.value) return users.value // Если ничего не ищем, возвращаем всех пользователей
  return users.value.filter((user) =>
    user.username.toLowerCase().includes(query.value.toLowerCase())
  )
})

watchEffect(() => {
  emit('update:filteredUsers', filteredUsers.value)
})
</script>

<style></style>
