<template>
  <div class="flex justify-between items-center">
    <div class="flex gap-6 items-end">
      <div class="text-2xl">#{{ info?.name }}</div>
      <button
        v-if="isAdmin"
        class="text-sm bg-accent-main py-1 px-3 rounded-md hover:opacity-80 transition-all"
        @click="openPopup()"
      >
        Change name
      </button>
    </div>
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
  <Popup ref="popupChangeName">
    <template v-slot:header><h3 class="text-2xl mb-8">Join to server</h3></template>
    <template v-slot:default
      ><ChangeRoomName
        :room-id="info?.id"
        :server-id="serverId"
        @close-popup="closePopup"
      ></ChangeRoomName>
    </template>
  </Popup>
</template>

<script setup lang="ts">
import { inject, onMounted, ref } from 'vue'
import { serverUsers } from '@/entities/server'
import MembersList from './MembersList.vue'
import LeaveServer from '@/features/leave-server'
import { MembersIcon } from '@/shared/ui/icons/headerIcons'
import { IconSettings } from '@/shared/ui/icons/menu'
import Dropdown from '@/shared/ui/dropdown'
import Popup from '@/shared/ui/popup/Popup.vue'
import ChangeRoomName from '@/features/change-room-name'

interface Info {
  info: { id?: number; name?: string }
  members: [{ id?: number; username?: string; email?: string; status?: string }]
}

const isAdmin = inject('isUserAdmin')

const isOpenDropdown = ref(false)

const popupChangeName = ref()

const serverId: number | undefined = inject('serverId')

const openPopup = () => {
  popupChangeName.value?.open()
}

const closePopup = () => {
  popupChangeName.value?.close()
}

const toggleDropdown = () => {
  isOpenDropdown.value = !isOpenDropdown.value
}

const props = defineProps<Info>()
</script>

<style></style>
