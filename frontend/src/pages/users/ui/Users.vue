<template>
  <div class="relative px-2 py-8 h-full overflow-y-auto">
    <Loader v-if="!filteredUsers"></Loader>
    <div class="w-64 m-auto mb-10">
      <SearchUsers @update:filteredUsers="setFilteredUsers"></SearchUsers>
    </div>
    <UserList v-if="filteredUsers" :users="filteredUsers"></UserList>
  </div>
</template>

<script setup lang="ts">
import UserList from '@/widgets/user-list'
import { getAllUsers } from '@/entities/user'
import { onMounted, ref, computed } from 'vue'
import { type User } from '@/entities/user'
import SearchUsers from '@/features/search-users'
import Loader from '@/shared/ui/loader/Loader.vue'
import Popup from '@/shared/ui/popup'
import { useUser } from '@/entities/user'
import { storeToRefs } from 'pinia'

const userStore = useUser()

const filteredUsers = ref<User[]>([])

const setFilteredUsers = (usersList: User[]) => {
  filteredUsers.value = usersList
}

onMounted(async () => {
  await userStore.getUsers()
})
</script>

<style></style>
