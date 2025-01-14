<template>
  <header class="pl-10 sm:pl-24 py-4 pr-10 flex justify-between items-center">
    <div class="mx-auto sm:mx-0 sm:ml-auto"><HeaderInput></HeaderInput></div>
    <div class="hidden sm:ml-auto sm:flex gap-4">
      <LogoutButton></LogoutButton>
    </div>
    <!-- <div>
      <div>1</div>
      <div>2</div>
      <div>3</div>
    </div> -->
  </header>
</template>

<script setup lang="ts">
import HeaderInput from '@/shared/ui/inputs/HeaderInput'
import defaultAvatar from '@/shared/assets/image/default-avatar-accent.png'
import { LogoutButton } from '@/features/auth'
</script>

<style></style>
