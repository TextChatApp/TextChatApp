<template>
  <div class="flex justify-between items-center">
    <div class="text-xl">#{{ info?.name }}</div>
    <div class="relative">
      <div @click="toggleDropdown()">
        <IconSettings :size="30" :isActive="isOpenDropdown" class="cursor-pointer"></IconSettings>
      </div>
      <Dropdown :isOpen="isOpenDropdown">
        <h2 class="text-xl font-bold mb-10 text-accent-main text-center">Server info</h2>
        <div class="mb-2">
          <h3 class="font-bold">Members:</h3>
        </div>
        <div class="flex flex-col gap-4 mb-4">
          <MembersList :members="members"></MembersList>
        </div>
        <div class="mb-2">
          <h3 class="font-bold">Settings:</h3>
        </div>
        <div><LeaveServer :server-id="serverId"></LeaveServer></div>
      </Dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { inject, onMounted, ref } from 'vue'
import { serverUsers } from '@/entities/server'
import MembersList from './MembersList.vue'
import LeaveServer from '@/features/leave-server'
import { MembersIcon } from '@/shared/ui/icons/headerIcons'
import { IconSettings } from '@/shared/ui/icons/menu'
import Dropdown from '@/shared/ui/dropdown'

interface Info {
  info: { id?: number; name?: string }
  members: [{ id?: number; username?: string; email?: string; status?: string }]
}

const isOpenDropdown = ref(false)

const serverId: number | undefined = inject('serverId')

const toggleDropdown = () => {
  isOpenDropdown.value = !isOpenDropdown.value
}

const props = defineProps<Info>()
</script>

<style></style>
