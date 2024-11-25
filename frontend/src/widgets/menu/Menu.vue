<template>
  <div
    class="px-4 py-4 sm:py-5 flex items-center justify-center min-h-auto bg-main w-full z-50 bottom-0 left-0 fixed gap-40 sm:flex-col sm:min-h-screen sm:w-auto sm:justify-start"
  >
    <Logo></Logo>
    <MenuNavigation></MenuNavigation>
  </div>
</template>

<script setup lang="ts">
import { Logo } from '@/shared/ui/logo'
import { IconHome, IconChats, IconSettings } from '@/shared/ui/icons/menu'
import { MenuNavigation } from '@/features/menu-nav'
</script>

<style></style>
