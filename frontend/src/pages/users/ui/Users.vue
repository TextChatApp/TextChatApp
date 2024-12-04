<template>
  <div class="p-10">
    <Loader v-if="!users"></Loader>
    <UserList v-if="users" :users="users"></UserList>
  </div>
</template>

<script setup lang="ts">
import UserList from '@/widgets/user-list'
import { getAllUsers } from '@/entities/user'
import { onMounted, ref } from 'vue'
import Loader from '@/shared/ui/loader/Loader.vue'

const users = ref()

const getUsers = async () => {
  try {
    const { data } = await getAllUsers()
    users.value = data
  } catch (err) {
    console.log(err)
  }
}

onMounted(async () => {
  await getUsers()
})
</script>

<style></style>
