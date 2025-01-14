<template>
  <button
    class="w-full gap-2 text-center font-bold text-lg text-white outline-none transition-all hover:opacity-80 active:translate-y-1"
    @click="logout()"
  >
    <span class="inline-block mr-4">Logout</span>
    <IconLogout class="inline-block"></IconLogout>
  </button>
</template>

<script setup lang="ts">
import { IconLogout } from '@/shared/ui/icons/menu'
import { useUser } from '@/entities/user'
import { useRouter } from 'vue-router'

const userSrore = useUser()
const router = useRouter()

const logout = () => {
  userSrore.logout()
  router.push({ path: '/login' })
}
</script>

<style></style>
