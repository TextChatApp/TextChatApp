<template>
  <div class="flex justify-between items-center">
    <div class="flex gap-4 sm:gap-6 items-end">
      <div class="text-xl md:text-2xl">#{{ info?.name }}</div>
      <div class="flex items-center gap-2">
        <div class="flex-1 min-w-0">
          <button
            v-if="isAdmin"
            class="text-sm bg-accent-main py-1 px-3 rounded-md hover:opacity-80 transition-all overflow-hidden truncate flex-shrink"
            @click="openPopup()"
          >
            Change
          </button>
        </div>
        <div class="flex-1 min-w-0">
          <DeleteRoom v-if="isAdmin" :roomId="info?.id" :serverId="serverId"></DeleteRoom>
        </div>
      </div>
    </div>
    <div class="relative">
      <div @click="toggleDropdown()">
        <IconSettings
          :size="30"
          :isActive="isOpenDropdown"
          class="cursor-pointer w-7 h-7 sm:w-auto sm:h-auto"
        ></IconSettings>
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
          <h3 class="font-bold">Server code</h3>
        </div>
        <div class="flex flex-col gap-4 mb-4">
          <CopyText
            :text-to-copy="serverCode"
            :placeholder-copied="'Code copied'"
            :placeholder-to-copy="'Copy server code'"
          ></CopyText>
        </div>
        <div class="mb-2">
          <h3 class="font-bold">Settings:</h3>
        </div>
        <div><LeaveServer :server-id="serverId"></LeaveServer></div>
      </Dropdown>
    </div>
  </div>
  <Popup ref="popupChangeName">
    <template v-slot:header><h3 class="text-2xl mb-8">Change room name</h3></template>
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
import DeleteRoom from '@/features/delete-room'
import MembersList from './MembersList.vue'
import LeaveServer from '@/features/leave-server'
import { MembersIcon } from '@/shared/ui/icons/headerIcons'
import { IconSettings } from '@/shared/ui/icons/menu'
import Dropdown from '@/shared/ui/dropdown'
import Popup from '@/shared/ui/popup/Popup.vue'
import ChangeRoomName from '@/features/change-room-name'
import CopyText from '@/shared/ui/copy-text'

interface Info {
  info: { id?: number; name?: string }
  members: [{ id?: number; username?: string; email?: string; status?: string }]
}

const isAdmin = inject('isUserAdmin')

const isOpenDropdown = ref(false)

const popupChangeName = ref()

const serverId: number | undefined = inject('serverId')
const serverCode: string | undefined = inject('serverCode')

const openPopup = () => {
  popupChangeName.value?.open()
}

const closePopup = () => {
  popupChangeName.value?.close()
}

const deleteRoom = async () => {
  try {
  } catch (err) {
    console.log(err)
  }
}

const toggleDropdown = () => {
  isOpenDropdown.value = !isOpenDropdown.value
}

const props = defineProps<Info>()
</script>

<style></style>
